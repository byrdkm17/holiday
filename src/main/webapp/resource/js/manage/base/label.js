/**
 * Created by Jing on 13-12-12.
 */

define(function (require, exports) {
    var $ = require('jquery');

    require('icheck')($);

    $('input').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green'
    });

    var base = $('body').data('base');

    var $modal = $('#modal');

    $modal.find('form').submit(function () {
        return false;
    });

    $('.save').click(function () {
        var production = $modal.find(':radio:checked').val();
        var name = $.trim($modal.find('#name').val());
        if (production && name) {
            $.ajax({
                url: base + '/manage/base/label/save',
                type: "POST",
                data: {production: production, name: name},
                success: function (res) {
                    if (res.success) {
                        location.reload();
                    }
                }
            });
        }
    });

    $('.children > div').hover(function () {
        $(this).find('a').show();
    }, function () {
        $(this).find('a').hide();
    });

    $('.name').hover(function () {
        $(this).find('a').show();
    }, function () {
        $(this).find('a').hide();
    });

    $('.remove').click(function (e) {
        e.preventDefault();
        $.ajax({
            url: base + '/manage/base/label/remove',
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
                url: base + '/manage/base/label/save',
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