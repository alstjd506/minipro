package kdgProject;

import java.util.*;

public class bookManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean run = true;
		Scanner sc = new Scanner(System.in);
		bookDao Bdao = new bookDao();
		memberDao Mdao = new memberDao();
		
		
		while(run) {
			System.out.println("---------------------------------------------------------------------------");
			System.out.println("1.회원가입 2.로그인 3.회원탈퇴 4.종료");
			System.out.println("---------------------------------------------------------------------------");
			System.out.print("선택 >> ");
			int menu = sc.nextInt();
			sc.nextLine();	
			
			switch(menu) {
			case 1: //회원가입
				System.out.print("아이디 >> ");
				String id = sc.nextLine();
				System.out.print("비밀번호 >> ");
				String pw = sc.nextLine();
				System.out.print("이름 >> ");
				String name = sc.nextLine();
				System.out.print("생년월일 >> ");
				String bdate = sc.nextLine(); 
				System.out.print("핸드폰 번호 >> ");
				String phone = sc.nextLine(); 
				
				member mem = new member();
				mem.setMemId(id);
				mem.setMemPw(pw);
				mem.setMemName(name);
				mem.setBdate(bdate);
				mem.setPhone(phone);
				
				if(Mdao.getInput(mem)) {
					System.out.println("회원가입되었습니다.");
				
				} else{
				System.out.println("예외 발생");
				}
				break;
				
			case 2: //로그인
				System.out.print("아이디 >> ");
				String memId = sc.nextLine();
				System.out.print("비밀번호 >> ");
				String memPw = sc.nextLine();
		
				if(Mdao.login(memId, memPw)) {
					if(memId.equals("admin")) {
						System.out.println("관리자 ID에 로그인되었습니다.");
						boolean run2 = true;
						while(run2) {
							System.out.println("===========================================================================");
							System.out.println("1.도서등록 2.도서삭제 3.도서목록조회 4.회원정보조회 5.로그아웃");
							System.out.println("===========================================================================");
							System.out.print("선택 >> ");
							int menu2 = sc.nextInt();
							sc.nextLine();
							switch(menu2) {
							case 1: //도서 등록
								System.out.print("도서코드 >> ");
								String bNo = sc.nextLine();
								System.out.print("도서명 >> ");
								String bName = sc.nextLine();
								System.out.print("저자 >> ");
								String bAuthor = sc.nextLine();
								
								book book = new book();
								book.setBookNo(bNo);
								book.setBookName(bName);
								book.setBookAuthor(bAuthor);
								
								if(Bdao.getBookInput(book)) {
									System.out.println("도서가 추가 되었습니다.");
								} else{
								System.out.println("예외 발생");
								}
								break;
							case 2: //도서 삭제
								System.out.print("삭제할 도서정보 >> ");
								String bookDel = sc.nextLine();
								
								book = new book();
								
								book.setBookNo(bookDel);
								book.setBookName(bookDel);
								
								if(Bdao.deleteBook(book)) {
									System.out.println("도서 삭제 완료.");
								}else {
									System.out.println("처리 실패.");
								}
								break;
							case 3: //도서목록 조회
								List<book> books = Bdao.bookList();
								System.out.println("===========================================================================");
								System.out.println("1)도서코드\t\t 2)도서명\t\t 3)저자\t\t 4)가능유무");
								
								for(book ele : books) {
									System.out.println(ele.toString());
								}
								break;
							case 4: //회원정보조회
								List<member> mems = Mdao.memList();
								System.out.println("===========================================================================");
								System.out.println("1)회원번호\t2)회원ID\t\t3)이름\t4)생년월일\t\t\t5)전화번호\t\t\t6)대여중인도서");
								
								for(member ele : mems) {
									System.out.println(ele.toString());
								}
								break;
							case 5: //로그아웃
								run2 = false;
								break;
							default :
								System.out.println("잘못된 입력입니다.");
							}
						}
					}else {
						System.out.println(Mdao.loginId + "로 로그인되었습니다.");
					
						boolean run3 = true;
						while(run3) {
							System.out.println("---------------------------------------------------------------------------");
							System.out.println("1.도서검색 2.대여가능한도서조회 3.도서대여 4.도서반납 5.대여중인도서조회 6.로그아웃");
							System.out.println("---------------------------------------------------------------------------");
							System.out.print("선택 >> ");
							int menu3 = sc.nextInt();
							sc.nextLine();
							
							switch(menu3) {
							case 1: //도서 검색
								System.out.print("조회할 도서정보 >> ");
								String bookInfo = sc.nextLine();
								
								book book = new book();
								if(bookInfo.equals(book.getBookNo())) {
									book.setBookNo(bookInfo);
								}else if(bookInfo.equals(book.getBookName())) {
									book.setBookName(bookInfo);
								}else {
									book.setBookAuthor(bookInfo);
								}
								System.out.println("1)도서코드\t\t 2)도서명\t\t 3)저자\t\t 4)대여가능유무");
								
								List<book> books1 = Bdao.showList(book);
								for(book ele1 : books1) {
									System.out.println(ele1.toString());
								}
								break;
								
							case 2: //대여가능도서 조회
								List<book> books = Bdao.nRentList();
								System.out.println("1)도서코드\t\t 2)도서명\t\t 3)저자\t\t 4)가능유무");
								System.out.println("------------------------------------------------------");
								for(book ele : books) {
									System.out.println(ele.toString());
								}
								break;
							case 3: //도서 대여
								System.out.print("대여 할 도서정보 >> ");
								String bookRent = sc.nextLine();
								
								mem = new member();
								mem.setMemBook(bookRent);
														
								book = new book();
								book.setBookName(bookRent);
								book.setBookNo(bookRent);
								
								if(Bdao.rentBook(book)) {
									System.out.println("도서 대여 완료.");
								}else {
									System.out.println("도서 처리 실패.");
								}
								if(Mdao.updateBook(book)) {
									
								}else {
									System.out.println("유저 처리 실패.");
								}
								
								break;
							case 4: //도서 반납
							
								System.out.println("반납 할 도서 정보 >> ");
								String bookReturn = sc.nextLine();
								
								mem = new member();
//															
								book = new book();
								book.setBookNo(bookReturn);
								book.setBookName(bookReturn);
								
								if(Bdao.returnBook(book)) {
									System.out.println("도서 반납 완료.");
								}else {
									System.out.println("도서 처리 실패.");
								}
								if(Mdao.returnBook(mem)) {
									
								}else {
									System.out.println("처리 실패.");
								}
								break;
								
							case 5://대여중인 도서조회
								
								System.out.println("1)대여중인도서");
								System.out.println("-----------------------------------------------------------------------");
								List<member> mems = Mdao.membookList();
								for(member ele : mems) {
									System.out.println(ele.showRent());
								}
								
								break;
							case 6:
								
								run3 = false;
								break;
							default :
								System.out.println("잘못된 입력입니다.");
							}
						
						}
					}
				} else{
				System.out.println("로그인 실패");
				}
				break;
			case 3:
				System.out.print("탈퇴 할 회원정보 >> ");
				String memDel = sc.nextLine();
				
				mem = new member();
				mem.setMemName(memDel);
				mem.setMemId(memDel);
				
				if(Mdao.deleteMember(mem)) {
					System.out.println("회원 탈퇴 완료.");
				}else {
					System.out.println("처리 실패.");
				}
				break;		
			
			case 4:
				run =false;
				System.out.println("시스템종료");
				break;
			default :
				System.out.println("잘못된 입력입니다.");
			
			}
			
		}
		
	}//
}//
