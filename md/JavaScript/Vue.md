# Vue.js

[TOC]

## 1. Vue.js 概述

### 1.1 MVVM 模式概述

- MVVM（Model-View-ViewModel），是 MVC 的一个改进版，主要达到分离模型和视图/业务的目的。

### 1.2 Vue.js 概述

- Vue 是一个构建数据驱动的 web 界面的渐进式框架。
- Vue 专注于 View 层，提供了 MVVM 风格的双向数据绑定的 JavaScript 库。
- Vue 的 ViewModel 负责连接 View 和 Model，保证视图和数据的一致性，让前端开发更加高效、便捷。



## 2. Vue.js 入门

- Html 中引入 Vue.js 库。

  - https://vuejs.org/js/vue.min.js

- 在文档加载完处新建 Vue 对象，绑定到某个 dom。

- 示例：

  ```html
  <!DOCTYPE html>
  <html>
      <head>
          <title>Test</title>
          <script src="https://cdn.staticfile.org/vue/2.2.2/vue.min.js"></script>
          <script>
              window.onload = function() {
                  let vm = new Vue({
                      el: '#test',
                      data: {
                          message: 'Hello Vue'
                      },
                      methods: {
                          sayHello: function() {
                              alert('Hello');
                          }
                      }
                  });
              }
          </script>
      </head>
      <body>
          <div id="test">
              {{message}}
              <button v-on:click="sayHello">click</button>
          </div>
      </body>
  </html>
  ```



## 3. Vue.js 插值表达式

### 3.1 概述

- Vue 将数据绑定到 View 的形式就是使用"Mustache"（双大括号）语法。
- 双大括号中可以写任意 js 表达式。

### 3.2 注意事项

- 双大括号中只能写“表达式”，而不能写完整的 js 语句。
- "Mustache"表达式不能作用于 html 特性，例如设置标签的属性。这种需求下只能使用指令。
- 基本任何绑定的数据对象上的属性值发生了改变，插值处的内容都会更新。不过也有例外，例如绑定的数据对象调用了 Object.freeze() 。

### 3.3 实例

```javascript
{{num + 1}}
{{flag ? 'pass' : 'fail'}}
```



## 4. Vue.js 指令

### 4.1 v-on

监听 dom 事件，在触发时执行一些 js 代码（特指已在 vue 对象中定义的函数）。

- 示例：

  ```html
  <div id='container'>
      <button v-on:click="myFunc('hello')">say hello</button>
  </div>
  <script>
      new Vue({
          el: '#container',
          data: {},
          methods: {
              myFunc: function(toSay) {
                  alert(toSay);
              }
          }
      })
  </script>
  ```

- 其他说明：

  - v-on 可以使用事件修饰符来处理 dom 事件细节，例如终止事件等。可使用的修饰符如下：

    - `.stop`：event.preventDefault()
    - `.prevent`：event.stopPropagation()
    - `.capture`
    - `.self`
    - `.once`

    示例：

    ```html
    <form @submit.prevent action='https://baidu.com'></form>
    <a @click.stop href='https://baidu.com'></a>
    ```

  - v-on 可以使用按键修饰符来监听某个特定按键。可以使用的修饰符如下：

    - `.enter`
    - `.delete`
    - `.tab`
    - `.esc`
    - `.space`
    - `.up`
    - `.down`
    - `.left`
    - `.right`
    - `.ctrl`
    - `.alt`
    - `.shift`
    - `.meta`

    示例：

    ```html
    
    ```

### 4.2





## 5. Vue.js AJAX





## 6. Vue 生命周期与钩子

