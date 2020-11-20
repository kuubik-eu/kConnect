package eu.kuubik.kConnect;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
public class kConnect extends JavaPlugin {


    @Override
    public void onEnable() {
        Database db = new Database();
        db.dbConnect();
        db.checkIfTableExists();
        updateConfig();
        getCommand("kc").setExecutor(new MainCommand());
        getCommand("genereeri").setExecutor((new CodeCommand()));
        getLogger().info("\n\n§9 /$$   /$$  /$$$$$$ \n" +
                "§9| $$  /$$/ /$$__  $$ \n" +
                "§9| $$ /$$/ | $$  \\__/\n" +
                "§9| $$$$$/  | $$             §fKCONNECT §7@ §fkuubik.eu\n" +
                "§9| $$  $$  | $$             §aENABLED\n" +
                "§9| $$\\  $$ | $$    $$\n" +
                "§9| $$ \\  $$|  $$$$$$/\n" +
                "§9|__/  \\__/ \\______/ \n");
    }

    @Override
    public void onDisable() {
        Database db = new Database();
        db.dbDisconnect();
        getLogger().info("\n\n§9 /$$   /$$  /$$$$$$ \n" +
                "§9| $$  /$$/ /$$__  $$ \n" +
                "§9| $$ /$$/ | $$  \\__/\n" +
                "§9| $$$$$/  | $$             §fKCONNECT §7@ §fkuubik.eu\n" +
                "§9| $$  $$  | $$             §cDISABLED\n" +
                "§9| $$\\  $$ | $$    $$\n" +
                "§9| $$ \\  $$|  $$$$$$/\n" +
                "§9|__/  \\__/ \\______/ \n");
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
