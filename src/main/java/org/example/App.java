package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main( String[] args ) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = new Person("test person", 30);

            Item newItem = new Item("Item from hibernate 2", person);

            // т.к. пользователь новый и товаров у него нет, создаём новый список товаров из одного товара
            person.setItems(new ArrayList<Item>(Collections.singletonList(newItem)));

            session.save(person);

            session.save(newItem);

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }
}
