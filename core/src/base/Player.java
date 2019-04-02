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

	private int tilePosX = 0;
	private int tilePosY = 0;
	
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
		init(-1, 0, new Position(), "");
	}

	public Player(int id, Position position, String name) {
		init(id, 100, position, name);
	}

	private void init(int id, int hp, Position position, String name) {
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
		sprite = new Sprite(playersTextures[0]);
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
		outgoingPlayer.put(KEY_POSITION, position.toString());
		outgoingPlayer.put(KEY_HP, hp);
		outgoingPlayer.put(KEY_NAME, name);
		outgoingPlayer.put(KEY_ID, id);
		return outgoingPlayer;
	}

	public void toPlayer(JSONObject jsonMsg) {
		this.id = jsonMsg.optInt(KEY_ID);
		this.name = jsonMsg.optString(KEY_NAME);
		this.hp = jsonMsg.optFloat(KEY_HP);
		if (null == position)
			this.position = new Position();
		this.position.toPosition(new JSONObject(jsonMsg.optString(KEY_POSITION)));
	}

	public void setFromOtherPlayer(Player newPlayer) {
		this.id = newPlayer.id;
		this.name = newPlayer.name;
		this.position.setX(newPlayer.position.getX());
		this.position.setY(newPlayer.position.getY());
		this.position.setDirection(newPlayer.position.getDirection());
		if (null == position)
			this.position = new Position();
		this.position.setSpeed(newPlayer.position.getSpeed());
		this.hp = newPlayer.hp;
	}

	public void Update() {
		

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			position.setDirection(Directions.WEST);
			calculateTilePos();
			if (Logic.mMap[tilePosX][tilePosY].getTileType().ordinal() != 0) {
				position.setX(position.getX() - 10);
			}
			// send message to move left
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			position.setDirection(Directions.EAST);
			calculateTilePos();
			if (Logic.mMap[tilePosX][tilePosY].getTileType().ordinal() != 0) {
				position.setX(position.getX() + 10);
			}

			// send message to move right
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			position.setDirection(Directions.NORTH);
			calculateTilePos();
			if (Logic.mMap[tilePosX][tilePosY].getTileType().ordinal() != 0) {
				position.setY(position.getY() + 10);
			}

			// send message to move up
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			position.setDirection(Directions.SOUTH);
			calculateTilePos();
			if (Logic.mMap[tilePosX][tilePosY].getTileType().ordinal() != 0) {
				position.setY(position.getY() - 10);
			}

			// send message to move south
		}
		

	}
	public void calculateTilePos()
	{
		switch(position.getDirection()) {
		case NORTH:
			tilePosX = (int) ((position.getX() + panX)) / 48;
			tilePosY = (int) (((position.getY() + panY) + 24) / 48);
			break;
		case SOUTH:
			tilePosX = (int) ((position.getX() + panX)) / 48;
			tilePosY = (int) (((position.getY() + panY) - 10) / 48);
			break;
		case WEST:
			tilePosX = (int) ((position.getX() + panX) - 10) / 48;
			tilePosY = (int) ((position.getY() + panY) / 48);
			break;
		case EAST:
			tilePosX = (int) ((position.getX() + panX) + 24) / 48;
			tilePosY = (int) ((position.getY() + panY) / 48);
			break;
		}
		
	}
	public void Draw(SpriteBatch sb) {
		if (null == sprite) {
			sprite = new Sprite(playersTextures[0]);
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

	/**
	 * @return the tilePosX
	 */
	public int getTilePosX() {
		return tilePosX;
	}

	/**
	 * @param tilePosX the tilePosX to set
	 */
	public void setTilePosX(int tilePosX) {
		this.tilePosX = tilePosX;
	}

	/**
	 * @return the tilePosY
	 */
	public int getTilePosY() {
		return tilePosY;
	}

	/**
	 * @param tilePosY the tilePosY to set
	 */
	public void setTilePosY(int tilePosY) {
		this.tilePosY = tilePosY;
	}

	/**
	 * @return the sprite
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * @param sprite the sprite to set
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

}
