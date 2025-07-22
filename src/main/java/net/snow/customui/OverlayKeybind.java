package net.snow.customui;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class OverlayKeybind implements ClientModInitializer {

    //default setting is on
    public static boolean overlayEnabled = true;
    public static KeyBinding toggleOverlayKey;

    @Override
    public void onInitializeClient() {
        toggleOverlayKey = new KeyBinding (
                "key.SnowClient.toggle_overlay",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.SnowClient.keys");

        //Register the key bind to in game tick
        net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding(toggleOverlayKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleOverlayKey.wasPressed()) {
                overlayEnabled = !overlayEnabled;
            }
        });

    }
}
