package com.maliavin.vcp.repository.storage;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.maliavin.vcp.domain.Company;

/**
 * DAO for entity Company
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface CompanyRepository extends PagingAndSortingRepository<Company, String>{

}
