package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import java.util.List;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = initMQ();
        try(Connection connection = connectionFactory.newConnection()){
            PersonApi personApi = new PersonApiImpl(connection);
            personApi.savePerson(1L, "Ivan", "Ivanov", "Ivanovych");
            personApi.savePerson(2L, "Emir", "Suleimanov", "Rinatovich");
            personApi.savePerson(2L, "Emir", "Suleimanov", "Rinatovich");
            personApi.savePerson(3L, "Petya", "Petrov", "Petrovich");
            personApi.savePerson(4L, "Temp_name", "Temp_LastName", "Temp_MiddleName");

            Thread.sleep(2000); // ожидание для обработки данных на сервере
            System.out.println("\nВсе пользователи");
            List<Person> personList = personApi.findAll();
            for (Person person : personList) {
                System.out.println(person);
            }
            System.out.println();

            personApi.savePerson(5L, "Mia", "Mia", "Mia");
            System.out.println(personApi.findPerson(5L));

            personApi.deletePerson(2L);
            personApi.deletePerson(8L);

            System.out.println("\nВсе пользователи");
            personList = personApi.findAll();
            for (Person person : personList) {
                System.out.println(person);
            }

        }
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }
}

// #типа обмена (Exchange types)
//Direct - используется для маршрутизации сообщений с определенным ключом маршрутизации (Routing key) на очередь с соответствующим ключом.
// Сообщение отправляется в очередь, если ключ маршрутизации сообщения точно совпадает с ключом маршрутизации очереди.
//
//Fanout - используется для маршрутизации сообщений на все связанные с обменом очереди.
// При использовании Fanout типа обмена, все сообщения отправляются на все связанные с обменом очереди.
//
//Topic - используется для маршрутизации сообщений, используя шаблоны ключей маршрутизации.
// Шаблон может содержать знаки * и #. * - заменяет одно слово в ключе маршрутизации, # - заменяет одно или несколько слов в ключе маршрутизации.
//
//Headers - используется для маршрутизации сообщений, основываясь на заголовках сообщения.
// При использовании этого типа обмена, обмен связывается с очередями по заголовкам сообщения, которые описываются в соответствующем связывании.
// Сообщение отправляется в очередь, если заголовок сообщения точно соответствует заголовку, указанному в связывании.


// #queueDeclare используется для объявления очереди.
// Он позволяет создавать новую очередь с указанным именем или получить ссылку на уже существующую. Параметры:
//queue: имя очереди
//durable: флаг, указывающий, следует ли сохранять очередь на диске, чтобы она могла выжить при перезапуске брокера
//exclusive: флаг, указывающий, является ли очередь эксклюзивной для подключения, которое ее создало, и будет ли она автоматически удалена, когда подключение закроется
//autoDelete: флаг, указывающий, должна ли очередь автоматически удаляться, когда последний подписчик отписывается от нее
//arguments: дополнительные аргументы, используемые для настройки очереди, например, TTL сообщений или ограничений на размер очереди
//Метод queueDeclare возвращает объект AMQP.Queue.DeclareOk, содержащий информацию о созданной очереди, такую как ее имя и количество сообщений, находящихся в очереди в данный момент.