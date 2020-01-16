//适配窗口
function resizeViewport() {

    // 初始化为父级的100%;
    var width = $(".books").width(),
        height = $(".books").height(),
        options = $('.flipbook').turn('options');

    $('.flipbook').removeClass('animated');

    $('.flipbook-viewport').css({
        width: width,
        height: height
    }).
    zoom('resize');


    if ($('.flipbook').turn('zoom')==1) {
        // 缩小时执行
        var bound = calculateBound({
            width: options.width,
            height: options.height,
            //拿到最小值
            boundWidth: Math.min(options.width, width),
            boundHeight: Math.min(options.height, height)
        });

        if (bound.width%2!==0)
            bound.width-=1;


        if (bound.width!=$('.flipbook').width() || bound.height!=$('.flipbook').height()) {

            $('.flipbook').turn('size', bound.width, bound.height);

            if ($('.flipbook').turn('page')==1)
                $('.flipbook').turn('peel', 'br');

        }

        $('.flipbook').css({top: -bound.height/2, left: -bound.width/2});
    }

    var bookOffset = $('.flipbook').offset();

    if (bookOffset.top<$('.made').height())
        $('.made').hide();
    else
        $('.made').show();

    $('.flipbook').addClass('animated');

}
function calculateBound(d) {

    var bound = {width: d.width, height: d.height};

    if (bound.width>d.boundWidth || bound.height>d.boundHeight) {

        var rel = bound.width/bound.height;

        if (d.boundWidth/rel>d.boundHeight && d.boundHeight*rel<=d.boundWidth) {

            bound.width = Math.round(d.boundHeight*rel);
            bound.height = d.boundHeight;

        } else {

            bound.width = d.boundWidth;
            bound.height = Math.round(d.boundWidth/rel);

        }
    }

    return bound;
}


