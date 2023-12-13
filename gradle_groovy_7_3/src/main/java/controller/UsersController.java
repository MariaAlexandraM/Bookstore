package controller;

import service.book.BookService;
import service.user.UserService;
import view.BooksView;
import view.UsersView;

public class UsersController {
    private final UsersView usersView;
    private final UserService userService;


    public UsersController(UsersView usersView, UserService userService) {
        this.usersView = usersView;
        this.userService = userService;
    }
}
