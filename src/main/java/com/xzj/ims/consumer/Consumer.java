package com.xzj.ims.consumer;


/**
 * @author xuzhijun.online  
 * @date 2019年4月16日
 */
public interface Consumer<T> extends Runnable {
	
	public void consume() throws Exception;

}
