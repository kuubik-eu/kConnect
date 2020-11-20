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

            if (!db.userHasCode(player.toString())) {
                sender.sendMessage("§9KC §8| §7Sinu genereeritud kood on - " +"§9" + playerCode);
                db.insertUser(player.toString(), playerCode);
                return true;
            } else {
                sender.sendMessage("§9KC §8| §7Sinule on juba genereeritud kood. Vaata oma koodi käsklusega §9/kc");
                return true;
            }
        }
        sender.sendMessage("§KC §8| §7Konsoolis koodi genereerimiseks kasuta käsklust §9/kc gen [mängija]");
        return true;
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
