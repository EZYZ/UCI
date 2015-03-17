package servlets.details_pages;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import queries.SearchQuery;
import data_beans.Movie;

@WebServlet("/FabFlixMovieDetails")
public class FabFlixMovieDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/MovieDB", type=javax.sql.DataSource.class)
	private DataSource dataSource;
	
	private Connection connection;
    
    public FabFlixMovieDetails() 
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			if (connection == null || connection.isClosed())
			{
				connection = dataSource.getConnection();
			}
			
			// Find movie based on id
			String movieId = request.getParameter("movieid");
			ArrayList<Movie> movies = SearchQuery.searchMovies(movieId, "", "", "", "", "", "","", false, connection);
			
			if (movies.size() == 1)
			{
				Movie movie = movies.get(0);
				
				request.setAttribute("movie", movie);
				request.setAttribute("stars", movie.getStars());
				request.setAttribute("genres", movie.getGenres());
				request.getRequestDispatcher("FabFlixMovieDetails.jsp").forward(request, response);
			}
			
			if (connection != null && !connection.isClosed())
			{
				connection.close();
			}
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
