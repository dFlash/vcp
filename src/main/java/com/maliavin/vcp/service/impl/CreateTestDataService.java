package com.maliavin.vcp.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maliavin.vcp.domain.Company;
import com.maliavin.vcp.domain.Statistics;
import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.exception.ApplicationException;
import com.maliavin.vcp.form.UploadVideoForm;
import com.maliavin.vcp.service.AvatarService;
import com.maliavin.vcp.service.UserService;

/**
 * Service which creates test data in DB if it is necessary
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Service
public class CreateTestDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateTestDataService.class);

    private static final Random RANDOM = new Random();

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private RedisTemplate<Statistics, String> redisTemplate;

    @Autowired
    private AvatarService avatarService;

    @Value("${media.dir}")
    private String mediaDir;

    @Value("${thumbnails.dir}")
    private String thumbnailsDir;

    @Value("${videos.dir}")
    private String videosDir;

    @Value("${mongo.recreate.db}")
    private boolean mondoRecreateDb;

    @PostConstruct
    public void createTestDataIfNecessary() {
        boolean createTestData = needCreateTestData();
        if (createTestData) {
            createTestData();
        } else {
            LOGGER.info("Mongo db exists");
        }
    }

    private boolean needCreateTestData() {
        boolean createTestData = mondoRecreateDb;

        if (!createTestData) {
            if (!mongoTemplate.collectionExists(Company.class)) {
                createTestData = true;
                LOGGER.info("Collection company not found");
            } else if (!mongoTemplate.collectionExists(User.class)) {
                createTestData = true;
                LOGGER.info("Collection user not found");
            } else if (!mongoTemplate.collectionExists(Video.class)) {
                createTestData = true;
                LOGGER.info("Collection video not found");
            }
        }
        return createTestData;
    }

    private void createTestData() {

        clearMediaSubFolders();

        clearCollections();

        clearElasticSearchIndexes();

        List<Company> companies = buildCompanies();

        List<User> users = buildUsers(companies);

        buildVideos(users);

        LOGGER.info("Test mongo db created successfully");
    }

    private void clearMediaSubFolders() {
        for (File f : new File(thumbnailsDir).listFiles()) {
            f.delete();
        }
        for (File f : new File(videosDir).listFiles()) {
            f.delete();
        }
        LOGGER.info("Media sub folders cleared");
    }

    private void clearCollections() {
        mongoTemplate.remove(new Query(), Video.class);
        mongoTemplate.remove(new Query(), User.class);
        mongoTemplate.remove(new Query(), Company.class);

        clearRedisStorage();
    }

    private void clearRedisStorage() {
        Set<byte[]> keys = redisTemplate.getConnectionFactory().getConnection().keys("*".getBytes());
        Iterator<byte[]> it = keys.iterator();

        while (it.hasNext()) {

            byte[] data = (byte[]) it.next();

            System.out.println(new String(data, 0, data.length));
        }
    }

    private List<Company> buildCompanies() {
        List<Company> companies = Arrays.asList(
                new Company("Google", "1600 Amphitheatre Parkway", "goo@gmail.com", "12-32-45"),
                new Company("Microsoft", "Redmond, WA", "ms@ms.com", "555-15-36-25"));
        for (Company company : companies) {
            mongoTemplate.insert(company);
        }
        LOGGER.info("Created {} test companies", companies.size());
        return companies;
    }

    private List<User> buildUsers(List<Company> companies) {
        List<User> users = Arrays.asList(
                new User("Tim", "Roberts", "tim", "vaks60021@gmail.com",
                        companies.get(RANDOM.nextInt(companies.size())), "User",
                        "$2a$10$uUGLVw.OTwXVRuwxdSN7TuKfHot8i5nMWSMSsEBAKTOiGFjXr53La"),
                new User("Max", "Pane", "max", "dima_91@ukr.net", companies.get(RANDOM.nextInt(companies.size())), "User",
                        "$2a$10$uUGLVw.OTwXVRuwxdSN7TuKfHot8i5nMWSMSsEBAKTOiGFjXr53La"),
                new User("Robert", "Dann", "rob", "tron78_22@mail.ru", companies.get(RANDOM.nextInt(companies.size())),
                        "User", "$2a$10$uUGLVw.OTwXVRuwxdSN7TuKfHot8i5nMWSMSsEBAKTOiGFjXr53La"),
                new User("Admin", "Admin", "admin", "admin@gmail.com", companies.get(RANDOM.nextInt(companies.size())),
                        "Admin", "$2a$10$QMlM1WTQdVw.F4Sp2jf6kOWt9RUeSgHAhOVT8ThXiaqOMs.idVIVa"));
        for (User user : users) {
            String avatar = avatarService.generateAvatarUrl(user.getEmail());
            user.setAvatar(avatar);
            mongoTemplate.insert(user);
        }
        LOGGER.info("Created {} test users", users.size());
        return users;
    }
    
    private void clearElasticSearchIndexes(){
        elasticsearchOperations.deleteIndex(Video.class);
    }

    private void buildVideos(List<User> users) {
        List<String> videoLinks = getVideoLinks();
        List<Video> videos = new ArrayList<Video>();
        int index = 1;
        for (String videoLink : videoLinks) {
            User user = users.get(RANDOM.nextInt(users.size()));
            Video video = userService.uploadVideo(user,
                    new UploadVideoForm("Video" + index, null, new URLMultipartFile(videoLink)));
            videos.add(video);
            LOGGER.info("Video {} processed", index++);
        }
        LOGGER.info("Created {} video files", videos.size());
    }

    private List<String> getVideoLinks() {
        List<String> links = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(mediaDir + "/urls.txt")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                links.add(line);
            }
        } catch (IOException e) {
            throw new ApplicationException(e);
        }

        return links;
    }

    private static class URLMultipartFile implements MultipartFile {
        private final String url;

        public URLMultipartFile(String url) {
            super();
            this.url = url;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getOriginalFilename() {
            return null;
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return 0;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return null;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return null;
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            try (InputStream in = new URL(url).openStream()) {
                Files.copy(in, Paths.get(dest.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
