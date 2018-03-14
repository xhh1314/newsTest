package com.hww.file.webservice.handler;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hww.file.common.utils.TransfMediaTool;
import com.hww.file.common.vo.FileVideoVo;
import com.hww.file.webservice.service.FileVideoService;

/**
 * 异步处理消息队列 视频上传
 * 
 * @author hewei
 *
 */
public class AsyncHandler {

	private static final Log log = LogFactory.getLog(AsyncHandler.class);
	@Autowired
	private FileVideoService fileVideoService;
	
	private static TransfMediaTool transfMediaTool = new TransfMediaTool();
	
	
	/**
	 * 阻塞队列.
	 */
	private volatile BlockingQueue<VideoMsg> queue;
	
	private Lock lock = new ReentrantLock();

	private static ExecutorService executorService = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

	private AsyncHandler() {
		queue = new LinkedBlockingQueue<VideoMsg>();
	}
	
	private static class handler {
		private static AsyncHandler singleton = new AsyncHandler();
	}
	
	public static AsyncHandler getInstance() {
		return handler.singleton;
	}

	private void handle() {
		log.info("video upload queue size:"+queue.size());
		if(lock.tryLock()) {
			if (queue.size() > 0) {
				//获取头部元素
				VideoMsg msg = queue.peek();
				//删除头部元素
				queue.poll();
				lock.unlock();
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						if (msg != null) {
							log.info("异步上传-begin-fileId:"+msg.getFileId());
							try {
								msg.getMultipartFile().transferTo(msg.getTarget());
								//上传结束后截取封面
//								FileVideoVo fileVideoVo = transfMediaTool.captureFirstFrame(msg.getTarget(),msg.getFfmpegPath(),msg.getCoverPath(),null);
//								//更新video信息
//								Long fileId = msg.getFileId();
//								fileVideoVo.setFileId(fileId);
//								fileVideoService.updateVideo(fileVideoVo);
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						log.info("异步上传-end");
					}
				});
			}
		}
	}

	/**
	 * 
	 * <pre>
	 * 往队列发送消息
	 * 调用处理线程
	 * </pre>
	 *
	 * @param text
	 */
	public void sendMsg(VideoMsg videoMsg) {
		if (videoMsg.getMultipartFile() != null && videoMsg.getTarget()!=null) {
			queue.add(videoMsg);
			this.handle();
		}
	}
}
