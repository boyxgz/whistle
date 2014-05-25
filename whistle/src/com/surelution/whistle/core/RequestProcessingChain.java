/**
 * 
 */
package com.surelution.whistle.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">Guangzong</a>
 *
 */
public class RequestProcessingChain {

	private static Map<String, RequestProcessingChain> instances = new HashMap<String, RequestProcessingChain>();
	private ClassLoader classLoader;

	private ArrayList<Class<BaseAction>> processors = new ArrayList<Class<BaseAction>>();

	private RequestProcessingChain(ClassLoader classLoader, String cfgFile) {
		this.classLoader = classLoader;
		if(this.classLoader == null) {
			this.classLoader = getClass().getClassLoader();
		}
		Configure config = Configure.config(cfgFile);
		for(String processorName : config.getProcessorNames()) {
			if(processorName != null) {
				try {
					Class<BaseAction> c = (Class<BaseAction>)this.classLoader.loadClass(processorName);
					processors.add( c);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getContent(Map<String, String> map) {
		for(Class<BaseAction> cProcessor : processors) {
			//TODO how to handle ?
			BaseAction processor = null;
			try {
				processor = cProcessor.newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
			System.out.print("try ");
			System.out.println(processor.getClass().getName());
			processor.feed(map);
			boolean accept = false;
			try{
				accept = processor.accept();
			} catch(Exception e){}
			if(accept) {
				System.out.print("processor ");
				System.out.print(processor.getClass().getName());
				System.out.println(" goal....");
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

	public static synchronized RequestProcessingChain getInstance(ClassLoader classLoader, String cfgFile) {
		RequestProcessingChain chain;
		if(!instances.containsKey(cfgFile)) {
			chain = new RequestProcessingChain(classLoader, cfgFile);
			instances.put(cfgFile, chain);
		} else {
			chain = instances.get(cfgFile);
		}
		return chain;
	}
}
