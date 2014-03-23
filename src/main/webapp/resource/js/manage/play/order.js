/**
 * Created by Jing on 13-12-12.
 */

define(function (require, exports) {
    var $ = require('jquery');

    var base = $('body').data('base');

    $('.doNote').click(function (e) {
        e.preventDefault();
        $(this).closest('li').hide();
        $(this).closest('.item').find('.editNote').show();
    });

    $('.saveNote').click(function (e) {
        e.preventDefault();
        save($(this).closest('.item').data('id'), $(this).closest('li').find('input').val());
        $(this).closest('li').hide();
        $(this).closest('.item').find('.showNote').show().find('.info').text($(this).closest('li').find('input').val());
    });

    var save = function (id, note) {
        $.ajax({
            url: base + '/manage/play/saveNote',
            type: "POST",
            data: {id: id, note: note},
            success: function (res) {
            }
        });
    };

});