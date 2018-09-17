var wallpaperIndex = 5;
var page = null;
function changeWallpaper() {
    if (wallpaperIndex == 6) {
        wallpaperIndex = 1;
    } else {
        wallpaperIndex ++;
    }
    document.getElementById('bodyE').style.backgroundImage = 'url(img/' + wallpaperIndex + '-min.jpg)';
}
function toTop() {
    if(page == 'JDBC'){
        showJDBC();
    } else if (page == 'J01'){
        showJavaSE01();
    } else if (page == 'J02'){
        showJavaSE02();
    } else if (page == 'HTML'){
        showHTMLCSS();
    } else if (page == 'JS'){
        showJavaScript();
    } else if (page == 'LINUX'){
        showLinux();
    } else if (page == 'MYSQL'){
        showMySQL();
    } else if (page == 'MD'){
        showMarkdown();
    } else if (page == 'IDES'){
        showIDEs();
    }
}
function showJDBC() {
	page = 'JDBC';
	document.getElementById("content").innerHTML = '<object type="text/html" data="html/JDBC.html" width="100%" height="100%"></object>';
};
function showJavaSE01() {
	page = 'J01';
	document.getElementById("content").innerHTML = '<object type="text/html" data="html/JavaSE-01.html" width="100%" height="100%"></object>';
}
function showJavaSE02() {
	page = 'J02';
	document.getElementById("content").innerHTML = '<object type="text/html" data="html/JavaSE-02.html" width="100%" height="100%"></object>';
}
function showHTMLCSS() {
	page = 'HTML';
	document.getElementById("content").innerHTML = '<object type="text/html" data="html/HTML&CSS笔记.html" width="100%" height="100%"></object>';
}
function showJavaScript() {
	page = 'JS';
	document.getElementById("content").innerHTML = '<object type="text/html" data="html/JavaScript.html" width="100%" height="100%"></object>';
}
function showLinux() {
	page = 'LINUX';
	document.getElementById("content").innerHTML = '<object type="text/html" data="html/Linux笔记.html" width="100%" height="100%"></object>';
}
function showMySQL() {
	page = 'MYSQL';
	document.getElementById("content").innerHTML = '<object type="text/html" data="html/MySQL.html" width="100%" height="100%"></object>';
}
function showMarkdown() {
	page = 'MD';
	document.getElementById("content").innerHTML = '<object type="text/html" data="html/MarkdownLearning.html" width="100%" height="100%"></object>';
}
function showIDEs() {
	page = 'IDES';
	document.getElementById("content").innerHTML = '<object type="text/html" data="html/部分软件常用操作.html" width="100%" height="100%"></object>';
}