/*
 * This file is part of the TweakerMore project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2023  Fallen_Breath and contributors
 *
 * TweakerMore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TweakerMore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TweakerMore.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.fallenbreath.tweakermore.impl.features.autoVillagerTradeFavorites;

import fi.dy.masa.itemscroller.util.InventoryUtils;
import fi.dy.masa.malilib.util.GuiUtils;
import me.fallenbreath.tweakermore.TweakerMoreMod;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.config.options.TweakerMoreConfigBoolean;
import me.fallenbreath.tweakermore.config.options.TweakerMoreConfigBooleanHotkeyed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//#if MC >= 12111
//$$ import fi.dy.masa.itemscroller.villager.VillagerDataStorage;
//$$ import fi.dy.masa.malilib.util.InfoUtils;
//$$ import fi.dy.masa.itemscroller.villager.VillagerUtils;
//#endif

public class MerchantAutoFavoritesTrader
{
	public static void doAutoTrade(Minecraft mc)
	{
		TweakerMoreConfigBooleanHotkeyed config = TweakerMoreConfigs.AUTO_VILLAGER_TRADE_FAVORITES;
		if (config.getTweakerMoreOption().isEnabled() && config.getBooleanValue())
		{
			Screen screen = GuiUtils.getCurrentScreen();
			if (screen instanceof MerchantScreen)
			{
				MerchantMenu container = ((MerchantScreen) screen).getMenu();

				//#if MC >= 12111
	//$$ if (
	//$$ 		!VillagerDataStorage.getInstance().getFavoritesForCurrentVillager(container).
	//$$ 		favorites().isEmpty()
	//$$ )
	//$$ {
	//$$ 	TweakerMoreConfigBoolean dropOutputConfig = TweakerMoreConfigs.AUTO_VILLAGER_TRADE_FAVORITES_DROP_OUTPUT;
	//$$ 	boolean dropOutput = dropOutputConfig.getBooleanValue();
	//$$ 	MerchantScreen merchantScreen = (MerchantScreen) screen;
	//$$ 	it.unimi.dsi.fastutil.ints.IntList originalFavorites = VillagerDataStorage.getInstance().getFavoritesForCurrentVillager(container).favorites();
	//$$
	//$$ 	MerchantOffers visibleList = container.getOffers();
	//$$ 	List<Integer> visibleFavorites = new ArrayList<>();
	//$$ 	for (int visibleIndex = 0; visibleIndex < visibleList.size(); visibleIndex++)
	//$$ 	{
	//$$ 		int realIndex = VillagerUtils.getRealTradeIndexFor(visibleIndex, container);
	//$$ 		if (realIndex != -1 && originalFavorites.contains(realIndex))
	//$$ 		{
	//$$ 			visibleFavorites.add(visibleIndex);
	//$$ 		}
	//$$ 	}
	//$$
	//$$ 	for (int visibleIndex : visibleFavorites)
	//$$ 	{
	//$$ 		if (dropOutput)
	//$$ 		{
	//$$ 			tradeAndDropOutput(merchantScreen, visibleIndex);
	//$$ 		}
	//$$ 		else
	//$$ 		{
	//$$ 			InventoryUtils.villagerTradeEverythingPossibleWithTrade(visibleIndex);
	//$$ 		}
	//$$ 	}
	//$$ 	InventoryUtils.villagerClearTradeInputSlots();
	//$$ 	InfoUtils.printActionbarMessage("tweakermore.impl.autoVillagerTradeFavorites.triggered", config.getPrettyName(), screen.getTitle());
	//$$ }
	//$$ else
	//$$ {
	//$$ 	InfoUtils.printActionbarMessage("tweakermore.impl.autoVillagerTradeFavorites.no_favorite", screen.getTitle());
	//$$ }
	//#else
				Object dummy = InventoryUtils.class;
				//#endif

				Objects.requireNonNull(mc.player).closeContainer();
			}
		}
	}

	//#if MC >= 12111
	//$$ private static void tradeAndDropOutput(MerchantScreen merchantScreen, int visibleIndex)
	//$$ {
	//$$ 	MerchantMenu handler = merchantScreen.getMenu();
	//$$ 	try
	//$$ 	{
	//$$ 		if (handler.getOffers().isEmpty()) return;
	//$$ 	}
	//$$ 	catch (Exception ignored) { return; }
	//$$
	//$$ 	ItemStack sellItem = handler.getOffers().get(visibleIndex).getResult().copy();
	//$$ 	while (true)
	//$$ 	{
	//$$ 		VillagerUtils.switchToTradeByVisibleIndex(visibleIndex);
	//$$ 		if (InventoryUtils.areStacksEqual(sellItem, handler.getSlot(2).getItem()) == false)
	//$$ 			break;
	//$$ 		InventoryUtils.dropStack(merchantScreen, 2);
	//$$ 		if (handler.getSlot(2).hasItem())
	//$$ 			break;
	//$$ 	}
	//$$ }
	//#endif
}

