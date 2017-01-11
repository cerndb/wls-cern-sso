
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SimpleHeadersTable
 */
@WebServlet("/showEnv")
public class ShowEnvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowEnvServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Map<String, String> env = System.getenv();
		out.println("<p>");
		out.println("<h2>Environment</h2>");
		env.forEach((k, v) -> out.println(k + " " + v));
		out.println("</p>");
		out.println("<p>");
		out.println("<h2>Properties</h2>");
		System.getProperties().list(out);
		out.println("</p>");
	}

}
