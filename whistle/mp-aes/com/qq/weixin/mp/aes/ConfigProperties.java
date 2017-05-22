package com.qq.weixin.mp.aes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件 system.properties
 * @author 603_02
 *
 */
public class ConfigProperties {
	public static Properties getProperties(){
		Properties p = new Properties();
		InputStream is = null;  
        try {  
            is = ConfigProperties.class.getClassLoader().getResourceAsStream("system.properties");  
            if(is!=null){  
                p.load(is);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            if(is!=null){  
                try {  
                    is.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }
		return p;
	}


}
