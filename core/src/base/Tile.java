package base;

import com.groupproject.util.TileType;

public class Tile {

	private TileType tileType; //Replace with TileType var type
	private float x, y;
	private float width, height;
	
	public Tile() {
		this.x = -1;
		this.y = -1;
		this.tileType = tileType.NULL;
		this.width = -1;
		this.height = -1;
	}

	public Tile(float x, float y, TileType tileType) {
		this.x = x;
		this.y = y;
		this.tileType = tileType;
		this.width = 20;
		this.height = 20;
	}
	
	public Tile(float x, float y, float width, float height, TileType tileType) {
		this.x = x;
		this.y = y;
		this.tileType = tileType;
		this.width = width;
		this.height = height;
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
}
