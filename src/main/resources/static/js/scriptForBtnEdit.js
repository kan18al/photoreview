$(function(){
    if (localStorage.getItem("role") == "moderator") {
        $("#btnEdit").css("display", "block");
    } else {
        $("#btnEdit").css("display", "none");
    }
});

