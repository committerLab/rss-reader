package fr.committer.tech.rssreader.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity(name = "channel")
public class ChannelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private Integer id;

    @Column(name = "channel_title")
    private String title;

    @Column(name = "channel_link")
    private String link;

    @Column(name = "channel_description_type")
    private String descriptionType;

    @Column(name = "channel_description_value")
    private String descriptionValue;

    @Column(name = "channel_published_at")
    private Timestamp publishedAt;

    @Column(name = "channel_updated_at")
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "channel")
    private Set<FeedEntity> feeds;
}
