package com.maliavin.vcp.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.service.NotificationService;

/**
 * Email notification service for "Forgot password" functionality
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Service
public class AsyncEmailNotificationService implements NotificationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncEmailNotificationService.class);
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${email.fromEmail}")
    private String fromEmail;

    @Value("${email.fromName}")
    private String fromName;

    @Value("${email.sendTryCount}")
    private int tryCount;

    @PreDestroy
    private void preDestroy() {
        executorService.shutdown();
    }

    @Override
    public void sendRestoreAccessLink(User profile, String restoreLink) {
        LOGGER.debug("Restore link: {} for account {}", restoreLink, profile.getId());
        String email = profile.getEmail();
        if (StringUtils.isNotBlank(email)) {
            String subject = "Restore access";
            String content = "To restore access please click on " + restoreLink;
            String fullName = profile.getName();
            executorService.submit(new EmailItem(subject, content, email, fullName, tryCount));
        } else {
            LOGGER.error("Can't send email to username=" + profile.getId() + ": email not found!");
        }
    }

    private class EmailItem implements Runnable {
        private final String subject;
        private final String content;
        private final String destinationEmail;
        private final String name;
        private int tryCount;

        private EmailItem(String subject, String content, String destinationEmail, String name, int tryCount) {
            super();
            this.subject = subject;
            this.content = content;
            this.destinationEmail = destinationEmail;
            this.name = name;
            this.tryCount = tryCount;
        }

        @Override
        public void run() {
            try {
                LOGGER.debug("Send a new email to {}", destinationEmail);
                MimeMessageHelper message = new MimeMessageHelper(javaMailSender.createMimeMessage(), false);
                message.setSubject(subject);
                message.setTo(new InternetAddress(destinationEmail, name));
                message.setFrom(fromEmail);
                message.setText(content);
                MimeMailMessage msg = new MimeMailMessage(message);
                javaMailSender.send(msg.getMimeMessage());
                LOGGER.debug("Email to {} successful sent", destinationEmail);
            } catch (Exception e) {
                LOGGER.error("Can't send email to " + destinationEmail + ": " + e.getMessage(), e);
                tryCount--;
                if (tryCount > 0) {
                    LOGGER.debug("Decrement tryCount and try again to send email: tryCount={}, destinationEmail={}",
                            tryCount, destinationEmail);
                    executorService.submit(this);
                } else {
                    LOGGER.error("Email not sent to " + destinationEmail);
                }
            }
        }
    }
}
