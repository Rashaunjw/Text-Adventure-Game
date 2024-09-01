import java.util.ArrayList;
import java.util.Scanner;



public class MyAdventure {
    /** Text adventure game. */

    public static void main(String[] args) {
        new MyAdventure().run();
    }

    // List of treasures the player has
    private ArrayList<Treasure> treasures;

    // List of weapons the player has
    private ArrayList<Weapon> weapons;

    // Damage done by best weapon
    private int bestWeaponDamage;

    // Room where player currently is
    private Room currentRoom;

    // Score is the number of points the player has gotten from the treasures
    private int score;

    // The amount of health a player has
    private int health;

    // The previous room is noted so that if attacked by a beast the player will be booted to the previous room
    private Room previousRoom;

    // Field to store the selected weapon
    private Weapon selectedWeapon;

    // Scanner for input
    private Scanner scanner;

    // To see if the user has looked around the room before any commands are inputted
    private boolean hasLooked = false;



    public MyAdventure() {

        // The player starts with no treasures or weapons
        treasures = new ArrayList<Treasure>();
        weapons = new ArrayList<Weapon>();
        scanner = new Scanner(System.in);


        // Create rooms
        Room entrance = new Room("the entrance", "a cramped natural passage, filled with dripping stalactites");
        Room hallway = new Room("a hallway", "a vast hallway with a vaulted stone ceiling");
        Room armory = new Room("the armory", "an abandoned armory");
        Room man_cave = new Room("a man cave", "a large cave, steaming with volcanic heat");
        Room lair = new Room("a lair", "a dark pit filled with skull heads made into lanterns");
        Room laboratory = new Room("a laboratory", "a room loaded with all the latest technology in every universe");
        Room thors_pantry = new Room("Thor's pantry", "a pantry stocked will all of the world's desires");
        Room sun_room = new Room("the Sun room", "a bright yellow room filled with sunlight");
        Room gods_bathroom = new Room("God's bathroom", "a baby blue room decorated with clouds and angels");

        // Create connections
        entrance.addNeighbor("north", hallway);
        hallway.addNeighbor("south", entrance);
        hallway.addNeighbor("west", armory);
        armory.addNeighbor("east", hallway);
        hallway.addNeighbor("east", lair);
        lair.addNeighbor("west", hallway);
        hallway.addNeighbor("north", sun_room);
        sun_room.addNeighbor("south", hallway);
        sun_room.addNeighbor("east", laboratory);
        laboratory.addNeighbor("east", thors_pantry);
        thors_pantry.addNeighbor("west",laboratory);
        laboratory.addNeighbor("west", sun_room);
        sun_room.addNeighbor("west", man_cave);
        man_cave.addNeighbor("east", sun_room);
        sun_room.addNeighbor("north", gods_bathroom);
        gods_bathroom.addNeighbor("south", sun_room);

        // Create and install monsters
        hallway.setMonster(new Monster("spider", 10, "a giant, shadowy [spider]", "shoots venomous webs that seep into your skin"));
        lair.setMonster(new Monster("jaguar", 20, "a vicious [jaguar]", "bites a chunk off your leg"));
        laboratory.setMonster(new Monster("badger", 30, "a wickedly sinister honey [badger]", "lunges at you with its sharp fangs and snarls ferociously"));
        man_cave.setMonster(new Monster("bear", 40, "a monstrous [bear]", "slams both its paws into you breaking a few bones"));
        gods_bathroom.setMonster(new Monster("dragon", 50, "a frightening, fire-breathing [dragon]", "breathes a scorching white-hot flame"));

        // Create and install treasures
        hallway.setTreasure(new Treasure("diamond", 10, "a huge, glittering [diamond]"));
        sun_room.setTreasure(new Treasure("chalice", 15, "a ruby red [chalice] encrusted with Ethiopian opal gemstones"));
        thors_pantry.setTreasure(new Treasure("cookie", 25, "a golden sugar [cookie]"));
        gods_bathroom.setTreasure(new Treasure("surfboard", 50, "THE silver [surfboard] made by Silver Surfer himself"));

        // Create and install weapons
        Weapon knife = new Weapon("knife", 11, "a razor-sharp, butcher [knife]", "As you lunge forward you slice the ");
        entrance.setWeapon(knife);
        Weapon pistol = new Weapon("pistol", 21, "a reverberating chrome [pistol]", "In a flash you shoot a gaping hole into the ");
        armory.setWeapon(pistol);
        Weapon wand = new Weapon("wand", 31, "a magical [wand] from Hogwarts", "As you raise your arm you cast a deathly spell upon the ");
        lair.setWeapon(wand);
        Weapon lightsaber = new Weapon("lightsaber", 41, "Yoda's very own [lightsaber]", "In a swift motion you slash cleanly through the ");
        laboratory.setWeapon(lightsaber);
        Weapon gauntlet = new Weapon("gauntlet", 51, "the infinity [gauntlet] gifted from Thanos", "With a snap of your finger you slowly disintegrate the ");
        man_cave.setWeapon(gauntlet);

        // Player starts off with health at 100 and the previous room is set to empty
        health = 100;
        previousRoom = null;

        // The player starts in the entrance, with no weapon
        currentRoom = entrance;
        bestWeaponDamage = 0;
    }

