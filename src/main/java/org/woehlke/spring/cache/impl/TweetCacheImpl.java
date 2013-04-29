package org.woehlke.spring.cache.impl;


import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.spring.cache.TweetCache;
import org.woehlke.spring.cache.model.ApiSource;
import org.woehlke.spring.cache.model.TweetCached;
import org.woehlke.spring.cache.repository.TweetRepository;

@Service
@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
public class TweetCacheImpl implements TweetCache {
	
	private Logger LOGGER = LoggerFactory
			.getLogger(TweetCacheImpl.class);

	@Inject
	private TweetRepository tweetRepository;
	
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)
	public void addTwitterMessage(Tweet tweet,
			ApiSource twitterApiSource) {
		TweetCached m = transformTweet2TwitterMessage(tweet);
		m.setTwitterApiSource(twitterApiSource);
		m=tweetRepository.saveAndFlush(m);
		LOGGER.debug("cached Tweet: "+m.toString());
	}
	
	@Override
	public TweetCached transformTweet2TwitterMessage(Tweet tweet) {
		TweetCached m = new TweetCached();
		m.setTwitterId(tweet.getId());
		m.setText(tweet.getText());
		m.setCreatedAt(tweet.getCreatedAt());
		m.setFromUser(tweet.getFromUser());
		m.setFromUserId(tweet.getFromUserId());
		m.setProfileImageUrl(tweet.getProfileImageUrl());
		m.setToUserId(tweet.getToUserId());
		m.setInReplyToStatusId(tweet.getInReplyToStatusId());
		m.setSource(tweet.getSource());
		m.setLanguageCode(tweet.getLanguageCode());
		m.setRetweetCount(tweet.getRetweetCount());
		return m;
	}
	
	@Override
	public boolean isTweetInCache(long twitterId,ApiSource twitterApiSource) {
		return tweetRepository.findByTwitterIdAndTwitterApiSource(twitterId, twitterApiSource)!=null;
	}

	@Override
	public Page<TweetCached> getTwitterMessages(ApiSource twitterApiSource,
			Pageable pageable) {
		return tweetRepository.findByTwitterApiSourceOrderByCreatedAtDesc(twitterApiSource, pageable);
	}

	@Override
	public Page<TweetCached> getTwitterMessagesForUserId(long userId,
			Pageable pageable) {
		return tweetRepository.findByFromUserId(userId, pageable);
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)
	public void resetTagCloud() {
		tweetRepository.deleteAll();
	}
}
