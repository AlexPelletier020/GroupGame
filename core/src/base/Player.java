package base;

import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupproject.game.Logic;
import com.groupproject.util.Directions;

/**
 * @author Mohammed Al-Safwan
 * @date Mar 12, 2019
 */
public class Player {

	private int id;
	private String name;
	private float hp;
	private Position position;

	private float panX = 24;
	private float panY = 24;

	private static Texture[] playersTextures;
	private Sprite sprite;

	private final String KEY_ID = "ID";
	private final String KEY_HP = "HP";
	private final String KEY_POSITION = "POSITION";
	private final String KEY_NAME = "NAME";

	public Player() {
		id = -1;
		name = "";
		hp = 0;
		position = new Position();

	}

	public Player(int id, Position position, String name) {
		this.id = id;
		this.name = name;
		this.hp = 100;
		this.position = position;

		if (null == playersTextures) {
			playersTextures = new Texture[8];

			for (int index = 0; index < 8; index++) {
				playersTextures[index] = new Texture("Player" + index + ".png");

			}
		}

		sprite = new Sprite(playersTextures[id]);
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

		// false if it's not a Player object
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
		this.position.toPosition(new JSONObject(jsonMsg.optString(KEY_POSITION)));
	}

	public void Update() {
		int tilePosX = 0;
		int tilePosY = 0;
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			tilePosX = (int) ((position.getX() + panX) - 10) / 48;
			tilePosY = (int) ((position.getY() + panY) / 48);
			if (Logic.mMap[tilePosX][tilePosY].getTileType().ordinal() != 0) {
				position.setDirection(Directions.WEST);
				position.setX(position.getX() - 10);
			}
			System.out.println(position.getDirection());
			// send message to move left
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			tilePosX = (int) ((position.getX() + panX) + 24) / 48;
			tilePosY = (int) ((position.getY() + panY) / 48);
			if (Logic.mMap[tilePosX][tilePosY].getTileType().ordinal() != 0) {
				position.setDirection(Directions.EAST);
				position.setX(position.getX() + 10);
			}
			System.out.println(position.getDirection());

			// send message to move right
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			tilePosX = (int) ((position.getX() + panX)) / 48;
			tilePosY = (int) (((position.getY() + panY) + 24) / 48);

			if (Logic.mMap[tilePosX][tilePosY].getTileType().ordinal() != 0) {
				position.setDirection(Directions.NORTH);
				position.setY(position.getY() + 10);
			}
			System.out.println(position.getDirection());

			// send message to move up
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			tilePosX = (int) ((position.getX() + panX)) / 48;
			tilePosY = (int) (((position.getY() + panY) - 10) / 48);
			if (Logic.mMap[tilePosX][tilePosY].getTileType().ordinal() != 0) {
				position.setDirection(Directions.SOUTH);
				position.setY(position.getY() - 10);
			}
			System.out.println(position.getDirection());

			// send message to move south
		}


	}

	public void Draw(SpriteBatch sb) {
		if(null == sprite) {
			sprite = new Sprite(playersTextures[id]);
		}

		switch (position.getDirection()) {
		case NORTH:
			sprite.setRotation(90);
			break;

		case SOUTH:
			sprite.setRotation(-90);
			break;
		case WEST:
			sprite.setRotation(180);
			break;
		case EAST:
			sprite.setRotation(0);
			break;
		}
		
		sprite.setPosition(position.getX(), position.getY());

		sprite.draw(sb);
	}

	public void setFromOtherPlayer(Player newPlayer) {
		this.position.setX(newPlayer.position.getX());
		this.position.setY(newPlayer.position.getY());
		this.position.setDirection(newPlayer.position.getDirection());
		this.position.setSpeed(newPlayer.position.getSpeed());
		this.hp = newPlayer.hp;
	}
}
