package com.example.john.flickrbrowserapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by John on 8/3/2016.
 */

enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK}

public class GetData {
    private String LOG_TAG = GetData.class.getSimpleName();
    private String mURL;
    private String mData;
    private DownloadStatus downloadStatus;

    public GetData(String mURL) {
        this.mURL = mURL;
        this.downloadStatus = DownloadStatus.IDLE;
    }

    public void reset() {
        this.downloadStatus = DownloadStatus.IDLE;
        this.mData = null;
        this.mData = null;
    }

    public String getmData() {
        return mData;
    }

    public DownloadStatus getDownloadStatus() {
        return downloadStatus;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }

    public void execute() {
        this.downloadStatus = DownloadStatus.PROCESSING;
        DownloadData downloadData = new DownloadData();
        downloadData.execute(mURL);
    }

    public class DownloadData extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            if (params == null) {
                return null;
            }

            try {
                URL url = new URL(params[0]);

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();

                StringBuilder stringBuilder = new StringBuilder();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error", e);
                return null;
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }

                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            mData = s;
            Log.v(LOG_TAG, "Data returned was " + mData);
            if (mData == null) {
                if (mURL == null) {
                    downloadStatus = DownloadStatus.NOT_INITIALISED;
                } else {
                    downloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                }
            } else {
                downloadStatus = DownloadStatus.OK;
            }
        }
    }
}