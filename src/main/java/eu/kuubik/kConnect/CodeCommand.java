package eu.kuubik.kConnect;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

            if (db.userHasCode(player.toString())) {
                TextComponent string1 = new TextComponent("§9KC §8| §7Sinu genereeritud kood on - ");
                TextComponent string2 = new TextComponent("§9" + playerCode);
                string2.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aVajuta, et kopeerida").create()));
                string2.setClickEvent( new ClickEvent( ClickEvent.Action.COPY_TO_CLIPBOARD, playerCode));
                string1.addExtra(string2);

                player.spigot().sendMessage(string1);
                db.insertUser(player.getName(), playerCode);
                return true;
            } else {
                sender.sendMessage("§9KC §8| §7Sinule on juba genereeritud kood..");
                return true;
            }
        } else {
            sender.sendMessage("§KC §8| §7Konsoolis koodi genereerimiseks kasuta käsklust §9/kc gen [mängija]");
            return true;
        }
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
