/**
 * jquery.wj_slide.js
 *
 * Created by :
 *      pancara kusuma wijonarko
 *      pancara.k.wijonarko@gmail.com
 *
 * developed with jQuery v1.4.2
 *
 */
(function($) {
    $.fn.wj_slide = function(params) {
        var _this = this;
        var scroller;
        var indicator;
        var tab_count;
        var content_width;
        var content_height;
        var handle_func = 0;
        var tab_index = 0;
        var indicator_height = 18;
        var on_paused = false;

        /*
         start_anim : start animation after [value] mili seconds
         interval   : interval between animation
         animation speed : "slow", "normal", "fast" or animation in miliseconds


         direction : direction of slide animation, slide to left or right
            1 = left
            2 = right
         */
        var settings = {
            start: 1000,
            interval: 5000,
            speed: 1400,
            direction: 1,
            img_prev: "images/wj_slide.arrow_left.png",
            img_next: "images/wj_slide.arrow_right.png",
            img_play: "images/wj_slide/control_play_blue.png",
            img_pause: "images/wj_slide/control_pause_blue.png"
        };

        settings = $.extend(settings, params);

        var _start_slide = function () {
            _update_indicator();
            scroller.animate({'left': -1 * tab_index * content_width}, settings.speed);
        };

        var _stop_slide = function () {
            scroller.stop(true, false);
        };


        var _update_indicator = function () {
            for (var i = 0; i < tab_count; i++) {
                var opacity = (i == tab_index ? 0.9 : 0.6);
                var background = (i == tab_index ? "#c22407" : "#06649d");
                var selector = "a[rel=" + i + "]";
                $(selector, indicator).css({"opacity" : opacity, "background" : background});
            }
        };

        var _start = function() {
            _update_indicator();
            handle_func = setTimeout(_run_change_tab, settings.start);
        };

        var _next_index = function () {
            var next = tab_index + 1;
            return  (next == tab_count ? 0 : next);
        };

        var _previous_index = function() {
            var previous = tab_index - 1;
            return (previous < 0 ? tab_count - 1 : previous);
        };

        var _run_change_tab = function() {
            _change_tab();
            handle_func = setTimeout(_run_change_tab, settings.interval);
        };

        var _change_tab = function() {
            tab_index = (settings.direction == 1 ? _next_index() : _previous_index());
            _start_slide();
        };

        var _pause_resume_slide = function() {
            if (on_paused) {
                on_paused = false;
                $("a.pause_resume>img", indicator).attr("src", settings.img_pause);
                _run_change_tab();
            } else {
                clearTimeout(handle_func);
                on_paused = true;
                $("a.pause_resume>img", indicator).attr("src", settings.img_play);
            }

        };
        var _clicker_click = function () {
            _stop_slide();
            tab_index = parseInt($(this).attr("rel"));
            _start_slide();
        };

        var _create_indicator = function() {
            indicator = $('<div></div>')
                    .css({
                             width: content_width,
                             height: indicator_height
                         })
                    .appendTo(_this)
                    .addClass("indicator");


            var html = "<a rel='previous' class='previous'>&lt;</a>";
            var previous = $(html).appendTo(indicator);

            for (var i = 0; i < tab_count; i++) {
                html = "<a rel='" + i + "'>" + (i + 1) + "</a>";
                a = $(html).appendTo(indicator);
                a.addClass("clicker");
            }

            html = "<a rel='next' class='next'>&gt;</a>";
            var next = $(html).appendTo(indicator);

            html = "<a rel='pause' class='pause_resume'><img src=' " + settings.img_pause + "'/></a>";
            var pause_resume = $(html).appendTo(indicator);
            $("a.pause_resume>img", indicator).click(_pause_resume_slide);

            $("a.clicker", indicator).click(_clicker_click);

            $(".indicator a.clicker", _this).hover(function() {
                $(this).addClass("highlight");
            }, function() {
                $(this).removeClass("highlight");
            })

            $("a.previous", _this).click(function() {
                _stop_slide();
                tab_index = _previous_index();
                _start_slide();
            });

            $("a.next", _this).click(function() {
                _stop_slide();
                tab_index = _next_index();
                _start_slide();
            });
        };

        var _create_container = function() {
            _this.addClass("wj_slide");

            content_width = _this.innerWidth();
            content_height = _this.innerHeight();
            tab_count = _this.children('div').length;

            $(_this).children('div').wrapAll('<div></div>');
            scroller = _this.children('div').first().css({
                top: indicator_height,
                position:'absolute',
                width: content_width * tab_count,
                height: content_height - indicator_height,
                margin: '0px 0px 0px 0px',
                padding: '0px 0px 0px 0px'

            });

            for (var i = 0; i < tab_count; i++) {
                var selector = "div:nth-child(" + (i + 1) + ")";
                scroller.children(selector).css({
                    width: content_width,
                    height: content_height,
                    left: content_width * i
                }).addClass("tab_content");
            }

        };

        var _init = function() {
            _create_container();
            _create_indicator();
        };

        $(_this).css({
            position: 'relative',
            overflow: 'hidden'
        });
        _init();
        _start();
    };

})(jQuery);

