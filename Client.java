package zacebook1;

import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("WELCOME!!!");
		int value;
		String loopVariable = "yes";
		while (loopVariable.equals("yes")) {
			System.out
					.println("1.Add username\n2.add post\n3.Add friends\n4.Display post\n5.like post");
			value = sc.nextInt();
			sc.nextLine();
			switch (value) {
			case 1:
				System.out.println("enter name:");
				String username;
				username = sc.nextLine();
				System.out.println("enter userid:");
				String userId;
				userId = sc.nextLine();
				Service.addUser(username, userId);
				break;
			case 2:
				System.out.println("enter UserId:");
				userId = sc.nextLine();
				System.out.println("enter postid:");
				String postId = sc.nextLine();
				System.out.println("enter post tag:");
				String postTag = sc.nextLine();
				System.out.println("enter post:");
				String post = sc.nextLine();
				Service.addPost(userId, postId, postTag, post, 0);
				break;
			case 3:
				System.out.println("enter your userid");
				userId = sc.nextLine();
				try {
					Service.displayUsers(userId);
				} catch (Exception e) {
					System.out.println(e);
				}
				System.out.println("enter the friend's userid:");
				String friendUserId = sc.nextLine();
				Service.addFriend(userId, friendUserId);
				try {
					Service.addFriendToFriend(friendUserId, userId);
				} catch (Exception e) {
					System.out.println(e);
				}
				break;
			case 4:
				try {
					Service.displayPost();
				} catch (Exception e) {
					System.out.println(e);
				}
				break;
			case 5:
				try {
					Service.displayPost();
				} catch (Exception e) {
					System.out.println(e);
				}
				System.out.println("enter the postid:");
				String postid = sc.nextLine();
				Service.likePost(postid);
				break;
			}
			System.out.println("Do you want to show the menu again?");
			loopVariable = sc.nextLine();
		}
		sc.close();
	}

}
