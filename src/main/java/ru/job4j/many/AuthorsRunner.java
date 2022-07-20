package ru.job4j.many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class AuthorsRunner {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book one = new Book("Отцы и дети");
            Book two = new Book("Преступление и наказание");
            Book three = new Book("Бесы");
            Book four = new Book("Война и мир");
            Book five = new Book("Двенадцать стульев");

            Author tolstoy = new Author("Л.Н.Толстой");
            Author dostoevsky = new Author("Ф.М.Достоевский");
            Author turgenev = new Author("И.С.Тургенев");
            Author ilf = new Author("И.А.Ильф");
            Author petrov = new Author("Е.П.Петров");

            tolstoy.getBooks().add(four);
            dostoevsky.getBooks().add(two);
            dostoevsky.getBooks().add(three);
            turgenev.getBooks().add(one);
            ilf.getBooks().add(five);
            petrov.getBooks().add(five);

            session.persist(tolstoy);
            session.persist(dostoevsky);
            session.persist(turgenev);
            session.persist(ilf);
            session.persist(petrov);

            Author author = session.get(Author.class, 5);
            session.remove(author);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
