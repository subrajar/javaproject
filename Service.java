package zacebook1;

import java.sql.*;
import java.util.*;

public class Service {

	public static Connection connect() {
		Connection con = null;
		try {
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

	public static void addPost(String userId, String postId, String postTag, String post, int likes, String postDate) {
		try {
			Connection con = connect();
			String query = "insert into post values(?,?,?,?,?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, userId);
			st.setString(2, postId);
			st.setString(3, postTag);
			st.setString(4, post);
			st.setInt(5, likes);
			st.setString(6, postDate);
			int row = st.executeUpdate();
			System.out.println(row + " row affected");
			closeConnection(con);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void displayUsers(String userId) throws Exception {
		Connection con = connect();
		String query = "select * from users where not userid='" + userId + "'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		String userIds = "";
		while (rs.next()) {
			userIds = "userId: " + rs.getString("userid") + "\tName: " + rs.getString("username");
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
		String query = "select user_id from friends where user_id='" + friendUserId + "'and friendid='" + userId + "'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if (!rs.next()) {
			addFriend(friendUserId, userId);
		}
		closeConnection(con);

	}

	public static void displayPost(String userId) throws Exception {
		Connection con = connect();
		ArrayList<String> friendList = new ArrayList<>();
		String query = "select friendid from friends where user_id='" + userId + "'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			friendList.add(rs.getString("friendid"));
		}
		for (int i = 0; i < friendList.size(); i++) {
			query = "select * from post where userid='" + friendList.get(i) + "'order by postdate desc limit 10 ";
			Statement st1 = con.createStatement();
			ResultSet rs1 = st1.executeQuery(query);
			String userIds = "";
			int cnt = 1;
			System.out.println("Recent 10 post:");
			while (rs1.next()) {
				userIds = "post" + cnt + ":\nUserId:" + rs1.getString(1) + "\nPostId:" + rs1.getString(2) + "\nPostTag:"
						+ rs1.getString(3) + "\nPost:" + rs1.getString(4) + "\nLikes:" + rs1.getInt(5) + "\nDate:"
						+ rs1.getString(6);
				System.out.println(userIds);
				cnt++;
			}
		}
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

	public static void displayFriends(String userId) throws Exception {
		Connection con = connect();
		String query = "select friendid from friends where user_id='" + userId + "'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		String friendIds = "";
		while (rs.next()) {
			friendIds = rs.getString("friendid");
			System.out.println(friendIds);

		}
		closeConnection(con);

	}

}
