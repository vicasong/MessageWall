<%@page contentType="text/html; utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta name="render" content="webkit">
    <meta name="Description" content="MsgWall">
    <title>MessageWall - Login</title>
    <link href="${pageContext.request.contextPath}/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet"
          type="text/css">
    <link href="${pageContext.request.contextPath}/style/index.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/libs/tlt/animate.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/libs/tlt/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-container">
    <div class="main-bg">
        <div class="textActor-container">
            <h2 class="textActor-head"> I look up at the starry sky</h2>
            <br>
            <p class="textActor-body">
                <span>I look up at the starry sky</span><br>
                <span>Which is so deep and vast.</span><br>
                <span>The never-ending truth</span><br>
                <span>Makes me struggle to follow and quest it.</span><br>
                <span></span><br>
                <span>I look up at the starry sky</span><br>
                <span>Which is so solemn, holy and pure.</span><br>
                <span>The severe and awe-inspiring justice</span><br>
                <span>Makes me filled with deep love and in awe of it.</span><br>
                <span></span><br>
                <span>I look up at the starry sky</span><br>
                <span>Which is so free and serene.</span><br>
                <span>The broad bosom</span><br>
                <span>Provides the place where where my soul rests and nestles to.</span><br>
                <span></span><br>
                <span>I look up at the starry sky</span><br>
                <span>Which is so marvelous and glorious.</span><br>
                <span>The eternal blaze</span><br>
                <span>Kindles the flame of hope in my heart, which resounds with spring thunder.</span>
            </p>
        </div>
    </div>
    <div class="main-content">
        <div class="login-panel">
            <div class="fields">
                <div>
                    <h3>欢迎使用留言板</h3>
                </div>
                <div class="line"></div>
                <div class="tab-content login-content">
                    <div class="tab-pane fade in active" id="login">
                        <h4>登陆</h4>
                        <div class="form-group">
                            <form action="/account" method="post" onsubmit="return validateFormEmpty(this);">
                                <div  <c:choose>
                                      <c:when test="${requestScope.error!=null}">class="form-group has-feedback has-error"</c:when>
                                      <c:otherwise>class="form-group has-feedback"</c:otherwise>
                                      </c:choose> >
                                    <span class="glyphicon glyphicon-user form-control-feedback"
                                          style="left: 0;"></span>
                                    <input class="form-control" name="userName" type="text"
                                           style="padding-left: 30px; padding-right: 12px;"
                                           placeholder="Enter Your Name">
                                </div>
                                <div class="form-group has-feedback">
                                    <span class="glyphicon glyphicon-lock form-control-feedback"
                                          style="left: 0;"></span>
                                    <input class="form-control" name="password" type="password"
                                           style="padding-left: 30px; padding-right: 12px;"
                                           placeholder="Put The Password">
                                </div>
                                <div class="form-group btn-group">
                                    <input class="btn btn-primary" type="submit" value="Sign In">
                                    <a class="btn btn-success" href="#register" data-toggle="tab">Sign Up</a>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="register">
                        <h4>注册</h4>
                        <div class="form-group">
                            <form>
                                <div class="form-group has-feedback">
                                    <span class="glyphicon glyphicon-user form-control-feedback"
                                          style="left: 0;"></span>
                                    <input class="form-control" name="userName" type="text" id="rg_txtName"
                                           style="padding-left: 30px; padding-right: 12px;"
                                           placeholder="Put Your AccountName">
                                </div>
                                <div class="form-group has-feedback">
                                    <span class="glyphicon glyphicon-globe form-control-feedback"
                                          style="left: 0;"></span>
                                    <input class="form-control" name="nickName" type="text" id="rg_txtNick"
                                           style="padding-left: 30px; padding-right: 12px;"
                                           placeholder="Enter Nick Name">
                                </div>
                                <div class="form-group has-feedback">
                                    <span class="glyphicon glyphicon-lock form-control-feedback"
                                          style="left: 0;"></span>
                                    <input class="form-control" name="password" type="password" id="rg_txtPass"
                                           style="padding-left: 30px; padding-right: 12px;"
                                           placeholder="Set Password">
                                </div>
                                <div class="form-group btn-group">
                                    <input class="btn btn-primary" type="button" onclick="putInfo(this)"
                                           value="Sign Up">
                                    <a class="btn btn-info" href="#login" data-toggle="tab">Back</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/jquery-3.0.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/tlt/jquery.fittext.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/tlt/jquery.lettering.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/tlt/highlight.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/tlt/jquery.textillate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/index.js"></script>
<script type="text/javascript">
    <c:if test="${requestScope.error!=null}">
        alert("${requestScope.error}");
    </c:if>
    $(function () {
        textActor.start(0, ".textActor-head");
        textActor.start(4600, ".textActor-body span", {
            in: {
                effect: 'fadeIn',
                delayScale: 1.5,
                delay: 320,
                sync: false,
                shuffle: false,
                reverse: false
            }
        });
    });
</script>
</html>