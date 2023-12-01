package repository.user;

import com.mysql.cj.util.DnsSrv;
import model.User;
import model.validator.Notification;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password); // cauta in bd sa vedem daca exista useru sau nu

    boolean save(User user);
    void removeAll();

    // verific daca useru are deja mailu in bd
    boolean existsByUsername(String username); // momentan ne referim la mail ca username, is aceeasi chestie

}
