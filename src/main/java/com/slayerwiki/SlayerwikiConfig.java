package com.slayerwiki;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Units;

@ConfigGroup("slayerwiki")
public interface SlayerwikiConfig extends Config
{
	@ConfigItem(
			keyName = "slayerHelm",
			name = "Slayer Helm",
			description = "Adds wiki to slayer helm",
			position = 1
	)
	default boolean slayerHelm()
	{
		return true;
	}

	@ConfigItem(
			keyName = "slayerGem",
			name = "Slayer Gem",
			description = "Adds wiki to slayer gem",
			position = 2
	)
	default boolean slayerGem()
	{
		return true;
	}
}
