/**
 * Created by Jing on 13-12-12.
 */

define(function (require, exports) {
    var $ = require('jquery');

    var base = $('body').data('base');

    $('.btn-default').click(function () {
        var that = $(this).closest('div');
        $(this).hide();
        $('.btn-success', that).show();
        $('li.value', that).hide();
        $('li.input', that).show();
    });

    $('.btn-success').click(function () {
        var that = $(this).closest('div');
        $(this).hide();
        $('.btn-default', that).show();
        $('li.value', that).show();
        $('li.input', that).hide();

        $.ajax({
            url: base + '/manage/system/setting/save',
            type: "POST",
            data: {id: that.data('id'), value: $('li.input input', that).val()},
            success: function (res) {
                if (res.success) {
                    location.reload();
                }
            }
        });
    });
});