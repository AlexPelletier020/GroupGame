package base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupproject.util.TileType;

public class Tile {

	private TileType tileType; // Replace with TileType var type
	private float x, y;
	private float width, height;
	private static Texture[] tileTextures;
	private Sprite sprite;

	public Tile() {
		init(-1, -1, 48, 48, TileType.NULL);
	}

	public Tile(float x, float y, TileType tileType) {
		init(x, y, 48, 48, tileType);

	}

	public Tile(float x, float y, float width, float height, TileType tileType) {
		init(x, y, width, height, tileType);
	}

	private void init(float x, float y, float width, float height, TileType tileType) {
		this.x = x;
		this.y = y;
		this.tileType = tileType;
		this.width = width;
		this.height = height;

		initTexture();
	}

	private void initTexture() {
		if (null == tileTextures) {
			tileTextures = new Texture[3];
			tileTextures[0] = new Texture("Wall.png");
			tileTextures[1] = new Texture("Ground.png");
			tileTextures[2] = new Texture("X.png");
		}
		sprite = new Sprite(tileTextures[tileType.ordinal()]);
		sprite.setPosition(x * width, y * height);
	}

	/**
	 * @return the tileType
	 */
	public TileType getTileType() {
		return tileType;
	}

	/**
	 * @param tileType
	 *            the tileType to set
	 */
	public void setTileType(TileType tileType) {
		this.tileType = tileType;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	public void Draw(SpriteBatch sb) {
		sprite.draw(sb);
	}
}
