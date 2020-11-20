package eu.kuubik.kConnect;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            main.getLogger().info(main.getConfig().getString("Messages.SQL.Drivernotfound"));
            return;
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbName, username, password);
            if (connection.isValid(1)) {
                main.getLogger().info(main.getConfig().getString("Messages.SQL.Connected"));
            } else {
                main.getLogger().info(main.getConfig().getString("Messages.SQL.Failed"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dbDisconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                main.getLogger().info(main.getConfig().getString("Messages.SQL.Disconnected"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkIfTableExists() {
        try {
            if (connection.getMetaData().getTables(null, null, tableName, null) != null) {
                main.getLogger().info("§cSQL tabelit ei leitud, tabel luuakse..");
                createTables();
            } else {
                main.getLogger().info("§aSQL tabel on olemas! Ridade kontrollimine..");
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
            main.getLogger().info("§aTabel §f" + tableName + " §aedukalt loodud!");
        } catch (SQLException e) {
            e.printStackTrace();
            main.getLogger().info("§cTabeli loomisel tekkis ootamatu viga..");
        }
    }

    public void insertUser(String p, String code) {
        try {
            PreparedStatement insert = connection.prepareStatement("INSERT INTO " + tableName + " (player, code, activated) " +
                                                                    "VALUES ('"+ p + "', '"+ code +"','0');");
            insert.execute();
            main.getLogger().info("§aMängija §f" + p + " §a(§f" + code + "§a) kood on lisatud andmebaasi!");
        } catch (SQLException e) {
            e.printStackTrace();
            main.getLogger().info("§cMängija §f" + p + " §ckoodi sisestamisel andmebaasi tekkis ootamatu viga..");
        }
    }
}
