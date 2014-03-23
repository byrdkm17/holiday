/**
 * Created by Jing on 13-12-12.
 */

define(function (require, exports) {
    var emptyImg = "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMTIwIiBoZWlnaHQ9IjkwIj48cmVjdCB3aWR0aD0iMTIwIiBoZWlnaHQ9IjkwIiBmaWxsPSIjZWVlIj48L3JlY3Q+PHRleHQgdGV4dC1hbmNob3I9Im1pZGRsZSIgeD0iNjAiIHk9IjQ1IiBzdHlsZT0iZmlsbDojYWFhO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1zaXplOjEycHg7Zm9udC1mYW1pbHk6QXJpYWwsSGVsdmV0aWNhLHNhbnMtc2VyaWY7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTIweDkwPC90ZXh0Pjwvc3ZnPg==";

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


    $('.selectLike').click(function (e) {
        e.preventDefault();
        $('[name="like"]').val($(this).data('id'));
        $(this).closest('.btn-group').find('.name').text($(this).text());
    });
    $('.selectCategory').click(function (e) {
        e.preventDefault();
        $('[name="category"]').val($(this).data('id'));
        $(this).closest('.btn-group').find('.name').text($(this).text());
    });

    $('.label').hover(function () {
        $(this).addClass('label-success').removeClass('label-default');
    },function () {
        if (!$(this).hasClass('selected')) {
            $(this).addClass('label-default').removeClass('label-success');
        }
    }).click(function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected label-success').addClass('label-default');
        } else {
            $(this).addClass('selected');
        }
    });

    var $form = $('form');
    var postUrl = $form.attr('action');

    $form.submit(function () {
        return false;
    });

    $('.save').click(function () {
        var label = [];
        $('.labels .label.selected').each(function () {
            label.push($(this).data('id'));
        });
        $('[name="label"]').val(label.join(','));

        $('[name="note"]').val(UE.getEditor('note').getContent());

        $.ajax({
            url: postUrl,
            type: "POST",
            data: $form.serialize(),
            success: function (res) {
                if (res.success) {
                    location.href = base + '/manage/play';
                }
            }
        });
    });

    if ($('[name="id"]').val()) {
        var label = $('[name="label"]').val();
        label = label.split(',');
        $.each(label, function () {
            $('[data-id="' + this + '"]').addClass('selected label-success').remove('label-default');
        });

        UE.getEditor('note').setContent($('#noteText').html());
    }

});