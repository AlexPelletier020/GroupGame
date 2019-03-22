package com.groupproject.game;

import java.net.URL;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupproject.util.FileUtilities;
import com.groupproject.util.TileType;

import base.Tile;

public class Logic {

	private Tile[][] mMap;

	public Logic() {

		initMap();
	}

	private void initMap() {
		URL url = this.getClass().getResource("/map.txt");
		String mapPath = url.getPath();

		char[][] charMap = FileUtilities.readLeaderBoardFile(mapPath);

		mMap = new Tile[charMap.length][charMap[0].length];

		int col;
		for (int row = 0; row < mMap.length; row++) {
			for (col = 0; col < mMap[0].length; col++) {
				mMap[row][col] = new Tile(row, col, TileType.fromInteger(Integer.parseInt(charMap[row][col] + "")));
			}
		}
	}

	public void drawMap(SpriteBatch mSpriteBatch) {

		int col;
		for (int row = 0; row < mMap.length; row++) {
			for (col = 0; col < mMap[0].length; col++) {
				mMap[row][col].Draw(mSpriteBatch);
			}
		}

	}
}
