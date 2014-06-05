/**
 * Created by Jing on 13-12-12.
 */

define(function (require, exports) {
    var emptyImg = "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMTIwIiBoZWlnaHQ9IjkwIj48cmVjdCB3aWR0aD0iMTIwIiBoZWlnaHQ9IjkwIiBmaWxsPSIjZWVlIj48L3JlY3Q+PHRleHQgdGV4dC1hbmNob3I9Im1pZGRsZSIgeD0iNjAiIHk9IjQ1IiBzdHlsZT0iZmlsbDojYWFhO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1zaXplOjEycHg7Zm9udC1mYW1pbHk6QXJpYWwsSGVsdmV0aWNhLHNhbnMtc2VyaWY7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTIweDkwPC90ZXh0Pjwvc3ZnPg==";

    var $ = require('jquery');

    require('icheck')($);

    $('input.square').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green'
    });

    var base = $('body').data('base');

    $('.reduce').click(function () {
        var value = +$('[name="preSale"]').val() || 0;
        if (value > 0) {
            $('[name="preSale"]').val(value - 1);
        }
    });
    $('.plus').click(function () {
        var value = +$('[name="preSale"]').val() || 0;
        $('[name="preSale"]').val(value + 1);
    });
    $('.editTitle').click(function (e) {
        e.preventDefault();
        $(this).hide().next().show().find('input').val($(this).find('.title').text());
    });
    $('.saveTitle').click(function () {
        var title = $.trim($(this).parent().prev().val());
        if (title) {
            $(this).closest('div').hide().prev().show().find('.title').text(title);
        }
    });

    $('[name="priceType"]').on('ifChecked', function () {
        var priceType = +$(this).val();
        if (priceType == 1) {
            $('.priceSet').find('.set').hide().end().find('.price').show();
        } else {
            $('.priceSet').find('.set').show().end().find('.price').hide();
        }
    });

    var $modal = $('#modal');

    $modal.find('form').submit(function () {
        return false;
    });

    $modal.on('hidden.bs.modal', function () {
        $modal.find('tbody').empty();
        $('.set').removeClass('cur');
    });

    $modal.on('show.bs.modal', function (e) {
        var priceList = $(e.relatedTarget).closest('.priceSet').data('priceList') || {};
        $(e.relatedTarget).addClass('cur');
        var tbody = $modal.find('tbody');
        var startTime = new Date($('[name="startTime"]').val()),
            endTime = new Date($('[name="endTime"]').val());
        if (!startTime || startTime < new Date()) {
            startTime = new Date();
        }
        if (!endTime || (endTime.getMonth() - startTime.getMonth() > 1)) {
            endTime.setMonth(startTime.getMonth() + 1);
        }
        var startDay = startTime.getDay();
        var tr = $('<tr>');
        var curTime = startTime.valueOf();
        for (var i = 0; i < 7; i++) {
            if (i < startDay) {
                tr.append('<td><input type="text" class="form-control input-sm" disabled></td>');
            } else {
                tr.append('<td><input type="text" class="form-control input-sm" title="' + formatDate(curTime + 60 * 60 * 24 * 1000 * (i - startDay)) + '" value="' + ((priceList[formatDate(curTime + 60 * 60 * 24 * 1000 * (i - startDay))] || 0) / 100) + '"></td>');
            }
        }
        tbody.append(tr);
        curTime = startTime.setDate(startTime.getDate() + 7 - startDay);
        var loop = 0;
        var s = '';
        while (curTime < endTime) {
            if (loop % 7 == 0) {
                s = '<tr>';
            }
            s = s + '<td><input type="text" class="form-control input-sm" title="' + formatDate(curTime) + '" value="' + ((priceList[formatDate(curTime)] || 0) / 100) + '"></td>';
            if (loop % 7 == 6) {
                s = s + '</tr>';
                tbody.append(s);
            }
            loop = loop + 1;
            curTime = curTime + 60 * 60 * 24 * 1000;
        }
        if (s.indexOf('</tr>') == -1) {
            for (i = loop % 7; i < 7; i++) {
                s = s + '<td><input type="text" class="form-control input-sm" disabled></td>';
            }
            s = s + '</tr>';
            tbody.append(s);
        }
    });

    var formatDate = function (date) {
        date = new Date(date);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? '0' + m : m;
        var d = date.getDate();
        d = d < 10 ? '0' + d : d;

        return y + '-' + m + '-' + d;
    };

    $('.saveSet').click(function () {
        var priceList = $('.set.cur').closest('.priceSet').data('priceList') || {};
        $modal.find('input[title]').each(function () {
            priceList[$(this).attr('title')] = ($(this).val() || 0) * 100;
        });
        $('.set.cur').closest('.priceSet').data('priceList', priceList);
        $modal.modal('hide');
    });


    $('.panel.price .panel-footer').click(function () {
        $(this).closest('.panel').remove();
        renderPriceSet();
    });

    $('.panel.price :checkbox').change(function () {
        renderPriceSet();
    });

    $('.addPrice').click(function () {
        $(this).before($('.panel.price:first').clone(true).show());
    });

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


    var renderPriceSet = function (priceSet) {
        priceSet = priceSet || count();
        var title = [];
        $('.panel.price').each(function (index) {
            if (index > 0) {
                var t = [];
                $(':checked', $(this)).each(function () {
                    var input = $(this).closest('span').next();
                    t.push(input.val() || input.attr('placeholder'));
                });
                if (t.length > 0) {
                    title.push(t);
                }
            }
        });

        if (title.length > 1) {
            title = matrix(title);
        } else {
            title = title[0];
        }
        $('.priceSet').each(function (index) {
            if (index > 0) {
                $(this).remove();
            }
        });

        title && $.each(title, function () {
            var ps = $('.priceSet:first').clone(true);
            var t = this;
            ps.find('button.btn-success').text(t);

            $.each(priceSet, function () {
                if (t == $.trim(this.title)) {
                    ps.find('input:eq(0)').val(+this.price / 100);
                    ps.find('input:eq(1)').val(this.number);
                    ps.data('priceList', this.priceList);
                }
            });

            $('.addPrice').after(ps.removeClass('hide'));
        });
    };

    var count = function () {
        var priceSet = [];
        $('.priceSet').each(function (index) {
            if (index > 0) {
                var s = {};
                s.title = $(this).find('button.btn-success').text();
                s.price = Math.round((+$(this).find('.price').val() || 0) * 100);
                s.priceList = $(this).data('priceList');
                s.number = $(this).find('.number').val();
                priceSet.push(s);
            }
        });
        return priceSet;
    };

    var matrix = function (title, arr, i) {
        arr = arr || title[0];
        i = i || 1;
        if (title.length > i) {
            var result = [];
            $.each(arr, function () {
                var t = this;
                $.each(title[i], function () {
                    result.push([t, this].join(','));
                });
            });
            return matrix(title, result, ++i);
        } else {
            return arr;
        }
    };

    $('.selectSupplier').click(function (e) {
        e.preventDefault();
        $('[name="supplier"]').val($(this).data('id'));
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

    $('.priceSet .btn-success').click(function () {
        $(this).blur();
    });

    var $form = $('form');
    var postUrl = $form.attr('action');

    $form.submit(function () {
        return false;
    });

    var $xc = $('[name="XC"]');
    if ($xc.length) {
        $('.addXC').click(function () {
            var cur = +$xc.val() + 1;
            $('[data-day="' + cur + '"]').show();
            $xc.val(cur);
            if (cur >= +$xc.data('max')) {
                $(this).hide();
            }
        });
    }

    $('[data-status]').click(function () {
        $(this).siblings().removeClass('btn-success');
        $(this).toggleClass('btn-success');
        var $success = $(this).closest('li').find('.btn-success');
        if ($success.length) {
            $(this).closest('li').find('[type="hidden"]').val($success.data('status'));
        } else {
            $(this).closest('li').find('[type="hidden"]').val(0);
        }
    });

    $('.save').click(function () {
        var service = [];
        $('.service .label.selected').each(function () {
            service.push($(this).data('id'));
        });
        $('[name="service"]').val(service.join(','));

        var priceGroup = [];
        $('.panel.price').each(function (index) {
            if (index > 0 && $(':checked', $(this)).length > 0) {
                var group = {};
                group.name = $(this).find('.title').text();
                group.names = [];
                $(':checked', $(this)).each(function () {
                    var input = $(this).closest('span').next();
                    group.names.push(input.val() || input.attr('placeholder'));
                });
                priceGroup.push(group);
            }
        });
        $('[name="priceGroup"]').val(JSON.stringify(priceGroup));

        var priceSet = [];
        var minPrice = 0, maxPrice = 0, linePrice1 = 0, linePrice2 = 0;
        $('.priceSet').each(function (index) {
            if (index > 0) {
                var s = {};
                s.title = $.trim($(this).find('button.btn-success').text());
                s.price = Math.round((+$(this).find('.price').val() || 0) * 100);
                s.priceList = $(this).data('priceList');
                s.number = $(this).find('.number').val();
                if (minPrice == 0 || minPrice > s.price) {
                    minPrice = s.price;
                }
                if (maxPrice == 0 || maxPrice < s.price) {
                    maxPrice = s.price;
                }
                if (s.title == '成人') {
                    linePrice1 = s.price;
                }
                if (s.title == '儿童') {
                    linePrice2 = s.price;
                }
                priceSet.push(s);
            }
        });
        $('[name="priceSet"]').val(JSON.stringify(priceSet));
        $('[name="minPrice"]').val(minPrice);
        $('[name="maxPrice"]').val(maxPrice);
        $('[name="linePrice1"]').val(linePrice1);
        $('[name="linePrice2"]').val(linePrice2);

        var arg = [];
        $('.arg .label.selected').each(function () {
            arg.push($(this).data('id'));
        });
        $('[name="arg"]').val(arg.join(','));


        if ($('[name="production"]').val() != 'line') {
            $('[name="feature"]').val(UE.getEditor('feature').getContent());
            $('[name="note"]').val(UE.getEditor('note').getContent());
        } else {

        }

        var label = [];
        $('.labels .label.selected').each(function () {
            label.push($(this).data('id'));
        });
        $('[name="label"]').val(label.join(','));

        var target = [];
        $('.target .label.selected').each(function () {
            target.push($(this).data('id'));
        });
        $('[name="target"]').val(target.join(','));

        var discount = $('[name="discount"]');
        discount.val(+discount.val() * 100);

        $.ajax({
            url: postUrl,
            type: "POST",
            data: $form.serialize(),
            success: function (res) {
                if (res.success) {
                    location.href = base + '/manage/goods';
                }
            }
        });
    });

    if ($('[name="id"]').val()) {
        var $addBtn = $('.addPrice');
        var priceGroup = JSON.parse($('#priceGroupPlain').html());
        var priceSet = JSON.parse($('#priceSetPlain').html());

        if ($('[name="production"]').val() != 'line') {
            $.each(priceGroup, function () {
                var ps = $('.panel.price:first').clone(true);

                ps.addClass('hide');
                ps.find('.title').text(this.name);
                $.each(this.names, function (index) {
                    ps.find(':checkbox:eq(' + index + ')').prop('checked', true);
                    ps.find(':checkbox:eq(' + index + ')').closest('div').find('input').val(this);
                    $addBtn.before(ps.show());
                });
            });
        }

        renderPriceSet(priceSet);

        if ($('[name="priceType"]:checked').val() == 1) {
            $('.priceSet').find('.set').hide().end().find('.price').show();
        } else {
            $('.priceSet').find('.set').show().end().find('.price').hide();
        }

        var services = $('[name="service"]').val();
        services = services.split(',');
        $.each(services, function () {
            $('[data-id="' + this + '"]').addClass('selected label-success').remove('label-default');
        });

        var args = $('[name="arg"]').val();
        args = args.split(',');
        $.each(args, function () {
            $('[data-id="' + this + '"]').addClass('selected label-success').remove('label-default');
        });

        var labels = $('[name="label"]').val();
        labels = labels.split(',');
        $.each(labels, function () {
            $('[data-id="' + this + '"]').addClass('selected label-success').remove('label-default');
        });

        var target = $('[name="target"]').val();
        target = target.split(',');
        $.each(target, function () {
            $('[data-id="' + this + '"]').addClass('selected label-success').remove('label-default');
        });

        if ($('[name="production"]').val() != 'line') {
            UE.getEditor('feature').setContent($('#featureText').html());
            UE.getEditor('note').setContent($('#noteText').html());
        }
    }

});