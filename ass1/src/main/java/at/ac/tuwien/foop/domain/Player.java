package at.ac.tuwien.foop.domain;

import java.util.ArrayList;

public class Player {
	private final String name;
	private Coordinates coordinates;
	// TODO: what is this used for?
	private ArrayList<Coordinates> visitedCoordinates = new ArrayList<>();
	private Coordinates lastCoordinates;

	public Player(String name, Coordinates coordinates, Coordinates lastCoordinates) {
		this.name = name;
		this.coordinates = coordinates;
		this.lastCoordinates = lastCoordinates;
		//this.visitedCoordinates.add(coordinates);
	}
	
	public Player(String name, Coordinates coordinates) {
		this(name, coordinates, null);
	}


	public String name() {
		return name;
	}

	public Coordinates coordinates() {
		return coordinates;
	}

	public Player moveTo(int x, int y, Coordinates lastCoordinates) {
		return new Player(name, new Coordinates(x, y), lastCoordinates);
	}

	public ArrayList<Coordinates> getVisitedCoordinates() {
		return visitedCoordinates;
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
}
