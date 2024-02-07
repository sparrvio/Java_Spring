package school21.spring.service.application;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PrintStream originalOut = System.out;

        try
            (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml")){;
            File file = new File("target/outputLog.txt");
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            System.setOut(ps);
            UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);

            User userToUpdate = new User(1, "NewEmail");
            User user1 = new User("user1@example.com");
            usersRepository.save(user1);
            User user2 = new User("user2@example.com");
            usersRepository.save(user2);
            User user3 = new User("user3@example.com");
            usersRepository.save(user3);

            List<User> result = new ArrayList<>();
            result = usersRepository.findAll();
            System.err.println("\nВывод всех user");

            result.stream().forEach(System.err::println);

            System.err.println("\nПоиск user по id \n" + usersRepository.findById(1L).toString());

            System.err.println("\nПоиск user по email \n" + usersRepository.findByEmail("user2@example.com"));

            usersRepository.update(userToUpdate);
            System.err.println("\nUPDATE user \n" + usersRepository.findById(1L).toString());

            result = usersRepository.findAll();
            System.err.println("\nВывод всех user");
            result.stream().forEach(System.err::println);


            System.err.println("\nDELETE user");
            usersRepository.delete(1L);
            result = usersRepository.findAll();
            result.stream().forEach(System.err::println);


            usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);

            user1.setEmail("newUser1@google.com");
            usersRepository.save(user1);
            user2.setEmail("newUser2@google.com");
            usersRepository.save(user2);
            user3.setEmail("newUser3@google.com");
            usersRepository.save(user3);

            result = usersRepository.findAll();
            System.err.println("\n__________________________________________________\n\n" +
                    "Вывод всех user");
            result.stream().forEach(System.err::println);

            System.err.println("\nПоиск user по id \n" + usersRepository.findById(1L).toString());

            System.err.println("\nПоиск user по email \n" + usersRepository.findByEmail("newUser2@google.com"));

            usersRepository.update(userToUpdate);
            System.err.println("\nUPDATE user \n" + usersRepository.findById(1L).toString());

            result = usersRepository.findAll();
            System.err.println("\nВывод всех user");
            result.stream().forEach(System.err::println);

            System.err.println("\nDELETE user");
            usersRepository.delete(1L);
            result = usersRepository.findAll();
            result.stream().forEach(System.err::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.setOut(originalOut);
        }
    }
}
