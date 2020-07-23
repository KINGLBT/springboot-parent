# 事件发布与监听

## JDK实现观察者模式

当对象间存在一对多的关系时，一个对象发生变化，会自动通知他的依赖对象。JDK内置了观察者模式，方便去实现。

观察者模式的使用场景:

+ 一个抽象模型有两个方面，其中一个方面依赖于另一个方面。将这些方面封装在独立的对象中使它们可以各自独立地改变和复用。

+ 一个对象的改变将导致其他一个或多个对象也发生改变，而不知道具体有多少对象将发生改变，可以降低对象之间的耦合度。
一个对象必须通知其他对象，而并不知道这些对象是谁。

+ 需要在系统中创建一个触发链，A对象的行为将影响B对象，B对象的行为将影响C对象……，可以使用观察者模式创建一种链式触发机制。

java.util包中提供了Observable类和Observer接口,被观察者需要继承Observable类，观察者需要实现Observer接口。
被观察提供了注册功能，可以添加观察者，当被观察者变更时，会去通知注册了的观察者。

### Observable类源码分析

#### 注册功能实现

obs是一个观察者集合，将观察者对象放入集合中

```
public synchronized void addObserver(Observer o) {
  if (o == null)
    throw new NullPointerException();
  if (!obs.contains(o)) {
    obs.addElement(o);
  }
}
```

#### 通知功能实现

+ 变化标志，判断是由有变更，只有变更了，采取通知

+ 从观察者集合中获取观察者对象，依次调用观察者的update方法去实现通知。

```
public void notifyObservers(Object arg) {
    Object[] arrLocal;
    synchronized (this) {
        if (!changed)
            return;
        arrLocal = obs.toArray();
        clearChanged();
    }
    for (int i = arrLocal.length-1; i>=0; i--)
        ((Observer)arrLocal[i]).update(this, arg);
}
```

### Observable类源码分析

所有的观察者，必须实现update方法

```java
public interface Observer {
    void update(Observable o, Object arg);
}
```

### 示例

以手机价格降价通知为例，手机作为被观察对象，用户作为观察者，当手机价格降价后，自动通知用户

### 被观察类

```java
public class HuaWeiP30 extends Observable {

  /**
   * 手机价格
   */
  private BigDecimal price;

  public HuaWeiP30(BigDecimal price) {
    this.price = price;
  }

  public void setPrice(BigDecimal price) {
    if (price.compareTo(this.price) != Integer.valueOf(0)) {
      setChanged();
    }
    if (price.compareTo(this.price) == Integer.valueOf(-1)) {
      this.notifyObservers(price);
    }
    this.price = price;
  }

  public BigDecimal getPrice() {
    return price;
  }
}

```


### 观察者类

```java
@Data
public class HuaWeiFan implements Observer {

  /**
   * 花粉名称
   */
  private String name;

  public HuaWeiFan(String name) {
    this.name = name;
  }

  @Override
  public void update(Observable o, Object arg) {
    if (o instanceof HuaWeiP30) {
      System.out.println("粉丝名为"+name+"发现华为p30降价了，新的价格为："+arg+"元");
    }
  }
}

```

### 添加观察者，模拟变更
```
@Test
  public void jdkObserverTest(){
    HuaWeiP30 p30 = new HuaWeiP30(BigDecimal.valueOf(3000));
    p30.addObserver(new HuaWeiFan("zhangsan"));
    p30.addObserver(new HuaWeiFan("lisi"));
    p30.addObserver(new HuaWeiFan("wangwu"));
    p30.setPrice(BigDecimal.valueOf(2500));
  }
```

## Java事件模型，监听模式

### 监听模式中的3个角色

+ 事件源 （事件发生的源头，作为被监听的对象，注册特定的监听，才能对事件进行响应）

+ 事件对象 （事件本身，用于包装事件源，提供事件的属性等）

+ 监听器 （事件发生后需要执行的动作，即回调函数，必须注册在事件源上）

所有的事件监听器，必须实现 **EventListener**接口,所有的事件对象类，都必须继承**EventObject**类

### 示例 Button按钮控件

#### 点击事件

```java
/**
 * @ClassName: ClickEvent
 * @description:
 * @author: luomeng
 * @time: 2020/7/21 10:20
 */
public class ClickEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ClickEvent(Object source) {
        super(source);
    }
}

```

#### 点击事件监听器

```java
public interface ClickListener extends EventListener {
    void handleEvent(ClickEvent clickEvent);
}
```

#### 事件触发源头 Button按钮

```java
/**
 * @ClassName: Button
 * @description:
 * @author: luomeng
 * @time: 2020/7/21 13:45
 */
public class Button {

    /**
     * 监听事件集合，后续触发点击，做通知处理
     */
    private List<ClickListener> eventListeners = new ArrayList<>();

    /**
     * 添加监听器
     *
     * @param eventListener
     */
    public void addListener(ClickListener eventListener) {
        eventListeners.add(eventListener);
    }

    /**
     * 通知所有的监听器，进行处理
     *
     * @param clickEvent
     */
    private void notifyListener(ClickEvent clickEvent) {
        Iterator<ClickListener> iterator = eventListeners.iterator();
        if (iterator.hasNext()) {
            ClickListener clickListener = iterator.next();
            clickListener.handleEvent(clickEvent);
        }
    }

    /**
     * 点击操作触发
     */
    public void click() {
        ClickEvent clickEvent = new ClickEvent(this);
        System.out.println("按钮被点击");
        notifyListener(clickEvent);
    }

}

```

#### 运行

```
@Test
public void buttonClickTest(){
    Button button = new Button();
    button.addListener(new ClickListener() {
            @Override
            public void handleEvent(ClickEvent clickEvent) {
                System.out.println("弹出对话框");
            }
    });
    button.click();
}
```

## Spring中的事件发布与监听


```java
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(Application.class);
    application.setLazyInitialization(Boolean.TRUE);
    application.run(args);
  }
  
  
}
```
