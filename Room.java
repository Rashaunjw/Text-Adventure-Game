import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private String description;
    private ArrayList<String> exits;
    private ArrayList<Room> neighbors;
    private List<String> treasures;

    private List<String> weapons;
    private Treasure treasure;
    private Weapon weapon;
    private Monster monster;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new ArrayList<>();
        this.neighbors = new ArrayList<>();
        treasures = new ArrayList<>();
        weapons = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String listExits() {
        return exits.toString();
    }

    public Room getNeighbor(String direction) {
        int index = exits.indexOf(direction);
        if (index != -1) {
            return neighbors.get(index);
        }
        return null;
    }

    public void addNeighbor(String direction, Room room) {
        exits.add(direction);
        neighbors.add(room);
    }

    public void setTreasure(Treasure diamond) {
        this.treasure = diamond;
    }

    public Weapon getWeapon() {
        return this.weapon;
    }

    public Treasure getTreasure() {
        return treasure;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }


}
