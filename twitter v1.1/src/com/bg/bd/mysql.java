package com.bg.bd;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * 
 * @author KRISTIAN
 * define the mysql conection to store tweets
 */

public class mysql {
	private String DATABASE;
	private String HOST;
	private String USER;
	private String PASSWORD;
	private String URL;
	private Connection CON;
	private PreparedStatement PSTMT;
	
	//Estableciendo los parametros iniciales del mysql
	public mysql (String DATABASE_, String HOST_, String USER_, String PASSWORD_){
		DATABASE = DATABASE_;
		HOST = HOST_;
		USER = USER_;
		PASSWORD = PASSWORD_;
		URL = "jdbc:mysql://" + HOST + ":3306/" + DATABASE; 
	}
	
	//Testing the connection with the database
	public void Test(){
		try{
			CON = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Success!");
		}catch(Exception e){
			System.out.println("Error(Test) -> " + e.toString());
		}finally{
			try {
				CON.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//funcion para verificar si los tweets estan almacenados
	public boolean Buscar(String id_tweet){
		int rpta = 1;
		try {
			CON = DriverManager.getConnection(URL, USER, PASSWORD);
			CallableStatement CS = CON.prepareCall(" { call get_id_tweets(?,?) } ");
			CS.setString(1,id_tweet);
			CS.registerOutParameter(2,java.sql.Types.INTEGER);
			CS.execute();
			rpta = CS.getInt(2);
			if (rpta == 0){ 
				System.out.println("Inserto!");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos(Buscar). -> " + e.toString());
		}
		System.out.println("No Inserto!");
		return true;
	}
	
	//funcion de insertar en la tabla tweets
	public void InsertarTweets(String id_tweet, String text, String from, String to, String date){
		int rpta = 1;
		try{
			CON = DriverManager.getConnection(URL, USER, PASSWORD);
			
			CallableStatement CS = CON.prepareCall(" { call get_id_tweets(?,?) } ");
			CS.setString(1,id_tweet);
			CS.registerOutParameter(2,java.sql.Types.INTEGER);
			CS.execute();
			rpta = CS.getInt(2);
			
			if (rpta == 0){
				PSTMT = CON.prepareStatement(" INSERT INTO tweets(id_tweet, text, from_, to_,date) VALUES(?,?,?,?,?) ");
				PSTMT.setString(1, id_tweet);
				PSTMT.setString(2, text);
				PSTMT.setString(3, from);
				PSTMT.setString(4, to);
				PSTMT.setString(5, date);
				PSTMT.executeUpdate();
				System.out.println("Inserto!");
			}else{
				System.out.println("No Inserto!");
			}	
			
		}catch(Exception e){
			System.out.println("Error al conectar con la base de datos(Insertar). -> " + e.toString());
		}finally{
			try {
				CON.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public int InsertarUser(String usuario){
		int rpta = 0;
		try{
			CON = DriverManager.getConnection(URL, USER, PASSWORD);
			CallableStatement CS = CON.prepareCall(" { call get_id_user(?,?) } ");
			CS.setString(1,usuario);
			CS.registerOutParameter(2,java.sql.Types.INTEGER);
			CS.execute();
			rpta = CS.getInt(2);
			if (rpta == 0){
				PSTMT = CON.prepareStatement(" INSERT INTO users(user) VALUES(?) ");
				PSTMT.setString(1, usuario);
				PSTMT.executeUpdate();
				System.out.println("Inserto!");	
			}else{
				System.out.println("No Inserto!");
			}	
			CS = CON.prepareCall(" { call get_id_user(?,?) } ");
			CS.setString(1,usuario);
			CS.registerOutParameter(2,java.sql.Types.INTEGER);
			CS.execute();
			rpta = CS.getInt(2);
		}catch(Exception e){
			System.out.println("Error al conectar con la base de datos(Insertar). -> " + e.toString());
		}finally{
			try {
				CON.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rpta;
	}
	
	//Actualizacion de algun campo
	public void Actualizar(String id_tweet, String campo_name, String campo){
		int rpta = 1;
		try{
			CON = DriverManager.getConnection(URL, USER, PASSWORD);
			
			CallableStatement CS = CON.prepareCall(" { call get_id_tweets(?,?) } ");
			CS.setString(1,id_tweet);
			CS.registerOutParameter(2,java.sql.Types.INTEGER);
			CS.execute();
			rpta = CS.getInt(2);
			
			if (rpta == 1){
				CON.setAutoCommit(true);
				PSTMT = CON.prepareStatement("UPDATE tweets SET " + campo_name + " = ? WHERE id_tweet = ? ");
				PSTMT.setString(1, campo);
				PSTMT.setString(2, id_tweet);
				System.out.println("Actualizo!");
			}else{
				System.out.println("No Actualizo!");
			}	
			
		}catch(Exception e){
			System.out.println("Error al conectar con la base de datos(Actualizar). -> " + e.toString());
		}finally{
			try {
				CON.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
