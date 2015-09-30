
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Set;

import javax.security.auth.Subject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weblogic.security.Security;

/**
 * Servlet implementation class SubjectPrincipals
 */
@WebServlet(urlPatterns = {"/secure/subjectPrincipals", "/secure/sso/subjectPrincipals"})
public class SubjectPrincipals extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubjectPrincipals() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Subject subject = Security.getCurrentSubject();
		Set<Principal> principals = subject.getPrincipals();
		for (Principal principal : principals) {
			out.println("Class:" + principal.getClass());
			out.println("Name:" + principal.getName());
		}
	}

}
