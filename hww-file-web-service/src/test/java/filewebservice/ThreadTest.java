//package filewebservice;
//
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//public class ThreadTest {
//
//	/**
//	 * 阻塞队列.
//	 */
//	private volatile BlockingQueue<String> queue;
//
//	private Lock lock = new ReentrantLock();
//
//	private static ExecutorService executorService = Executors
//			.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
//
//	private ThreadTest() {
//		queue = new LinkedBlockingQueue<String>();
//	}
//
//	private void handle() {
//		System.out.println("video upload queue size:" + queue.size());
//
//		try {
//			if (lock.tryLock()) {
//				if (queue.size() > 0) {
//					executorService.execute(new Runnable() {
//
//						@Override
//						public void run() {
//							try {
//								// 获取头部元素
//								String msg = queue.peek();
//								// 删除头部元素
//								queue.poll();
//								if (msg != null) {
//									System.out.println("异步上传-begin-fileId:");
//									System.out.println("excute some code");
//								}
//								System.out.println("异步上传-end");
//							} catch (Exception e) {
//								
//							}
//						}
//					});
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			lock.unlock();
//		}
//	}
//
//	/**
//	 * 
//	 * <pre>
//	 * 往队列发送消息
//	 * 调用处理线程
//	 * </pre>
//	 *
//	 * @param text
//	 */
//	public void sendMsg(String videoMsg) {
//		queue.add(videoMsg);
//		this.handle();
//	}
//
//	public static void main(String args[]) {
//		ThreadTest test = new ThreadTest();
//		for(int i=0;i<2;i++) {
//			test.sendMsg("冬至吃饺子");
//		}
//	}
//}
