package eu.kuubik.kConnect;

import org.bukkit.event.Listener;

import java.sql.*;

public class Database implements Listener {

    private kConnect main = kConnect.getPlugin(kConnect.class);
    final String host = main.getConfig().getString("Database.Host");
    final String username = main.getConfig().getString("Database.Username");
    final String password = main.getConfig().getString("Database.Password");
    final String dbName = main.getConfig().getString("Database.Databasename");
    final String tableName = main.getConfig().getString("Database.Tablename");
    final String port = main.getConfig().getString("Database.Port");
    static Connection connection;

    public void dbConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            main.getLogger().info("§9KC §8| §cJDBC Driverit ei leitud..");
            return;
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbName, username, password);
            if (connection.isValid(1)) {
                main.getLogger().info("§9KC §8| §aSQL ühendus edukalt loodud!");
            } else {
                main.getLogger().info("§9KC §8| §cSQL ühenduse loomine ebaõnnestus!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dbDisconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                main.getLogger().info("§9KC §8| §cSQL ühendus katkestatud!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkIfTableExists() {
        try {
            if (connection.getMetaData().getTables(null, null, tableName, null) != null) {
                main.getLogger().info("§9KC §8| §cSQL tabelit ei leitud! &aTabel luuakse..");
                createTables();
            } else {
                main.getLogger().info("§9KC §8| §aSQL tabel on olemas!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        try {
            PreparedStatement create = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                                                                    "player VARCHAR(40)," +
                                                                    "code VARCHAR(40)," +
                                                                    "activated BOOLEAN default FALSE" +
                                                                    ");");
            create.execute();
            main.getLogger().info("§9KC §8| §aTabel §f" + tableName + " §aedukalt loodud!");
        } catch (SQLException e) {
            e.printStackTrace();
            main.getLogger().info("§9KC §8| §cTabeli loomisel tekkis ootamatu viga..");
        }
    }

    public boolean userHasCode(String p) {
        try {
            PreparedStatement select = connection.prepareStatement("SELECT 'player' FROM " + tableName + " WHERE player = '" + p + "';");
            boolean res = select.execute();
            if (res) {
                return true;
            } else {
                return false;
            }

            /*
            if (res != null) {
                return true;
            } else {
                return false;
            }
            */
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertUser(String p, String code) {
        try {
            PreparedStatement insert = connection.prepareStatement("INSERT INTO " + tableName + " (player, code, activated) " +
                        "VALUES ('" + p + "', '" + code + "','0');");
            insert.execute();
            main.getLogger().info("§9KC §8| §aMängija §f" + p + " §a(§f" + code + "§a) kood on lisatud andmebaasi!");
        } catch (SQLException e) {
            e.printStackTrace();
            main.getLogger().info("§9KC §8| §cMängija §f" + p + " §ckoodi sisestamisel andmebaasi tekkis ootamatu viga..");
        }
    }

    public void deleteUser(String p) {
        try {
            PreparedStatement delete = connection.prepareStatement("DELETE FROM " + tableName + " WHERE player = '" + p + "';");
            delete.execute();
            main.getLogger().info("§9KC §8| §aMängija §f" + p + " §akood on kustutatud andmebaasist!");
        } catch (SQLException e) {
            e.printStackTrace();
            main.getLogger().info("§9KC §8| §cMängija §f" + p + " §ckoodi kustutamisel andmebaasist tekkis ootamatu viga..");
        }
    }
}

