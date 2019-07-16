package com.appsdeveloperblog.app.ws;

import com.appsdeveloperblog.app.ws.utils.HibernateUtils;
import org.hibernate.Session;

public class Program {

    public static void main (String[] args) {
        System.out.println("Hibernate");
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.close();
    }
}
