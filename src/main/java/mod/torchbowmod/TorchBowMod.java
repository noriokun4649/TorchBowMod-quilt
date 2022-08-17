package mod.torchbowmod;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;

public class TorchBowMod implements ModInitializer {

    public static final String MODID = "torchbowmod";
	public static final ItemGroup TORCH_BOW_GROUP = QuiltItemGroup.builder(new Identifier(MODID, "torchbowmod_tab"))
			.icon(() -> new ItemStack(TorchBowMod.TORCH_BOW_ITEM)).build();
    public static final Item TORCH_BOW_ITEM = new TorchBow(new Item.Settings().group(TORCH_BOW_GROUP).maxDamage(384));
    public static final Item MULCH_TORCH_ITEM = new Item(new Item.Settings().group(TORCH_BOW_GROUP).maxCount(64));
    public static final Item TORCH_ARROW_ITEM = new TorchArrow(new Item.Settings().group(TORCH_BOW_GROUP).maxCount(64));
    public static final EntityType<TorchEntity> TORCH;

    static {
        TORCH = Registry.register(Registry.ENTITY_TYPE,
                new Identifier(MODID, "entitytorch"),
                FabricEntityTypeBuilder.<TorchEntity>create(SpawnGroup.MISC, TorchEntity::new)
                        .trackRangeBlocks(60).trackedUpdateRate(5).forceTrackedVelocityUpdates(true).build());
    }

	@Override
	public void onInitialize(ModContainer mod) {
		Registry.register(Registry.ITEM, new Identifier(MODID, "torchbow"), TORCH_BOW_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "multitorch"), MULCH_TORCH_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MODID, "torcharrow"), TORCH_ARROW_ITEM);
	}
}
