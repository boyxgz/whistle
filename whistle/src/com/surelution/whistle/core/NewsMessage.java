/**
 * 
 */
package com.surelution.whistle.core;

import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="mailto:guangzong.syu@gmail.com">Guangzong</a>
 *
 */
public class NewsMessage extends Message {

	private ArrayList<ArticleMessage> articles = new ArrayList<NewsMessage.ArticleMessage>();

	public static final String KEY_Articles = "Articles";
	public static final String KEY_Item = "item";
	public static final String KEY_Title = "Title";
	public static final String KEY_Description = "Description";
	public static final String KEY_PicUrl = "PicUrl";
	public static final String KEY_Url = "Url";
	public static final String KEY_ArticleCount = "ArticleCount";

	public NewsMessage() {
		super(KEY_Articles, null, false);
	}
	
	public void add(String title, String description, String picUrl, String url) {
		ArticleMessage am = new ArticleMessage(title, description, picUrl, url);
		articles.add(am);
	}
	
	public int getArticleCount() {
		return articles.size();
	}

	/**
	 * 
	 * @return
	 */
	protected List<Message> getFellows() {
		Message ac = new Message(KEY_ArticleCount, String.valueOf(getArticleCount()), false);
		Message mt = new Message(Message.KEY_MsgType, Message.Msg_Type_NEWS);
		List<Message> ms = new ArrayList<Message>();
		ms.add(ac);
		ms.add(mt);
		return ms;
	}

	/* (non-Javadoc)
	 * @see com.surelution.wxmp.Message#getMessage()
	 */
	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		for(ArticleMessage am : articles) {
			sb.append(am.toXml());
		}
		return sb.toString();
	}

	public class ArticleMessage extends Message {
		private String title;
		private String description;
		private String picUrl;
		private String url;

		/**
		 * @param title
		 * @param description
		 * @param picUrl
		 * @param url
		 */
		public ArticleMessage(String title, String description, String picUrl,
				String url) {
			super(KEY_Item, null, false);
			this.title = title;
			this.description = description;
			this.picUrl = picUrl;
			this.url = url;
		}

		public String getTitle() {
			return title;
		}
		public String getDescription() {
			return description;
		}
		public String getPicUrl() {
			return picUrl;
		}
		public String getUrl() {
			return url;
		}
		
		public String getMessage() {
			Message title = new Message(KEY_Title, this.title);
			Message description = new Message(KEY_Description, this.description);
			Message picUrl = new Message(KEY_PicUrl, this.picUrl);
			Message url = new Message(KEY_Url, this.url);
			StringBuilder sb = new StringBuilder();
			sb.append(title.toXml());
			sb.append(description.toXml());
			sb.append(picUrl.toXml());
			sb.append(url.toXml());
			return sb.toString();
		}
	}
}
