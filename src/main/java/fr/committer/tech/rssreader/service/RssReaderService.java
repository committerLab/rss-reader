package fr.committer.tech.rssreader.service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import fr.committer.tech.rssreader.model.ChannelEntity;
import fr.committer.tech.rssreader.model.FeedEntity;
import fr.committer.tech.rssreader.model.RssChannel;
import fr.committer.tech.rssreader.model.RssItem;
import fr.committer.tech.rssreader.repository.ChannelRepository;
import fr.committer.tech.rssreader.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class RssReaderService {
    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private FeedRepository feedRepository;

    public Iterable<FeedEntity> getFeeds(){
        return feedRepository.findAll();
    }

    public Iterable<ChannelEntity> getChannel(){
        return channelRepository.findAll();
    }

    public RssChannel getClient(String url) throws IOException, FeedException {

        XmlReader reader = new XmlReader(new URL(url));
        SyndFeed feed = new SyndFeedInput().build(reader);
        System.out.println(feed.getPublishedDate());
        RssChannel.RssChannelBuilder channelBuilder = RssChannel.builder()
                .title(feed.getTitle())
                .link(feed.getLink())
                .feedType(feed.getFeedType())
                .encoding(feed.getEncoding())
                .uri(feed.getUri())
                .description(feed.getDescription())
                .author(feed.getAuthor())
                .copyright(feed.getCopyright())
                .language(feed.getLanguage());
        List<RssItem> rssItems = new ArrayList<>();
        for (SyndEntry entry : feed.getEntries()) {
            rssItems.add(
                    RssItem.builder()
                            .title(entry.getTitle())
                            .description(entry.getDescription())
                            .pubDate(entry.getPublishedDate())
                            .link(entry.getLink())
                            .comments(entry.getComments())
                            .build());
        }

        return channelBuilder.items(rssItems).build();
    }
}
