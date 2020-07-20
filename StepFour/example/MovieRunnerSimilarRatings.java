package example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MovieRunnerSimilarRatings {

	public void printAverageRatings() throws IOException {

		String moviesFile = "ratedmoviesfull.csv";
		String ratingsFile = "ratings.csv";
//		String moviesFile = "ratedmovies_short.csv";
//		String ratingsFile = "ratings_short.csv";

		// create FourthRatings object
		FourthRatings fr = new FourthRatings();
		MovieDatabase.initialize(moviesFile);
		RaterDatabase.initialize(ratingsFile);

		System.out.println("Number of raters: " + RaterDatabase.size());

		MovieDatabase.initialize(moviesFile);
		System.out.println("Number of movies rated: " + MovieDatabase.size());

		int minimalRaters = 35;
		ArrayList<Rating> ratings = fr.getAverageRatings(minimalRaters);
		System.out.println("found " + ratings.size() + " movies ");
		Collections.sort(ratings);
		for (Rating r : ratings) {
			String Title = MovieDatabase.getTitle(r.getItem());
			System.out.println(r.getValue() + " " + Title);
		}

	}

	public void printAverageRatingsByYearAfterAndGenre() {

		String moviesFile = "ratedmoviesfull.csv";
		String ratingsFile = "ratings.csv";
//		String moviesFile = "ratedmovies_short.csv";
//		String ratingsFile = "ratings_short.csv";

		// create FourthRatings object
		FourthRatings fr = new FourthRatings();
		MovieDatabase.initialize(moviesFile);
		RaterDatabase.initialize(ratingsFile);
		MovieDatabase.initialize(moviesFile);
		System.out.println("read data for " + RaterDatabase.size() + " raters");
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
		ArrayList<Rating> ratings = fr.getAverageRatingsByFilter(minimalRaters, allFilter);
		Collections.sort(ratings);
		System.out.println("found " + ratings.size() + " movies");

		for (Rating r : ratings) {
			String Title = MovieDatabase.getTitle(r.getItem());
			String Genre = MovieDatabase.getGenres(r.getItem());
			int Year = MovieDatabase.getYear(r.getItem());
			System.out.println(r.getValue() + " " + Year + " " + Title + " \n---|~~>\t" + "[" + Genre + "]");
		}
	}

	public void printSimilarRatings() {
		int minimalRaters = 5;
		String id = "71";
		int similarRaters = 20;

		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";

		FourthRatings fr = new FourthRatings();
		RaterDatabase.initialize(ratingsfile);
		MovieDatabase.initialize(moviefile);
		System.out.println("read data for " + RaterDatabase.size() + " raters");
		System.out.println("read data for " + MovieDatabase.size() + " movies");

		ArrayList<Rating> recommendations = fr.getSimilarRatings(id, similarRaters, minimalRaters);

		System.out.println("Found :--> " + recommendations.size() + " movie ");
//        System.out.println("All the recommendations: " + recommendations);

		for (Rating rating : recommendations) {
			String movieTitle = MovieDatabase.getTitle((rating.getItem()));
			System.out.println(movieTitle + " : " + rating.getValue());
		}
	}

	public void printSimilarRatingsByGenre() {
		int minimalRaters = 5;
		String id = "964";
		int numSimilarRaters = 20;
		String selecGenre = "Mystery";

		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		RaterDatabase.initialize(ratingsfile);
		MovieDatabase.initialize(moviefile);
		System.out.println("read data for " + RaterDatabase.size() + " raters");
		System.out.println("read data for " + MovieDatabase.size() + " movies");

		Filter gr = new GenreFilter(selecGenre);
		ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, gr);
		System.out.println("Found :--> " + recommendations.size() + " movie ");

		for (Rating rating : recommendations) {
			String movieTitle = MovieDatabase.getTitle((rating.getItem()));
			System.out.println(movieTitle + " : " + rating.getValue());
		}
	}

	public void printSimiliarRatingsByDirector() {
		int minimalRaters = 2;
		String id = "120";
		int numSimilarRaters = 10;
		String inputDirectors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";

		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		RaterDatabase.initialize(ratingsfile);
		MovieDatabase.initialize(moviefile);
		System.out.println("read data for " + RaterDatabase.size() + " raters");
		System.out.println("read data for " + MovieDatabase.size() + " movies");

		Filter dr = new DirectorsFilter(inputDirectors);
		ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, dr);
		System.out.println("Found :--> " + recommendations.size() + " movie ");

		for (Rating rating : recommendations) {
			String movieTitle = MovieDatabase.getTitle((rating.getItem()));
			String director = MovieDatabase.getDirector((rating.getItem()));
			System.out.println(movieTitle + " : " + rating.getValue());
			System.out.println("\t director :~> " + director);
		}
	}

	public void printSimiliarRatingsByGenreAndMinutes() {
		int minimalRaters = 3;
		String id = "168";
		int numSimilarRaters = 10;
		int minMinutes = 80;
		int maxMinutes = 160;
		String selecGenre = "Drama";

		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		RaterDatabase.initialize(ratingsfile);
		MovieDatabase.initialize(moviefile);

		System.out.println("read data for " + RaterDatabase.size() + " raters");
		System.out.println("read data for " + MovieDatabase.size() + " movies");

		Filter gr = new GenreFilter(selecGenre);
		Filter mr = new MinutesFilter(minMinutes, maxMinutes);
		AllFilters filtersList = new AllFilters();
		filtersList.addFilter(mr);
		filtersList.addFilter(gr);

		ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters,
				filtersList);
		System.out.println("Found :--> " + recommendations.size() + " movie ");

		for (Rating rating : recommendations) {
			String movieTitle = MovieDatabase.getTitle((rating.getItem()));
			int minutes = MovieDatabase.getMinutes((rating.getItem()));
			String genres = MovieDatabase.getGenres((rating.getItem()));
			System.out.println(movieTitle + " , Time: " + minutes + " , Ratings:" + rating.getValue());
			System.out.println("\t Genres :~> " + genres);

		}
	}

	public void printAverageRatingsByYearAfterAndMinutes() {
		int minimalRaters = 5;
		String id = "314";
		int numSimilarRaters = 10;
		int minMinutes = 70;
		int maxMinutes = 200;
		int yearCount = 1975;

		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		RaterDatabase.initialize(ratingsfile);
		MovieDatabase.initialize(moviefile);

		System.out.println("read data for " + RaterDatabase.size() + " raters");
		System.out.println("read data for " + MovieDatabase.size() + " movies");

		Filter yf = new YearAfterFilter(yearCount);
		Filter mr = new MinutesFilter(minMinutes, maxMinutes);
		AllFilters filtersList = new AllFilters();
		filtersList.addFilter(mr);
		filtersList.addFilter(yf);

		ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters,
				filtersList);
		System.out.println("Found :--> " + recommendations.size() + " movie ");

		for (Rating rating : recommendations) {
			String movieTitle = MovieDatabase.getTitle((rating.getItem()));
			int year = MovieDatabase.getYear((rating.getItem()));
			int minutes = MovieDatabase.getMinutes((rating.getItem()));
			System.out.println(
					movieTitle + ", year : " + year + " , Time :" + minutes + " , ratings: " + rating.getValue());
		}
	}

	public static void main(String[] args) throws IOException {
		MovieRunnerSimilarRatings obj = new MovieRunnerSimilarRatings();
//		obj.printAverageRatings();
//		obj.printAverageRatingsByYearAfterAndGenre();
//		obj.printSimilarRatings();
//		obj.printSimilarRatingsByGenre();
//		obj.printSimiliarRatingsByDirector();
//		obj.printSimiliarRatingsByGenreAndMinutes();
		obj.printAverageRatingsByYearAfterAndMinutes();

	}

}
