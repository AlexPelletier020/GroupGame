package com.groupproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMain {

	private SpriteBatch mSpriteBatch;
	private Logic mLogic;

	public GameMain() {
		mSpriteBatch = new SpriteBatch();
		//		mLogic = new Logic(JOptionPane.showInputDialog("Enter IP:","10.153.64.66"),
		//				JOptionPane.showInputDialog("Port:","6066"));
		//		String tempIP;
		//		String tempPort;
		//		MyTextInputListener listener = new MyTextInputListener();
		//		Gdx.input.getTextInput(listener, "Enter IP", "10.153.64.66", "");
		//		tempIP = listener.getText();
		//		Gdx.input.getTextInput(listener, "Enter Port", "6066", "");
		//		tempPort = listener.getText();
		mLogic = new Logic("10.153.64.66", "6066");

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
