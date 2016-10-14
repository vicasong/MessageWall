/**
 * 作者：vica
 * 时间：2016-07-20 18:23:52
 */

var replyHere = function (link, tid) {
    vaildHere();
    var area = $(link).parent().parent().parent().children(".area").get(0);
    if (area.innerHTML.trim().length > 0) {
        return;
    }
    var html = $("#replyLayer").get(0).innerHTML;
    var title = $(link).parent().parent().parent().children("dt").children("span").get(0);
    var toName = title.innerHTML.trim().split(" ")[0];
    area.innerHTML = html;
    var btn = $(area).children().children("input").get(0);
    $(area).children().children("textarea").get(0).setAttribute("placeholder", "回复" + toName + "：");
    $(btn).click(function () {
        sendReply(btn, tid);
    });
};

var vaildHere = function () {
    var divs = $(".area");
    var i = 0;
    for (; i < divs.length; i++) {
        if (divs.get(i).innerHTML.trim().length > 0) {
            divs.get(i).innerHTML = "";
        }
    }
};

var sendReply = function (btn, tid) {
    var textbox = $(btn).parent().children("textarea").get(0);
    var msg = textbox.value;
    textbox.value = "";
    if (msg.length < 1) {
        alert("Filled out please.");
        return;
    }
    vaildHere();
    requestReply(msg, tid);
};

var requestReply = function (msg, target) {
    $.ajax({
        url: "/home/reply",
        async: false,
        data: {msg: msg, target: target},
        type: "POST",
        dataType: "json",
        success: function (pdata) {
            if (pdata) {
                if (pdata.success == "ok") {
                    location.replace(location.href);
                } else {
                    alert(pdata.data);
                }
            } else {
                alert("Error.");
            }
        },
        error: function () {
            alert("网络故障");
        }
    });
}

var putMsg = function () {
    var title = $("#theme").val();
    var msg = $("#content").val();
    if (title.length < 1 || msg.length < 1) {
        alert("Filled out please.");
        return;
    }
    $.ajax({
        url: "/home/build",
        async: true,
        data: {title: title, msg: msg},
        type: "POST",
        dataType: "json",
        success: function (pdata) {
            if (pdata) {
                if (pdata.success == "ok") {
                    location.replace(location.href);
                } else {
                    alert(pdata.data);
                }
            } else {
                alert("Error.");
            }
        },
        error: function () {
            alert("网络故障");
        }
    });
    $("#newMsg").modal('hide');
};

var putReply = function () {
    var msg = $("#reply-msg").val();
    if (msg.length < 1) {
        alert("Filled out please.");
        return;
    }
    requestReply(msg, 0);
    $("#replyModal").modal('hide');
};
