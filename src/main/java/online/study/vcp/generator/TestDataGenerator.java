package online.study.vcp.generator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.FileChannelWrapper;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import online.study.vcp.config.MongoConfig;
import online.study.vcp.domain.Company;
import online.study.vcp.domain.User;
import online.study.vcp.domain.Video;

@Deprecated
public class TestDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataGenerator.class);
    private static final String[] VIDEO_LINKS = {
            "https://www.dropbox.com/s/x28bgvxnuxeqmi5/22.mp4?dl=1",
            "https://www.dropbox.com/s/w8xy8hjhugvhibt/How%20the%20Internet%20Works.mp4?dl=1",
            "https://www.dropbox.com/s/088f5bn54g3pwqx/Learn%20Functional%20Python%20at%20Treehouse.mp4?dl=1",
            "https://www.dropbox.com/s/cpjnejopdl6jz9i/11.mp4?dl=1"};

    private static final Random RANDOM = new Random();

    public static void main(String[] args) throws Exception {
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext()) {
            ctx.register(MongoConfig.class);
            ctx.refresh();

            MongoOperations mongoOps = ctx.getBean(MongoOperations.class);

            mongoOps.remove(new Query(), Company.class);
            mongoOps.remove(new Query(), User.class);
            mongoOps.remove(new Query(), Video.class);

            clearMediaSubFolders();

            List<Company> companies = buildCompanies();
            for (Company company : companies) {
                mongoOps.insert(company);
            }

            LOGGER.info("Companies inserted");

            List<User> users = buildUsers(companies);
            for (User account : users) {
                mongoOps.insert(account);
            }
            LOGGER.info("Users inserted");

            List<Video> videos = buildVideo(users);
            mongoOps.insert(videos, Video.class);
            LOGGER.info("Videos inserted");

            LOGGER.info("Insert succesful");
        }
    }

    private static void clearMediaSubFolders() {
        for (File f : new File("src/main/webapp/media/thumbnails").listFiles()) {
            f.delete();
        }
        for (File f : new File("src/main/webapp/media/video").listFiles()) {
            f.delete();
        }
        LOGGER.info("Media sub folders cleared");
    }

    private static List<Company> buildCompanies() {
        return Arrays.asList(new Company("Google", "1600 Amphitheatre Parkway", "goo@gmail.com", "12-32-45"),
                new Company("Microsoft", "Redmond, WA", "ms@ms.com", "555-15-36-25"));
    }

    private static List<User> buildUsers(List<Company> companies) {
        return Arrays.asList(
                new User("Tim", "Roberts", "tim", "tim@gmail.com", companies.get(RANDOM.nextInt(companies.size())),
                        "https://s3.amazonaws.com/uifaces/faces/twitter/mantia/128.jpg"),
                new User("Max", "Pane", "max", "max@gmail.com", companies.get(RANDOM.nextInt(companies.size())),
                        "https://s3.amazonaws.com/uifaces/faces/twitter/emirik/128.jpg"),
                new User("Robert", "Dann", "rob", "robert@gmail.com", companies.get(RANDOM.nextInt(companies.size())),
                        "https://s3.amazonaws.com/uifaces/faces/twitter/andyvitale/128.jpg"));
    }

    private static List<Video> buildVideo(List<User> users) throws IOException, JCodecException {
        List<Video> videos = new ArrayList<Video>();
        int index = 1;
        for (String videoLink : VIDEO_LINKS) {
            String uid = UUID.randomUUID().toString() + ".mp4";
            File destVideo = new File("src/main/webapp/media/video", uid);

            try (InputStream in = new URL(videoLink).openStream()) {
                Files.copy(in, Paths.get(destVideo.getAbsolutePath()));
            }

            List<String> thumbnails = createThumbnails(destVideo);
            videos.add(new Video("Cats sing " + index++, "Cats sing song", users.get(RANDOM.nextInt(users.size())),
                    thumbnails, "/media/video/" + uid));
            LOGGER.info("Video {} processed", destVideo.getName());
        }
        return videos;
    }

    private static List<String> createThumbnails(File destVideo) throws IOException, JCodecException {
        List<String> thumbnails = new ArrayList<String>();
        FileChannel fc = FileChannel.open(Paths.get(destVideo.getAbsolutePath()));
        FrameGrab grab = new FrameGrab(
                new FileChannelWrapper(fc));
        for (int i = 0; i < RANDOM.nextInt(15) + 3; i++) {
            Picture nativeFrame = grab.seekToSecondPrecise(i * 3 + i).getNativeFrame();
            if (nativeFrame != null) {
                BufferedImage img = AWTUtil.toBufferedImage(nativeFrame);
                String uid = UUID.randomUUID() + ".jpg";
                ImageIO.write(img, "jpg", new File("src/main/webapp/media/thumbnails", uid));
                thumbnails.add("/media/thumbnails/" + uid);
            }
        }
        LOGGER.info("Created {} thumbnails for video {}", thumbnails.size(), destVideo.getName());
        return thumbnails;
    }
}
