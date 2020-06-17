package com.slayerwiki;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import com.google.common.base.CaseFormat;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.Notifier;
import net.runelite.client.menus.MenuManager;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.MenuOpened;
import net.runelite.client.plugins.slayer.SlayerConfig;
import net.runelite.client.util.ColorUtil;
import net.runelite.api.ItemID;
import net.runelite.client.util.Text;

import java.awt.*;
import java.util.Set;


@Slf4j
@PluginDescriptor(
		name = "Slayer Wiki",
		description = "Adds Wiki option to slayer equipment to lookup current task.",
		tags = {"slayer", "wiki", "slayer wiki"}
)
public class SlayerwikiPlugin extends Plugin
{
	private static final String SLAYER_HELMET = "slayer helmet";
	private static final String ETERNAL_GEM = "eternal gem";
	private static final String ENCHANTED_GEM = "enchanted gem";
	private static final String SLAYER_RING = "slayer ring";


	private final static Set<String> SLAYER_ITEMS = ImmutableSet.of(SLAYER_HELMET, SLAYER_RING, ETERNAL_GEM, ENCHANTED_GEM);

	@Inject
	private Client client;

	@Inject
	private SlayerwikiConfig config;

	@Inject
	private SlayerConfig slayerConfig;

	@Inject
	private Notifier notifier;

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{

	}

	@Subscribe
	public void onMenuOpened(MenuOpened event) {

		final String target = Text.removeTags(event.getFirstEntry().getTarget().toLowerCase());

		for ( String slayerItem : SLAYER_ITEMS) {
			if (target.contains(slayerItem)) {
				final String slayerTask = slayerConfig.taskName();
				if (!slayerTask.isEmpty()) {
					final String TASK = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, slayerTask);
					final MenuEntry entry = new MenuEntry();

					MenuEntry[] menuList = event.getMenuEntries();
					entry.setOption("Wiki");
					entry.setTarget(ColorUtil.prependColorTag(TASK, Color.orange));
					entry.setType(MenuAction.RUNELITE.getId());
					menuList[1] = entry;
					client.setMenuEntries(menuList);
				}
				break;
			}
		}
	}


	@Subscribe
	public void onGameTick(GameTick event)
	{
		final Player local = client.getLocalPlayer();
	}

	@Provides
	SlayerwikiConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(SlayerwikiConfig.class);
	}
	@Provides
	SlayerConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(SlayerConfig.class);
	}
}
