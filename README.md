EasyWebPageDownloadForAndroid
=============================

Good for connecting to rest api's, html parsing, and many other uses!

Example Of Searching Google Programmatically: https://github.com/horvste/EasyWebPageDownloadForAndroidExample/tree/master


```
/**
* Simple Activity to demonstrate webpagedownoadlib. Searches Google Programatically. To see full code sample go to: 
* https://github.com/horvste/EasyWebPageDownloadForAndroidExample/tree/master
*
* @author Steven Horvatin
*
*/

	public void setListeners() {
		mSearchGoogleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String query = mSearchQueryEditText.getText().toString();
				DownloadWebPage downloadWebPage = new DownloadWebPage(
						new GoogleWebPageDownloader(MainActivity.this,
								mShowHtmlTextView), query);
				downloadWebPage.downloadHtml();

			}
		});
	}


	static class GoogleWebPageDownloader implements OnProgressUpdate {
		private Context context;

		public GoogleWebPageDownloader(Context c, TextView htmlUpdateTextView) {
			this.context = c;
		}

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

}```

