package willow.train.kuayue.network.s2c;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import kasuga.lib.core.network.S2CPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.api.distmarker.Dist;
import net.minecraft.world.level.block.entity.BlockEntity;
public class ContraptionNbtUpdatePacket extends S2CPacket {
    private final int id;
    private final BlockPos blockPos;
    private final CompoundTag nbt;

    public ContraptionNbtUpdatePacket(int id, BlockPos blockPos, CompoundTag nbt){
        this.id = id;
        this.blockPos = blockPos;
        this.nbt = nbt;
    }

    public ContraptionNbtUpdatePacket(FriendlyByteBuf friendlyByteBuf){
        this.id = friendlyByteBuf.readInt();
        this.blockPos = friendlyByteBuf.readBlockPos();
        this.nbt = friendlyByteBuf.readNbt();
    }

    @Override
    public void handle(Minecraft minecraft) {
        if (FMLEnvironment.dist.isClient()) {

                if(minecraft.level == null)
                    return;
                Entity entity = minecraft.level.getEntity(id);
                if(entity instanceof AbstractContraptionEntity contraptionEntity){
                    var contraption = contraptionEntity.getContraption();
                    var blocks = contraption.getBlocks();
                    var blockData = blocks.get(blockPos);
                    if(blockData == null)
                        return;
                    var newBlockData = new StructureTemplate.StructureBlockInfo(blockPos, blockData.state(), nbt);
                    blocks.put(blockPos, newBlockData);
                    BlockEntity target = contraption.getBlockEntityClientSide(blockPos);
                    if(target != null){
                        // 1.21: BlockEntity#load(CompoundTag) 已移除，需显式传入 HolderLookup.Provider
                        target.loadWithComponents(nbt, minecraft.level.registryAccess());
                    }
                }
            
        }
    }

    @Override
    public void encode(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(id);
        friendlyByteBuf.writeBlockPos(blockPos);
        friendlyByteBuf.writeNbt(nbt);
    }
}
