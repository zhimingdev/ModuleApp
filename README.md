# ModuleViewProject

## 组件化项目 

### 三方库使用

 * Multid
    * 解决在加载APK的时候限制了class.dex包含的Java方法总数不能超过65535问题
    * 参考链接: https://www.cnblogs.com/butterfly-clover/p/5161150.html


* Arouter
    * 是ARouter是阿里巴巴开源的Android平台中对页面、服务提供路由功能的中间件，提倡的是简单且够用
    * 支持直接解析标准URL进行跳转，并自动注入参数到目标页面中
    * 支持多模块工程使用
    * 支持添加多个拦截器，自定义拦截顺序
    * 支持依赖注入，可单独作为依赖注入框架使用
    * 支持InstantRun
    * 支持MultiDex(Google方案)
    * 映射关系按组分类、多级管理，按需初始化
    * 支持用户指定全局降级与局部降级策略
    * 页面、拦截器、服务等组件均自动注册到框架
    * 支持多种方式配置转场动画
    * 支持获取Fragment
    * 参考链接: https://blog.csdn.net/zhaoyanjun6/article/details/76165252


* Bbutterknife
    * 通过注解的方式来对Android View进行绑定取代findViewById
    * 参考链接: https://www.jianshu.com/p/e7b0eeb9fa82


* Retrofit2和Rxjava2
    * Retrofit 负责 请求的数据 和 请求的结果，使用 接口的方式 呈现，OkHttp 负责请求的过程，RxJava 负责异步，各种线程之间的切换。
    * 参考链接: https://www.jianshu.com/p/0ad99e598dba


* SmartRefreshLayou
    * 支持多点触摸
    * 支持淘宝二楼和二级刷新
    * 支持嵌套多层的视图结构 Layout (LinearLayout,FrameLayout...)
    * 支持所有的 View（AbsListView、RecyclerView、WebView....View）
    * 支持自定义并且已经集成了很多炫酷的 Header 和 Footer.
    * 支持和ListView的无缝同步滚动 和 CoordinatorLayout 的嵌套滚动 .
    * 支持自动刷新、自动上拉加载（自动检测列表惯性滚动到底部，而不用手动上拉）.
    * 支持自定义回弹动画的插值器，实现各种炫酷的动画效果.
    * 支持设置主题来适配任何场景的App，不会出现炫酷但很尴尬的情况.
    * 支持设多种滑动方式：平移、拉伸、背后固定、顶层固定、全屏
    * 支持所有可滚动视图的越界回弹
    * 参考链接: https://github.com/scwang90/SmartRefreshLayout

* Photoview
    * 开箱即用的缩放，使用多点触控和双击。
    * 滚动，滚动平滑。
    * 在滚动父级（例如ViewPager）中使用时，可以正常工作。
    * 允许在显示的Matrix更改时通知应用程序。当您需要根据当前缩放/滚动位置更新UI时非常有用。
    * 允许在用户点击照片时通知应用程序。
    * 参考链接: https://github.com/chrisbanes/PhotoView#features


*  Glide
    * Glide和Picasso非常相似,Glide更易用，因为Glide的with方法不光接受Context，还接受Activity 和 Fragment，Context会自动的从他们获取,
    * Glide默认的Bitmap格式是RGB_565 ，比ARGB_8888格式的内存开销要小一半.
    * 参考链接: https://blog.csdn.net/guolin_blog/article/details/53759439


* BaseRecyclerViewAdapterHelper
    * 优化Adapter代码（减少百分之70%代码）
    * 添加点击item点击、长按事件、以及item子控件的点击事件
    * 添加加载动画（一行代码轻松切换5种默认动画）
    * 添加头部、尾部、下拉刷新、上拉加载（感觉又回到ListView时代）
    * 设置自定义的加载更多布局
    * 添加分组（随心定义分组头部）
    * 自定义不同的item类型（简单配置、无需重写额外方法）
    * 设置空布局（比Listview的setEmptyView还要好用！）
    * 添加拖拽item
    * 参考链接: https://www.jianshu.com/p/b343fcff51b0


* Eventbus
    * EventBus是一个Android事件发布/订阅框架，通过解耦发布者和订阅者简化Android事件传递
    * 参考链接: https://www.jianshu.com/p/a040955194fc


* MZBannerView
    * 图片轮播控件,支持多种模式切换：普通ViewPager使用，普通Banner使用，仿魅族Banner使用。
    * 参考链接: https://www.jianshu.com/p/653680cfe877


* AndroidUtilCode
    * 相当强大的工具类,包含开发常用的该工具类,节省开发时间,例如:加解密,正则缓存等等
    * 参考链接: https://blog.csdn.net/qq_33847549/article/details/73840901