package com.example.downloadsong;


import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Player extends ActionBarActivity {
	//Carrega a classe
	MediaPlayer mp = new MediaPlayer();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		
		try {

			//No emulador adicionei a mp3 no sdcard pelo eclipse android explorer
			String path = getIntent().getStringExtra("path");
			//limpando o tocador, como se tivesse criado agora
			mp.reset();
			//colocando o caminho onde esta a mp3
			mp.setDataSource(path);
			//Se tudo ocorrer bem, muda o estado do objeto para preparado
			mp.prepare();
			// inicia a musica
			mp.start();
			//Quando a musica terminar invoca esse metodo
			mp.setOnCompletionListener(new OnCompletionListener() {

				public void onCompletion(MediaPlayer arg0) {
					System.out.println("Acabou a musica");

				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		setListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player, menu);
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
	
	public void setListeners() {
		Button btPlayer = (Button) findViewById(R.id.play);
		btPlayer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mp.start();
			}
		});
		
		Button btPause = (Button) findViewById(R.id.pause);
		btPause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mp.pause();
			}
		});
	
		Button btStop = (Button) findViewById(R.id.stop);
		btStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mp.stop();
			}
		});
		
	}
		
}
