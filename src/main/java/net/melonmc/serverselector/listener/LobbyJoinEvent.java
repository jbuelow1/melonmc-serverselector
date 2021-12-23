package net.melonmc.serverselector.listener;

import dev.simplix.protocolize.api.Direction;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.listener.AbstractPacketListener;
import dev.simplix.protocolize.api.listener.PacketReceiveEvent;
import dev.simplix.protocolize.api.listener.PacketSendEvent;
import net.md_5.bungee.protocol.packet.LoginSuccess;
import net.melonmc.serverselector.ServerSelector;

public class LobbyJoinEvent extends AbstractPacketListener<LoginSuccess> {

    public LobbyJoinEvent(ServerSelector plugin) {
        super(LoginSuccess.class, Direction.DOWNSTREAM, 0);
        Protocolize.listenerProvider().registerListener(this);
    }

    @Override
    public void packetReceive(PacketReceiveEvent<LoginSuccess> packetReceiveEvent) {
        System.out.println("packetReceive");
    }

    @Override
    public void packetSend(PacketSendEvent<LoginSuccess> packetSendEvent) {
        System.out.println("packetSend");
    }
}
