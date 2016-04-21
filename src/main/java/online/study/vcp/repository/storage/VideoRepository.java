package online.study.vcp.repository.storage;

import org.springframework.data.repository.PagingAndSortingRepository;

import online.study.vcp.domain.Video;
/**
 * DAO for entity Video 
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface VideoRepository extends PagingAndSortingRepository<Video, String>{
}
