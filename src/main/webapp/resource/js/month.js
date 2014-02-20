function monthCurrent() {
    var timee = new Date();
    var MM = timee.getMonth() + 1;
    var month = MM;
    setTimeout(monthCurrent, 1000);
    var tarGet = $("div.monthCat>ul");
    $(tarGet).children().each(function (i) {
        var thisMonth = i + 1;
        if (month == thisMonth) {
            $(this).addClass("current").parent().addClass("m" + (i + 1))
        }
    });
}