package org.woehlke.twitterwall.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.social.twitter.api.Tweet;
import org.woehlke.twitterwall.entities.TweetCached;

public interface TweetCache {
	
	void addTwitterMessage(Tweet tweet);
	
	TweetCached transformTweet2TwitterMessage(Tweet tweet);
	
	boolean isTweetInCache(long twitterId);

	Page<TweetCached> getTwitterMessages(Pageable pageable);

	void resetTagCloud();

}
