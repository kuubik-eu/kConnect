package eu.kuubik.kConnect;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CodeCommand implements CommandExecutor {
    public Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("kConnect");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Database db = new Database();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String playerCode = generateCode(15);
            sender.sendMessage(plugin.getConfig().getString("Messages.Generate.Yourgeneratedcode") +
                                  "§a" + playerCode);
            db.insertUser(player.toString(), playerCode);
            return true;
        } else if (sender instanceof ConsoleCommandSender){
            if (args.length == 1) {
                String playerCode = generateCode(15);
                String player = args[0];
                sender.sendMessage("§7Mängijale §a" + player + " §7on genereeritud kood §a" + playerCode);
                db.insertUser(player, playerCode);
                return true;
            } else {
                sender.sendMessage(plugin.getConfig().getString("Message.Generate.Pleasementionplayer"));
            }
        }
        return false;
    }

    static String generateCode(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }
}
