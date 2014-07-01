package com.sangmyung.dimi;

import java.io.PrintWriter;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ForMouse extends Fragment implements OnTouchListener {

	private int m_x = 0;
	private int m_y = 0;

	private SocketManager socketManager;
	private int QR_NUMBER;
	private int TOUCH_NUM = 0;
	PrintWriter out;

	public ForMouse(SocketManager socketManager) {
		// TODO Auto-generated constructor stub
		this.socketManager = socketManager;

		QR_NUMBER = socketManager.getIQrkey();
		out = socketManager.getWriter();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.activity_mouse, container, false);

		ImageView touch_pad = (ImageView) v.findViewById(R.id.touch_pad);
		touch_pad.setOnTouchListener(this);
		
		touch_pad.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				String str = QR_NUMBER + "mouse" + "t" + "right" + "q";
				
				out.println(str);
				return false;
			}
		});

		return v;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		int x = (int) event.getX();
		int y = (int) event.getY();
		
		int d_x =0;
		int d_y = 0;

		switch (v.getId()) {
		case R.id.touch_pad:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				m_x = (int) event.getX();
				m_y = (int) event.getY();
				
				d_x= (int) event.getX();
				d_y = (int) event.getY();
				TOUCH_NUM++;
				Log.e("mo", TOUCH_NUM + "   DOWN");
			}
			if(event.getAction() == MotionEvent.ACTION_POINTER_UP)
			{
				Log.e("mo", TOUCH_NUM + "   two");
				String str = QR_NUMBER + "mouse" + "t" + "right" + "q";
				
				out.println(str);
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				TOUCH_NUM++;
				Log.e("mo", TOUCH_NUM + "    UP");
				if (TOUCH_NUM==3 || TOUCH_NUM ==4) {
					String str = QR_NUMBER + "mouse" + "t" + "left" + "q";
					
					out.println(str);
				}
				TOUCH_NUM =0;
			}
		
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				TOUCH_NUM ++;
				Log.e("TAG", (int) event.getX() + "   " + (int) event.getY()
						+ "   " + m_x + "   " + m_y);
				x = (int) event.getX() - m_x;
				y = (int) event.getY() - m_y;

				m_x = (int) event.getX();
				m_y = (int) event.getY();

				String str = QR_NUMBER + "mouse" + "t" + "move" + "q" + "x" + x
						+ "y" + y + "w" + v.getWidth() + "h" + v.getHeight();
				out.println(str);
			}

			break;
		}
		return true;
	}

}
