# Write your code here
water_amount = int(input('Write how many ml of water the coffee machine has:'))
milk_amount = int(input('Write how many ml of milk the coffee machine has:'))
coffee_amount = int(input('Write how many grams of coffee beans the coffee machine has:'))
N = int(water_amount / 200)
if N > milk_amount / 50:
    N = int(milk_amount / 50)
if N > coffee_amount / 15:
    N = int(coffee_amount / 15)
number_cups = int(input("Write how many cups of coffee you will need: "))
if number_cups == N:
    print("Yes, I can make that amount of coffee")
elif N > number_cups:
    print("Yes, I can make that amount of coffee (and even ", N-1," more than that)")
else:
    print("No, I can make only ", N," cups of coffee")
#print("""Starting to make a coffee
#Grinding coffee beans
#Boiling water
#Mixing boiled water with crushed coffee beans
#Pouring coffee into the cup
#Pouring some milk into the cup
#Coffee is ready!""")
#
#
#print("For ", number_cups, " cups of coffee you will need:")
#print(200 * number_cups, " ml of water")
#print(50 * number_cups, " ml of milk")
#print(15 * number_cups, " g of coffee beans")