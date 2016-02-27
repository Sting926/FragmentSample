#Fragment 总结
##创建fragment

创建fragment必须要继承android.app.Fragment，还有下面几种除了Fragment基类的可扩展子类可以继承。

* DialogFragment
显示浮动对话框。使用此类创建对话框可有效地替代使用 Activity 类中的对话框帮助程序方法，因为您可以将片段对话框纳入由 Activity 管理的片段返回栈，从而使用户能够返回清除的片段。
* ListFragment
显示由适配器（如 SimpleCursorAdapter）管理的一系列项目，类似于 ListActivity。它提供了几种管理列表视图的方法，如用于处理点击事件的 onListItemClick() 回调。
* PreferenceFragment
以列表形式显示 Preference 对象的层次结构，类似于 PreferenceActivity。这在为您的应用创建“设置” Activity 时很有用处。

>如果使用Android3.0以下的版本，需要引入support.v4的包。

##Fragment的生命周期
![lifecycle](https://github.com/Sting926/FragmentSample/blob/master/screenshots/fragment_lifecycle.png)
![lifecycle](http://i.imgur.com/fjGYjRN.png)

通常，我们的Fragment至少应实现以下生命周期方法：

* onCreate()
系统会在创建Fragment时调用此方法。我们应该在这个方法里初始化那些在Fragment暂停或停止后恢复时必需保留的Fragment组件。
* onCreateView()
系统会在Fragment首次绘制其用户界面时调用此方法。 要想为您的Fragment绘制 UI，您从此方法中返回的 View 必须是Fragment布局的根视图。如果Fragment未提供 UI，可以返回 null。
* onPause()
系统将此方法作为用户离开Fragment的第一个信号（但并不总是意味着此Fragment会被销毁）进行调用。 通常应该在此方法内保存一些将来需要恢复的数据（因为用户可能不会返回）。

其它生命周期方法的作用在下面会讲到。

##添加到Activity
###使用布局
在 Activity 的布局文件内声明Fragment
可以将Fragment当作视图来为其指定布局属性。 例如：
~~~
<fragment
    android:id="@+id/fragment"
    android:name="com.xfdsj.fragment.FragmentOne"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"
    tools:layout="@layout/fragment_main"/>
~~~
`<fragment>` 中的 `android:name` 属性指定要在布局中实例化的 Fragment 类。

当系统创建此 Activity 布局时，会实例化在布局中指定的每个Fragment，并为每个片段调用 `onCreateView()` 方法，以检索每个Fragment的布局。系统会直接插入Fragment返回的 `View` 来替代 `<fragment>` 元素。
>每个Fragment都需要一个唯一的标识符，重启 Activity 时，系统可以使用该标识符来恢复Fragment（也可以使用该标识符来捕获Fragment以执行某些事务，如将其删除）。 可以通过三种方式为片段提供 ID：

>1. 为 android:id 属性提供唯一 ID
>2. 为 android:tag 属性提供唯一字符串
>3. 如果您未给以上两个属性提供值，系统会使用容器视图的 ID

###动态添加
通过编程方式将片段添加到某个现有 `ViewGroup`
在 Activity 运行期间随时可以将Fragment添加到 Activity 布局中。只需指定将Fragment放入哪个 `ViewGroup`。

要想在您的 Activity 中执行Fragment事务（如添加、删除或替换Fragment），您必须使用 `FragmentTransaction` 中的 API。您可以像下面这样从 `Activity` 获取一个 `FragmentTransaction` 实例：
~~~
FragmentManager fragmentManager = getFragmentManager()
FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
~~~
然后，您可以使用 `add()` 方法添加一个Fragment，指定要添加的Fragment以及将其插入哪个视图。例如：
~~~
ExampleFragment fragment = new ExampleFragment();
fragmentTransaction.add(R.id.fragment_container, fragment);
fragmentTransaction.commit();
~~~
传递到 `add()` 的第一个参数是 `ViewGroup`，即应该放置Fragment的位置，由资源 ID 指定，第二个参数是要添加的片段。

一旦您通过 `FragmentTransaction` 做出了更改，就必须调用 `commit()` 以使更改生效。

###管理Fragment
要想管理您的 Activity 中的Fragment，您需要使用 `FragmentManager`。要想获取它，从 `Activity` 调用 `getFragmentManager()`。

`FragmentManager` 可以执行的操作包括：

- 通过 `findFragmentById()`（对于在 Activity 布局中提供 UI 的Fragment）或 `findFragmentByTag()`（对于提供或不提供 UI 的Fragment）获取 Activity 中存在的Fragment
- 通过 `popBackStack()`（模拟用户发出的 Back 命令）将片段从返回栈中弹出
- 通过 `addOnBackStackChangedListener()` 注册一个侦听返回栈变化的侦听器

如上文所说的，也可以使用 `FragmentManager` 打开一个 `FragmentTransaction`，通过它来执行某些事务，如添加和删除Fragment。

####执行Fragment事物
在 Activity 中使用Fragment的一大优点是，可以根据用户行为对Fragment执行添加、删除、替换以及其他操作。 `commit`给 Activity 的每组更改都称为事务，FragmentTransaction 中的 API 用来执行事务。我们也可以将每个事务保存到由 Activity 管理的返回栈内，从而让用户能够回退Fragment更改（类似于回退 Activity）。

下面的代码表示如何从 FragmentManager 获取一个FragmentTransaction 实例：
~~~
FragmentManager fragmentManager = getFragmentManager();
FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
~~~
事务都是原子性的，同时执行的一组更改。事物的方法都有：

- add() 往Activity中添加一个Fragment
- remove() 从Activity中移除一个Fragment
- replace() 使用另一个Fragment替换当前的
- hide() 隐藏当前的Fragment，仅仅是设为不可见，并不会销毁
- show() 显示之前隐藏的Fragment
- detach() 会将view从UI中移除,和remove()不同,此时fragment的状态依然由FragmentManager维护。
- attach() 重建view视图，附加到UI上并显示。
等方法为给定事务设置您想要执行的所有更改。然后，要想将事务应用到 Activity，必须调用 commit()。

不过，在您调用 commit() 之前，您可能想调用 addToBackStack()，以将事务添加到片段事务返回栈。 该返回栈由 Activity 管理，允许用户通过按“返回” 按钮返回上一片段状态。

例如，以下示例说明了如何将一个片段替换成另一个片段，以及如何在返回栈中保留先前状态：