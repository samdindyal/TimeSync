/**
	Title: The "TimeSyncRuntime" class
	Date Written: November 2015
	Author: Samuel Dindyal, Balin Banh, Danel Tran
	Description: The runtime for the client side of TimeSync.
*/

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.DataOutputStream;

import java.net.Socket;
import java.net.ServerSocket;

import java.util.Calendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeSyncRuntime
{
	private Calendar calendar;
	private String runtimeID, serverInput, location, time, date, id, inputCode;
	private ActionListener listener;
	private boolean running, isConnected;

	private Socket clientSocket;
	private BufferedReader inputFromServer;
	private DataOutputStream outputToServer;
	private Thread syncThread;
	
	public TimeSyncRuntime(String location){

		runtimeID 		= "";
		serverInput		= "";
		this.location 	= location;
		calendar 		= Calendar.getInstance();
		running			= false;
	}

	public void sync(String str)
	{
		(syncThread = new Thread(new Runnable() {
			@Override
			public void run(){
				connect();
			try{
				System.out.println("REQUESTING: "  + str);
				outputToServer.writeBytes(str);
				clientSocket.close();
			}catch(Exception e){e.printStackTrace();}

			connect();
		
			try {
				listen();
			}catch(Exception e){e.printStackTrace();}


			if (serverInput.startsWith("DATE:"))
				date = serverInput.substring(5);
			else if (serverInput.startsWith("TIME:"))
				time = serverInput.substring(5);
			else if (serverInput.startsWith("DATE_AND_TIME"))
			{
				String[] temp = serverInput.substring(14).split(",");
				date = temp[0];
				time = temp[1];
			}
			}
		})).start();

		try {
			syncThread.join();
			System.out.println("SERVER RESPONSE: " + serverInput);
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "", System.currentTimeMillis(), 0));
		}catch(Exception e){e.printStackTrace();}
		
	}

	private void listen() {
		try {	
			serverInput = inputFromServer.readLine();
		}catch(Exception e){e.printStackTrace();}
		
	}

	private void connect() {
		try {
			clientSocket = new Socket(location, TimeSyncLibrary.TCP_SERVER_SOCKET);
			inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outputToServer = new DataOutputStream(clientSocket.getOutputStream());
		} catch (Exception e){e.printStackTrace();}
	}



	public boolean isRunning()
	{
		return running;
	}

	public void addActionListener(ActionListener listener)
	{
		this.listener = listener;
	}


	public String getTime()
	{
		return time;
	}

	public String getDate()
	{
		return date;
	}

}