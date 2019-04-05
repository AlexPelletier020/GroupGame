package com.groupproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import base.Player;

public class GameMain {

	private SpriteBatch mSpriteBatch;
	private Logic mLogic;
	private BitmapFont font = new BitmapFont();
	private String[] topThreeChar;

	public GameMain(String ipAddress, String port, String playerName) {
		mSpriteBatch = new SpriteBatch();
		mLogic = new Logic(ipAddress, port, playerName);
	}

	public void Update() {
		mLogic.UpdatePlayer();

	}

	public void Draw() {
		mSpriteBatch.begin();

		if (mLogic.isGameOver) {
			topThreeChar = mLogic.mTop3Players.split(" ");
			font.draw(mSpriteBatch, "Player " + resolvePlayer(topThreeChar[0]) + ": " + topThreeChar[1], 985, 700);
			font.draw(mSpriteBatch, "Player " + resolvePlayer(topThreeChar[2]) + ": " + topThreeChar[3], 985, 600);
			font.draw(mSpriteBatch, "Player " + resolvePlayer(topThreeChar[4]) + ": " + topThreeChar[5], 985, 500);

			//Draw game over screen

		} else {
			mLogic.drawMap(mSpriteBatch);
			mLogic.drawPlayer(mSpriteBatch);
		}

		mSpriteBatch.end();
	}

	private String resolvePlayer(String winnerID) {
		if (mLogic.getMainPlayer().getId() == Integer.parseInt(winnerID))
			return mLogic.getMainPlayer().getName();
		
		Player[] otherPlayers = mLogic.getOtherPlayers();
		for(Player player: otherPlayers) {
			if(player.getId() == Integer.parseInt(winnerID)) {
				return player.getName();
			}
		}
		
		return "Someone";
	}

	public void Control() {
		//control input here
	}

	public void dispose() {
		mSpriteBatch.dispose();
		// img.dispose();
	}

	private class MyTextInputListener implements TextInputListener {

		private String finalText;

		@Override
		public void input(String text) {
			finalText = text;
		}

		public String getText() {
			return finalText;
		}

		@Override
		public void canceled() {
		}
	}
}
