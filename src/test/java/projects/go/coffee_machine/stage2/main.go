package main

import (
	"fmt"
)

func main() {
	var cups int
	fmt.Print("Write how many cups of coffee you will need: ")
	fmt.Scanf("%d\n", &cups)
	fmt.Printf(`For %d cups of coffee you will need:
%d ml of water
%d ml of milk
%d g of coffee beans`, cups, cups*200, cups*50, cups*15)
}
