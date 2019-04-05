package com.groupproject.game;

import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMain {

	private SpriteBatch mSpriteBatch;
	private Logic mLogic;

	public GameMain() {
		mSpriteBatch = new SpriteBatch();

		mLogic = new Logic("10.153.64.66", "6066");

	}

	public void Update() {
		mLogic.UpdatePlayer();

	}

	public void Draw() {
		mSpriteBatch.begin();
		if(mLogic.isGameOver)
		{
			//Draw game over screen
			
		}
		else {
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
