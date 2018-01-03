/* Smooth Scrolling */
$(function() {
    $('a[href*="#"]').click(function() {
        $('html, body').animate({
            scrollTop: $(this.hash).offset().top - $('#header').height()
        }, 1000);
        return false;
    });
});
