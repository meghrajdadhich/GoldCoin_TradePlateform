package com.pctc.goldcoin.repository.search;

import com.pctc.goldcoin.domain.InterviewSchedule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the InterviewSchedule entity.
 */
public interface InterviewScheduleSearchRepository extends ElasticsearchRepository<InterviewSchedule, Long> {
}
