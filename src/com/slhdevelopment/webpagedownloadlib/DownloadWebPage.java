package com.slhdevelopment.webpagedownloadlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

/**
 * Downloads a webpage's html and acts according to the interface.
 * 
 * @author steven
 * 
 */
public class DownloadWebPage extends AsyncTask<String, Integer, StringBuilder> {
	private OnProgressUpdate updateInter;
	private String url;

	public DownloadWebPage() {
		super();
	}

	public DownloadWebPage(OnProgressUpdate updateInter) {
		this.updateInter = updateInter;
	}

	public DownloadWebPage(OnProgressUpdate updateInter, String url) {
		this(updateInter);
		this.url = url;
	}

	@Override
	protected StringBuilder doInBackground(String... url) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url[0]);
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			long content = response.getEntity().getContentLength();
			String line = "";
			StringBuilder htmlBuilder = new StringBuilder();
			long bytesRead = 0;
			while ((line = rd.readLine()) != null) {
				htmlBuilder.append(line);
				bytesRead = bytesRead + line.getBytes().length + 2;
				publishProgress(new Integer[] { (int) (((double) bytesRead / (double) content) * 100) });
			}
			return htmlBuilder;
		} catch (IOException e) {
			return null;
		}

	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		if (values[0].equals(Math.abs(values[0]))) {
			updateInter.onUpdate(values[0]);
		} else if (!values[0].equals(Math.abs(values[0]))) {
			updateInter.onUpdateFailure();
		}
	}

	@Override
	protected void onPostExecute(StringBuilder result) {
		if (result == null) {
			updateInter.onFailure();
		} else if (result != null) {
			updateInter.onSuccess(result);
		}

	}

	public void setOnProgressUpdate(OnProgressUpdate updateInter) {
		this.updateInter = updateInter;

	}

	public void downloadHtml() {
		if (url == null) {
			updateInter.onFailure();
			return;
		}
		System.out.println("STATUS=" + getStatus());
		if (getStatus().equals(AsyncTask.Status.FINISHED)) {
			DownloadWebPage iseDownloader = new DownloadWebPage();
			iseDownloader.setOnProgressUpdate(updateInter);
			iseDownloader.execute(new String[] { url });
		} else if (getStatus().equals(AsyncTask.Status.RUNNING)) {
		} else {
			execute(new String[] { url });
		}
	}

	public void downloadHtml(String urlToDownload) {
		this.url = urlToDownload;
		downloadHtml();
	}
}