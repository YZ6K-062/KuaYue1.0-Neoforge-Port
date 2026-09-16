package willow.train.kuayue.systems.overhead_line.block.support;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.neoforged.fml.loading.FMLEnvironment;

public class CustomRendererItem extends BlockItem {
    private Supplier<Supplier<BlockEntityWithoutLevelRenderer>> renderer;

    public CustomRendererItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    public CustomRendererItem withRenderer(Supplier<Supplier<BlockEntityWithoutLevelRenderer>> renderer) {
        this.renderer = renderer;
        return this;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        if (FMLEnvironment.dist.isClient()) consumer.accept(new CustomRendererItemExtension(
                        () -> this.renderer.get()
                ));
    }
}