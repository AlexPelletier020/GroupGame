package base;

import org.json.JSONObject;

import com.groupproject.util.Directions;
import com.groupproject.util.MessageType;
/**
 * @author Ryan Gowan
 * @date Mar 12, 2019
 */
public class Position {

	private float x, y;
	private Directions direction;
	private float speed;

	private final String KEY_X = "x";
	private final String KEY_Y = "y";
	private final String KEY_DIRECTION = "direction";
	private final String KEY_SPEED = "speed";
	
	public Position(){
		x = 0;
		y = 0;
		direction = Directions.NULL;
		speed = 0;
	}

	public Position(float x, float y, Directions direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.speed = 0;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Directions getDirection() {
		return direction;
	}

	public void setDirection(Directions dir) {
		this.direction = dir;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void toPosition(String optString) {
		JSONObject newPosition = new JSONObject(optString);
		x = newPosition.getFloat(KEY_X);
		y = newPosition.getFloat(KEY_Y);
		direction = Directions.valueOf(newPosition.optString(KEY_DIRECTION, Directions.NULL.name()));
		speed = newPosition.getFloat(KEY_SPEED);
	}

	@Override
	public boolean equals(Object obj) {
		// If the object is compared with itself then return true   
		if (obj == this) {
			return true;
		}

		//false if it's not a Position object
		if (!(obj instanceof Position)) {
			return false;
		}

		return ((Position) obj).getX() == this.x && ((Position) obj).getY() == this.y
				&& ((Position) obj).getDirection().equals(this.direction) && ((Position) obj).getSpeed() == this.speed;
	}

	@Override
	public String toString() {
		return toJSON().toString();
	}

	public String toPrettyString() {
		return toJSON().toString(4);
	}

	public JSONObject toJSON() {
		JSONObject outgoingPosition = new JSONObject();
		outgoingPosition.put(KEY_X, this.x);
		outgoingPosition.put(KEY_Y, this.y);
		outgoingPosition.put(KEY_DIRECTION, direction.toString());
		outgoingPosition.put(KEY_SPEED, this.speed);
		return outgoingPosition;
	}

	public void toPosition(JSONObject jsonMsg) {
		this.x = jsonMsg.optFloat(KEY_X);
		this.y = jsonMsg.optFloat(KEY_Y);
		this.direction = Directions.valueOf(jsonMsg.getString(KEY_DIRECTION));
		this.speed = jsonMsg.optFloat(KEY_SPEED);
	}
}
