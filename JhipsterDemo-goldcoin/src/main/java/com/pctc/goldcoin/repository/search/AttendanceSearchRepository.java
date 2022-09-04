package com.pctc.goldcoin.repository.search;

import com.pctc.goldcoin.domain.Attendance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Attendance entity.
 */
public interface AttendanceSearchRepository extends ElasticsearchRepository<Attendance, Long> {
}
