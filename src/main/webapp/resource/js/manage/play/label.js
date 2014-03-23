/**
 * Created by Jing on 13-12-12.
 */

define(function (require, exports) {
    var $ = require('jquery');

    var base = $('body').data('base');

    $('.children > div').hover(function () {
        $(this).find('a').show();
    }, function () {
        $(this).find('a').hide();
    });

    $('.remove').click(function (e) {
        e.preventDefault();
        $.ajax({
            url: base + '/manage/play/labelRemove',
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
        var type = $(this).closest('.item').data('type');
        var name = $.trim($(this).closest('div').find('input').val());
        if (name) {
            $.ajax({
                url: base + '/manage/play/labelSave',
                type: "POST",
                data: {type: type, name: name},
                success: function (res) {
                    if (res.success) {
                        location.reload();
                    }
                }
            });
        }

    });
});