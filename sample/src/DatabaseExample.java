import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/databaseExample", "/secure/databaseExample" })
public class DatabaseExample extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3620077727343691675L;

	private IdentifierGenerator identifierGenerator = new IdentifierGenerator();

	private Connection getConnection(String dbType) throws IOException, SQLException, ClassNotFoundException {
		String jdbcClass = "oracle.jdbc.OracleDriver";
		String dbPropertiesFile = "sample.oracle.properties";
		if(dbType!=null && dbType.equals("mysql")){
			jdbcClass="com.mysql.jdbc.Driver";
			dbPropertiesFile="sample.mysql.properties";
		}
		Class.forName(jdbcClass);
		Connection conn = null;
		Properties sampleProperties = new Properties();
		InputStream inputStream = DatabaseExample.class.getClassLoader().getResourceAsStream(dbPropertiesFile);
		sampleProperties.load(inputStream);
		Properties connectionProps = new Properties();
		connectionProps.put("user", sampleProperties.getProperty("db.user"));
		connectionProps.put("password", sampleProperties.getProperty("db.password"));
		conn = DriverManager.getConnection(sampleProperties.getProperty("db.url"), connectionProps);
		System.out.println("Connected to DB: " + sampleProperties.getProperty("db.url"));
		return conn;
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		PreparedStatement pstmt;
		try (Connection con = getConnection(req.getParameter("db"))) {
			pstmt = con.prepareStatement("DELETE employee WHERE id = ?");
			pstmt.setString(1, req.getParameter("id"));	
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		out.println(req.getParameter("id"));
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		PreparedStatement pstmt;
		try (Connection con = getConnection(req.getParameter("db"))) {
			pstmt = con.prepareStatement("SELECT * FROM employee");
			ResultSet resultSet = pstmt.executeQuery();
			if (resultSet != null) {
				while (resultSet.next()) {
					out.print(resultSet.getString("id"));
					out.print("-");
					out.print(resultSet.getString("name"));
					out.print(" ");
					out.print(resultSet.getString("last_name"));
					out.print(" ");
					out.println();
				}
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		PreparedStatement pstmt;
		String id = null;
		try (Connection con = getConnection(req.getParameter("db"))) {
			pstmt = con.prepareStatement("INSERT INTO employee (id,name,last_name) VALUES (?,?,?)");
			id = identifierGenerator.nextId();
			pstmt.setString(1, id);
			pstmt.setString(2, req.getParameter("name"));
			pstmt.setString(3, req.getParameter("lastName"));
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		out.println(id);
	}
}
