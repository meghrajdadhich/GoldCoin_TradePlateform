package com.pctc.goldcoin.repository.search;

import com.pctc.goldcoin.domain.EducationalProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EducationalProfile entity.
 */
public interface EducationalProfileSearchRepository extends ElasticsearchRepository<EducationalProfile, Long> {
}
