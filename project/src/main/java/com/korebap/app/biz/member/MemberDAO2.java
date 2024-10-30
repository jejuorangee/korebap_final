package com.korebap.app.biz.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
//DAO는 DB와 가장 가까운 메모리에 저장
//DAO에 사용되며, DB와 상호작용하는 객체
public class MemberDAO2 {
   // 회원가입
   private final String MEMBER_INSERT = "INSERT INTO MEMBER (MEMBER_ID, MEMBER_PASSWORD, MEMBER_NICKNAME, MEMBER_NAME, MEMBER_PHONE, MEMBER_ADDRESS, MEMBER_ROLE) \r\n"
         + "VALUES (?, ?, ?, ?, ?, ?, ?)";
   // 회원 탈퇴
   private final String MEMBER_DELETE = "DELETE FROM MEMBER WHERE MEMBER_ID=?";
   // 회원 정보 수정
   private final String MEMBER_UPDATE_PROFILE = "UPDATE MEMBER SET MEMBER_PROFILE = ? WHERE MEMBER_ID = ?"; 
   // 비밀번호 변경                                                                           
   private final String MEMBER_UPDATE_PASSWORD = "UPDATE MEMBER SET MEMBER_PASSWORD = ? WHERE MEMBER_ID = ?"; 
   // 이름+ 닉네임 변경                                                                       
   private final String MEMBER_UPDATE_NAME_NICKNAME = "UPDATE MEMBER SET MEMBER_NAME = COALESCE(?, MEMBER_NAME), MEMBER_NICKNAME = COALESCE(?, MEMBER_NICKNAME) WHERE MEMBER_ID = ?"; // 이름
   // 회원 비활성화 (계정 차단)                                                                                                                          
   private final String MEMBER_UPDATE_ROLE = "UPDATE MEMBER SET MEMBER_ROLE = 'BANNED' WHERE MEMBER_ID = ?"; 
   // 회원 전체보기
   private final String MEMBER_SELECTALL = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_NICKNAME, MEMBER_PROFILE, MEMBER_REGISTRATION_DATE, MEMBER_ROLE, MEMBER_PHONE, MEMBER_ADDRESS FROM MEMBER ORDER BY MEMBER_REGISTRATION_DATE DESC";
   // 회원 전체 수 출력
   private final String MEMBER_SELECTONE_COUNT = "SELECT COUNT(MEMBER_ID) FROM MEMBER";
   // 일반회원 전체 수 출력
   private final String MEMBER_USER_SELECTONE_COUNT = "SELECT COUNT(MEMBER_ID) FROM MEMBER WHERE MEMBER_ROLE='USER'";
   // 사장님 전체 수 출력
   private final String MEMBER_OWNER_SELECTONE_COUNT = "SELECT COUNT(MEMBER_ID) FROM MEMBER WHERE MEMBER_ROLE='OWNER'";
   // 아이디 중복검사 - 비동기 중복 검사
   private final String SELECTONE_MEMBER_ID = "SELECT MEMBER_ID FROM MEMBER WHERE MEMBER_ID = ?"; 
   // 닉네임 중복검사 - 비동기 중복 검사
   private final String SELECTONE_MEMBER_NICKNAME = "SELECT MEMBER_NICKNAME FROM MEMBER WHERE MEMBER_NICKNAME = ?"; 
   // 로그인
   private final String SELECTONE_LOGIN = "SELECT MEMBER_ID, MEMBER_ROLE, MEMBER_PASSWORD, MEMBER_NICKNAME, MEMBER_NAME, MEMBER_PROFILE, MEMBER_REGISTRATION_DATE \r\n"
         + "FROM MEMBER WHERE MEMBER_ID = ? AND MEMBER_PASSWORD = ?"; // 로그인 확인
   // 마이페이지
   private final String SELECTONE_MYPAGE = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_NICKNAME, MEMBER_PHONE, MEMBER_ADDRESS, MEMBER_PROFILE FROM MEMBER WHERE MEMBER_ID = ?"; // 마이페이지
   // 비밀번호 확인 -비동기
   private final String SELECTONE_MEMBER_PASSWORD = "SELECT MEMBER_ID, MEMBER_PASSWORD FROM MEMBER WHERE MEMBER_ID = ?"; // 회원의
   // 회원 ID 랜덤으로 출력을 위한 member_id 전체 select >> 크롤링
   private final String RANDOM_MEMBER_ID = "SELECT MEMBER_ID FROM MEMBER ORDER BY RAND() LIMIT 1";
   // 사장님 ID 랜덤으로 출력을 위한 member_id 전체 select >> 크롤링
   private final String RANDOM_MEMBER_OWNER_ID="SELECT MEMBER_ID FROM MEMBER WHERE MEMBER_ROLE = 'OWNER' ORDER BY RAND() LIMIT 1";
   // 일반 회원 ID로 검색 - 10/29 추가
   private final String SELECTALL_USER_SEARCH ="SELECT MEMBER_ID, MEMBER_NAME, MEMBER_NICKNAME, MEMBER_PROFILE, MEMBER_REGISTRATION_DATE, MEMBER_ROLE, MEMBER_PHONE, MEMBER_ADDRESS FROM MEMBER WHERE MEMBER_ID LIKE CONCAT('%',?,'%') AND MEMBER_ROLE='USER'";
   // 사장님 ID로 검색 - 10/29 추가
   private final String SELECTALL_OWNER_SEARCH ="SELECT MEMBER_ID, MEMBER_NAME, MEMBER_NICKNAME, MEMBER_PROFILE, MEMBER_REGISTRATION_DATE, MEMBER_ROLE, MEMBER_PHONE, MEMBER_ADDRESS FROM MEMBER WHERE MEMBER_ID LIKE CONCAT('%',?,'%') AND MEMBER_ROLE='OWNER'";
		   
