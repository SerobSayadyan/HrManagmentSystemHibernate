package org.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private HibernateUtil(){};

    public static SessionFactory getSessionFactory() {
        synchronized (Object.class) {
            if (sessionFactory == null) {
                try {
                    StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
                    Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
                    sessionFactory = metadata.getSessionFactoryBuilder().build();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return sessionFactory;
    }

    public static void shutDown() {
        sessionFactory.close();
    }

}
