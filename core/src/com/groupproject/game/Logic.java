package com.groupproject.game;

import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
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
	private Player[] mOtherPlayers;
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
		}).start();

		mOtherPlayers = new Player[7];

		mTimedSender = new Timer();
		mTimedReciever = new Timer();
		long delay = 1 * 1000;
		long period = 1 * 5;

		mTimedSender.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.setType(MessageType.UPDATE_PLAYER);
				msg.setSender(mMainPlayer.getId() + "");
				msg.setBody(mMainPlayer.toString());

				mGCS.sendRequest(msg.toString());
			}
		}, delay, period);

		mTimedReciever.schedule(new TimerTask() {

			@Override
			public void run() {
				Message msg = new Message();
				msg.setType(MessageType.RECEIVE_PLAYERS);
				msg.setSender(mMainPlayer.getId() + "");
				msg.setBody("");
				String receivedMsg = mGCS.sendRequest(msg.toString());

				msg.toMessage(new JSONObject(receivedMsg));

				JSONArray receivedPlayers = new JSONArray(msg.getBody());

				for (int index = 0; index < receivedPlayers.length(); index++) {
					Player newPlayer = new Player();
					newPlayer.toPlayer((JSONObject) receivedPlayers.get(index));
					if (null == mOtherPlayers[newPlayer.getId()]) {
						mOtherPlayers[newPlayer.getId()] = new Player();
					} else {
						mOtherPlayers[newPlayer.getId()].setFromOtherPlayer(newPlayer);
					}
				}
			}
		}, delay, period);
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
		for (Player otherPlayer : mOtherPlayers) {
			if (null != otherPlayer) {
				otherPlayer.Draw(mSpriteBatch);
			}
		}
	}

	public void UpdatePlayer() {
		mMainPlayer.Update();
	}
}
