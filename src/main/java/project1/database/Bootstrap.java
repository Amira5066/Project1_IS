package project1.database;

import project1.repository.security.RightsRolesRepository;
import project1.repository.security.RightsRolesRepositoryMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static project1.database.Constants.Rights.RIGHTS;
import static project1.database.Constants.Roles.ROLES;
import static project1.database.Constants.Schemas.SCHEMAS;
import static project1.database.Constants.getRolesRights;

// Script - code that automates some steps or processes

public class Bootstrap {

    private static RightsRolesRepository rightsRolesRepository;

    public static void main(String[] args) throws SQLException {
        dropAll();

        bootstrapTables();

        bootstrapUserData();

        bootstrapBookData();
    }

    private static void dropAll() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Dropping all tables in schema: " + schema);

            Connection connection = new JDBConnectionWrapper(schema).getConnection();
            Statement statement = connection.createStatement();

            String[] dropStatements = {
                    "TRUNCATE `physical_book`",
                    "DROP TABLE `physical_book`",
                    "TRUNCATE `audio_book`",
                    "DROP TABLE `audio_book`",
                    "TRUNCATE `ebook`",
                    "DROP TABLE `ebook`",
                    "TRUNCATE `role_right`;",
                    "DROP TABLE `role_right`;",
                    "TRUNCATE `right`;",
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

    private static void bootstrapTables() throws SQLException {
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

    private static void bootstrapUserData() throws SQLException {
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

    private static void bootstrapUserRoles() throws SQLException {

    }

    private static void bootstrapBookData() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Inserting books in schema: " + schema);

            Connection connection = new JDBConnectionWrapper(schema).getConnection();
            Statement statement = connection.createStatement();

            String[] dataStatements = {
                    "INSERT INTO `book`(`author`, `title`, `publishedDate`, `price`) VALUES ('J.K. Rowling','Harry Potter','1997-06-26', 32);",
                    "INSERT INTO `book`(`author`, `title`, `publishedDate`, `price`) VALUES ('Jojo Moyes','Me Before You','2012-01-05', 39);",
                    "INSERT INTO `book`(`author`, `title`, `publishedDate`, `price`) VALUES ('James Dashner','The Maze Runner','2009-10-06', 41);",
                    "INSERT INTO `book`(`author`, `title`, `publishedDate`, `price`) VALUES ('Rick Yancey','The 5th wave','2013-05-07', 48);",

                    "INSERT INTO `physical_book`(`id`, `cover`, `stock`) VALUES (1, 'hardcover', 30);",
                    "INSERT INTO `physical_book`(`id`, `cover`, `stock`) VALUES (1, 'paperback', 20);",
                    "INSERT INTO `physical_book`(`id`, `cover`, `stock`) VALUES (2, 'paperback', 40);",
                    "INSERT INTO `physical_book`(`id`, `cover`, `stock`) VALUES (3, 'hardcover', 52);",
                    "INSERT INTO `physical_book`(`id`, `cover`, `stock`) VALUES (4, 'paperback', 67);",

                    "INSERT INTO `audio_book`(`id`, `runTime`) VALUES (1, 200);",
                    "INSERT INTO `audio_book`(`id`, `runTime`) VALUES (2, 300);",

                    "INSERT INTO `ebook`(`id`, `format`) VALUES (3, 'pdf');",
                    "INSERT INTO `ebook`(`id`, `format`) VALUES (4, 'pdf');",
            };
            Arrays.stream(dataStatements).forEach(dropStatement -> {
                try {
                    statement.execute(dropStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}