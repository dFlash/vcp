package com.maliavin.vcp.service.impl;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.exception.ApplicationException;
import com.maliavin.vcp.form.ChangePasswordForm;
import com.maliavin.vcp.repository.search.VideoSearchRepository;
import com.maliavin.vcp.repository.storage.UserRepository;
import com.maliavin.vcp.repository.storage.VideoRepository;
import com.maliavin.vcp.security.CurrentUser;
import com.maliavin.vcp.service.CommonService;
import com.maliavin.vcp.service.NotificationService;
import com.maliavin.vcp.service.StatisticsService;

/**
 * Common service for all users.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Service
public class CommonServiceImpl implements CommonService {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Value("${resource.url}")
    private String resourceUrl;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoSearchRepository videoSearchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreDestroy
    private void preDestroy() {
        executorService.shutdown();
    }

    @Override
    public Page<Video> listAllVideos(Pageable pageable) {
        return videoRepository.findAll(pageable);
    }

    @Override
    public Video getVideo(String id, CurrentUser currentUser, HttpServletRequest request) {
        Video video = videoRepository.findOne(id);
        executorService.submit(new StatisticsItem(currentUser, request.getRemoteAddr(), video));
        return video;
    }

    @Override
    public Page<Video> listVideosBySearchQuery(String searchQuery, Pageable pageable) {
        SearchQuery query = createSearchQuery(searchQuery, pageable);
        Page<Video> videos = videoSearchRepository.search(query);
        return videos;
    }

    private SearchQuery createSearchQuery(String searchQuery, Pageable pageable) {
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(new MultiMatchQueryBuilder(searchQuery, "owner.name", "title", "owner.company.name")
                        .fuzziness(Fuzziness.ONE).operator(Operator.OR))
                .withPageable(pageable).build();
        return query;
    }

    @Override
    public void sendMail(String username) {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new ApplicationContextException("User does not exist");
        }
        String hash = generateHash();
        user.setHash(hash);
        userRepository.save(user);
        String restoreUrl = generateRestoreLink(user);
        notificationService.sendRestoreAccessLink(user, restoreUrl);
    }

    private String generateHash() {
        return UUID.randomUUID().toString();
    }

    private String generateRestoreLink(User user) {
        String link = "<a href='" + resourceUrl + "/auth?id=" + user.getId() + "&hash=" + user.getHash() + "'></a>";
        return link;
    }

    @Override
    public User findUser(String id, String hash) {
        return userRepository.findByIdAndHash(id, hash);
    }

    @Override
    public ResponseEntity<String> changePassword(ChangePasswordForm changePasswordForm, CurrentUser currentUser) {

        String newPassword = changePasswordForm.getNewPassword();
        String repeatPassword = changePasswordForm.getRepeatPassword();
        if (StringUtils.isEmpty(newPassword) || !newPassword.equals(repeatPassword)) {
            throw new ApplicationException("Passwords are empty or not equals");
        }
        String id = currentUser.getUser().getId();
        User user = userRepository.findOne(id);
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    private class StatisticsItem implements Runnable
    {
        private CurrentUser currentUser;
        
        private String addr;
        
        private Video video;
        
        public StatisticsItem(CurrentUser currentUser, String addr, Video video) {
            this.currentUser = currentUser;
            this.addr = addr;
            this.video = video;
        }
        @Override
        public void run() {
            statisticsService.save(video.getTitle(), currentUser, addr);
        }
    }

    @Override
    public User findUserByHash(String id, String hash) {
        User user = findUser(id, hash);
        if (user == null)
        {
            throw new ApplicationContextException("User is not authenticated");
        }
        user.setHash(null);
        userRepository.save(user);
        return user;
    }

}
