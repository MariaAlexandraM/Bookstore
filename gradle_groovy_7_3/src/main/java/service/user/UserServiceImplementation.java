package service.user;

import model.User;
import repository.user.UserRepository;

import java.util.List;

public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        try {
            return userRepository.findById(id);//.orElseThrow(() -> new IllegalArgumentException("User with id = %d was not found".formatted(id)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void removeAll() {
        userRepository.removeAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    public boolean existsByUsername(String email) {
        return false;
    }
}
