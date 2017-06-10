package org.woehlke.twitterwall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.woehlke.twitterwall.entities.TweetCached;

public interface TweetRepository extends JpaRepository<TweetCached,Long> {

	TweetCached findByTwitterId(long twitterId);
}
