# Write your code here
# print("""Starting to make a coffee
# Grinding coffee beans
# Boiling water
# Mixing boiled water with crushed coffee beans
# Pouring coffee into the cup
# Pouring some milk into the cup
# Coffee is ready!""")
print("Write how many cups of coffee you will need:")
cups = int(input())

water = cups * 200
milk = cups * 50
beans = cups * 15
print(f"for {cups} cups of coffee you will need:")
print(f"{water} ml of water")
print(f"{milk} ml of milk")
print(f"{beans} g of coffee beans")
