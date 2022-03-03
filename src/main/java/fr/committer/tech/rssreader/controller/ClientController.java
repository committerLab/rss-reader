package fr.committer.tech.rssreader.controller;

import com.rometools.rome.io.FeedException;
import fr.committer.tech.rssreader.model.ChannelEntity;
import fr.committer.tech.rssreader.model.FeedEntity;
import fr.committer.tech.rssreader.service.RssReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final RssReaderService rssReaderService;

    @RequestMapping(value = "/client")
    @ResponseBody
    public ChannelEntity getClient(@RequestParam String url) {
        return rssReaderService.addChannel(url);
    }

    @RequestMapping(value = "/feed")
    @ResponseBody
    public Page<FeedEntity> getFeeds(Pageable pageable){
        return rssReaderService.getFeeds(pageable);
    }

    @RequestMapping(value = "/channel")
    @ResponseBody
    public Page<ChannelEntity> getChannel(Pageable pageable){
        return rssReaderService.getChannel(pageable);
    }
}