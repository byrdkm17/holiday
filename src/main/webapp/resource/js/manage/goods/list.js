/**
 * Created by Jing on 13-12-12.
 */

define(function (require, exports) {
    var $ = require('jquery');

    require('icheck')($);

    $('input').iCheck({
        checkboxClass: 'icheckbox_square-green'
    });

    var base = $('body').data('base');

    $('.store').click(function () {
        $(this).blur();
        var that = $(this).closest('.item');
        $.ajax({
            url: base + '/manage/goods/status',
            type: "POST",
            data: {status: 0, id: that.data('id')},
            success: function (res) {
                if (res.success) {
                    location.reload();
                }
            }
        });
    });

    $('.hot').click(function () {
        $(this).blur();
        var that = $(this).closest('.item');
        $.ajax({
            url: base + '/manage/goods/hot',
            type: "POST",
            data: {hot: $(this).data('hot'), id: that.data('id')},
            success: function (res) {
                if (res.success) {
                    location.reload();
                }
            }
        });
    });

});