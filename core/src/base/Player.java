package base;

import org.json.JSONObject;

/**
 * @author Mohammed Al-Safwan
 * @date Mar 12, 2019
 */
public class Player {

	private int id;
	private String name;
	private float hp;
	private Position position;

	private final String KEY_ID = "ID";
	private final String KEY_HP = "HP";
	private final String KEY_POSITION = "POSITION";
	private final String KEY_NAME = "NAME";

	public Player() {
		id = 0;
		name = "";
		hp = 0;
		position = null;
		
	}
	
	public Player(int id, Position position, String name) {
		this.id = id;
		this.name = name;
		this.hp = 100;
		this.position = position;
	}
	

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the hp
	 */
	public float getHp() {
		return hp;
	}

	/**
	 * @param hp
	 *            the hp to set
	 */
	public void setHp(float hp) {
		this.hp = hp;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		// If the object is compared with itself then return true   
		if (obj == this) {
			return true;
		}

		//false if it's not a Player object
		if (!(obj instanceof Player)) {
			return false;
		}

		return ((Player) obj).getId() == this.id && ((Player) obj).getHp() == this.hp
				&& ((Player) obj).getName().equals(this.name) && ((Player) obj).getPosition() == this.position;
	}

	@Override
	public String toString() {
		return toJSON().toString();
	}

	public String toPrettyString() {
		return toJSON().toString(4);
	}

	public JSONObject toJSON() {
		JSONObject outgoingPlayer = new JSONObject();
		outgoingPlayer.put(KEY_ID, id);
		outgoingPlayer.put(KEY_NAME, name);
		outgoingPlayer.put(KEY_HP, hp);
		outgoingPlayer.put(KEY_POSITION, position.toString());
		return outgoingPlayer;
	}

	public void toPlayer(JSONObject jsonMsg) {
		this.id = jsonMsg.optInt(KEY_ID);
		this.name = jsonMsg.optString(KEY_NAME);
		this.hp = jsonMsg.optFloat(KEY_HP);
		this.position.toPosition(jsonMsg.optString(KEY_POSITION));
	}

}
