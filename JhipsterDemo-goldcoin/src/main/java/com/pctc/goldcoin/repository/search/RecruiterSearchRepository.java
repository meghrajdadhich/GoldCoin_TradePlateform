package com.pctc.goldcoin.repository.search;

import com.pctc.goldcoin.domain.Recruiter;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Recruiter entity.
 */
public interface RecruiterSearchRepository extends ElasticsearchRepository<Recruiter, Long> {
}
