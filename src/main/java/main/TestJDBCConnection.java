package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJDBCConnection {
	private static final String create_table_query = "CREATE TABLE categories (id integer , name text) ";
	private static final String insert_table_query = "INSERT INTO categories (id, name) VALUES (?,?) ";
	private static final String update_table_query = "UPDATE categories SET name = ? WHERE id = ?";
	private static final String delete_table_query = "DELETE FROM categories WHERE id = ?";
	private static final String show_table_query = "SELECT * FROM categories ";
	
	public static void main(String[] args) throws SQLException {
		
		String url = "jdbc:postgresql://localhost/ecommerce?user=postgres&password=123&ssl=false";
		Connection conn = DriverManager.getConnection(url);
		
		Statement stm = conn.createStatement();
		createCategoryTable(stm);
		addCategory(conn, 18, "Beauty");
		
		updateCategoryName(conn, 4, "Sport");
		deleteCategory(conn, 777);
		
		showCategories(stm);
		

	}
	
	public static void createCategoryTable(Statement stm) {
		try {
			stm.executeUpdate(create_table_query);
		} catch (SQLException e) {
			System.out.println("Can not create table!");
		}
			
	} 		
	
	
	public static void addCategory(Connection conn, int id, String name) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(insert_table_query);
        
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.executeUpdate();
           
	}

	public static void updateCategoryName(Connection conn, int id, String name) {
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(update_table_query);
			ps.setString(1, name);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (SQLException e1) {
			System.out.println("Can not update record!");
		}		
	}
	
	public static void deleteCategory(Connection conn, int id) {
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(delete_table_query);
			ps.setInt(1, id);
			int res = ps.executeUpdate();
			if( res==0 ) System.out.println("No records to delete");
		} catch (SQLException e) {
			System.out.println("Can not delete record!");
		}
	}
	
	public static void showCategories(Statement stm) {
		try {
			ResultSet res;
			res = stm.executeQuery(show_table_query);
			System.out.println("Category table:");
			System.out.println("---------------------------");
			while (res.next()) {
				int id = res.getInt("id");
				String name = res.getString("name");
				System.out.printf("|%3d |%-20s| \n",  id , name);
				
			}
			System.out.print("---------------------------");
		} catch (SQLException e) {

		}
		

	}
}

