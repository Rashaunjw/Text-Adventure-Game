
public class Monster {
    private String name;
    private int damage;
    private String description;
    private String attackMove;

    public Monster(String name, int damage, String description, String attackMove) {
        this.name = name;
        this.damage = damage;
        this.description = description;
        this.attackMove = attackMove;
    }
    public String getName() {
        return name;
    }
    public int getDamage() {
        return damage;
    }
    public String getDescription() {
        return description;
    }

    public String getAttackMove() {
        return attackMove;
    }
}

