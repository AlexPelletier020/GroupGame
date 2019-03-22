package com.groupproject.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Mohammed Al-Safwan
 * @date Jan 31, 2019
 */
public class GameClientService {

	private String mServerName;
	private int mPort;

	public GameClientService() {
		mServerName = "127.0.0.1"; //Default IP Address and port
		mPort = 6066;
	}

	public GameClientService(String serverName, int port) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	public DrawService(String serverName, int port) 
		//
		// Method parameters	:	serverName - The IP Address to connect to
		//										port - The port to connect to the given IP Address
		// Method return		:	
		//
		// Synopsis				:    A constructor
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 17, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		mServerName = serverName;
		mPort = port;
	}

	public void setServerName(String serverName) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	public void setServerName(String serverName)
		//
		// Method parameters	:	
		//
		// Method return		:	void
		//
		// Synopsis				:   set the server IP Address
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 17, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		mServerName = serverName;
	}

	public void setPort(int port) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	public void setPort(int port)
		//
		// Method parameters	:	port - the port to be connect to the server
		//
		// Method return		:	void
		//
		// Synopsis				:   return a list of all the lines that should be drawn
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 17, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		mPort = port;
	}

	public String sendRequest(String newMsg) {

		String response = "";

		Socket client = null;

		InputStream inFromServer = null;
		DataInputStream in = null;
		OutputStream outToServer = null;
		DataOutputStream out = null;

		try {

			client = new Socket(mServerName, mPort);

			inFromServer = client.getInputStream();
			in = new DataInputStream(inFromServer);
			outToServer = client.getOutputStream();
			out = new DataOutputStream(outToServer);

			System.out.println("Sending:");
			System.out.println(newMsg.toString());
			out.writeUTF(newMsg.toString());

			System.out.println("Trying to read from the input stream");
			response = in.readUTF();
			System.out.println(response);
			System.out.println("Read inputStream");

			//			InputStream inFromServer = client.getInputStream();
			//			DataInputStream in = new DataInputStream(inFromServer);
			//			System.out.println("Server says " + in.readUTF());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.flush();
				out.close();
				outToServer.flush();
				outToServer.close();
				client.close();
			} catch (NullPointerException e1) {
				System.out.println("Client object is null");
				//e1.printStackTrace();
			} catch (IOException e) {
				System.out.println("Client couldn't flush correctly");
				e.printStackTrace();
			}
		}
		return response;
	}
}
