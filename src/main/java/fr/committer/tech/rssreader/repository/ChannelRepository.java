package fr.committer.tech.rssreader.repository;

import fr.committer.tech.rssreader.model.ChannelEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ChannelRepository extends PagingAndSortingRepository<ChannelEntity, Integer> {
}
