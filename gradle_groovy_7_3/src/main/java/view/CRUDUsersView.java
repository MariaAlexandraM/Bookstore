package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Book;
import model.Role;
import model.User;
import service.user.UserService;

import java.util.List;

public class CRUDUsersView {
    private Stage stage;
    private TextField idTextField;
    private Button deleteUserButton;
    private Button updateUserButton;
    private ComboBox<String> rolesComboBox;
    private Text actionTarget;

    public CRUDUsersView(UserService userService) {
        this.stage = new Stage();
        stage.setTitle("Users");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        TableView<User> tableView = createUsersTable(userService.findAll());
        gridPane.add(tableView, 0, 0, 2, 1);

        initializeFields(gridPane);

        Scene scene = new Scene(gridPane, 800, 600);
        stage.setScene(scene);

        stage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
    }

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

    private void initializeFields(GridPane gridPane) {
        idTextField = new TextField();
        idTextField.setPromptText("ID");
        gridPane.add(idTextField, 0, 1);

        deleteUserButton = new Button("Delete user");
        gridPane.add(deleteUserButton, 1, 1);

        rolesComboBox = new ComboBox<>();
        rolesComboBox.setPromptText("Select role");
        rolesComboBox.getItems().addAll("administrator", "employee", "customer");
        gridPane.add(rolesComboBox, 2, 1);

        updateUserButton = new Button("Update user");
        gridPane.add(updateUserButton, 3, 1);

        actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK);
        gridPane.add(actionTarget, 1, 6);
    }

    public String getId() {
        return idTextField.getText();
    }

    public String getSelectedRole() {
        return rolesComboBox.getSelectionModel().getSelectedItem();
    }

    public void setActionTargetText(String text) {
        this.actionTarget.setText(text);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteUserButton.setOnAction(deleteButtonListener);
    }

    public void addUpdateButtonListener(EventHandler<ActionEvent> updateButtonListener) {
        updateUserButton.setOnAction(updateButtonListener);
    }
}
