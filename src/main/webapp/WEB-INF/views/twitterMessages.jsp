<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
			<div class="row">
                <span class="span8">
                    <c:choose>
                        <c:when test="${not empty twitterMessages}">
                        <table class="table">
                            <c:forEach items="${twitterMessages}" var="twitterMessage">
                                <tr><td>
                                <img alt="${twitterMessage.fromUser}"
                                     title="${twitterMessage.fromUser}"
                                     src="${twitterMessage.profileImageUrl}"
                                     width="48" height="48">
                                <b><a href="https://www.twitter.com/${twitterMessage.fromUser}">@<c:out value="${twitterMessage.fromUser}"/></a></b><br />
                                ${twitterMessage.formattedText}<br/>
                                [ <c:out value="${twitterMessage.createdAt}"/> | 
                                <c:out value="${twitterMessage.retweetCount}"/> RT ]
                            </td></tr>
                            </c:forEach>
                        </table>
                        </c:when>
                        <c:otherwise>No Twitter messages found.</c:otherwise>
                    </c:choose>
                </span>
                <span id="wordcloud1" class="wordcloud span4 offset8">
			   	<c:forEach items="${tagCloudSource}" var="tag">
			        <span data-weight="${tag.frequency}"><c:out value="${tag.text}"/></span>
			    </c:forEach>
				</span>
		</div>

