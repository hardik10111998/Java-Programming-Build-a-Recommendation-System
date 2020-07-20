package example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerWithFilters {

	public void printAverageRatings() throws IOException {

		String moviesFile = "ratedmoviesfull.csv";
		String ratingsFile = "ratings.csv";
//		String moviesFile = "ratedmovies_short.csv";
//		String ratingsFile = "ratings_short.csv";

		// create ThirdRatings object
		ThirdRatings tr = new ThirdRatings(ratingsFile);

		System.out.println("Number of raters: " + tr.getRaterSize());

		MovieDatabase.initialize(moviesFile);
		System.out.println("Number of movies rated: " + MovieDatabase.size());

		int minimalRaters = 35;
		ArrayList<Rating> ratings = tr.getAverageRatings(minimalRaters);
		System.out.println("found " + ratings.size() + " movies ");
		Collections.sort(ratings);
		for (Rating r : ratings) {
			String Title = MovieDatabase.getTitle(r.getItem());
			System.out.println(r.getValue() + " " + Title);
		}

	}

	public void printAverageRatingsByYear() {

		String moviesFile = "ratedmoviesfull.csv";
		String ratingsFile = "ratings.csv";
//		String moviesFile = "ratedmovies_short.csv";
//		String ratingsFile = "ratings_short.csv";

		// create ThirdRatings object
		ThirdRatings tr = new ThirdRatings(ratingsFile);

		System.out.println("read data for " + tr.getRaterSize() + " raters");

		MovieDatabase.initialize(moviesFile);
		System.out.println("read data for " + MovieDatabase.size() + " movies");

		// create YearAfterFilter
		Filter yaf = new YearAfterFilter(2000);

		int minimalRaters = 20;
		ArrayList<Rating> list = tr.getAverageRatingsByFilter(minimalRaters, yaf);
		Collections.sort(list);
		System.out.println("found " + list.size() + " movies");

		for (Rating r : list) {
			int Year = MovieDatabase.getYear(r.getItem());
			String Title = MovieDatabase.getTitle(r.getItem());
			System.out.println(r.getValue() + " " + Year + " " + Title);
		}

	}

	public void printAverageRatingsByGenre() {

		String moviesFile = "ratedmoviesfull.csv";
		String ratingsFile = "ratings.csv";
//		String moviesFile = "ratedmovies_short.csv";
//		String ratingsFile = "ratings_short.csv";

		// create ThirdRatings object
		ThirdRatings tr = new ThirdRatings(ratingsFile);

		System.out.println("read data for " + tr.getRaterSize() + " raters");

		MovieDatabase.initialize(moviesFile);
		System.out.println("read data for " + MovieDatabase.size() + " movies");

//		String genre = "Crime";
		String genre = "Comedy";
		// create GenreFilter
		Filter gf = new GenreFilter(genre);

		int minimalRaters = 20;
		ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, gf);
		Collections.sort(ratings);
		System.out.println("found " + ratings.size() + " movies");

		for (Rating r : ratings) {
			String Genre = MovieDatabase.getGenres(r.getItem());
			String Title = MovieDatabase.getTitle(r.getItem());
			System.out.println(r.getValue() + " " + Title + "\n---|~~>\t [" + Genre + "]");
		}

	}

	public void printAverageRatingsByMinutes() {

		String moviesFile = "ratedmoviesfull.csv";
		String ratingsFile = "ratings.csv";
//		String moviesFile = "ratedmovies_short.csv";
//		String ratingsFile = "ratings_short.csv";

		// create ThirdRatings object
		ThirdRatings tr = new ThirdRatings(ratingsFile);

		System.out.println("read data for " + tr.getRaterSize() + " raters");

		MovieDatabase.initialize(moviesFile);
		System.out.println("read data for " + MovieDatabase.size() + " movies");

		int minMinutes = 105;
		int maxMinutes = 135;
		// create MinutesFilter
		Filter mf = new MinutesFilter(minMinutes, maxMinutes);

		int minimalRaters = 5;
		ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, mf);
		Collections.sort(ratings);
		System.out.println("found " + ratings.size() + " movies");

		for (Rating r : ratings) {
			int Time = MovieDatabase.getMinutes(r.getItem());
			String Title = MovieDatabase.getTitle(r.getItem());
			System.out.println(r.getValue() + " " + "Time: " + Time + " " + Title);
		}
	}

	public void getAverageRatingsByFilter() {

		String moviesFile = "ratedmoviesfull.csv";
		String ratingsFile = "ratings.csv";
//		String moviesFile = "ratedmovies_short.csv";
//		String ratingsFile = "ratings_short.csv";

		// create ThirdRatings object
		ThirdRatings tr = new ThirdRatings(ratingsFile);

		System.out.println("read data for " + tr.getRaterSize() + " raters");

		MovieDatabase.initialize(moviesFile);
		System.out.println("read data for " + MovieDatabase.size() + " movies");

//		String dir="Charles Chaplin,Michael Mann,Spike Jonze";
		String dir = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
		// create MinutesFilter
		Filter dr = new DirectorsFilter(dir);

		int minimalRaters = 4;
		ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, dr);
		Collections.sort(ratings);
		System.out.println("found " + ratings.size() + " movies");

		for (Rating r : ratings) {
			String Title = MovieDatabase.getTitle(r.getItem());
			String directors = MovieDatabase.getDirector(r.getItem());
			System.out.println(r.getValue() + " " + Title + " \n---|~~>\t" + "[" + directors + "]");
		}
	}

	public void printAverageRatingsByYearAfterAndGenre() {

		String moviesFile = "ratedmoviesfull.csv";
		String ratingsFile = "ratings.csv";
//		String moviesFile = "ratedmovies_short.csv";
//		String ratingsFile = "ratings_short.csv";

		// create ThirdRatings object
		ThirdRatings tr = new ThirdRatings(ratingsFile);

		System.out.println("read data for " + tr.getRaterSize() + " raters");

		MovieDatabase.initialize(moviesFile);
		System.out.println("read data for " + MovieDatabase.size() + " movies");

		int year = 1990;
		String genre = "Drama";

		// create MinutesFilter
		Filter yf = new YearAfterFilter(year);
		Filter gf = new GenreFilter(genre);
		AllFilters allFilter = new AllFilters();
		allFilter.addFilter(yf);
		allFilter.addFilter(gf);

		int minimalRaters = 8;
		ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, allFilter);
		Collections.sort(ratings);
		System.out.println("found " + ratings.size() + " movies");

		for (Rating r : ratings) {
			String Title = MovieDatabase.getTitle(r.getItem());
			String Genre = MovieDatabase.getGenres(r.getItem());
			int Year = MovieDatabase.getYear(r.getItem());
			System.out.println(r.getValue() + " " + Year + " " + Title + " \n---|~~>\t" + "[" + Genre + "]");
		}
	}

	public void printAverageRatingsByDirectorsAndMinutes() {

		String moviesFile = "ratedmoviesfull.csv";
		String ratingsFile = "ratings.csv";
//		String moviesFile = "ratedmovies_short.csv";
//		String ratingsFile = "ratings_short.csv";

		// create ThirdRatings object
		ThirdRatings tr = new ThirdRatings(ratingsFile);

		System.out.println("read data for " + tr.getRaterSize() + " raters");

		MovieDatabase.initialize(moviesFile);
		System.out.println("read data for " + MovieDatabase.size() + " movies");

		int minMinutes = 90;
		int maxMinutes = 180;
		String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";

		// create MinutesFilter
		Filter mr = new MinutesFilter(minMinutes, maxMinutes);
		Filter dr = new DirectorsFilter(directors);
		AllFilters allFilter = new AllFilters();
		allFilter.addFilter(mr);
		allFilter.addFilter(dr);

		int minimalRaters = 3;
		ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, allFilter);
		Collections.sort(ratings);
		System.out.println("found " + ratings.size() + " movies");

		for (Rating r : ratings) {
			String Title = MovieDatabase.getTitle(r.getItem());
			String dir = MovieDatabase.getDirector(r.getItem());
			int time = MovieDatabase.getMinutes(r.getItem());
			System.out.println(r.getValue() + " Time: " + time + " " + Title + " \n---|~~>\t" + "[" + dir + "]");
		}
	}

	public static void main(String[] args) throws IOException {
		MovieRunnerWithFilters obj = new MovieRunnerWithFilters();
//		obj.printAverageRatings();
//		obj.printAverageRatingsByYear();
//		obj.printAverageRatingsByGenre();
//		obj.printAverageRatingsByMinutes();
//		obj.getAverageRatingsByFilter();
		obj.printAverageRatingsByYearAfterAndGenre();
//		obj.printAverageRatingsByDirectorsAndMinutes();
	}
}
