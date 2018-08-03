package cn.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.dao.LinkDao;
import cn.model.PlaceLink;
import readability.ReadabilityDriver;

public class NormalLinkSearch{

	private final String USER_AGENT = "Mozilla/5.0";
	private final String  savePath= "/prepage";
	

	public void saveDocument(PlaceLink placeLink) {

		try {
			
			//System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3,SSLv2Hello");
			System.setProperty(" jsse.enableSNIExtension","false");
			org.jsoup.Connection.Response response = Jsoup.connect(placeLink.getTextUrl()).
					ignoreHttpErrors(true).
					timeout(3000).
		//			validateTLSCertificates(true).
					followRedirects(false).
					execute();
			if(response.statusCode() == 200) {
				//String titlename = grabTitle();
				Document doc = response.parse();
				String filename = System.getProperty("user.dir")+"/"+placeLink.getUserSession_id()+savePath+"/"+placeLink.getTextName()+".html";
				ReadabilityDriver readDriver = new ReadabilityDriver();
				File file = new File(filename);
				FileUtils.writeStringToFile(file, doc.outerHtml(), "UTF-8");
				readDriver.readabilityDriver(placeLink.getTextName(),placeLink.getUserSession_id());
				//connect database
				LinkDao tLink = new LinkDao();
				tLink.insertUrl(placeLink);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("time out2");
			return;
		}	
	}
	
	
	

}