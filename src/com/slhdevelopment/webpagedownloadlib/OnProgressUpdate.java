package com.slhdevelopment.webpagedownloadlib;

/**
 * 
 * @author Steven Horvatin
 * 
 */
public interface OnProgressUpdate {
	/**
	 * 
	 * @param percentProgress
	 *            is the percent out of 100 that is 'downloaded'
	 */
	public void onUpdate(Integer percentProgress);

	/**
	 * This is called if onUpdate(Integer percentProgress) fails meaning that
	 * the website does not provide a progress of the html downloaded
	 */
	public void onUpdateFailure();

	/**
	 * 
	 * @param result
	 *            is the html. Call result.toString() to get the String
	 *            representation of the StringBuilder.
	 */
	public void onSuccess(StringBuilder result);

	/**
	 * If downloading failed...This means that there is no connection, slow
	 * connection, or a connection with no internet capabilities. Usually you
	 * would say 'Ooops Something Went Wrong' or 'Do you have a valid
	 * connection?'
	 */
	public void onFailure();

}