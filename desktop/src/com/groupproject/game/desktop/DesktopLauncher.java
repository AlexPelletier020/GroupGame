package com.groupproject.game.desktop;

import javax.swing.JOptionPane;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.groupproject.game.Mainclass;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 1920;
		config.height = 1080;
		config.fullscreen = true;

		String ipAddress = JOptionPane.showInputDialog(null, "What is your IP Address? Default = 10.153.64.100", "10.153.64.100");
		if (null == ipAddress || ipAddress.trim().length() == 0) {
			ipAddress = "10.153.64.100";
		}
		String port = JOptionPane.showInputDialog(null, "What is your Port# ? Default = 6066", "6066");
		if (null == port || port.trim().length() == 0) {
			port = "6066";
		}
		String playerName = JOptionPane.showInputDialog(null, "What is your Name? Default = PotatoMan", "PotatoMan");
		if (null == playerName || playerName.trim().length() == 0) {
			playerName = "PotatoMan";
		}
		new LwjglApplication(new Mainclass(ipAddress, port, playerName), config);
	}
}
