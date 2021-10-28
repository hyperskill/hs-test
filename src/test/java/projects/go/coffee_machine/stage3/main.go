package main

import "fmt"

func main() {
	var cups, water, milk, beans, needs int

	fmt.Print("Write how many ml of water the coffee machine has: ")
	fmt.Scanf("%d\n", &water)
	cups = water / 200

	fmt.Print("Write how many ml of milk the coffee machine has: ")
	fmt.Scanf("%d\n", &milk)
	if milk/50 < cups {
		cups = milk / 50
	}

	fmt.Print("Write how many grams of coffee beans the coffee machine has: ")
	fmt.Scanf("%d\n", &beans)
	if beans/15 < cups {
		cups = beans / 15
	}

	fmt.Print("Write how many cups of coffee you will need: ")
	fmt.Scanf("%d\n", &needs)
	if needs == cups {
		fmt.Println("Yes, I can make that amount of coffee")
	} else if needs < cups {
		fmt.Printf(
			"Yes, I can make that amount of coffee (and even %d more than that)\n",
			cups-needs,
		)
	} else {
		fmt.Printf("No, I can make only %d cup(s) of coffee\n", cups)
	}
}
