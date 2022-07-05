package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;

public class CandidateDbStore {
    private Session session;
    private StandardServiceRegistry registry;

    public Session getSession() {
        return session;
    }

    public void init() {
        registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            session = sf.openSession();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    public void findAll() {
        Query query = session.createQuery("from Candidate ");
        for (Object candidate : query.getResultList()) {
            System.out.println(candidate);
        }
    }

    public void findById(int id) {
        Query query = session.createQuery("from Candidate where id = :fId");
        query.setParameter("fId", id);
        System.out.println(query.getSingleResult());
    }

    public void findByName(String name) {
        Query query = session.createQuery("from Candidate where name = :fName");
        query.setParameter("fName", name);
        System.out.println(query.setMaxResults(1).getSingleResult());
    }

    public void update() {
        session.createQuery(
                        "update Candidate c set c.salary = :newSalary, c.experience = :newExperience where c.id = :fId"
                )
                .setParameter("newExperience", 4)
                .setParameter("newSalary", 17000)
                .setParameter("fId", 2)
                .executeUpdate();
    }

    public void delete(int id) {
        session.createQuery("delete from Candidate where id = :fId")
                .setParameter("fId", id)
                .executeUpdate();
    }
}
