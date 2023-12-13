package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import model.builder.BookBuilder;
import service.book.BookService;
import view.CRUDBooksView;

import java.time.LocalDate;
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
        this.CRUDbooksView.refreshButtonListener(new RefreshButtonListener());
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
                String newAuthor = CRUDbooksView.getAuthor();
                String newTitle = CRUDbooksView.getTitle();
                String newDate = CRUDbooksView.getDate();
                int newStock = Integer.parseInt(CRUDbooksView.getStock());
                float newPrice = Float.parseFloat(CRUDbooksView.getPrice());

                Book oldBook = bookService.findById(id);
                String author = (newAuthor.isEmpty()) ? oldBook.getAuthor() : newAuthor;
                String title = (newTitle.isEmpty()) ? oldBook.getTitle() : newTitle;
                LocalDate date = (newDate.isEmpty()) ? oldBook.getPublishedDate() : LocalDate.parse(newDate);
                int stock = (CRUDbooksView.getStock().isEmpty()) ? oldBook.getStock() : newStock;
                float price = (CRUDbooksView.getPrice().isEmpty()) ? oldBook.getPrice() : newPrice;

                Book updatedBook = new BookBuilder()
                        .setId(id)
                        .setAuthor(author)
                        .setTitle(title)
                        .setPublishedDate(date)
                        .setStock(stock)
                        .setPrice(price)
                        .build();

                if (bookService.updateBook(updatedBook)) {
                    CRUDbooksView.setActionTargetText("Book with id = " + id + " updated");
                } else {
                    CRUDbooksView.setActionTargetText("Error updating book with id = " + id);
                }

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

    private class RefreshButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            List<Book> updatedBooks = bookService.findAll();
            CRUDbooksView.clearBooksTable();
            CRUDbooksView.populateBooksTable(updatedBooks);
        }
    }


}