   @Autowired
   private JdbcTemplate jdbcTemplate;

   public boolean insert(MemberDTO memberDTO) { // 입력
      System.out.println("====member.MemberDAO2.insert 시작");
      // 회원가입
      String member_id = memberDTO.getMember_id();
      String member_password = memberDTO.getMember_password();
      String member_nickname = memberDTO.getMember_nickname();
      String member_name = memberDTO.getMember_name();
      String member_phone = memberDTO.getMember_phone();
      String member_address = memberDTO.getMember_address();
      String member_role = memberDTO.getMember_role();

      int result = jdbcTemplate.update(MEMBER_INSERT, member_id, member_password, member_nickname, member_name,
            member_phone, member_address, member_role);
      System.out.println("====member.MemberDAO2.insert result :" + result);
      if (result <= 0) {
         System.err.println("====member.MemberDAO2.insert 실패");
         return false;
      }
      System.out.println("====member.MemberDAO2.insert 성공");
      return true;
   }

   public boolean update(MemberDTO memberDTO) { // 업데이트
      System.out.println("====member.MemberDAO2.update 시작");

      String member_id = memberDTO.getMember_id();
      String member_password = memberDTO.getMember_password();
      String member_profile = memberDTO.getMember_profile();
      String member_nickname = memberDTO.getMember_nickname();
      //String member_role = memberDTO.getMember_role();
      String member_name = memberDTO.getMember_name();
      int result = 0;
      System.out.println("====member.MemberDAO2.update memberDTO.getMember_condition() : ["
            + memberDTO.getMember_condition() + "]");
      if (memberDTO.getMember_condition().equals("MEMBER_PROFILE")) { // 프로필(사진) 변경
         // 회원 프로필, 아이디
         result = jdbcTemplate.update(MEMBER_UPDATE_PROFILE, member_profile, member_id);
      } 
      else if (memberDTO.getMember_condition().equals("MEMBER_PASSWORD")) { // 비밀번호 변경
         // 비밀번호, 아이디
         result = jdbcTemplate.update(MEMBER_UPDATE_PASSWORD, member_password, member_id);
      } 
      else if (memberDTO.getMember_condition().equals("MEMBER_NAME")) { // 이름+닉네임 변경
         // 이름, 닉네임, 아이디
         result = jdbcTemplate.update(MEMBER_UPDATE_NAME_NICKNAME, member_name, member_nickname, member_id);
      } 
      else if (memberDTO.getMember_condition().equals("MEMBER_ROLE")) { // 계정 차단
         result = jdbcTemplate.update(MEMBER_UPDATE_ROLE, member_id);
      } 
      else {
         System.err.println("====member.MemberDAO2.update 컨디션 에러");
         return false;
      }
      System.out.println("====member.MemberDAO2.update result :" + result);
      if (result <= 0) {
         System.err.println("====member.MemberDAO2.update 실패");
         return false;
      }
      System.out.println("====member.MemberDAO2.update 성공");
      return true;
   }

   public boolean delete(MemberDTO memberDTO) { // 회원 탈퇴
      System.out.println("====member.MemberDAO2.delete 시작");
      int result = jdbcTemplate.update(MEMBER_DELETE, memberDTO.getMember_id());
      System.out.println("====member.MemberDAO2.delete result :" + result);
      if (result <= 0) {
         System.err.println("====member.MemberDAO2.delete 실패");
         return false;
      }
      System.out.println("====member.MemberDAO2.delete 성공");
      return true;
   }

