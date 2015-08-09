<?xml version="1.0" encoding="UTF-8"?>
<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
			<div class="row">
                <div class="col-md-8">
                    <c:choose>
                        <c:when test="${not empty twitterMessages}">
                        <table class="table table-striped table-hover">
                            <c:forEach items="${twitterMessages}" var="twitterMessage">
                                <tr><td>
                                <img alt="${twitterMessage.fromUser}"
                                     title="${twitterMessage.fromUser}"
                                     src="${twitterMessage.profileImageUrl}"
                                     width="48" height="48">
                                <b><a href="https://www.twitter.com/${twitterMessage.fromUser}">@<c:out value="${twitterMessage.fromUser}"/></a></b><br />
                                ${twitterMessage.formattedText}<br/>
                                [ <c:out value="${twitterMessage.createdAt}"/> |
                                <c:out value="${twitterMessage.retweetCount}"/> RT ]
                            </td></tr>
                            </c:forEach>
                        </table>
                        </c:when>
                        <c:otherwise>No Twitter messages found.</c:otherwise>
                    </c:choose>
                </div>
                <div class="col-md-4">
                <div id="wordcloud1" class="wordcloud">
			   	<c:forEach items="${tagCloudSource}" var="tag">
			        <span data-weight="${tag.frequency}"><c:out value="${tag.text}"/></span>
			    </c:forEach>
                </div>
				</div>
		</div>

