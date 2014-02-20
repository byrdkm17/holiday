;
(function ($) {
    $.fn.formStop = function () {
        $(this).bind("submit", function () {
            return false;
        });
    };
    $.fn.extend({
        "shortHints": function (options) {
            options = $.extend({
                displayText: "this is your message",
                showSpeed: "fast",
                showTime: 1500,
                disappearSpeed: "slow",
                messageType: "msgWarn"
            }, options)
            $(this).children().addClass(options.messageType).text(options.displayText);
            $(this).slideDown(options.showSpeed).delay(options.showTime).fadeOut(options.disappearSpeed);
            return this;
        },
        "tableFacade": function (options) {
            options = $.extend({
                odd: "odd",
                even: "even",
                tdOdd: "odd",
                tdEven: "even"
            }, options)
            $("tbody>tr:odd", this).addClass(options.odd);
            $("tbody>tr:even", this).addClass(options.even);
            $("tbody>tr>td:odd,thead>tr>th:odd", this).addClass(options.tdOdd);
            $("tbody>tr>td:even", this).addClass(options.tdEven);
            return this;
        }
    });
    $.fn.resetSize = function (new_height, new_width) {
        var imgWidth = $(this).width(),
            imgHeight = $(this).height();
        if (imgWidth > new_width || imgHeight > new_height) {
            var imgRatio = imgWidth / imgHeight,
                newRatio = new_width / new_height;
            if (imgRatio > newRatio) {
                $(this).width(new_width);
                $(this).height(new_height * imgHeight / imgWidth);
            } else {
                $(this).height(new_height);
                $(this).width(new_height * imgHeight / imgWidth);
            }
        }
        return this;
    };
    $.fn.smartFloat = function () {
        var position = function (element) {
            var top = element.position().top, pos = element.css("position");
            $(window).scroll(function () {
                var scrolls = $(this).scrollTop();
                if (scrolls > top) { //如果滚动到页面超出了当前元素element的相对页面顶部的高度
                    if (window.XMLHttpRequest) { //如果不是ie6
                        element.css({
                            position: "fixed",
                            top: 0
                        }).addClass("shadow");
                    } else { //如果是ie6
                        element.css({
                            top: scrolls
                        });
                    }
                } else {
                    element.css({
                        position: pos,
                        top: top
                    }).removeClass("shadow");
                }
            });
        };
        return $(this).each(function () {
            position($(this));
        });
    };
})(jQuery);