package fr.committer.tech.rssreader.repository;

import fr.committer.tech.rssreader.model.FeedEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FeedRepository extends PagingAndSortingRepository<FeedEntity, Integer> {
}
