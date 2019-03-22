package com.groupproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMain {

	private SpriteBatch mSpriteBatch;
	private Logic mLogic;
	
	public GameMain() {
		mSpriteBatch = new SpriteBatch();
		mLogic = new Logic();
	}

	public void Update() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//			sprite.translateX(-10);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//			sprite.translateY(10);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//			sprite.translateX(10);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//			sprite.translateY(-10);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			// shoot bullet
		}

	}

	public void Draw() {
		mSpriteBatch.begin();
		mLogic.drawMap(mSpriteBatch);
		mSpriteBatch.end();
	}

	public void Control() {
		
	}

	public void dispose() {
		mSpriteBatch.dispose();
//		img.dispose();
	}
}
