package fr.committer.tech.rssreader.service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import fr.committer.tech.rssreader.model.ChannelEntity;
import fr.committer.tech.rssreader.model.FeedEntity;
import fr.committer.tech.rssreader.repository.ChannelRepository;
import fr.committer.tech.rssreader.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RssReaderService {

    private final ChannelRepository channelRepository;

    private final FeedRepository feedRepository;

    public Page<FeedEntity> getFeeds(Pageable pageable){
        return feedRepository.findAll(pageable);
    }

    public Page<ChannelEntity> getChannel(Pageable pageable){
        return channelRepository.findAll(pageable);
    }

    public ChannelEntity addChannel(String url) {
        return channelRepository.findByLink(url).orElseGet(() -> {
            ChannelEntity channel = getChannelInfo(url);
            if (channel != null) {
                return channelRepository.save(channel);
            }
            return null;
        });
    }

    public List<FeedEntity> getChannelFeeds(ChannelEntity channel) {
        SyndFeed feed = getSyndFeed(channel.getLink());
        List<FeedEntity> feeds = new ArrayList<>();
        if (feed != null) {
            log.debug("find {} feed", feed.getEntries().size());
            for (SyndEntry entry : feed.getEntries()) {
                feeds.add(
                        FeedEntity.builder()
                                .link(entry.getLink())
                                .title(entry.getTitle())
                                .descriptionType((entry.getDescription() != null) ? entry.getDescription().getType() : null)
                                .descriptionValue((entry.getDescription() != null) ? entry.getDescription().getValue() : null)
                                .pubDate(entry.getPublishedDate())
                                .comments(entry.getComments())
                                .channel(channel)
                                .language((entry.getSource() != null) ? entry.getSource().getLanguage(): null)
                                .type((entry.getSource() != null) ? entry.getSource().getFeedType() : null)
                                .build());
            }
            return feeds;
        }
        return null;
    }

    public ChannelEntity getChannelInfo(String channelLink) {
        SyndFeed feed = getSyndFeed(channelLink);
        if (feed != null) {
            return ChannelEntity.builder()
                    .title(feed.getTitle())
                    .link(channelLink)
                    .type(feed.getFeedType())
                    .encoding(feed.getEncoding())
                    .description(feed.getDescription())
                    .author(feed.getAuthor())
                    .copyright(feed.getCopyright())
                    .language(feed.getLanguage())
                    .build();
        }
        return null;
    }

    public SyndFeed getSyndFeed(String url) {
        try {
            XmlReader reader = new XmlReader(new URL(url));
            return new SyndFeedInput().build(reader);
        } catch (FeedException | IOException | IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public Optional<ChannelEntity> queueForUpdateInfoTask(Date date, Integer retryLimit) {
        return channelRepository.findFirstByNextRunAtBeforeAndCountRunErrorLessThanOrNextRunAtIsNullOrderByNextRunAtAsc(date, retryLimit);
    }

    public List<ChannelEntity> topForUpdateInfoTask(Date date, Integer retryLimit) {
        return channelRepository.findTop20ByNextRunAtBeforeAndCountRunErrorLessThanOrNextRunAtIsNullOrderByNextRunAtAsc(date, retryLimit);
    }

    public List<ChannelEntity> queueForUpdateFeedTask(Date date, Integer retryLimit) {
        return channelRepository.findTop20ByNextRunAtBeforeAndCountRunErrorLessThanOrderByNextRunAtAsc(date, retryLimit);
    }
}
