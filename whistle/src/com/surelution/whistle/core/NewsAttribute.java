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
public class NewsAttribute extends Attribute {

	private ArrayList<ArticleAttribute> articles = new ArrayList<NewsAttribute.ArticleAttribute>();

	public static final String KEY_Articles = "Articles";
	public static final String KEY_Item = "item";
	public static final String KEY_Title = "Title";
	public static final String KEY_Description = "Description";
	public static final String KEY_PicUrl = "PicUrl";
	public static final String KEY_Url = "Url";
	public static final String KEY_ArticleCount = "ArticleCount";

	public NewsAttribute() {
		super(KEY_Articles, null, false);
	}
	
	public void add(String title, String description, String picUrl, String url) {
		ArticleAttribute am = new ArticleAttribute(title, description, picUrl, url);
		articles.add(am);
	}
	
	public int getArticleCount() {
		return articles.size();
	}

	/**
	 * 
	 * @return
	 */
	protected List<Attribute> fellows() {
		Attribute ac = new Attribute(KEY_ArticleCount, String.valueOf(getArticleCount()), false);
		Attribute mt = new Attribute(Attribute.KEY_MsgType, Attribute.Msg_Type_NEWS);
		List<Attribute> ms = new ArrayList<Attribute>();
		ms.add(ac);
		ms.add(mt);
		return ms;
	}

	/* (non-Javadoc)
	 * @see com.surelution.wxmp.Message#getMessage()
	 */
	@Override
	public String getValue() {
		StringBuilder sb = new StringBuilder();
		for(ArticleAttribute am : articles) {
			sb.append(am.toXml());
		}
		return sb.toString();
	}

	public class ArticleAttribute extends Attribute {
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
		public ArticleAttribute(String title, String description, String picUrl,
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
		
		public String getValue() {
			Attribute title = new Attribute(KEY_Title, this.title);
			Attribute description = new Attribute(KEY_Description, this.description);
			Attribute picUrl = new Attribute(KEY_PicUrl, this.picUrl);
			Attribute url = new Attribute(KEY_Url, this.url);
			StringBuilder sb = new StringBuilder();
			sb.append(title.toXml());
			sb.append(description.toXml());
			sb.append(picUrl.toXml());
			sb.append(url.toXml());
			return sb.toString();
		}
	}
}
