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

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "select * from book where id = ?";


        return Optional.empty();
    }

    @Override
    public boolean save(Book book) {
        String sql = "insert into book values (null, ?, ?, ?);"; // null pt ca baza de date imi completeaza, si ? pt ca o sa completam noi dupa

        // de ce despart asa si fac preparedStatement si nu cu Statement direct si scriu gen "insert..." + "values(" + book.getAuthor etc
        // SQL injection!
        //ptrpSt se asigura ca seteaza tipu corect gen int, exemplu ala cu titlu lao carte sa fie "); drop table; --"

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getAuthor()); // imi pune la primu arametru autoru
            preparedStatement.setString(2, book.getTitle());
            //preparedStatement.setDate(3, String.valueOf(Date.valueOf(book.getPublishedDate())));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void removeAll() {

    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setAuthor(resultSet.getString("author"))
                .setTitle(resultSet.getString("title"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .build();
    }
}
