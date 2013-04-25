package org.woehlke.spring.mvc.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.spring.cache.TagCache;
import org.woehlke.spring.cache.TweetCache;
import org.woehlke.spring.cache.model.ApiSource;
import org.woehlke.spring.cache.model.TweetCached;
import org.woehlke.spring.cache.model.TagCached;
import org.woehlke.spring.eai.TweetEventController;


/**
 * Web Controller for all Tweets coming in from automatic pollling in the backend.
 * @author tw
 */
@Controller
public class TimelineController {

	private Logger LOGGER = LoggerFactory.getLogger(TimelineController.class);

	@Autowired
	private TweetEventController tweetEventController;
	
	@Autowired
	private TweetCache tweetCache;
	
	@Autowired
	private TagCache tagCache;

	@Autowired
	@Value("${twitter.pagesize}") 
	private int pagesize;
	
	@Autowired
	@Value("${twitter.searchterm}") 
	private String searchterm;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/")
	public String searchterm(Model model) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("HTTP-Request for /");
		}
		Pageable pageable = new PageRequest(0,10);
		final Page<TweetCached> twitterMessages  = tweetCache.getTwitterMessages(ApiSource.DEFAULT_SEARCH,
				pageable);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("Retrieved %s Twitter messages.", twitterMessages.getNumberOfElements()));
		}
		Pageable tagCloudSourcePageRequest = new PageRequest(0,100,Direction.DESC,"frequency");
		Page<TagCached> tagCloudSource = tagCache.getTagCloudSource(tagCloudSourcePageRequest);
		model.addAttribute("tagCloudSource", tagCloudSource.getContent());
		model.addAttribute("twitterMessages", twitterMessages.getContent());
		model.addAttribute("twitterSearchterm", searchterm);
		long tagCloudFactor = tagCache.getTagCloudFactor(tagCloudSourcePageRequest);
		model.addAttribute("tagCloudFactor", tagCloudFactor);
		return "home";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/ajax")
	public String searchtermAjax(Model model) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("HTTP-Request for /ajax");
		}
		Pageable pageable = new PageRequest(0,pagesize);
		Page<TweetCached> twitterMessages = tweetCache.getTwitterMessages(ApiSource.DEFAULT_SEARCH,
				pageable);
		
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("Retrieved last %s Twitter messages from cache.", twitterMessages.getNumberOfElements()));
		}
		Pageable tagCloudSourcePageRequest = new PageRequest(0,pagesize,Direction.DESC,"frequency");
		Page<TagCached> tagCloudSource = tagCache.getTagCloudSource(tagCloudSourcePageRequest);
		model.addAttribute("tagCloudSource", tagCloudSource.getContent());
		model.addAttribute("twitterMessages", twitterMessages.getContent());
		model.addAttribute("twitterSearchterm", searchterm);
		long tagCloudFactor = tagCache.getTagCloudFactor(tagCloudSourcePageRequest);
		model.addAttribute("tagCloudFactor", tagCloudFactor);
		return "twitterMessages";
	}

}
