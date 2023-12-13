package controller;

import service.user.AuthenticationService;
import view.EmployeeView;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final AuthenticationService authenticationService;


    public EmployeeController(EmployeeView employeeView, AuthenticationService authenticationService) {
        this.employeeView = employeeView;
        this.authenticationService = authenticationService;
    }
}
