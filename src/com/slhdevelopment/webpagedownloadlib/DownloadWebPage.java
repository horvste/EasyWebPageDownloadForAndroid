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
 * @see AsyncTask
 * @author Steven Horvatin
 * 
 */
public class DownloadWebPage extends AsyncTask<String, Integer, StringBuilder> {
	private OnProgressUpdate updateInter;
	private String url;

	/**
	 * Visibility should be kept private for encapsulation purposes.
	 */
	private DownloadWebPage() {
		super();
	}

	/**
	 * Instantiates a DownloadWebPage Object, you should only use this
	 * constructor if you don't know which url you will be downloading at the
	 * moment.
	 * 
	 * @param updateInter
	 *            is the interface which will be updated when you call
	 *            downloadHtml() or downloadHtml(String urlToDownload)
	 */
	public DownloadWebPage(OnProgressUpdate updateInter) {
		this.updateInter = updateInter;
	}

	/**
	 * Instantiates a DownloadWebPage Object. You should use this constructor if
	 * you aren't familliar with this library.
	 * 
	 * @param updateInter
	 *            is the interface which will be updated when you call
	 *            downloadHtml() or downloadHtml(String urlToDownload)
	 * @param url
	 *            is the url which html will be downloaded.
	 */
	public DownloadWebPage(OnProgressUpdate updateInter, String url) {
		this(updateInter);
		this.url = url;
	}

	/**
	 * @param url
	 *            is the amount of url's to download. Accepting a variable
	 *            amount of arguments is not appropriate in this scenario,
	 *            however we have to because is is a superclass method.
	 * 
	 */
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
			// use the StringBuilder for efficiency. In java Strings are
			// immutable.
			StringBuilder htmlBuilder = new StringBuilder();
			long bytesRead = 0;
			// while there is html left we will loop.
			while ((line = rd.readLine()) != null) {
				htmlBuilder.append(line);
				bytesRead = bytesRead + line.getBytes().length + 2;
				// getting the progress from the html, this is not guaranteed.
				publishProgress(new Integer[] { (int) (((double) bytesRead / (double) content) * 100) });
			}
			return htmlBuilder;
		} catch (IOException e) {
			// Poor connection or something else went wrong.
			return null;
		}

	}

	/**
	 * Updating the progress for the interface.
	 * 
	 * @param values
	 *            currently not using the variable arguments, that is values
	 *            will only have one element at the zero index.
	 * 
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		// if there is a progress to update: update the interface with the
		// proper percentage complete. Or else we tell the interface the update
		// is failed and we cannot find the progress.
		if (values[0].equals(Math.abs(values[0]))) {
			updateInter.onUpdate(values[0]);
		} else if (!values[0].equals(Math.abs(values[0]))) {
			updateInter.onUpdateFailure();
		}
	}

	/**
	 * @param result
	 *            the html which can be null or contain a String of html.
	 */
	@Override
	protected void onPostExecute(StringBuilder result) {
		// if there is nothing in result, the download has failed. Or else
		// we have succeeded and return the result (html).
		if (result == null) {
			updateInter.onFailure();
		} else if (result != null) {
			updateInter.onSuccess(result);
		}

	}

	public void setOnProgressUpdate(OnProgressUpdate updateInter) {
		this.updateInter = updateInter;

	}

	/**
	 * Downloads the html from the last time the url was set, either in the
	 * constructor or in the downloadHtml(String urlToDownload) method.
	 */
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
			// TODO: Decide if we should do anything here.
		} else {
			execute(new String[] { url });
		}
	}

	/**
	 * 
	 * @param urlToDownload
	 *            will be set to the current url which will be used in
	 *            conjuction with the OnProgressUpdate interface.
	 */
	public void downloadHtml(String urlToDownload) {
		this.url = urlToDownload;
		downloadHtml();
	}
}