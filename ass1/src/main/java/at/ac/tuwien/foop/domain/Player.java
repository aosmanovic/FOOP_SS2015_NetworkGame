package at.ac.tuwien.foop.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {
	private final String name;
	private Coordinates coordinates;
	private Coordinates lastCoordinates;
	private State state;
	private String color = "";
	
	public static enum State {
		crazy, notSoCrazy, notCrazy, crash
	} ;
	
	

	@JsonCreator
	public Player(@JsonProperty("name") String name, @JsonProperty("coordinates") Coordinates coordinates, @JsonProperty("lastCoordinates") Coordinates lastCoordinates, @JsonProperty("state") State crazyState) {
		this.name = name;
		this.coordinates = coordinates;
		this.lastCoordinates = lastCoordinates;
		this.state = crazyState;
	}
	
	public Player(String name, Coordinates coordinates) {
		this(name, coordinates, null, State.notCrazy);
	}

	@JsonProperty("name")
	public String name() {
		return name;
	}

	@JsonProperty("coordinates")
	public Coordinates coordinates() {
		return coordinates;
	}

	public Player moveTo(int x, int y, Coordinates lastCoordinates, State state) {
		return new Player(name, new Coordinates(x, y), lastCoordinates, state);
	}

	public Player moveTo(int x, int y) {
		return new Player(name, new Coordinates(x, y));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", coordinates=" + coordinates + "]";
	}

	public Coordinates getLastCoordinates() {
		return lastCoordinates;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}	
	
	
	
}
