echo Write how many cups of coffee you will need:
read -r cups
echo "For $cups cups of coffee you will need:"
echo "$((cups * 200)) ml of water"
echo "$((cups * 50)) ml of milk"
echo "$((cups * 15)) g of coffee beans"