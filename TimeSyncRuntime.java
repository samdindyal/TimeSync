import java.util.Calendar;

public class TimeSyncRuntime
{
	private Calendar calendar;
	
	public TimeSyncRuntime(){
		calendar = Calendar.getInstance();
	}

	public int[] getTime() {

		int time[] = {calendar.get(Calendar.SECOND),
					calendar.get(Calendar.MINUTE),
					((calendar.get(Calendar.AM_PM) == Calendar.AM) ? 0 : 12 )+ calendar.get(Calendar.HOUR)};
		return time;
	}

	public int[] getDate() {
		int date[] =  	{calendar.get(Calendar.DAY_OF_WEEK)-1,
				 		calendar.get(Calendar.MONTH),
				 		calendar.get(Calendar.DAY_OF_MONTH),
				 		calendar.get(Calendar.YEAR) };
		return date;
	}
}