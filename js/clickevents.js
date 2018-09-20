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
    page = id;
    document.getElementById("content").innerHTML = '<object type="text/html" data="html/' + id + '.html" width="100%" height="100%"></object>';
}

function toTop() {
    showPage(page);
}