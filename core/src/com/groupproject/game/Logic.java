package com.groupproject.game;

import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groupproject.util.Directions;
import com.groupproject.util.FileUtilities;
import com.groupproject.util.GameClientService;
import com.groupproject.util.MessageType;
import com.groupproject.util.TileType;

import base.Bullet;
import base.BulletManager;
import base.Message;
import base.Player;
import base.Position;
import base.Tile;

public class Logic {

	public static Tile[][] mMap;
	public boolean isGameOver = false;
	public String mTop3Players;
	
	private GameClientService mGCS;
	private Player mMainPlayer;
	private Player[] mOtherPlayers;
	private Timer mTimedSender;
	private Timer mTimedReceiver;
	private BulletManager mBM;

	private float elapsedtime;
	private float timeStep;

	private boolean canShoot = true;

	public Logic(String serverIP, String port) {
		System.out.println("The name of the main thread is >>>>>> " + Thread.currentThread().getName());
		elapsedtime = Gdx.graphics.getDeltaTime();
		timeStep = 60.f / 60.f;

		initMap();
		mGCS = new GameClientService(serverIP, Integer.parseInt(port));
		mMainPlayer = new Player(0, new Position(3 * 24.0f, 3 * 24.0f, Directions.NORTH), "Potato");

		new Thread(() -> {
			Message msg = new Message();
			msg.setType(MessageType.ADD_PLAYER);

			msg.toMessage(new JSONObject(mGCS.sendRequest(msg.toString())));
			mMainPlayer.setId(Integer.parseInt(msg.getBody()));
		}).start();

		mOtherPlayers = new Player[7];
		mBM = new BulletManager(mGCS);
		mTimedSender = new Timer();
		mTimedReceiver = new Timer();
		long delay = 1 * 1000;
		long period = 1 * 10;
		long lag = 1 * 5;

		//Send Player
		mTimedSender.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.setType(MessageType.UPDATE_PLAYER);
				msg.setSender(mMainPlayer.getId() + "");
				msg.setBody(mMainPlayer.toString());

				String receivedMsg = mGCS.sendRequest(msg.toString());
				JSONObject jRecivedMsg = new JSONObject(receivedMsg);
				msg.toMessage(jRecivedMsg);

				//return and end the game
				if (msg.getType() == MessageType.GAME_OVER) {
					isGameOver = true;
					mTop3Players = msg.getBody();
					return;
				}
			}
		}, delay, period);

		//Receive Everything
		mTimedReceiver.schedule(new TimerTask() {

			@Override
			public void run() {
				getAllPlayers();
				getAllBullets();
			}

			private void getAllBullets() {
				Message msg = new Message();
				msg.setType(MessageType.RECEIVE_BULLETS);
				msg.setSender(mMainPlayer.getId() + "");
				msg.setBody(mBM.mLastBulletRecievedDate.getTime() + "");

				String receivedMsg = mGCS.sendRequest(msg.toString());
				JSONObject jRecivedMsg = new JSONObject(receivedMsg);
				msg.toMessage(jRecivedMsg);

				//return and end the game
				if (msg.getType() == MessageType.GAME_OVER) {
					isGameOver = true;
					mTop3Players = msg.getBody();
					return;
				}

				mBM.addToOthersBullets(new JSONArray(msg.getBody()));
			}

			private void getAllPlayers() {

				Message msg = new Message();
				msg.setType(MessageType.RECEIVE_PLAYERS);
				msg.setSender(mMainPlayer.getId() + "");
				msg.setBody("");

				String receivedMsg = mGCS.sendRequest(msg.toString());
				JSONObject jRecivedMsg = new JSONObject(receivedMsg);
				msg.toMessage(jRecivedMsg);

				//return and end the game
				if (msg.getType() == MessageType.GAME_OVER) {
					isGameOver = true;
					mTop3Players = msg.getBody();
					return;
				}

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
		}, delay + lag, period);
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

	public void drawPlayer(SpriteBatch mSpriteBatch) {
		mMainPlayer.Draw(mSpriteBatch);
		for (Player otherPlayer : mOtherPlayers) {
			if (null != otherPlayer) {
				otherPlayer.Draw(mSpriteBatch);
			}
		}

		mBM.draw(mSpriteBatch);
	}

	public void UpdatePlayer() {
		bulletUpdate();
		mMainPlayer.Update();
		mBM.update(mMainPlayer, mOtherPlayers);

	}

	private void bulletUpdate() {
		if (!canShoot && elapsedtime > timeStep) {

			//Do your thing
			canShoot = true;
		} else {
			elapsedtime += Gdx.graphics.getDeltaTime();
		}

		if (canShoot && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			Bullet newBullet = new Bullet(mMainPlayer.getId(), mMainPlayer.getPosition().clone());
			newBullet.getPosition().setSpeed(15);
			mBM.addToMyBullet(newBullet);
			canShoot = false;
			elapsedtime = 0;
		}
	}

}
