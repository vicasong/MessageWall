<%--
  Created by IntelliJ IDEA.
  User: Vica-tony
  Date: 9/11/2016
  Time: 6:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>PUT测试</title>
</head>
<body>
<input type="text" id="txt">
<input type="button" value="go" id="btn">

</body>
<script type="text/javascript" src="/libs/jquery/jquery-3.0.0.min.js"></script>
<script>
    $($("#btn").click(function () {
        var val=$("#txt").val();
        $.ajax({
            url:"/Home",
            type:"PUT",
            data:{val:val},
            success:function () {
                alert("succeed.");
            },
            error:function () {
                alert("error")
            }
        });
    }));
</script>
</html>
