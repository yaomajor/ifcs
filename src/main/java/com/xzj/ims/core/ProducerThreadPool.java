package com.xzj.ims.core;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
/**
 * 
 * @author xuzhijun.online 
 * @date 2019年4月13日
 *
 */
public class ProducerThreadPool extends ThreadPoolExecutor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("producer-pool-%d").build();
	
	private static volatile ProducerThreadPool INSTANCE;

	private ProducerThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}

	private ProducerThreadPool(int corePoolSize) {
		
		this(corePoolSize, corePoolSize, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(1), NAMED_THREAD_FACTORY, new ThreadPoolExecutor.AbortPolicy());
	}
	
	private ProducerThreadPool() throws Exception{
		//一个生产者
		this(1);
	}

	public static ProducerThreadPool getInstance() throws Exception {
		if(INSTANCE == null) {
			synchronized (ProducerThreadPool.class) {
				if(INSTANCE == null) {
					INSTANCE = new ProducerThreadPool();
				}
			}
		}
		return INSTANCE;
	}

	@SuppressWarnings("unused")
	private ProducerThreadPool readResolve() {
		return INSTANCE;
	}
	
	public void execute(Runnable command, long delay) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread.sleep(delay);
		super.execute(command);
	}
}
