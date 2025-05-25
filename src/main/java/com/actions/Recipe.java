import java.util.List;

public class Recipe {
    private String name;
    private List<String> ingredients; // Bisa berupa ID atau nama item

    public Recipe(String name, List<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public boolean canCook(Inventory inventory) {
        for (String ingredient : ingredients) {
            if (!inventory.hasItem(ingredient)) {
                return false;
            }
        }
        return true;
    }
} 
