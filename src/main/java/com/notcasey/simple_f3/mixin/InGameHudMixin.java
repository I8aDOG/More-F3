package com.notcasey.simple_f3.mixin;

import com.notcasey.simple_f3.SimpleDebugHud;
import com.notcasey.simple_f3.SimpleF3;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawableHelper {
	@Shadow @Final private MinecraftClient client;

	private static SimpleDebugHud simpleDebugHud;

	@Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/render/item/ItemRenderer;)V")
	private void ConstructorInject(MinecraftClient client, ItemRenderer itemRenderer, CallbackInfo info) {
		this.simpleDebugHud = new SimpleDebugHud(client);
	}

	@Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V")
	private void render(MatrixStack matrices, float tickDelta, CallbackInfo info) {
		if (SimpleF3.simpleDebugEnabled) {
			simpleDebugHud.render(matrices);
		}
	}
}
