jQuery.fn.scrollTo = function (speed) {
    var targetOffset = $(this).offset().top;
    $('html,body').stop().animate({scrollTop: targetOffset}, speed);
    return this;
};
$(document).ready(function () {
    $("#btnOpenWx").hover(function () {
        $(this).addClass("item2Hov");
        $(this).children("ul").show();
    }, function () {
        $(this).removeClass("item2Hov");
        $(this).children("ul").hide();
    });
    $(".searchFormInputTxt>ul>li").each(function (i) {
        $(this).addClass("item" + (i + 1));
    });
    $(".searchFormInputTxt>ul").hover(function () {
        $(this).addClass("hover");
        $(this).children(":first").addClass("item1Hov");
        $(this).children().not(":first").show();
    }, function () {
        $(this).removeClass("hover");
        $(this).children(":first").removeClass("item1Hov");
        $(this).children().not(":first").hide();
    });
    $(".searchFormInputTxt>ul>li:not(:first)").click(function () {
        var txt = $(this).text(),
            dTxt = $(this).siblings().first().text();
        $(this).siblings().first().text(txt).removeClass("item1Hov");
        $(this).text(dTxt).hide();
        $(this).siblings().not(":first").hide();
    });
    var searchInput = $("input.inputSearch");
    $(searchInput).focus(function () {
        if ($(this).val() == this.defaultValue) {
            $(this).val("").css("color", "#656565")
        }
    }).blur(function () {
        if ($(this).val() == '') {
            $(this).val(this.defaultValue).css("color", "#C5C4C4")
        }
    });
    $("#siderCat").children().each(function (i) {
        $(this).addClass("siderCatItemBg" + (i + 1));
        var arrow = $("<i>展开全部</i>");
        $(this).has(".siderCatItemMain").append(arrow).hover(function () {
            $(this).addClass("siderCatItemHov").children("div").show();
        }, function () {
            $(this).removeClass("siderCatItemHov").children("div").hide();
        });
        $(this).children("div").children(".siderCatItemMainRight").children(".closeSiderCatItemMain").click(function () {
            $(this).parents(".siderCatItemMain").hide();
        });
    });
    $(".siderCatItemMain:first").addClass("siderCatItemMainNoBorder");
    $(".menuMain>ul>li").hover(function () {
        $(this).addClass("hover");
    }, function () {
        $(this).removeClass("hover");
    });
    $(".menuMain>ul>li:eq(6)").append("<i></i>");
    $(".siteIntro").find("li").each(function (i) {
        $(this).addClass("item" + (i + 1))
    });
    $(".helpCenter").find("dl:first").addClass("first");
    $(".helpCenter").find("dl:last").addClass("last");
    $.fn.smartFloat = function () {
        var position = function (element) {
            var top = element.position().top, pos = element.css("position");
            $(window).scroll(function () {
                var scrolls = $(this).scrollTop();
                if (scrolls > 210) { //如果滚动到页面超出了当前元素element的相对页面顶部的高度
                    if (window.XMLHttpRequest) { //如果不是ie6
                        element.css({
                            position: "fixed",
                            top: 30
                        });
                    } else { //如果是ie6
                        element.css({
                            top: scrolls
                        });
                    }
                } else {
                    element.css({
                        position: pos,
                        top: top
                    });
                }
            });
        };
        return $(this).each(function () {
            position($(this));
        });
    };
    $("#floatServiceWrap").smartFloat();
    $(".goTop").click(function () {
        $("body").scrollTo(500);
        return false;
    }).hover(function () {
        $(this).addClass("goTopHov")
    }, function () {
        $(this).removeClass("goTopHov")
    });

});
function b() {
    h = $(window).height();
    t = $(document).scrollTop();
    if (t > 250) {
        $('.goTop').fadeIn("slow");
    } else {
        $('.goTop').fadeOut("slow");
    }
}
$(window).scroll(function (e) {
    b();
});
$(window).load(function () {
    var winW = $(window).width(),
        resW = (winW - 1002) / 2 + 1012;
    $("#floatServiceWrap").css("left", resW);
    $(window).resize(function () {
        var winW = $(window).width(),
            resW = (winW - 1002) / 2 + 1012;
        $("#floatServiceWrap").css("left", resW);
    });
});