package network.melonmc.serverselector.listener;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import network.melonmc.serverselector.ServerSelector;
import network.melonmc.serverselector.select.SelectorUI;

public class ServersCommandListener extends Command {

    public static ServersCommandListener factory(ServerSelector plugin) {
        String command = plugin.getConfig().getString("command");
        String perm = plugin.getConfig().getString("permissionPrefix")+"."+plugin.getConfig().getString("permission");
        String[] aliases = plugin.getConfig().getStringList("aliases").toArray(new String[0]);

        return new ServersCommandListener(plugin, command, perm, aliases);
    }

    public ServersCommandListener(ServerSelector plugin, String command, String perm, String[] aliases) {
        super(command, perm, aliases);
        ProxyServer.getInstance().getPluginManager().registerCommand(plugin, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            new SelectorUI((ProxiedPlayer) sender).show();
        }
    }

}
