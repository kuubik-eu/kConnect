package eu.kuubik.kConnect;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;

public class kConnect extends JavaPlugin {

    FileConfiguration config = getConfig();
    public PluginDescriptionFile pd = this.getDescription();
    public File configFile = new File(this.getDataFolder(), "config.yml");
    private File file;
    final String host = getConfig().getString("Database.Host");
    final String username = getConfig().getString("Database.Username");
    final String password = getConfig().getString("Database.Password");
    final String dbName = getConfig().getString("Database.Databasename");
    final String port = getConfig().getString("Database.Port");
    final String url = "jdbc:mysql://"+host+":3306/" + dbName + "";
    static Connection connection;

    @Override
    public void onEnable() {
        updateConfig();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("§cJDBC driverit ei leitud!");
            return;
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbName, this.username, this.password);
            if (connection.isValid(1)) {
                getLogger().info("§aSQL Connected!");
            } else {
                getLogger().info("§cCheck your SQL connection data in config!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getCommand("kc").setExecutor(new MainCommand());
        getCommand("genereeri").setExecutor((new CodeCommand()));
        getLogger().info("§akConnect §fv"+ getDescription().getVersion() +" §akäivitatud!");
    }

    @Override
    public void onDisable() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                getLogger().info("§cSQL Disconnected!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        getLogger().info("§ckConnect §fv" + getDescription().getVersion() + " §cplugin peatatud!");
    }

    private void updateConfig() {
        saveDefaultConfig();
        Set<String> options = getConfig().getDefaults().getKeys(false);
        Set<String> current = getConfig().getKeys(false);
        boolean changed = false;

        for (String s : options) {
            if (!current.contains((s))) {
                getConfig().set(s, getConfig().getDefaults().get((s)));
                changed = true;
            }
        }

        for (String s : current) {
            if (!options.contains(s)) {
                getConfig().set(s, null);
                changed = true;
            }
        }

        getConfig().options().copyHeader(true);
        if (changed) {
            saveConfig();
        }
    }
}
