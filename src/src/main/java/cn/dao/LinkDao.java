package cn.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import cn.model.PlaceLink;


public class LinkDao {
	    private Session session;
	    private Transaction transaction;
	    private Query query;
	    HibernateSessionFactory getSession;
	public  String insertUrl(PlaceLink url) {
		String mess="error";
        getSession=new HibernateSessionFactory();
        session=getSession.getSession();
        try{
            transaction=session.beginTransaction();
            session.save(url);
            transaction.commit();
            mess="success";
            getSession.closeSession();
            return mess;
        }catch(Exception e){
            System.out.println("RegisterInfo.error:"+e);
            e.printStackTrace();
            getSession.closeSession();
            return null;
        }

	} 
	public String deleteUrl(String sessionid) {
		String mess="error";
        getSession=new HibernateSessionFactory();
        session=getSession.getSession();
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery("delete from PlaceLink where SESSIONID = :sessionid");
			query.setString("sessionid", sessionid).executeUpdate();
            transaction.commit();
            mess="success";
            getSession.closeSession();
            return mess;
        }catch(Exception e){
            System.out.println("RegisterInfo.error:"+e);
            e.printStackTrace();
            getSession.closeSession();
            return null;
        }
        
	}
	public String selectUrl(String name,String sessionid) {
		//PlaceLink placeLink=new PlaceLink();
		String mess="error";
        getSession=new HibernateSessionFactory();
        session=getSession.getSession();
        try{
            transaction=session.beginTransaction();
            Query q=session.createQuery("from PlaceLink where PAGENAME=:name and SESSIONID=:sessionid");
            List<PlaceLink> pList=q.setParameter("name", name).setParameter("sessionid", sessionid).list();
            mess="success";
            getSession.closeSession();
            return pList.get(0).getTextUrl();
        }catch(Exception e){
            System.out.println("RegisterInfo.error:"+e);
            e.printStackTrace();
            getSession.closeSession();
            return null;
        }
	}

}
