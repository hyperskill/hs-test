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
		switch choice {
		case 1:
			money += 4
			water -= 250
			beans -= 16
			cups -= 1
		case 2:
			money += 7
			water -= 350
			milk -= 75
			beans -= 20
			cups -= 1
		case 3:
			money += 6
			water -= 200
			milk -= 100
			beans -= 12
			cups -= 1
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

	printState()

	var action string
	fmt.Print("\nWrite action (buy, fill, take): ")
	fmt.Scanf("%s\n", &action)
	switch action {
	case "buy":
		sellCoffee()
	case "fill":
		fillMachine()
	case "take":
		takeMoney()
	}

	fmt.Println()
	printState()
}
