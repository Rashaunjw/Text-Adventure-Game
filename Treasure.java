public class Treasure {

	private final String description;

	private final String name;

	private final int value;


	public Treasure(String name, int value, String description) {
		this.name = name;
		this.value = value;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

}
