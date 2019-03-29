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
	private GameClientService mGCS;
	private Player mMainPlayer;
	private Player[] mOtherPlayers;
	private Timer mTimedSender;
	private Timer mTimedReceiver;
	private BulletManager mBM;

	private float elapsedtime;
	private float timeStep;

	private boolean canShoot = true;

	public Logic() {
		elapsedtime = Gdx.graphics.getDeltaTime();
		timeStep = 60.f / 60.f;

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
		mBM = new BulletManager(mGCS);
		mTimedSender = new Timer();
		mTimedReceiver = new Timer();
		long delay = 1 * 1000;
		long period = 1 * 10;

		//Send Player
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
				msg.toMessage(new JSONObject(receivedMsg));
				mBM.addToOthersBullets(new JSONArray(msg.getBody()));
			}

			private void getAllPlayers() {

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

		mBM.draw(mSpriteBatch);
	}

	public void UpdatePlayer() {
		bulletUpdate();
		mBM.update();
		mMainPlayer.Update();
	}

	private void bulletUpdate() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void bulletUpdate()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	void
		//
		// Synopsis				:   
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Mar 29, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		if (!canShoot && elapsedtime > timeStep) {

			//			while (elapsedtime >= timeStep) {
			//				elapsedtime = elapsedtime - timeStep;
			//			}
			//			if (elapsedtime >= timeStep) {
			//				elapsedtime = 0;
			//			}
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
