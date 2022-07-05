package fr.committer.tech.rssreader.repository;

import fr.committer.tech.rssreader.model.ChannelEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends PagingAndSortingRepository<ChannelEntity, Integer> {

    Optional<ChannelEntity> findByLink(String link);

    Long countByNextRunAtBeforeAndCountRunErrorLessThanOrderByNextRunAtAsc(Date now, Integer countError);

    List<ChannelEntity> findTop20ByNextRunAtBeforeAndCountRunErrorLessThanOrderByNextRunAtAsc(Date now, Integer countError);

    Long countByNextRunAtBeforeAndCountRunErrorLessThanOrNextRunAtIsNullOrderByNextRunAtAsc(Date now, Integer countError);

    List<ChannelEntity> findTop20ByNextRunAtBeforeAndCountRunErrorLessThanOrNextRunAtIsNullOrderByNextRunAtAsc(Date now, Integer countError);

    Optional<ChannelEntity> findFirstByNextRunAtBeforeAndCountRunErrorLessThanOrNextRunAtIsNullOrderByNextRunAtAsc(Date now, Integer countError);

}
