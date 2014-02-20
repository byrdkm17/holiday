/**
 * Created by Jing on 13-12-12.
 */

define(function (require) {
    var $ = require('jquery');

    require('icheck')($);

    var emptyImg = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAi4AAACMCAYAAABbASITAAAMAUlEQVR4Xu3dPW/UShQGYKcAREELFDQIUSFRAuLnUyAqRAk1dEgp6YAr58poNHjt8e7s4nPy0FDEmcx5zkR+M/7Yq+vr69+DfwQIECBAgACBAAJXgkuALpkiAQIECBAgcCMguFgIBAgQIECAQBgBwSVMq0yUAAECBAgQEFysAQIECBAgQCCMgOASplUmSoAAAQIECAgu1gABAgQIECAQRkBwCdMqEyVAgAABAgQEF2uAAAECBAgQCCMguIRplYkSIECAAAECgos1QIAAAQIECIQREFzCtMpECRAgQIAAAcHFGiBAgAABAgTCCAguYVplogQIECBAgIDgYg0QIECAAAECYQQElzCtMlECBAgQIEBAcLEGCBAgQIAAgTACgkuYVpkoAQIECBAgILhYAwQIECBAgEAYAcElTKtMlAABAgQIEBBcrAECBAgQIEAgjIDgEqZVJkqAAAECBAgILtYAAQIECBAgEEZAcAnTKhMlQIAAAQIEBBdrgAABAgQIEAgjILiEaZWJEiBAgAABAoKLNUCAAAECBAiEERBcwrTKRAkQIECAAAHBxRogQIAAAQIEwggILmFaZaIECBAgQICA4GINECBAgAABAmEEBJcwrTJRAgQIECBAQHCxBggQIECAAIEwAoJLmFaZKAECBAgQICC4WAMECBAgQIBAGAHBJUyrTJQAAQIECBAQXKwBAgQIECBAIIyA4BKmVSZKgAABAgQICC7WAAECBAgQIBBGQHAJ0yoTJUCAAAECBAQXa4AAAQIECBAIIyC4hGmViRIgQIAAAQKCizVAgAABAgQIhBEQXMK0ykQJECBAgAABwcUaIECAAAECBMIICC5hWmWiBAgQIECAgOBiDRAgQIAAAQJhBASXMK0yUQIECBAgQEBwsQYIECBAgACBMAKCS5hWmSgBAgQIECAguFgDBAgQIECAQBgBwSVMq0yUAAECBAgQEFysAQIECBAgQCCMgOASplUmSoAAAQIECAgu1gABAgQIECAQRkBwCdMqEyVAgAABAgQEF2uAAAECBAgQCCMguIRplYkSIECAAAECgos1QOCMAtfX18OnT5+GX79+Nf+Uhw8fDi9fvvzr+J5jTYP//PlzePfu3TD+X/+7uroaXrx4MTx+/Lh57uc68PPnz8O3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';

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
        $modal.find('img').attr('src', emptyImg);
        $modal.find('#image').val('');
        $modal.find('#url').val('');
        $modal.find('#id').val('');
        $modal.find(':radio').iCheck('uncheck');
    });

    $('[type="file"]').on('change', function () {

        var parent = $(this).closest('div');
        var $box = parent.find('img');
        var $input = parent.find('[type="hidden"]');

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
                    }
                }
            });
        }
    });

    $('.save').click(function () {
        var position = $modal.find(':radio:checked').val();
        var image = $modal.find('#image').val();
        var id = $modal.find('#id').val();
        var url = $modal.find('#url').val();
        if (position) {
            $.ajax({
                url: base + '/manage/ad/save',
                type: "POST",
                data: {position: position, image: image, id: id, url: url},
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
            url: base + '/manage/ad/remove/' + $(this).closest('div').data('id'),
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
        var url = $(this).closest('div').find('.url').text();
        var position = $(this).closest('div').find('.position').text();
        var img = $(this).closest('div').find('img').attr('src');

        $modal.find('#id').val(id);
        $modal.find('img').attr('src', img);
        $modal.find('#image').val(img.split('/')[img.split('/').length - 1]);
        $modal.find('#url').val(url);
        $modal.find(':radio[value="' + position + '"]').iCheck('check');
        $modal.modal('show');
    });
});