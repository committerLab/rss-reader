package fr.committer.tech.rssreader.model;

import fr.committer.tech.rssreader.entity.Channel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Set;

@Data
@Entity(name = "feed")
public class FeedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Integer id;

    @Column(name = "feed_link")
    private String link;

    @Column(name = "feed_name")
    private String name;

    @Column(name = "feed_type")
    private String type;

    @Column(name = "feed_language")
    private String language;

    @ManyToOne
    @JoinColumn(name = "feed_channel_id", referencedColumnName = "channel_id")
    private Channel channel;
}
