package cn.model;

public class PlaceLink {
private String url;
private String sessionId;
private String pagename;
public PlaceLink(String textName,String textUrl,String userSession_id) {
	this.pagename = textName;
	this.url = textUrl;
	this.sessionId=userSession_id;
	// TODO Auto-generated constructor stub
}
public PlaceLink() {
	// TODO Auto-generated constructor stub
}
public void setTextName(String textName) {
	this.pagename = textName;
}
public String getTextName() {
	return this.pagename;
	
}
public void setTextUrl(String textUrl) {
	this.url = textUrl;
}
public String getTextUrl() {
	return this.url;
	
}
public void setUserSession_id(String userSession_id) {
	this.sessionId = userSession_id;
}
public String getUserSession_id() {
	return this.sessionId;
	
}

}
