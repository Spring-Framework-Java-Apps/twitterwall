package org.woehlke.spring.cache.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.social.twitter.api.Tweet;
import org.woehlke.spring.cache.model.TweetCached;

public interface TweetCache {
	
	void addTwitterMessage(Tweet tweet);
	
	TweetCached transformTweet2TwitterMessage(Tweet tweet);
	
	boolean isTweetInCache(long twitterId);

	Page<TweetCached> getTwitterMessages(Pageable pageable);

	void resetTagCloud();

}
