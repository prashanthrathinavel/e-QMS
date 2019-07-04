package com.study.project.quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import com.study.project.quiz.dao.QuizDaoImpl;

/**
 * 
 * @author prashanthrathinavel
 *
 */
public class Index {
	private static String answer[] = new String[50];
	static int count = 0;
	static int score = 0;
	static Scanner sc = new Scanner(System.in);
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:~/test";
	private static QuizDaoImpl dao = QuizDaoImpl.getInstance();

	// Database credentials
	static final String USER = "sa";
	static final String PASS = "";

	public static void main(String[] args) throws Exception {
		System.out.println("Weclome to Quiz Master!");
		System.out.println("-----------------------------------------");
		System.out.println("Please choose an option below : \n1. Teacher\n2. Student");
		System.out.println("-----------------------------------------");

		Scanner sc = new Scanner(System.in);
		int opt = Integer.parseInt(sc.nextLine());
		if (opt == 2) {
			System.out.println("Welcome to Student Panel!");
			quizTaker();
		} else if (opt == 1) {
			System.out.println("Welcome to Admin Panel!");
			quizMaker();
		}

	}

	private static void quizMaker() throws Exception {
		Scanner scn = new Scanner(System.in);
		String ans = "";
		while (!ans.equals("quit")) {

			ans = menuDsply(scn);

			switch (ans) {
			case "1":
				createQues(scn);
				break;
			case "2":
				updtQues(scn);
				break;
			case "3":
				delQues(scn);
				break;
			case "4":
				searQues(scn);
				break;
			
			case "q":
				System.out.println("Thank You!");
				break;

			default:
				System.out.println("Please enter valid option");
				break;
			}
		}
	}

	private static void searQues(Scanner scn) {

		System.out.println("You are going to Search a Question:");
			
		System.out.println("Please Enter Topic :");
		String tpc = scn.nextLine();		
	
		
		try {
			List<Question> quesList = dao.searQues(tpc);
			System.out.println("Questions retreived based on Topic given below :");
			for(Question q : quesList) {
				System.out.println(q.getQuestion());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
			
	}

	private static void delQues(Scanner scn) {

		System.out.println("You are going to Delete a Question:");
			
		System.out.println("Please Enter Topic :");
		String tpc = scn.nextLine();		
		System.out.println("Please Enter Question :");
		String ques = scn.nextLine();
		
		boolean isDel = false;
		try {
			isDel = dao.delQues(tpc, ques);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(isDel) {
			System.out.println("Question has been Deleted successfully.");
		}		
			
	}

	private static void updtQues(Scanner scn) {
		System.out.println("You are going to Update a Question:");
			
		System.out.println("Please Enter Topic :");
		String tpc = scn.nextLine();		
		System.out.println("Please Give Old Question :");
		String oQues = scn.nextLine();
		System.out.println("Please Give New Question :");
		String nQues = scn.nextLine();
		
		boolean isUpdt = false;
		try {
			isUpdt = dao.updtQues(tpc, oQues, nQues);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(isUpdt) {
			System.out.println("Question has been Updated successfully.");
		}		
	}

	private static void createQues(Scanner scn) throws Exception {
		System.out.println("You are going to create a Question:");
		System.out.println("Please Enter Question :");
		String ques = scn.nextLine();		
		System.out.println("Please Enter Topic :");
		String tpc = scn.nextLine();		
		System.out.println("Please Enter Difficulty :");
		String dff = scn.nextLine();		
		System.out.println("Please Enter Answer :");
		String ans = scn.nextLine();
		
		boolean isCreate = dao.createQues(ques, tpc, dff, ans);
		if(isCreate) {
			System.out.println("Question has been Created successfully.");
		}

	}

	private static String menuDsply(Scanner scanner) {
		String answer;
		System.out.println("-----------------------------------------");

		System.out.println("1. Create Question");
		System.out.println("2. Update Question");
		System.out.println("3. Delete Question");
		System.out.println("4. Search Question");
		System.out.println("q. Quit");
		System.out.println("-----------------------------------------");

		System.out.println("Please enter your choice :");
		answer = scanner.nextLine();
		return answer;
	}
	
	public static void quizTaker() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);

			System.out.println("Connecting to a database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			System.out.println("Connected database successfully...");
			stmt = conn.createStatement();
			String sql = "";
			stmt.executeUpdate(sql);

			sql = "SELECT question age FROM questn";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				String question = rs.getString("question");

				System.out.println("Answer the question : " + question);
				answer[count] = sc.nextLine();
				count++;

			}
			count = 0;
			sql = "SELECT answer age FROM questn";
			ResultSet rs2 = stmt.executeQuery(sql);
			while (rs2.next()) {
				// Retrieve by column name

				String answers = rs2.getString("answer");
				if (answer[count].equals(answers)) {
					score++;
				}
				count++;
				// Display values

			}
			System.out.println("Your Score is : " + score);
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("Thank You!");

	}
}