package cn.service;


import java.io.File;
import java.io.FileNotFoundException;

import cn.dao.LinkDao;
import cn.model.Place;
import net.sf.json.JSONArray;

public class PlaceNameSearch {
 

 public JSONArray hotTopic(Place place,String sessionid) throws Exception {
	 File file1=new File(System.getProperty("user.dir")+"/"+sessionid+"/prepage");
	 file1.mkdirs();
	 File file2=new File(System.getProperty("user.dir")+"/"+sessionid+"/page");
	 file2.mkdirs();
	 LinkDao linkDao=new LinkDao();
	 linkDao.deleteUrl(sessionid);
	 for(int i=0;i<place.getNum();i++) {
		 System.out.println(place.getPlaceName()[i]);
		 GoogleLinkSearch googleLinkSearch=new GoogleLinkSearch(place.getPlaceName()[i], sessionid);
		 googleLinkSearch.pageLink();
	 }
	 MiningDriver miningDriver=new MiningDriver();
	 miningDriver.center(sessionid);
	 linkDao.deleteUrl(sessionid);
	 deletefile(System.getProperty("user.dir")+"/"+sessionid);
	 return miningDriver.getArry();
	
}
 public  boolean deletefile(String delpath) throws Exception {
     try {

         File file = new File(delpath);
         // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
         if (!file.isDirectory()) {
             file.delete();
         } else if (file.isDirectory()) {
             String[] filelist = file.list();
             for (int i = 0; i < filelist.length; i++) {
                 File delfile = new File(delpath + "/" + filelist[i]);
                 if (!delfile.isDirectory()) {
                     delfile.delete();
                     System.out.println(delfile.getAbsolutePath() + "删除文件成功");
                 } else if (delfile.isDirectory()) {
                     deletefile(delpath + "/" + filelist[i]);
                 }
             }
             System.out.println(file.getAbsolutePath() + "删除成功");
             file.delete();
         }

     } catch (FileNotFoundException e) {
         System.out.println("deletefile() Exception:" + e.getMessage());
     }
     return true;
 }
}


