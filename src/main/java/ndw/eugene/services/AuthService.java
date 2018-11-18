package ndw.eugene.services;

import ndw.eugene.DAO.UserDAO;
import ndw.eugene.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private UserService userService;
    private Map<String, User> authorizedUsers;

    @Autowired
    public AuthService(UserService userService) {
        this.userService = userService;
        this.authorizedUsers = new HashMap<>();
    }

    public void authorizeUser(String sessionId, String login, String password){
        User user = userService.getUserByLoginAndPassword(login, password);
        if(user != null){
            authorizedUsers.put(sessionId, user);
        }
    }

    public void registerUser(String login, String password){
        User user = new User(login, password);
        userService.createUser(user);
    }

    public User returnUserIfAuthorized(String sessionId){
        User user = authorizedUsers.get(sessionId);

        if(user != null){
            return user;
        } else {
            throw new IllegalArgumentException("Unauthorized");
        }
    }

    public void unAuthUser(String sessionId){
        authorizedUsers.remove(sessionId);
    }
}
