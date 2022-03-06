package fr.committer.tech.rssreader.job;

import fr.committer.tech.rssreader.model.ChannelEntity;
import fr.committer.tech.rssreader.model.FeedEntity;
import fr.committer.tech.rssreader.repository.ChannelRepository;
import fr.committer.tech.rssreader.repository.FeedRepository;
import fr.committer.tech.rssreader.service.RssReaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

    private final RssReaderService rssReaderService;

    private final ChannelRepository channelRepository;

    private final FeedRepository feedRepository;

    @Value("#{T(java.time.Duration).parse('${fr.committer.task.frequency}')}")
    private Duration frequency;

    @Value("${fr.committer.task.retryLimit}")
    private Integer retryLimit;

    @Scheduled(fixedRate = 10000)
    public void updateChannelInfo() {
        Long count = channelRepository.countByNextRunAtBeforeAndCountRunErrorLessThanOrderByNextRunAtAsc(new Date(), retryLimit);
        log.info("Task in queue info update: {} with frequency {}", count, frequency);

        rssReaderService.queueForUpdateInfoTask(new Date(), retryLimit)
                .ifPresent(channelForJob -> {
                    log.info("run task for: {} - {} with link {}", channelForJob.getId(), channelForJob.getTitle(), channelForJob.getLink());

                    ChannelEntity channelInfo = rssReaderService.getChannelInfo(channelForJob.getLink());
                    if (channelInfo == null) {
                        postponeTaskWithError(channelForJob);
                    } else {
                        ChannelEntity saved = channelRepository.save(updateChannelFields(channelForJob, channelInfo));
                        log.info("Channel {} updated", saved.getId());
                    }
                });
    }

    @Scheduled(fixedRate = 10000)
    public void getChannelFeeds() {
        Long count = channelRepository.countByNextRunAtBeforeAndCountRunErrorLessThanOrderByNextRunAtAsc(new Date(), retryLimit);
        log.info("Task in queue feed update: {} with frequency {}", count, frequency);
        rssReaderService.queueForUpdateFeedTask(new Date(), retryLimit)
                .ifPresent(channel -> {
                    log.info("Get Feed for channel {} - {}", channel.getTitle(), channel.getId());
                    List<FeedEntity> feeds = rssReaderService.getChannelFeeds(channel.getLink());
                    if (feeds != null) {
                        postponeTask(channel);
                        channel.setFeeds(feeds);
                        feeds.forEach(feedEntity -> {
                            if(feedRepository.existsByLink(feedEntity.getLink())) {
                                log.debug("Feed {} already stored", feedEntity.getLink());
                            } else {
                                feedRepository.save(feedEntity);
                            }
                        });
                    } else {
                        postponeTaskWithError(channel);
                    }
                });
    }

    private void postponeTask(ChannelEntity channel) {
        log.debug("Postpone Channel Task : {}", channel.getId());
        channel.setLastRunAt(Timestamp.from(ZonedDateTime.now().toInstant()));
        channel.setNextRunAt(Timestamp.from(ZonedDateTime.now().plusMinutes(frequency.toMinutes()).toInstant()));
        channel.setUpdatedAt(Timestamp.from(ZonedDateTime.now().toInstant()));
        channelRepository.save(channel);
    }

    private void postponeTaskWithError(ChannelEntity channelForJob) {
        log.warn("Channel info not found for: {}", channelForJob.getLink());
        channelForJob.setLastRunAt(Timestamp.from(ZonedDateTime.now().toInstant()));
        channelForJob.setNextRunAt(Timestamp.from(ZonedDateTime.now().plusMinutes(frequency.toMinutes() * channelForJob.getCountRunError() + 1).toInstant()));
        channelForJob.incrementCountRunError();
        channelRepository.save(channelForJob);
    }

    private ChannelEntity updateChannelFields(ChannelEntity db, ChannelEntity remote) {
        db.setLastRunAt(Timestamp.from(ZonedDateTime.now().toInstant()));
        db.setNextRunAt(Timestamp.from(ZonedDateTime.now().plusMinutes(frequency.toMinutes()).toInstant()));
        db.setUpdatedAt(Timestamp.from(ZonedDateTime.now().toInstant()));
        db.setTitle(remote.getTitle());
        db.setType(remote.getType());
        db.setEncoding(remote.getEncoding());
        db.setDescription(remote.getDescription());
        db.setAuthor(remote.getAuthor());
        db.setCopyright(remote.getCopyright());
        db.setLanguage(remote.getLanguage());
        return db;
    }
}
