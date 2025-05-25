public class Food extends Consumable {
    private int energyRestore;

    public Food(String name, int buyPrice, int sellPrice, int energyRestore) {
        super(name, buyPrice, sellPrice);
        this.energyRestore = energyRestore;
    }

    public int getEnergyRestore() {
        return energyRestore;
    }

    public void consume(Player player) {
        player.restoreEnergy(energyRestore);
        // You may also want to remove this item from inventory after use
    }

    @Override
    public String toString() {
        return getName() + " (Energy: +" + energyRestore + ", Buy: " + getBuyPrice() + "g, Sell: " + getSellPrice() + "g)";
    }
}
