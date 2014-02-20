/**
 * Created by Jing on 13-12-12.
 */

define(function (require) {
    var $ = require('jquery');

    require('icheck')($);

    $('input').iCheck({
        checkboxClass: 'icheckbox_square-green'
    });

    var base = $('body').data('base');

    $('.store').click(function () {
        var that = $(this).closest('div');
        $.ajax({
            url: base + '/manage/goods/status',
            type: "POST",
            data: {status: 1, id: that.data('id')},
            success: function (res) {
                if (res.success) {
                    location.reload();
                }
            }
        });
    });

});