# ACTIONS
BUY = "buy"
FILL = "fill"
TAKE = "take"
REMAINING = "remaining"
EXIT_ = "exit"

# COFFEE PRICES
# 0 - water, 1 - milk, 2 - beans, 4 - cus, 5 - cost
ESPRESSO_NEEDS = [250, 0, 16, 1, 4]
LATTE_NEEDS = [350, 75, 20, 1, 7]
CAPPUCCINO_NEEDS = [200, 100, 12, 1, 6]
# INITIAL MACHINE STATE
machine_state = [400, 540, 120, 9, 550]
machine_desc = ["water", "milk", "coffee beans", "disposable cups", "money"]


def print_machine_state():
    print("The coffee machine has:")
    for i in range(len(machine_state)):
        print(machine_state[i], "of " + machine_desc[i])


def action_handle(user_action_def):
    if user_action_def == BUY:
        return buy_coffee()
    elif user_action_def == FILL:
        return fill_machine(machine_state)
    elif user_action_def == TAKE:
        return take_money(machine_state)
    elif user_action_def == REMAINING:
        return print_machine_state()


def buy_coffee():
    print("What do you want to buy? "
          + "1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
    user_choice = input()
    if user_choice == "back":
        return False
    else:
        user_choice = int(user_choice)

    if not check_resources(user_choice):
        return False
    if user_choice == 1:
        change_machine_state(machine_state, ESPRESSO_NEEDS)
    elif user_choice == 2:
        change_machine_state(machine_state, LATTE_NEEDS)
    elif user_choice == 3:
        change_machine_state(machine_state, CAPPUCCINO_NEEDS)


def check_resources(user_choice):
    if user_choice == 1:
        for i in range(len(machine_state)):
            if machine_state[i] < ESPRESSO_NEEDS[i]:
                print("Sorry, not enough " + machine_desc[i] + "!")
                return False
    elif user_choice == 2:
        for i in range(len(machine_state)):
            if machine_state[i] < LATTE_NEEDS[i]:
                print("Sorry, not enough " + machine_desc[i] + "!")
                return False
    elif user_choice == 3:
        for i in range(len(machine_state)):
            if machine_state[i] < CAPPUCCINO_NEEDS[i]:
                print("Sorry, not enough " + machine_desc[i] + "!")
                return False
    else:
        print("Unknown case!")
        return False

    print("I have enough resources, making you a coffee!")
    return True


def change_machine_state(machine, coffee_cost):
    for i in range(len(machine)-1):
        machine[i] -= coffee_cost[i]
        # print(machine[i])
    # add money
    machine[len(machine)-1] += coffee_cost[len(machine)-1]


def fill_machine(machine):
    print("Write how many ml of water do you want to add:")
    fill_water = int(input())
    print("Write how many ml of milk do you want to add:")
    fill_milk = int(input())
    print("Write how many grams of coffee beans do you want to add:")
    fill_beans = int(input())
    print("Write how many disposable cups of coffee do you want to add:")
    fill_cups = int(input())

    machine[0] += fill_water
    machine[1] += fill_milk
    machine[2] += fill_beans
    machine[3] += fill_cups


def take_money(machine):
    print("I gave you $" + str(machine[4]))
    machine[4] = 0


while True:
    print()
    print("Write action (buy, fill, take, remaining, exit):")
    user_action = input()
    if user_action == EXIT_:
        break
    action_handle(user_action)
