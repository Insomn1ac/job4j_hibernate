package ru.job4j.hql;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "vacancies_base")
public class VacanciesBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vacancy> vacancies = new HashSet<>();

    public VacanciesBase() {
    }

    public VacanciesBase(Set<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(Set<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public void addVacancy(Vacancy vacancy) {
        vacancies.add(vacancy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VacanciesBase that = (VacanciesBase) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VacanciesBase{"
                + "vacancies=" + vacancies
                + '}';
    }
}
