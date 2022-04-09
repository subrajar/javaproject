package zacebook1;

import java.sql.*;

public class Service {

	public static Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zbook", "root", "subraja");

		} catch (Exception e) {

			System.out.println(e);
		}
		return con;
	}

	public static void closeConnection(Connection con) throws Exception {
		con.close();
	}

	public static void addUser(String username, String userId) {
		try {
			Connection con = connect();
			String query = "insert into users values(?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, username);
			st.setString(2, userId);
			int row = st.executeUpdate();
			System.out.println(row + " row affected");
			closeConnection(con);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void addPost(String userId, String postId, String postTag, String post, int likes) {
		try {
			Connection con = connect();
			String query = "insert into post values(?,?,?,?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, userId);
			st.setString(2, postId);
			st.setString(3, postTag);
			st.setString(4, post);
			st.setInt(5, likes);
			int row = st.executeUpdate();
			System.out.println(row + " row affected");
			closeConnection(con);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void displayUsers(String userId) throws Exception {
		Connection con = connect();
		String query = "select userid from users where not userid=" + userId;
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		String userIds = "";
		while (rs.next()) {
			userIds = rs.getString(2);
			System.out.println(userIds);
		}
		closeConnection(con);

	}

	public static void addFriend(String userId, String friendUserId) {
		try {
			Connection con = connect();
			String query = "insert into friends values(?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, userId);
			st.setString(2, friendUserId);
			int row = st.executeUpdate();
			System.out.println(row + " row affected");
			closeConnection(con);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void addFriendToFriend(String friendUserId, String userId) throws Exception {
		Connection con = connect();
		String query = "select userid from friends where user_id=" + friendUserId + "and friendid=" + userId;
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		String friendId = "";
		rs.next();
		friendId = rs.getString(2);
		if (friendId.equals("")) {
			addFriend(friendUserId, userId);
		}
		closeConnection(con);

	}

	public static void displayPost() throws Exception {
		Connection con = connect();
		String query = "select * from post ";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		String userIds = "";
		int cnt = 1;
		while (rs.next()) {
			userIds = "post" + cnt + ":" + rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + ","
					+ rs.getString(4) + "," + rs.getInt(5);
			System.out.println(userIds);
			cnt++;
		}
		System.out.println("\n\n");
		closeConnection(con);

	}

	static void likePost(String post_id) {
		try {
			Connection con = connect();
			Statement st = con.createStatement();
			String query = "select likes from post where postid='" + post_id + "'";
			ResultSet rs = st.executeQuery(query);
			int likes;
			rs.next();
			likes = rs.getInt("likes");
			likes++;
			String query1 = "UPDATE post SET likes=" + likes + "\nWHERE postid='" + post_id + "';";
			int row = st.executeUpdate(query1);
			System.out.println(row + " row affected");
			closeConnection(con);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