   // 전체 출력
   public List<MemberDTO> selectAll(MemberDTO memberDTO) { 
      System.out.println("====member.MemberDAO2.selectAll 시작");
      List<MemberDTO> datas = new ArrayList<>(); // 배열에 담아 전달하기 위해 list를 생성한다.
      System.out.println("====member.MemberDAO2.selectAll memberDTO.getMember_condition() : ["
            + memberDTO.getMember_condition() + "]"); // 컨디션을 확인하여 알맞는 기능을 수행한다.
      if (memberDTO.getMember_condition().equals("MEMBER_SELECTALL")) { // 전체 회원 검색
         datas = (List<MemberDTO>) jdbcTemplate.query(MEMBER_SELECTALL, new MemberRowMapper_all());
      } 
      else if (memberDTO.getMember_condition().equals("SELECTALL_USER_SEARCH")) { // 일반 회원 검색
    	  Object[] args = { memberDTO.getMember_searchkeyword() };
    	  datas = (List<MemberDTO>) jdbcTemplate.query(SELECTALL_USER_SEARCH, args, new MemberRowMapper_all());
      } 
      else if (memberDTO.getMember_condition().equals("SELECTALL_OWNER_SEARCH")) { // 사장님 검색
    	  Object[] args = { memberDTO.getMember_searchkeyword() };
    	  datas = (List<MemberDTO>) jdbcTemplate.query(SELECTALL_OWNER_SEARCH, args, new MemberRowMapper_all());
      } 
      else { // 컨디션이 없다면 에러문구를 띄운다.
         System.err.println("====member.MemberDAO2.selectAll 컨디션 에러");
      }
      System.out.println("====member.MemberDAO2.selectAll 성공");
      return datas; // 데이터가 담긴 리스트를 반환한다.
   }

   public MemberDTO selectOne(MemberDTO memberDTO) {
      System.out.println("====member.MemberDAO2.selectOne 시작");
      MemberDTO data = null;

      // MemberRowMapper memberRowMapper = new MemberRowMapper(); // 미리 생성

      System.out.println("====member.MemberDAO2.selectOne memberDTO.getMember_condition() : ["+memberDTO.getMember_condition()+"]");
      if (memberDTO.getMember_condition().equals("CHECK_MEMBER_ID")) { // 이메일 중복검사
         Object[] args = { memberDTO.getMember_id() };
         data = jdbcTemplate.queryForObject(SELECTONE_MEMBER_ID, args, new MemberRowMapper_one_id());
      } 
      else if (memberDTO.getMember_condition().equals("CHECK_MEMBER_NICKNAME")) { // 닉네임 중복검사
         Object[] args = { memberDTO.getMember_nickname() };
         data = jdbcTemplate.queryForObject(SELECTONE_MEMBER_NICKNAME, args, new MemberRowMapper_one_nickname());
      } 
      else if (memberDTO.getMember_condition().equals("LOGIN")) { // 로그인
         Object[] args = { memberDTO.getMember_id(), memberDTO.getMember_password() };
         data = jdbcTemplate.queryForObject(SELECTONE_LOGIN, args, new MemberRowMapper_one_login());
      } 
      else if (memberDTO.getMember_condition().equals("MYPAGE")) { // 마이페이지
         Object[] args = { memberDTO.getMember_id() };
         data = jdbcTemplate.queryForObject(SELECTONE_MYPAGE, args, new MemberRowMapper_one_mypage());
      } 
      else if (memberDTO.getMember_condition().equals("MEMBER_PASSWORD_GET")) { // 비밀번호 확인
         Object[] args = { memberDTO.getMember_id() };
         data = jdbcTemplate.queryForObject(SELECTONE_MEMBER_PASSWORD, args, new MemberRowMapper_one_password());
      } 
      else if (memberDTO.getMember_condition().equals("MEMBER_SELECTONE_COUNT")) { // 회원 전체 수 반환
         data = jdbcTemplate.queryForObject(MEMBER_SELECTONE_COUNT, new MemberRowMapper_one_count());
      }
      else if (memberDTO.getMember_condition().equals("MEMBER_USER_SELECTONE_COUNT")) { // 일반 회원 수 반환
         data = jdbcTemplate.queryForObject(MEMBER_USER_SELECTONE_COUNT, new MemberRowMapper_one_count());
      }
      else if (memberDTO.getMember_condition().equals("MEMBER_OWNER_SELECTONE_COUNT")) { // 사장님 수 반환
         data = jdbcTemplate.queryForObject(MEMBER_OWNER_SELECTONE_COUNT, new MemberRowMapper_one_count());
      } 
      else if (memberDTO.getMember_condition().equals("RANDOM_MEMBER_ID")) { // 아이디 확인
         data = jdbcTemplate.queryForObject(RANDOM_MEMBER_ID, new MemberRowMapper_one_random_id());
      } 
      else if (memberDTO.getMember_condition().equals("RANDOM_MEMBER_OWNER_ID")) { // 사장님 아이디 확인
         data = jdbcTemplate.queryForObject(RANDOM_MEMBER_OWNER_ID, new MemberRowMapper_one_random_id());
      }
      else {
         System.err.println("====member.MemberDAO2.selectOne 컨디션 에러");
         return null;
      }

      System.out.println("====member.MemberDAO2.selectOne data: " + data);
      return data;
   }
}

