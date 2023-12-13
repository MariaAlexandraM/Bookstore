package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import model.Role;
import model.User;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import service.book.BookService;
import service.user.UserService;
import view.BooksView;
import view.CRUDUsersView;
import view.UsersView;

import java.util.ArrayList;
import java.util.List;

public class CRUDUsersController {
    private final CRUDUsersView CRUDusersView;
    private final UserService userService;

    public CRUDUsersController(CRUDUsersView CRUDusersView, UserService userService) {
        this.CRUDusersView = CRUDusersView;
        this.userService = userService;

        this.CRUDusersView.addDeleteButtonListener(new DeleteButtonListener());
        this.CRUDusersView.addUpdateButtonListener(new UpdateButtonListener());
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                Long id = Long.valueOf(CRUDusersView.getId());
                //String role = CRUDusersView.getSelectedRole();

                userService.findById(id);
                try {
                    userService.deleteById(id);
                    CRUDusersView.setActionTargetText("User with id = " + id + " deleted");
                } catch(Exception e) {
                    CRUDusersView.setActionTargetText("User with id = " + id + " not found");
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
                Long id = Long.valueOf(CRUDusersView.getId());
                String role = CRUDusersView.getSelectedRole();

                User user = userService.findById(id);

                if (user != null) {
                    List<Role> existingRoles = user.getRoles();
                    List<Role> newRoles = new ArrayList<>();
                    newRoles = existingRoles;
                    //Role findRole =
                    // ramane sa convertesc stringu in Role si sa folosesc findRoleByTitle sau cv

                    try {
                        if (userService.updateUser(user)) {
                            CRUDusersView.setActionTargetText("User with id = " + id + " updated");
                        } else {
                            CRUDusersView.setActionTargetText("Error updating user with id = " + id);
                        }
                    } catch (Exception e) {
                        CRUDusersView.setActionTargetText("Error updating user with id = " + id);
                    }
                } else {
                    CRUDusersView.setActionTargetText("User with id = " + id + " not found");
                }

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
            }
        }
    }

}
