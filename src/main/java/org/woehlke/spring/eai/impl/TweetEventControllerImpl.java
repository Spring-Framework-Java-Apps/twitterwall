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
import org.springframework.integration.channel.DirectChannel;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import org.woehlke.spring.cache.TagCache;
import org.woehlke.spring.cache.TweetCache;
import org.woehlke.spring.cache.impl.TagCacheImpl;
import org.woehlke.spring.cache.model.ApiSource;
import org.woehlke.spring.eai.TweetEventController;
import org.woehlke.spring.eai.events.RequestTwitterwallEvent;
import org.woehlke.spring.eai.events.UpdateTagCloudEvent;

/*
 * Copyright 2002-2012 the original author or authors
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
@MessageEndpoint
public class TweetEventControllerImpl implements TweetEventController {

	private Logger LOGGER = LoggerFactory
			.getLogger(TweetEventControllerImpl.class);

	@Autowired
	@Qualifier("controlBusChannel")
	private DirectChannel controlBusChannel;

	@Autowired
	@Qualifier("twitterTemplate")
	private TwitterTemplate twitterTemplate;

	@Inject
	private TweetCache tweetCache;

	@Inject
	private TagCache tagCache;

	@Autowired
	@Value("${twitter.searchterm}")
	private String searchterm;

	@Autowired
	@Value("${twitter.pagesize}")
	private int pagesize;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.woehlke.spring.service.impl.TwitterEventControllerService#
	 * startTwitterAdapter()
	 */
	@Override
	public void startTwitterAdapter() {
		/*
		 * final MessagingTemplate m = new MessagingTemplate(); final
		 * Message<String> operationSearch = MessageBuilder.withPayload(
		 * "@twitterSearch.start()").build(); final Message<String>
		 * operationMentions = MessageBuilder.withPayload(
		 * "@twitterMentions.start()").build(); final Message<String>
		 * operationTimeline = MessageBuilder.withPayload(
		 * "@twitterTimeline.start()").build(); m.send(controlBusChannel,
		 * operationSearch); m.send(controlBusChannel, operationMentions);
		 * m.send(controlBusChannel, operationTimeline);
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.woehlke.spring.service.impl.TwitterEventControllerService#
	 * stopTwitterAdapter()
	 */
	@Override
	public void stopTwitterAdapter() {
		/*
		 * final MessagingTemplate m = new MessagingTemplate(); final
		 * Message<String> operationSearch = MessageBuilder.withPayload(
		 * "@twitterSearch.stop()").build(); final Message<String>
		 * operationMentions = MessageBuilder.withPayload(
		 * "@twitterMentions.stop()").build(); final Message<String>
		 * operationTimeline = MessageBuilder.withPayload(
		 * "@twitterTimeline.stop()").build(); m.send(controlBusChannel,
		 * operationSearch); m.send(controlBusChannel, operationMentions);
		 * m.send(controlBusChannel, operationTimeline);
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.woehlke.spring.service.impl.TwitterEventControllerService#
	 * addTwitterMessagesFromSearch
	 * (org.springframework.social.twitter.api.Tweet)
	 */
	@Override
	public Tweet addTwitterMessagesFromSearch(Tweet tweet) {
		tweetCache.addTwitterMessage(tweet, ApiSource.DEFAULT_SEARCH);
		return tweet;
	}

	@Filter
	@Override
	public boolean isTweetFromSearchNotYetCached(Tweet tweet) {
		return !tweetCache.isTweetInCache(tweet.getId(),
				ApiSource.DEFAULT_SEARCH);
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
	public void resetTagCloud(UpdateTagCloudEvent e) {
		tagCache.resetTagCloud();
		tweetCache.resetTagCloud();
	}
}
