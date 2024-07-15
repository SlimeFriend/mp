package control;

import java.util.List;
import java.util.Scanner;

import dao.BoardDao;
import dao.LikeDAO;
import vo.BoardVO;

public class BoardControl {

	boolean run = true;
	Scanner sc = new Scanner(System.in);
	BoardDao bdao = new BoardDao();
	int anony;
	String cid = "유저";

	public void go() {
		System.out.println("─────────────────────────────────────────────────────────\r\n"
				+ "              YD게시판에 온 것을 환영합니다.\r\n" + "─────────────────────────────────────────────────────────");
		while (run) {
			System.out.println("---------------------------------------------------------\r\n"
					+ "       1. 회원 접속                   2.비회원 접속\r\n"
					+ "---------------------------------------------------------");
			System.out.print("선택> ");
			int cLogin = Integer.parseInt(sc.nextLine());


			if (cLogin == 1) {
				System.out.print("아이디> ");
				String id = sc.nextLine();
				System.out.print("비밀번호> ");
				String pwd = sc.nextLine();

				if (bdao.accCorrect(id, pwd) == 1) {		
					System.out.println(id + "님께서 접속하셨습니다.");
					anony = 1;
					cid = id;
					run = false;
					} else {
						System.out.println("로그인 실패");
				}
			}else if (cLogin == 2) {
				System.out.println("비회원으로 입장하셨습니다.");
				anony = 2;
				run = false;
			}
			System.out.println();

		} // end of while  회원 비회원 접속 끝
		//-----------------------게시판 선택 -----------------------------

		boolean A = true;
		while(A) {
			System.out.println("---------------------------------------------------------\r\n"
					+ " 1.게임        2.음악          3.뷰티          4.종료\r\n"
					+ "---------------------------------------------------------");
			System.out.print("게시판 선택> ");
			int cList = Integer.parseInt(sc.nextLine());
			
			
			System.out.println();
			
			if(cList==1) {
				boolean a = true;
				while(a) {
					List<BoardVO> gList = bdao.viewGList();
					System.out.println("게임 게시판에 접속하셨습니다.");
					System.out.println("---------------------------------------------------------\r\n"
							+ "번호 	제목	 작성자		작성일시		좋아요\r\n"
							+ "---------------------------------------------------------");
					for(int i = 0; i<gList.size();i++) {
						System.out.print(gList.get(i).getBoardNo()+"\t");
						System.out.print(gList.get(i).getTitle()+"\t");
						System.out.print(gList.get(i).getWriter()+"\t");
						System.out.print(gList.get(i).getMdate()+"\t");
						System.out.println(gList.get(i).getSumLike());
					}
					
					System.out.println("---------------------------------------------------------\r\n"
							+ "  1. 글선택            2. 글쓰기          3. 게시판 선택화면\r\n"
							+ "---------------------------------------------------------");
					
					System.out.print("선택> ");
					int sN = Integer.parseInt(sc.nextLine());
					
					
					// ---------------------------------- 글선택 
					if(sN == 1) {
						boolean runsn = true;
						System.out.print("게시글 번호> ");
						int bN = Integer.parseInt(sc.nextLine());
						BoardDao bd = new BoardDao();
						BoardVO vB = bd.viewBoard(bN);
						LikeDAO ldao = new LikeDAO();
						while(runsn) {
							System.out.println("---------------------------------------------------------");
							System.out.print(vB.getBoardNo()+") ");
							System.out.println(vB.getTitle());
							System.out.println("작성자: "+vB.getWriter());
							System.out.println("내용: "+vB.getbContent());
							System.out.println("좋아요 "+ldao.cntLike(bN)+"        싫어요 "+ldao.cntHate(bN)+"");
							System.out.println("---------------------------------------------------------");
							
							//--------------------------------------------------------------------------------------------------------------
							System.out.println("---------------------------------------------------------");
							System.out.println("   1. 좋아요   2. 싫어요   3. 글 수정   4. 글 삭제   5. 뒤로가기    ");
							System.out.println("---------------------------------------------------------");
							System.out.print("선택> ");
							int bcc = Integer.parseInt(sc.nextLine());
							
							// ------------------------    좋아요
							if(bcc==1) {
								if(anony==1) {
									if(ldao.checkL(bN, cid)==0) {
										System.out.println("게시글을 좋아요를 남깁니다.");
										ldao.cLike(cid, bN);
										ldao.cntLike(bN);
										bd.likeBoard(ldao.cntLike(bN)-ldao.cntHate(bN), bN);
									}
									else {
										System.out.println("이미 처리된 게시글 입니다.");
									}
								}else if(anony==2) {
									System.out.println("권한이 없습니다");
								}
							}
							
							//--------------------------- 싫어요 
							
							else if(bcc==2) {
								if(anony==1) {
									if(ldao.checkL(bN, cid)==0) {
										System.out.println("게시글에 싫어요를 남깁니다");
										ldao.cHate(cid, bN);
										ldao.cntHate(bN);
										bd.likeBoard(ldao.cntLike(bN)-ldao.cntHate(bN), bN);
									}else {
										System.out.println("이미 처리된 게시물 입니다.");
									}
								}else if(anony==2) {
									System.out.println("권한이 없습니다");
								}
							}
							
							//------------------------------ 게시글 수정
							
							else if(bcc==3) {
								if(anony==1) {
									if(cid.equals(vB.getWriter())) {
										System.out.println("변경할 내용들을 입력하세요");
										System.out.print("제목> ");
										String chbN = sc.nextLine();
										System.out.print("내용> ");
										String chbC = sc.nextLine();
										bdao.chBoard(chbN, chbC, bN);
										System.out.println("게시글 수정이 완료되었습니다.");
										runsn=false;
									}else {
										System.out.println("권한이 없습니다.");
									}
									
								}else if(anony==2) {
									if(cid.equals(vB.getWriter())) {
										System.out.println("게시글 수정을 선택하셨습니다.");
										System.out.println("비밀번호를 입력해 주세요.");
										System.out.print("비밀번호> ");
										String pP = sc.nextLine();
										if(vB.getbPassword().equals(pP)) {
											System.out.println("인증 완료");
											System.out.println("변경할 내용들을 입력하세요");
											System.out.print("제목> ");
											String chbN = sc.nextLine();
											System.out.print("내용> ");
											String chbC = sc.nextLine();
											bdao.chBoard(chbN, chbC, bN);
											System.out.println("게시글 수정이 완료되었습니다.");
											runsn = false;
										}else {
											System.out.println("비밀번호 오류");
										}		
									}else {
										System.out.println("권한이 없습니다.");
									}
								}
							}
							
							// --------------------------   게시글 삭제 
							
							else if(bcc==4) {
								if(anony==1) {
									if(cid.equals(vB.getWriter())) {
										System.out.println("게시글 삭제가 완료되었습니다.");
										runsn = false;
										bdao.dBoard(bN);
									}else {
										System.out.println("권한이 없습니다.");
									}
									
								}else if(anony==2) {
									if(cid.equals(vB.getWriter())) {
										System.out.println("게시글 삭제을 선택하셨습니다.");
										System.out.println("비밀번호를 입력해 주세요.");
										System.out.print("비밀번호> ");
										String pP = sc.nextLine();
										if(vB.getbPassword().equals(pP)) {
											System.out.println("인증 완료");
											System.out.println("게시글을 정말 삭제하시겠습니까?");
											System.out.println("1. 예  2. 아니오");
											System.out.print("입력> ");
											int rd = Integer.parseInt(sc.nextLine());
											if(rd == 1) {
												bdao.dBoard(bN);
												runsn = false;
											}
											if(rd == 2) {
												System.out.println("삭제를 취소하셨습니다.");
											}
										}else {
											System.out.println("비밀번호 오류");
										}
									}else {
										System.out.println("권한이 없습니다.");
									}
								}
							}
							
							// ------------------------------ 뒤로가기
							
							else if(bcc==5) {
								runsn =false;
							}
						}
						
					}
					
					
					// ------------------------------------------- 글 작성
					if(sN == 2) {
						if(anony==1) {
							BoardDao bd = new BoardDao();
							System.out.println("게시글을 작성합니다.");
							System.out.println("게시글 제목을 입력해 주세요.");
							System.out.print("> ");
							String bN = sc.nextLine();
							System.out.println("게시글 내용을 입력해 주세요.");
							System.out.print("> ");
							String bC = sc.nextLine();

							bd.ccBoard(bN, cid, bC);
							System.out.println("게시글이 작성되었습니다.");

							
						}
						if(anony==2) {
							
							BoardDao bd = new BoardDao();
							System.out.println("게시글을 작성합니다.");
							System.out.println("게시글 제목을 입력해 주세요.");
							System.out.print("> ");
							String bN = sc.nextLine();
							System.out.println("게시글 내용을 입력해 주세요.");
							System.out.print("> ");
							String bC = sc.nextLine();
							System.out.println("비회원으로 접속하셨습니다. 게시글 비밀번호를 입력해주세요.");
							System.out.print("> ");
							String bP = sc.nextLine();
							
							bd.cBoard(bN, bC, bP);
							System.out.println("게시글이 작성되었습니다.");
						}
					}
					if(sN == 3) {
						a=false;
					}
				}
			}
			
			
			
			
			
			
//ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ
			
			
			
			
			
			
			
			
			
			if(cList==2) {
				boolean a = true;
				while(a) {
					List<BoardVO> mList = bdao.viewMList();
					System.out.println("음악 게시판에 접속하셨습니다.");
					System.out.println("---------------------------------------------------------\r\n"
							+ "번호 	제목	 작성자		작성일시		좋아요\r\n"
							+ "---------------------------------------------------------");
					for(int i = 0; i<mList.size();i++) {
						System.out.print(mList.get(i).getBoardNo()+"\t");
						System.out.print(mList.get(i).getTitle()+"\t");
						System.out.print(mList.get(i).getWriter()+"\t");
						System.out.print(mList.get(i).getMdate()+"\t");
						System.out.println(mList.get(i).getSumLike());
					}
					
					System.out.println("---------------------------------------------------------\r\n"
							+ "  1. 글선택            2. 글쓰기          3. 게시판 선택화면\r\n"
							+ "---------------------------------------------------------");
					
					System.out.print("선택> ");
					int sN = Integer.parseInt(sc.nextLine());
					
					
					// ---------------------------------- 글선택 
					if(sN == 1) {
						boolean runsn = true;
						System.out.print("게시글 번호> ");
						int bN = Integer.parseInt(sc.nextLine());
						BoardDao bd = new BoardDao();
						BoardVO vB = bd.viewBoard(bN);
						LikeDAO ldao = new LikeDAO();
						while(runsn) {
							System.out.println("---------------------------------------------------------");
							System.out.print(vB.getBoardNo()+") ");
							System.out.println(vB.getTitle());
							System.out.println("작성자: "+vB.getWriter());
							System.out.println("내용: "+vB.getbContent());
							System.out.println("좋아요 "+ldao.cntLike(bN)+"        싫어요 "+ldao.cntHate(bN)+"");
							System.out.println("---------------------------------------------------------");
							
							//--------------------------------------------------------------------------------------------------------------
							System.out.println("---------------------------------------------------------");
							System.out.println("   1. 좋아요   2. 싫어요   3. 글 수정   4. 글 삭제   5. 뒤로가기    ");
							System.out.println("---------------------------------------------------------");
							System.out.print("선택> ");
							int bcc = Integer.parseInt(sc.nextLine());
							
							// ------------------------    좋아요
							if(bcc==1) {
								if(anony==1) {
									if(ldao.checkL(bN, cid)==0) {
										System.out.println("게시글을 좋아요를 남깁니다.");
										ldao.cLike(cid, bN);
										ldao.cntLike(bN);
										bd.likeBoard(ldao.cntLike(bN)-ldao.cntHate(bN), bN);
									}
									else {
										System.out.println("이미 처리된 게시글 입니다.");
									}
								}else if(anony==2) {
									System.out.println("권한이 없습니다");
								}
							}
							
							//--------------------------- 싫어요 
							
							else if(bcc==2) {
								if(anony==1) {
									if(ldao.checkL(bN, cid)==0) {
										System.out.println("게시글에 싫어요를 남깁니다");
										ldao.cHate(cid, bN);
										ldao.cntHate(bN);
										bd.likeBoard(ldao.cntLike(bN)-ldao.cntHate(bN), bN);
									}else {
										System.out.println("이미 처리된 게시물 입니다.");
									}
								}else if(anony==2) {
									System.out.println("권한이 없습니다");
								}
							}
							
							//------------------------------ 게시글 수정
							
							else if(bcc==3) {
								if(anony==1) {
									if(cid.equals(vB.getWriter())) {
										System.out.println("변경할 내용들을 입력하세요");
										System.out.print("제목> ");
										String chbN = sc.nextLine();
										System.out.print("내용> ");
										String chbC = sc.nextLine();
										bdao.chBoard(chbN, chbC, bN);
										System.out.println("게시글 수정이 완료되었습니다.");
										runsn=false;
									}else {
										System.out.println("권한이 없습니다.");
									}
									
								}else if(anony==2) {
									if(cid.equals(vB.getWriter())) {
										System.out.println("게시글 수정을 선택하셨습니다.");
										System.out.println("비밀번호를 입력해 주세요.");
										System.out.print("비밀번호> ");
										String pP = sc.nextLine();
										if(vB.getbPassword().equals(pP)) {
											System.out.println("인증 완료");
											System.out.println("변경할 내용들을 입력하세요");
											System.out.print("제목> ");
											String chbN = sc.nextLine();
											System.out.print("내용> ");
											String chbC = sc.nextLine();
											bdao.chBoard(chbN, chbC, bN);
											System.out.println("게시글 수정이 완료되었습니다.");
											runsn = false;
										}else {
											System.out.println("비밀번호 오류");
										}		
									}else {
										System.out.println("권한이 없습니다.");
									}
								}
							}
							
							// --------------------------   게시글 삭제 
							
							else if(bcc==4) {
								if(anony==1) {
									if(cid.equals(vB.getWriter())) {
										System.out.println("게시글 삭제가 완료되었습니다.");
										runsn = false;
										bdao.dBoard(bN);
									}else {
										System.out.println("권한이 없습니다.");
									}
									
								}else if(anony==2) {
									if(cid.equals(vB.getWriter())) {
										System.out.println("게시글 삭제을 선택하셨습니다.");
										System.out.println("비밀번호를 입력해 주세요.");
										System.out.print("비밀번호> ");
										String pP = sc.nextLine();
										if(vB.getbPassword().equals(pP)) {
											System.out.println("인증 완료");
											System.out.println("게시글을 정말 삭제하시겠습니까?");
											System.out.println("1. 예  2. 아니오");
											System.out.print("입력> ");
											int rd = Integer.parseInt(sc.nextLine());
											if(rd == 1) {
												bdao.dBoard(bN);
												runsn = false;
											}
											if(rd == 2) {
												System.out.println("삭제를 취소하셨습니다.");
											}
										}else {
											System.out.println("비밀번호 오류");
										}
									}else {
										System.out.println("권한이 없습니다.");
									}
								}
							}
							
							// ------------------------------ 뒤로가기
							
							else if(bcc==5) {
								runsn =false;
							}
						}
						
					}
					
					
					// ------------------------------------------- 글 작성
					if(sN == 2) {
						if(anony==1) {
							BoardDao bd = new BoardDao();
							System.out.println("게시글을 작성합니다.");
							System.out.println("게시글 제목을 입력해 주세요.");
							System.out.print("> ");
							String bN = sc.nextLine();
							System.out.println("게시글 내용을 입력해 주세요.");
							System.out.print("> ");
							String bC = sc.nextLine();

							bd.ccmBoard(bN, cid, bC);
							System.out.println("게시글이 작성되었습니다.");

							
						}
						if(anony==2) {
							
							BoardDao bd = new BoardDao();
							System.out.println("게시글을 작성합니다.");
							System.out.println("게시글 제목을 입력해 주세요.");
							System.out.print("> ");
							String bN = sc.nextLine();
							System.out.println("게시글 내용을 입력해 주세요.");
							System.out.print("> ");
							String bC = sc.nextLine();
							System.out.println("비회원으로 접속하셨습니다. 게시글 비밀번호를 입력해주세요.");
							System.out.print("> ");
							String bP = sc.nextLine();
							
							bd.cmBoard(bN, bC, bP);
							System.out.println("게시글이 작성되었습니다.");
						}
					}
					if(sN == 3) {
						a=false;
					}
				}
				
			}
			
			
			
			
			
			
			
			
	//ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ		
			
			
			
			
			
			
			
			
			
			
			if(cList==3) {
				boolean a = true;
				while(a) {
					List<BoardVO> bList = bdao.viewBList();
					System.out.println("뷰티 게시판에 접속하셨습니다.");
					System.out.println("---------------------------------------------------------\r\n"
							+ "번호 	제목	 작성자		작성일시		좋아요\r\n"
							+ "---------------------------------------------------------");
					for(int i = 0; i<bList.size();i++) {
						System.out.print(bList.get(i).getBoardNo()+"\t");
						System.out.print(bList.get(i).getTitle()+"\t");
						System.out.print(bList.get(i).getWriter()+"\t");
						System.out.print(bList.get(i).getMdate()+"\t");
						System.out.println(bList.get(i).getSumLike());
					}
					
					System.out.println("---------------------------------------------------------\r\n"
							+ "  1. 글선택            2. 글쓰기          3. 게시판 선택화면\r\n"
							+ "---------------------------------------------------------");
					
					System.out.print("선택> ");
					int sN = Integer.parseInt(sc.nextLine());
					
					
					// ---------------------------------- 글선택 
					if(sN == 1) {
						boolean runsn = true;
						System.out.print("게시글 번호> ");
						int bN = Integer.parseInt(sc.nextLine());
						BoardDao bd = new BoardDao();
						BoardVO vB = bd.viewBoard(bN);
						LikeDAO ldao = new LikeDAO();
						while(runsn) {
							System.out.println("---------------------------------------------------------");
							System.out.print(vB.getBoardNo()+") ");
							System.out.println(vB.getTitle());
							System.out.println("작성자: "+vB.getWriter());
							System.out.println("내용: "+vB.getbContent());
							System.out.println("좋아요 "+ldao.cntLike(bN)+"        싫어요 "+ldao.cntHate(bN)+"");
							System.out.println("---------------------------------------------------------");
							
							//--------------------------------------------------------------------------------------------------------------
							System.out.println("---------------------------------------------------------");
							System.out.println("   1. 좋아요   2. 싫어요   3. 글 수정   4. 글 삭제   5. 뒤로가기    ");
							System.out.println("---------------------------------------------------------");
							System.out.print("선택> ");
							int bcc = Integer.parseInt(sc.nextLine());
							
							// ------------------------    좋아요
							if(bcc==1) {
								if(anony==1) {
									if(ldao.checkL(bN, cid)==0) {
										System.out.println("게시글을 좋아요를 남깁니다.");
										ldao.cLike(cid, bN);
										ldao.cntLike(bN);
										bd.likeBoard(ldao.cntLike(bN)-ldao.cntHate(bN), bN);
									}
									else {
										System.out.println("이미 처리된 게시글 입니다.");
									}
								}else if(anony==2) {
									System.out.println("권한이 없습니다");
								}
							}
							
							//--------------------------- 싫어요 
							
							else if(bcc==2) {
								if(anony==1) {
									if(ldao.checkL(bN, cid)==0) {
										System.out.println("게시글에 싫어요를 남깁니다");
										ldao.cHate(cid, bN);
										ldao.cntHate(bN);
										bd.likeBoard(ldao.cntLike(bN)-ldao.cntHate(bN), bN);
									}else {
										System.out.println("이미 처리된 게시물 입니다.");
									}
								}else if(anony==2) {
									System.out.println("권한이 없습니다");
								}
							}
							
							//------------------------------ 게시글 수정
							
							else if(bcc==3) {
								if(anony==1) {
									if(cid.equals(vB.getWriter())) {
										System.out.println("변경할 내용들을 입력하세요");
										System.out.print("제목> ");
										String chbN = sc.nextLine();
										System.out.print("내용> ");
										String chbC = sc.nextLine();
										bdao.chBoard(chbN, chbC, bN);
										System.out.println("게시글 수정이 완료되었습니다.");
										runsn=false;
									}else {
										System.out.println("권한이 없습니다.");
									}
									
								}else if(anony==2) {
									if(cid.equals(vB.getWriter())) {
										System.out.println("게시글 수정을 선택하셨습니다.");
										System.out.println("비밀번호를 입력해 주세요.");
										System.out.print("비밀번호> ");
										String pP = sc.nextLine();
										if(vB.getbPassword().equals(pP)) {
											System.out.println("인증 완료");
											System.out.println("변경할 내용들을 입력하세요");
											System.out.print("제목> ");
											String chbN = sc.nextLine();
											System.out.print("내용> ");
											String chbC = sc.nextLine();
											bdao.chBoard(chbN, chbC, bN);
											System.out.println("게시글 수정이 완료되었습니다.");
											runsn = false;
										}else {
											System.out.println("비밀번호 오류");
										}		
									}else {
										System.out.println("권한이 없습니다.");
									}
								}
							}
							
							// --------------------------   게시글 삭제 
							
							else if(bcc==4) {
								if(anony==1) {
									if(cid.equals(vB.getWriter())) {
										System.out.println("게시글 삭제가 완료되었습니다.");
										runsn = false;
										bdao.dBoard(bN);
									}else {
										System.out.println("권한이 없습니다.");
									}
									
								}else if(anony==2) {
									if(cid.equals(vB.getWriter())) {
										System.out.println("게시글 삭제을 선택하셨습니다.");
										System.out.println("비밀번호를 입력해 주세요.");
										System.out.print("비밀번호> ");
										String pP = sc.nextLine();
										if(vB.getbPassword().equals(pP)) {
											System.out.println("인증 완료");
											System.out.println("게시글을 정말 삭제하시겠습니까?");
											System.out.println("1. 예  2. 아니오");
											System.out.print("입력> ");
											int rd = Integer.parseInt(sc.nextLine());
											if(rd == 1) {
												bdao.dBoard(bN);
												runsn = false;
											}
											if(rd == 2) {
												System.out.println("삭제를 취소하셨습니다.");
											}
										}else {
											System.out.println("비밀번호 오류");
										}
									}else {
										System.out.println("권한이 없습니다.");
									}
								}
							}
							
							// ------------------------------ 뒤로가기
							
							else if(bcc==5) {
								runsn =false;
							}
						}
						
					}
					
					
					// ------------------------------------------- 글 작성
					if(sN == 2) {
						if(anony==1) {
							BoardDao bd = new BoardDao();
							System.out.println("게시글을 작성합니다.");
							System.out.println("게시글 제목을 입력해 주세요.");
							System.out.print("> ");
							String bN = sc.nextLine();
							System.out.println("게시글 내용을 입력해 주세요.");
							System.out.print("> ");
							String bC = sc.nextLine();

							bd.ccbBoard(bN, cid, bC);
							System.out.println("게시글이 작성되었습니다.");

							
						}
						if(anony==2) {
							
							BoardDao bd = new BoardDao();
							System.out.println("게시글을 작성합니다.");
							System.out.println("게시글 제목을 입력해 주세요.");
							System.out.print("> ");
							String bN = sc.nextLine();
							System.out.println("게시글 내용을 입력해 주세요.");
							System.out.print("> ");
							String bC = sc.nextLine();
							System.out.println("비회원으로 접속하셨습니다. 게시글 비밀번호를 입력해주세요.");
							System.out.print("> ");
							String bP = sc.nextLine();
							
							bd.cbBoard(bN, bC, bP);
							System.out.println("게시글이 작성되었습니다.");
						}
					}
					if(sN == 3) {
						a=false;
					}
				}
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			if(cList==4) {
				System.out.println("게시판 접속을 종료합니다");
				return;
			}
			
			System.out.println();
			
		}


		
		
		
		
		
	}// end of go()

	

}// end of class


