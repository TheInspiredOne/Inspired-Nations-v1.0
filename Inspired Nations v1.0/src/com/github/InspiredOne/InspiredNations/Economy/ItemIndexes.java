package com.github.InspiredOne.InspiredNations.Economy;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

import com.github.InspiredOne.InspiredNations.InspiredNations;

public class ItemIndexes {

	InspiredNations plugin;
	
	public HashMap<ItemStack, Integer> index = new HashMap<ItemStack, Integer>();
	
	public ItemIndexes(InspiredNations instance) {
		plugin = instance;
		
		
		
		this.put(1, 0, "Stone");
		this.put(2, 0, "Grass Block");
		this.put(3, 0, "Dirt");
		this.put(4, 0, "Cobblestone");
		this.put(5, 0, "Wooden Plank(Oak)");
		this.put(5, 1, "Wooden Plank(Spruce)");
		this.put(5, 2, "Wooden Plank(Birch)");
		this.put(5, 3, "Wooden Plank(Jungle)");
		this.put(6, 0, "Sapling(Oak)");
		this.put(6, 1, "Sapling(Spruce)");
		this.put(6, 2, "Sapling(Birch)");
		this.put(6, 3, "Sapling(Jungle)");
		this.put(7, 0, "Bedrock");
		this.put(8, 0, "Water");
		this.put(9, 0, "Water (No Spread)");
		this.put(10, 0, "Lava");
		this.put(11, 0, "Lava (No Spread)");
		this.put(12, 0, "Sand");
		this.put(13, 0, "Gravel");
		this.put(14, 0, "Gold Ore");
		this.put(15, 0, "Iron Ore");
		this.put(16, 0, "Coal Ore");
		this.put(17, 0, "Wood (Oak)");
		this.put(17, 1, "Wood (Spruce)");
		this.put(17, 2, "Wood (Birch)");
		this.put(17, 3, "Wood (Jungle)");
		this.put(18, 0, "Leaves (Oak)");
		this.put(18, 1, "Leaves (Spruce)");
		this.put(18, 2, "Leaves (Birch)");
		this.put(18, 3, "Leaves (Jungle)");
		this.put(19, 0, "Sponge");
		this.put(20, 0, "Glass");
		this.put(21, 0, "Lapis Lazuli Ore");
		this.put(22, 0, "Lapis Lazuli Block");
		this.put(23, 0, "Dispenser");
		this.put(24, 0, "Sandstone");
		this.put(24, 1, "Sandstone (Chiseled)");
		this.put(24, 2, "Sandstone (Smooth)");
		this.put(25, 0, "Note Block");
		this.put(26, 0, "Bed (Block)");
		this.put(27, 0, "Rail (Powered)");
		this.put(28, 0, "Rail (Detector)");
		this.put(29, 0, "Sticky Piston");
		this.put(30, 0, "Cobweb");
		this.put(31, 0, "Tall Grass (Dead Shrub)");
		this.put(31, 1, "Tall Grass");
		this.put(31, 2, "Tall Grass (Fern)");
		this.put(32, 0, "Dead Shrub");
		this.put(33, 0, "Piston");
		this.put(34, 0, "Piston (Head)");
		this.put(35, 0, "Wool");
		this.put(35, 1, "Orange Wool");
		this.put(35, 2, "Magenta Wool");
		this.put(35, 3, "Light Blue Wool");
		this.put(35, 4, "Yellow Wool");
		this.put(35, 5, "Lime Wool");
		this.put(35, 6, "Pink Wool");
		this.put(35, 7, "Gray Wool");
		this.put(35, 8, "Light Gray Wool");
		this.put(35, 9, "Cyan Wool");
		this.put(35, 10, "Purple Wool");
		this.put(35, 11, "Blue Wool");
		this.put(35, 12, "Brown Wool");
		this.put(35, 13, "Green Wool");
		this.put(35, 14, "Red Wool");
		this.put(35, 15, "Black Wool");
		this.put(36, 0, "Piston (Moving)");
		this.put(37, 0, "Dandelion");
		this.put(38, 0, "Rose");
		this.put(39, 0, "Brown Mushroom");
		this.put(40, 0, "Red Mushroom");
		this.put(41, 0, "Block Of Gold");
		this.put(42, 0, "Block Of Iron");
		this.put(43, 0, "Stone Slab (Double)");
		this.put(43, 1, "Sandstone Slab (Double)");
		this.put(43, 2, "Wooden Slab (Double)");
		this.put(43, 3, "Cobblestone Slab (Double)");
		this.put(43, 4, "Brick Slab (Double)");
		this.put(43, 5, "Stone Brick Slab (Double)");
		this.put(43, 6, "Nether Brick Slab (Double)");
		this.put(43, 7, "Quartz Slab (Double)");
		this.put(43, 8, "Smooth Stone Slab (Double)");
		this.put(43, 9, "Smooth Sandstone Slab (Double)");
		this.put(44, 0, "Stone Slab");
		this.put(44, 1, "Sandstone Slab");
		this.put(44, 2, "Wooden Slab");
		this.put(44, 3, "Cobblestone Slab");
		this.put(44, 4, "Brick Slab");
		this.put(44, 4, "Stone Brick Slab");
		this.put(44, 6, "Nether Brick Slab");
		this.put(44, 7, "Quartz Slab");
		this.put(45, 0, "Brick");
		this.put(46, 0, "TNT");
		this.put(47, 0, "Bookcase");
		this.put(48, 0, "Moss Stone");
		this.put(49, 0, "Obsidian");
		this.put(50, 0, "Torch");
		this.put(51, 0, "Fire");
		this.put(52, 0, "Mob Spawner");
		this.put(53, 0, "Wooden Stairs (Oak)");
		this.put(54, 0, "Chest");
		this.put(55, 0, "Redstone Wire");
		this.put(56, 0, "Diamond Ore");
		this.put(57, 0, "Blcok Of Diamond");
		this.put(58, 0, "Workbench");
		this.put(59, 0, "Wheat (Crop)");
		this.put(60, 0, "Farmland");
		this.put(61, 0, "Furnace");
		this.put(62, 0, "Furnace (Smelting)");
		this.put(63, 0, "Sign (Block)");
		this.put(64, 0, "Wood Door (Block)");
		this.put(65, 0, "Ladder");
		this.put(66, 0, "Rail");
		this.put(67, 0, "CobbleStone Stairs");
		this.put(68, 0, "Sign (Wall Block)");
		this.put(69, 0, "Lever");
		this.put(70, 0, "Stone Pressure Plate");
		this.put(71, 0, "Iron Door (Block)");
		this.put(72, 0, "Wooden Pressure Plate");
		this.put(73, 0, "Redstone Ore");
		this.put(74, 0, "Redstone Ore (Glowing)");
		this.put(75, 0, "Redstone Torch (Off)");
		this.put(76, 0, "Redstone Torch");
		this.put(77, 0, "Button (Stone)");
		this.put(78, 0, "Snow");
		this.put(79, 0, "Ice");
		this.put(80, 0, "Snow Block");
		this.put(81, 0, "Cactus");
		this.put(82, 0, "Clay Block");
		this.put(83, 0, "Sugar Cane (Block)");
		this.put(84, 0, "Jukebox");
		this.put(85, 0, "Fence");
		this.put(86, 0, "Pumpkin");
		this.put(87, 0, "Netherrack");
		this.put(88, 0, "Soul Sand");
		this.put(89, 0, "GlowStone");
		this.put(90, 0, "Portal");
		this.put(91, 0, "Jack-O-Lantern");
		this.put(92, 0, "Cake");
		this.put(93, 0, "Redstone Repeater (Block Off)");
		this.put(94, 0, "Redstone Repeater (Block On)");
		this.put(95, 0, "Locked Chest");
		this.put(96, 0, "TrapDoor");
		this.put(97, 0, "Silverfish Stone");
		this.put(97, 1, "Silverfish Cobblestone");
		this.put(97, 2, "Silverfish Stone Brick");
		this.put(98, 0, "Stone Brick");
		this.put(98, 1, "Mossy Stone Brick");
		this.put(98, 2, "Cracked Stone Brick");
		this.put(98, 3, "Chiseled Stone Brick");
		this.put(99, 0, "Brown Mushroom (Block)");
		this.put(100, 0, "Red Mushroom (Block)");
		this.put(101, 0, "Iron Bars");
		this.put(102, 0, "Glass Pane");
		this.put(103, 0, "Melon (Block)");
		this.put(104, 0, "Pumpkin Vine");
		this.put(105, 0, "Melon Vine");
		this.put(106, 0, "Vines");
		this.put(107, 0, "Fence Gate");
		this.put(108, 0, "Brick Stairs");
		this.put(109, 0, "Stone Brick Stairs");
		this.put(110, 0, "Mycelium");
		this.put(111, 0, "Lily Pad");
		this.put(112, 0, "Nether Brick");
		this.put(113, 0, "Nether Brick Fence");
		this.put(114, 0, "Nether Brick Stairs");
		this.put(115, 0, "Nether Wart");
		this.put(116, 0, "Enchantment Table");
		this.put(117, 0, "Brewing Stand (Block)");
		this.put(118, 0, "Cauldron (Block)");
		this.put(119, 0, "End Portal");
		this.put(120, 0, "End Portal Frame");
		this.put(121, 0, "End Stone");
		this.put(122, 0, "Dragon Egg");
		this.put(123, 0, "Redstone Lamp");
		this.put(124, 0, "Redstone Lamp (On)");
		this.put(125, 0, "Oak Slab (Double)");
		this.put(125, 1, "Spruce Slab (Double)");
		this.put(125, 2, "Birch Slab (Double)");
		this.put(125, 3, "Jungle Slab (Double)");
		this.put(126, 0, "Oak Slab");
		this.put(126, 1, "Spruce Slab");
		this.put(126, 2, "Birch Slab");
		this.put(126, 3, "Jungle Slab");
		this.put(127, 0, "Coca Plant");
		this.put(128, 0, "Sandstone Stairs");
		this.put(129, 0, "Emerald Ore");
		this.put(130, 0, "Ender Chest");
		this.put(131, 0, "Tripwire Hook");
		this.put(132, 0, "Trip Wire");
		this.put(133, 0, "Block Of Emerald");
		this.put(134, 0, "Spruce Stairs");
		this.put(135, 0, "Birch Stairs");
		this.put(136, 0, "Jungle Stairs");
		this.put(137, 0, "Command Block");
		this.put(138, 0, "Beacon");
		this.put(139, 0, "Cobblestone Wall");
		this.put(139, 1, "Mossy Cobble Wall");
		this.put(140, 0, "Flower Pot (Block)");
		this.put(141, 0, "Carrot (Crop)");
		this.put(142, 0, "Potatoes (Crop)");
		this.put(143, 0, "Wood Button");
		this.put(144, 0, "Skeleton Head");
		this.put(144, 1, "Wither Head");
		this.put(144, 2, "Zombie Head");
		this.put(144, 3, "Head");
		this.put(144, 4, "Creeper Head");
		this.put(145, 0, "Anvil");
		this.put(145, 1, "Damaged Anvil");
		this.put(145, 2, "Very Damaged Anvil");
		this.put(146, 0, "Trapped Chest");
		this.put(147, 0, "Gold Pressure Plate");
		this.put(148, 0, "Iron Pressure Plate");
		this.put(149, 0, "Redstone Comparator (Off)");
		this.put(150, 0, "Redstone Comparator (On)");
		this.put(151, 0, "Daylight Sensor");
		this.put(152, 0, "Redstone Block");
		this.put(153, 0, "Nether Quartz Ore");
		this.put(154, 0, "Hopper");
		this.put(155, 0, "Quartze Block");
		this.put(155, 1, "Chiseled Quartz");
		this.put(155, 2, "Pillar Quartz");
		this.put(156, 0, "Quartz Stairs");
		this.put(157, 0, "Activator Rail");
		this.put(158, 0, "Dropper");
		this.put(159, 0, "White Clay");
		this.put(159, 1, "Orange Clay");
		this.put(159, 2, "Magenta Clay");
		this.put(159, 3, "Light Blue Clay");
		this.put(159, 4, "Yellow Clay");
		this.put(159, 5, "Lime Clay");
		this.put(159, 6, "Pink Clay");
		this.put(159, 7, "Gray Clay");
		this.put(159, 8, "Light Gray Clay");
		this.put(159, 9, "Cyan Clay");
		this.put(159, 10, "Purple Clay");
		this.put(159, 11, "Blue Clay");
		this.put(159, 12, "Brown Clay");
		this.put(159, 13, "Green Clay");
		this.put(159, 14, "Red Clay");
		this.put(159, 15, "Black Clay");
		this.put(170, 0, "Hay Bale");
		this.put(171, 0, "White Carpet");
		this.put(171, 1, "Orange Carpet");
		this.put(171, 2, "Magenta Carpet");
		this.put(171, 3, "Light Blue Carpet");
		this.put(171, 4, "Yellow Carpet");
		this.put(171, 5, "Lime Carpet");
		this.put(171, 6, "Pink Carpet");
		this.put(171, 7, "Gray Carpet");
		this.put(171, 8, "Light Gray Carpet");
		this.put(171, 9, "Cyan Carpet");
		this.put(171, 10, "Purple Carpet");
		this.put(171, 11, "Blue Carpet");
		this.put(171, 12, "Brown Carpet");
		this.put(171, 13, "Green Carpet");
		this.put(171, 14, "Red Carpet");
		this.put(171, 15, "Black Carpet");
		this.put(172, 0, "Hardened Clay");
		this.put(173, 0, "Coal Block");
		this.put(256, 0, "Iron Shovel");
		this.put(257, 0, "Iron Pickaxe");
		this.put(258, 0, "Iron Axe");
		this.put(259, 0, "Flint And Steel");
		this.put(260, 0, "Apple");
		this.put(261, 0, "Bow");
		this.put(262, 0, "Arrow");
		this.put(263, 0, "Coal");
		this.put(263, 1, "Charcoal");
		this.put(264, 0, "Diamond");
		this.put(265, 0, "Iron Ingot");
		this.put(266, 0, "Gold Ingot");
		this.put(267, 0, "Iron Sword");
		this.put(268, 0, "Wooden Sword");
		this.put(269, 0, "Wooden Shovel");
		this.put(270, 0, "Wooden Pickaxe");
		this.put(271, 0, "Wooden Axe");
		this.put(272, 0, "Stone Sword");
		this.put(273, 0, "Stone Shovel");
		this.put(274, 0, "Stone Pickaxe");
		this.put(275, 0, "Stone Axe");
		this.put(276, 0, "Diamond Sword");
		this.put(277, 0, "Diamond Shovel");
		this.put(278, 0, "Diamond Pickaxe");
		this.put(279, 0, "Diamond Axe");
		this.put(280, 0, "Stick");
		this.put(281, 0, "Bowl");
		this.put(282, 0, "Mushroom Stew");
		this.put(283, 0, "Gold Sword");
		this.put(284, 0, "Gold Shovel");
		this.put(285, 0, "Gold Pickaxe");
		this.put(286, 0, "Gold Axe");
		this.put(287, 0, "String");
		this.put(288, 0, "Feather");
		this.put(289, 0, "Gunpowder");
		this.put(290, 0, "Wooden Hoe");
		this.put(291, 0, "Stone Hoe");
		this.put(292, 0, "Iron Hoe");
		this.put(293, 0, "Diamond Hoe");
		this.put(294, 0, "Gold Hoe");
		this.put(295, 0, "Wheat Seeds");
		this.put(296, 0, "Wheat");
		this.put(297, 0, "Bread");
		this.put(298, 0, "Leather Helmet");
		this.put(299, 0, "Leather Chestplate");
		this.put(300, 0, "Leather Leggings");
		this.put(301, 0, "Leather Boots");
		this.put(302, 0, "Chain Helmet");
		this.put(303, 0, "Chain Chestplate");
		this.put(304, 0, "Chain Leggings");
		this.put(305, 0, "Chain Boots");
		this.put(306, 0, "Iron Helmet");
		this.put(307, 0, "Iron Chestplate");
		this.put(308, 0, "Iron Leggings");
		this.put(309, 0, "Iron Boots");
		this.put(310, 0, "Diamond Helmet");
		this.put(311, 0, "Diamond Chestplate");
		this.put(312, 0, "Diamond Leggings");
		this.put(313, 0, "Diamond Boots");
		this.put(314, 0, "Gold Helmet");
		this.put(315, 0, "Gold Chestplate");
		this.put(316, 0, "Gold Leggings");
		this.put(317, 0, "Gold Boots");
		this.put(318, 0, "Flint");
		this.put(319, 0, "Raw Porkchop");
		this.put(320, 0, "Cooked Porkchop");
		this.put(321, 0, "Painting");
		this.put(322, 0, "Golden Apple");
		this.put(322, 1, "Enchanted Golden Apple");
		this.put(323, 0, "Sign");
		this.put(324, 0, "Wooden Door");
		this.put(325, 0, "Bucket");
		this.put(326, 0, "Water Bucket");
		this.put(327, 0, "Lava Bucket");
		this.put(328, 0, "Minecart");
		this.put(329, 0, "Saddle");
		this.put(330, 0, "Iron Door");
		this.put(331, 0, "Redstone Dust");
		this.put(332, 0, "Snowball");
		this.put(333, 0, "Boat");
		this.put(334, 0, "Leather");
		this.put(335, 0, "Milk Bucket");
		this.put(336, 0, "Clay Brick");
		this.put(337, 0, "Clay");
		this.put(338, 0, "SugarCane");
		this.put(339, 0, "Paper");
		this.put(340, 0, "Book");
		this.put(341, 0, "Slime Ball");
		this.put(342, 0, "Storage Minecart");
		this.put(343, 0, "Powered Minecart");
		this.put(344, 0, "Egg");
		this.put(345, 0, "Compass");
		this.put(346, 0, "Fishing Rod");
		this.put(347, 0, "Watch");
		this.put(348, 0, "Glowstone Dust");
		this.put(349, 0, "Raw Fish");
		this.put(350, 0, "Cooked Fish");
		this.put(351, 0, "Ink Sack");
		this.put(351, 1, "Red Dye");
		this.put(351, 2, "Cactus Green Dye");
		this.put(351, 3, "Coca Bean");
		this.put(351, 4, "Lapis Lazuli");
		this.put(351, 5, "Purple Dye");
		this.put(351, 6, "Cyan Dye");
		this.put(351, 7, "Light Gray Dye");
		this.put(351, 8, "Gray Dye");
		this.put(351, 9, "Pink Dye");
		this.put(351, 10, "Lime Dye");
		this.put(351, 11, "Yellow Dye");
		this.put(351, 12, "Light Blue Dye");
		this.put(351, 13, "Magenta Dye");
		this.put(351, 14, "Orange Dye");
		this.put(351, 15, "Bone Meal");
		this.put(352, 0, "Bone");
		this.put(353, 0, "Sugar");
		this.put(254, 0, "Cake");
		this.put(355, 0, "Bed");
		this.put(356, 0, "Redstone Repeater");
		this.put(357, 0, "Cookie");
		this.put(358, 0, "Map");
		this.put(359, 0, "Shears");
		this.put(360, 0, "Melon Slice");
		this.put(361, 0, "Pumpkin Seeds");
		this.put(362, 0, "Melon Seeds");
		this.put(363, 0, "Raw Beef");
		this.put(364, 0, "Steak");
		this.put(365, 0, "Raw Chicken");
		this.put(366, 0, "Cooked Chicken");
		this.put(367, 0, "Rotten Flesh");
		this.put(368, 0, "Ender Pearl");
		this.put(369, 0, "Blaze Rod");
		this.put(370, 0, "Ghast Tear");
		this.put(371, 0, "Gold Nugget");
		this.put(372, 0, "Nether Wart");
		this.put(373, 0, "Water Bottle");
		this.put(373, 16, "Awkward Potion");
		this.put(373, 32, "Thick Potion");
		this.put(373, 64, "Mundane Potion");
		this.put(373, 8193, "Regeneration Potion");
		this.put(373, 8194, "Swiftness Potion");
		this.put(373, 8195, "Fire Resistance Potion");
		this.put(373, 8196, "Poison Potion");
		this.put(373, 8197, "Healing Potion");
		this.put(373, 8198, "Night Vision Potion");
		this.put(373, 8200, "Weakness Potion");
		this.put(373, 8201, "Strength Potion");
		this.put(373, 8202, "Slowness Potion");
		this.put(373, 8204, "Harming Potion");
		this.put(373, 8206, "Invisibility Potion");
		this.put(373, 8225, "Regeneration II Potion");
		this.put(373, 8226, "Swiftness II Potion");
		this.put(373, 8228, "Poison II Potion");
		this.put(373, 8229, "Healing II Potion");
		this.put(373, 8233, "Strength II Potion");
		this.put(373, 8236, "Harming II Potion");
		this.put(373, 8257, "Regeneration Extended Potion");
		this.put(373, 8258, "Swifness Extended Potion");
		this.put(373, 8259, "Fire Resistance Extended Potion");
		this.put(373, 8260, "Poison Extended Potion");
		this.put(373, 8262, "Night Vision Extended Potion");
		this.put(373, 8264, "Weakness Extended Potion");
		this.put(373, 8265, "Strenght Extended Potion");
		this.put(373, 8266, "Slowness Extended Potion");
		this.put(373, 8270, "Invisibility Extended Potion");
		this.put(373, 8289, "Regeneration II Extended Potion");
		this.put(373, 8290, "Swiftness II Extended Potion");
		this.put(373, 8292, "Poison II Extended Potion");
		this.put(373, 8297, "Strength II Extended Potion");
		this.put(373, 16385, "Regeneration Splash Potion");
		this.put(373, 16386, "Swiftness Splash Potion");
		this.put(373, 16387, "Fire Resistance Splash Potion");
		this.put(373, 16388, "Poison Splash Potion");
		this.put(373, 16389, "Healing Splash Potion");
		this.put(373, 16390, "Night Vision Splash Potion");
		this.put(373, 16392, "Weakness Splash Potion");
		this.put(373, 16393, "Strength Splash Potion");
		this.put(373, 16394, "Slowness Splash Potion");
		this.put(373, 16396, "Harming Splash Potion");
		this.put(373, 16398, "Invisibility Splash Potion");
		this.put(373, 16417, "Regeneration II Splash Potion");
		this.put(373, 16418, "Swiftness II Splash Potion");
		this.put(373, 16420, "Poison II Splash Potion");
		this.put(373, 16421, "Healing II Splash Potion");
		this.put(373, 16425, "Strength II Splash Potion");
		this.put(373, 16428, "Harming II Splash Potion");
		this.put(373, 16449, "Regeneration Extended Splash Potion");
		this.put(373, 16450, "Swiftness Extended Splash Potion");
		this.put(373, 16451, "Fire Resistance Extended Splash Potion");
		this.put(373, 16452, "Poison Extended Splash Potion");
		this.put(373, 16454, "Night Vision Extended Splash Potion");
		this.put(373, 16456, "Weakness Extended Splash Potion");
		this.put(373, 16457, "Strength Extended Splash Potion");
		this.put(373, 16458, "Slowness Extended Splash Potion");
		this.put(373, 16562, "Invisibility Extended Splash Potion");
		this.put(373, 16481, "Regeneration II Extended Splash Potion");
		this.put(373, 16482, "Swiftness II Extended Splash Potion");
		this.put(373, 16484, "Poison II Extended Splash Potion");
		this.put(373, 16489, "Strength II Extended Splash Potion");
		this.put(374, 0, "Glass Bottle");
		this.put(375, 0, "Spider Eye");
		this.put(376, 0, "Fermented Spider Eye");
		this.put(377, 0, "Blaze Powder");
		this.put(378, 0, "Magma Cream");
		this.put(379, 0, "Brewing Stand");
		this.put(380, 0, "Cauldron");
		this.put(381, 0, "Eye Of Ender");
		this.put(382, 0, "Glistering Melon Slice");
		this.put(383, 50, "Creeper Egg");
		this.put(383, 51, "Skeleton Egg");
		this.put(383, 52, "Spider Egg");
		this.put(383, 54, "Zombie Egg");
		this.put(383, 55, "Slime Egg");
		this.put(383, 56, "Ghast Egg");
		this.put(383, 57, "Zombie Pigman Egg");
		this.put(383, 58, "Enderman Egg");
		this.put(383, 59, "Cave Spider Egg");
		this.put(383, 60, "Silverfish Egg");
		this.put(383, 61, "Blaze Egg");
		this.put(383, 62, "Magma Cube Egg");
		this.put(383, 65, "Bat Egg");
		this.put(383, 66, "Witch Egg");
		this.put(383, 90, "Pig Egg");
		this.put(383, 91, "Sheep Egg");
		this.put(383, 92, "Cow Egg");
		this.put(383, 93, "Chicken Spawn Egg");
		this.put(383, 94, "Squid Egg");
		this.put(383, 95, "Wolf Egg");
		this.put(383, 96, "Mooshroom Egg");
		this.put(383, 98, "Ocelot Egg");
		this.put(383, 100, "Horse Egg");
		this.put(383, 120, "Villager Egg");
		this.put(384, 0, "Bottle Of Enchanting");
		this.put(385, 0, "Fire Charge");
		this.put(386, 0, "Book And Quil");
		this.put(387, 0, "Written Book");
		this.put(388, 0, "Emerald");
		this.put(389, 0, "Item Frame");
		this.put(390, 0, "Flower Pot");
		this.put(391, 0, "Carrot");
		this.put(392, 0, "Potato");
		this.put(393, 0, "Baked Potato");
		this.put(394, 0, "Poisonous Potato");
		this.put(395, 0, "Empty Map");
		this.put(396, 0, "Golden Carrot");
		this.put(397, 0, "Skeleton Head");
		this.put(397, 1, "Wither Head");
		this.put(397, 2, "Zombie Head");
		this.put(397, 3, "Head");
		this.put(397, 4, "Creeper Head");
		this.put(398, 0, "Carrot On A Stick");
		this.put(399, 0, "Nether Star");
		this.put(400, 0, "Pumpkin Pie");
		this.put(401, 0, "Firework Rocket");
		this.put(402, 0, "Firework Star");
		this.put(403, 0, "Enchanted Book");
		this.put(404, 0, "Redstone Comparator");
		this.put(405, 0, "Nether Brick Item");
		this.put(406, 0, "Nether Quartz");
		this.put(407, 0, "TNT Minecart");
		this.put(408, 0, "Hopper Minecart");
		this.put(417, 0, "Iron Horse Armor");
		this.put(418, 0, "Gold Horse Armor");
		this.put(419, 0, "Diamond Horse Armor");
		this.put(420, 0, "Lead");
		this.put(421, 0, "Name Tag");
		this.put(2256, 0, "Music Disk 13");
		this.put(2257, 0, "Music Disk Cat");
		this.put(2258, 0, "Music Disk Blocks");
		this.put(2259, 0, "Music Disk Chirp");
		this.put(2260, 0, "Music Disk Far");
		this.put(2261, 0, "Music Disk Mall");
		this.put(2262, 0, "Music Disk Mellohi");
		this.put(2263, 0, "Music Disk Stal");
		this.put(2264, 0, "Music Disk Strad");
		this.put(2265, 0, "Music Disk Ward");
		this.put(2266, 0, "Music Disk 11");
		this.put(2267, 0, "Music Disk Wait");
		
	}
	
	public boolean contains(ItemStack item) {
		return index.containsKey(item);
	}
	
	public void put(ItemStack item) {
		index.put(item, index.size());
	}
	
	public void put(int type, int dura, String name) {
		index.put(new ItemStack(type,1,(short) dura), index.size());
	}
	
	public int get(ItemStack item) {
		return index.get(item);
	}
	
}
