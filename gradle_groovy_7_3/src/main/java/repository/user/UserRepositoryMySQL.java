package repository.user;
import model.Book;
import model.User;
import model.builder.BookBuilder;
import model.builder.UserBuilder;
import model.validator.Notification;
import repository.security.RightsRolesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;
import static java.util.Collections.singletonList;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    // TODO
    @Override
    public List<User> findAll() {
        String sql = "select * from user";
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // SQL Injection Attacks should not work after fixing functions
    // Be careful that the last character in sql injection payload is an empty space
    // alexandru.ghiurutan95@gmail.com' and 1=1; --
    // ' or username LIKE '%admin%'; --

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();

        try {
            String fetchUserSql = "select * from `" + USER + "` where `username` = ? and `password` = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(fetchUserSql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet userResultSet = preparedStatement.executeQuery();

                if (userResultSet.next()) {
                    User user = new UserBuilder()
                            .setUsername(userResultSet.getString("username"))
                            .setPassword(userResultSet.getString("password"))
                            .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                            .build();

                    findByUsernameAndPasswordNotification.setResult(user);
                } else {
                    findByUsernameAndPasswordNotification.addError("Invalid username or password");
                    return findByUsernameAndPasswordNotification;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findByUsernameAndPasswordNotification.addError("Something is wrong with the database");
        }

        return findByUsernameAndPasswordNotification;
    }


    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("insert into user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS); // odata ce se creeaza cu succes un user, ne returneaza si folosim mai jos id-u acestui user
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            // cu id-u asta ii atribuim rolurile
            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "delete from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsByUsername(String email) {
        try {
            String fetchUserSql = "select * from `" + USER + "` where `username` = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(fetchUserSql)) {
                preparedStatement.setString(1, email);

                ResultSet userResultSet = preparedStatement.executeQuery();
                return userResultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new UserBuilder()
                .setUsername(resultSet.getString("username"))
                .setPassword(resultSet.getString("password"))
                .setRoles(rightsRolesRepository.findRolesForUser(resultSet.getLong("id")))
                .build();
    }

}