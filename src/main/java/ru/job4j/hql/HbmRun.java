package ru.job4j.hql;

import org.hibernate.Session;

import java.util.List;

public class HbmRun {
    private static final CandidateDbStore STORE = new CandidateDbStore();

    public static void main(String[] args) {
        STORE.init();
        Session session = STORE.getSession();
        session.beginTransaction();
        List<Candidate> candidates = session.createQuery("select distinct c from Candidate c "
                        + "join fetch c.vacanciesBase v "
                        + "join fetch v.vacancies ", Candidate.class)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        candidates.forEach(System.out::println);
        STORE.destroy();
    }

    private static void addInDb(Session session) {
        List<Vacancy> vacancies = List.of(
                new Vacancy("Developer"),
                new Vacancy("HR"),
                new Vacancy("Sales")
        );
        vacancies.forEach(session::save);
        VacanciesBase base = new VacanciesBase();
        base.addVacancy(session.load(Vacancy.class, 1));
        base.addVacancy(session.load(Vacancy.class, 2));
        base.addVacancy(session.load(Vacancy.class, 3));
        session.save(base);
        Candidate one = new Candidate("Ivan", 5, 20000, base);
        Candidate two = new Candidate("Alexey", 3, 15000, base);
        Candidate three = new Candidate("Marina", 4, 16000, base);
        session.save(one);
        session.save(two);
        session.save(three);
    }
}
