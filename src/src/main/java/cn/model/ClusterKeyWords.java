package cn.model;

import java.util.List;


public class ClusterKeyWords {

	private String clusterName;
	private String[] list;
	private List<TextLinkSen> txtlist;
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public String getClusterName() {
		return this.clusterName;
	}
	public void setList(String[] list) {
		this.list = list;
	}
	public String[] getList() {
		return this.list;
	}
	public void setTxtlist(List<TextLinkSen> txtlist) {
		this.txtlist = txtlist;
	}
	public List<TextLinkSen> getTxtlist() {
		return this.txtlist;
	}
	public ClusterKeyWords(String clusterName,String[] list,List<TextLinkSen>txtlist) {
		this.setClusterName(clusterName);
		this.setList(list);
		this.setTxtlist(txtlist);
	}
	public ClusterKeyWords() {
		
	}

}
