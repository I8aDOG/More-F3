package com.notcasey.simple_f3;

import com.google.common.base.Strings;
import com.notcasey.simple_f3.config.SimpleF3Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class SimpleDebugHud extends DrawableHelper {
    private static final int TEXT_COLOR = 14737632;
    private final MinecraftClient client;
    private final TextRenderer textRenderer;

    public SimpleDebugHud(MinecraftClient client) {
        this.client = client;
        this.textRenderer = client.textRenderer;
    }

    public void render(MatrixStack matrices) {
        if (SimpleF3Config.IsDisabled()) {
            SimpleF3.simpleDebugEnabled = false;
            return;
        }

        BlockPos blockPos = this.client.getCameraEntity().getBlockPos();

        List<String> list = new ArrayList<String>();
        if (SimpleF3Config.show_game_version)
            list.add("Minecraft ".concat(SharedConstants.getGameVersion().getName()));
        if (SimpleF3Config.show_fps)
            list.add(Integer.toString(this.client.getCurrentFps()).concat(" fps"));

        if (SimpleF3Config.show_game_version || SimpleF3Config.show_fps)
            list.add("");

        if (SimpleF3Config.show_coords)
            list.add(String.format(Locale.ROOT, "XYZ: %.3f / %.5f / %.3f", this.client.getCameraEntity().getX(), this.client.getCameraEntity().getY(), this.client.getCameraEntity().getZ()));
        if (SimpleF3Config.show_biome && blockPos.getY() >= this.client.world.getBottomY() && blockPos.getY() < this.client.world.getTopY()) {
            RegistryEntry biome = this.client.world.getBiome(blockPos);
            list.add("Biome: " + getBiomeString(biome));
        }

        for(int i = 0; i < list.size(); ++i) {
            String string = (String)list.get(i);
            if (!Strings.isNullOrEmpty(string)) {
                Objects.requireNonNull(this.textRenderer);
                int j = 9;
                int k = this.textRenderer.getWidth(string);
                int l = 2;
                int m = 2 + j * i;

                if (SimpleF3Config.right_text)
                    l = this.client.getWindow().getScaledWidth() - 2 - k;

                int textColor = 14737632;
                if (SimpleF3Config.rainbow_text)
                    textColor = Color.HSBtoRGB((float) SimpleF3.rainbowHue/255f, 1f, 1f);

                if (SimpleF3Config.classic_style)
                    this.textRenderer.drawWithShadow(matrices, string, (float)l, (float)m, textColor);
                else {
                    fill(matrices, l - 1, m - 1, l + k + 1, m + j - 1, -1873784752);
                    this.textRenderer.draw(matrices, string, (float) l, (float) m, textColor);
                }
            }
        }
    }

    private static String getBiomeString(RegistryEntry<Biome> biome) {
        return (String)biome.getKeyOrValue().map((biomeKey) -> {
            return biomeKey.getValue().toString();
        }, (biome_) -> {
            return "[unregistered " + biome_ + "]";
        });
    }
}
