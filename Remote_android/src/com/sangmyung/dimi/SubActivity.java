package com.sangmyung.dimi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class SubActivity extends Activity implements OnTouchListener {

	private final String mobile = "MO";

	private BufferedReader in;
	private PrintWriter out;

	Context context;
	Socket socket;
	int m_x =0;
	int m_y =0;
	private ImageView pad;
	int i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_mouse);

		context = this;

		Intent intent = getIntent();

		String qrKey = intent.getExtras().get("qrKey").toString();

		new Setting_Socket().execute(qrKey);

		pad = (ImageView) findViewById(R.id.touch_pad);
		pad.setOnTouchListener(this);

	}

	private class Setting_Socket extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Background 작업을 진행
			// onPreExecute() Backgroud 작업 시작전에 UI 작업을 진행한다.
			// onPostExecute() Backgroud 작업이 끝난후 UI 작업을 진행한다.
			// onPre -> doIn -> onPost
			String data = params[0];
			try {
				Log.e("TAG", "test");
				socket = new Socket("192.168.1.100", 9001);
				Log.e("TAG", "test2");

				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);

				out.println(mobile + data);// QR 코드를 입력하면 데이터를 받아서 이것을 실행
				
				String line = in.readLine();
				Log.e("TAG", "서버와 모바일 연결되었습니다.  " + line);
				i = Integer.parseInt(data);
				// while(true)
				// {
				// out.println(i); //pc로 보내보자!
				// }

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(context, "PC의 프로그램을 확인해주세요", 200).show();
				e.printStackTrace();
			}
			return null;
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		//event 터치 무브 시 움직임
		int x = (int)event.getX();
		int y = (int)event.getY();
		
		int d_x = 0;
		int d_y = 0;
		switch(v.getId())
		{
		case R.id.touch_pad:
			if(event.getAction() == MotionEvent.ACTION_DOWN)
			{
				m_x = (int)event.getX();
				m_y =(int)event.getY();
//				out.println(str); 다운일때 따로 보내줌
			}
	
			if(event.getAction() == MotionEvent.ACTION_MOVE)
			{
				Log.e("TAG", (int)event.getX() + "   " + (int)event.getY() +  "   " +  m_x + "   " + m_y);
				x = (int)event.getX()-m_x;
				y =(int)event.getY()-m_y;
				
				m_x = (int)event.getX();
				m_y = (int)event.getY();
				
				String str = i + "mouse" + "x"+x+"y"+y+"w"+v.getWidth()+"h"+v.getHeight();
				out.println(str);
			}
		
		break;
		}
		return true;
	}

}
