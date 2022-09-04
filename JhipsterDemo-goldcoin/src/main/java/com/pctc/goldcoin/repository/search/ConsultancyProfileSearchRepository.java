package com.pctc.goldcoin.repository.search;

import com.pctc.goldcoin.domain.ConsultancyProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ConsultancyProfile entity.
 */
public interface ConsultancyProfileSearchRepository extends ElasticsearchRepository<ConsultancyProfile, Long> {
}
