import java.util.List;

public class Cooking {
    private Inventory inventory;
    private int fuel;
    private int energy;

    public Cooking(Inventory inventory, int fuel, int energy) {
        this.inventory = inventory;
        this.fuel = fuel;
        this.energy = energy;
    }

    public boolean cook(Recipe recipe) {
        if (energy < 10) {
            System.out.println("Not enough energy to cook.");
            return false;
        }

        if (fuel < 1) {
            System.out.println("Not enough fuel to cook.");
            return false;
        }

        if (!recipe.canCook(inventory)) {
            System.out.println("Missing ingredients to cook " + recipe.getName());
            return false;
        }

        // Deduct ingredients
        for (String ingredient : recipe.getIngredients()) {
            inventory.removeItem(ingredient);
        }

        // Consume fuel and energy
        fuel -= 1;
        energy -= 10;

        // Add cooked food to inventory
        inventory.addItem(recipe.getName());
        System.out.println("Successfully cooked: " + recipe.getName());

        return true;
    }

    public int getFuel() {
        return fuel;
    }

    public int getEnergy() {
        return energy;
    }

    public void addFuel(int amount) {
        this.fuel += amount;
    }

    public void addEnergy(int amount) {
        this.energy += amount;
    }
} 
