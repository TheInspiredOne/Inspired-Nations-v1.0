Inspired-Nations-v1.0
=====================

A bukkit plugin that adds player controlled protection, governments, wars, and a closed economy built with economic models.

### What is Inspired Nations?
Inspired Nations is an economic protection protection plugin meant to be an alternative to Factions and Towny. My design goals were to make the plugin simple to use similar to Factions, but sophisticated enough for relatively complex interactions like with Towny. I wanted players to be able to build something grand and protect it with relatively high confidence that it is safe. At the same time I also wanted to give players a means to seize land from others. In addition, I felt that everything should be tied together by the economy. All to often the economy of a server feels like an afterthought. Money becomes a reward for playing the game rather than a tool, and all too often the concept of value is misunderstood by the players. It seems like even if everybody on the server is a billionaire, there is still the expectation of cheap goods. These are all problems I set out to address in Inspired Nations. The final and overarching goal of the plugin is to preserve all the aspects of vanilla minecraft while enabling players to participate on large public servers with high confidence that what they create can remain protected.

The Economy
===========

The economy of Inspired Nations implements several aspects of microeconomics and macroeconomics to recreate the consequences of capitalism. One challenge faced was that minecraft servers often don't have enough players to pressure the economy into an equilibrium. To solve this, NPCs with a purchasing AI were developed using utility functions derived from microeconomics. These NPCs increase the velocity of money in the economy, simulating a higher pressure for prices and taxes to settle to an equilibrium.

### Money Cycle
In the global economy, money is ideally neither created nor destroyed. Every cent spent is a cent earned by another individual. Inspired Nations tries to recreate this by connecting the conventional money sinks to the conventional money sources. Put another way, the taxes paid by the players are given to an account that is used to buy things from the players. This closed loop forms the basis of this plugin and makes the economy unique in its attributes. For instance, if players lower taxes but continue to sell products, then eventually they will see a decrease in consumption indicative of a wealth disparity. This is a direct result of the tax account drying up. With low taxes, there is no funds replenishing the tax account, but it will continue to consume until it cannot afford to anymore.
### Consumption Algorithm
The tax account gets supplied with funding from taxes. Once players begin to sell items, the tax account begins to purchase items. Which items it chooses and how many is a function of the underlying algorithm. The algorithm takes prices, account balance, and item availability as inputs and outputs a fund allocation distributed over all the items it decides to purchase. To illustrate this, consider an economy with two items and one buyer. The buyer has a certain amount of money that he is willing to spend on some amount of each of the items, lets refer to those items as Apples and Potatoes. Whatever the buyer does with his money, he can tell us how happy he is on a numerical scale; i.e. 3 Apples and 2 Potatoes may give the buyer a happiness level of 50. The technical term for this "happiness level" is utility. The higher the utility, the better the buyer feels about his purchase. Now if the buyer had his way, he would take infinite apples and infinite potatoes so that he could get a utility of infinity, however, real consumers live under certain limits, namely their budget. So the buyer has to decide how many apples and potatoes he can buy to maximize his utility while staying within his budget. The situation gets trickier when you consider that apples probably don't cost the same, and the buyer probably doesn't get the same amount of utility for either item.  This problem is classified as an optimization problem. The buyer wants to maximize utility while staying under budget.

Inspired Nations implements this concept on a large scale. The tax account is distributed to simulated buyers, the NPCs, each having a unique preference to items. They take their budget and look at the entire market; at the maximum, the market can contain over 500 unique items. The buyer divides up his funds and then distributes it into saving bins for each item, putting more money in the bins for the items they want more. This will happen over and over until one of the bins actually has enough money to purchase the respective item. Once this happens, the buyer will purchase as many of the item that they can and the player selling those items will see a deposit of money.

To view this explicitly, consider the following graphic:

