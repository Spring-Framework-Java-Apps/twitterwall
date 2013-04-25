package org.woehlke.spring.cache.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.woehlke.spring.cache.TagCache;
import org.woehlke.spring.cache.model.TagCached;
import org.woehlke.spring.cache.repository.TagCachedRepository;

@Service
public class TagCacheImpl implements TagCache {

	private Logger LOGGER = LoggerFactory.getLogger(TagCacheImpl.class);
	
	@Inject
	private TagCachedRepository tagCachedRepository;
	
	private final String[] filter = {
			"RT","es","mit","den","dem","dann","bei","für","ein","an","hat",
			"Der","der","Die","die","das","Das","von","in","dass",
			"ist","sind","bist","bin","werdet","gewesen","und","für",
			"im","auf"
	};
	
	@Override
	public void updateTagsForOneTweetsText(String text) {
		List<String> tags = new ArrayList<String>();
		LOGGER.info("# UPDATE TAGS FOR: "+text);
		//find URLs
		Pattern regexUrl = Pattern.compile("http\\S+");
		Matcher matcherUrl = regexUrl.matcher(text);
		while(matcherUrl.find()){
			tags.add(matcherUrl.group());
		}
		//Strip URLS from text
		for(String tag:tags){
			text=text.replaceAll(tag, "");
		}
		//filter words from text
		for(String f:filter){
			text=text.replaceAll("\\b"+f+"\\b", "");
		}
		//Split into Words
		String regex = "[^a-zäöüÄÖÜß?A-Z_0-9@#]";
		for(String a:text.split(regex)){
			if(!a.isEmpty()){
				tags.add(a);
			}
		}
		for(String tag:tags){
			//LOGGER.info("# ONE TAG: --"+tag+"--");
			TagCached tagCached=tagCachedRepository.findByText(tag);
			if(tagCached==null){
				tagCached=new TagCached(tag);
			} else {
				tagCached.incrementFrequency();
			}
			tagCachedRepository.saveAndFlush(tagCached);
		}
	}

	@Override
	public Page<TagCached> getTagCloudSource(Pageable tagCloudSourcePageRequest) {
		return tagCachedRepository.findAll(tagCloudSourcePageRequest);
	}

	@Override
	public void resetTagCloud() {
		tagCachedRepository.deleteAll();
	}

	@Override
	public long getTagCloudFactor(Pageable tagCloudSourcePageRequest) {
		Page<TagCached> page = tagCachedRepository.findAll(tagCloudSourcePageRequest);
		int number = page.getNumberOfElements();
		if(number<2){
			return 1;
		} else {
			return page.getContent().get(number-1).getFrequency();
		}
	}
	
}
