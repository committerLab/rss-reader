package fr.committer.tech.rssreader.repository;

import fr.committer.tech.rssreader.model.ChannelEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ChannelRepository extends PagingAndSortingRepository<ChannelEntity, Integer> {
    Optional<ChannelEntity> findByLink(String link);
}
