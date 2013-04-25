/*
 * Copyright 2002-2012 the original author or authors
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package org.woehlke.spring.eai;



import java.util.List;

import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Splitter;
import org.springframework.social.twitter.api.Tweet;
import org.woehlke.spring.eai.events.RequestTwitterwallEvent;
import org.woehlke.spring.eai.events.UpdateTagCloudEvent;

/**
 * Provides some basic methods for controlling the flow of Twitter messages.
 * 
 * @author Your Name Here
 * @version 1.0
 * 
 */
@MessageEndpoint
public interface TweetEventController {

	/**
	 * By default - After application startup, the Spring Integration Twitter
	 * search-inbound-channel-adapter is stopped. Use this method to start
	 * the adapter.
	 */
	void startTwitterAdapter();

	/**
	 * Allows for stopping the Spring Integration Twitter
	 * search-inbound-channel-adapter.
	 */
	void stopTwitterAdapter();

	Tweet addTwitterMessagesFromSearch(Tweet tweet);
	
	Tweet updateTaglist(Tweet tweet);

	@Filter
	boolean isTweetFromSearchNotYetCached(Tweet tweet);
	
	RequestTwitterwallEvent requestTwitterwall();
	
	@Splitter
	List<Tweet> fetchTwitterwallFromSearch(RequestTwitterwallEvent e);
	
	UpdateTagCloudEvent requestUpdateTagCloudEvent();
	
	void resetTagCloud(UpdateTagCloudEvent e);
}
