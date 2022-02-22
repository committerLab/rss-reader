package fr.committer.tech.rssreader.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RssChannel {
    private String feedType;
    private String encoding;
    private String uri;
    private String title;
    private String link;
    private String description;
    private String author;
    private String copyright;
    private String language;

    private List<RssItem> items;
}
