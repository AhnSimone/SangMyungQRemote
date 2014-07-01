package com.sangmyung.dimi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SocketManager extends AsyncTask<String, Void, Void> {
	
	private final String mobile = "MO";
	
	private final String TAG = "SocketManager";
	
	private Socket _Socket;
	private BufferedReader in;
	private PrintWriter out;
	
	private String IP ="192.168.1.101";
	private int PORT =9001;
	
	private int i_qrkey;
	
	Context context;
	public SocketManager(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	public Socket getSocket()
	{
		return _Socket;
	}
	public int getIQrkey()
	{
		return this.i_qrkey;
	}
	public BufferedReader getReader()
	{
		return this.in;
	}
	public PrintWriter getWriter()
	{
		return this.out;
	}
	
	
	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		String data = params[0];
		
		try{
			_Socket = new Socket(IP, PORT);
			in = new BufferedReader(new InputStreamReader(
					_Socket.getInputStream()));
			out = new PrintWriter(_Socket.getOutputStream(), true);
			
			out.println(mobile +data);
			
			String line = in.readLine();
			Log.e(TAG, "pc��?" + line);
			
			i_qrkey = Integer.parseInt(data);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
