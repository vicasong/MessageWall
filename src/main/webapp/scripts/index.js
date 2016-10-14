/**
 * Created by Vica-tony on 2016/8/22.
 */
var textActor = {
    effects: {
        // enable looping
        loop: false,
        // sets the minimum display time for each text before it is replaced
        //minDisplayTime: 2000,
        // sets the initial delay before starting the animation
        // (note that depending on the in effect you may need to manually apply
        // visibility: hidden to the element before running this plugin)
        initialDelay: 30,
        // set whether or not to automatically start animating
        autoStart: true,
        in: {// set the effect name
            effect: 'fadeInDown',
            // set the delay factor applied to each consecutive character
            delayScale: 1.5,
            // set the delay between each character
            delay: 300,
            // set to true to animate all the characters at the same time
            sync: false,
            // randomize the character sequence
            // (note that shuffle doesn't make sense with sync = true)
            shuffle: false,
            // reverse the character sequence
            // (note that reverse doesn't make sense with sync = true)
            reverse: false
        },
        type: "char" // 'char' or 'words'
    },
    start: function (delay, selector, effects) {
        var items = $(selector);
        var start = delay;
        var settings = $.extend({}, textActor.effects, effects);

        items.each(function (index, elm) {
            var opt = $.extend({}, settings, {initialDelay: start});
            var text = $(this).text();
            text = text.replace(/[\~|\`|\!|\@|\#|\$|\%|\^|\&|\*|\(|\)|\-|\_|\+|\=|\||\\|\[|\]|\{|\}|\;|\:|\"|\'|\,|\<|\.|\>|\/|\?]/g, "");
            var len = text.length;
            start += settings.in.delay * len / 1.5 + (len > 0 ? 100 : 0);
            $(this).textillate(opt);
        })
    }
};
var putInfo=function (btn) {
    var valid = validateEmpty(btn);
    if(!valid){
        return;
    }
    var userName = $("#rg_txtName").val();
    var nickName = $("#rg_txtNick").val();
    var password = $("#rg_txtPass").val();
    $.ajax({
        url:"/account/register",
        async:true,
        data:{userName:userName,nickName:nickName,password:password},
        type:"POST",
        dataType:"json",
        success:function (result) {
            if(result) {
                if (result.success == "ok") {
                    alert("User " + userName + " Register Succeed.")
                } else {
                    alert("User " + userName + " Already Exists.");
                }
            }else{
                alert("Error.")
            }
        },
        error:function () {
            alert("网络故障");
        }
    });
};

var validateEmpty= function (btn) {
    var root = $(btn).parent().parent();
    return validateFormEmpty(root);
};



var validateFormEmpty = function (form) {
    var valid = true;
    $(form).children("div").children("input").each(function () {
        if($(this).attr("type")!="submit" && $(this).attr("type")!="button") {
            if ($(this).val().trim().length < 1) {
                alert("Please, All Text Fields Must Be Filled Out.");
                valid = false;
                return false;
            }
        }
    });
    return valid;
};