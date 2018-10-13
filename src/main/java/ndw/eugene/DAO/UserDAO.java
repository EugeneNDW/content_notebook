package ndw.eugene.DAO;

import ndw.eugene.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUserByLoginAndPassword(String login, String password){
       return jdbcTemplate.queryForObject("SELECT * FROM users WHERE login=? AND password=?", new Object[]{login, password}, (resultSet, i)->{
           User user = new User();
           user.setId(resultSet.getInt("id"));
           user.setLogin(resultSet.getString("login"));
           user.setPassword(resultSet.getString("password"));
           return user;
       });
    }

    public void createUser(User user){
        jdbcTemplate.update("INSERT INTO users(login, password) VALUES(?,?);", user.getLogin(), user.getPassword());
    }
}
