#Java内存模型（Java Memory Model）

Java内存模型（JMM），不同于Java运行时数据区，JMM的主要目标是定义程序中各个变量的访问规则，即在虚拟机中将变量存储到内存和从内存中读取数据这样的底层细节。JMM规定了所有的变量都存储在主内存中，但每个线程还有自己的工作内存，线程的工作内存中保存了被该线程使用到的变量的主内存副本拷贝。线程对变量的所有操作都必须在工作内存中进行，而不能直接读写主内存中的变量，工作内存是线程之间独立的，线程之间变量值的传递均需要通过主内存来完成。

##volatile关键字
平时在阅读jdk源码的时候，经常看到源码中有写变量被volatile关键字修饰，但是却不是十分清除这个关键字到底有什么用处，现在终于弄清楚了，那么我就来讲讲这个volatile到底有什么用吧。

当一个变量被定义为volatile之后，就可以保证此变量对所有线程的可见性，即当一个线程修改了此变量的值的时候，变量新的值对于其他线程来说是可以立即得知的。可以理解成：对volatile变量所有的写操作都能立刻被其他线程得知。但是这并不代表基于volatile变量的运算在并发下是安全的，因为volatile只能保证内存可见性，却没有保证对变量操作的原子性。比如下面的代码：


/**
 * 发起20个线程，每个线程对race变量进行10000次自增操作，如果代码能够正确并发，
 * 则最终race的结果应为200000，但实际的运行结果却小于200000。
 * 
 * @author Colin Wang
 *
 */
public class VolatileTest {
	public static volatile int race = 0;

	public static void increase() {
		race++;
	}

	private static final int THREADS_COUNT = 20;

	public static void main(String[] args) {
		Thread[] threads = new Thread[THREADS_COUNT];

		for (int i = 0; i < THREADS_COUNT; i++) {
			threads[i] = new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 10000; i++) {
						increase();
					}
				}
			});
			threads[i].start();
		}
		
		while (Thread.activeCount() > 1)
			Thread.yield();

		System.out.println(race);
	}
}
这便是因为race++操作不是一个原子操作，导致一些线程对变量race的修改丢失。若要使用volatale变量，一般要符合以下两种场景：

变量的运算结果并不依赖于变量的当前值，或能够保证只有单一的线程修改变量的值。
变量不需要与其他的状态变量共同参与不变约束。
使用volatile变量还可以禁止JIT编译器进行指令重排序优化，这里使用单例模式来举个例子：


/**
 * 单例模式例程一
 * 
 * @author Colin Wang
 *
 */
public class Singleton_1 {

	private static Singleton_1 instance = null;

	private Singleton_1() {
	}

	public static Singleton_1 getInstacne() {
		/*
		 * 这种实现进行了两次instance==null的判断，这便是单例模式的双检锁。
		 * 第一次检查是说如果对象实例已经被创建了，则直接返回，不需要再进入同步代码。
		 * 否则就开始同步线程，进入临界区后，进行的第二次检查是说：
		 * 如果被同步的线程有一个创建了对象实例， 其它的线程就不必再创建实例了。
		 */
		if (instance == null) {
			synchronized (Singleton_1.class) {
				if (instance == null) {
					/*
					 * 仍然存在的问题：下面这句代码并不是一个原子操作，JVM在执行这行代码时，会分解成如下的操作：
					 * 1.给instance分配内存，在栈中分配并初始化为null
					 * 2.调用Singleton_1的构造函数，生成对象实例，在堆中分配 
					 * 3.把instance指向在堆中分配的对象
					 * 由于指令重排序优化，执行顺序可能会变成1，3，2，
					 * 那么当一个线程执行完1，3之后，被另一个线程抢占，
					 * 这时instance已经不是null了，就会直接返回。
					 * 然而2还没有执行过，也就是说这个对象实例还没有初始化过。
					 */
					instance = new Singleton_1();
				}
			}
		}
		return instance;
	}
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
/**
 * 单例模式例程二
 * 
 * @author Colin Wang
 *
 */
public class Singleton_2 {

	/*
	 * 为了避免JIT编译器对代码的指令重排序优化，可以使用volatile关键字，
	 * 通过这个关键字还可以使该变量不会在多个线程中存在副本，
	 * 变量可以看作是直接从主内存中读取，相当于实现了一个轻量级的锁。
	 */
	private volatile static Singleton_2 instance = null;

	private Singleton_2() {
	}

	public static Singleton_2 getInstacne() {
		if (instance == null) {
			synchronized (Singleton_2.class) {
				if (instance == null) {
					instance = new Singleton_2();
				}
			}
		}
		return instance;
	}
}
变量在有了volatile修饰之后，对变量的修改会有一个内存屏障的保护，使得后面的指令不能被重排序到内存屏障之前的位置。volalite变量的读性能与普通变量类似，但是写性能要低一些，因为它需要插入内存屏障指令来保证处理器不会发生乱序执行。即便如此，大多数场景下volatile的总开销仍然要比锁低，所以volatile的语义能满足需求时候，选择volatile要优于使用锁。