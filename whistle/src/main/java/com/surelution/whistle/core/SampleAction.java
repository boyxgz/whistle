/**
 * 
 */
package com.surelution.whistle.core;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">Guangzong</a>
 *
 */
public class SampleAction extends BaseAction {

	/* process every request, if you don't want this processor handle every
	 * request, return false
	 * eg:
	 * 
	 *	if(getParam(Attribute.KEY_Content).equals("?")) {
	 *		return false;
	 *	}
	 * (non-Javadoc)
	 * @see com.surelution.whistle.core.BaseRequestProcessor#accept()
	 */
	@Override
	public boolean accept() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.surelution.whistle.core.BaseRequestProcessor#process()
	 */
	@Override
	public void execute() {
		put(new Attribute(Attribute.KEY_Content, "hello, whistle"));
	}

}
