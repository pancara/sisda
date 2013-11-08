(function($) {
    $.fn.wj_progress_indicator = function(params) {
        var _this = $(this);

        var defaults = {
            action: "show",
            
            image : "images/progress-working.gif"
        };
        var settings = $.extend(defaults, params);

        return _this.each(function() {
            if (settings.action == "show") {
                var img = $("<img src='" + settings.image + "' class='wj_progress_indicator_img'></img>");
                $(img).css({"background": "#FFF", "border-radius": "5px", "opacity": 0.6});
                $(img).prependTo(_this);
                $("img.wj_progress_indicator_img").show();
                $(img).center();
            } else {
                $("img.wj_progress_indicator_img").remove();
            }
        });
    };
})(jQuery);