package fr.committer.tech.rssreader.model;

import com.rometools.rome.feed.rss.Enclosure;
import com.rometools.rome.feed.synd.SyndContent;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RssItem {
    private String title;
    private String link;
    private String guid;
    private SyndContent description;
    private Date pubDate;
    private String comments;
    private Enclosure enclosure;
}
