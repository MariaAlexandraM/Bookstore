package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Role;
import model.User;
import service.user.UserService;

import java.util.List;

public class UsersView {
    private Stage stage;

    public UsersView(UserService userService) {
        this.stage = new Stage();

        stage.setTitle("Users");

        VBox vBox = new VBox();
        initializeVBox(vBox);

        TableView<User> tableView = createUsersTable(userService.findAll());
        vBox.getChildren().add(tableView);

        Scene scene = new Scene(vBox, 800, 600);
        stage.setScene(scene);

        stage.show();
    }

    private void initializeVBox(VBox vBox) {
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
    }

    // TODO: add rights column
    private TableView<User> createUsersTable(List<User> users) {
        TableView<User> tableView = new TableView<>();
        ObservableList<User> data = FXCollections.observableArrayList(users);
        tableView.setMinWidth(700);

        TableColumn<User, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<User, String> roleColumn = getUserStringTableColumn();

        tableView.getColumns().addAll(idColumn, usernameColumn, passwordColumn, roleColumn);

        tableView.setItems(data);

        return tableView;
    }

    private static TableColumn<User, String> getUserStringTableColumn() {
        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(it -> {
            User user = it.getValue();
            List<Role> roles = user.getRoles();
            StringBuilder rolesStringBuilder = new StringBuilder();

            for (Role role : roles) {
                rolesStringBuilder.append(role.getRole()).append(" ");
            }

            String rolesResult = rolesStringBuilder.toString();

            return new SimpleStringProperty(rolesResult);
        });
        return roleColumn;
    }

}
