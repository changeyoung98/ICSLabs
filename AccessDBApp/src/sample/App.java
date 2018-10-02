package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String passwd = "reins2011";
		Connection con = DriverManager.getConnection(url, user, passwd);
		System.out.println(con.getTransactionIsolation());

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select id, firstname, lastname, phone, email from users");
		while (rs.next()) {
			System.out.println("User No." + rs.getString("id"));
			System.out.println("Username: " + rs.getString("firstname") + " " + rs.getString("lastname"));
			System.out.println("Phone: " + rs.getString("phone"));
			System.out.println("Email: " + rs.getString("email"));
		}
	}
}
