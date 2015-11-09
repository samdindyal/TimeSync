import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.Socket;
import java.net.ServerSocket;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

public class TimeSyncServerRuntime {

	private String clientInput;
	private Calendar calendar;
	private ServerSocket serverSocket;
	private Socket connectionSocket;
	private DataOutputStream outputStream;
	private BufferedReader inputReader;
	private Thread syncThread;


	public TimeSyncServerRuntime()
	{
		clientInput = "";
		calendar = new GregorianCalendar();

		try{
			serverSocket = new ServerSocket(TimeSyncLibrary.TCP_SERVER_SOCKET);	
		}catch (Exception e){e .printStackTrace();}
		
		while(true)
		{
			(syncThread = new Thread(new Runnable(){
				@Override
				public void run()
				{
					start();
				}
			})).start();

			try {
				syncThread.join();
			}catch (Exception e){
				e.printStackTrace();
			}
			
		}
			
	}

	private void listen()
	{
		try {
			connect();
			clientInput = inputReader.readLine();
			System.out.println("REQUEST: " + clientInput);
		}catch (Exception e){e.printStackTrace();}
	}


	private void connect ()
	{
		try {
				connectionSocket = serverSocket.accept();
				outputStream = new DataOutputStream(connectionSocket.getOutputStream());
				inputReader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			} catch(Exception e){e.printStackTrace();}
	}

	public void start()
	{
		System.out.println("Server started.");
		try {
				listen();
				connect();

				respond(clientInput);
				connectionSocket.close();

				}catch(Exception e)
				{
					e.printStackTrace();
				};		
	}

	private void respond(String str)
	{
		try {
			if (str.equals("DATE"))
				outputStream.writeBytes("DATE:" + getDate());
			else if (str.equals("TIME"))
				outputStream.writeBytes("TIME:" + getTime());
			else if (str.equals("DATE_AND_TIME"))
				outputStream.writeBytes("DATE_AND_TIME:" + getDate() + "," + getTime());
				
		}catch(Exception e)
		{
			e.printStackTrace();
		};
	}


	public String getTime() 
	{
		calendar = new GregorianCalendar();
		return TimeSyncLibrary.pad(((calendar.get(Calendar.AM_PM) == Calendar.AM) ? 0 : 12 )+ calendar.get(Calendar.HOUR)) + ":"
		+ TimeSyncLibrary.pad(calendar.get(Calendar.MINUTE)) + ":"
		+ TimeSyncLibrary.pad(calendar.get(Calendar.SECOND));
	}

	public String getDate() 
	{
		calendar = new GregorianCalendar();
		return calendar.get(Calendar.MONTH) + "/"
		+ calendar.get(Calendar.DAY_OF_MONTH) + "/"
		+ calendar.get(Calendar.YEAR);
	}
}