package database;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.RIGHTS;
import static database.Constants.Roles.*;
import static database.Constants.Schemas.SCHEMAS;
import static database.Constants.getRolesRights;


// Bootstrap - acei pasi necesari pe care trb neaparat sa-i facem inainte de prima rulare a aplicatiei
// noua ne trebe o serie de tabele si de baze de date pe care sa le avem disponibile cand rulam aplicatia.
// clasa asta isi creeaza tabelele, si ne folosim de Repository

// Script - cod care automatizeaza mai multi pasi specifici, care trb executati repetitiv

public class Bootstrap {

    private static RightsRolesRepository rightsRolesRepository;

    public static void main(String[] args) throws SQLException {
        dropAll();

        bootstrapTables();

        bootstrapUserData();
    }

    private static void dropAll() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Dropping all tables in schema: " + schema);

            Connection connection = new JDBConnectionWrapper(schema).getConnection();
            Statement statement = connection.createStatement();

            String[] dropStatements = {
                    "TRUNCATE `role_right`;", // sterge tot continutu tabelei
                    "DROP TABLE `role_right`;", // sterge efectiv tabelu din baza de date
                    "TRUNCATE `right`;", // stergem basically in ordinea inversa creari, ca sa nu avem erori (Deci sterg tabelele de legatura prima data)
                    "DROP TABLE `right`;",
                    "TRUNCATE `user_role`;",
                    "DROP TABLE `user_role`;",
                    "TRUNCATE `role`;",
                    "DROP TABLE  `book`, `role`, `user`;"
            };

            Arrays.stream(dropStatements).forEach(dropStatement -> {
                try {
                    statement.execute(dropStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapTables() throws SQLException { // practic creeaza tabelele
        SQLTableCreationFactory sqlTableCreationFactory = new SQLTableCreationFactory();

        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping " + schema + " schema");


            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            Connection connection = connectionWrapper.getConnection();

            Statement statement = connection.createStatement();

            for (String table : Constants.Tables.ORDERED_TABLES_FOR_CREATION) {
                String createTableSQL = sqlTableCreationFactory.getCreateSQLForTable(table);
                statement.execute(createTableSQL);
            }
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapUserData() throws SQLException { // populeaza tabelele
        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping user data for " + schema);

            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());

            bootstrapRoles();
            bootstrapRights();
            bootstrapRoleRight();
            bootstrapUserRoles();
        }
    }

    // populeaza tabelele de legatura, in ordinea asta a rioritatilor
    private static void bootstrapRoles() throws SQLException {
        for (String role : ROLES) {
            rightsRolesRepository.addRole(role);
        }
    }

    private static void bootstrapRights() throws SQLException {
        for (String right : RIGHTS) {
            rightsRolesRepository.addRight(right);
        }
    }

    private static void bootstrapRoleRight() throws SQLException {
        Map<String, List<String>> rolesRights = getRolesRights();

        for (String role : rolesRights.keySet()) {
            Long roleId = rightsRolesRepository.findRoleByTitle(role).getId();

            for (String right : rolesRights.get(role)) {
                Long rightId = rightsRolesRepository.findRightByTitle(right).getId();

                rightsRolesRepository.addRoleRight(roleId, rightId);
            }
        }
    }

    // aici adaug admin sau alti useri default
    private static void bootstrapUserRoles() throws SQLException {
//        for (String schema : SCHEMAS) {
//            System.out.println("Creating admin user for " + schema);
//
//            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
//            rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
//
//            Role adminRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
//
//            User adminUser = new UserBuilder()
//                    .setUsername("admin")
//                    .setPassword("pass")
//                    .setRoles(Collections.singletonList(adminRole))
//                    .build();
//
//            rightsRolesRepository.addRolesToUser(adminUser, adminUser.getRoles());
//        }

    }
}