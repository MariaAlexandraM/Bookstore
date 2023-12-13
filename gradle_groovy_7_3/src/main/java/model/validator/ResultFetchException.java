package model.validator;

import java.util.*;
import java.util.stream.Collectors;

// exceptie personalizata de noi, facuta pt Notification in getResult
// daca atunci cand vr sa dau return la un rezultat, prefer sa arunc si o exceptie,
// chiar daca stiu ca rez nu ii valid (?)
public class ResultFetchException extends RuntimeException {

    private final List<String> errors;

    public ResultFetchException(List<String> errors) {
        super("Failed to fetch the result as the operation encountered errors!");
        this.errors = errors;
    }

    @Override
    public String toString() {
        return errors.stream().map(Object::toString)
                .collect(Collectors.joining(", ")) + super.toString();
        // iterez toate mesajele de eroare - stirng- si pt fiecare, fac un map: fiecare obiect il mape cu toString pt formatare, le colectez c Collect si fac join cu virgula, si mai apoi concatenez cu stringu ala cu Failed to fetch... cred
    }
}

