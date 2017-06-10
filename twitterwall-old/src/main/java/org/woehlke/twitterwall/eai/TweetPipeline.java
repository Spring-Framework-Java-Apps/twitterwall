package org.woehlke.twitterwall.eai;

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

import org.woehlke.twitterwall.service.TagCache;
import org.woehlke.twitterwall.service.TweetCache;

@MessageEndpoint
public class TweetPipeline {

	private final Logger LOGGER = LoggerFactory.getLogger(TweetPipeline.class);

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

	public Tweet addTwitterMessagesFromSearch(Tweet tweet) {
		try {
			tweetCache.addTwitterMessage(tweet);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}
		return tweet;
	}

	@Filter
	public boolean isTweetFromSearchNotYetCached(Tweet tweet) {
		return !tweetCache.isTweetInCache(tweet.getId());
	}

	public RequestTwitterwallEvent requestTwitterwall() {
		return new RequestTwitterwallEvent(searchterm);
	}

	@Splitter
	public List<Tweet> fetchTwitterwallFromSearch(RequestTwitterwallEvent e) {
		return twitterTemplate.searchOperations()
				.search(e.getSearchterm(), pagesize).getTweets();
	}

	public Tweet updateTaglist(Tweet tweet) {
		try {
			tagCache.updateTagsForOneTweetsText(tweet.getText());
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}
		return tweet;
	}

	public UpdateTagCloudEvent requestUpdateTagCloudEvent() {
		return new UpdateTagCloudEvent();
	}

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
