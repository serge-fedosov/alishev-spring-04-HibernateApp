package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;

public class App {
    public static void main( String[] args ) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = session.get(Person.class, 1);
            System.out.println("Получили человека из таблицы");

            session.getTransaction().commit();
            // session.close() вызывается автоматически после коммита

            System.out.println("Сессия завершилась");

            // открываем новую сессию и транзакцию
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            System.out.println("Внутри второй транзакции");

            // прикрепляем объект к сессии
            person = session.merge(person);
            Hibernate.initialize(person.getItems());

            session.getTransaction().commit();

            System.out.println("Вне второй сессии");

            // это работает т.к. связанные товары были подгружены
            System.out.println(person.getItems());

        } finally {
            sessionFactory.close();
        }
    }
}
