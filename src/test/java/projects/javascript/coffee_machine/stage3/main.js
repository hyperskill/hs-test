const input = require('sync-input');

var cups, water, milk, beans, needs;

water = Number(input('Write how many ml of water the coffee machine has: '))
cups = water / 200 | 0;

milk = Number(input("Write how many ml of milk the coffee machine has: "))
if ((milk/50|0) < cups) {
	cups = milk / 50 | 0;
}

beans = Number(input("Write how many grams of coffee beans the coffee machine has: "));
if ((beans/15|0) < cups) {
	cups = beans / 15 | 0;
}

needs = Number(input("Write how many cups of coffee you will need: "));
if (needs === cups) {
	console.log("Yes, I can make that amount of coffee")
} else if (needs < cups) {
	console.log("Yes, I can make that amount of coffee (and even " + (cups-needs) + " more than that)")
} else {
	console.log("No, I can make only " + cups + " cup(s) of coffee")
}
