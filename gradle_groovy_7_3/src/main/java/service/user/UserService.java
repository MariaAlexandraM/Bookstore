package service.user;

import model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User findById(Long id);
    boolean save(User user);
    void removeAll();
    void deleteById(Long id);
    boolean updateUser(User user);
    boolean existsByUsername(String email);

}
