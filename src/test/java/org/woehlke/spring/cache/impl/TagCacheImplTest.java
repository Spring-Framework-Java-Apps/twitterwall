package org.woehlke.spring.cache.impl;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.woehlke.spring.cache.TagCache;
import org.woehlke.spring.service.impl.TwitterEventControllerServiceImplTest;

@ContextConfiguration(
		locations={"/spring-integration-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TagCacheImplTest {

	private Logger LOGGER = LoggerFactory.getLogger(TagCacheImplTest.class);
	
	private String[] testData = {
		"Stuttgarts OB #Kuhn weist Vorwurf der Blockade zur?ck \"Bahn wusste von Kostenexplosion\" http://t.co/KmaayjiI #S21 #Deutschlandfunk",
		"Die Bahn baut das zweite Gro?projekt namens #S21. Dieses Mal in Berlin. Das sind ja Strategen. Die Besten der Besten, Sir!"
	};
	
	@Inject
	private TagCache tagCache;
	
	@Test
	public void updateTagsForOneTweetsTextTest() throws Exception {
		for(String tweetText:testData){
			tagCache.updateTagsForOneTweetsText(tweetText);
		}
	}
}
