package machine;

public class MachineCommand {
    private MachineState state;
    private MachineState refill;
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;

    MachineCommand(int water, int milk, int beans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.money = money;

        initiate();
    }

    public boolean Online() {
        return state != MachineState.OFF;
    }

    public void initiate() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        state = MachineState.STANDBY;
    }

    public void menu(String input) {
        switch (state) {
            case STANDBY -> {
                setState(input);
            }
            case BUY -> {
                buy(input);
            }
            case REFILLING -> {
                if (NumberChecker(input)) {
                    fill(input);
                }
                switch (refill) {
                    case REFILLING_MILK -> System.out.println("Write how many ml of milk you want to add:");
                    case REFILLING_COFFEE ->
                            System.out.println("Write how many grams of coffee beans you want to add:");
                    case REFILLING_CUPS -> System.out.println("Write how many disposable cups you want to add:");
                    default -> {
                    }
                }
            }
        }
    }

    public void setState(String input) {
        switch (input) {
            case "buy" -> {
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                state = MachineState.BUY;
            }
            case "fill" -> {
                state = MachineState.REFILLING;
                refill = MachineState.REFILLING_WATER;
                System.out.println("Write how many ml of water you want to add:");
            }
            case "take" -> take();
            case "remaining" -> storageStatus();
            case "exit" -> state = MachineState.OFF;
            default -> System.out.println("Unrecognized Input!");
        }
    }

    public boolean numberChecker(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException nfe) {
            System.out.println("Your input doesn't contain only digits!");
            return false;
        }
    }

    public void storageStatus() {
        System.out.printf("The coffee machine has:%n" +
                "%d ml of water%n" +
                "%d ml of milk%n" +
                "%d g of coffee beans%n" +
                "%d disposable cups%n" +
                "$%d of money%n", water, milk, beans, cups, money);
        initiate();
    }

    public void buy(String code) {
        Beverage coffeeType;
        switch (code) {
            case "1" -> coffeeType = Beverage.ESPRESSO;
            case "2" -> coffeeType = Beverage.LATTE;
            case "3" -> coffeeType = Beverage.CAPPUCCINO;
            case "back" -> {
                initiate();
                return;
            }
            default -> {
                System.out.println("Unrecognized Input!");
                return;
            }
        }
        int waterRemain = water - coffeeType.getWater();
        int milkRemain = milk - coffeeType.getMilk();
        int beansRemain = beans - coffeeType.getCoffee();
        int cupsRemain = cups - 1;
        if (waterRemain < 0) {
            System.out.println("Sorry, not enough water!");
        } else if (milkRemain < 0) {
            System.out.println("Sorry, not enough milk!");
        } else if (beansRemain < 0) {
            System.out.println("Sorry, not enough coffee beans!");
        } else if (cupsRemain < 0) {
            System.out.println("Sorry, not enough disposable cups!");
        } else {
            System.out.printf("I have enough resources, making you a coffee!%n%n");
            water = waterRemain;
            milk = milkRemain;
            beans = beansRemain;
            cups = cupsRemain;
            money += coffeeType.getPrice();
        }
        initiate();
    }

    public void fill(String input) {
        switch (refill) {
            case REFILLING_WATER -> {
                water += Integer.parseInt(input);
                refill = MachineState.REFILLING_MILK;
            }
            case REFILLING_MILK -> {
                milk += Integer.parseInt(input);
                refill = MachineState.REFILLING_COFFEE;
            }
            case REFILLING_COFFEE -> {
                beans += Integer.parseInt(input);
                refill = MachineState.REFILLING_CUPS;
            }
            case REFILLING_CUPS -> {
                cups += Integer.parseInt(input);
                refill = MachineState.STANDBY;
                initiate();
            }
        }
    }

    public void take() {
        if (money == 0) {
            System.out.println("No Money Left");
        } else {
            System.out.printf("I gave you $%d%n", money);
            money -= money;
        }
        initiate();
    }
}
