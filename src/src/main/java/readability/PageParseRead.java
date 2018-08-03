package readability;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PageParseRead {
	private final String USER_AGENT = "Mozilla/5.0";
	private final String  savePath= "/Users/ustctll/Desktop/demo1";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "";
		try {
			Document doc = Jsoup.connect(url).execute().parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
