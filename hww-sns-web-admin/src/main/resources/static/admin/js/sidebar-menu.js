var pathname = window.location.pathname;
$("li a").each(function() {
	var href = $(this).attr("href");
	if(pathname == href){
		$(this).parents("ul").parent("li").addClass("open");
		$(this).parent("li").addClass("active");
	}else{
		//$(this).parents("ul").parent("li").removeClass("active");
		$(this).parent("li").removeClass("active");
	}
});