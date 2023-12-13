package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CustomerView {
    private Stage stage;
    private Button booksButton, buyButton;
    private TextField idTextField, quantityTextField;
    private String username;
    private Text actiontarget;

    public CustomerView(String user_name) {
        this.stage = new Stage();
        username = user_name;

        stage.setTitle("Customer");
        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        this.stage.setScene(scene);
        initializeSceneTitle(gridPane);

        addButton(gridPane);
        initializeFields(gridPane);

        stage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initializeSceneTitle(GridPane gridPane) {
        Text sceneTitle = new Text("Welcome, " + username + "!");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);
        GridPane.setHalignment(sceneTitle, javafx.geometry.HPos.CENTER);
    }

    private void addButton(GridPane gridPane) {
        booksButton = new Button("View books");
        booksButton.setMinSize(150, 90);
        booksButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(booksButton, javafx.geometry.HPos.CENTER);

        gridPane.add(booksButton, 0, 2);
    }

    private void initializeFields(GridPane gridPane) {
        idTextField = new TextField();
        idTextField.setPromptText("ID");
        gridPane.add(idTextField, 0, 3);

        quantityTextField = new TextField();
        quantityTextField.setPromptText("Quantity");
        gridPane.add(quantityTextField, 1, 3);

        buyButton = new Button("Buy");
        buyButton.setMinSize(80, 40);
        buyButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        gridPane.add(buyButton, 2, 3);

        actiontarget = new Text();
        actiontarget.setFill(Color.FIREBRICK);
        gridPane.add(actiontarget, 1, 6);
    }

    public void setActionTargetText(String text) {
        this.actiontarget.setText(text);
    }


    public void addBooksButtonListener(EventHandler<ActionEvent> booksButtonListener) {
        booksButton.setOnAction(booksButtonListener);
    }

    public void addBuyButtonListener(EventHandler<ActionEvent> buyButtonListener) {
        buyButton.setOnAction(buyButtonListener);
    }

    public String getId() {
        return idTextField.getText();
    }

    public String getQuantity() {
        return quantityTextField.getText();
    }
}
