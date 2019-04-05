package com.groupproject.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Mainclass extends ApplicationAdapter {

	private GameMain mGm;
	private String mIPAddress;
	private String mPort;
	private String mPlayerName;

	public Mainclass(String ipAddress, String port, String playerName) {
		mIPAddress = ipAddress;
		mPort = port;
		mPlayerName = playerName;
	}

	@Override
	public void create() {
		mGm = new GameMain(mIPAddress, mPort, mPlayerName);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mGm.Update();

		mGm.Draw();
	}

	@Override
	public void dispose() {
		mGm.dispose();
	}

}
