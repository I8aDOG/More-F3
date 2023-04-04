package com.notcasey.simple_f3.mixin;

import com.notcasey.simple_f3.SimpleF3;
import com.notcasey.simple_f3.config.SimpleF3Config;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        SimpleF3.simpleDebugEnabled = SimpleF3Config.auto_enable;
    }
}
