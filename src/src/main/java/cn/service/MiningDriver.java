package cn.service;

import java.util.ArrayList;
import java.util.List;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import cn.dao.LinkDao;
import cn.model.ClusterKeyWords;
import cn.model.TextLinkSen;
import net.sf.json.JSONArray;

public class MiningDriver {

	private RConnection c;
	private RList  rlist;
	private JSONArray arry;
	public void setArray(JSONArray arry) {
		this.arry=arry;
	}
	public JSONArray getArry() {
		return this.arry;
	}
	public MiningDriver() {

	}
	
	public void center(String sessionid) {
		//File directory = new File("page");
		String dire = System.getProperty("user.dir")+"/"+sessionid+"/page";
		System.out.println("qqqqqqqqqqqqqqq");
		List  listMax = new ArrayList();
		try {
			c = new RConnection("127.0.0.1");
			c.eval("source('~/R workspace/Untitled112.R')");
            rlist = c.eval("tesb('"+dire+"')").asList();
            RList rlist0 = rlist.at(0).asList().at(0).asList();
            REXP rexp1 = rlist.at(0).asList().at(1);
            String[] txtnn = rlist.at(1)._attr().asList().at(0).asStrings();
            RList txtcon = rlist.at(1).asList();
            String[]  txtname = rexp1._attr().asList().at(0).asStrings();
            int[] num = rexp1.asIntegers();
            for(int i = 0; i<rlist0.capacity();i++) {
	            	List<TextLinkSen> txtlist = new ArrayList<TextLinkSen>();
	            	String name = "cluster"+i+":";
	            	String[] list = rlist0.at(i).asStrings();
	            	for(int j=0;j<txtname.length;j++) {
	            		if(num[j] == i+1) {
	            			//find url
	            			LinkDao tLink = new LinkDao();
	            			String nnpp = txtname[j].replaceAll(".txt", "");
	            			String urlpp = tLink.selectUrl(nnpp,sessionid);
	            			//find sentence
	            			for(int k =0;k<txtnn.length;k++) {
	            				if(txtname[j].equals(txtnn[k])) {
	            					String namepp = txtcon.at(k).asList().at(0).asString();
	            					String[] keypp = txtcon.at(k).asList().at(1).asStrings();
	            					
	    	            			TextLinkSen textLinkSen = new TextLinkSen(txtname[j],urlpp,namepp,keypp);
	    	            			txtlist.add(textLinkSen);
	            				}
	            			}
	            			

	            		}
	            	}
	            	ClusterKeyWords listKeyWords = new ClusterKeyWords(name, list,txtlist);
	            	listMax.add(listKeyWords);
            }
            arry=JSONArray.fromObject(listMax);
            
		
		} catch (RserveException | REXPMismatchException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	


}
