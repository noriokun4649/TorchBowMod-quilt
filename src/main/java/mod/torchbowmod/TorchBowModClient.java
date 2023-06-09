package mod.torchbowmod;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import static mod.torchbowmod.TorchBowMod.*;

@Environment(EnvType.CLIENT)
public class TorchBowModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		ModelPredicateProviderRegistry.register(TORCH_BOW_ITEM, new Identifier("pull"), (itemStack, world, livingEntity, seed) -> {
			if (livingEntity == null) {
				return 0.0F;
			}
			return livingEntity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
		});
		ModelPredicateProviderRegistry.register(TORCH_BOW_ITEM, new Identifier("pulling"), (itemStack, world, livingEntity, seed) -> {
			if (livingEntity == null) {
				return 0.0F;
			}
			return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
		});
		EntityRendererRegistry.register(TORCH, TorchEntityRender::new);
		ClientPlayNetworking.registerGlobalReceiver(new Identifier(MODID, "spawntorch"), (client, handler, buf, responseSender) -> {
			int entityId = buf.readInt();
			double x = buf.readDouble();
			double y = buf.readDouble();
			double z = buf.readDouble();
			client.execute(() -> {
				ClientWorld world = client.world;
				PlayerEntity playerEntity = client.player;
				TorchEntity torchEntity = new TorchEntity(world, playerEntity);
				torchEntity.setPos(x, y, z);
				torchEntity.setId(entityId);
				torchEntity.syncPacketPositionCodec(x, y, z);
				if (world != null) world.addEntity(entityId, torchEntity);
			});
		});
	}
}

