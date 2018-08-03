package cn.model;

import javax.servlet.http.HttpSession;

public class Place {
private String[] placeName;
private int num;

public String[] getPlaceName() {
	return this.placeName;
}
public void setPlaceName(String[] placeName) {
	//for(int i=0;i<placeName.length;i++)
	this.placeName=placeName;
}
public int getNum() {
	return this.num;
}
public void setNum(String num) {
	this.num=Integer.parseInt(num);
}

}
