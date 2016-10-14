<%@page contentType="text/html; utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@include file="WEB-INF/content/head.jsp" %>
<title>MessageWall - Home</title>
<%@include file="WEB-INF/content/body.jsp" %>
<div class="top">
    <div class="search">

    </div>
    <div class="msg-list">
        <c:if test="${requestScope.home==null}">

        </c:if>
        <c:forEach var="item" items="${requestScope.home.list}">
            <div class="msg-item">
                <a href="/home/detail?msg=${item.msg_id}">${item.title}</a>
                <div>
                    <span><fmt:formatDate value="${item.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/> </span>
                    <span>${item.owner.nick_name} 留言</span>
                    <span>(${item.replyCount}回复)</span>
                </div>
            </div>
        </c:forEach>
        <%--<div class="msg-item">--%>
            <%--This is Title Here.--%>
            <%--<div>--%>
                <%--<span>2013-10-12 10:10:23</span>--%>
                <%--<span>海蓝Vica留言</span>--%>
                <%--<span>(31回复)</span>--%>
            <%--</div>--%>
        <%--</div>--%>
    </div>
</div>
<div class="pager">
    <c:if test="${requestScope.home.pages>0}">
        <c:forEach var="page" begin="1" end="${requestScope.home.pages}" step="1">
            <c:choose>
                <c:when test="${page==requestScope.home.indexPage}">
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
<%@include file="WEB-INF/content/tail.jsp" %>