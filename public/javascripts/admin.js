(function($) {

  $(function() {

    $('.jsconfirm').click(function() {
      return confirm($(this).text() + "?");
    });
  });

})(jQuery);
