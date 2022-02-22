package fr.committer.tech.rssreader.controller;

import com.rometools.rome.io.FeedException;
import fr.committer.tech.rssreader.model.ChannelEntity;
import fr.committer.tech.rssreader.model.FeedEntity;
import fr.committer.tech.rssreader.model.RssChannel;
import fr.committer.tech.rssreader.service.RssReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ClientController {

    @Autowired
    private RssReaderService rssReaderService;

    @RequestMapping(value = "/client")
    @ResponseBody
    public RssChannel getClient(@RequestParam String url) throws IOException, FeedException {
        return rssReaderService.getClient(url);
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