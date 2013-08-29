package org.woehlke.spring.service.impl;

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
import org.woehlke.spring.cache.service.TweetCache;
import org.woehlke.spring.cache.model.TweetCached;

@ContextConfiguration(
		locations={"/spring-integration-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TwitterEventControllerServiceImplTest {

	private Logger LOGGER = LoggerFactory.getLogger(TwitterEventControllerServiceImplTest.class);
	
	@Autowired
	private TweetCache tweetCache;

	@Value("${twitter.pagesize}") 
	private int pagesize;

	@Value("${twitter.searchterm}") 
	private String searchterm;
	
	@Test
	@Transactional
	public void testGetTwitterMessagesFromSearch() throws Exception{
		LOGGER.info("testGetTwitterMessagesFromSearch");
		Thread.sleep(4000);
		Pageable pageable = new PageRequest(0,pagesize);
		Page<TweetCached> tweets=tweetCache.getTwitterMessages(pageable);
		for(TweetCached tweet :tweets){
			LOGGER.info(tweet.toString());
		}
		Thread.sleep(1000);
	}
		
}
