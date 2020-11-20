package eu.kuubik.kConnect;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class MainCommand implements CommandExecutor{

    public Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("kConnect");
    public YamlConfiguration config;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(plugin.getConfig().getString("Messages.Commands.Reload.Success"));
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("info")) {
            sender.sendMessage("\n§9KCONNECT PLUGIN §8=======\n" +
                                  "§7kConnect plugin on tehtud eesmärgiga, et KUUBIKu minecrafti serveris olevad kasutajad ühendada\n" +
                                  "§7kodulehel olevate kasutajatega.\n" +
                                  "§7Plugin genereerib sulle võtme, mis tuleb kodulehel sisestada ja nii lihtne ongi.\n" +
                                  "§fPlugina autor: §9kqrmo §f@ kuubik.eu §c<3\n" +
                                  "§8=======================\n");
        }
        return false;
    }


}
