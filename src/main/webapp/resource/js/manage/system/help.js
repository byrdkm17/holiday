/**
 * Created by Jing on 13-12-12.
 */

define(function (require, exports) {
    var $ = require('jquery');

    var base = $('body').data('base');

    $('.selectTag').click(function (e) {
        e.preventDefault();
        $('.tag').text($(this).text());
    });

    $('.save').click(function () {
        if ($('.tag').text() == '请选择') {
            return;
        }
        if (!$('.title').val()) {
            return;
        }
        $.ajax({
            url: base + '/manage/system/help/save',
            type: "POST",
            data: {
                id: $('[data-help]').data('help'),
                tag: $('.tag').text(),
                title: $('.title').val(),
                content: UE.getEditor('edit').getContent()
            },
            success: function (res) {
                if (res.success) {
                    location.href = base + '/manage/system/help';
                }
            }
        });
    });

    if ($('[data-help]').data('help')) {
        UE.getEditor('edit').setContent($('#content').html());
    }
});