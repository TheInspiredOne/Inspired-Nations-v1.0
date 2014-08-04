package com.github.InspiredOne.InspiredNations.Listeners.Implem;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNations.Exceptions.InspiredGovTooStrongException;
import com.github.InspiredOne.InspiredNations.Exceptions.InsufficientRefundAccountBalanceException;
import com.github.InspiredOne.InspiredNations.Exceptions.RegionOutOfEncapsulationBoundsException;
import com.github.InspiredOne.InspiredNations.Listeners.InspiredListener;
import com.github.InspiredOne.InspiredNations.Regions.Implem.SignShopLand;
import com.github.InspiredOne.InspiredNations.Regions.Implem.SignShopRegion;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.Point3D;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools;

public class ClaimSignShopListener extends InspiredListener<ClaimSignShopManager> {

	public SignShopLand land = new SignShopLand();
	public SignShopRegion region = new SignShopRegion();
	public boolean valid = false;
	PlayerData PDI;
	public ClaimSignShopListener(ClaimSignShopManager manager) {
		super(manager);
		Debug.print("Inside ClaimSignShopListeners 1");
		PDI = manager.getActionMenu().PDI;
		Debug.print("Inside ClaimSignShopListeners 2");
	}

	@EventHandler
	public void onSignPlace(BlockPlaceEvent event) {
		if(!this.getPlayerData().getPlayerID().equals(new PlayerID(event.getPlayer()))) {
			Debug.print("Inside onsignplace 0");
			return;
		}
		Debug.print("Inside onsignplace 1");
		Debug.print(event.getBlock().getType());
		if(event.getBlock().getType().equals(Material.SIGN_POST) || event.getBlock().getType().equals(Material.WALL_SIGN)) {
			Debug.print("Inside onsignplace 2");
			region.loca = new Point3D(event.getBlock().getLocation());
			valid = false;
			try {
				this.manager.getActionMenu().shop.setLand(region);
				valid = true;
			} catch (BalanceOutOfBoundsException e) {
				this.manager.getActionMenu().setError(MenuError.NOT_ENOUGH_MONEY(PDI));
			} catch (InspiredGovTooStrongException e) {
				this.manager.getActionMenu().setError(MenuError.GOV_TOO_STRONG(e.gov, PDI));
			} catch (RegionOutOfEncapsulationBoundsException e) {
				this.manager.getActionMenu().setError(MenuError.CLAIM_OUT_OF_BOUNDS(e.gov, PDI));
			} catch (InsufficientRefundAccountBalanceException e) {
				this.manager.getActionMenu().setError(MenuError.NOT_ENOUGH_MONEY(PDI));
			}
		}
		manager.Update();
	}
	
	@EventHandler
	public void onSignChangeText(SignChangeEvent event) {
		if(!this.getPlayerData().getPlayerID().equals(new PlayerID(event.getPlayer()))) {
			return;
		}
		if(valid) {
			Sign sign = (Sign) event.getBlock().getState();
			event.setLine(0, manager.getActionMenu().shop.getSuperGovObj().getName());
			event.setLine(1, manager.getActionMenu().getData().getName());
			event.setLine(2, manager.getActionMenu().getData().getItem().getAmount() + "");
			event.setLine(3, Tools.cut(manager.getActionMenu().getData().getPrice(PDI.getCurrency(), region.getCharacteristicPoint())).toString());

			sign.update(true);
			sign.update();
		}
	}
}