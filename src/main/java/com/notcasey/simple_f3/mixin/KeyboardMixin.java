package com.notcasey.simple_f3.mixin;

import com.notcasey.simple_f3.SimpleF3;
import com.notcasey.simple_f3.config.SimpleF3Config;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Shadow
    private MinecraftClient client;

    @Shadow
    private boolean switchF3State;

    // This is a really weird way of adding the second menu, but I don't feel like losing sanity over the other ways to mixin.
    @Inject(method = "onKey(JIIII)V", at = @At("TAIL"))
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (SimpleF3Config.IsDisabled())
            return;

        if (this.client.currentScreen == null || this.client.currentScreen.passEvents) {
            if (action == 0 && key == 292 && !this.switchF3State) {
                if (!this.client.options.debugEnabled && !SimpleF3.simpleDebugEnabled) {
                    SimpleF3.simpleDebugEnabled = true;
                }
                else if (this.client.options.debugEnabled && SimpleF3.simpleDebugEnabled) {
                    SimpleF3.simpleDebugEnabled = false;

                    this.client.options.debugEnabled = false;
                    this.client.options.debugProfilerEnabled = false;
                    this.client.options.debugTpsEnabled = false;
                }
            }
        }
    }
}
