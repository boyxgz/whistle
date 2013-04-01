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

	private ArrayList<BaseAction> processors = new ArrayList<BaseAction>();

	private RequestProcessingChain(ClassLoader classLoader, String cfgFile) {
		this.classLoader = classLoader;
		if(this.classLoader == null) {
			this.classLoader = getClass().getClassLoader();
		}
		Configure config = Configure.config(cfgFile);
		for(String processorName : config.getProcessorNames()) {
			if(processorName != null) {
				try {
					Class<?> c = this.classLoader.loadClass(processorName);
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
