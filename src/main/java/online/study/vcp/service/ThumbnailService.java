package online.study.vcp.service;

import java.nio.file.Path;
import java.util.List;

public interface ThumbnailService {

    List<String> createThumbnails(Path videoFilePath);
}
