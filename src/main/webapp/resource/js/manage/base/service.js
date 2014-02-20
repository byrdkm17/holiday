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

    $modal.on('hidden.bs.modal', function () {
        $modal.find('#name').val('');
        $modal.find('#id').val('');
        $modal.find(':radio').iCheck('uncheck');
    });

    $('.save').click(function () {
        var production = $modal.find(':radio:checked').val();
        var name = $.trim($modal.find('#name').val());
        var id = $modal.find('#id').val();
        if (production && name) {
            $.ajax({
                url: base + '/manage/base/service/save',
                type: "POST",
                data: {production: production, name: name, id: id},
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
            url: base + '/manage/base/service/remove/' + $(this).closest('div').data('id'),
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
        var production = $(this).closest('div').data('production');

        $modal.find('#id').val(id);
        $modal.find('#name').val(name);
        $modal.find(':radio[value="' + production + '"]').iCheck('check');
        $modal.modal('show');
    });

});