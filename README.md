#Fragment 总结
##创建fragment

创建fragment必须要继承android.app.Fragment，还有下面几种除了Fragment基类的可扩展子类可以继承。

* DialogFragment
显示浮动对话框。使用此类创建对话框可有效地替代使用 Activity 类中的对话框帮助程序方法，因为您可以将Fragment对话框纳入由 Activity 管理的Fragment返回栈，从而使用户能够返回清除的Fragment。
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
    tools:layout="@layout/fragment_main"/>
~~~
`<fragment>` 中的 `android:name` 属性指定要在布局中实例化的 Fragment 类。

当系统创建此 Activity 布局时，会实例化在布局中指定的每个Fragment，并为每个Fragment调用 `onCreateView()` 方法，以检索每个Fragment的布局。系统会直接插入Fragment返回的 `View` 来替代 `<fragment>` 元素。
>每个Fragment都需要一个唯一的标识符，重启 Activity 时，系统可以使用该标识符来恢复Fragment（也可以使用该标识符来捕获Fragment以执行某些事务，如将其删除）。 可以通过三种方式为Fragment提供 ID：

>1. 为 android:id 属性提供唯一 ID
>2. 为 android:tag 属性提供唯一字符串
>3. 如果您未给以上两个属性提供值，系统会使用容器视图的 ID

###动态添加
通过编程方式将Fragment添加到某个现有 `ViewGroup`
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
传递到 `add()` 的第一个参数是 `ViewGroup`，即应该放置Fragment的位置，由资源 ID 指定，第二个参数是要添加的Fragment。

一旦您通过 `FragmentTransaction` 做出了更改，就必须调用 `commit()` 以使更改生效。

###管理Fragment
要想管理您的 Activity 中的Fragment，您需要使用 `FragmentManager`。要想获取它，从 `Activity` 调用 `getFragmentManager()`。

`FragmentManager` 可以执行的操作包括：

- 通过 `findFragmentById()`（对于在 Activity 布局中提供 UI 的Fragment）或 `findFragmentByTag()`（对于提供或不提供 UI 的Fragment）获取 Activity 中存在的Fragment
- 通过 `popBackStack()`（模拟用户发出的 Back 命令）将Fragment从返回栈中弹出
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

在调用 commit() 之前，您可能想调用 addToBackStack()，以将事务添加到Fragment事务返回栈。 该返回栈由 Activity 管理，允许用户通过按“返回” 按钮返回上一Fragment状态。

例如，以下示例说明了如何将一个Fragment替换成另一个Fragment，以及如何在返回栈中保留先前状态：
~~~
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.content_main, fragmentTwo).addToBackStack(null).commit();
~~~

在上例中，`fragmentTwo` 会替换目前在 `R.id.content_main` ID 所标识的布局容器中的任何Fragment（如有）。通过调用 `addToBackStack()` 可将替换事务保存到返回栈，以便用户能够通过按“返回” 按钮撤消事务并回退到上一Fragment。

如果您向事务添加了多个更改（如又一个 add() 或 remove()），并且调用了 addToBackStack()，则在调用 commit() 前应用的所有更改都将作为单一事务添加到返回栈，并且“返回” 按钮会将它们一并撤消。

向 FragmentTransaction 添加更改的顺序无关紧要，不过：

- 您必须最后调用 commit()
- 如果您要向同一容器添加多个Fragment，则您添加Fragment的顺序将决定它们在视图层次结构中的出现顺序

如果您没有在执行删除Fragment的事务时调用 addToBackStack()，则事务提交时该Fragment会被销毁，用户将无法回退到该Fragment。 不过，如果您在删除Fragment时调用了 addToBackStack()，则系统会停止该Fragment，并在用户回退时将其恢复。

>提示：对于每个Fragment事务，您都可以通过在提交前调用 setTransition() 来应用过渡动画。

