package view;

import controller.BookController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;
import service.book.BookServiceImplementation;

import java.time.LocalDate;

public class BookView extends Application {
    private Stage window;
    private TableView<Book> tableView;
    private BookController bookController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Books");

      //  BookServiceImplementation bookService = new BookServiceImplementation();
       // BookController bookController = new BookController(bookService);

//
//        TableColumn<Book, Long> idColumn = new TableColumn<>("ID");
//        idColumn.setMinWidth(200);
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setMinWidth(200);
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));


        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(200);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));


        TableColumn<Book, LocalDate> publishedDateColumn = new TableColumn<>("Published Date");
        publishedDateColumn.setMinWidth(200);
        publishedDateColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));

        tableView = new TableView<>();
        tableView.setItems(bookController.getAllBooks());
        //tableView.getColumns().addAll(idColumn);
        tableView.getColumns().addAll(authorColumn, titleColumn, publishedDateColumn);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(tableView);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
    }

    public ObservableList<Book> getAllBooks() {
        ObservableList<Book> books = FXCollections.observableArrayList();

        return books;
    }
}
