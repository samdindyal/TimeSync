/**
	Title: The "TimeSync" class
	Date Written: November 2015
	Author: Samuel Dindyal, Balin Banh, Danel Tran
	Description: The launcher for the client side of TimeSync.
*/

public class TimeSync {
	public static void main (String[] args)
	{
		TimeSyncUI ui = new TimeSyncUI("TimeSync", new TimeSyncRuntime("localhost"));
	}
}