package ndw.eugene.services;

import ndw.eugene.DAO.UserDAO;
import ndw.eugene.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void createUser(User user){
        userDAO.createUser(user);
    }

    public User getUserByLoginAndPassword(String login, String password){
        return userDAO.getUserByLoginAndPassword(login, password);
    }
}
