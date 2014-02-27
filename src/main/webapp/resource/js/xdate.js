/*
 XDate v0.8
 Docs & Licensing: http://arshaw.com/xdate/
 */
var XDate = function (g, n, A, p) {
    function f() {
        var a = this instanceof f ? this : new f, c = arguments, b = c.length, d;
        typeof c[b - 1] == "boolean" && (d = c[--b], c = q(c, 0, b));
        if (b)if (b == 1)if (b = c[0], b instanceof g || typeof b == "number")a[0] = new g(+b); else if (b instanceof f) {
            var c = a, h = new g(+b[0]);
            if (l(b))h.toString = v;
            c[0] = h
        } else {
            if (typeof b == "string") {
                a[0] = new g(0);
                a:{
                    for (var c = b, b = d || !1, h = f.parsers, w = 0, e; w < h.length; w++)if (e = h[w](c, b, a)) {
                        a = e;
                        break a
                    }
                    a[0] = new g(c)
                }
            }
        } else a[0] = new g(m.apply(g, c)), d || (a[0] = r(a[0])); else a[0] = new g;
        typeof d == "boolean" && B(a, d);
        return a
    }

    function l(a) {
        return a[0].toString === v
    }

    function B(a, c, b) {
        if (c) {
            if (!l(a))b && (a[0] = new g(m(a[0].getFullYear(), a[0].getMonth(), a[0].getDate(), a[0].getHours(), a[0].getMinutes(), a[0].getSeconds(), a[0].getMilliseconds()))), a[0].toString = v
        } else l(a) && (a[0] = b ? r(a[0]) : new g(+a[0]));
        return a
    }

    function C(a, c, b, d, h) {
        var e = k(j, a[0], h), a = k(D, a[0], h), h = !1;
        d.length == 2 && typeof d[1] == "boolean" && (h = d[1], d = [b]);
        b = c == 1 ? (b % 12 + 12) % 12 : e(1);
        a(c, d);
        h && e(1) != b && (a(1, [e(1) - 1]), a(2, [E(e(0),
            e(1))]))
    }

    function F(a, c, b, d) {
        var b = Number(b), h = n.floor(b);
        a["set" + o[c]](a["get" + o[c]]() + h, d || !1);
        h != b && c < 6 && F(a, c + 1, (b - h) * G[c], d)
    }

    function H(a, c, b) {
        var a = a.clone().setUTCMode(!0, !0), c = f(c).setUTCMode(!0, !0), d = 0;
        if (b == 0 || b == 1) {
            for (var h = 6; h >= b; h--)d /= G[h], d += j(c, !1, h) - j(a, !1, h);
            b == 1 && (d += (c.getFullYear() - a.getFullYear()) * 12)
        } else b == 2 ? (b = a.toDate().setUTCHours(0, 0, 0, 0), d = c.toDate().setUTCHours(0, 0, 0, 0), d = n.round((d - b) / 864E5) + (c - d - (a - b)) / 864E5) : d = (c - a) / [36E5, 6E4, 1E3, 1][b - 3];
        return d
    }

    function s(a) {
        var c =
            a(0), b = a(1), d = a(2), a = new g(m(c, b, d)), c = t(I(c, b, d));
        return n.floor(n.round((a - c) / 864E5) / 7) + 1
    }

    function I(a, c, b) {
        c = new g(m(a, c, b));
        if (c < t(a))return a - 1; else if (c >= t(a + 1))return a + 1;
        return a
    }

    function t(a) {
        a = new g(m(a, 0, 4));
        a.setUTCDate(a.getUTCDate() - (a.getUTCDay() + 6) % 7);
        return a
    }

    function J(a, c, b, d) {
        var h = k(j, a, d), e = k(D, a, d);
        b === p && (b = I(h(0), h(1), h(2)));
        b = t(b);
        d || (b = r(b));
        a.setTime(+b);
        e(2, [h(2) + (c - 1) * 7])
    }

    function K(a, c, b, d, h) {
        var e = f.locales, g = e[f.defaultLocale] || {}, i = k(j, a, h), b = (typeof b == "string" ?
            e[b] : b) || {};
        return x(a, c, function (a) {
            if (d)for (var b = (a == 7 ? 2 : a) - 1; b >= 0; b--)d.push(i(b));
            return i(a)
        }, function (a) {
            return b[a] || g[a]
        }, h)
    }

    function x(a, c, b, d, e) {
        for (var f, g, i = ""; f = c.match(N);) {
            i += c.substr(0, f.index);
            if (f[1]) {
                g = i;
                for (var i = a, j = f[1], l = b, m = d, n = e, k = j.length, o = void 0, q = ""; k > 0;)o = O(i, j.substr(0, k), l, m, n), o !== p ? (q += o, j = j.substr(k), k = j.length) : k--;
                i = g + (q + j)
            } else f[3] ? (g = x(a, f[4], b, d, e), parseInt(g.replace(/\D/g, ""), 10) && (i += g)) : i += f[7] || "'";
            c = c.substr(f.index + f[0].length)
        }
        return i + c
    }

    function O(a, c, b, d, e) {
        var g = f.formatters[c];
        if (typeof g == "string")return x(a, g, b, d, e); else if (typeof g == "function")return g(a, e || !1, d);
        switch (c) {
            case "fff":
                return i(b(6), 3);
            case "s":
                return b(5);
            case "ss":
                return i(b(5));
            case "m":
                return b(4);
            case "mm":
                return i(b(4));
            case "h":
                return b(3) % 12 || 12;
            case "hh":
                return i(b(3) % 12 || 12);
            case "H":
                return b(3);
            case "HH":
                return i(b(3));
            case "d":
                return b(2);
            case "dd":
                return i(b(2));
            case "ddd":
                return d("dayNamesShort")[b(7)] || "";
            case "dddd":
                return d("dayNames")[b(7)] || "";
            case "M":
                return b(1) +
                    1;
            case "MM":
                return i(b(1) + 1);
            case "MMM":
                return d("monthNamesShort")[b(1)] || "";
            case "MMMM":
                return d("monthNames")[b(1)] || "";
            case "yy":
                return(b(0) + "").substring(2);
            case "yyyy":
                return b(0);
            case "t":
                return u(b, d).substr(0, 1).toLowerCase();
            case "tt":
                return u(b, d).toLowerCase();
            case "T":
                return u(b, d).substr(0, 1);
            case "TT":
                return u(b, d);
            case "z":
            case "zz":
            case "zzz":
                return e ? c = "Z" : (d = a.getTimezoneOffset(), a = d < 0 ? "+" : "-", b = n.floor(n.abs(d) / 60), d = n.abs(d) % 60, e = b, c == "zz" ? e = i(b) : c == "zzz" && (e = i(b) + ":" + i(d)), c =
                    a + e), c;
            case "w":
                return s(b);
            case "ww":
                return i(s(b));
            case "S":
                return c = b(2), c > 10 && c < 20 ? "th" : ["st", "nd", "rd"][c % 10 - 1] || "th"
        }
    }

    function u(a, c) {
        return a(3) < 12 ? c("amDesignator") : c("pmDesignator")
    }

    function y(a) {
        return!isNaN(+a[0])
    }

    function j(a, c, b) {
        return a["get" + (c ? "UTC" : "") + o[b]]()
    }

    function D(a, c, b, d) {
        a["set" + (c ? "UTC" : "") + o[b]].apply(a, d)
    }

    function r(a) {
        return new g(a.getUTCFullYear(), a.getUTCMonth(), a.getUTCDate(), a.getUTCHours(), a.getUTCMinutes(), a.getUTCSeconds(), a.getUTCMilliseconds())
    }

    function E(a, c) {
        return 32 - (new g(m(a, c, 32))).getUTCDate()
    }

    function z(a) {
        return function () {
            return a.apply(p, [this].concat(q(arguments)))
        }
    }

    function k(a) {
        var c = q(arguments, 1);
        return function () {
            return a.apply(p, c.concat(q(arguments)))
        }
    }

    function q(a, c, b) {
        return A.prototype.slice.call(a, c || 0, b === p ? a.length : b)
    }

    function L(a, c) {
        for (var b = 0; b < a.length; b++)c(a[b], b)
    }

    function i(a, c) {
        c = c || 2;
        for (a += ""; a.length < c;)a = "0" + a;
        return a
    }

    var o = "FullYear,Month,Date,Hours,Minutes,Seconds,Milliseconds,Day,Year".split(","), M = ["Years",
        "Months", "Days"], G = [12, 31, 24, 60, 60, 1E3, 1], N = /(([a-zA-Z])\2*)|(\((('.*?'|\(.*?\)|.)*?)\))|('(.*?)')/, m = g.UTC, v = g.prototype.toUTCString, e = f.prototype;
    e.length = 1;
    e.splice = A.prototype.splice;
    e.getUTCMode = z(l);
    e.setUTCMode = z(B);
    e.getTimezoneOffset = function () {
        return l(this) ? 0 : this[0].getTimezoneOffset()
    };
    L(o, function (a, c) {
        e["get" + a] = function () {
            return j(this[0], l(this), c)
        };
        c != 8 && (e["getUTC" + a] = function () {
            return j(this[0], !0, c)
        });
        c != 7 && (e["set" + a] = function (a) {
            C(this, c, a, arguments, l(this));
            return this
        }, c !=
            8 && (e["setUTC" + a] = function (a) {
            C(this, c, a, arguments, !0);
            return this
        }, e["add" + (M[c] || a)] = function (a, d) {
            F(this, c, a, d);
            return this
        }, e["diff" + (M[c] || a)] = function (a) {
            return H(this, a, c)
        }))
    });
    e.getWeek = function () {
        return s(k(j, this, !1))
    };
    e.getUTCWeek = function () {
        return s(k(j, this, !0))
    };
    e.setWeek = function (a, c) {
        J(this, a, c, !1);
        return this
    };
    e.setUTCWeek = function (a, c) {
        J(this, a, c, !0);
        return this
    };
    e.addWeeks = function (a) {
        return this.addDays(Number(a) * 7)
    };
    e.diffWeeks = function (a) {
        return H(this, a, 2) / 7
    };
    f.parsers = [function (a, c, b) {
        if (a = a.match(/^(\d{4})(-(\d{2})(-(\d{2})([T ](\d{2}):(\d{2})(:(\d{2})(\.(\d+))?)?(Z|(([-+])(\d{2})(:?(\d{2}))?))?)?)?)?$/)) {
            var d = new g(m(a[1], a[3] ? a[3] - 1 : 0, a[5] || 1, a[7] || 0, a[8] || 0, a[10] || 0, a[12] ? Number("0." + a[12]) * 1E3 : 0));
            a[13] ? a[14] && d.setUTCMinutes(d.getUTCMinutes() + (a[15] == "-" ? 1 : -1) * (Number(a[16]) * 60 + (a[18] ? Number(a[18]) : 0))) : c || (d = r(d));
            return b.setTime(+d)
        }
    }];
    f.parse = function (a) {
        return+f("" + a)
    };
    e.toString = function (a, c, b) {
        return a === p || !y(this) ? this[0].toString() : K(this, a, c, b, l(this))
    };
    e.toUTCString = e.toGMTString = function (a, c, b) {
        return a === p || !y(this) ? this[0].toUTCString() : K(this, a, c, b, !0)
    };
    e.toISOString = function () {
        return this.toUTCString("yyyy-MM-dd'T'HH:mm:ss(.fff)zzz")
    };
    f.defaultLocale = "";
    f.locales = {"": {monthNames: "January,February,March,April,May,June,July,August,September,October,November,December".split(","), monthNamesShort: "Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec".split(","), dayNames: "Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday".split(","), dayNamesShort: "Sun,Mon,Tue,Wed,Thu,Fri,Sat".split(","),
        amDesignator: "AM", pmDesignator: "PM"}};
    f.formatters = {i: "yyyy-MM-dd'T'HH:mm:ss(.fff)", u: "yyyy-MM-dd'T'HH:mm:ss(.fff)zzz"};
    L("getTime,valueOf,toDateString,toTimeString,toLocaleString,toLocaleDateString,toLocaleTimeString,toJSON".split(","), function (a) {
        e[a] = function () {
            return this[0][a]()
        }
    });
    e.setTime = function (a) {
        this[0].setTime(a);
        return this
    };
    e.valid = z(y);
    e.clone = function () {
        return new f(this)
    };
    e.clearTime = function () {
        return this.setHours(0, 0, 0, 0)
    };
    e.toDate = function () {
        return new g(+this[0])
    };
    f.now = function () {
        return+new g
    };
    f.today = function () {
        return(new f).clearTime()
    };
    f.UTC = m;
    f.getDaysInMonth = E;
    if (typeof module !== "undefined" && module.exports)module.exports = f;
    typeof define === "function" && define.amd && define([], function () {
        return f
    });
    return f
}(Date, Math, Array);
