package fr.committer.tech.rssreader.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feed")
public class FeedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Integer id;

    @Column(name = "feed_link")
    private String link;

    @Column(name = "feed_title")
    private String title;

    @Column(name = "feed_description_type")
    private String descriptionType;

    @Column(name = "feed_description_value")
    private String descriptionValue;

    @Column(name = "feed_pub_date")
    private Date pubDate;

    @Column(name = "feed_comments")
    private String comments;

    @Column(name = "feed_type")
    private String type;

    @Column(name = "feed_language")
    private String language;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "feed_channel_id")
    @JsonBackReference
    private ChannelEntity channel;
}
