package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class OrdersStoreTest {
    private final BasicDataSource pool = new BasicDataSource();
    private final OrdersStore store = new OrdersStore(pool);

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/scripts/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void delete() throws SQLException {
        String sql = "drop table if exists orders";
        pool.getConnection().prepareStatement(sql).executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        store.save(Order.of("name1", "description1"));
        List<Order> all = (List<Order>) store.findAll();
        assertEquals(all.size(), 1);
        assertEquals(all.get(0).getDescription(), "description1");
        assertEquals(all.get(0).getId(), 1);
    }

    @Test
    public void whenSaveOrderAndFindByIdWithTimeOfCreation() {
        Order order = store.save(Order.of(1, "name2"));
        Order rsl = store.findById(order.getId());
        assertEquals(rsl.getId(), order.getId());
        assertEquals(rsl.getCreated(), order.getCreated());
    }

    @Test
    public void whenSaveOrderAndFindByName() {
        Order order = store.save(Order.of(1, "name2"));
        Order rsl = store.findByName(order.getName());
        assertEquals(rsl.getId(), order.getId());
        assertEquals(rsl.getCreated(), order.getCreated());
    }

    @Test
    public void whenUpdateOrder() {
        store.save(Order.of(1, "name2"));
        Order updatedOrder = new Order(1, "name3", "updated order", Timestamp.valueOf(LocalDateTime.now()));
        store.update(updatedOrder);
        assertNotNull(store.findByName("name3"));
        assertNull(store.findByName("name2"));
        assertEquals(store.findById(1).getDescription(), updatedOrder.getDescription());
    }
}