package org.woehlke.twitterwall.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.woehlke.twitterwall.entities.TagCached;


public interface TagCachedRepository extends JpaRepository<TagCached,Long> {
	TagCached findByText(String text);
}
