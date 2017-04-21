<h1 align='center'>大学生时间规划开发文档</h1>
<h4 align="right" >----我们说的都队</h4>

##0、程序设计目标：
本程序将大学生的课程表和todolist结合起来，为学生智能安排学习生活时间，通过学生本人的学习计划和对课程表与考试成绩的分析，自动为其提供自习计划；通过群组功能方便组织者在大部分人空闲的时候发布活动，学生加入的活动会自动作为日程的一部分显示出来，集活动发布，报名参加，行程备忘于一体。旨在为大学生打造方便愉快的学习生活。

##1、功能：

+ 自动导入课程表。
+ 在某个确定时间添加日程计划。
+ 长期目标计划（设置为每周几或者每隔几天执行一次）。
+ *对于不确定时间的任务，自动按照优先级安排时间。
+ *推荐自习内容。
+ 提醒完成任务。
+ 创建和加入群组。
+ 在群组中发起活动，发起人可以查看组中大部分人什么时间空闲。
+ 群组成员可以查看并报名加入活动，如果活动时间与学习时间冲突会提示。
+ 对于一个活动，有Q&A功能，方便其他人了解活动。
+ *把已经完成的活动保存下来，将来可以再发起一次（可以选择旧成员优先或者禁止旧成员参加）
+ 加入的活动成为自己日程的一部分。
+ 由于各种原因错过的学习时间会提醒补回来。（包括自习和*上课）
+ *完成长期目标可以种一棵树。

##2、程序实现：
android端由java实现。

+ 界面部分：
	+ MainActivity 实现选择界面的按钮，日程表、群组、活动和菜单均由Frament实现。
	+ 登陆/注册界面，设置界面，目标等全屏显示不需要交叉跳转的界面由独立的activity实现。
+ 数据结构：
	+ 用户类：
	<pre>class   User {
	enum Sex {male, female};
	String  name;
	String id;
	int age;
	string phone1;
	Sex sex;
	string email;
	string schoolId;
	string schoolName;
	Location location;  // location类
	string major;
	int grade;
	Groups groups;
	Calendar claendar;
	public User(string identified, string password);
	static public signUp(string identified, string password) ;
	public update();  // 本地与服务器同步数据
    	public save(); // 保存到本地
	}
	</pre>
	+ 描述位置的类：
	<pre>class Location() {
	string contry;
	string province;
	string city;
	string zipCode;
	public Location(string line, string zipCode) // 解析xml中的字符串
	public string toString(boolen contry, boolen province, boolen city);
	public string toString();  // 整理成字符串
	</pre>
	+ 群组类
	<pre>class Groups implements Iteator, Iteatalbe{
	enum Privilege{member, master, owner};
	class Group{
		string gid;
		string name;
		Location location;
		Privilege privilege;
		}
	// 定义内部类可能无法在主调方法中使用，如果这样的话把这个类和枚举类都放到一个单独的文件里。
	ArrayList  &lt;group&gt; groups
	int all; // 一共有多少群组
	public Groups(xml 相关的结构); // 构造函数，传入xml树，生成群组表。
	public Groups();  生成空列表

	@override
	public remove(Group which) ; // 删除
	public add(Group);  // 添加或者删除后，迭代关系会改变，都把iteratorExists设置为false以销毁迭代器。
	}
	</pre>
	<pre>
	class GroupsIterator implements Iterator, Iterable {
	protect int index;
	protect int all;
	protect Groups g;
	@override
	public Iterator(); // 生成迭代器
	@override
	public boolean hasNext();
	@override
	public Gtoup next();
    public resetIterator(); // 重置迭代器，当列表中元素有改动时，原迭代器将失效。
    public resetIterator(Group to); // 把迭代器重新定位到to的位置
	}
	</pre>
    + 日程表类
     <pre>
    class Item {
    	string name;
        Type type;
        string id;
        string place;
        LocalTime firstDate;
        LocalTime lastDate;
        ArrList &lt;class {LocalTime start; LocalTime end; int day; string place; string commit;}&gt; time; 
        // 每天活动的时间，保证day按升序排列
        // 
        boolean doesTheDayActivited[7]; // 每天是否有活动（仅对target和course有效）
        string detials;
        boolean priority; // 此任务是否为优先
        }
    </pre>
    <pre>
    enum Type {course, activity, target};
    </pre>
    <pre>
    class Calendar {  
        ArrayList &lt;Item&gt; g;
        int all;
        public Item(xml);
        public Item();
        public remove(Item i); // 删除整个Item
        public remove(Item i, int day); // 删除某个活动星期几的计划，如果任和一天都没有这个活动，那么删除整个Item，此方法仅对course和target有效
        public remove(Item i, LocalDate d); // 删除某个活动在某个日期的计划。这个方法仅对activity有效。
        public save();
    }
    </pre>
	+ 网络类
	<pre>
    class Network{
    	string addr;
        static public signIn(string indetified, string password);
        static public signUp(string identified, string password);
        static public int checkUpDate(enum {user, calendar, groups}, string userId);
        static public request(enum {user, calendar, groups}, string userId);
        static public upLoad(enum {user, calendar, groups}, string userId);
        static public checkUpdate();
        static protect downloadFile(string fileName);
    }
    </pre>
