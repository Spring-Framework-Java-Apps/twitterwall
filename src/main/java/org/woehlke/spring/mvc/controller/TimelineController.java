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
import org.woehlke.spring.cache.service.TagCache;
import org.woehlke.spring.cache.service.TweetCache;
import org.woehlke.spring.cache.model.TweetCached;
import org.woehlke.spring.cache.model.TagCached;


/**
 * Web Controller for all Tweets coming in from automatic pollling in the backend.
 * @author tw
 */
@Controller
public class TimelineController {

	private Logger LOGGER = LoggerFactory.getLogger(TimelineController.class);
	
	@Autowired
	private TweetCache tweetCache;
	
	@Autowired
	private TagCache tagCache;

	@Value("${twitter.pagesize}") 
	private int pagesize;

	@Value("${twitter.searchterm}") 
	private String searchterm;

    private final Pageable pageable = new PageRequest(0,100,Direction.DESC,"createdAt");

    private final Pageable tagCloudSourcePageRequest = new PageRequest(0,100,Direction.DESC,"frequency");
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/")
	public String searchterm(Model model) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("HTTP-Request for /");
		}
		final Page<TweetCached> twitterMessages  = tweetCache.getTwitterMessages(pageable);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("Retrieved %s Twitter messages.", twitterMessages.getNumberOfElements()));
		}
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
		Page<TweetCached> twitterMessages = tweetCache.getTwitterMessages(pageable);
		
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("Retrieved last %s Twitter messages from cache.", twitterMessages.getNumberOfElements()));
		}
		Page<TagCached> tagCloudSource = tagCache.getTagCloudSource(tagCloudSourcePageRequest);
		model.addAttribute("tagCloudSource", tagCloudSource.getContent());
		model.addAttribute("twitterMessages", twitterMessages.getContent());
		model.addAttribute("twitterSearchterm", searchterm);
		long tagCloudFactor = tagCache.getTagCloudFactor(tagCloudSourcePageRequest);
		model.addAttribute("tagCloudFactor", tagCloudFactor);
		return "twitterMessages";
	}

}
