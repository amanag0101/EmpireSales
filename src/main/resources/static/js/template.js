function toggleSidebar() {
    console.log("working");
    const topnav = document.getElementById("topnav");
    const sidenav = document.getElementById("sidenav");

    if(topnav.style.display != "none") {
        topnav.style.display = "none";
        sidenav.style.display = "block";
    }
    else {
        sidenav.style.display = "none";
        topnav.style.display = "flex";
    }
}