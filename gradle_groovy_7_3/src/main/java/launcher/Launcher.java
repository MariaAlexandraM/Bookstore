package launcher;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args); // astao sa apeleze ulterior Start
    }

    //
    @Override
    public void start(Stage primaryStage) throws Exception {
        ComponentFactory componentFactory = ComponentFactory.getInstance(false, primaryStage); // false = nu lucrez cu baza de test
    }
}
