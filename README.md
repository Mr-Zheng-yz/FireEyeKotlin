# FireEyeKotlin
初次使用Kotlin开发，语法生疏，望各路大神给出意见。

FireEyeKoglin踩坑日记
</br>
</br>
----

<font><b>截图</b></font>

话不多说，先上图

![](https://i.imgur.com/tt6TNrB.png)

![](https://i.imgur.com/X4DyLsk.png)

<font><b>###kotlin与databinding冲突解决</b></font>


近日来公司任务不多，为了不被时代的洪流落下，抽空学习了一下Kotlin。看完一些基础语法后，在GitHub上clone了一个基础项目，运行后发现bug满满。在借鉴的同时也想自己撸一个Kotlin项目。创建，编译全没问题，当导入databinding时，却报了
> Error:Execution failed for task ':app:compileDebugKotlin'. > Compilation error. See log for more details

![](https://i.imgur.com/Yrg6e8s.jpg)

Google一番发现是Kotlin与databinding的冲突，还需要依赖kpat：
打开build。gradle添加以下两句代码：

1.dependanceies节点下添加依赖：

```
kapt 'com.android.databinding:compiler:3.1.4'
```

2.添加

```
applyplugin:'kotlin-kapt'
```

![](https://i.imgur.com/GjgZ1iG.png)

问题解决；

[参考全球最大同性交流网站](https://stackoverflow.com/questions/44035504/how-to-use-data-binding-and-kotlin-in-android-studio-3-0-0)

注意！注意！注意！kapt的依赖一定要和gradle版本对应，因为这个问题迷茫了一下午，最后经朋友提醒才发现。

![](https://i.imgur.com/ofJx4BF.png)

    
我本地gradle版本只有这几个，然而kpat却导入了3.1.4的，最后却报了databinding的错误。
最后索性把kpat与gradle版本抽取了出来，如图：
    
![](https://i.imgur.com/qtJUsKY.png)

gradle使用：

![](https://i.imgur.com/QDFWRK0.png)
 
kapt依赖：

![](https://i.imgur.com/UKKhrNg.png)
   
问题解决。
   
<font><b>###补充</b></font>
 
在之后的开发中又遇到了databinding与RoomDatabase的坑， 而且由于databinding一旦有错，全局报错的“团结”特性，浪费了一整个下午才排查出来，最后只好将xml中的data节点删掉了，在代码中控制布局显示。
    
借此机会，结合我对databinding的实际使用情况，抒发一下心中感情：
一开始接触databinding，觉得这是一个非常强大的插件，各种高阶用法，不亦乐乎。不过一旦使用不当，还是非常坑的。
    
整理一下具体优缺点：

- **缺点**：
	1. 错误信息不明显。
	2. BindingView生成不及时，每创建一个以layout为根节点的xml布局，插件会自动生成布局关联的BindingView布局，不过有时你写好这个xml很久了，但是还会有引用不到的情况。这里根据我的使用经验，终于知道了一些简单的原理：一是书写xml布局时，项目中一定不能有编译错误。其二，书写xml布局后，childView至少要声明一个id。自从我遵从了这两点后，大部分情况都能引用到对应的BindingView类。

- **优点**：
	1. xml布局中data节点的使用，增强了xml界面的功能，这点是我非常喜欢，也是舍不得放弃databinding的原因。
	2. 单向绑定和双向绑定
	3. 自定义属性
    
虽然缺点很坑，但是这些优点短时间内kotlin extensions还是取代不了的，也是这个项目使用databinding和kotlin结合的原因。
    
**ps**：*2018的GDD之后，由于大会对kotlin的场次较少，有人说kotlin已经没落了。但我不这样认为，一门语言除了语法与基本的使用方式之外，还有什么需要介绍的呢，如果只因为场次安排就说明一门语言的没落，真是不敢苟同。个人使用kotlin的体验非常好，虽然踩了很多坑，但是踩坑过来之后代码写起来还是非常流畅的，而且一些特性也是java所没有的，Kotlin的兴衰，拭目以待。*



**###Android架构组件依赖**

1.添加Google Maven仓库

```

	allprojects{
		repositories{
		google()
		jcenter()
		}
	}
``` 

2.添加架构组件

	//Room数据库
	implementation"android.arch.persistence.room:runtime:1.1.1"
	annotationProcessor"android.arch.persistence.room:compiler:1.1.1"
	//ViewModelandLiveData
	implementation"android.arch.lifecycle:extensions:1.1.1"
	implementation"android.arch.paging:runtime:1.0.1"
	//Rxjava,ReactiveStreamssupportforLiveData
	implementation"android.arch.persistence.room:rxjava2:1.1.1"
	implementation"android.arch.lifecycle:reactivestreams:1.1.1"

参考[https://www.jianshu.com/p/5457d5530e40](https://www.jianshu.com/p/5457d5530e40)

**###Gilde的placeholder()导致图片变形问题**

Glide默认开启的crossFade动画导致TransitionDrawable绘制异常

其根本原因就是placeholder图片和要加载显示图片的宽高不一致，导致了TransitionDrawable无法很好地处理不同宽高比的过度问题

- **解决**：

	*  1.dontAnimation()
	*  2.取消placeholder()
	*  3.placeholder()图片宽高小于要加载的图片
	*  4.animate（）方法自己写动画，自己修复TransitionDrawable问题

要了解更多请参考[http://www.cnblogs.com/ldq2016/p/7249892.html](http://www.cnblogs.com/ldq2016/p/7249892.html)

**###感知生命周期组件化开发**

[传送门](https://blog.csdn.net/guiying712/article/details/78474177#app%E5%BC%80%E5%8F%91%E8%80%85%E9%9D%A2%E4%B8%B4%E7%9A%84%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)

**###Kotlin特性**
 
**####空安全**

- 声明变量

在kotlin中由于空指针安全，声明变量时一般直接赋值：
```
val a = 10 //直接声明时变量类型可以不写
```

或

定义一个可空类型的变量的格式为：```修饰符 变量名 ： 类型? = 值```
	
```
var b : String? = null
```
	
如果要延迟加载，要用到**lateinit**修饰符,格式 ```var lateinit 变量名:变量类型```，如：

```
var lateinit c:Int
```

使用lateinit一定要在使用之前即时赋值，否则还是会有空指针错误

- 判断空
	1. 直接使用if...else...判断
	2. 使用符号?.判断
		- 该符号的用法为：可空类型变量?.属性/方法。如果可空类型变量为null时，返回null
		- 这种用法大量用于链式操作的用法中，能有效避免空引用异常（NullPointException），因为只要链式其中的一个为null，则整个表达式都为null
		- *注意：当一个函数/方法有返回值时，如果方法中的代码使用?.去返回一个值，那么方法的返回值的类型后面也要加上?符号*
		</br>

	3. let操作符
		- let操作符的作用：当时用符号?.验证的时候忽略掉null
		- let的用法：变量?.let{ ... }

- Evils操作符

	1. `?:`:表示在判断一个可空类型时，会返回一个自己我们自己设定好的默认值（类似三元运算符）
	2. `!!`:表示判断一个可空类型是，直接抛出空异常
	3. `as?`:表示安全类型转换

 
**###扩展函数**

Kotlin支持扩展一个类的新功能而无需继承该类或使用像装饰着这样的设计模式。
格式：```fun 被扩展类名.扩展方法名(参数名:参数类型)：返回值类型{}```

举个例子，Toast是比较常用的UI提示方式，需要Context上下文支持，这时就可以扩展Context来直接让Context来支持Toast，并且无需继承，上代码：


    //拓展Context
    fun Context.showToast(message:String):Toast{
    var toast: Toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
    return toast
    }

- 使用

直接在Activity中使用，或者`context.shotToast()`：
    showToast("显示Toast了。。。")


**####when函数**

相当于switch（）的增强版，通过一个例子来了解用法：

    when (position) {
    	0 -> {
    		showToast("111")
    	}
    	1 -> {
    		showToast("222")
    	}
    }


**###函数指定参数**

fun方法中传入的参数可以设置默认值，再也不用java的方法重载了。

- 为传入参数设置默认值：

```

	@JvmStatic
	fun e(tag: String = TAG, msg: String) {
	Log.e(tag, String.format(s, msg))
	}
```

如上述代码，函数声明时，直接为tag设置了默认值，再使用时，既可以传入tag和msg两个值（传入参数会覆盖默认参数），也可以只传msg，如图：

![](https://i.imgur.com/Pbr31TI.png)


**###Databinding错误**
> 使用databinding其实有个缺点，就是会遇到一些编译错误，而AS不能很好的定位到错误的位置，这对于刚开始使用databinding的开发者来说是一个比较郁闷的事。那么我在此把我自己在开发中遇到的各种编译问题的解决方法分享给大家，希望这对你会有所帮助。

摘自[传送门](https://github.com/goldze/MVVMHabit#about)
内容有点多，就将我关心的摘了下来，对databinding错误总结的非常详细，如果让我早点看到肯定能省不少时间了...

####**绑定错误**

绑定错误是一个很常见的错误，基本都会犯。比如TextView的 android:text="" ，本来要绑定的是一个String类型，结果你不小心，可能绑了一个Boolean上去，或者变量名写错了，这时候编辑器不会报红错，而是在点编译运行的时候，在AS的Messages中会出现错误提示，如下图：

![](https://i.imgur.com/ngynONj.png)

**解决方法**：把错误提示拉到最下面 (上面的提示找不到BR类这个不要管它)，看最后一个错误 ，这里会提示是哪个xml出了错，并且会定位到行数，按照提示找到对应位置，即可解决该编译错误的问题。

***注意***： 行数要+1，意思是上面报出第33行错误，实际是第34行错误，AS定位的不准确 (这可能是它的一个bug)

####xml导包错误

在xml中需要导入ViewModel或者一些业务相关的类，假如在xml中导错了类，那一行则会报红，但是res/layout却没有错误提示，有一种场景，非常特殊，不容易找出错误位置。就是你写了一个xml，导入了一个类，比如XXXUtils，后来因为业务需求，把那个XXXUtils删了，这时候res/layout下不会出现任何错误，而你在编译运行的时候，才会出现错误日志。苦逼的是，不会像上面那样提示哪一个xml文件，哪一行出错了，最后一个错误只是一大片的报错报告。如下图：

![](https://i.imgur.com/oSIlfDY.png)

**解决方法**：同样找到最后一个错误提示，找到Cannot resolve type for xxx这一句 (xxx是类名)，然后使用全局搜索 (Ctrl+H) ，搜索哪个xml引用了这个类，跟踪点击进去，在xml就会出现一个红错，即可解析该编译错误的问题。

####build错误

构建多module工程时，如出现【4.1.1、绑定错误】，且你能确定这个绑定是没有问题的，经过修改后出现下图错误：

![](https://i.imgur.com/oJKd89C.jpg)

**解决方法**： 这种是databinding比较大的坑，清理、重构和删build都不起作用，网上很难找到方法。经过试验，解决办法是手动创建异常中提到的文件夹，或者拷贝上一个没有报错的版本中对应的文件夹，可以解决这个异常
 
**####总结**

kotlin语法糖还是非常多的，比如foreach{}、drop等等，熟练使用这些操作符，一定可以大大减少代码量，更多特性就不多说了，一起探索吧。

![](https://i.imgur.com/luyaCp2.jpg)

 
 
 
 
 
 
 
