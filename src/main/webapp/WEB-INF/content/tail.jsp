<%@page contentType="text/html; utf-8" pageEncoding="utf-8" isELIgnored="false" %>
</div>
<div id="replyLayer" style="display:none;">
    <div class="reply">
        <textarea id="replytxt" placeholder=""></textarea>
        <input id="send" type="button" value="确定"/>
    </div>
</div>
<div id="newMsg" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    新留言
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="theme" id="theme"
                               placeholder="输入留言标题/主题" class="form-control"/>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <textarea class="form-control" style="resize: none;" name="content"
                                  placeholder="输入留言内容"
                                  id="content"></textarea>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <span id="userPhoneMessage" class="glyphicon"></span>
                <button type="button" id="userPhoneBtn" class="btn btn-success" onclick="putMsg()">
                    <span class="glyphicon glyphicon-ok"></span>
                    Submit
                </button>
            </div>
        </div>
    </div>
</div>
<div id="replyModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    回复留言
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="reply" id="reply-msg"
                               placeholder="输入回复内容" class="form-control"/>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="replyBtn" class="btn btn-success" onclick="putReply()">
                    <span class="glyphicon glyphicon-ok"></span>
                    Submit
                </button>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/jquery-3.0.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/home.js"></script>
</html>
