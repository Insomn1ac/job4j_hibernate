package ru.job4j.lazy;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "models")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "mark_id")
    private Mark mark;

    public Model() {
    }

    public Model(String name, Mark mark) {
        this.name = name;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Model model = (Model) o;
        return id == model.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Model{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", mark=" + mark
                + '}';
    }
}
