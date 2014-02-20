/**
 * Created by Jing on 13-12-12.
 */

define(function (require) {
    var $ = require('jquery');

    require('icheck')($);

    $('input').iCheck({
        checkboxClass: 'icheckbox_square-green'
    });

    var base = $('body').data('base');
});