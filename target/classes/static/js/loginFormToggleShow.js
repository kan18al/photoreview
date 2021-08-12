$("#loginBtn").click(function () {
    if (localStorage.getItem("userEmail") == null) {
        $("#loginForm").removeClass("hide")
        $(".div_popup_overlay").fadeTo("slow", 0.8).css({'display':'block'})
    }

});

$(".div_popup_overlay").click(function (){
    $(".div_popup_overlay").css({'display':'none'})
    $("#loginForm").addClass("hide")
});