package com.ifpb.pdm.downloadsong;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.downloadsong.Player;

public class ExecuteDownload extends AsyncTask<String, Void, String> {
    ProgressDialog mProgressDialog;

    Context context;
    String urlDownload;

    public ExecuteDownload(Context context,String url) {
        this.context = context;
        this.urlDownload=url;
    }

    protected void onPreExecute() {
        mProgressDialog = new ProgressDialog(context);//.show(context, "","Please wait, Download for (%i) " + urlDownload );
        mProgressDialog.setMax(100);
        //mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        Log.v("DOWNLOAD", "Wait for downloading url : " + urlDownload);
    }

    protected String doInBackground(String... params){
        try 
        {
            URL url = new URL(urlDownload);

            Log.w( "DOWNLOAD" , "URL TO CALL : " + url.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //set up some things on the connection
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            File folder = new File(Environment.getExternalStorageDirectory()+ "/MyDownloaded/") ;

            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }

            File file = new File(folder,"song.mp3");

            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file
            int totalSize = urlConnection.getContentLength();
            //variable to store total downloaded bytes
            int downloadedSize = 0;

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0; //used to store a temporary size of the buffer
            
            //now, read through the input buffer and write the contents to the file
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                //add the data in the buffer to the file in the file output stream (the file on the sd card
                fileOutput.write(buffer, 0, bufferLength);
                //add up the size so we know how much is downloaded
                downloadedSize += bufferLength;
                //this is where you would do something to report the prgress, like this maybe
                //updateProgress(downloadedSize, totalSize);
                publishProgress((downloadedSize*100/totalSize));
            }
            //close the output stream when done
            fileOutput.close();

        //catch some possible errors...
        } 
        catch (MalformedURLException e){
            Log.e( "DOWNLOAD" , "ERROR : " + e );
        } 
        catch (IOException e) {
            Log.e( "DOWNLOAD" , "ERROR : " + e );
        }
        return Environment.getExternalStorageDirectory()+ "/MyDownloaded/" + "song.mp3";
    }

    private void publishProgress(final int i){
    	MainActivity mainActivity = (MainActivity) context;
    	mainActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mProgressDialog.setMessage("Baixando arquivo...");
				mProgressDialog.show();
			}
		});
    	mainActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mProgressDialog.setProgress(i);
			}
		});
    	//
    	Log.w( "DOWNLOAD" , "progress " + i + "% ");
    }

    protected void onPostExecute(String path) {
    	MainActivity mainActivity = (MainActivity) context;
    	//
    	Intent intent = new Intent(mainActivity, Player.class);
    	intent.putExtra("path", path);
    	//
    	mainActivity.startActivity(intent);
    	//
    	Log.e("Path ", path);
        //if (result.equals("done")) 
        //    mProgressDialog.dismiss();
    }
    
    
}