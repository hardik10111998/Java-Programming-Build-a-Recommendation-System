package example;

/**
 * @author Hardik 
 */

import edu.duke.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import org.apache.commons.csv.*;

public class FirstRatings {

//	 to fetch every record from file and return movieList
	public ArrayList<Movie> loadMovies(String filename) throws IOException {
		ArrayList<Movie> mList = new ArrayList<Movie>();

		Reader in = new FileReader("data/" + filename);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in); // here., DEFAULT=>RFC4180
		for (CSVRecord record : records) {
			Movie currMovie = new Movie(record.get("id"), record.get("title"), record.get("year"), record.get("genre"),
					record.get("director"), record.get("country"), record.get("poster"),
					Integer.parseInt(record.get("minutes")));
			;
			mList.add(currMovie);
		}

		return mList;
	}

	public void testLoadMovies() throws IOException {

//		 Step 1.: call the method loadMovies on the file 'ratemovies_short.csv' or 'ratedmoviesfull.csv'

//		ArrayList<Movie> allMovies = loadMovies("ratedmovies_short.csv");
		ArrayList<Movie> allMovies = loadMovies("ratedmoviesfull.csv");

		System.out.println("Total movies: " + allMovies.size());
//		System.out.println("MovieList: " + allMovies);

//        for(int i=0;i<allMovies.size();i++) {
//        	System.out.println((i+1)+":"+allMovies.get(i));        	
//        }

//      Step2,:Add code to determine how many movies include the Comedy genre
		System.out.println(
				"Comedy genre : --> " + allMovies.stream().filter(m -> m.getGenres().contains("Comedy")).count());

//		Step3.:Add code to determine how many movies are greater than 150 minutes in length
		System.out.println("Greater than 150 minutes in length : --> "
				+ allMovies.stream().filter(m -> m.getMinutes() > 150).count());

//		Step 4,:Add code to determine the maximum number of movies by any director, and who
//				the directors are that directed that many movies. Remember that some movies
//				may have more than one director.

		HashMap<String, Integer> directorCounts = new HashMap<String, Integer>();
		for (Movie m : allMovies) {

			String currentDirectors = m.getDirector();
//	         System.out.println("current director: " + currentDirectors);

			if (directorCounts.containsKey(currentDirectors)) {
				directorCounts.put(currentDirectors, directorCounts.get(currentDirectors) + 1);
			} else {
				directorCounts.put(currentDirectors, 1);
			}

		}

		int dirWithMaxMovies = Collections.max(directorCounts.values());

		ArrayList<String> movieWithMaxdirs = new ArrayList<String>();
		for (String dir : directorCounts.keySet()) {
			if (directorCounts.get(dir) == dirWithMaxMovies) {
				movieWithMaxdirs.add(dir);
			}
		}
		System.out.println("Director Counts : " + directorCounts);
		System.out.println("Director with Max movies: " + dirWithMaxMovies);
		System.out.println("Directors that directed the max number of movies: " + movieWithMaxdirs);
	}

	public ArrayList<Rater> loadRaters(String fileName) throws IOException {
		ArrayList<Rater> allRater = new ArrayList<Rater>();

		Reader in = new FileReader("data/" + fileName);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in); // here., DEFAULT=>RFC4180

		Boolean flag = true; // execute first record

		for (CSVRecord record : records) {

			String rater_id = record.get("rater_id");
			String movie_id = record.get("movie_id");
			double rating = Double.parseDouble(record.get("rating"));

//           For First record             
			if (flag) {
//				Rater currRater = new PlainRater(rater_id);
				Rater currRater = new EfficientRater(rater_id);
				currRater.addRating(movie_id, rating);
				allRater.add(currRater);
				flag = false;
			} else {
				boolean isPresent = false; // to check is element present till we parse csv
				ArrayList<Rater> temp = new ArrayList<Rater>(allRater);
				for (Rater r : temp) {
					if (r.getID().equals(rater_id)) {
						r.addRating(movie_id, rating);
						isPresent = true;
					}
				}
				if (isPresent == false) { // if not present , then add it to final list
//					Rater currRater = new PlainRater(rater_id);
					Rater currRater = new EfficientRater(rater_id);
					currRater.addRating(movie_id, rating);
					allRater.add(currRater);

				}

			}
		}

		return allRater;

	}

	public void testLoadRaters() throws IOException {

//		 print the total number of raters
//        ArrayList<Rater> allRaters = loadRaters("ratings_short.csv");
		ArrayList<Rater> allRaters = loadRaters("ratings.csv");

//        Step 1 :
		System.out.println("Total number of raters: " + allRaters.size());
//        for(Rater r:allRaters) {
//        	System.out.println("Rater id: "+r.getID());
//        	System.out.println("Number of ratings they made : "+r.numRatings());
//        	System.out.println("All Ratings Given By him/her : "+r);
//        }  

//        Step 2:
//        System.out.println("Number of ratings for a particular rater : "+totalRatingsByRater(allRaters,"2"));
		System.out.println("Number of ratings for a particular rater : " + totalRatingsByRater(allRaters, "193"));

//      Step 3:  call "getMaxRatingsByRater(ArrayList<Rater> allRaters)"
		getMaxRatingsByRater(allRaters);

//      step 4 :  to find the number of ratings a particular movie has 
//        "getRatingPerMovie(ArrayList<Rater> raters,String movie_id)"
		int numOfRatingPerMovie = getRatingPerMovie(allRaters, "1798709");
		System.out.println("Number of ratings a particular movie has : " + numOfRatingPerMovie);

//       Step 5: call "totalMoviesRated(ArrayList<Rater> raters)"
		totalMoviesRated(allRaters);

	}

