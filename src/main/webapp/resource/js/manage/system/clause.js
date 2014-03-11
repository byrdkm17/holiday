/**
 * Created by Jing on 13-12-12.
 */

define(function (require, exports) {
    var $ = require('jquery');

    var base = $('body').data('base');

    $('.save').click(function () {
        $.ajax({
            url: base + '/manage/system/help/clauseSave',
            type: "POST",
            data: {
                content: UE.getEditor('edit').getContent()
            },
            success: function (res) {
                if (res.success) {
                    location.href = base + '/manage/system/help/clause';
                }
            }
        });
    });

    UE.getEditor('edit').setContent($('#content').html());
});