package com.pctc.goldcoin.repository.search;

import com.pctc.goldcoin.domain.JobSkills;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JobSkills entity.
 */
public interface JobSkillsSearchRepository extends ElasticsearchRepository<JobSkills, Long> {
}