//	to find the number of ratings for a particular rater you specify
	public int totalRatingsByRater(ArrayList<Rater> allRaters, String rater_id) {
		int total = 0;
		for (Rater r : allRaters) {
			if (r.getID().equals(rater_id)) {
				total = r.getItemsRated().size();
			}
		}
		return total;
	}

//	to find the maximum number of ratings by any rater
	public void getMaxRatingsByRater(ArrayList<Rater> allRaters) {

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		for (Rater rater : allRaters) {
			map.put(rater.getID(), rater.getItemsRated().size());
		}

//		System.out.println("map------>:"+map);

		int maxValue = Collections.max(map.values());
//		System.out.println("maxValue ------>:"+maxValue);
		String maxKey = "";
		for (String s : map.keySet()) {
			if (map.get(s) == maxValue) {
				maxKey = s;
			}
		}
		System.out.println("maxKey: " + maxKey + ", " + "maxValue: " + maxValue);

	}

//	to find the number of ratings a particular movie has
	public int getRatingPerMovie(ArrayList<Rater> raters, String movie_id) {

		int numOfRatingPerMovie = 0;
		for (Rater rater : raters) {
			// use method of hasRating in class "Rater"
			if (rater.hasRating(movie_id)) {
				numOfRatingPerMovie += 1;
			}
		}
		return numOfRatingPerMovie;
	}

//	to determine how many different movies have been rated by all these raters
	public void totalMoviesRated(ArrayList<Rater> raters) {

		HashMap<String, Integer> movieRatingCounts = new HashMap<String, Integer>();

		for (int k = 0; k < raters.size(); k++) {
			Rater currRater = raters.get(k); // get current rater
			for (int i = 0; i < currRater.numRatings(); i++) { // loop through all movies rated by each Rater

				String currMovieID = currRater.getItemsRated().get(i);

				if (movieRatingCounts.containsKey(currMovieID)) {
					movieRatingCounts.put(currMovieID, movieRatingCounts.get(currMovieID) + 1);
				} else {
					movieRatingCounts.put(currMovieID, 1);
				}
			}
		}

		System.out.println("Number of movies rated: " + movieRatingCounts.size());
	}

	public static void main(String[] args) throws IOException {

		FirstRatings obj = new FirstRatings();

		obj.testLoadMovies();

//		obj.testLoadRaters();

//    	System.out.println(Arrays.asList(loadMovies("./data/ratedmovies_short.csv")));

//		testLoadMovies();	

//		System.out.println(loadRaters("ratings_short.csv"));

//		testLoadRaters();
	}
}
