/**
 * Created by Jing on 13-12-12.
 */

define(function (require, exports) {
    var $ = require('jquery');

    var base = $('body').data('base');

    var $modal = $('#addManage');

    $modal.on('hidden.bs.modal', function () {
        $modal.find('#id').val('');
        $modal.find('#username').val('');
        $modal.find('#password').val('');
    });

    $('.saveManage').click(function () {
        var id = $modal.find('#id').val()
            , username = $.trim($modal.find('#username').val())
            , password = $.trim($modal.find('#password').val());

        if (username && password) {
            $.ajax({
                url: base + '/manage/system/manager/save',
                type: "POST",
                data: {
                    id: id,
                    username: username,
                    password: password
                },
                success: function (res) {
                    if (res.success) {
                        location.href = base + '/manage/system/manager';
                    }
                }
            });
        }
    });

    $('.editManage').click(function () {
        var id = $(this).closest('.item').data('id');

        $.ajax({
            url: base + '/manage/system/manager/find/' + id,
            type: "GET",
            success: function (res) {
                if (res.success) {
                    $modal.find('#id').val(res.member.id);
                    $modal.find('#username').val(res.member.username);
                    $modal.modal('show');
                }
            }
        });
    });
});