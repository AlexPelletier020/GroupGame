package base;

import java.util.Date;

import org.json.JSONObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupproject.game.Logic;

public class Bullet implements Comparable<Bullet> {

	private static int idCounter;
	private int id;
	private int reference;
	private Position position;
	private Date creationDate;

	private int tilePosX = 0;
	private int tilePosY = 0;
	private static Texture bulletTexture;
	private Sprite sprite;

	private final String KEY_ID = "id";
	private final String KEY_REFERENCE = "reference";
	private final String KEY_POSITION = "position";
	private final String KEY_DATE = "creatingDate";

	private static final int panX = 24;
	private static final int panY = 24;;

	private static boolean wasTextureCreated = false;

	public Bullet() {
		init(-1, new Position());
	}

	public Bullet(int PID, Position startPos) {
		this.idCounter++;
		init(PID, startPos);
		if (!wasTextureCreated && null == bulletTexture) {
			bulletTexture = new Texture("shot.png");
			wasTextureCreated = true;
		}
		sprite = new Sprite(bulletTexture);
	}

	private void init(int PID, Position startPos) {
		this.id = idCounter;
		this.reference = PID;
		this.position = startPos;
		this.position.addX(panX);
		this.position.addY(panY);
		this.creationDate = new Date();

		//		if (Thread.currentThread().getName().equals("LWJGL Application")) {
		//
		//		}
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
				&& ((Bullet) obj).getPosition().equals(this.position)
				&& ((Bullet) obj).getCreationDate() == this.creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
		outgoingBullet.put(KEY_DATE, creationDate.getTime());
		outgoingBullet.put(KEY_POSITION, position.toString());
		outgoingBullet.put(KEY_REFERENCE, reference);
		outgoingBullet.put(KEY_ID, id);
		return outgoingBullet;
	}

	/**
	 * @return the tilePosX
	 */
	public int getTilePosX() {
		return tilePosX;
	}

	/**
	 * @param tilePosX
	 *            the tilePosX to set
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
	 * @param tilePosY
	 *            the tilePosY to set
	 */
	public void setTilePosY(int tilePosY) {
		this.tilePosY = tilePosY;
	}

	public void toBullet(JSONObject jsonMsg) {
		this.id = jsonMsg.optInt(KEY_ID);
		this.reference = jsonMsg.optInt(KEY_REFERENCE);
		this.position.toPosition(new JSONObject(jsonMsg.optString(KEY_POSITION)));
		this.creationDate = new Date(jsonMsg.getLong(KEY_DATE));
	}

	public boolean Update() {
		tilePosX = (int) ((position.getX())) / 48;
		tilePosY = (int) ((position.getY())) / 48;

		if (Logic.mMap[tilePosX][tilePosY].getTileType().ordinal() == 0) {
			return false;
		}

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

		return true;

	}

	public boolean collisionCheck(Player mainPlayer) {
		return tilePosX == mainPlayer.getTilePosX() && tilePosY == mainPlayer.getTilePosY();
	}
	//	public int Update(Player thePlayer) {
	//		int result = 0;
	//		if(Update()) {
	//			result = 0;
	//		}else {
	//			//collision with player here 
	//			//-collided with player 
	//			//- result = 1
	//			// else 
	//			//- result = 2
	//		}
	//		return result;
	//	}

	public void Draw(SpriteBatch sb) {
		if (!wasTextureCreated && null == bulletTexture) {
			bulletTexture = new Texture("shot.png");
			wasTextureCreated = true;
		}

		if (null == sprite) {
			sprite = new Sprite(bulletTexture);
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

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Bullet bulletToCompare) {
		return this.creationDate.compareTo(bulletToCompare.creationDate);
	}

}
