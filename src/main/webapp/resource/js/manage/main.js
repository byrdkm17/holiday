/**
 * Created with IntelliJ IDEA.
 * User: Jing
 * Date: 13-11-4
 * Time: 下午8:16
 */

define(function (require) {
    var $ = require('jquery');

    var $module = $('[data-module]'),
        path = ($module.data('module') || '').replace('.', '/'),
        id = $module.attr('id');

    path && require.async('./' + path, function (m) {
        m && m.init && m.init();
    });

    $('[data-target="' + id + '"]').closest('li').addClass('active');
});
