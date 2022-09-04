package com.pctc.goldcoin.repository.search;

import com.pctc.goldcoin.domain.PCTCBasicUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PCTCBasicUser entity.
 */
public interface PCTCBasicUserSearchRepository extends ElasticsearchRepository<PCTCBasicUser, Long> {
}
