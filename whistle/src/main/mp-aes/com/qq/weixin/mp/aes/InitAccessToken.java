package com.qq.weixin.mp.aes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * @author 初始化accesstokrn
 *
 */
@SuppressWarnings("serial")
public class InitAccessToken extends HttpServlet {


	public void init() throws ServletException {
		new GetAccessToken(this.getServletContext());
	}

}
