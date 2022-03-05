package fr.committer.tech.rssreader.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "channel")
public class ChannelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private Integer id;

    @Column(name = "channel_title")
    private String title;

    @Column(name = "channel_link")
    private String link;

    @Column(name = "channel_type")
    private String type;

    @Column(name = "channel_encoding")
    private String encoding;

    @Column(name = "channel_author")
    private String author;

    @Column(name = "channel_copyright")
    private String copyright;

    @Column(name = "channel_language")
    private String language;

    @Column(name = "channel_description")
    private String description;

    @Column(name = "channel_published_at")
    private Timestamp publishedAt;

    @Column(name = "channel_updated_at")
    private Timestamp updatedAt;

    @Column(name = "channel_last_run_at")
    private Timestamp lastRunAt;

    @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<FeedEntity> feeds;
}
