EasyWebPageDownloadForAndroid
=============================

Good for connecting to ***REST API's***, ***HTML parsing***, and ***many other uses***. Using this library is meant to be easy:
<p>1. Create a class which implements OnProgressUpdate <p>

```
public class SampleClass implements OnProgressUpdate {

		@Override
		public void onUpdate(Integer percentProgress) {
			
		}

		@Override
		public void onUpdateFailure() {
		
		}

		@Override
		public void onSuccess(StringBuilder result) {

		}

		@Override
		public void onFailure() {

		}
	}

}
```
2. Instantiate DownloadWebPage object<p>

```
DownloadWebPage webPage = new DownloadWebPage(new SampleClass(), myUrl);
```

3. Call .downloadHtml() from the DownloadWebPage<p>
```
webPage.downloadHtml();
```

<p><p><p>
**Full Example:**<p>

Example Of Searching Google Programmatically: https://github.com/horvste/EasyWebPageDownloadForAndroidExample/tree/master


```
/**
* Simple Activity to demonstrate webpagedownoadlib. Searches Google Programatically. To see full code sample go to: 
* https://github.com/horvste/EasyWebPageDownloadForAndroidExample/tree/master
*
* @author Steven Horvatin
*
*/
public class MainActivity extends Activity {
	private static final String SEARCHGOOGLE = "Search Google";
	private static final String RESULT = "result";
	private Button mSearchGoogleButton;
	private EditText mSearchQueryEditText;
	private TextView mShowHtmlTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewsById();
		setUiDefaults();
		setListeners();

	}

	public void findViewsById() {
		mSearchGoogleButton = (Button) findViewById(R.id.button1);
		mSearchQueryEditText = (EditText) findViewById(R.id.editText1);
		mShowHtmlTextView = (TextView) findViewById(R.id.textView1);

	}

	public void setUiDefaults() {
		mSearchGoogleButton.setText(SEARCHGOOGLE);
		mSearchQueryEditText.setHint(SEARCHGOOGLE);
		mShowHtmlTextView.setText(SEARCHGOOGLE + " " + RESULT);
		mShowHtmlTextView.setMovementMethod(new ScrollingMovementMethod());
	}

	public void setListeners() {
		mSearchGoogleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String query = mSearchQueryEditText.getText().toString();
				
				//This is the IMPORTANT PART:
				DownloadWebPage downloadWebPage = new DownloadWebPage(
						new GoogleWebPageDownloader(MainActivity.this,
								mShowHtmlTextView), GoogleWebPageDownloader
								.getGoogleSearchQuery(query));
				downloadWebPage.downloadHtml();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	static class GoogleWebPageDownloader implements OnProgressUpdate {
		private Context context;
		private ProgressDialog mDialog;
		private View mShowHtmlView;
		private static final String SOMETHINGWENTWRONG = "Something Went Wrong";
		public static final String GOOGLEURLBEGIN = "https://www.google.com/search?client=ubuntu&channel=fs&q=";
		public static final String GOOGLEURLEND = "&ie=utf-8&oe=utf-8";
		private static final String DIALOGTITLE = "Downloading Query";

		public GoogleWebPageDownloader(Context c, TextView htmlUpdateTextView) {
			this.context = c;
			mDialog = new ProgressDialog(context);
			mDialog.setTitle("Downloading Query");
			mDialog.setCancelable(false);
			mDialog.setProgress(0);
			mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mDialog.show();
			mShowHtmlView = htmlUpdateTextView;
		}

		public static final String getGoogleSearchQuery(String searchQuery) {
			String url = GOOGLEURLBEGIN + searchQuery.replace(" ", "+")
					+ GOOGLEURLEND;
			return url;

		}
		
		@Override
		public void onUpdate(Integer percentProgress) {
			mDialog.setProgress(percentProgress);
		}

		@Override
		public void onUpdateFailure() {
			mDialog.setTitle(DIALOGTITLE + ": Unable to get progress");
			mDialog.setProgress(-1);
		}

		@Override
		public void onSuccess(StringBuilder result) {
			((TextView) mShowHtmlView).setText(result.toString());
			mDialog.setTitle("Successful");
			mDialog.dismiss();

		}

		@Override
		public void onFailure() {
			mDialog.dismiss();
			Toast.makeText(context, SOMETHINGWENTWRONG, Toast.LENGTH_SHORT)
					.show();
		}
	}

}
```
Program's that use this library:<p>https://play.google.com/store/apps/details?id=org.fortschools.app

