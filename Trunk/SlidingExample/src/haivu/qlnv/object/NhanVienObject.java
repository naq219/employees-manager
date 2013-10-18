package haivu.qlnv.object;

public class NhanVienObject {

	String name;
	String day;
	String morning;
	String time_begin;
	String time_end;
	String content_work;
	String reminder;
	String repeat;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMorning() {
		return morning;
	}

	public void setMorning(String morning) {
		this.morning = morning;
	}

	public String getTime_begin() {
		return time_begin;
	}

	public void setTime_begin(String time_begin) {
		this.time_begin = time_begin;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public String getContent_work() {
		return content_work;
	}

	public void setContent_work(String content_work) {
		this.content_work = content_work;
	}

	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public NhanVienObject(String name, String day, String morning,
			String time_begin, String time_end, String content_work,
			String reminder, String repeat) {
		super();
		this.name = name;
		this.day = day;
		this.morning = morning;
		this.time_begin = time_begin;
		this.time_end = time_end;
		this.content_work = content_work;
		this.reminder = reminder;
		this.repeat = repeat;
	}

}
