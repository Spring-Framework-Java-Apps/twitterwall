package org.woehlke.spring.service.impl;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.spring.cache.TweetCache;
import org.woehlke.spring.cache.model.ApiSource;
import org.woehlke.spring.cache.model.TweetCached;
import org.woehlke.spring.eai.TweetEventController;

@ContextConfiguration(
		locations={"/spring-integration-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TwitterEventControllerServiceImplTest {

	private Logger LOGGER = LoggerFactory.getLogger(TwitterEventControllerServiceImplTest.class);
	
	@Autowired
	private TweetEventController tweetEventController;
	
	@Autowired
	private TweetCache tweetCache;
	
	@Autowired
	@Value("${twitter.pagesize}") 
	private int pagesize;
	
	@Autowired
	@Value("${twitter.searchterm}") 
	private String searchterm;
	
	@Test
	@Transactional
	public void testGetTwitterMessagesFromSearch() throws Exception{
		LOGGER.info("testGetTwitterMessagesFromSearch");
		tweetEventController.startTwitterAdapter();
		Thread.sleep(4000);
		Pageable pageable = new PageRequest(0,pagesize);
		Page<TweetCached> tweets=tweetCache.getTwitterMessages(ApiSource.DEFAULT_SEARCH,
				pageable);
		for(TweetCached tweet :tweets){
			Assert.assertEquals(ApiSource.DEFAULT_SEARCH.ordinal(), tweet.getTwitterApiSource().ordinal());
			LOGGER.info(tweet.toString());
		}
		Thread.sleep(1000);
		tweetEventController.stopTwitterAdapter();
	}
		
}
