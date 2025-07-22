package net.snow.customui.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//ensures up-to-date syntax is used, HUDcallback is now depreciated I think
@Mixin(InGameHud.class)
public class SnowHUDMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void renderCustomOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) { //do not use float value
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.getNetworkHandler() == null) return;

        int fps = client.getCurrentFps();
        int ping = 0;

        PlayerListEntry entry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
        if (entry != null) {
            ping = entry.getLatency();
        }

        String FPSText = "FPS: " + fps;
        String PingText = "Ping: " + ping + "ms";

        int x = 10;
        int y = 10;
        int lineHeight = client.textRenderer.fontHeight + 2; //adds gap between FPS and Ping, made it a variable why not
        int color = 0xffffff;

        context.drawText(client.textRenderer, FPSText, x, y, color, true);
        context.drawText(client.textRenderer, PingText, x, y + lineHeight, color, true);
    }


}
