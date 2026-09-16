package willow.train.kuayue.event.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import willow.train.kuayue.Kuayue;
import willow.train.kuayue.systems.overhead_line.block.support.OverheadLineSupportBlockEntity;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Kuayue.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
//        ItemBlockRenderTypes.setRenderLayer(FluidsInit.STILL_COLA.get(), RenderType.translucent());
//        ItemBlockRenderTypes.setRenderLayer(FluidsInit.FLOWING_COLA.get(), RenderType.translucent());
//        ItemBlockRenderTypes.setRenderLayer(FluidsInit.STILL_BLUE_BULL.get(), RenderType.translucent());
//        ItemBlockRenderTypes.setRenderLayer(FluidsInit.FLOWING_BLUE_BULL.get(), RenderType.translucent());
    }
}