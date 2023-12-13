package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class EmployeeView {
    private Stage stage;
    private Button booksButton;
    private String username;

    public EmployeeView(String user_name) {
        this.stage = new Stage();
        this.username = user_name;

        stage.setTitle("Employee");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        // scene.setFill(Color.rgb(255, 199, 185));
        this.stage.setScene(scene);
        initializeSceneTitle(gridPane);
        addButtons(gridPane);

        stage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.setStyle("-fx-background-color: rgb(245, 217, 208);");
    }


    private void initializeSceneTitle(GridPane gridPane) {
        Text sceneTitle = new Text("Welcome, " + username + "!");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);
        GridPane.setHalignment(sceneTitle, javafx.geometry.HPos.CENTER); // centered the Welcome admin text
    }

    private void addButtons(GridPane gridPane) {
        booksButton = new Button("Books");
        booksButton.setMinSize(200, 100);
        booksButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        gridPane.add(booksButton, 0, 1);
    }

    public void addBooksButtonListener(EventHandler<ActionEvent> booksButtonListener) {
        booksButton.setOnAction(booksButtonListener);
    }
}
