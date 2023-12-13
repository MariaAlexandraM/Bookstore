package repository.user;

import model.User;
import model.validator.Notification;

import java.util.List;

public interface UserRepository {


    List<User> findAll();
    Notification<User> findByUsernameAndPassword(String username, String password); // cauta in bd sa vedem daca exista useru sau nu
    boolean save(User user);
    void removeAll();
    void deleteById(Long id);
    User findById(Long id);
    boolean updateUser(User user);
    boolean existsByUsername(String email);

}