    // Strike method to calculate damage dealt to monster and monster damage dealt to player and kicks player out of room if struck by monster
    public void strike(String name) {
        Monster monster = (Monster) currentRoom.getMonster();
        if (monster != null && monster.getName().equals(name)) {
            if (selectedWeapon != null && selectedWeapon.getDamage() > monster.getDamage()) {
                System.out.println();
                System.out.println(selectedWeapon.getAttackMove() + currentRoom.getMonster().getName() + " with your " + selectedWeapon.getName() + ", and defeat the beast!");
                currentRoom.setMonster(null);
            } else {
                int damageTaken = monster.getDamage();
                health -= damageTaken;
                System.out.println();
                System.out.println("Your assault had no effect.");
                System.out.println("The " + monster.getName() + " " + monster.getAttackMove() + " causing " + damageTaken + " damage!");
                if (health <= 0) {
                    System.out.println("You have been defeated!");
                    System.out.println("GAME OVER");
                    System.exit(0);
                } else {
                    // Kick the player out of room
                    currentRoom = previousRoom;
                    System.out.println("You crawl back into the previous room!");
                }
            }
        } else {
            System.out.println("There is no " + name + " here.");
        }
    }

    // Go method moves player to and through exits
    public void go(String direction) {
        previousRoom = currentRoom;
        Room destination = currentRoom.getNeighbor(direction);
        if (destination == null) {
            System.out.println();
            System.out.println("You can't go that way from here.");
        } else {
            currentRoom = destination;
            hasLooked = false; // Reset the flag when entering a new room
        }
    }

