package com.groupproject.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Mainclass extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private Sprite sprite;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		sprite = new Sprite(img);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			sprite.translateX(-10);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			sprite.translateY(10);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			sprite.translateX(10);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
		{
			sprite.translateY(-10);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
		{
			//shoot bullet
		}
		batch.begin();
		sprite.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
