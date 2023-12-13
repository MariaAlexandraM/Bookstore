package service.user;

import model.User;

import java.util.List;
import java.util.Optional;

public interface UserSevice {

    List<User> findAll();
    User findById(Long id);
    boolean save(User user);

}
