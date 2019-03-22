package com.groupproject.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMain {

	private SpriteBatch mSpriteBatch;
	private Logic mLogic;

	public GameMain() {
		mSpriteBatch = new SpriteBatch();
		mLogic = new Logic();

	}

	public void Update() {
		mLogic.UpdatePlayer();

	}

	public void Draw() {
		mSpriteBatch.begin();

		mLogic.drawMap(mSpriteBatch);
		mLogic.drawPlayer(mSpriteBatch);
		mSpriteBatch.end();
	}

	public void Control() {

	}

	public void dispose() {
		mSpriteBatch.dispose();
		// img.dispose();
	}
}
