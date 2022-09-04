package com.pctc.goldcoin.repository.search;

import com.pctc.goldcoin.domain.ApplyJob;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ApplyJob entity.
 */
public interface ApplyJobSearchRepository extends ElasticsearchRepository<ApplyJob, Long> {
}
