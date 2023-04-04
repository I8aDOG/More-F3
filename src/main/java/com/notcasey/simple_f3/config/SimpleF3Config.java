package com.notcasey.simple_f3.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class SimpleF3Config extends MidnightConfig {
    @Comment(centered = true) public static Comment general_comment;
    @Entry public static boolean auto_enable = false;
    @Comment(centered = true) public static Comment display_comment;
    @Entry public static boolean show_game_version = false;
    @Entry public static boolean show_fps = true;
    @Entry public static boolean show_coords = false;
    @Entry public static boolean show_biome = false;
    @Comment(centered = true) public static Comment rendering_comment;
    @Entry public static boolean right_text = false;
    @Entry public static boolean classic_style = false;
    @Entry public static boolean rainbow_text = false;

    public static boolean IsDisabled() {
        return !show_game_version && !show_fps && !show_coords && !show_biome;
    }

}
