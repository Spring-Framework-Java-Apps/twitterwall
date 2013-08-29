package org.woehlke.spring.eai.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Splitter;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import org.woehlke.spring.cache.service.TagCache;
import org.woehlke.spring.cache.service.TweetCache;
import org.woehlke.spring.eai.TweetEventController;
import org.woehlke.spring.eai.events.RequestTwitterwallEvent;
import org.woehlke.spring.eai.events.UpdateTagCloudEvent;

@MessageEndpoint
public class TweetEventControllerImpl implements TweetEventController {

	private Logger LOGGER = LoggerFactory
			.getLogger(TweetEventControllerImpl.class);

	@Autowired
	@Qualifier("twitterTemplate")
	private TwitterTemplate twitterTemplate;

	@Inject
	private TweetCache tweetCache;

	@Inject
	private TagCache tagCache;

	@Value("${twitter.searchterm}")
	private String searchterm;

	@Value("${twitter.pagesize}")
	private int pagesize;


	@Override
	public Tweet addTwitterMessagesFromSearch(Tweet tweet) {
		try {
			tweetCache.addTwitterMessage(tweet);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}
		return tweet;
	}

	@Filter
	@Override
	public boolean isTweetFromSearchNotYetCached(Tweet tweet) {
		return !tweetCache.isTweetInCache(tweet.getId());
	}

	@Override
	public RequestTwitterwallEvent requestTwitterwall() {
		return new RequestTwitterwallEvent(searchterm);
	}

	@Override
	@Splitter
	public List<Tweet> fetchTwitterwallFromSearch(RequestTwitterwallEvent e) {
		return twitterTemplate.searchOperations()
				.search(e.getSearchterm(), pagesize).getTweets();
	}

	@Override
	public Tweet updateTaglist(Tweet tweet) {
		try {
			tagCache.updateTagsForOneTweetsText(tweet.getText());
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}
		return tweet;
	}

	@Override
	public UpdateTagCloudEvent requestUpdateTagCloudEvent() {
		return new UpdateTagCloudEvent();
	}

	@Override
	public void resetTagCloud(UpdateTagCloudEvent event) {
		try {
			tagCache.resetTagCloud();
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}
		try {
			tweetCache.resetTagCloud();
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}
	}
}
