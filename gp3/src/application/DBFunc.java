package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBFunc {
//  change url dependently
	static String url = "jdbc:mysql://127.0.0.1:3306/groupassignment";
	static String user = "root";
	static String password = "---";

	static Connection conn = null;
	static Statement stmt = null;

	public static void execDB(String sql) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			stmt.execute(sql);

		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static boolean verifyDB(String name, String table) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from " + table);
			while (rs.next()) {
				if (rs.getString(table + "Name").equals(name)) {
					return false;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static boolean verifyIdDB(String id, String table) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from " + table);
			while (rs.next()) {
				if (rs.getString(table + "ID").equals(id)) {
					return false;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static int nameToIdDB(String name, String table) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("select " + table + "ID from " + table + " where " + table + "Name ='" + name + "'");
			if (rs.next()) {
				return rs.getInt(table + "ID");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String getStrDB(int id, String table, String column) {
		String a = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + table + " where " + table + "ID = " + id);
			if (rs.next()) {
				a = rs.getString(column);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return a;
	}

	public static int getIntDB(int id, String table, String column) {
		int a = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + table + " where " + table + "ID = '" + id + "'");
			if (rs.next()) {
				a = rs.getInt(column);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return a;
	}

	public static int getLastDB(String name) {
		int out = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + name + " ORDER BY " + name + "ID DESC LIMIT 1");
			if (rs.next()) {
				out = rs.getInt(name + "ID");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return out + 1;
	}

}
