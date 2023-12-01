package model;

import java.util.*;

// am fi putut face builder, da pt ca il folosim destul de restrictiv (tabelu de Roluri),
// ramane asa, fara builder. ramane builder doar la user
public class Role {
    private Long id;
    private String role;
    private List<Right> rights;

    public Role(Long id, String role, List<Right> rights) {
        this.id = id;
        this.role = role;
        this.rights = rights;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Right> getRights() {
        return rights;
    }

    public void setRights(List<Right> rights) {
        this.rights = rights;
    }
}
