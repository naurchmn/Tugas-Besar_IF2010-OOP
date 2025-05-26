package StatusBar;
public class StatusBarController {
    private Player player;

    public StatusBarController(Player player) {
        this.player = player;
    }

    public String getName() {
        return player.getName();
    }

    public String getEnergy() {
        return player.getEnergy() + "/100";
    }

    public String getGold() {
        return player.getGold().toString();
    }

    public String getLocation() {
        return player.getLocation();
    }

    public String getPartner() {
        return (player.getPartner() != null) ? player.getPartner().getName() : "-";
    }
}