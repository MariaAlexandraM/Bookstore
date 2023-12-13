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
import service.book.BookService;
import service.user.UserService;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CRUDBooksView {
    private Stage stage;
    private TextField idTextField, authorTextField, titleTextField, stockTextField, priceTextField, dateTextField;
    private Button deleteBookButton, addBookButton;
    private Button updateBookButton, refresh;
    private Text actionTarget;
    private TableView<Book> tableView;

    public CRUDBooksView(BookService bookService) {
        this.stage = new Stage();
        stage.setTitle("Books");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        TableView<Book> tableView = createBooksTable(bookService.findAll());
        gridPane.add(tableView, 0, 0, 2, 1);

        initializeFields(gridPane);

        Scene scene = new Scene(gridPane, 1200, 600);
        stage.setScene(scene);

        stage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
    }

    private TableView<Book> createBooksTable(List<Book> books) {
        TableView<Book> tableView = new TableView<>();

        tableView.setMinWidth(1000);
        ObservableList<Book> data = FXCollections.observableArrayList(books);

        TableColumn<Book, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> publishedDateColumn = new TableColumn<>("Published Date");
        publishedDateColumn.setCellValueFactory(cellData -> {
            Book book = cellData.getValue();
            String formattedDate = book.getPublishedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return new SimpleStringProperty(formattedDate);
        });

        TableColumn<Book, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Book, Float> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getColumns().addAll(idColumn, authorColumn, titleColumn, publishedDateColumn, stockColumn, priceColumn);

        tableView.setItems(data);

        this.tableView = tableView;

        return tableView;
    }


    private void initializeFields(GridPane gridPane) {
        idTextField = new TextField();
        idTextField.setPromptText("ID");
        gridPane.add(idTextField, 0, 1);

        deleteBookButton = new Button("Delete book");
        gridPane.add(deleteBookButton, 1, 1);

        authorTextField = new TextField();
        authorTextField.setPromptText("Author");
        gridPane.add(authorTextField, 2, 1);

        titleTextField = new TextField();
        titleTextField.setPromptText("Title");
        gridPane.add(titleTextField, 3, 1);

        dateTextField = new TextField();
        dateTextField.setPromptText("YYYY-MM-DD");
        gridPane.add(dateTextField, 4, 1);

        stockTextField = new TextField();
        stockTextField.setPromptText("Stock");
        gridPane.add(stockTextField, 5, 1);

        priceTextField = new TextField();
        priceTextField.setPromptText("Price");
        gridPane.add(priceTextField, 6, 1);

        updateBookButton = new Button("Update book");
        gridPane.add(updateBookButton, 7, 1);

        addBookButton = new Button("Add book");
        gridPane.add(addBookButton, 7, 2);

        refresh = new Button("Refresh");
        gridPane.add(refresh, 0, 3);

        actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK);
        gridPane.add(actionTarget, 1, 6);
    }

    public String getId() {
        return idTextField.getText();
    }

    public String getAuthor() {
        return authorTextField.getText();
    }

    public String getTitle() {
        return titleTextField.getText();
    }

    public String getDate() {
        return dateTextField.getText();
    }

    public String getStock() {
        return stockTextField.getText();
    }

    public String getPrice() {
        return priceTextField.getText();
    }

    public void setActionTargetText(String text) {
        this.actionTarget.setText(text);
    }

    public void addAddButtonListener(EventHandler<ActionEvent> addButtonListener) {
        addBookButton.setOnAction(addButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteBookButton.setOnAction(deleteButtonListener);
    }

    public void addUpdateButtonListener(EventHandler<ActionEvent> updateButtonListener) {
        updateBookButton.setOnAction(updateButtonListener);
    }

    public void refreshButtonListener(EventHandler<ActionEvent> refreshButtonListener) {
        refresh.setOnAction(refreshButtonListener);
    }

    public void clearBooksTable() {
        tableView.getItems().clear();
    }

    public void populateBooksTable(List<Book> books) {
        createBooksTable(books);
    }
}
