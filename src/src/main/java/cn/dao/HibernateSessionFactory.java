package cn.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

    private SessionFactory sessionFactory;
    
    public HibernateSessionFactory(){
        
    }
    
    public SessionFactory config(){
        try{
            Configuration configuration= new Configuration();
            Configuration configure=configuration.configure("cn/model/hibernate.cfg.xml");
            return configure.buildSessionFactory();
        }catch(Exception e){
        e.getMessage();
        return null;
        }
    }
    
    public Session getSession(){
        sessionFactory=config();
        return sessionFactory.openSession();
    }
    public void closeSession(){
        sessionFactory.close();
    }
    
}