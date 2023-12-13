package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import service.book.BookService;
import service.user.AuthenticationService;
import view.BooksView;
import view.CustomerView;

public class CustomerController {
    private final CustomerView customerView;
    private final BookService bookService;


    public CustomerController(CustomerView customerView, BookService bookService) {
        this.customerView = customerView;
        this.bookService = bookService;

        this.customerView.addBooksButtonListener(new BooksButtonListener());
        this.customerView.addBuyButtonListener(new BuyBookButtonListener());
    }

    private class BooksButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            BooksView booksView = new BooksView(bookService);
            BooksController booksController = new BooksController(booksView, bookService);
        }
    }

    private class BuyBookButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            try {
                Long id = Long.valueOf(customerView.getId());
                int quantity = Integer.parseInt(customerView.getQuantity());

                try {
                    Book book = bookService.findById(id);
                    String text = bookService.decreaseQty(book, quantity);
                    customerView.setActionTargetText(text);
                } catch(Exception e) {
                    customerView.setActionTargetText("Book with id = " + id + " not found");
                }

            } catch (NumberFormatException ex) {
                customerView.setActionTargetText("Invalid inputs");
            }


        }
    }
}
