package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.User;
import model.validator.Notification;
import service.user.AuthenticationService;
import view.AdminView;
import view.LoginView;


// controleaza abs toata aplicatia
// specific mvc, pt fiecare view sa am controller dedicat
public class LoginController {

    private final LoginView loginView;

    // mereu service uri, nu repo! its all abt layers bby. ca shrek.
    private final AuthenticationService authenticationService;


    public LoginController(LoginView loginView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    // TODO
    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()) {
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Login successful!");
                // TODO
                // acuma aici vreau sa deschid a new window cu functionalitati in functie de user role
                AdminView adminView = new AdminView(); //loginView.getStage()
                AdminController adminController = new AdminController(adminView, authenticationService);
                //adminView.show();
            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successful!\nYou need to log in to continue.");
            }
        }
    }
}

