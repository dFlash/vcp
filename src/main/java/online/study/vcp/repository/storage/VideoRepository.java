package online.study.vcp.repository.storage;

import org.springframework.data.repository.PagingAndSortingRepository;

import online.study.vcp.domain.Video;

public interface VideoRepository extends PagingAndSortingRepository<Video, String>{
}
