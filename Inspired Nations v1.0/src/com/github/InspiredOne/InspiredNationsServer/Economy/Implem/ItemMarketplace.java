package com.github.InspiredOne.InspiredNationsServer.Economy.Implem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.Economy.MarketPlace;
import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.SortTool;

public class ItemMarketplace implements MarketPlace<ItemSellable> {

	public static final String name = "Item Marketplace";
	
	public ItemMarketplace() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ItemSellable> getSales(Buyer viewer) {
		List<ItemSellable> output = new ArrayList<ItemSellable>();
		
		for(Class<? extends InspiredGov> govclass:InspiredNationsServer.regiondata.keySet()) {
			for(InspiredGov busi:InspiredNationsServer.regiondata.get(govclass)) {
				
				for(Facility fac:busi.getFacilities()) {
					if(fac instanceof ChestShop) {
						for(ItemSellable item:((ChestShop) fac).getItems()) {
							if(item.isForSale() && !output.contains(item)) {
								output.add(item);
							}
						}
					}
				}
			}
		}
		if(this.getComparators(viewer).size() > 0) {
			Collections.sort(output, this.getComparators(viewer).get(0).getComparator());
		}
		return output;
	}
	
	public ItemSellable getCheapestUnit(ItemStack stack, Buyer buy) {
		ItemSellable cheapest = null;
		for(ItemSellable item:this.getSales(buy)) {
			if(item.getItem().isSimilar(stack)) {
				if(cheapest == null) {
					cheapest = item;
					continue;
				}
				else {
					if(cheapest.getUnitPrice(buy.getCurrency(), buy.getLocation()).compareTo(item.
							getUnitPrice(buy.getCurrency(), buy.getLocation())) < 0) {
						cheapest = item;
					}
				}
			}
		}
		return cheapest;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		
	}

	@Override
	public String getDisplayName(PlayerID viewer) {
		return name;
	}

	@Override
	public List<SortTool<ItemSellable>> getComparators(final Buyer viewer) {
		List<SortTool<ItemSellable>> output = new ArrayList<SortTool<ItemSellable>>();
		// Alphabetically
		output.add(new SortTool<ItemSellable>() {

			@Override
			public String getName() {
				return "Alphabetically";
			}

			@Override
			public Comparator<ItemSellable> getComparator() {
				
				return new Comparator<ItemSellable>() {

					@Override
					public int compare(ItemSellable o1, ItemSellable o2) {
						return o1.getName().compareToIgnoreCase(o2.getName());
					}
					
				};
			}
			
		});
		
		// Price
		output.add(new SortTool<ItemSellable>() {

			@Override
			public String getName() {
				return "Price";
			}

			@Override
			public Comparator<ItemSellable> getComparator() {
				return new Comparator<ItemSellable>() {

					@Override
					public int compare(ItemSellable o1, ItemSellable o2) {

						return o1.getPrice(viewer.getCurrency(), viewer.getLocation()).compareTo(
								o2.getPrice(viewer.getCurrency(), viewer.getLocation()));
					}
				};
			}
		});
		
		// Unit Price
		output.add(new SortTool<ItemSellable>() {

			@Override
			public String getName() {
				return "Unit Price";
			}

			@Override
			public Comparator<ItemSellable> getComparator() {
				return new Comparator<ItemSellable>() {

					@Override
					public int compare(ItemSellable o1, ItemSellable o2) {
						BigDecimal o1uprice = o1.getPrice(viewer.getCurrency(), viewer.getLocation()).
								divide(new BigDecimal(o1.getItem().getAmount()), InspiredNations.Exchange.mcup);
						BigDecimal o2uprice = o2.getPrice(viewer.getCurrency(), viewer.getLocation()).
								divide(new BigDecimal(o2.getItem().getAmount()), InspiredNations.Exchange.mcup);
						
						return o1uprice.compareTo(o2uprice);
					}
				};
			}
		});
		
		// Distance
		output.add(new SortTool<ItemSellable>() {

			@Override
			public String getName() {
				return "Distance";
			}

			@Override
			public Comparator<ItemSellable> getComparator() {
				return new Comparator<ItemSellable>() {

					@Override
					public int compare(ItemSellable o1, ItemSellable o2) {
						double distdif = o1.getLocation().getLocation().distance(viewer.getLocation())
								- o2.getLocation().getLocation().distance(viewer.getLocation());
						if(distdif < 0) {
							return -1;
						}
						else if(distdif == 0) {
							return 0;
						}
						else  {
							return 1;
						}
					}
				};
			}
		});
		
		return output;
	}

}
