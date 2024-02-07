package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import org.apache.commons.lang3.RandomStringUtils;

@Component("userServiceBean")
public class UsersServiceImpl implements UsersService {
    UsersRepository<User> usersRepository;

    @Autowired
    public UsersServiceImpl(@Qualifier("jdbcTemplateBean") UsersRepository<User> usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) {
        String password = generatePassword(10);
        User newUser = new User(email, password);
        usersRepository.save(newUser);
        return password;
    }


    private String generatePassword(int length) {
        return RandomStringUtils.random(length, true, true);
    }
}
