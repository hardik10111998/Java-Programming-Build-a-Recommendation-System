package example;

public class MinutesFilter implements Filter {
	private int minMinutes;
	private int maxMinutes;

	public MinutesFilter(int minMinutes, int maxMinutes) {
		this.minMinutes = minMinutes;
		this.maxMinutes = maxMinutes;
	}

	@Override
	public boolean satisfies(String id) {
		int time = MovieDatabase.getMinutes(id);
		if (time >= minMinutes && time <= maxMinutes) {
			return true;
		} else {
			return false;
		}
	}
}