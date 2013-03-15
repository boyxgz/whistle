/**
 * 
 */
package com.surelution.whistle.core;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">Guangzong</a>
 *
 */
public class RequestProcessingChain {

	private static RequestProcessingChain instance = new RequestProcessingChain();

	private ArrayList<BaseAction> processors = new ArrayList<BaseAction>();

	private RequestProcessingChain() {
		Configure config = Configure.config();
		for(String processorName : config.getProcessorNames()) {
			if(processorName != null) {
				try {
					Class<?> c = Class.forName(processorName);
					processors.add((BaseAction) c.newInstance());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getContent(Map<String, String> map) {
		for(BaseAction processor : processors) {
			processor.feed(map);
			boolean accept = false;
			try{
				accept = processor.accept();
			} catch(Exception e){}
			if(accept) {
				processor.preExecute();
				processor.execute();
				String xml = processor.buildXml();
				return xml;
			} else if(processor.moveOn()) {
				continue;
			} else {
				break;
			}
		}
		
		return null;
	}

	public static RequestProcessingChain getInstance() {
		return instance;
	}
}
