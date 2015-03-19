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
**Apps that use this library**:<p>https://play.google.com/store/apps/details?id=org.fortschools.app