调用 commit() 不会立即执行事务，而是在 Activity 的 UI 线程（“主”线程）可以执行该操作时再安排其在线程上运行。不过，如有必要，您也可以从 UI 线程调用 executePendingTransactions() 以立即执行 commit() 提交的事务。通常不必这样做，除非其他线程中的作业依赖该事务。

>注意：您只能在 Activity保存其状态（用户离开 Activity）之前使用 commit() 提交事务。如果您试图在该时间点后提交，则会引发异常。 这是因为如需恢复 Activity，则提交后的状态可能会丢失。 对于丢失提交无关紧要的情况，请使用 commitAllowingStateLoss()。

##与 Activity 通信
尽管 Fragment 是作为独立于 Activity 的对象实现，并且可在多个 Activity 内使用，但Fragment的给定实例会直接绑定到包含它的 Activity。

具体地说，Fragment可以通过 getActivity() 访问 Activity 实例，并轻松地执行在 Activity 布局中查找视图等任务。
~~~
View listView = getActivity().findViewById(R.id.list);
~~~
同样地，您的 Activity 也可以使用 findFragmentById() 或 findFragmentByTag()，通过从 FragmentManager 获取对 Fragment 的引用来调用Fragment中的方法。例如：
~~~
ExampleFragment fragment = (ExampleFragment) getFragmentManager().findFragmentById(R.id.example_fragment);
~~~
###创建对 Activity 的事件回调

在某些情况下，您可能需要通过Fragment与 Activity 共享事件。执行此操作的一个好方法是，在Fragment内定义一个回调接口，并要求宿主 Activity 实现它。 当 Activity 通过该接口收到回调时，可以根据需要与布局中的其他Fragment共享这些信息。

例如，如果一个新闻应用的 Activity 有两个Fragment —一个用于显示文章列表（Fragment A），另一个用于显示文章（Fragment B）—，那么Fragment A必须在列表项被选定后告知 Activity，以便它告知Fragment B 显示该文章。 在本例中，OnArticleSelectedListener 接口在Fragment A 内声明：
~~~
public static class FragmentA extends ListFragment {
    ...
    // Container Activity must implement this interface
    public interface OnArticleSelectedListener {
        public void onArticleSelected(Uri articleUri);
    }
    ...
}
~~~
然后，该Fragment的宿主 Activity 会实现 OnArticleSelectedListener 接口并替代 onArticleSelected()，将来自Fragment A 的事件通知Fragment B。为确保宿主 Activity 实现此界面，Fragment A 的 onAttach() 回调方法（系统在向 Activity 添加Fragment时调用的方法）会通过转换传递到 onAttach() 中的 Activity 来实例化 OnArticleSelectedListener 的实例：
~~~
public static class FragmentA extends ListFragment {
    OnArticleSelectedListener mListener;
    ...
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnArticleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }
    ...
}
~~~
如果 Activity 未实现界面，则Fragment会引发 ClassCastException。实现时，mListener 成员会保留对 Activity 的 OnArticleSelectedListener 实现的引用，以便Fragment A 可以通过调用 OnArticleSelectedListener 界面定义的方法与 Activity 共享事件。例如，如果Fragment A 是 ListFragment 的一个扩展，则用户每次点击列表项时，系统都会调用Fragment中的 onListItemClick()，然后该方法会调用 onArticleSelected() 以与 Activity 共享事件：
~~~
public static class FragmentA extends ListFragment {
    OnArticleSelectedListener mListener;
    ...
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Append the clicked item's row ID with the content provider Uri
        Uri noteUri = ContentUris.withAppendedId(ArticleColumns.CONTENT_URI, id);
        // Send the event and Uri to the host activity
        mListener.onArticleSelected(noteUri);
    }
    ...
}
~~~
传递到 onListItemClick() 的 id 参数是被点击项的行 ID，即 Activity（或其他Fragment）用来从应用的 ContentProvider 获取文章的 ID。

