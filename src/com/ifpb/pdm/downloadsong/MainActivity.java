package com.ifpb.pdm.downloadsong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.example.downloadsong.Player;
import com.example.downloadsong.R;


public class MainActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ExecuteDownload executeDownload = new ExecuteDownload(MainActivity.this, "http://10f.b.sscdn.co/f/c/2/1/espectromusic-pharrell-williams-happy-myfreemp3eu-6111b6.mp3");
		String path = executeDownload.doInBackground("test");
		Log.e("Path ", path);
		Intent intent = new Intent(MainActivity.this, Player.class);
		intent.putExtra("path",path);
		startActivity(intent);
	}
	
//	public void updateProgress(String p) {
//		TextView progress = (TextView) findViewById(R.id.hello);
//		progress.setText(p);
//	}

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
}
