const input = require('sync-input');

var money  = 550;
var water  = 400;
var milk   = 540;
var beans  = 120;
var cups   = 9;

function printState() {
	console.log("The coffee machine has:")
	console.log(
		water + " of water \n" +
		milk + " of milk \n" +
		beans + " of coffee beans \n" +
		cups + " of disposable cups \n" +
		money + " of money")
}

function takeMoney() {
	console.log("I gave you $" + money)
	money = 0
}

function sellCoffee() {
	let choice = input("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ");

	function makeCoffee(getMoney, needWater, needMilk, needBeans) {
		if (water < needWater) {
			console.log("Sorry, not enough water!")
		} else if (milk < needMilk) {
			console.log("Sorry, not enough milk!")
		} else if (beans < needBeans) {
			console.log("Sorry, not enough beans!")
		} else if (cups < 1) {
			console.log("Sorry, not enough cups!")
		} else {
			console.log("I have enough resources, making you a coffee!")
			money += getMoney
			water -= needWater
			milk -= needMilk
			beans -= needBeans
			cups--
		}
	}

	if (choice === '1') {
		makeCoffee(4, 250, 0, 16)
	} else if (choice === '2') {
		makeCoffee(7, 350, 75, 20)
	} else if (choice === '3') {
		makeCoffee(6, 200, 100, 12)
	}
}

function fillMachine() {
	water += Number(input("Write how many ml of water do you want to add: "))
	milk += Number(input("Write how many ml of milk do you want to add: "))
	beans += Number(input("Write how many grams of coffee beans do you want to add: "))
	cups += Number(input("Write how many disposable cups of coffee do you want to add: "))
}


while (true) {
	action = input("Write action (buy, fill, take, remaining, exit): ")

	if (action === 'buy') {
		sellCoffee()
	} else if (action === 'fill') {
		fillMachine()
	} else if (action === "take") {
		takeMoney()
	} else if (action === "remaining") {
		printState();
	} else if (action === 'exit') {
		break
	}
}
