(    function ($) {
    $.fn.search = function (params) {
        var options = {
            vertical:true,
            horizontal:true
        };
        options = jQuery.extend(options, params);

        var input = $("input[type=text]", this);
        var divInput = $('div.input');
        var width = divInput.width();
        var outerWidth = divInput.parent().width() - (divInput.outerWidth() - width) - 28;
        var submit = $('input[type=text]', this);
        var txt = input.val();

        input.bind('focus',
            function () {
                if (input.val() === txt) {
                    input.val('');
                }
                $(this).animate({color:'#000'}, 300); // text color

                $(this).parent().animate({
                        width:outerWidth + 'px',
                        backgroundColor:'#fff', // background color
                        paddingRight:'43px'
                    }, 300,
                    function () {
                        if (!(input.val() === '' || input.val() === txt)) {
                            submit.fadeIn(300);
                        }
                    }).addClass('focus');
            }).bind('blur',
            function () {
                $(this).animate({color:'#b4bdc4'}, 300); // text color
                $(this).parent().animate({
                        width:width + 'px',
                        backgroundColor:'#e8edf1', // background color
                        paddingRight:'15px'
                    }, 300,
                    function () {
                        if (input.val() === '') {
                            input.val(txt)
                        }
                    }).removeClass('focus');

                submit.fadeOut(100);
            }).keyup(function () {
                if (input.val() === '') {
                    submit.fadeOut(300);
                } else {
                    submit.fadeIn(300);
                }
            });

        )
        (jQuery);
