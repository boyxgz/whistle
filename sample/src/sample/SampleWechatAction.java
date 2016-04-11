/**
 * 
 */
package sample;

import com.surelution.whistle.core.Attribute;
import com.surelution.whistle.core.BaseAction;
import com.surelution.whistle.core.NewsAttribute;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guagnzong</a>
 *
 */
public class SampleWechatAction extends BaseAction {

	/* (non-Javadoc)
	 * @see com.surelution.whistle.core.BaseAction#accept()
	 */
	@Override
	public boolean accept() {
		//如果用户发送文本内容，则由本类处理
		return getParam(Attribute.KEY_MsgType) == Attribute.Msg_Type_TEXT;
	}

	/* (non-Javadoc)
	 * @see com.surelution.whistle.core.BaseAction#execute()
	 */
	@Override
	public void execute() {
		NewsAttribute na = new NewsAttribute(); //创建一个NewsAttribute，NewsAttribute是一个图文消息
		
		na.add("我是标题", 
				"我是描述", 
				"http://www.sinaimg.cn/home/deco/2009/0330/logo_home_tech_news.gif", 
				"http://tech.sina.com.cn/"); //添加一条图文消息
		put(na); // 发送图文消息
	}

}
