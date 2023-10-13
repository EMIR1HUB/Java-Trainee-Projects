package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class ApiApp {
  public static void main(String[] args) throws Exception {
    // Тут пишем создание PersonApi, запуск и демонстрацию работы
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();
    PersonApi personApi = applicationContext.getBean(PersonApi.class);

    personApi.savePerson(1L, "Ivan", "Ivanov", "Ivanovych");
    personApi.savePerson(2L, "Emir", "Suleimanov", "Rinatovich");
    personApi.savePerson(2L, "Emir", "Suleimanov", "Rinatovich");
    personApi.savePerson(3L, "Petya", "Petrov", "Petrovich");
    personApi.savePerson(4L, "Temp_name", "Temp_LastName", "Temp_MiddleName");

    Thread.sleep(2000); // ожидание для обработки данных на сервере
    System.out.println("\nВсе пользователи");
    List<Person> personList = personApi.findAll();
    for (Person p : personList) {
      System.out.println(p.getName() + " " + p.getLastName());
    }
    System.out.println();

    personApi.savePerson(5L, "Mia", "Mia", "Mia");
    System.out.println(personApi.findPerson(5L));

    personApi.deletePerson(2L);
    personApi.deletePerson(8L);

    System.out.println("\nВсе пользователи");
    personList = personApi.findAll();
    for (Person p : personList) {
      System.out.println(p.getName() + " " + p.getLastName());
    }

    applicationContext.close();
  }
}
