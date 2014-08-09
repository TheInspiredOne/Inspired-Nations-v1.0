package com.github.InspiredOne.InspiredNationsServer.Economy;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.github.InspiredOne.InspiredNationsServer.Economy.Nodes.CobDugNode;
import com.github.InspiredOne.InspiredNationsServer.Economy.Nodes.ItemNode;
import com.github.InspiredOne.InspiredNationsServer.Economy.Nodes.Node;
import com.github.InspiredOne.InspiredNationsServer.Economy.Nodes.PerfectCompNode;
import com.github.InspiredOne.InspiredNationsServer.Economy.Nodes.PerfectSubNode;

public class ItemRefTrees {

	public ItemRefTrees item;
	public Node smelt;
	public Node goldIngot;
	public Node ironIngot;
	public Node diamond;
	public Node stick;
	public Node plank;
	public Node armor;
	public Node tools;
	
	public ItemRefTrees() {
		item = this;
		
		plank = new PerfectSubNode( new double[] {1,1,1,1,1./3.,1,1,1,1}, new Node[] {
				new ItemNode( new ItemStack(Material.WOOD,1,(short) 0)), // Oak Plank
				new ItemNode( new ItemStack(Material.WOOD,1,(short) 1)), // Spruce Plank
				new ItemNode( new ItemStack(Material.WOOD,1,(short) 2)), // Birch Plank
				new ItemNode( new ItemStack(Material.WOOD,1,(short) 3)), // Jungle Plank
				new ItemNode( new ItemStack(Material.BOAT, 1, (short) 0)), // Boat
				new PerfectCompNode( new double[] {1./4.}, new Node[] {
						new ItemNode( new ItemStack(Material.LOG,1,(short) 0)) // Oak Log
				}),
				new PerfectCompNode( new double[] {1./4.}, new Node[] {
						new ItemNode( new ItemStack(Material.LOG,1,(short) 1)) // Spruce Log
				}),
				new PerfectCompNode( new double[] {1./4.}, new Node[] {
						new ItemNode( new ItemStack(Material.LOG,1,(short) 2)) // Birch Log
				}),
				new PerfectCompNode( new double[] {1./4.}, new Node[] {
						new ItemNode( new ItemStack(Material.LOG,1,(short) 3)) // Jungle Log
				})
				
		});
		diamond = new PerfectSubNode( new double[] {1,1,1}, new Node[] {
				new PerfectCompNode( new double[] {1}, new Node[] {
					new ItemNode( new ItemStack(Material.DIAMOND, 1, (short) 0)) // Diamond
				}),
				new PerfectCompNode( new double[] {1}, new Node[] {
					new ItemNode( new ItemStack(Material.DIAMOND_ORE, 1, (short) 0)) // Diamond Ore
				}),
				new PerfectCompNode( new double[] {1./9.}, new Node[] {
					new ItemNode( new ItemStack(Material.DIAMOND_BLOCK, 1, (short) 0)) // Diamond Block
				})
			});
		stick = new PerfectSubNode( new double[] {1, 1./2.}, new Node[] {
				new ItemNode( new ItemStack(Material.STICK, 1, (short) 0), new Node[] { // Stick
					new PerfectCompNode( new double[] {2}, new Node[] {
							this.plank
					})
				}),
				new ItemNode( new ItemStack(Material.BOAT, 1, (short) 0)) // Boat
		});
		
		smelt = new PerfectSubNode( new double[] {1./8., 1./8., 1./72., 1, 1, 2, 2./3.}, new Node[] {
					new ItemNode( new ItemStack(Material.COAL, 1, (short) 0)), // Coal
					new ItemNode( new ItemStack(Material.COAL, 1, (short) 1)), // Charcoal
					new ItemNode( new ItemStack(Material.COAL_BLOCK, 1, (short) 0)), // Coal Block
					new ItemNode( new ItemStack(Material.WOOD_PICKAXE, 1, (short) 0), new Node[] { // Wooden Pickaxe
						new PerfectCompNode( new double[] {2,3}, new Node[] {
								this.stick,
								this.plank
						})
					}),
					new ItemNode( new ItemStack(Material.WOOD_SPADE, 1, (short) 0), new Node[] { // Wooden Shovel
						new PerfectCompNode( new double[] {2,1}, new Node[] {
								this.stick,
								this.plank
						})
					}),
					new ItemNode( new ItemStack(Material.WOOD_AXE, 1, (short) 0), new Node[] { // Wooden Axe
						new PerfectCompNode( new double[] {2,3}, new Node[] {
								this.stick,
								this.plank
						})
					}),
					new ItemNode( new ItemStack(Material.WOOD_HOE, 1, (short) 0), new Node[] { // Wooden Hoe
						new PerfectCompNode( new double[] {2,2}, new Node[] {
								this.stick,
								this.plank
						})
					}),
					this.stick,
					this.plank
				});
		goldIngot = new PerfectSubNode( new double[] {1,1,1,1}, new Node[] {
				new ItemNode( new ItemStack(Material.GOLD_INGOT, 1, (short) 0)), // Gold Ingot
				new PerfectCompNode( new double[] {9}, new Node[] {
					new ItemNode( new ItemStack(Material.GOLD_NUGGET, 1, (short) 0)) // Gold Nugget	
				}),
				new PerfectCompNode( new double[] {1, 1}, new Node[] {
					new ItemNode( new ItemStack(Material.GOLD_ORE, 1, (short) 0)), // Gold Ore
					smelt
				}),
				new PerfectCompNode( new double[] {1./9.}, new Node[] {
					new ItemNode( new ItemStack(Material.GOLD_BLOCK, 1, (short) 0)) // Gold Block
				})
			});

		ironIngot = new PerfectSubNode( new double[] {1,1,1}, new Node[] {
				new PerfectCompNode( new double[] {1}, new Node[] {
					new ItemNode( new ItemStack(Material.IRON_INGOT, 1, (short) 0)) // Iron Ingot
				}),
				new PerfectCompNode( new double[] {1, 1./8.}, new Node[] {
					new ItemNode( new ItemStack(Material.IRON_ORE, 1, (short) 0)), // Iron Ore
					smelt
				}),
				new PerfectCompNode( new double[] {1/9}, new Node[] {
					new ItemNode( new ItemStack(Material.IRON_BLOCK, 1, (short) 0)) // Iron Block
				})
			});
		
		armor = new CobDugNode( new double[] {1.25,2,1.75,1}, new Node[] { // Armor
				// { Helmet
				new PerfectSubNode( new double[] {1,3,4,6,10}, new Node[] { // Helmet
						new ItemNode( new ItemStack(Material.LEATHER_HELMET, 1, (short) 0), new Node[] { // Leather Helmet
							new PerfectCompNode( new double[] {5}, new Node[] {
								new ItemNode( new ItemStack(Material.LEATHER, 1, (short) 0)) // Leather
							})
						}),
						new ItemNode( new ItemStack(Material.CHAINMAIL_HELMET, 1, (short) 0), new Node[] { // Chain Helmet
							new PerfectCompNode( new double[] {5}, new Node[] {
								new ItemNode( new ItemStack(Material.FIRE, 1, (short) 0)) // Fire
							})
						}),
						new ItemNode( new ItemStack(Material.GOLD_HELMET, 1, (short) 0), new Node[] { // Gold Helmet
							new PerfectCompNode( new double[] {5}, new Node[] {
									item.goldIngot
							})
						}),
						new ItemNode( new ItemStack(Material.IRON_HELMET, 1, (short) 0), new Node[] { // Iron Helmet
							new PerfectCompNode( new double[] {5}, new Node[] {
									item.ironIngot
							})
						}),
						new ItemNode( new ItemStack(Material.DIAMOND_HELMET, 1, (short) 0), new Node[] { // Diamond Helmet
							new PerfectCompNode( new double[] {5}, new Node[] {
								item.diamond
							})
						})
				}),
				// }
				// { ChestPlate
				new PerfectSubNode( new double[] {1,3,4,6,10}, new Node[] { // Chestplate
						new ItemNode( new ItemStack(Material.LEATHER_CHESTPLATE, 1, (short) 0), new Node[] { // Leather Chestplate
							new PerfectCompNode( new double[] {8}, new Node[] {
								new ItemNode( new ItemStack(Material.LEATHER, 1, (short) 0)) // Leather
							})
						}),
						new ItemNode( new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1, (short) 0), new Node[] { // Chain Chestplate
							new PerfectCompNode( new double[] {8}, new Node[] {
								new ItemNode( new ItemStack(Material.FIRE, 1, (short) 0)) // Fire
							})
						}),
						new ItemNode( new ItemStack(Material.GOLD_CHESTPLATE, 1, (short) 0), new Node[] { // Gold Chestplate
							new PerfectCompNode( new double[] {8}, new Node[] {
									item.goldIngot
							})
						}),
						new ItemNode( new ItemStack(Material.IRON_CHESTPLATE, 1, (short) 0), new Node[] { // Iron Chestplate
							new PerfectCompNode( new double[] {8}, new Node[] {
									item.ironIngot
							})
						}),
						new ItemNode( new ItemStack(Material.DIAMOND_CHESTPLATE, 1, (short) 0), new Node[] { // Diamond Chestplate
							new PerfectCompNode( new double[] {8}, new Node[] {
								item.diamond
							})
						})
				}),
				// }
				// { Leggings
				new PerfectSubNode( new double[] {1,3,4,6,10}, new Node[] { // Leggings
						new ItemNode( new ItemStack(Material.LEATHER_LEGGINGS, 1, (short) 0), new Node[] { // Leather Leggings
							new PerfectCompNode( new double[] {7}, new Node[] {
								new ItemNode( new ItemStack(Material.LEATHER, 1, (short) 0)) // Leather
							})
						}),
						new ItemNode( new ItemStack(Material.CHAINMAIL_LEGGINGS, 1, (short) 0), new Node[] { // Chain Leggings
							new PerfectCompNode( new double[] {7}, new Node[] {
								new ItemNode( new ItemStack(Material.FIRE, 1, (short) 0)) // Fire
							})
						}),
						new ItemNode( new ItemStack(Material.GOLD_LEGGINGS, 1, (short) 0), new Node[] { // Gold Leggings
							new PerfectCompNode( new double[] {7}, new Node[] {
									item.goldIngot
							})
						}),
						new ItemNode( new ItemStack(Material.IRON_LEGGINGS, 1, (short) 0), new Node[] { // Iron Leggings
							new PerfectCompNode( new double[] {7}, new Node[] {
									item.ironIngot
							})
						}),
						new ItemNode( new ItemStack(Material.DIAMOND_LEGGINGS, 1, (short) 0), new Node[] { // Diamond Leggings
							new PerfectCompNode( new double[] {7}, new Node[] {
								item.diamond
							})
						})
				}),
				// }
				// { Boots
				new PerfectSubNode( new double[] {1,3,4,6,10}, new Node[] { // Boots
						new ItemNode( new ItemStack(Material.LEATHER_BOOTS, 1, (short) 0), new Node[] { // Leather Boots
							new PerfectCompNode( new double[] {4}, new Node[] {
								new ItemNode( new ItemStack(Material.LEATHER, 1, (short) 0)) // Leather
							})
						}),
						new ItemNode( new ItemStack(Material.CHAINMAIL_BOOTS, 1, (short) 0), new Node[] { // Chain Boots
							new PerfectCompNode( new double[] {4}, new Node[] {
								new ItemNode( new ItemStack(Material.FIRE, 1, (short) 0)) // Fire
							})
						}),
						new ItemNode( new ItemStack(Material.GOLD_BOOTS, 1, (short) 0), new Node[] { // Gold Boots
							new PerfectCompNode( new double[] {4}, new Node[] {
									item.goldIngot
							})
						}),
						new ItemNode( new ItemStack(Material.IRON_BOOTS, 1, (short) 0), new Node[] { // Iron Boots
							new PerfectCompNode( new double[] {4}, new Node[] {
									item.ironIngot
							})
						}),
						new ItemNode( new ItemStack(Material.DIAMOND_BOOTS, 1, (short) 0), new Node[] { // Diamond Boots
							new PerfectCompNode( new double[] {4}, new Node[] {
								item.diamond
							})
						})
				})
				// }
			});
		tools =	new CobDugNode( new double[] {5,4,3.5,2,1}, new Node[] { // Tools
				// { Pickaxe
				new PerfectSubNode( new double[] {1,2,2.5,5,7}, new Node[] { // Pickaxe
						new ItemNode( new ItemStack(Material.WOOD_PICKAXE, 1, (short) 0), new Node[] { // Wooden Pickaxe
							new PerfectCompNode( new double[] {2,3}, new Node[] {
									item.stick,
									item.plank
							})
						}),
						new ItemNode( new ItemStack(Material.STONE_PICKAXE, 1, (short) 0), new Node[] { // Stone Pickaxe
							new PerfectCompNode( new double[] {2,3}, new Node[] {
									item.stick,
									new ItemNode( new ItemStack(Material.COBBLESTONE, 1, (short) 0)) // Cobblestone
							})
						}),
						new ItemNode( new ItemStack(Material.GOLD_PICKAXE, 1, (short) 0), new Node[] { // Gold Pickaxe
							new PerfectCompNode( new double[] {2,3}, new Node[] {
									item.stick,
									item.goldIngot
							})
						}),
						new ItemNode( new ItemStack(Material.IRON_PICKAXE, 1, (short) 0), new Node[] { // Iron Pickaxe
							new PerfectCompNode( new double[] {2,3}, new Node[] {
									item.stick,
									item.ironIngot
							})
						}),
						new ItemNode( new ItemStack(Material.DIAMOND_PICKAXE, 1, (short) 0), new Node[] { // Diamond Pickaxe
							new PerfectCompNode( new double[] {2,3}, new Node[] {
									item.stick,
									item.diamond
							})
						})
				}),
				// }
				// { Shovel
				new PerfectSubNode( new double[] {1,2,4,5,7}, new Node[] { // Shovel
						new ItemNode( new ItemStack(Material.WOOD_SPADE, 1, (short) 0), new Node[] { // Wooden Shovel
							new PerfectCompNode( new double[] {2,1}, new Node[] {
									item.stick,
									item.plank
							})
						}),
						new ItemNode( new ItemStack(Material.STONE_SPADE, 1, (short) 0), new Node[] { // Stone Shovel
							new PerfectCompNode( new double[] {2,1}, new Node[] {
									item.stick,
									new ItemNode( new ItemStack(Material.COBBLESTONE, 1, (short) 0)) // Cobblestone
							})
						}),
						new ItemNode( new ItemStack(Material.GOLD_SPADE, 1, (short) 0), new Node[] { // Gold Shovel
							new PerfectCompNode( new double[] {2,1}, new Node[] {
									item.stick,
									item.goldIngot
							})
						}),
						new ItemNode( new ItemStack(Material.IRON_SPADE, 1, (short) 0), new Node[] { // Iron Shovel
							new PerfectCompNode( new double[] {2,1}, new Node[] {
									item.stick,
									item.ironIngot
							})
						}),
						new ItemNode( new ItemStack(Material.DIAMOND_SPADE, 1, (short) 0), new Node[] { // Diamond Shovel
							new PerfectCompNode( new double[] {2,1}, new Node[] {
									item.stick,
									item.diamond
							})
						})
				}),
				// }
				// { Axe
				new PerfectSubNode( new double[] {1,2,4,5,7}, new Node[] { // Axe
						new ItemNode( new ItemStack(Material.WOOD_AXE, 1, (short) 0), new Node[] { // Wooden Axe
							new PerfectCompNode( new double[] {2,3}, new Node[] {
									item.stick,
									item.plank
							})
						}),
						new ItemNode( new ItemStack(Material.STONE_AXE, 1, (short) 0), new Node[] { // Stone Axe
							new PerfectCompNode( new double[] {2,3}, new Node[] {
									item.stick,
									new ItemNode( new ItemStack(Material.COBBLESTONE, 1, (short) 0)) // Cobblestone
							})
						}),
						new ItemNode( new ItemStack(Material.GOLD_AXE, 1, (short) 0), new Node[] { // Gold Axe
							new PerfectCompNode( new double[] {2,3}, new Node[] {
									item.stick,
									item.goldIngot
							})
						}),
						new ItemNode( new ItemStack(Material.IRON_AXE, 1, (short) 0), new Node[] { // Iron Axe
							new PerfectCompNode( new double[] {2,3}, new Node[] {
									item.stick,
									item.ironIngot
							})
						}),
						new ItemNode( new ItemStack(Material.DIAMOND_AXE, 1, (short) 0), new Node[] { // Diamond Axe
							new PerfectCompNode( new double[] {2,3}, new Node[] {
									item.stick,
									item.diamond
							})
						})
				}),
				// }
				// { Shears
				new ItemNode( new ItemStack(Material.SHEARS, 1, (short) 0), new Node[] { // Shears
					new PerfectCompNode( new double[] {2}, new Node[] {
							item.ironIngot
					})
				}),
				// }
				// { Hoe
				new PerfectSubNode( new double[] {1,2,2.3,2.3,2.4}, new Node[] { // Hoe
						new ItemNode( new ItemStack(Material.WOOD_HOE, 1, (short) 0), new Node[] { // Wooden Hoe
							new PerfectCompNode( new double[] {2,2}, new Node[] {
									item.stick,
									item.plank
							})
						}),
						new ItemNode( new ItemStack(Material.STONE_HOE, 1, (short) 0), new Node[] { // Stone Hoe
							new PerfectCompNode( new double[] {2,2}, new Node[] {
									item.stick,
									new ItemNode( new ItemStack(Material.COBBLESTONE, 1, (short) 0)) // Cobblestone
							})
						}),
						new ItemNode( new ItemStack(Material.GOLD_HOE, 1, (short) 0), new Node[] { // Gold Hoe
							new PerfectCompNode( new double[] {2,2}, new Node[] {
									item.stick,
									item.goldIngot
							})
						}),
						new ItemNode( new ItemStack(Material.IRON_HOE, 1, (short) 0), new Node[] { // Iron Hoe
							new PerfectCompNode( new double[] {2,2}, new Node[] {
									item.stick,
									item.ironIngot
							})
						}),
						new ItemNode( new ItemStack(Material.DIAMOND_HOE, 1, (short) 0), new Node[] { // Diamond Hoe
							new PerfectCompNode( new double[] {2,2}, new Node[] {
									item.stick,
									item.diamond
							})
						})
				})
				// }
				// { Redstone
				
				// }
		});

		

	}
	
}
