<%@page contentType="text/html; utf-8" pageEncoding="utf-8" isELIgnored="false" %>
</head>
<body>
<div class="profile">
    <span>${sessionScope.user.nick_name}</span><a href="${pageContext.request.contextPath}/account/exit">退出</a>
    <input class="input-sm" type="text" placeholder="Search Here" id="key-box">
    <input type="button" class="btn btn-sm" value="Search" onclick="location.href='/home/fingerout?key='+$('#key-box').val()">
    <input type="button" data-toggle="modal" data-target="#newMsg" class="btn btn-sm" value="New..." alt="新建留言主题">
</div>
<div class="main">
    <div class="mynav">
        <div>
            <a href="${pageContext.request.contextPath}/home">首页</a>
            <a href="${pageContext.request.contextPath}/home/my">我的</a>
        </div>
    </div>