package ru.job4j.hql;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class HbmRun {
    private static final CandidateDbStore STORE = new CandidateDbStore();

    public static void main(String[] args) {
        STORE.init();
        Session session = STORE.getSession();
        session.beginTransaction();
        List<Candidate> candidates = addInDb(session);
        STORE.findAll();
        STORE.findById(candidates.get(1).getId());
        STORE.findByName(candidates.get(2).getName());
        STORE.update();
        STORE.delete(candidates.get(0).getId());
        session.getTransaction().commit();
        session.close();
        STORE.destroy();
    }

    private static List<Candidate> addInDb(Session session) {
        Candidate one = new Candidate("Ivan", 5, 20000);
        Candidate two = new Candidate("Alexey", 3, 15000);
        Candidate three = new Candidate("Marina", 4, 16000);
        List<Candidate> candidateList = new ArrayList<>();
        candidateList.add(one);
        candidateList.add(two);
        candidateList.add(three);
        session.save(one);
        session.save(two);
        session.save(three);
        return candidateList;
    }
}
