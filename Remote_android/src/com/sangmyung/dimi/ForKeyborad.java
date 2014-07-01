package com.sangmyung.dimi;

import java.io.PrintWriter;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ForKeyborad extends Fragment implements OnTouchListener {
	
	private int m_x =0;
	private int m_y =0;
	private SocketManager socketManager;
	int i;
	PrintWriter out;
	public ForKeyborad(SocketManager socketManager) {
		// TODO Auto-generated constructor stub
		this.socketManager = socketManager;
		
		i = socketManager.getIQrkey();
		out = socketManager.getWriter();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v  = inflater.inflate(R.layout.activity_keyborad, container, false);
		
		ImageView touch_pad = (ImageView)v.findViewById(R.id.touch_pad);
		touch_pad.setOnTouchListener(this);
		
		return v;
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		//event 터치 무브 시 움직임
			int x = (int)event.getX();
			int y = (int)event.getY();
			
			switch(v.getId())
			{
			case R.id.touch_pad:
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					m_x = (int)event.getX();
					m_y =(int)event.getY();
//					out.println(str); 다운일때 따로 보내줌
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
