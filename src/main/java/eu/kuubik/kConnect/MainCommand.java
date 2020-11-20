package eu.kuubik.kConnect;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.help.HelpTopic;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

public class MainCommand implements CommandExecutor{

    public Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("kConnect");
    public YamlConfiguration config;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CodeCommand cc = new CodeCommand();
        Database db = new Database();
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                db.dbDisconnect();
                db.dbConnect();
                sender.sendMessage("§9KC §8| §7Plugina taaslaadimine õnnestus!");
                return true;
            } else if (args.length > 1 && args[0].equalsIgnoreCase("genereeri") ||
                        args.length > 1 && args[0].equalsIgnoreCase("generate") ||
                        args.length > 1 && args[0].equalsIgnoreCase("gen")) {
                String playerCode = cc.generateCode(15);
                String player = args[1];
                if (!db.userHasCode(player)) {
                    sender.sendMessage("§9KC §8| §7Mängijale §c" + player + " §7on juba genereeritud kood!");
                } else {
                    sender.sendMessage("§9KC §8| §7Mängijale §9" + player + " §7edukalt genereeritud kood! (§f" + playerCode + "§7)");
                    db.insertUser(player, playerCode);
                }
                return true;
            } else if (args.length > 1 && args[0].equalsIgnoreCase("kustuta") ||
                        args.length > 1 && args[0].equalsIgnoreCase("delete") ||
                        args.length > 1 && args[0].equalsIgnoreCase("del")) {
                String playerName = args[1];
                if (db.userHasCode(playerName)) {
                    db.deleteUser(playerName);
                    sender.sendMessage("§9KC §8| §7Mängija §9" + playerName + " §7kood edukalt kustutatud");
                } else {
                    sender.sendMessage("§9KC §8| §7Mängijal §9" + playerName + " §7ei ole genereeritud koodi!");
                }
                return true;
            } else {
                sender.sendMessage("§9kConnect v1.0 §f@ §9kuubik.eu");
                return true;
            }
        } else {
            sender.sendMessage("§9kConnect v1.0 §f@ §9kuubik.eu");
            return true;
        }
    }
}
