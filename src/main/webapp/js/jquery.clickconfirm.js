;
(function($) {
    $.fn.clickConfirm = function(options) {
        var _this = this;

        if (!this.length)
            return this;

        $.clickConfirm = {
            defaults: {
                position: 'bottom',
                offset: {top: 0, left: 0},
                zIndex: 10000,
                questionText: "Apakah Anda yakin?",
                proceedText: "Ya",
                cancelText: "Batal",
                clickConfirmClass: "click_confirm",
                followLinkOnProceed: true,
                onProceed: function(trigger) {
                },
                onCancel: function(trigger) {
                }
            }
        };
        var params = $.extend($.clickConfirm.defaults, options || {});

        var $confirmBox, $confirmYes, $confirmNo, $arrow,
                $arrowBorder, confirmBoxArrowClass, confirmBoxArrowBorderClass;
        return this.each(function() {
            // unbind first
            $(this).unbind("click");

            var handler = function() {
                // remove
                $($confirmBox).remove();

                // create dialog box
                $confirmYes = $('<button class="' + params.clickConfirmClass + '_proceed">' + params.proceedText + '</button>');
                $confirmNo = $('<button class="' + params.clickConfirmClass + '_cancel">' + params.cancelText + '</button>');

                $confirmBox = $('<div class="' + params.clickConfirmClass + '"><div class="' + params.clickConfirmClass + '_arrow_border"></div><div class="' + params.clickConfirmClass + '_arrow"></div>' + params.questionText + '<br/></div>');
                $arrow = $('div.' + params.clickConfirmClass + '_arrow', $confirmBox);
                $arrowBorder = $('div.' + params.clickConfirmClass + '_arrow_border', $confirmBox);
                $confirmBox.append($confirmYes).append($confirmNo);

                // Append the confirm box to the body. It will not be visible as it is off-screen by default. Positionning will be done at the last time
                $('body').append($confirmBox);

                var trigger = $(this);

                // Make the confirm box appear right where it belongs
                // Calculate absolute positionning depending on the trigger-relative position
                var offset = $(trigger).offset();
                switch (params.position) {
                    case 'top':
                        confirmBoxArrowClass = params.clickConfirmClass + '_bottom';
                        confirmBoxArrowBorderClass = params.clickConfirmClass + '_bottom';

                        $arrow.addClass(confirmBoxArrowClass).css('left', $confirmBox.outerWidth() / 2 - $arrow.outerWidth() / 2);
                        $arrowBorder.addClass(confirmBoxArrowBorderClass).css('left', $confirmBox.outerWidth() / 2 - $arrowBorder.outerWidth() / 2);

                        topOffset = offset.top - $confirmBox.outerHeight() - $arrowBorder.outerHeight() + params.offset.top;
                        leftOffset = offset.left - $confirmBox.outerWidth() / 2 + $(trigger).outerWidth() / 2 + params.offset.left;
                        break;
                    case 'right':
                        confirmBoxArrowClass = params.clickConfirmClass + '_left';
                        confirmBoxArrowBorderClass = params.clickConfirmClass + '_left';

                        $arrow.addClass(confirmBoxArrowClass).css('top', $confirmBox.outerHeight() / 2 - $arrow.outerHeight() / 2);
                        $arrowBorder.addClass(confirmBoxArrowBorderClass).css('top', $confirmBox.outerHeight() / 2 - $arrowBorder.outerHeight() / 2);

                        topOffset = offset.top + $(trigger).outerHeight() / 2 - $confirmBox.outerHeight() / 2 + params.offset.top;
                        leftOffset = offset.left + $(trigger).outerWidth() + $arrowBorder.outerWidth() + params.offset.left;
                        break;
                    case 'bottom':
                        confirmBoxArrowClass = params.clickConfirmClass + '_top';
                        confirmBoxArrowBorderClass = params.clickConfirmClass + '_top';

                        $arrow.addClass(confirmBoxArrowClass).css('left', $confirmBox.outerWidth() / 2 - $arrow.outerWidth() / 2);
                        $arrowBorder.addClass(confirmBoxArrowBorderClass).css('left', $confirmBox.outerWidth() / 2 - $arrowBorder.outerWidth() / 2);

                        topOffset = offset.top + $(trigger).outerHeight() + $arrowBorder.outerHeight() + params.offset.top;
                        leftOffset = offset.left - $confirmBox.outerWidth() / 2 + $(trigger).outerWidth() / 2 + params.offset.left;
                        break;
                    case 'left':
                        confirmBoxArrowClass = params.clickConfirmClass + '_right';
                        confirmBoxArrowBorderClass = params.clickConfirmClass + '_right';

                        $arrow.addClass(confirmBoxArrowClass).css('top', $confirmBox.outerHeight() / 2 - $arrow.outerHeight() / 2);
                        $arrowBorder.addClass(confirmBoxArrowBorderClass).css('top', $confirmBox.outerHeight() / 2 - $arrowBorder.outerHeight() / 2);

                        topOffset = offset.top + $(trigger).outerHeight() / 2 - $confirmBox.outerHeight() / 2 + params.offset.top;
                        leftOffset = offset.left - $confirmBox.outerWidth() - $arrowBorder.outerWidth() + params.offset.left;
                        break;
                }
                var href = $(this).attr("href");
                // Register actions
                $confirmYes.click(function() {
                    if (params.followLinkOnProceed == true) {
                        if (typeof href != "undefined")
                            window.location.href = href;
                    }
                    $(this).closest('div.' + params.clickConfirmClass).remove();
                    params.onProceed(trigger);
                });

                $confirmNo.click(function() {
                    $(this).closest('div.' + params.clickConfirmClass).remove();
                    params.onCancel(trigger);
                });

                var w = $($confirmBox).width();
                var h = $($confirmBox).height();
                $confirmBox.css({
                    opacity: 0,
                    width: 0,
                    height: 0,
                    top: topOffset,
                    left: leftOffset,
                    zIndex: params.zIndex});

                $confirmBox.animate({
                    opacity: 1.0,
                    width: w,
                    height: h
                }, "fast");

                return false;
            };
            $(this).bind("click", handler);
        });
    };
})(jQuery);
