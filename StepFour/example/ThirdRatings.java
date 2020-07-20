package example;

import java.io.IOException;
import java.util.ArrayList;

// content copied from SecondRatind
public class ThirdRatings {
//	private ArrayList<Movie> myMovies;
	private ArrayList<Rater> myRaters;

	public ThirdRatings() {
		this("ratings.csv");
	}

	public ThirdRatings(String ratingsfile) {
		// create a FirsrRatings object
		FirstRatings fr = new FirstRatings();
		// call the loadRaters
		try {
			myRaters = fr.loadRaters(ratingsfile);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public int getRaterSize() {
		return myRaters.size();
	}

	/*
	 * This method returns a double representing the average movie rating for this
	 * ID if there are at least minimalRaters ratings. If there are not
	 * minimalRaters ratings, then it returns 0.0.
	 */
	private double getAverageByID(String movieId, int minimalRaters) {
		double average = 0, total = 0;
		int totalRaters = 0;
		for (Rater rater : myRaters) {
			if (rater.hasRating(movieId)) {
				totalRaters++;
				total = total + rater.getRating(movieId);
			}
		}

		if (totalRaters >= minimalRaters) {
			average = total / totalRaters;
		} else {
			average = 0.0;
		}
		return average;
	}

//	step 7.
	/*
	 * This method should find the average rating for every movie that has been
	 * rated by at least minimalRaters raters. Store each such rating in a Rating
	 * object in which the movie ID and the average rating are used increating the
	 * Rating object
	 */
	public ArrayList<Rating> getAverageRatings(int minimalRaters) {

		ArrayList<Rating> ratings = new ArrayList<>();

		ArrayList<String> myMovies = MovieDatabase.filterBy(new TrueFilter());
		for (String id : myMovies) {

			double avg = getAverageByID(id, minimalRaters);
			if (avg > 0.0) {
				Rating avgRating = new Rating(id, avg);
				ratings.add(avgRating);
			}
		}
		return ratings;
	}

	public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {

		ArrayList<Rating> ratings = new ArrayList<>();

		ArrayList<String> myMovies = MovieDatabase.filterBy(filterCriteria);
		for (String id : myMovies) {

			double avg = getAverageByID(id, minimalRaters);
			if (avg > 0.0) {
				Rating avgRating = new Rating(id, avg);
				ratings.add(avgRating);
			}
		}
		return ratings;

	}

}
