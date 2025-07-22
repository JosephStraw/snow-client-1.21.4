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

@Mixin(InGameHud.class)
public class SnowHUDMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void renderCustomOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.getNetworkHandler() == null) return;

        int fps = client.getCurrentFps();
        int ping = 0;

        PlayerListEntry entry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
        if (entry != null) {
            ping = entry.getLatency();
        }

        String text = "FPS: " + fps + "\nPing: " + ping + "ms";
        int x = 10;
        int y = 10;
        int color = 0xffffff;

        context.drawText(client.textRenderer, text, x, y, color, true);
    }


}
