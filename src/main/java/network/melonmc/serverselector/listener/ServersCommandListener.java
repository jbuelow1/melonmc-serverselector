package net.melonmc.serverselector.listener;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.melonmc.serverselector.ServerConnector;
import net.melonmc.serverselector.ServerSelector;
import net.melonmc.serverselector.select.SelectorUI;

public class ServersCommandListener extends Command {

    private final Configuration config;

    public static ServersCommandListener factory(ServerSelector plugin) {
        String command = plugin.getConfig().getString("command");
        String perm = plugin.getConfig().getString("permissionPrefix")+"."+plugin.getConfig().getString("permission");
        String[] aliases = plugin.getConfig().getStringList("aliases").toArray(new String[0]);

        return new ServersCommandListener(plugin, command, perm, aliases);
    }

    public ServersCommandListener(ServerSelector plugin, String command, String perm, String[] aliases) {
        super(command, perm, aliases);
        config = plugin.getConfig();
        ProxyServer.getInstance().getPluginManager().registerCommand(plugin, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) { return; }

        if (args.length > 0) {
            if (config.getBoolean("allowSubcommands")) {
                for (String id : config.getSection("servers").getKeys()) {
                    if (id.equalsIgnoreCase(args[0]) ||
                            args[0].equalsIgnoreCase(config.getString("servers."+id+".alias"))) {
                        ServerConnector.connectPlayerTo((ProxiedPlayer) sender, id);
                        return;
                    }
                }
            }
        }

        new SelectorUI((ProxiedPlayer) sender).show();
    }

}
