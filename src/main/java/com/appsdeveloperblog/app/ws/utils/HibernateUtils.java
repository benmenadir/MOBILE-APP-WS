package com.appsdeveloperblog.app.ws.utils;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.common.util.impl.LoggerFactory;

import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;


public class HibernateUtils {

    private static final SessionFactory sessionFactory;
    private static final Logger LOG = LoggerFactory.logger(HibernateUtils.class);
    static {
        Configuration conf = new Configuration();
        conf.configure();

        try {
            LOG.info("<<<<<<<<<<<<< message dans le try du serveur >>>>>>>>>>>>>>>>>>");

            sessionFactory = new Configuration().configure().buildSessionFactory();

        }catch (HibernateException e){
            LOG.info("<<<<<<<<<<<<<<< message erreur du serveur >>>>>>>>>>>>>>>>>>>>");
            System.out.println("Initial SessionFactory creation field." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
