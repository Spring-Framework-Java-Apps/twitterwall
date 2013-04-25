package org.woehlke.spring.cache.model;

import javax.persistence.Enumerated;


/**
 * From which Channel Adapter came the Tweet in and for what View shall it be stored.
 * @author tw
 * @see spring-integration-context.xml
 */
public enum ApiSource {
	
	/**
	 * int-twitter:search-inbound-channel-adapter
	 */
	@Enumerated
	DEFAULT_SEARCH,
	
	/**
	 * int-twitter:mentions-inbound-channel-adapter
	 */
	@Enumerated
	MENTIONS,
	
	/**
	 * int-twitter:inbound-channel-adapter
	 */
	@Enumerated
	TIMELINE,
	
	/**
	 * Spring Social Twitter Template
	 */
	@Enumerated
	FAVORITES,
	
	/**
	 * Spring Social Twitter Template
	 */
	@Enumerated
	RETWEETED_TO_ME,
	
	/**
	 * Spring Social Twitter Template
	 */
	@Enumerated
	RETWEETED_BY_ME,
	
	@Enumerated
	TIMELINE_USER,
}
