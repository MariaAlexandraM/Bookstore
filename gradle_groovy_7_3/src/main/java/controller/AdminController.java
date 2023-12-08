package controller;

import service.user.AuthenticationService;
import view.AdminView;
import view.LoginView;

public class AdminController {

    private final AdminView adminView;

    // mereu service uri, nu repo! its all abt layers bby. ca shrek.
    private final AuthenticationService authenticationService;


    public AdminController(AdminView adminView, AuthenticationService authenticationService) {
        this.adminView = adminView;
        this.authenticationService = authenticationService;

    }
}
