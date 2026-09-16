package willow.train.kuayue.utils;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

/**
 * 1.21 NBT compatibility helper.
 * <p>
 * Minecraft 1.20.5+ removed {@code ItemStack#getOrCreateTag()}, {@code #getTag()}
 * and {@code #hasTag()} in favour of DataComponents. This helper mimics the old
 * live-reference semantics: mutations on the returned tag are persisted on the stack.
 */
public final class ItemNbtUtil {

    private ItemNbtUtil() {
    }

    /** Returns the (live) custom tag of the stack, creating it if absent. */
    public static CompoundTag getOrCreateTag(ItemStack stack) {
        CompoundTag tag;
        CustomData existing = stack.get(DataComponents.CUSTOM_DATA);
        if (existing != null) {
            tag = existing.getUnsafe();
        } else {
            tag = new CompoundTag();
        }
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        return tag;
    }

    /** Returns the (live) custom tag, or null when the stack has none. */
    public static CompoundTag getTag(ItemStack stack) {
        CustomData existing = stack.get(DataComponents.CUSTOM_DATA);
        return existing == null ? null : existing.getUnsafe();
    }

    /** Returns true when the stack carries a custom tag. */
    public static boolean hasTag(ItemStack stack) {
        return stack.has(DataComponents.CUSTOM_DATA);
    }

    /**
     * 1.21 removed {@code ItemStack#getOrCreateTagElement(String)}. Same contract: returns the
     * named sub-compound, creating and persisting it when absent. The returned tag is the live
     * instance, so mutations are carried by the stack's {@code CUSTOM_DATA} component.
     */
    public static CompoundTag getOrCreateTagElement(ItemStack stack, String name) {
        CompoundTag tag = getOrCreateTag(stack);
        if (!tag.contains(name, Tag.TAG_COMPOUND)) {
            tag.put(name, new CompoundTag());
        }
        return tag.getCompound(name);
    }
}
