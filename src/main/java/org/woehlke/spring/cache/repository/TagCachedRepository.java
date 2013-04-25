package org.woehlke.spring.cache.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.woehlke.spring.cache.model.TagCached;


public interface TagCachedRepository extends JpaRepository<TagCached,Long> {
	TagCached findByText(String text);
}
