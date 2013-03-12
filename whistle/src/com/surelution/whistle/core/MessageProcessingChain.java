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
public class MessageProcessingChain {

	private static MessageProcessingChain instance = new MessageProcessingChain();

	private ArrayList<BaseMessageProcessor> processors = new ArrayList<BaseMessageProcessor>();

	private MessageProcessingChain() {
		Configure config = Configure.config();
		for(String processorName : config.getProcessorNames()) {
			if(processorName != null) {
				try {
					Class<?> c = Class.forName(processorName);
					processors.add((BaseMessageProcessor) c.newInstance());
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
		for(BaseMessageProcessor processor : processors) {
			processor.feed(map);
			boolean accept = false;
			try{
				accept = processor.accept();
			} catch(Exception e){}
			if(accept) {
				processor.process();
				String xml = processor.getXml();
				return xml;
			} else if(processor.goOn()) {
				continue;
			} else {
				break;
			}
		}
		
		return null;
	}

	public static MessageProcessingChain getInstance() {
		return instance;
	}
}
