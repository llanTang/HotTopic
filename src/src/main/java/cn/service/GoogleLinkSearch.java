package cn.service;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.model.PlaceLink;

public class GoogleLinkSearch  {
	static String urlPref="https://www.google.com.au/search?";
	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
	private static final int RESULT_OK = 200;
	private String urlQ;
	private String userSession_id;
	protected int pageIndex=0;
	public GoogleLinkSearch (String placename,String sessionid) {
		this.urlQ=placename;
		this.userSession_id=sessionid;
	}
	public void pageLink() {
		Document document=null;
		String nextHref=this.urlPref+"q="+this.urlQ;
		for(int i =0; i < 3;i++) {
			try {
				System.setProperty(" jsse.enableSNIExtension","false");
				//TimeUnit.SECONDS.sleep(10);
				document = Jsoup.connect(nextHref).
						userAgent(USER_AGENT).
						ignoreContentType(true).
						ignoreHttpErrors(true).
						timeout(3000).
		//				validateTLSCertificates(true).
						followRedirects(false).
						get();
				//document = response.parse();
				Elements links = document.select("h3[class=r]");
				System.out.println(links.size());
				nextHref = document.select("td.b").last().select("a[href]").attr("abs:href");
				System.out.println(nextHref);
				for(Element link : links) {
					Elements href = link.select("a[href]");
					String temp = href.attr("abs:href");
					System.out.println(temp);
					if(temp != null && temp.length()!=0) {
						String name=this.userSession_id+this.urlQ+this.pageIndex;
						this.pageIndex++;
						PlaceLink dUrl = new PlaceLink(name, temp,this.userSession_id);
						NormalLinkSearch rUrlRequest = new NormalLinkSearch();
					//	TimeUnit.SECONDS.sleep(10);
						rUrlRequest.saveDocument(dUrl);
					}
				}
			}
			catch (SocketTimeoutException | UnknownHostException |SSLHandshakeException |SocketException e) {
				// TODO: handle exception
				System.out.println("time out");
				continue;
			}
			catch (Exception e) {
				// TODO: handle exception
				System.out.println("time out");
				continue;
			}
		}

		
	}
}
