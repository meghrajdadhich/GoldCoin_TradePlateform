package com.pctc.goldcoin.repository.search;

import com.pctc.goldcoin.domain.CandidateProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CandidateProfile entity.
 */
public interface CandidateProfileSearchRepository extends ElasticsearchRepository<CandidateProfile, Long> {
}
