/**
 * 
 */
package com.surelution.whistle.core;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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
		if(checkSignature(token, timestamp, nonce, signature))
			resp.getWriter().write(echostr);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletInputStream is = req.getInputStream();
		Map<String, String> map = IncomeMessageParser.parse(is);
		System.out.println(map);
		MessageProcessingChain chain = MessageProcessingChain.getInstance();
		String ret = chain.getContent(map);
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(ret);
	}
	
	private boolean checkSignature(String token, String timestamp, String nonce, String signature) {
		String[] sha1Params = {token, timestamp, nonce};
		Arrays.sort(sha1Params);
		StringBuilder sb = new StringBuilder();
		for(String param : sha1Params) {
			sb.append(param);
		}
		StringBuilder sha1 = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] bs = md.digest(sb.toString().getBytes());
			for(byte b : bs) {
				sha1.append(Integer.toString( (b & 0xff) + 0x100, 16).substring(1));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sha1.toString().equals(signature);
	}
}
