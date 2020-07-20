package example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FourthRatings {

	/*
	 * This method returns a double representing the average movie rating for this
	 * ID if there are at least minimalRaters ratings. If there are not
	 * minimalRaters ratings, then it returns 0.0.
	 */
	private double getAverageByID(String movieId, int minimalRaters) {
		double average = 0, total = 0;
		int totalRaters = 0;

		ArrayList<Rater> myRaters = RaterDatabase.getRaters();

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

	private double dotProduct(Rater me, Rater r) {
		double similarity = 0.0;
		ArrayList<String> moviesRatedByMe = me.getItemsRated();
		for (String movieId : moviesRatedByMe) {
			if (r.hasRating(movieId)) {
//			translate a rating from the scale 0 to 10 to the scale 5 to 5
				double rRating = r.getRating(movieId) - 5;
				double myRating = me.getRating(movieId) - 5;

				similarity = similarity + (rRating * myRating);
			}
		}

		return similarity;
	}

	private ArrayList<Rating> getSimilarities(String id) {

		ArrayList<Rating> ratings = new ArrayList<>();
		ArrayList<Rater> allRaters = RaterDatabase.getRaters();

		// here input id is myID
		Rater me = RaterDatabase.getRater(id);

		for (Rater r : allRaters) {
			String raterID = r.getID();
			// add dotProduct(me,r) to list if r !=me
			if (raterID.equals(id) == false) {
				double similarity = dotProduct(me, r);
				Rating similarRating = new Rating(raterID, similarity);
				ratings.add(similarRating);
			}

		}
		Collections.sort(ratings, Collections.reverseOrder());
		return ratings;
	}

	public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
		return getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, new TrueFilter());
	}

	public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters,
			Filter filterCriteria) {
		ArrayList<Rating> movieSimRatings = new ArrayList<Rating>();

		HashMap<String, Double> similarMap = new HashMap<String, Double>();
		int mapSize = getSimilarities(id).size();
		int minIndex = Math.min(mapSize, numSimilarRaters);

		for (Rating similar : getSimilarities(id).subList(0, minIndex)) {
			if (similar.getValue() > 0) {
				similarMap.put(similar.getItem(), similar.getValue());
			}
		}

		for (String movieID : MovieDatabase.filterBy(filterCriteria)) {
			int count = 0;
			double total = 0;

			for (Rater curRater : RaterDatabase.getRaters()) {
				double rating = -1;
				if (similarMap.containsKey(curRater.getID()) && curRater.hasRating(movieID)) {
					rating = curRater.getRating(movieID) * similarMap.get(curRater.getID());
				}

				if (rating == -1) {
				}

				else {
					count++;
					total = total + rating;
				}
			}

			if (count < minimalRaters || total == 0) {
			} else {
				movieSimRatings.add(new Rating(movieID, total / count));
			}

		}
		Collections.sort(movieSimRatings, Collections.reverseOrder());
		return movieSimRatings;
	}

}
