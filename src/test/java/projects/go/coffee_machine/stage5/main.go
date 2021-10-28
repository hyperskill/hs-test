package main

import "fmt"

func main() {
	var (
		money  = 550
		water  = 400
		milk   = 540
		beans  = 120
		cups   = 9
		choice int
	)

	printState := func() {
		fmt.Println("The coffee machine has:")
		fmt.Printf(
			"%d of water\n%d of milk\n%d of coffee beans\n%d of disposable cups\n$%d of money\n",
			water, milk, beans, cups, money,
		)
	}

	takeMoney := func() {
		fmt.Printf("I gave you $%d\n", money)
		money = 0
	}

	sellCoffee := func() {
		fmt.Print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ")
		fmt.Scanf("%d\n", &choice)

		makeCoffee := func(getMoney, needWater, needMilk, needBeans int) {
			if water < needWater {
				fmt.Println("Sorry, not enough water!")
			} else if milk < needMilk {
				fmt.Println("Sorry, not enough milk!")
			} else if beans < needBeans {
				fmt.Println("Sorry, not enough beans!")
			} else if cups < 1 {
				fmt.Println("Sorry, not enough cups!")
			} else {
				fmt.Println("I have enough resources, making you a coffee!")
				money += getMoney
				water -= needWater
				milk -= needMilk
				beans -= needBeans
				cups--
			}
		}

		switch choice {
		case 1:
			makeCoffee(4, 250, 0, 16)
		case 2:
			makeCoffee(7, 350, 75, 20)
		case 3:
			makeCoffee(6, 200, 100, 12)
		}
	}

	fillMachine := func() {
		var next int
		fmt.Print("Write how many ml of water do you want to add: ")
		fmt.Scanf("%d\n", &next)
		water += next
		fmt.Print("Write how many ml of milk do you want to add: ")
		fmt.Scanf("%d\n", &next)
		milk += next
		fmt.Print("Write how many grams of coffee beans do you want to add: ")
		fmt.Scanf("%d\n", &next)
		beans += next
		fmt.Print("Write how many disposable cups of coffee do you want to add: ")
		fmt.Scanf("%d\n", &next)
		cups += next
	}

Loop:
	for {
		var action string
		fmt.Print("\nWrite action (buy, fill, take, remaining, exit): ")
		fmt.Scanf("%s\n", &action)
		switch action {
		case "buy":
			sellCoffee()
		case "fill":
			fillMachine()
		case "take":
			takeMoney()
		case "remaining":
			printState()
		case "exit":
			break Loop
		}
	}
}
