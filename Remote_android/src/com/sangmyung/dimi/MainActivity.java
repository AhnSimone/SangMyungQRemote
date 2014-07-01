package com.sangmyung.dimi;

import java.io.PrintWriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.client.android.integration.IntentIntegrator;
import com.google.zxing.client.android.integration.IntentResult;
import com.sangmyung.dimi.scroll.CustomHorizontalScrollView;

public class MainActivity extends Activity implements OnClickListener {
	
	private final String TAG ="MAIN";
	private final String mobile = "MO";
	
	private boolean _Flag = false;
	
	private boolean _MenuOnOff = false;
	
	PrintWriter out;
	
	private SocketManager _SocketManager;
	
	private CustomHorizontalScrollView _ScrollView;
	
	private Handler _KillHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				_Flag = false;
			}
		}
	};
	
	private void _InitUI()
	{
		View _MousePad;
		View _RemotePad;

		Button btn_mouse;
		Button btn_keyborad;
		
		btn_mouse = (Button)findViewById(R.id.My_go_mouse);
		btn_mouse.setOnClickListener(this);
		
		btn_keyborad = (Button) findViewById(R.id.My_go_Keyborad);
		btn_keyborad.setOnClickListener(this);
		
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		if(_SocketManager == null)
		{
			IntentIntegrator.initiateScan(MainActivity.this);
		}
		_InitUI();
	
	}
	 public void fragmentReplace(android.app.Fragment newFragment) {
		 
	        final android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
	        transaction.replace(R.id.container, newFragment);
	        transaction.commit();
	    }
	 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
				IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
				//Toast.makeText(getApplicationContext(), result.getContents(), 300).show();
				
				_SocketManager = new SocketManager(MainActivity.this);
				_SocketManager.execute(result.getContents());
				
//				Intent intentSubActivity = 
//						new Intent(MainActivity.this, SubActivity.class);
//				
//				intentSubActivity.putExtra("qrKey",  result.getContents());
//				startActivity(intentSubActivity);
				
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId())
		{
		case R.id.My_go_mouse:
//			fragmentReplace(new ForMouse(socketManager));
			break;
		case R.id.My_go_Keyborad:
			fragmentReplace(new ForKeyborad(_SocketManager));
			break;
		}
	}
	
	
	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {

			if (KeyCode == KeyEvent.KEYCODE_BACK) {
					if (!_Flag) {
						Toast.makeText(MainActivity.this, "on more back", Toast.LENGTH_SHORT)
								.show();
						_Flag = true;
						_KillHandler.sendEmptyMessageDelayed(0, 2000);
						return false;
					}
					else {
						android.os.Process.killProcess(android.os.Process.myPid());
					}
				}
		}
		return super.onKeyDown(KeyCode, event);
	}

}
