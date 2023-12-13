package service.user;

import model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User findById(Long id);
    boolean save(User user);

}
