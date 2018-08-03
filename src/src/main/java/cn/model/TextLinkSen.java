package cn.model;

public class TextLinkSen {

	private String textName;
	private String urlLink;
	private String textSen;
	private String[] reKeyWords;
	public String[] getReKeyWords() {
		return this.reKeyWords;
	}
	public void setReKeyWords(String[] reKeyWords) {
		this.reKeyWords = reKeyWords;
	}
	public String getTextName() {
		return this.textName;
	}
	public void setTextName(String textName) {
		this.textName = textName;
	}
	public String getUrlLink() {
		return this.urlLink;
	}
	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}
	public String getTextSen() {
		return this.textSen;
	}
	public void setTextSen(String textSen) {
		this.textSen = textSen;
	}
	public   TextLinkSen() {
		// TODO Auto-generated constructor stub
	}
	public   TextLinkSen(String textName,String urlLink,String textSen,String[] reKeyWords) {
		// TODO Auto-generated constructor stub
		this.setTextName(textName);
		this.setTextSen(textSen);
		this.setUrlLink(urlLink);
		this.setReKeyWords(reKeyWords);
	}



}