class MemberRowMapper_all implements RowMapper<MemberDTO> { 

   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	  //MEMBER_ID, MEMBER_NAME, MEMBER_NICKNAME, MEMBER_PROFILE, MEMBER_REGISTRATION_DATE, MEMBER_ROLE, MEMBER_PHONE, MEMBER_ADDRESS
      System.out.println("====member.MemberDAO2.MemberRowMapper_all 실행");

      MemberDTO data = new MemberDTO();
      data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
      data.setMember_name(rs.getString("MEMBER_NAME")); // 이름
      data.setMember_nickname(rs.getString("MEMBER_NICKNAME")); // 닉네임
      data.setMember_profile(rs.getString("MEMBER_PROFILE")); // 프로필 사진
      data.setMember_registration_date(rs.getDate("MEMBER_REGISTRATION_DATE")); // 가입 날짜
      data.setMember_role(rs.getString("MEMBER_ROLE"));
      data.setMember_phone(rs.getString("MEMBER_PHONE"));
      data.setMember_address(rs.getString("MEMBER_ADDRESS"));
      System.out.println("====member.MemberDAO2.MemberRowMapper_all 반환");

      return data;
   }
}

class MemberRowMapper_one_id implements RowMapper<MemberDTO> { // 아이디(이메일) 중복검사

   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_id 실행");

      MemberDTO data = new MemberDTO();
      data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_id 반환");

      return data;
   }
}

class MemberRowMapper_one_nickname implements RowMapper<MemberDTO> { // 닉네임 중복검사
   
   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_nickname 실행");

      MemberDTO data = new MemberDTO();
      data.setMember_nickname(rs.getString("MEMBER_NICKNAME")); // 아이디
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_nickname 반환");

      return data;
   }
}

class MemberRowMapper_one_login implements RowMapper<MemberDTO> { // 로그인
   
   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_login 실행");

      MemberDTO data = new MemberDTO();
      data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
      data.setMember_role(rs.getString("MEMBER_ROLE")); // 롤 

      System.out.println("====member.MemberDAO2.MemberRowMapper_one_login 반환");
      return data;
   }
}
class MemberRowMapper_one_mypage implements RowMapper<MemberDTO> { // 마이페이지
   
   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_mypage 실행");
      
      MemberDTO data = new MemberDTO();
      data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
      data.setMember_name(rs.getString("MEMBER_NAME")); // 이름
      data.setMember_nickname(rs.getString("MEMBER_NICKNAME")); // 닉네임
      data.setMember_phone(rs.getString("MEMBER_PHONE")); // 핸드폰 번호
      data.setMember_address(rs.getString("MEMBER_ADDRESS")); // 주소
      data.setMember_profile(rs.getString("MEMBER_PROFILE")); // 프로필 사진
      
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_mypage 반환");
      return data;
   }
}

class MemberRowMapper_one_password implements RowMapper<MemberDTO> { // 회원의 비밀번호 반환
   
   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_password 실행");

      MemberDTO data = new MemberDTO();
      data.setMember_password(rs.getString("MEMBER_PASSWORD")); // 회원의 비밀번호

      System.out.println("====member.MemberDAO2.MemberRowMapper_one_password 반환");
      return data;
   }
}

class MemberRowMapper_one_count implements RowMapper<MemberDTO> { // 회원 전체 수 반환
   
   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_count 실행");

      MemberDTO data = new MemberDTO();
      data.setMember_total_cnt(rs.getInt("MEMBER_TOTAL_CNT"));

      System.out.println("====member.MemberDAO2.MemberRowMapper_one_count 반환");
      return data;
   }
}

class MemberRowMapper_one_random_id implements RowMapper<MemberDTO> { // 크롤링 때문에!
   
   @Override
   public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      System.out.println("====member.MemberDAO2.MemberRowMapper_one_random_id 실행");

      MemberDTO data = new MemberDTO();
      data.setMember_id(rs.getString("MEMBER_ID"));

      System.out.println("====member.MemberDAO2.MemberRowMapper_one_random_id 반환");
      return data;
   }
}
