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

通常，我们的Fragment至少应实现以下生命周期方法：

* onCreate()
系统会在创建Fragment时调用此方法。我们应该在这个方法里初始化那些在Fragment暂停或停止后恢复时必需保留的Fragment组件。
* onCreateView()
系统会在Fragment首次绘制其用户界面时调用此方法。 要想为您的Fragment绘制 UI，您从此方法中返回的 View 必须是Fragment布局的根视图。如果Fragment未提供 UI，可以返回 null。
* onPause()
系统将此方法作为用户离开Fragment的第一个信号（但并不总是意味着此Fragment会被销毁）进行调用。 通常应该在此方法内保存一些将来需要恢复的数据（因为用户可能不会返回）。

