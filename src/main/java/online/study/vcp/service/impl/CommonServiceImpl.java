package online.study.vcp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import online.study.vcp.domain.Video;
import online.study.vcp.repository.storage.VideoRepository;
import online.study.vcp.service.CommonService;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public Page<Video> listAllVideos(Pageable pageable) {
        return videoRepository.findAll(pageable);
    }

    @Override
    public Video getVideo(String id) {
        return videoRepository.findOne(id);
    }

}
