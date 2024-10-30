package com.korebap.app.biz.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// XxxUtil
// == 패턴화(템플릿화)된 코드
public class JDBCUtil {

   // final : 상수화
	private static final String driverName="com.mysql.cj.jdbc.Driver";
	private static final String url="jdbc:mysql://localhost:3306/fishing";
	private static final String userName="root";
	private static final String password="1234";
	
   
   // [1,2]단계
   public static Connection connect() {
      Connection conn=null;
      
      try {
         Class.forName(driverName);
      } catch (ClassNotFoundException e) {
         System.err.println("[1]단계 실패 ...");
      } finally {
         System.err.println("드라이버를 메모리에 로드(load,적재)");
      }
      
      try {
         conn=DriverManager.getConnection(url, userName, password);
      } catch (SQLException e) {
    	  e.printStackTrace();
         System.err.println("[2]단계 실패 ...");
      } finally {
         System.err.println("연결 객체 확보");
      }
      
      return conn;
   }
   
   // [4]단계
   public static boolean disconnect(PreparedStatement pstmt,Connection conn) {
      try {
         pstmt.close();
         conn.close();
      } catch (SQLException e) {
         System.err.println("[4]단계 실패 ...");
         return false;
      } finally {
         System.err.println("연결 해제");
      }
      return true;
   }
   
}


