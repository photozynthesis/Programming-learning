let wallpaperIndex = 5;
// 更换壁纸
function changeWallpaper() {
    if (wallpaperIndex === 6) {
        wallpaperIndex = 1;
    } else {
        wallpaperIndex++;
    }
    document.getElementById('bodyE').style.backgroundImage = 'url(img/' + wallpaperIndex + '-min.jpg)';
}

let lastActiveNode = null;
// 点击目录按钮后执行的方法
function showPage(element) {
    // 若当前点击元素与上次点击元素相同不改变颜色，否则清除上个元素的颜色并设置新元素的颜色
    if (element !== lastActiveNode) {
        if (lastActiveNode !== null) {
            // 清除上个元素的颜色
            lastActiveNode.style.backgroundColor = null;
            lastActiveNode.style.color = null;
        }
        // 改变颜色
        element.style.backgroundColor = '#888888';
        element.style.color = '#eeeeee';
    }
    // 显示内容
    document.getElementById('iframe-content').src = 'html/' + element.id + '.html';
    // 设置 lastActive
    lastActiveNode = element;
}

// 回到当前页面顶部的方法
function toTop() {
    showPage(lastActiveNode);
}

// 页面加载完毕
window.onload = function() {

    // 启用 FastClick，降低移动端点击延迟
    document.addEventListener('DOMContentLoaded', function() {
        FastClick.attach(document.body);
    }, false);

    // 给所有一级目录项按钮绑定点击弹出菜单事件
    let navItemTitles = document.querySelectorAll('.navItem-title');
    let subLists = document.querySelectorAll('.navItem-subList');
    let arrows = document.querySelectorAll('.navItem-title-arrow');
    for (let i = 0; i < navItemTitles.length; i++) {
        navItemTitles[i]['expand'] = 0;
        navItemTitles[i].onclick = function() {
            if (navItemTitles[i]['expand'] === 0) {
            	let subListLength = subLists[i].getElementsByTagName('li').length;
                // subLists[i].style.height = (subListLength * 40 - subListLength + 1) + 'px';
                subLists[i].style.height = subListLength * 40 + 'px';
                // navItemTitles[i].style.backgroundColor = '#bbbbbb';
                arrows[i].style.transform = 'rotate(90deg)';
                navItemTitles[i]['expand'] = 1;
            } else {
                subLists[i].style.height = '0px';
                // navItemTitles[i].style.backgroundColor = null;
                arrows[i].style.transform = null;
                navItemTitles[i]['expand'] = 0;
            }
            
            // alert(subLists[i].getElementsByTagName('li').length);
        }
    }

    // 给所有文章条目按钮绑定点击显示文章事件
    let subListItems = document.querySelectorAll('.navItem-subList-item');
    // let lastActiveNode = null;
    for(let i = 0; i < subListItems.length; i ++) {
        subListItems[i].onclick = function() {
            showPage(this);
        }
    }

    // 给背景按钮绑定事件
    document.getElementById('navButton-changeImage').onclick = changeWallpaper;

    // 给回到顶部按钮绑定事件
    document.getElementById('navButton-toTop').onclick = toTop;

    

}