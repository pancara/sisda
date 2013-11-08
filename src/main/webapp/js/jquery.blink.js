(function ($) {
    $.fn.blink = function (params) {
        var _this = this;
        var settings = {
            speed: "slow",
            interval: 5000
        };

        settings = $.extend(settings, params);
        var func_start = function () {
            var timer = window.setInterval(function () {
                $(_this).css("opacity", "0.1");
                window.setTimeout(function () {
                    $("img").css("opacity", "1");
                }, 100);
            }, interval);
            
            window.setTimeout(function () {
                clearInterval(timer);
            }, time);
        }
    }
})(jQuery);