/*
 * vertical news ticker
 * Version: 1.4
 * Tadas Juozapaitis ( kasp3rito@gmail.com )
 * http://www.jugbit.com/jquery-vticker-vertical-news-ticker/
 \ */
(function($) {
    $.fn.vTicker = function(options) {
        var _this = $(this);
        var handle_func = null;
        var defaults = {
            speed: "slow",
            pause: 5000,
            showItems: 3,
            animation: '',
            mousePause: true,
            isPaused: false,
            direction: 'up',
            height: 0
        };

        var options = $.extend(defaults, options);

        var moveUp = function() {
            if (!options.isPaused) {
                var obj = _this.children('ul');
                var height = obj.children('li:first').height();

                obj.animate({top: (-1 * height) + 'px'}, "slow", function() {
                    var clone = obj.children('li:first').clone(true);
                    $(this).children('li:first').remove();
                    $(this).css('top', '0px');
                    clone.appendTo(obj);
                    schedule_animate();
                });
            } else {
                schedule_animate();
            }
        };

        var moveDown = function() {
            if (!options.isPaused) {
                var obj = _this.children('ul');
                var last_li = obj.children('li:last');
                var height = last_li.height();
                var clone = last_li.clone(true);
                obj.css({top: (-1 * height) + "px"});
                clone.prependTo(obj);
                last_li.remove();
                obj.animate({top: "0px"}, "slow", function() {
                    schedule_animate();
                });
            } else {
                schedule_animate();
            }
        };

        var schedule_animate = function () {
            handle_func = setTimeout(function() {
                if (options.direction == "down")
                    moveDown();
                else
                    moveUp();
            }, options.pause);
        };


        var mouse_enter_handler = function () {
            options.isPaused = true;
            clearTimeout(handle_func);
            var obj = _this.children('ul');
            obj.stop();
        };

        var mouse_leave_handler = function () {
            options.isPaused = false;
            if (options.direction == "down")
                moveDown();
            else
                moveUp();
        };

        return this.each(function() {
            var maxHeight = 0;
            _this.css({overflow: 'hidden', position: 'relative'});
            _this.children('ul').css({position: 'absolute', margin: 0, padding: 0});
            _this.children('li').css({margin: 0, padding: 0});

            if (options.mousePause) {
                _this.bind("mouseenter", mouse_enter_handler);
                _this.bind("mouseleave", mouse_leave_handler);
            }
            schedule_animate();
        });
    };
})(jQuery);