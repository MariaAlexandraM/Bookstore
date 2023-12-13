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
import model.Book;
import service.book.BookService;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BooksView {
    private Stage stage;

    public BooksView(BookService bookService) {
        this.stage = new Stage();

        stage.setTitle("Books");

        VBox vBox = new VBox();
        initializeVBox(vBox);

        TableView<Book> tableView = createBooksTable(bookService.findAll());
        vBox.getChildren().add(tableView);

        Scene scene = new Scene(vBox, 800, 600);
        stage.setScene(scene);

        stage.show();
    }

    private void initializeVBox(VBox vBox) {
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
    }

    private TableView<Book> createBooksTable(List<Book> books) {
        TableView<Book> tableView = new TableView<>();
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

        return tableView;
    }
}
