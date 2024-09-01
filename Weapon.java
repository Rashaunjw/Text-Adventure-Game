
public class Weapon {

    private String name;
    private String description;
    private int damage;
    private String attackMove;

    public Weapon(String name, int damage, String description, String attackMove) {
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.attackMove = attackMove;
    }


    public String getAttackMove() {
        return attackMove;
    }


    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }
}




