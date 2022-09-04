package com.pctc.goldcoin.repository.search;

import com.pctc.goldcoin.domain.Hierarchy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Hierarchy entity.
 */
public interface HierarchySearchRepository extends ElasticsearchRepository<Hierarchy, Long> {
}
