1.5.16
* 加了GameActivity等三个Activity(完全没写完只是有个大概)
* 加了GameView
* ImageManger感觉能用了，需要构造的时候传一个context进去，在Activity里用一般就是this
* 需要用Activity代替所有的界面，每个Activity对应一个xml用来布局


* 感觉要做的是
  * 把音乐播放用MediaPlay实现
  * GameView是默认的游戏界面，需要再写EasyGameView等，基本按照GameView写，绘图的话在draw里用canvas画图（不会）
  * 画不同Activity的布局，加交互，我的想法是MainActivity里是主菜单，里面可以选择开始游戏登录排行榜之类的，点开始游戏进入gameactivity
  * 鼠标控制英雄机机没用了，要重写英雄机控制
  * 暂时不知道了，太多了，好痛苦

2. 5.17
* 添加了图片绘制（背景 子弹 道具 飞机等）
 
3. 5.18
* 改了图片绘制的bug
* 添加了Main Activity的页面