/**
 * 
 */
package com.surelution.whistle.core;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author <a href="mailto:guangzong.syu@gmail.com">Guangzong</a>
 *
 */
public class GateServlet extends HttpServlet {

	private static final long serialVersionUID = -5292716157964954282L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		Configure config = Configure.config();
		String token = config.getToken();
		if(WxmpHelper.checkSignature(token, timestamp, nonce, signature))
			resp.getWriter().write(echostr);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletInputStream is = req.getInputStream();
		Map<String, String> map = IncomeMessageDegister.parse(is);
		System.out.println(map);
		RequestProcessingChain chain = RequestProcessingChain.getInstance(null, "whistle.xml");
		String ret = chain.getContent(map);
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(ret);
	}

}
