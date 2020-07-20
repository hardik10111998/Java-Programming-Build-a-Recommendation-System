package example;

import java.util.*;

public class DirectorsFilter implements Filter {
	private String directors;

	public DirectorsFilter(String directors) {
		this.directors = directors;
	}

	@Override
	public boolean satisfies(String id) {

		ArrayList<String> dirList = new ArrayList<String>(Arrays.asList(directors.split(",")));
		for (String dir : dirList) {
			if (MovieDatabase.getDirector(id).contains(dir)) {
				return true;
			}
		}
		return false;
	}
}