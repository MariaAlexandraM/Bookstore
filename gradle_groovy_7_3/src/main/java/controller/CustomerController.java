package controller;

import service.user.AuthenticationService;
import view.CustomerView;

public class CustomerController {
    private final CustomerView customerView;
    private final AuthenticationService authenticationService;


    public CustomerController(CustomerView customerView, AuthenticationService authenticationService) {
        this.customerView = customerView;
        this.authenticationService = authenticationService;
    }
}
