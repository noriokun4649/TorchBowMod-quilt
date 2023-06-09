package mod.torchbowmod;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

public class TorchBowMod implements ModInitializer {

    public static final String MODID = "torchbowmod";
	public static final ItemGroup TORCH_BOW_GROUP = FabricItemGroup.builder()
            .name(Text.translatable("itemGroup.torchbowmod.torchbowmod_tab"))
            .icon(() -> new ItemStack(TorchBowMod.TORCH_BOW_ITEM))
            .entries((enabledFeatures, entries) -> {
                entries.addItem(TorchBowMod.TORCH_BOW_ITEM);
                entries.addItem(TorchBowMod.MULCH_TORCH_ITEM);
                entries.addItem(TorchBowMod.TORCH_ARROW_ITEM);
            })
            .build();
    public static final Item TORCH_BOW_ITEM = new TorchBow(new Item.Settings().maxDamage(384));
    public static final Item MULCH_TORCH_ITEM = new Item(new Item.Settings().maxCount(64));
    public static final Item TORCH_ARROW_ITEM = new TorchArrow(new Item.Settings().maxCount(64));
    public static final EntityType<TorchEntity> TORCH;

    static {
        TORCH = Registry.register(Registries.ENTITY_TYPE,
                new Identifier(MODID, "entitytorch"),
                QuiltEntityTypeBuilder.<TorchEntity>create(SpawnGroup.MISC, TorchEntity::new)
                        .maxBlockTrackingRange(60).trackingTickInterval(5).alwaysUpdateVelocity(true).build());
    }

	@Override
	public void onInitialize(ModContainer mod) {
        Registry.register(Registries.ITEM_GROUP, new Identifier(MODID, "torchbowmod_tab"), TORCH_BOW_GROUP);
        Registry.register(Registries.ITEM, new Identifier(MODID, "torchbow"), TORCH_BOW_ITEM);
		Registry.register(Registries.ITEM, new Identifier(MODID, "multitorch"), MULCH_TORCH_ITEM);
		Registry.register(Registries.ITEM, new Identifier(MODID, "torcharrow"), TORCH_ARROW_ITEM);
	}
}