![The AI Purchase Decision Tree](http://vegacore.net/DotTreeSub.png)

This shows the decision tree made by the AI as it figures out how much money to allot for a Leather helmet, a Chainmail helmet, and a Gold helmet. Oval nodes are decision nodes and square nodes are the goods. There are 4 kinds of decision nodes:

* CobDugg: A compromise between PerfSub and PerfComp. This generally results in a flexible ratio of quantity purchased based on how much money the NPC has. (Armor and food for instance)
* PerfSub: For the decision between two items that are substitutes, meaning one negates the need for the other (Diamond boots and Iron Boots for instance)
* PerfComp: For the decision with items that must be purchased in the correct ratio in order to get any utility (the ingredients for cake for instance)
* Item: A decision between purchasing the item directly, or purchasing its components to make it. 

Starting from the top of the tree, the NPC distributes all of it's available funds two nodes down to the first PerfSub node, where he then must decide between the three kinds of Item nodes. Because it is a PerfSub node, the NPC will chose to allocate all it's money into one of the three item nodes. This decision will be based on how much utility the NPC stands to gain from each outcome. The decision that grants the NPC the most utility is the one that wins.

Each item node has a PerfSub branch that gives the NPC the chance to make the item from its materials rather than just purchasing the item directly. If the materials are cheaper, the NPC will go for buying the materials over the fully made item. The Leather helmet and the Chainmail helmet both have fairly simple PerfSub branches, but the Gold helmet has quite a bit more complexity. This is because there are many ways to obtain the materials for the Gold helmet. Therefore each material Item node may have alternative ways to get it, creating sub trees for that item. The decision tree grows complex very quickly, gaining many decision nodes for each additional item added to the economy. As a result, the AI's make intricate and complicated decision on how to distribute their wealth in a way that pressures prices to an equilibrium.

This tree only represents 23 different items. Inspired Nations has an AI decision tree for all the unique items in Minecraft, numbering into the 500s and counting. The beauty of this nodal structure is that it is relatively easy to keep up to date. Any added items can quickly be appended to the tree where they belong resulting in quick updates to the plugin.

Some consequences of this algorithm are that the demand for one item is tightly linked with not only it's price, but also the prices of all the other items in the market. Items that are closely related such as bread, apples, potatoes and pie will have a stronger relation than unrelated items. This means that if the price of apples goes up, then the demand for bread, potatoes and pie will go up as the demand for apples falls. Unrelated items will also see an increase in demand, but the effect will likely go unnoticed. Another consequence is a result of the craftability of certain items from resources. If the price for the final product goes up, then the lost demand for that product will go into purchasing the resources to make the product.
### Currencies
Another aspect that Inspired Nations introduces is the notion of multiple currencies. By default, every country has it's own currency that can be used. Players establish the currency's name and value at the creation of their county, and from then on they can only control it through use of the market. This system introduces some complexities for the player but Inspired Nations is designed to minimize the complexity with it's defaults. By default, every player sees money amounts in the currency of their nation. Players can change what currency they operate under if they want. By default, all money that the player receives is exchanged for their operating currency. This gives the player a static picture of their account, meaning that value fluctuations of their operating currency do not cause balance changes. If players feel that they want to deal in other currencies, they can exchange any currency they have for the other currency.

Protection
==========

## Protection Levels
Every government sits on a tier inherent to it's relative status as compared to the highest form of government. In the current arrangement, countries are tier one, towns are tier two, and everything else is tier three. Every government can purchase protection, expressed as an integer number. The cost of protection levels is a function of land volume, tax level from the government tier above, and the protection level that is trying to be purchased. The larger the land volume, the more expensive a given protection level will be. This allows for small countries to maintain relatively high protection levels so that they are safe from larger, more dominating countries.

Protection levels by default give players different amounts of protection at each level. Here are the default protections at each level.

0 No Protection: anybody can break blocks, join your government, claim over your land, or hurt your citizens

1 Claim protection: No other government of the same tier can claim over your government. 

2 Immigration control: Nobody can join your government without your permission.

3 Place and Break Protection: No blocks can be interacted with, broken, or placed by non-citizens

4 Player Protection: citizens of your government are protected from damage by non-citizens

Protection is cumulative, so protection level 3 grants also protection level 2 and 1. All governments can pay for protection and the levels are the same for everybody. For governments where a certain protection level doesn't make sense (immigration control for a facility) the level still exists but buying it does not grant more protection privileges. There is no limit to how high of a level you can purchase. Above level 4, no additional protection is granted by a higher level, although you will see later why this might still be a valid option.
### Military Levels
The second set of levels available for purchase are called military levels. These levels are only available for governments that have subjects (countries, towns, and businesses). These military levels are used to breach the protection levels of any other government. The way this works is as follows.

Effective Protection = (Attacked Gov's Protection Level) - ((Attacking Gov's Military Level) - (Attacked Gov's Military Level))

So if a country has protection level 4 and military level 3, then a country with military level 5 can breach two protection levels leaving the country at protection level 2. This would effectively bypass player and block protection, allowing the attacking country to kill and rob from their enemy.

Military levels are granted for each tier of government that has subjects. Governments that do not have subjects have a military level of zero, but can still be attacked by governments at the same tier. Therefore, a business with a high enough military level can breach protection levels of a house. The only defense that a house has is its protection levels, so having levels above the basic 4 is a necessity. 
