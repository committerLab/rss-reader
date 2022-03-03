package fr.committer.tech.rssreader.service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import fr.committer.tech.rssreader.model.ChannelEntity;
import fr.committer.tech.rssreader.model.FeedEntity;
import fr.committer.tech.rssreader.repository.ChannelRepository;
import fr.committer.tech.rssreader.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

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
            try {
                XmlReader reader = new XmlReader(new URL(url));
                SyndFeed feed = new SyndFeedInput().build(reader);
                ChannelEntity channel = ChannelEntity.builder()
                        .title(feed.getTitle())
                        .link(url)
                        .type(feed.getFeedType())
                        .encoding(feed.getEncoding())
                        .description(feed.getDescription())
                        .author(feed.getAuthor())
                        .copyright(feed.getCopyright())
                        .language(feed.getLanguage())
                        .build();

                return channelRepository.save(channel);
            } catch (FeedException | IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
