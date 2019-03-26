package base;

import org.json.JSONObject;

public class Bullet {

	private int id;
	private int reference;
	private Position position;

	private final String KEY_ID = "id";
	private final String KEY_REFERENCE = "reference";
	private final String KEY_POSITION = "position";

	public Bullet() {
		this.id = -1;
		this.reference = -1;
		this.position = null;
	}

	public Bullet(int ID, int PID, Position startPos) {
		this.id = ID;
		this.reference = PID;
		this.position = startPos;
	}

	public int getId() {
		return id;
	}

	public int getReference() {
		return reference;
	}

	public Position getPosition() {
		return position;
	}

	public void Update() {
		switch (position.getDirection()) {

		case NORTH:
			position.setY(position.getY() + position.getSpeed());
			break;

		case EAST:
			position.setX(position.getX() + position.getSpeed());
			break;

		case SOUTH:
			position.setY(position.getY() - position.getSpeed());
			break;

		case WEST:
			position.setX(position.getX() - position.getSpeed());
			break;
		}

	}

	@Override
	public boolean equals(Object obj) {
		// If the object is compared with itself then return true
		if (obj == this) {
			return true;
		}

		// false if it's not a Bullet object
		if (!(obj instanceof Bullet)) {
			return false;
		}

		return ((Bullet) obj).getId() == this.id && ((Bullet) obj).getReference() == this.reference
				&& ((Bullet) obj).getPosition().equals(this.position);
	}

	@Override
	public String toString() {
		return toJSON().toString();
	}

	public String toPrettyString() {
		return toJSON().toString(4);
	}

	public JSONObject toJSON() {
		JSONObject outgoingBullet = new JSONObject();

		outgoingBullet.put(KEY_ID, id);
		outgoingBullet.put(KEY_REFERENCE, reference);
		outgoingBullet.put(KEY_POSITION, position.toString());
		return outgoingBullet;
	}

	public void toBullet(JSONObject jsonMsg) {
		this.id = jsonMsg.optInt(KEY_ID);
		this.reference = jsonMsg.optInt(KEY_REFERENCE);
		this.position.toPosition(new JSONObject(jsonMsg.optString(KEY_POSITION)));
	}

}
