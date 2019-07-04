package com.study.project.quiz.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.study.project.quiz.Question;
/**
 * 
 * @author prashanthrathinavel
 *
 */
public class QuizDaoImpl {
	private static QuizDaoImpl instance;

	static final String DB_URL = "jdbc:h2:~/test";
	static final String USER = "sa";
	static final String PASS = "";

	private static final String ADD_QUES = "INSERT into QUESTN (QUESTION,TOPIC,DIFFICULTY,ANSWER) values(?,?,?,?)";
	private static final String UPDT_QUES = "UPDATE QUESTN SET QUESTION=? WHERE QUESTION=? AND TOPIC=?";
	private static final String DEL_QUES = "DELETE FROM QUESTN  WHERE TOPIC = ? AND  QUESTION=?";
	private static final String SEAR_QUES = "SELECT QUESTION FROM QUESTN WHERE TOPIC=?";

	public static QuizDaoImpl getInstance() {
		if (instance == null) {
			instance = new QuizDaoImpl();
		}
		return instance;
	}

	/**
	 * This method is used to connect the application with the h2 database. 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	
	private Connection getConnection() throws SQLException, IOException {
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}

	/**
	 * This method is used to create questions(insert records) in the respective table in the h2 database.  
	 * @param ques
	 * @param tpc
	 * @param dff
	 * @param ans
	 * @return
	 * @throws Exception
	 */
	
	public boolean createQues(String ques, String tpc, String dff, String ans) throws Exception {
		boolean isCreate = false;
		try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(ADD_QUES);) {
			ps.setString(1, ques);
			ps.setString(2, tpc);
			ps.setString(3, dff);
			ps.setString(4, ans);
			ps.execute();
			isCreate = true;
		} catch (SQLException sqle) {
			throw new Exception();
		}
		return isCreate;
	}
	
/**
 * This method is used to update questions(update records) in the respective table in the h2 database.
 * @param tpc
 * @param oQues
 * @param nQues
 * @return
 * @throws Exception
 */
	
	public boolean updtQues(String tpc, String oQues, String nQues) throws Exception {
		boolean isUpdt = false;
		try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(UPDT_QUES);) {
			ps.setString(1, nQues);
			ps.setString(2, oQues);
			ps.setString(3, tpc);

			ps.execute();
			isUpdt = true;
		} catch (SQLException sqle) {
			throw new Exception();
		}
		return isUpdt;
	}
	
/**
 * This method is used to delete questions(delete records) in the respective table in the h2 database.
 * @param tpc
 * @param ques
 * @return
 * @throws Exception
 */
	
	public boolean delQues(String tpc, String ques) throws Exception {
		boolean isDel = false;
		try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(DEL_QUES);) {
			ps.setString(1, tpc);
			ps.setString(2, ques);

			ps.execute();
			isDel = true;
		} catch (SQLException sqle) {
			throw new Exception();
		}
		return isDel;
	}
	
/**
 * This method is used to search questions(display records) from the respective table in the h2 database.
 * @param tpc
 * @return
 */
	public List<Question> searQues(String tpc) {
		List<Question> qList = new ArrayList<Question>();
		try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(SEAR_QUES)) {
			ps.setString(1, tpc);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String ques = rs.getString(1);
				Question qus = new Question(ques);
				qList.add(qus);

			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return qList;

	}

}
