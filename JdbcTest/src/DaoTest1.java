


public class DaoTest1 {
	public static void main(String[] args) {
		 User u = new User("ff22f","남궁성","1234","aaa@aaa.com");
	     System.out.println(u);
	}
	
}
/*
 * 1. user_info테이블에 mappping하기 위한 User클래스를 작성하하고 테스트 하시오.
	1.1 생성자를 적절히 작성하시오. - 이클립스의 기능이용(메뉴Source > Generate Constructor using Fields...)
	1.2 toString()을 오버라이딩 하시오 - 이클립스 기능이용
	1.3 getter와 setter를 추가하시오. - 이클립스 기능이용
 */
class User {
	String id;
	String name;
	String pwd;
	String mail;
	
	User(String id, String name, String pwd, String mail) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.mail = mail;
	}
	
	public String toString() {
		return "id: "+id + " name: "+ name + " pwd: "+ pwd + " mail: "+ mail;
	}
}