package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import model.Role;
import model.User;
import model.builder.BookBuilder;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import service.book.BookService;
import service.user.UserService;
import view.BooksView;
import view.CRUDBooksView;
import view.CRUDUsersView;
import view.UsersView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CRUDBooksController {
    private final CRUDBooksView CRUDbooksView;
    private final BookService bookService;

    public CRUDBooksController(CRUDBooksView CRUDbooksView, BookService bookService) {
        this.CRUDbooksView = CRUDbooksView;
        this.bookService = bookService;

        this.CRUDbooksView.addDeleteButtonListener(new DeleteButtonListener());
        this.CRUDbooksView.addUpdateButtonListener(new UpdateButtonListener());
        this.CRUDbooksView.addAddButtonListener(new AddButtonListener());
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                Long id = Long.valueOf(CRUDbooksView.getId());

                bookService.findById(id);
                try {
                    bookService.deleteById(id);
                    CRUDbooksView.setActionTargetText("Book with id = " + id + " deleted");
                } catch(Exception e) {
                    CRUDbooksView.setActionTargetText("Book with id = " + id + " not found");
                }

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
            }
        }
    }

    private class UpdateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                Long id = Long.valueOf(CRUDbooksView.getId());

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
            }
        }
    }

    private class AddButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                String author = CRUDbooksView.getAuthor();
                String title = CRUDbooksView.getTitle();
                String date = CRUDbooksView.getDate();
                int stock = Integer.parseInt(CRUDbooksView.getStock());
                float price = Float.parseFloat(CRUDbooksView.getPrice());


                Book book = new BookBuilder().setAuthor(author).setTitle(title).setPublishedDate(LocalDate.parse(date)).setStock(stock).setPrice(price).build();

                if(bookService.save(book)) {
                    CRUDbooksView.setActionTargetText("Book added successfully!");
                } else {
                    CRUDbooksView.setActionTargetText("Error adding " + title + " by " + author);
                }

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
            }
        }
    }

}
