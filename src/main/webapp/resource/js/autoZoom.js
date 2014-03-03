/**
 * Created by Jing on 14-2-16.
 */


$("img.autoZoom").load(function () {
    resize.call(this);
}).bind('resize', function () {
    resize.call(this);
});

var resize = function () {
    console.log('12555');
    var img = new Image();
    img.src = $(this).attr('src');
    var ow = img.width, oh = img.height, w = $(this).width(), h = $(this).height(), nw, nh;

    if (ow / oh > w / h) {
        nh = h;
        nw = ow / (oh / h);
        $(this).width(nw).height(nh);
        $(this).css('margin-left', (nw - w) / -2 + 'px');
    } else {
        nw = w;
        nh = oh / (ow / w);
        $(this).width(nw).height(nh);
        $(this).css('margin-top', (nh - h) / -2 + 'px');
    }
};