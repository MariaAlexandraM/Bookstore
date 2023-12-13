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

public class CustomerView {
    private Stage stage;
    private Button booksButton;

    public CustomerView() {
        this.stage = new Stage();

        stage.setTitle("Customer");
        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        this.stage.setScene(scene);
        initializeSceneTitle(gridPane);
        addButton(gridPane);

        stage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initializeSceneTitle(GridPane gridPane) {
        Text sceneTitle = new Text("Welcome, customer!");
        sceneTitle.setFont(Font.font("Tahome", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);
        GridPane.setHalignment(sceneTitle, javafx.geometry.HPos.CENTER);
    }

    private void addButton(GridPane gridPane) {
        booksButton = new Button("View books");
        booksButton.setMinSize(200, 100);
        booksButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        gridPane.add(booksButton, 1, 1);
    }

    public void addBooksButtonListener(EventHandler<ActionEvent> booksButtonListener) {
        booksButton.setOnAction(booksButtonListener);
    }

}
