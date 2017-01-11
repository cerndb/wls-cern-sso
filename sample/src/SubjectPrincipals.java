import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Set;

import javax.security.auth.Subject;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.cern.sso.weblogic.principals.CernWlsGroupPrincipal;
import ch.cern.sso.weblogic.principals.CernWlsUserPrincipal;
import weblogic.security.Security;

/**
 * Servlet implementation class SubjectPrincipals
 */
@WebServlet(urlPatterns = { "/secure/subjectPrincipals",
		"/secure/sso/subjectPrincipals" })
@ServletSecurity(@HttpConstraint(rolesAllowed = "AllUsers"))
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
		if (subject != null) {
			Set<Principal> principals = subject.getPrincipals();
			if (principals != null && principals.size() > 0) {
				for (Principal principal : principals) {
					out.println("Class:" + principal.getClass());
					out.println("Name:" + principal.getName());
					if (principal instanceof CernWlsUserPrincipal) {
						CernWlsUserPrincipal cernWlsUserPrincipal = (CernWlsUserPrincipal) principal;
						out.println(cernWlsUserPrincipal.getBuilding());
						out.println(cernWlsUserPrincipal.getCommonName());
						out.println(cernWlsUserPrincipal.getDepartment());
						out.println(cernWlsUserPrincipal.getDisplayName());
						out.println(cernWlsUserPrincipal.getDn());
						out.println(cernWlsUserPrincipal.getEmailAddress());
						out.println(cernWlsUserPrincipal.getFirstName());
						out.println(cernWlsUserPrincipal.getGidNumber());
						out.println(cernWlsUserPrincipal.getGuid());
						out.println(cernWlsUserPrincipal.getHomeInstitute());
						out.println(cernWlsUserPrincipal.getIdentityClass());
						out.println(cernWlsUserPrincipal.getLastName());
						out.println(cernWlsUserPrincipal.getPhoneNumber());
						out.println(cernWlsUserPrincipal.getPreferredLanguage());
						out.println(cernWlsUserPrincipal.getRole());
						out.println(cernWlsUserPrincipal.getUpn());
						out.println(cernWlsUserPrincipal.getPersonID());
						out.println(cernWlsUserPrincipal.getGroups().toString());
					}
					if (principal instanceof CernWlsGroupPrincipal) {
						CernWlsGroupPrincipal cernWlsGroupPrincipal = (CernWlsGroupPrincipal) principal;
						out.println(cernWlsGroupPrincipal.toString());
					}
				}
			} else {
				out.println("NO principals from javax.security.auth.Subject;");
			}
		} else {
			out.println("NO Subject from weblogic.security.Security");
		}
	}

}
