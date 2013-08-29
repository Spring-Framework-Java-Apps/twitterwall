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
package org.woehlke.spring.cache.model;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Represents some common Twitter related fields.
 *
 * @author Thomas Woehlke
 * @version 1.0
 *
 */
@XmlRootElement
@Entity
@Table(name="TWEET")
public class TweetCached {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	@XmlElement
	private long twitterId;
	
	@Column
	@XmlElement
	private String text;
	
	@Column
	@XmlElement
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column
	@XmlElement
	private String fromUser;
	
	@Column
	@XmlElement
	private String profileImageUrl;
	
	@Column
	@XmlElement
	private Long toUserId;
	
	@Column
	@XmlElement
	private Long inReplyToStatusId;
	
	@Column
	@XmlElement
	private long fromUserId;
	
	@Column
	@XmlElement
	private String languageCode;
	
	@Column
	@XmlElement
	private String source;
	
	@Column
	@XmlElement
	private Integer retweetCount;
	
	@Transient
	public String getFormattedText(){
		String returnValue = new String(this.text);
		Pattern regexUrl = Pattern.compile("http\\S+");
		Pattern regexUser = Pattern.compile("@\\w+");
		Matcher matcherUrl = regexUrl.matcher(this.text);
		Matcher matcherUser = regexUser.matcher(this.text);
		while(matcherUrl.find()){
			String url = matcherUrl.group();
			returnValue=returnValue.replaceAll(url, "<a href=\""+url+"\">"+url+"</a>");
		}
		while(matcherUser.find()){
			String user = matcherUser.group();
			returnValue=returnValue.replaceAll(user, "<a href=\"https://www.twitter.com/"+user+"\">"+user+"</a>");
		}
		return returnValue;
	}
	
	/** Constructor to initialize all fields available. */
	public TweetCached(long twitterId, String text, Date createdAt, String fromUser, String profileImageUrl, long fromUserId, Long toUserId, Long inReplyToStatusId, String languageCode, String source) {
		this.twitterId = twitterId;
		this.text = text;
		this.createdAt = createdAt;
		this.fromUser = fromUser;
		this.profileImageUrl = profileImageUrl;
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.inReplyToStatusId=inReplyToStatusId;
		this.languageCode = languageCode;
		this.source = source;		
	}

	/** Default constructor. */
	public TweetCached() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(long twitterId) {
		this.twitterId = twitterId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public Long getToUserId() {
		return toUserId;
	}

	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}

	public Long getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	public void setInReplyToStatusId(Long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	public long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public Integer getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(Integer retweetCount) {
		this.retweetCount = retweetCount;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TweetCached)) return false;

        TweetCached that = (TweetCached) o;

        if (twitterId != that.twitterId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (twitterId ^ (twitterId >>> 32));
    }

    @Override
    public String toString() {
        return "TweetCached{" +
                "id=" + id +
                ", twitterId=" + twitterId +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", fromUser='" + fromUser + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", toUserId=" + toUserId +
                ", inReplyToStatusId=" + inReplyToStatusId +
                ", fromUserId=" + fromUserId +
                ", languageCode='" + languageCode + '\'' +
                ", source='" + source + '\'' +
                ", retweetCount=" + retweetCount +
                '}';
    }
}
