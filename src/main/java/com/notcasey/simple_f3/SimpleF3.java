package com.notcasey.simple_f3;

import com.notcasey.simple_f3.config.SimpleF3Config;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleF3 implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("simple_f3");

	public static boolean simpleDebugEnabled = false;

	public static int rainbowHue;

	@Override
	public void onInitializeClient() {
		ClientTickEvents.START_CLIENT_TICK.register((tick) -> {
			if (!simpleDebugEnabled)
				return;

			rainbowHue += 1;
			if (rainbowHue > 255)
				rainbowHue = 0;
		});

		MidnightConfig.init("simple_f3", SimpleF3Config.class);
	}
}
