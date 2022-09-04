package com.pctc.goldcoin.repository.search;

import com.pctc.goldcoin.domain.ProfessionalProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProfessionalProfile entity.
 */
public interface ProfessionalProfileSearchRepository extends ElasticsearchRepository<ProfessionalProfile, Long> {
}