##处理Fragment生命周期
![activity_fragment_lifecycle](https://github.com/Sting926/FragmentSample/blob/master/screenshots/activity_fragment_lifecycle.png)

管理Fragment生命周期与管理 Activity 生命周期很相似。和 Activity 一样，Fragment也以三种状态存在：

*恢复*

Fragment在运行中的 Activity 中可见。

*暂停*

另一个 Activity 位于前台并具有焦点，但此Fragment所在的 Activity 仍然可见（前台 Activity 部分透明，或未覆盖整个屏幕）。

*停止*

Fragment不可见。宿主 Activity 已停止，或Fragment已从 Activity 中删除，但已添加到返回栈。 停止Fragment仍然处于活动状态（系统会保留所有状态和成员信息）。 不过，它对用户不再可见，如果 Activity 被终止，它也会被终止。
同样与 Activity 一样，假使 Activity 的进程被终止，而您需要在重建 Activity 时恢复Fragment状态，您也可以使用 Bundle 保留Fragment的状态。您可以在Fragment的 onSaveInstanceState() 回调期间保存状态，并可在 onCreate()、onCreateView() 或 onActivityCreated() 期间恢复状态。如需了解有关保存状态的详细信息，请参阅Activity文档。

Activity 生命周期与Fragment生命周期之间的最显著差异在于它们在其各自返回栈中的存储方式。 默认情况下，Activity 停止时会被放入由系统管理的 Activity 返回栈（以便用户通过“返回” 按钮回退到Activity，任务和返回栈对此做了阐述）。不过，仅当您在删除Fragment的事务执行期间通过调用 addToBackStack() 显式请求保存实例时，系统才会将Fragment放入由宿主 Activity 管理的返回栈。

- fragmentTransaction.add(R.id.content_main, fragmentOne).commit();
~~~
FragmentOne@583021416: onAttach;
FragmentOne@583021416: onCreate;
FragmentOne@583021416: onCreateView;
FragmentOne@583021416: onActivityCreated;
FragmentOne@583021416: onStart;
FragmentOne@583021416: onResume;
~~~
为Activity添加第一个Fragment的时候不要把事物添加到返回栈，如果添加了，则会在“返回”按钮按下后回退到Activity的空白页面，这应该是大家不想看到的。

- fragmentTransaction.replace(R.id.content_main, fragmentTwo).addToBackStack(null).commit();
~~~
FragmentOne@583021416: onPause;
FragmentOne@583021416: onStop;
FragmentOne@583021416: onDestroyView;
FragmentTwo@158406241: onAttach;
FragmentTwo@158406241: onCreate;
FragmentTwo@158406241: onCreateView;
FragmentTwo@158406241: onActivityCreated;
FragmentTwo@158406241: onStart;
FragmentTwo@158406241: onResume;
~~~
FragmentTransaction的replace方法相当于remove方法和add方法的结合，上面的程序输出也证明了这一点

- fragmentTransaction.hide(fragmentTwo).add(R.id.content_main, fragmentThree).addToBackStack(null).commit();
~~~
FragmentThree@892945888: onAttach;
FragmentThree@892945888: onCreate;
FragmentThree@892945888: onCreateView;
FragmentThree@892945888: onActivityCreated;
FragmentThree@892945888: onStart;
FragmentThree@892945888: onResume;
~~~
FragmentTransaction的hide方法不触发Fragment的生命周期方法，所以隐藏FragmentTwo没有触发接下来的生命周期，上面的输出表明这个事物提交后只触发了添加fragmentThree的生命周期方法。

- fragmentTransaction.detach(fragmentThree).addToBackStack(null).commit();
~~~
FragmentThree@623537138: onPause;
FragmentThree@623537138: onStop;
FragmentThree@623537138: onDestroyView;
~~~

- 按下返回键 popBackStack
~~~
FragmentThree@623537138: onCreateView;
FragmentThree@623537138: onActivityCreated;
FragmentThree@623537138: onStart;
FragmentThree@623537138: onResume;
~~~
返回操作，恢复到detach FragmentThree的状态，FragmentThree从detach状态变为attach状态

- 按下返回键 popBackStack FragmentThree被移除
~~~
FragmentThree@892945888: onPause;
FragmentThree@892945888: onStop;
FragmentThree@892945888: onDestroyView;
FragmentThree@892945888: onDestroy;
FragmentThree@892945888: onDetach;
~~~
返回操作，恢复到加载FragmentThree前的状态，上一个提交的事物为`hide->FragmentTwo` `add ->FragmentThree` 则恢复操作相反 `“remove->FragmentThree”` `“show->FragmentTwo”`,
上面讲到hide方法不触发Fragment生命周期，同理show方法也不触发Fragment生命周期。
由于FragmentThree已经退出"历史舞台"取消与Activity关联，所以执行`onDetach`方法。

- 按下返回键 popBackStack FragmentTwo
~~~
FragmentTwo@158406241: onPause;
FragmentTwo@158406241: onStop;
FragmentTwo@158406241: onDestroyView;
FragmentTwo@158406241: onDestroy;
FragmentTwo@158406241: onDetach;
FragmentOne@583021416: onCreateView;
FragmentOne@583021416: onActivityCreated;
FragmentOne@583021416: onStart;
FragmentOne@583021416: onResume;
~~~
返回操作，恢复到加载FragmentTwo前的状态，则replace的逆操作`“remove FragmentTwo”` `“add FragmentOne”`

- 按下返回键 popBackStack FragmentOne
~~~
FragmentOne@583021416: onPause;
FragmentOne@583021416: onStop;
FragmentOne@583021416: onDestroyView;
FragmentOne@583021416: onDestroy;
FragmentOne@583021416: onDetach;
~~~
返回操作，恢复到加载FragmentOne前的状态，没有加入回退栈，Activity直接退出。

在其他方面，管理Fragment生命周期与管理 Activity 生命周期非常相似。 因此，管理 Activity 生命周期的做法同样适用于Fragment。 但您还需要了解 Activity 的生命周期对Fragment生命周期的影响。

>注意：如需 Fragment 内的某个 Context 对象，可以调用 getActivity()。但要注意，请仅在Fragment附加到 Activity 时调用 getActivity()。如果Fragment尚未附加，或在其生命周期结束期间分离，则 getActivity() 将返回 null。

##与 Activity 生命周期协调一致
Fragment所在的 Activity 的生命周期会影响Fragment的生命周期，其表现为，Activity 的每次生命周期回调都会引发每个Fragment的类似回调。 例如，当 Activity 收到 onPause() 时，Activity 中的每个Fragment也会收到 onPause()。

不过，Fragment还有几个额外的生命周期回调，用于处理与 Activity 的唯一交互，以执行构建和销毁Fragment UI 等操作。这些额外的回调方法是：

- `onAttach()`
在Fragment已与 Activity 关联时调用（Activity 传递到此方法内）。
- `onCreateView()`
调用它可创建与Fragment关联的视图层次结构。
- `onActivityCreated()`
在 Activity 的 onCreate() 方法已返回时调用。
- `onDestroyView()`
在删除与Fragment关联的视图层次结构时调用。
- `onDetach()`
在取消Fragment与 Activity 的关联时调用。

图 3 图示说明了受其宿主 Activity 影响的Fragment生命周期流。在该图中，您可以看到 Activity 的每个连续状态如何决定Fragment可以收到的回调方法。 例如，当 Activity 收到其 onCreate() 回调时，Activity 中的Fragment只会收到 onActivityCreated() 回调。

一旦 Activity 达到恢复状态，您就可以意向 Activity 添加Fragment和删除其中的Fragment。 因此，只有当 Activity 处于恢复状态时，Fragment的生命周期才能独立变化。

不过，当 Activity 离开恢复状态时，Fragment会在 Activity 的推动下再次经历其生命周期。