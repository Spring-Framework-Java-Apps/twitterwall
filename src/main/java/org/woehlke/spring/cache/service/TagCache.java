package org.woehlke.spring.cache.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.spring.cache.model.TagCached;

public interface TagCache {
	
	void updateTagsForOneTweetsText(String text);

	Page<TagCached> getTagCloudSource(Pageable tagCloudSourcePageRequest);

	void resetTagCloud();

	long getTagCloudFactor(Pageable tagCloudSourcePageRequest);
}
