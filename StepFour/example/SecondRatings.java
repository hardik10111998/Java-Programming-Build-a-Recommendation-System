package example;

/**
 * Write a description of SecondRatings here.
 * 
 * @author Hardik
 */

import edu.duke.*;

import java.io.IOException;
import java.util.*;
import org.apache.commons.csv.*;

public class SecondRatings {
	private ArrayList<Movie> myMovies;
	private ArrayList<Rater> myRaters;

	public SecondRatings() throws IOException {
		// default constructor
		this("ratedmoviesfull.csv", "ratings.csv");
	}

//	Step 1 and 2.
	public SecondRatings(String moviefile, String loadRaters) throws IOException {
		// create a FirsrRatings object
		FirstRatings fr = new FirstRatings();
		// call the loadMovies and loadRaters using object
		myMovies = fr.loadMovies(moviefile);
		myRaters = fr.loadRaters(loadRaters);

//		System.out.println("myMovies : " + myMovies);
//		System.out.println("myRaters : " + myRaters);
	}

//	Step 3.
	public int getMovieSize() {
		return myMovies.size();
	}

//	step 4.
	public int getRaterSize() {
		return myRaters.size();
	}

//	step 6.
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
		for (Movie m : myMovies) {
//			System.out.println("Movie details : " + m);
			double avg = getAverageByID(m.getID(), minimalRaters);
			if (avg > 0.0) {
				Rating avgRating = new Rating(m.getID(), avg);
				ratings.add(avgRating);
			}
		}
		return ratings;
	}

//	step 8.
	public String getTitle(String movieId) {
		String title = "";
		for (Movie movie : myMovies) {
			if (movie.getID().equals(movieId)) {
				title = movie.getTitle();
				break;
			}
		}
		if (title == "") {
			title = "This ID is Not Found";
		}
		return title;
	}

//	Step 10. This method returns the movie ID of this movie.
	public String getID(String title) {
		String id = "";
		for (Movie movie : myMovies) {
			if (movie.getTitle().equals(title)) {
				id = movie.getID();
				break;
			}
		}
		if (id == "") {
			return "NO SUCH TITLE";
		} else {
			return id;
		}

	}

}