    // Inventory method prints each treasure and weapon player has obtained and prints the score of treasure and if the weapon has the best damage and or if the weapon is currently selected.
    public void inventory() {
        System.out.println();
        System.out.println("Inventory:");
        if (!treasures.isEmpty()) {
            System.out.println("    Treasures:");
            for (Treasure treasure : treasures) {
                System.out.println("        - " + treasure.getName() + " (" + treasure.getValue() + " points!)");
            }
        }
        if (!weapons.isEmpty()) {
            System.out.println("    Weapons:");
            boolean hasSelectedWeapon = false;
            Weapon bestWeapon = null;
            int maxDamage = 0;
            int weaponCount = 0;

            for (Weapon weapon : weapons) {
                String weaponInfo = "        - " + weapon.getName() + " (Damage = " + weapon.getDamage() + ")";
                if (weapon == selectedWeapon) {
                    weaponInfo += " (Selected)";
                    hasSelectedWeapon = true;
                }
                if (weapon.getDamage() == bestWeaponDamage && weaponCount > 0) {
                    weaponInfo += " [Best Damage]";
                }
                System.out.println(weaponInfo);
                weaponCount++;

                if (weapon.getDamage() > maxDamage) {
                    maxDamage = weapon.getDamage();
                    bestWeapon = weapon;
                }
            }
            while (!hasSelectedWeapon) {
                System.out.println();
                System.out.println("Enter the name of the weapon in your inventory:");
                String selectedWeaponName = scanner.nextLine().trim();

                boolean weaponFound = false;
                for (Weapon weapon : weapons) {
                    if (weapon.getName().equalsIgnoreCase(selectedWeaponName)) {
                        selectedWeapon = weapon;
                        hasSelectedWeapon = true;
                        weaponFound = true;
                        System.out.println();
                        System.out.println("** You've selected the " + selectedWeapon.getName() + " as your weapon!");
                        break;
                    }
                }

                if (!weaponFound) {
                    System.out.println();
                    System.out.println("** The entered weapon name is not in your inventory.");
                }
            }
            if (bestWeapon != null) {
                bestWeaponDamage = bestWeapon.getDamage(); // Update the best weapon damage
            }
        }
        if (treasures.isEmpty() && weapons.isEmpty()) {
            System.out.println("Your inventory is empty.");
        }
        System.out.println();
        listCommands();
    }

    // Handle command method handles command from player. If the player is confronted with monster and input is "take" then it prints an error message and lists out commands.
    // Also lets the player know they must look around the room first before doing any together command
    public void handleCommand(String line) {
        String[] words = line.split(" ");

        if (!hasLooked && !words[0].equals("look")) {
            System.out.println();
            System.out.println("** You must 'look' around the room first **");
            return;
        }

        if (words.length < 2 && (words[0].equals("strike") || words[0].equals("go") || words[0].equals("take") || words[0].equals("select"))) {
            System.out.println();
            System.out.println("**You need to provide more information.");
            System.out.println();
            listCommands();
            return;
        }

        if (currentRoom.getMonster() != null && (words[0].equals("take"))) {
            System.out.println();
            System.out.println("** You can't do that with unfriendlies about.");
            System.out.println();
            listCommands();
        } else if (words[0].equals("strike")) {
            strike(words[1]);
        } else if (words[0].equals("go")) {
            go(words[1]);
        } else if (words[0].equals("look")) {
            look();
            inventory();
        } else if (words[0].equals("take")) {
            take(words[1]);
        } else if (words[0].equals("select")) {
            select(words[1]);
        } else {
            System.out.println();
            System.out.println("**Sorry, I didn't understand that.");
            System.out.println();
            listCommands();
        }
    }

    // List commands method lists commands for player if they input the wrong command depending on specific room
    public void listCommands() {
        System.out.println("Examples of commands:");

        if (currentRoom.getName().equals("the entrance")) {
            System.out.println("  go north");
            System.out.println("  take knife");
        } else if (currentRoom.getName().equals("a hallway")) {
            System.out.println("  go south");
            System.out.println("  go west");
            System.out.println("  go east");
            System.out.println("  go north");
            System.out.println("  take diamond");
        } else if (currentRoom.getName().equals("the armory")) {
            System.out.println("  go east");
            System.out.println("  take pistol");
        } else if (currentRoom.getName().equals("a man cave")) {
            System.out.println("  go east");
            System.out.println("  take gauntlet");
        } else if (currentRoom.getName().equals("a lair")) {
            System.out.println("  go west");
            System.out.println("  take wand");
        } else if (currentRoom.getName().equals("a laboratory")) {
            System.out.println("  go west");
            System.out.println("  go east");
            System.out.println("  take lightsaber");
        } else if (currentRoom.getName().equals("Thor's pantry")) {
            System.out.println("  go west");
            System.out.println("  take cookie");
        } else if (currentRoom.getName().equals("the Sun room")) {
            System.out.println("  go south");
            System.out.println("  go east");
            System.out.println("  go west");
            System.out.println("  go north");
            System.out.println("  take chalice");
        } else if (currentRoom.getName().equals("God's bathroom")) {
            System.out.println("  go south");
            System.out.println("  take surfboard");
        }
        if (!weapons.isEmpty()) {
            for (Weapon weapon : weapons) {
                System.out.println("  select " + weapon.getName());
            }
        }
        if (currentRoom.getMonster() != null) {
            System.out.println("  strike " + currentRoom.getMonster().getName());
        }
    }

