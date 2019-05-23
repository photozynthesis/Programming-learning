let wallpaperIndex = 5;
let page = null;

// 更换壁纸
function changeWallpaper() {
    if (wallpaperIndex === 6) {
        wallpaperIndex = 1;
    } else {
        wallpaperIndex++;
    }
    document.getElementById('bodyE').style.backgroundImage = 'url(img/' + wallpaperIndex + '-min.jpg)';
}

// 点击目录按钮后执行的方法
function showPage(id) {
    if (id === page) {
        // document.getElementById("content").innerHTML = '<object type="text/html" data="html/' + id + '.html" width="100%" height="100%"></object>';
        // $('#content').load('html/' + id + '.html');
        document.getElementById('iframe-content').src = 'html/' + id + '.html';
        return;
    }
    if (page !== null) {
        document.getElementById(page).style.backgroundColor = null;
        document.getElementById(page).getElementsByTagName("a")[0].style.color = null;
    }
    page = id;
    document.getElementById(id).style.backgroundColor = '#333333';
    document.getElementById(id).getElementsByTagName("a")[0].style.color = '#dddddd';
    // document.getElementById("content").innerHTML = '<object type="text/html" data="html/' + id + '.html" width="100%" height="100%"></object>';
    document.getElementById('iframe-content').src = 'html/' + id + '.html';
    // $('#content').load('html/' + id + '.html');
}

// 回到当前页面顶部的方法
function toTop() {
    showPage(page);
}

// 当鼠标移动到目录项上执行的方法（改变目录项的颜色）



// 当鼠标移出目录项后止执行的方法（恢复目录项颜色）



// 页面加载完毕
window.onload = function() {

    // 给所有一级目录项按钮绑定点击弹出菜单事件
    let navItemTitles = document.querySelectorAll('.navItem-title');
    let subLists = document.querySelectorAll('.navItem-subList');
    let arrows = document.querySelectorAll('.navItem-title-arrow');
    for (let i = 0; i < navItemTitles.length; i++) {
        navItemTitles[i]['expand'] = 0;
        navItemTitles[i].onclick = function() {
            if (navItemTitles[i]['expand'] === 0) {
                subLists[i].style.height = subLists[i].getElementsByTagName('li').length * 40 + 'px';
                arrows[i].style.transform = 'rotate(90deg)';
                navItemTitles[i]['expand'] = 1;
            } else {
                subLists[i].style.height = '0px';
                arrows[i].style.transform = null;
                navItemTitles[i]['expand'] = 0;
            }
            
            // alert(subLists[i].getElementsByTagName('li').length);
        }
    }
    

}