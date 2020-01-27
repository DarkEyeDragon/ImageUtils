package me.darkeyedragon.imageutils;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.glfw.GLFW;

import static me.darkeyedragon.imageutils.client.ImageUtilsMain.MODID;

public class Keybinds {

    public final static KeyBinding PARTIAL_SCREENSHOT = new KeyBinding(I18n.format("imageutil.keybind.partial_screenshot"), GLFW.GLFW_KEY_F4, MODID);

}
