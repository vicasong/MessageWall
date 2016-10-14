<%@page contentType="text/html; utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@include file="WEB-INF/content/head.jsp" %>
<title>MessageWall - Message</title>
<%@include file="WEB-INF/content/body.jsp" %>
<div class="top">
    <div class="putmsg">
        <h2>${sessionScope.detail.message.title}</h2>
        <p>
            ${sessionScope.detail.message.content}
        </p>
        <div style="text-align: right; margin-right: 10px; margin-bottom: 15px; font-size: 0.9em; ">
                <span style="margin-right: 15px; color:rgb(161, 161, 161)">
                    ${sessionScope.detail.message.owner.nick_name} 发表与<fmt:formatDate
                        value="${sessionScope.detail.message.create_time}" pattern="yyyy年MM月dd日 HH:mm:ss"/>
                </span>
            <a href="#replyModal" data-toggle="modal">回复</a>
        </div>

    </div>
</div>
<div class="content">
    <c:forEach var="floor" items="${sessionScope.detail.list}">
        <dl class="floor">
            <dt>
					<span>${floor.owner.nick_name} <fmt:formatDate value="${floor.create_time}"
                                                                   pattern="yyyy年MM月dd日 HH:mm:ss"/>
						回复：${sessionScope.detail.message.owner.nick_name}
					</span>
            <div>
                <a onclick="replyHere(this,${floor.r_id})">回复</a>
                <c:if test="${floor.owner.user_id==sessionScope.user.user_id}">
                    <a href="/home/del?id=${floor.r_id}">删除</a>
                </c:if>
            </div>
            </dt>
            <dd>${floor.content}</dd>
            <div class="area"></div>
            <c:forEach var="refloor" items="${floor.replies}">
                <dl class="refloor">
                    <dt>
					<span>${refloor.owner.nick_name} <fmt:formatDate value="${refloor.create_time}"
                                                                     pattern="yyyy年MM月dd日 HH:mm:ss"/>
						回复：${floor.owner.nick_name}
					</span>
                    <div>
                        <a onclick="replyHere(this,${floor.r_id})">回复</a>
                        <c:if test="${refloor.owner.user_id==sessionScope.user.user_id}">
                            <a href="/home/del?id=${refloor.r_id}">删除</a>
                        </c:if>
                    </div>
                    </dt>
                    <dd>${refloor.content}</dd>
                    <div class="area"></div>
                </dl>
            </c:forEach>
        </dl>
    </c:forEach>
    <%--<dl class="floor">--%>
    <%--<dt>--%>
    <%--<span>vica 2016年08月21日 16:16:55--%>
    <%--回复：vica--%>
    <%--</span>--%>
    <%--<div>--%>
    <%--<a onclick="replyHere(this,1)">回复</a>--%>
    <%--<a>删除</a>--%>
    <%--</div>--%>
    <%--</dt>--%>
    <%--<dd>Hello Hi</dd>--%>
    <%--<div class="area"></div>--%>
    <%--<dl class="refloor">--%>
    <%--<dt>--%>
    <%--<span>vica 2016年08月21日 16:16:48--%>
    <%--回复：vica--%>
    <%--</span>--%>
    <%--<div>--%>
    <%--<a onclick="replyHere(this,1)">回复</a>--%>
    <%--<a>删除</a>--%>
    <%--</div>--%>
    <%--</dt>--%>
    <%--<dd>Hi Im Yom</dd>--%>
    <%--<div class="area"></div>--%>
    <%--</dl>--%>
    <%--<dl class="refloor">--%>
    <%--<dt>--%>
    <%--<span>vica 2016年08月21日 16:12:07--%>
    <%--回复：vica--%>
    <%--</span>--%>
    <%--<div>--%>
    <%--<a onclick="replyHere(this,1)">回复</a>--%>
    <%--<a>删除</a>--%>
    <%--</div>--%>
    <%--</dt>--%>
    <%--<dd>sdada</dd>--%>
    <%--<div class="area"></div>--%>
    <%--</dl>--%>
    <%--<dl class="refloor">--%>
    <%--<dt>--%>
    <%--<span>vica 2016年08月10日 21:38:36--%>
    <%--</span>--%>
    <%--<div>--%>
    <%--<a onclick="replyHere(this,1)">回复</a>--%>
    <%--<a>删除</a>--%>
    <%--</div>--%>
    <%--</dt>--%>
    <%--<dd>Hi Vica</dd>--%>
    <%--<div class="area"></div>--%>
    <%--</dl>--%>
    <%--</dl>--%>
    <%--<dl class="floor">--%>
    <%--<dt>--%>
    <%--<span>vica 2016年08月10日 21:38:33--%>
    <%--</span>--%>
    <%--<div>--%>
    <%--<a onclick="replyHere(this,1)">回复</a>--%>

    <%--<a>删除</a>--%>
    <%--</div>--%>
    <%--</dt>--%>
    <%--<dd>Hi hello</dd>--%>
    <%--<div class="area">--%>
    <%--<div class="reply">--%>
    <%--<textarea id="replytxt" placeholder=""></textarea>--%>
    <%--<input id="send" type="button" value="确定"/>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</dl>--%>
</div>
<div class="pager">
    <c:if test="${sessionScope.detail.pages>0}">
        <c:forEach var="page" begin="1" end="${sessionScope.detail.pages}" step="1">
            <c:choose>
                <c:when test="${page==sessionScope.detail.indexPage}">
                    <font class="selectpage">${page}</font>
                </c:when>
                <c:otherwise>
                    <c:set var="url" value="/home"/>
                    <c:if test="${pageContext.request.pathInfo!=null}">
                        <c:set var="url" value="${url.concat(pageContext.request.pathInfo)}"/>
                    </c:if>
                    <c:choose>
                        <c:when test="${pageContext.request.queryString!=null}">
                            <c:set var="tmpPage" value='${pageContext.request.queryString.substring(pageContext.request.queryString.lastIndexOf("page="))}'/>
                            <c:set var="tmpPage" value="${tmpPage.substring(0,tmpPage.indexOf('&')<1?tmpPage.length():tmpPage.indexOf('&'))}"/>
                            <c:set var="url" value='${url.concat("?").concat(pageContext.request.queryString.replace(tmpPage,""))}'/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="url" value='${url.concat("?")}'/>
                        </c:otherwise>
                    </c:choose>
                    <a href="${url.concat("page=").concat(page)}">${page}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:if>

    <%--<font class="selectpage">1</font>--%>
    <%--<a>2</a><a>3</a><a>3</a><a>3</a><a>3</a>--%>
</div>
<%--<div class="pager">--%>
<%--<font class="selectpage">1</font>--%>
<%--<a>2</a><a>3</a><a>3</a><a>3</a><a>3</a>--%>
<%--</div>--%>
<%@include file="WEB-INF/content/tail.jsp" %>