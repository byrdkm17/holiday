/**
 * Created by Jing on 13-12-12.
 */

define(function (require, exports) {
    var emptyImg = "data:image/png;base64,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";

    var $ = require('jquery');

    var base = $('body').data('base');

    $('[type="file"]').on('change', function () {

        var parent = $(this).closest('div');
        var $box = parent.find('img');
        var $clean = parent.find('.clean');
        var $input = parent.find('[type="hidden"]');

        if (!$box.data('empty')) {
            $box.data('empty', $box.attr('src'));
        }

        var file = this.files[0], formdata = new FormData();
        if (!!file.type.match(/image.*/)) {
            var reader = new FileReader();
            reader.onloadend = function (e) {
                $box.attr('src', e.target.result);
            };
            reader.readAsDataURL(file);
            formdata.append("images", file);

            $.ajax({
                url: base + '/rest/image/save',
                type: "POST",
                data: formdata,
                processData: false,
                contentType: false,
                success: function (res) {
                    if (res.success) {
                        $input.val(res.list && res.list[0] && res.list[0].id);
                        $clean.show();
                    }
                }
            });
        }
    });

    $('a.clean').click(function (e) {
        e.preventDefault();
        var parent = $(this).closest('div');
        var img = parent.find('img');
        var $input = parent.find('[type="hidden"]');
        img.attr('src', img.data('empty') || emptyImg);
        $input.val('');
        $(this).hide();
    });

    $('.selectType').click(function (e) {
        e.preventDefault();
        $('[name="type"]').val($(this).text());
        $(this).closest('.btn-group').find('.name').text($(this).text());
    });

    $('.selectLike').click(function (e) {
        e.preventDefault();
        $('[name="like"]').val($(this).text());
        $(this).closest('.btn-group').find('.name').text($(this).text());
    });

    var $form = $('form');
    var postUrl = $form.attr('action');

    $form.submit(function () {
        return false;
    });

    $('.save').click(function () {
        $('[name="content"]').val(UE.getEditor('content').getContent());
        $.ajax({
            url: postUrl,
            type: "POST",
            data: $form.serialize(),
            success: function (res) {
                if (res.success) {
                    location.href = base + '/manage/custom';
                }
            }
        });
    });

    if ($('[name="id"]').val()) {
        UE.getEditor('content').setContent($('#contentText').html());
    }

});