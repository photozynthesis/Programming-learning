var wallpaperIndex = 5;
var page = null;

function changeWallpaper() {
    if (wallpaperIndex == 6) {
        wallpaperIndex = 1;
    } else {
        wallpaperIndex++;
    }
    document.getElementById('bodyE').style.backgroundImage = 'url(img/' + wallpaperIndex + '-min.jpg)';
}

function showPage(id) {
    if (id == page) {
        document.getElementById("content").innerHTML = '<object type="text/html" data="html/' + id + '.html" width="100%" height="100%"></object>';
        return;
    }
    if (page != null) {
        document.getElementById(page).style.backgroundColor = null;
        document.getElementById(page).getElementsByTagName("a")[0].style.color = null;
    }
    page = id;
    document.getElementById(id).style.backgroundColor = '#333333';
    document.getElementById(id).getElementsByTagName("a")[0].style.color = '#dddddd';
    document.getElementById("content").innerHTML = '<object type="text/html" data="html/' + id + '.html" width="100%" height="100%"></object>';
}

function toTop() {
    showPage(page);
}