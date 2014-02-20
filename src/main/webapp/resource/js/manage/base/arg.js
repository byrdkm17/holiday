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

    $('.children > div').hover(function () {
        $(this).find('a').show();
    }, function () {
        $(this).find('a').hide();
    });

    $('.remove').click(function (e) {
        e.preventDefault();
        $.ajax({
            url: base + '/manage/base/arg/remove',
            type: "POST",
            data: {id: $(this).closest('div').data('id')},
            success: function (res) {
                if (res.success) {
                    location.reload();
                }
            }
        });
    });

    $('.addInput').keydown(function (e) {
        if (e.keyCode == 13) {
            $('.add').click();
        }
    });

    $('.add').click(function () {
        var father = $(this).closest('.item').data('id');
        var name = $.trim($(this).closest('div').find('input').val());
        if (name) {
            $.ajax({
                url: base + '/manage/base/arg/save',
                type: "POST",
                data: {father: father, name: name},
                success: function (res) {
                    if (res.success) {
                        location.reload();
                    }
                }
            });
        }

    });
});