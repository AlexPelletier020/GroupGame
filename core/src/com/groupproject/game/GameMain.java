package com.groupproject.game;

import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMain {

	private SpriteBatch mSpriteBatch;
	private Logic mLogic;
	private BitmapFont font = new BitmapFont();
	private String[] topThreeChar;

	public GameMain() {
		mSpriteBatch = new SpriteBatch();

		mLogic = new Logic("10.153.64.100", "6066");

	}

	public void Update() {
		mLogic.UpdatePlayer();

	}

	public void Draw() {
		mSpriteBatch.begin();

		if (mLogic.isGameOver) {
			topThreeChar = mLogic.mTop3Players.split(" ");
			font.draw(mSpriteBatch, "Player " + topThreeChar[0] + ": " + topThreeChar[1], 985, 700);
			font.draw(mSpriteBatch, "Player " + topThreeChar[2] + ": " + topThreeChar[3], 985, 600);
			font.draw(mSpriteBatch, "Player " + topThreeChar[4] + ": " + topThreeChar[5], 985, 500);

			//Draw game over screen

		} else {
			mLogic.drawMap(mSpriteBatch);
			mLogic.drawPlayer(mSpriteBatch);
		}

		mSpriteBatch.end();
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
