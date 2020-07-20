package example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerAverage {

//	Step 5.
	public void printAverageRatings() throws IOException {
		// create secondRatings object
		String moviesFile = "ratedmoviesfull.csv";
		String ratingsFile = "ratings.csv";
//		String moviesFile = "ratedmovies_short.csv";
//		String ratingsFile = "ratings_short.csv";

//		Step 5.1
		SecondRatings sr = new SecondRatings(moviesFile, ratingsFile);
//      Step 5.2:   print the number of movies and the number of raters
		System.out.println("Number of rated movie: " + sr.getMovieSize());
		System.out.println("Number of raters: " + sr.getRaterSize());

//        Step 9.
		int minimalRaters = 20;
		ArrayList<Rating> ratings = sr.getAverageRatings(minimalRaters);
		Collections.sort(ratings);
		for (Rating r : ratings) {
			System.out.println(r.getValue() + " " + sr.getTitle(r.getItem()));
		}

	}

	public void getAverageRatingOneMovie() throws IOException {
		// create secondRatings object
		String moviesFile = "ratedmoviesfull.csv";
		String ratingsFile = "ratings.csv";
//		String moviesFile = "ratedmovies_short.csv";
//		String ratingsFile = "ratings_short.csv";

		SecondRatings sr = new SecondRatings(moviesFile, ratingsFile);

//		String title="The Godfather";
//		String title="No Country for Old Men";
		String title = "Inside Llewyn Davis";
//		String title="The Maze Runner";
//		String title="Moneyball";
//		String title="Vacation";
		String id = sr.getID(title);
		int minimalRaters = 5;
		ArrayList<Rating> ratings = sr.getAverageRatings(minimalRaters);
		for (Rating rating : ratings) {

			if (rating.getItem().equals(id)) {
				System.out.println("Average rating for '" + title + "' is: " + rating.getValue());
			}

		}
//		System.out.println("There are " + ratings.size() + " movies that has  more than "+ minimalRaters);

	}

	public static void main(String[] args) throws IOException {
		MovieRunnerAverage obj = new MovieRunnerAverage();
//		obj.printAverageRatings();
		obj.getAverageRatingOneMovie();
	}
}
