package me.darkeyedragon.imageutils.client.event;

import me.darkeyedragon.imageutils.client.ModConfig;
import me.darkeyedragon.imageutils.client.util.ServerUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class IngameGuiEvent {
    @SubscribeEvent
    public void event(RenderGameOverlayEvent.Text event){
        if (ModConfig.debug && Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            List<String> left = event.getLeft();
            if(!left.isEmpty()){
                event.getLeft().add("");
            }
            event.getLeft().add("Image server");
            event.getLeft().add("Reachable: " + ServerUtil.isReachable());
            event.getLeft().add("Response time: " + ServerUtil.getPing() + "ms");
        }
    }
}
