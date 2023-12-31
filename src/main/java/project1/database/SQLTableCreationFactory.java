package project1.database;

import static project1.database.Constants.Tables.*;

public class SQLTableCreationFactory {

    public String getCreateSQLForTable(String table) {
        return switch (table) {
            case BOOK -> "CREATE TABLE IF NOT EXISTS book (" +
                    "  id bigint NOT NULL AUTO_INCREMENT," +
                    "  author varchar(500) NOT NULL," +
                    "  title varchar(500) NOT NULL," +
                    "  publishedDate datetime DEFAULT NULL," +
                    "  price INT DEFAULT 0," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
            case PHYSICAL_BOOK -> "CREATE TABLE IF NOT EXISTS physical_book(" +
                    " id bigint NOT NULL," +
                    " cover varchar(20) NOT NULL," +
                    " stock INT NOT NULL," +
                    " PRIMARY KEY(id, cover)," +
                    " FOREIGN KEY (id) REFERENCES book(id) ON DELETE CASCADE"+
                    ") DEFAULT CHARSET=utf8;";
            case AUDIO_BOOK -> "CREATE TABLE IF NOT EXISTS audio_book(" +
                    " id bigint NOT NULL AUTO_INCREMENT," +
                    " runTime bigint NOT NULL," +
                    " PRIMARY KEY(id, runTime)," +
                    " FOREIGN KEY (id) REFERENCES book(id) ON DELETE CASCADE, "+
                    " UNIQUE KEY id_UNIQUE(id)" +
                    ") DEFAULT CHARSET=utf8;";
            case EBOOK -> "CREATE TABLE IF NOT EXISTS ebook(" +
                    " id bigint NOT NULL AUTO_INCREMENT," +
                    " format varchar(10) NOT NULL," +
                    " PRIMARY KEY(id, format)," +
                    " FOREIGN KEY (id) REFERENCES book(id) ON DELETE CASCADE, "+
                    " UNIQUE KEY id_UNIQUE(id)" +
                    ") DEFAULT CHARSET=utf8;";
            case USER -> "CREATE TABLE IF NOT EXISTS user (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  username VARCHAR(200) NOT NULL," +
                    "  password VARCHAR(64) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  UNIQUE INDEX username_UNIQUE (username ASC));";
            case ROLE -> "  CREATE TABLE IF NOT EXISTS role (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  role VARCHAR(100) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  UNIQUE INDEX role_UNIQUE (role ASC));";
            case RIGHT -> "  CREATE TABLE IF NOT EXISTS `right` (" +
                    "  `id` INT NOT NULL AUTO_INCREMENT," +
                    "  `right` VARCHAR(100) NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC)," +
                    "  UNIQUE INDEX `right_UNIQUE` (`right` ASC));";
            case ROLE_RIGHT -> "  CREATE TABLE IF NOT EXISTS role_right (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  role_id INT NOT NULL," +
                    "  right_id INT NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  INDEX role_id_idx (role_id ASC)," +
                    "  INDEX right_id_idx (right_id ASC)," +
                    "  CONSTRAINT role_id" +
                    "    FOREIGN KEY (role_id)" +
                    "    REFERENCES role (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE," +
                    "  CONSTRAINT right_id" +
                    "    FOREIGN KEY (right_id)" +
                    "    REFERENCES `right` (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE);";
            case USER_ROLE -> "CREATE TABLE IF NOT EXISTS user_role (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  user_id INT NOT NULL," +
                    "  role_id INT NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  INDEX user_id_idx (user_id ASC)," +
                    "  INDEX role_id_idx (role_id ASC)," +
                    "  CONSTRAINT user_fkid" +
                    "    FOREIGN KEY (user_id)" +
                    "    REFERENCES user (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE," +
                    "  CONSTRAINT role_fkid" +
                    "    FOREIGN KEY (role_id)" +
                    "    REFERENCES role (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE);";
            case EMPLOYEE_REPORT -> "CREATE TABLE IF NOT EXISTS employee_report (" +
                    "  user_id INT NOT NULL," +
                    "  books_sold INT NOT NULL DEFAULT 0," +
                    "  income INT NOT NULL DEFAULT 0," +
                    "  PRIMARY KEY (user_id, books_sold)," +
                    "  CONSTRAINT user_report_fkid" +
                    "    FOREIGN KEY (user_id)" +
                    "    REFERENCES user (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE);";
            default -> "";
        };
    }

}