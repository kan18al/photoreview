const ratings = document.querySelectorAll(".rating__active");

for (const rating of ratings) {
    let rat = rating.getAttribute("rate");
    rating.style.width = (rat / 5 * 100) + "%";
    rating.closest(".rating").querySelector(".rating__value").innerHTML = parseFloat(rat).toFixed(1);
}
