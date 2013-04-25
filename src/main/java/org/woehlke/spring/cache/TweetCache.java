package org.woehlke.spring.cache;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.social.twitter.api.Tweet;
import org.woehlke.spring.cache.model.ApiSource;
import org.woehlke.spring.cache.model.TweetCached;

public interface TweetCache {
	
	void addTwitterMessage(Tweet tweet,
			ApiSource twitterApiSource);
	
	TweetCached transformTweet2TwitterMessage(Tweet tweet);
	
	boolean isTweetInCache(long twitterId,ApiSource twitterApiSource);

	Page<TweetCached> getTwitterMessages(ApiSource twitterApiSource,
			Pageable pageable);

	Page<TweetCached> getTwitterMessagesForUserId(long userId, Pageable pageable);

	void resetTagCloud();

}