    // Select method selects the desired weapon from inventory
    public void select(String weaponName) {
        for (Weapon weapon : weapons) {
            if (weapon.getName().equalsIgnoreCase(weaponName)) {
                selectedWeapon = weapon;
                System.out.println("You've selected the " + selectedWeapon.getName() + " as your weapon.");
                return;
            }
        }
        System.out.println();
        System.out.println("The entered weapon name is not in your inventory.");
    }


    // Look method prints health score, inventory, things in particular room, and exits.
    public void look() {
        System.out.println();
        System.out.println("Health = " + health + " ");
        System.out.println("*** You are in " + currentRoom.getDescription() + ". ***");
        if (currentRoom.getMonster() != null) {
            System.out.println("There is " + currentRoom.getMonster().getDescription() + ". Strength = " + currentRoom.getMonster().getDamage() + ".");
        }
        if (currentRoom.getWeapon() != null) {
            System.out.println("There is " + currentRoom.getWeapon().getDescription() + ". Damage = " + currentRoom.getWeapon().getDamage());
        }
        if (currentRoom.getTreasure() != null) {
            System.out.println("There is " + currentRoom.getTreasure().getDescription() + ".");
        }
        System.out.println("Exits: " + currentRoom.listExits());
        hasLooked = true;

    }

    // Run method runs the game, prints opening instructions, and the rules for game.
    public void run() {
        System.out.println();
        System.out.println("        **************** How to play *****************");
        System.out.println();
        System.out.println("        Type 'strike' to kill enemies in rooms.");
        System.out.println("        Type 'go' to advance thru exits.");
        System.out.println("        Type 'look' to scan your surroundings.");
        System.out.println("        Type 'take' to obtain treasures and/or weapons. ");
        System.out.println("        Type 'select' to grab a weapon from inventory.");
        System.out.println();
        System.out.println("        **************** How to play *****************");
        System.out.println();
        System.out.println("- Every enemy you encounter is stronger than the last and every weapon you obtain creates more damage");
        System.out.println("- In order to defeat enemies you must select a weapon from inventory that deals the most damage");
        System.out.println("- Read command examples to help navigate through adventure");
        System.out.println("- To win you must claim all treasures");
        System.out.println();
        System.out.println();
        System.out.println("First, type 'look' to scan your surroundings.");
        System.out.println();
        System.out.println();
        while (true) {
            System.out.println("You are in " + currentRoom.getName() + ".");
            System.out.print("> ");
            handleCommand(scanner.nextLine());
            System.out.println();
        }
    }

    // Take method lets player take a weapon or treasure out specific room
    public void take(String name) {
        if (currentRoom.getTreasure() != null && currentRoom.getTreasure().getName().equals(name)) {
            Treasure treasure = currentRoom.getTreasure();
            currentRoom.setTreasure(null);
            treasures.add(treasure);
            score += treasure.getValue();
            System.out.println("Your score is now " + score + " out of 100.");
            if (score == 100) {
                System.out.println();
                System.out.println("YOU WIN!");
                System.exit(0);
            }
        } else if (currentRoom.getWeapon() != null && currentRoom.getWeapon().getName().equals(name)) {
            Weapon weapon = currentRoom.getWeapon();
            currentRoom.setWeapon(null);
            weapons.add(weapon);
            if (weapon.getDamage() > bestWeaponDamage) {
                bestWeaponDamage = weapon.getDamage();
                System.out.println();
                System.out.println("You'll be a more effective fighter with this!");
            }
        } else {
            System.out.println("There is no " + name + " here.");
        }
    }
}