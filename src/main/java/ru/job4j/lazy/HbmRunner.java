package ru.job4j.lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HbmRunner {
    public static void main(String[] args) {
        List<Mark> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            session.persist(initMark());
            list = session.createQuery("select distinct mark FROM Mark mark join fetch mark.models").list();
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (Mark mark : list) {
            for (Model model : mark.getModels()) {
                System.out.println(model);
            }
        }
    }

    public static Mark initMark() {
        Mark toyota = new Mark("Toyota");
        toyota.setModels(List.of(
                new Model("Corolla", toyota),
                new Model("RAV-4", toyota),
                new Model("Prius", toyota)
        ));
        return toyota;
    }
}