//<!CDATA[
function g(o) {
    return document.getElementById(o);
}
function SerLi(n) {
    $('.allclass li').each(function (i) {
        g('S_' + i).className = 'Saltab';
        g('Sbc_0' + i).className = 'undis';
    });
    g('Sbc_0' + n).className = 'dis';
    g('S_' + n).className = 'Srtab';
}
//如果要做成点击后再转到请将<li>中的onmouseover 改成 onclick;
//]]>