package com.groupproject.game;

import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupproject.util.Directions;
import com.groupproject.util.FileUtilities;
import com.groupproject.util.GameClientService;
import com.groupproject.util.MessageType;
import com.groupproject.util.TileType;

import base.Message;
import base.Player;
import base.Position;
import base.Tile;

public class Logic {

	public static Tile[][] mMap;
	private GameClientService mGCS;
	private Player mMainPlayer;
	private Timer mTimedSender;
	private Timer mTimedReciever;

	public Logic() {

		initMap();
		mGCS = new GameClientService();
		mMainPlayer = new Player(0, new Position(4 * 24.0f, 4 * 24.0f, Directions.NORTH), "Potato");

		new Thread(() -> {
			Message msg = new Message();
			msg.setType(MessageType.ADD_PLAYER);
			
			msg.toMessage(new JSONObject(mGCS.sendRequest(msg.toString())));
			mMainPlayer.setId(Integer.parseInt(msg.getBody()));
			
			System.out.println("Player ID = " + mMainPlayer.getId());
		}).start();
		mMainPlayer = new Player(0, new Position(4 * 24.0f, 4 * 24.0f, Directions.NORTH), "Potato");


		mTimedSender = new Timer();
//		mTimedSender.scheduleAtFixedRate(new TimerTask() {
//			@Override
//			public void run() {
//				mGCS.sendRequest(mMainPlayer.toString());
//			}
//		}, 0, 1 * 500);
	}

	private void initMap() {
		URL url = this.getClass().getResource("/map.txt");
		String mapPath = url.getPath();

		char[][] charMap = FileUtilities.readLeaderBoardFile(mapPath);

		mMap = new Tile[charMap.length][charMap[0].length];

		int col;
		for (int row = 0; row < mMap.length; row++) {
			for (col = 0; col < mMap[0].length; col++) {
				mMap[col][row] = new Tile(col, row, TileType.fromInteger(Integer.parseInt(charMap[col][row] + "")));
			}
		}
	}

	public void drawMap(SpriteBatch mSpriteBatch) {

		int col;
		for (int row = 0; row < mMap.length; row++) {
			for (col = 0; col < mMap[0].length; col++) {
				mMap[col][row].Draw(mSpriteBatch);
			}
		}

	}

	public void drawPlayer(SpriteBatch mSpriteBatch) {
		mMainPlayer.Draw(mSpriteBatch);
	}

	public void UpdatePlayer() {
		mMainPlayer.Update();

	}
}
