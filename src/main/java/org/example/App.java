package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class App {
    public static void main( String[] args ) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = session.get(Person.class, 2);

            Item newItem = new Item("Item from hibernate", person);

            person.getItems().add(newItem); // не обязательно, в БД всё будет правильно.
            // но в кэшированном объекте останутся старые данные, поэтому items у person тоже обновляем
            // хорошей практикой является обновление отношений с двух сторон

            session.save(newItem);

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }
}
