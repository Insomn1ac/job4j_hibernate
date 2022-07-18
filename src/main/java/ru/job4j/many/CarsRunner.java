package ru.job4j.many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class CarsRunner {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            List<CarModel> hondaCars = List.of(
                    new CarModel("Accord"),
                    new CarModel("Civic"),
                    new CarModel("CR-V"),
                    new CarModel("HR-V"),
                    new CarModel("Jazz")
            );
            for (CarModel model : hondaCars) {
                session.save(model);
            }

            CarBrand honda = new CarBrand("Honda");
            honda.addModel(session.load(CarModel.class, 1));
            honda.addModel(session.load(CarModel.class, 2));
            honda.addModel(session.load(CarModel.class, 3));
            honda.addModel(session.load(CarModel.class, 4));
            honda.addModel(session.load(CarModel.class, 5));

            session.save(honda);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
