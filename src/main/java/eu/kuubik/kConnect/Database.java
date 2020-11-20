package eu.kuubik.kConnect;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Database implements Listener {

    private kConnect main = kConnect.getPlugin(kConnect.class);
    final String host = main.getConfig().getString("Database.Host");
    final String username = main.getConfig().getString("Database.Username");
    final String password = main.getConfig().getString("Database.Password");
    final String dbName = main.getConfig().getString("Database.Databasename");
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
}
