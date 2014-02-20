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

    var $modal = $('#modal');

    $modal.find('form').submit(function () {
        return false;
    });

    $modal.on('hidden.bs.modal', function () {
        $modal.find('#name').val('');
        $modal.find('#id').val('');
    });

    $('.save').click(function () {
        var name = $.trim($modal.find('#name').val());
        var id = $modal.find('#id').val();
        if (name) {
            $.ajax({
                url: base + '/manage/base/supplier/save',
                type: "POST",
                data: {name: name, id: id},
                success: function (res) {
                    if (res.success) {
                        location.reload();
                    }
                }
            });
        }
    });

    $('.remove').click(function () {
        $.ajax({
            url: base + '/manage/base/supplier/remove/' + $(this).closest('div').data('id'),
            type: "POST",
            success: function (res) {
                if (res.success) {
                    location.reload();
                }
            }
        });
    });

    $('.edit').click(function () {
        var id = $(this).closest('div').data('id');
        var name = $(this).closest('div').find('.name').text();
        $modal.find('#id').val(id);
        $modal.find('#name').val(name);
        $modal.modal('show');
    });

});