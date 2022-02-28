package fr.committer.tech.rssreader.controller;

import com.rometools.rome.io.FeedException;
import fr.committer.tech.rssreader.model.ChannelEntity;
import fr.committer.tech.rssreader.model.FeedEntity;
import fr.committer.tech.rssreader.service.RssReaderService;
import lombok.RequiredArgsConstructor;
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
    public Iterable<FeedEntity> getFeeds(){
        return rssReaderService.getFeeds();
    }

    @RequestMapping(value = "/channel")
    @ResponseBody
    public Iterable<ChannelEntity> getChannel(){
        return rssReaderService.getChannel();
    }
}