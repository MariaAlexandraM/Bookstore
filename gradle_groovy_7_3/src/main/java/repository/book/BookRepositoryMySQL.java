package repository.book;

import model.Book;
import model.builder.BookBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMySQL implements BookRepository{

    private final Connection connection;
    public BookRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Book> findAll() {
        String sql = "select * from book";
        List<Book> books = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // TODO
    public Optional<Book> findById(Long id) {
        String sql = "select * from book where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public boolean save(Book book) {
        String sql = "insert into book values (null, ?, ?, ?, ?, ?);"; // null pt ca baza de date imi completeaza, si ? pt ca o sa completam noi dupa

        // de ce despart asa si fac preparedStatement si nu cu Statement direct si scriu gen "insert..." + "values(" + book.getAuthor etc
        // SQL injection!
        //ptrpSt se asigura ca seteaza tipu corect gen int, exemplu ala cu titlu lao carte sa fie "); drop table; --"

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getAuthor()); // imi pune la primu arametru autoru
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            preparedStatement.setInt(4, book.getStock());
            preparedStatement.setFloat(5, book.getPrice());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    // TODO Done?
    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "delete from book where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateBook(Book book) {
        String sql = "update book set author = ?, title = ?, publishedDate = ?, stock = ?, price = ? where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            preparedStatement.setInt(4, book.getStock());
            preparedStatement.setFloat(5, book.getPrice());
            preparedStatement.setLong(6, book.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String decreaseQty(Book book, int quantity) {
        if (book.getStock() >= quantity) {
            String sql = "update book set stock = stock - ? where id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, quantity);
                preparedStatement.setLong(2, book.getId());
                preparedStatement.executeUpdate();

                return "Book(s) bought successfully!";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return ("Insufficient stock. Available stock: " + book.getStock()).toString();
        }
        return "";
    }

    @Override
    public void deleteById(Long id) {
        try {
            String deleteSql = "delete from book where id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setAuthor(resultSet.getString("author"))
                .setTitle(resultSet.getString("title"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setStock(resultSet.getInt("stock"))
                .setPrice(resultSet.getFloat("price"))
                .build();
    }
}
