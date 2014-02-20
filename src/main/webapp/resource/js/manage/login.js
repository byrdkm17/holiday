/**
 * Created by Jing on 13-12-12.
 */

define(function (require, exports) {
    var $ = require('jquery');

    $('input').keydown(function () {
        $('.alert').fadeOut();
    });

    exports.init = function () {
        $('#username').focus().select();
    }
});