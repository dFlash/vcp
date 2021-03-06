package com.maliavin.vcp.repository.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;

/**
 * DAO for entity Video
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface VideoRepository extends PagingAndSortingRepository<Video, String> {

    Page<Video> findByOwner(User owner, Pageable pageable);

    Long deleteByOwnerId(String ownerId);

    Long deleteByOwnerUserCompanyId(String ownerUserCompanyId);
}
