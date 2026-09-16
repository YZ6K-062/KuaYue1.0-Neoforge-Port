package willow.train.kuayue.systems.train_extension.client.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.RemovedGuiUtils;
import net.createmod.catnip.theme.Color;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CClient;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;

import java.util.ArrayList;
import java.util.List;

public class TrainOverlayRenderer {
    private static int hoverTicks = 0;
    private static boolean stateChanged = false;

    public static LayeredDraw.Layer OVERLAY = TrainOverlayRenderer::render;

    public static boolean visible = false;
    public static ItemStack icon = ItemStack.EMPTY;
    public static Component message = Component.empty();

    public static void setVisible(boolean visible) {
        TrainOverlayRenderer.visible = visible;
    }

    public static void setShowInfo(ItemStack icon, Component message) {
        if(icon == null || message == null) return;

        TrainOverlayRenderer.icon = icon;
        TrainOverlayRenderer.message = message;
    }

    public static void clearShowInfo() {
        TrainOverlayRenderer.visible = false;
        TrainOverlayRenderer.icon = ItemStack.EMPTY;
        TrainOverlayRenderer.message = Component.empty();
    }

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        PoseStack poseStack = guiGraphics.pose();
        float partialTicks = deltaTracker.getGameTimeDeltaPartialTick(false);
        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();

        if (!visible || message.equals(Component.empty())) {
            hoverTicks = 0;
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui || mc.gameMode.getPlayerMode() == GameType.SPECTATOR)
            return;

        if(stateChanged) {
            hoverTicks = 1;
            stateChanged = false;
        } else {
            hoverTicks++;
        }

        Component component = icon.equals(ItemStack.EMPTY) ?
                message :
                Component.literal("    ").append(message);

        poseStack.pushPose();

        CClient cfg = AllConfigs.client();
        int posX = width / 2 + cfg.overlayOffsetX.get();
        int posY = height / 2 + cfg.overlayOffsetY.get();

        posX = Math.min(posX, width - mc.font.width(component) - 20);
        posY = Math.min(posY, height - 28);

        float fade = Mth.clamp((hoverTicks + partialTicks) / 24f, 0, 1);
        Boolean useCustom = cfg.overlayCustomColor.get();
        Color colorBackground = useCustom ? new Color(cfg.overlayBackgroundColor.get())
                : new Color(0xF0100010)
                .scaleAlpha(.75f);
        Color colorBorderTop = useCustom ? new Color(cfg.overlayBorderColorTop.get())
                : new Color(0x805000FF)
                .copy();
        Color colorBorderBot = useCustom ? new Color(cfg.overlayBorderColorBot.get())
                : new Color(0x8025952F)
                .copy();

        if (fade < 1) {
            poseStack.translate(Math.pow(1 - fade, 3) * Math.signum(cfg.overlayOffsetX.get() + .5f) * 8, 0, 0);
            colorBackground.scaleAlpha(fade);
            colorBorderTop.scaleAlpha(fade);
            colorBorderBot.scaleAlpha(fade);
        }

        List<Component> tooltip = new ArrayList<>();
        tooltip.add(component);

        RemovedGuiUtils.drawHoveringText(guiGraphics, tooltip, posX, posY, width, height, -1, colorBackground.getRGB(),
                colorBorderTop.getRGB(), colorBorderBot.getRGB(), mc.font);

        // Create 6.0 removed GuiGameElement; draw the item icon directly.
        guiGraphics.renderItem(icon, posX + 10, posY - 16);

        poseStack.popPose();
    }
}
