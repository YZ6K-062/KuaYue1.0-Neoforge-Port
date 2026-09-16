package willow.train.kuayue.network.s2c;

import kasuga.lib.core.network.S2CPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import willow.train.kuayue.KuayueConfig;
import willow.train.kuayue.initial.AllElements;
import willow.train.kuayue.systems.editable_panel.ColorTemplate;
import willow.train.kuayue.systems.editable_panel.overlay.GetShareOverlay;

public class ColorTemplateS2CPacket extends S2CPacket {
    private final CompoundTag nbt;
    public ColorTemplateS2CPacket(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public ColorTemplateS2CPacket(FriendlyByteBuf buf) {
        super(buf);
        this.nbt = buf.readNbt();
    }

    @Override
    public void handle(Minecraft minecraft) {
        // TODO: need a hud;
        if (!KuayueConfig.CONFIG.getBoolValue("RECEIVE_COLOR_SHARE")) return;
        GetShareOverlay gso = willow.train.kuayue.initial.ClientInit.GET_SHARE_OVERLAY;
        gso.setTemplate(new ColorTemplate(this.nbt));
    }

    @Override
    public void encode(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeNbt(nbt);
    }
}
