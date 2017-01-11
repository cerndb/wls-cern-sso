

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserPrincipals
 */
@WebServlet(urlPatterns = {"/secure/simpleUserPrincipal", "/secure/sso/simpleUserPrincipal"})
public class SimpleUserPrincipal extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SimpleUserPrincipal() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		if(request.getUserPrincipal()!=null){
			out.println("Class: " + request.getUserPrincipal().getClass());
			out.println("UserPrincipal: " + request.getUserPrincipal());
			out.println("RemoteUser: " + request.getRemoteUser());
		} else {
			out.println("NO User principal from HttpServletRequest");
		}
	}

}
