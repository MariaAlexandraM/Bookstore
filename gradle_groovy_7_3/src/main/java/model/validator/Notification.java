package model.validator;

import java.util.ArrayList;
import java.util.List;

// notification pattern inglobeaza rezultatul pe care noi il cautam
// ex: in findByUser -> user, returneaza useru, dar pot returna si o lista de erori sau potentiale warning-uri din aplicatie
public class Notification<T> { // functioneaza si cu useri, si cu carti, deci ii generic
    private T result; // ce returneaza functia, ce cautam de fapt
    private final List<String> errors; // toate potentialele erori din procesarea obiectului meu
    public Notification() {
        this.errors = new ArrayList<>();
    }

    public void addError(String error) {
        this.errors.add(error);
    }

    public boolean hasErrors() {
        return !(this.errors.isEmpty());
    }

    public void setResult(T result) {
        this.result = result;
    }

    public T getResult() {
        if (hasErrors()){
            throw new ResultFetchException(errors);
        }
        return result;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }

}
