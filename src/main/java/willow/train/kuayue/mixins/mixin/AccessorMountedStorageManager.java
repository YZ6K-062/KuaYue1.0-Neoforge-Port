package willow.train.kuayue.mixins.mixin;

import com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorage;
import com.simibubi.create.api.contraption.storage.item.MountedItemStorage;
import com.simibubi.create.api.contraption.storage.item.MountedItemStorageWrapper;
import com.simibubi.create.content.contraptions.MountedStorageManager;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(MountedStorageManager.class)
public interface AccessorMountedStorageManager {
    @Accessor("itemsBuilder")
    Map<BlockPos, MountedItemStorage> getStorage();

    @Accessor("itemsBuilder")
    void setStorage(Map<BlockPos, MountedItemStorage> storage);

    @Accessor("fluidsBuilder")
    Map<BlockPos, MountedFluidStorage> getFluidStorage();

    @Accessor("fluidsBuilder")
    void setFluidStorage(Map<BlockPos, MountedFluidStorage> fluidStorage);

    // Create 6.0 dropped Contraption.ContraptionInvWrapper and the "allItems" field;
    // MountedStorageManager now exposes "items" (a CombinedInvWrapper / IItemHandler).
    @Accessor("items")
    MountedItemStorageWrapper getItems();
}
