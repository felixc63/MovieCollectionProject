import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a caster: ");
        String searchTerm = scanner.nextLine().toLowerCase();
        ArrayList<String> casts = new ArrayList<>();
        ArrayList<String> results = new ArrayList<>();
        //Adds all casts in the movie file.
        for(int i = 0; i < movies.size(); i++){
            int count = 0;
            String cast = movies.get(i).getCast() + "|";
            for(int k = 0; k < cast.length(); k++){
                if(cast.charAt(k) == '|'){
                    count++;
                }
            }
            for(int j = 0; j < count; j++){
                String temp = cast.substring(0,cast.indexOf("|"));
                if(casts.indexOf(temp) == -1) {
                    casts.add(temp);
                    cast = cast.substring(cast.indexOf("|") + 1);
                }
            }
        }
        //Finds the related casts according to the search term
        for(int i = 0; i < casts.size(); i++){
            if(casts.get(i).toLowerCase().indexOf(searchTerm) != -1){
                results.add(casts.get(i));
            }
        }
        //Selection Sort
        for(int i = 0; i < results.size()-1; i++){
            int minIdx = i;
            for(int k = i+1; k < results.size(); k++){
                if(results.get(k).compareTo(results.get(minIdx)) < 0){
                    minIdx = k;
                }
            }
            if(i!=minIdx){
                String temp1 = results.get(i);
                String temp2 = results.get(minIdx);
                results.set(i,temp2);
                results.set(minIdx,temp1);
            }
        }
        //Display casts
        for (int i = 0; i < results.size(); i++)
        {
            String name = results.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + name);
        }
        System.out.println("Which caster would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        String cast = results.get(choice-1);
        System.out.println("Selected: " + cast);
        ArrayList<Movie> castMovies = new ArrayList<>();
        for(int i = 0; i < movies.size(); i++){
            if(movies.get(i).getCast().indexOf(cast) != -1){
                castMovies.add(movies.get(i));
            }
        }
        sortResults(castMovies);
        for (int i = 0; i < castMovies.size(); i++)
        {
            String title = castMovies.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choiceMovie = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = castMovies.get(choiceMovie - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            if(movies.get(i).getKeywords().toLowerCase().indexOf(searchTerm) != -1) {
                String movieKeyword = movies.get(i).getKeywords();
                movieKeyword = movieKeyword.toLowerCase();

                if (movieKeyword.indexOf(searchTerm) != -1) {
                    //add the Movie object to the results list
                    results.add(movies.get(i));
                }
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        ArrayList<String> genres = new ArrayList<>();
        //Adds all available genres in the movie file.
        for(int i = 0; i < movies.size(); i++){
            int count = 0;
            String genre = movies.get(i).getGenres() + "|";
            for(int k = 0; k < genre.length(); k++){
                if(genre.charAt(k) == '|'){
                    count++;
                }
            }
            for(int j = 0; j < count; j++){
                String temp = genre.substring(0,genre.indexOf("|"));
                if(genres.indexOf(temp) == -1) {
                    genres.add(temp);
                    genre = genre.substring(genre.indexOf("|") + 1);
                }
            }
        }
        //Selection Sort
        for(int i = 0; i < genres.size()-1; i++){
            int minIdx = i;
            for(int k = i+1; k < genres.size(); k++){
                if(genres.get(k).compareTo(genres.get(minIdx)) < 0){
                    minIdx = k;
                }
            }
            if(i!=minIdx){
                String temp1 = genres.get(i);
                String temp2 = genres.get(minIdx);
                genres.set(i,temp2);
                genres.set(minIdx,temp1);
            }
        }
        //Display genres
        for (int i = 0; i < genres.size(); i++)
        {
            String name = genres.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + name);
        }
        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        String genre = genres.get(choice-1);
        System.out.println("Selected: " + genre);
        ArrayList<Movie> genreMovies = new ArrayList<>();
        for(int i = 0; i < movies.size(); i++){
            if(movies.get(i).getGenres().indexOf(genre) != -1){
                genreMovies.add(movies.get(i));
            }
        }
        sortResults(genreMovies);
        for (int i = 0; i < genreMovies.size(); i++)
        {
            String title = genreMovies.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choiceMovie = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = genreMovies.get(choiceMovie - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Movie> highestRated = new ArrayList<>();
        //Clone object movies to object highestRated
        for(Movie movie: movies){
            highestRated.add(movie);
        }
        //Selection Sort
        for(int i = 0; i < highestRated.size()-1; i++){
            int maxIdx = i;
            for(int k = i+1; k < highestRated.size(); k++){
                if(highestRated.get(k).getUserRating() > highestRated.get(maxIdx).getUserRating()){
                    maxIdx = k;
                }
            }
            if(i!=maxIdx){
                Movie temp1 = highestRated.get(i);
                Movie temp2 = highestRated.get(maxIdx);
                highestRated.set(i,temp2);
                highestRated.set(maxIdx,temp1);
            }
        }
        //Prints until 50
        for (int i = 0; i < 50; i++)
        {
            String title = highestRated.get(i).getTitle();
            double rating = highestRated.get(i).getUserRating();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title + ": " + rating);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = highestRated.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> highestRevenue = new ArrayList<>();
        //Clone object movies to object highestRevenue
        for(Movie movie: movies){
            highestRevenue.add(movie);
        }
        //Selection Sort
        for(int i = 0; i < highestRevenue.size()-1; i++){
            int maxIdx = i;
            for(int k = i+1; k < highestRevenue.size(); k++){
                if(highestRevenue.get(k).getRevenue() > highestRevenue.get(maxIdx).getRevenue()){
                    maxIdx = k;
                }
            }
            if(i!=maxIdx){
                Movie temp1 = highestRevenue.get(i);
                Movie temp2 = highestRevenue.get(maxIdx);
                highestRevenue.set(i,temp2);
                highestRevenue.set(maxIdx,temp1);
            }
        }
        //Prints until 50
        for (int i = 0; i < 50; i++)
        {
            String title = highestRevenue.get(i).getTitle();
            int revenue = highestRevenue.get(i).getRevenue();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title + ": $" + revenue);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = highestRevenue.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}