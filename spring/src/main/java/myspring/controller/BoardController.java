package myspring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import myspring.model.Board;
import myspring.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService bs;
	
	// 글 작성 폼
	@RequestMapping("boardform.do")
	public String boardform() {
		return "board/boardform";
	}
	
	// 글 작성
	@RequestMapping("boardwrite.do")
	public String boardwrite(Board board, Model model) {
		
		int result = bs.insert(board);
		if(result == 1) System.out.println("글 작성 성공");
		
		model.addAttribute("result", result);
		return "board/insertresult";
	}
	
	// 글 목록
	@RequestMapping("boardlist.do")
	public String boardlist(HttpServletRequest request, Model model) {
		
		int page = 1;	// 현재 페이지 번호
		int limit = 10;	// 한 화면에 출력할 데이터 갯수
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		int startRow = (page - 1) * limit + 1;
		int endRow = page * limit;
		
		int listcount = bs.getCount();	// 총 데이터 갯수
		System.out.println("listcount : " + listcount);
		
		List<Board> boardlist = bs.getBoardList(page); // 게시판 목록
		System.out.println("boardlist : " + boardlist);
		
		// 총 페이지
		int pageCount = listcount / limit + ((listcount % limit == 0) ? 0 : 1);
		
		int startPage = ((page - 1) / 10) * limit + 1; // 1, 11, 21...
		int endPage = startPage + 10 - 1;	// 10, 20, 30...
		
		if(endPage > pageCount) endPage = pageCount;
		
		model.addAttribute("page", page);
		model.addAttribute("listcount", listcount);
		model.addAttribute("boardlist", boardlist); // 리스트
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		return "board/boardlist";
	}
	
	// 상세 페이지 : 조회수 1 증가 + 상세 정보 구하기
	@RequestMapping("boardcontent.do")
	public String boardcontent(int no, int page, Model model) {
		
		bs.updatecount(no);	// 조회수 1 증가
		Board board = bs.getBoard(no);	// 상세 정보 구하기
		String content = board.getContent().replace("\n", "<br>");
		
		model.addAttribute("board", board);
		model.addAttribute("content", content);
		model.addAttribute("page", page);
		
		return "board/boardcontent";
	}
	
	// 수정 폼
	@RequestMapping("boardupdateform.do")
	public String boardupdateform(int no, int page, Model model) {
		
		Board board = bs.getBoard(no); // 상세 정보 구하기
		
		model.addAttribute("board", board);
		model.addAttribute("page", page);
		
		return "board/boardupdateform";
	}
	
	// 글 수정
	@RequestMapping("boardupdate.do")
	public String boardupdate(Board board, int page, Model model) {
		int result = 0;
		Board old = bs.getBoard(board.getNo());
		
		// 비번 비교
		if(old.getPasswd().equals(board.getPasswd())) { // 비번 일치시
			result = bs.update(board); // 글 수정
		} else { // 비번 불일치시
			result = -1;
		}
		
		model.addAttribute("result", result);
		model.addAttribute("board", board);
		model.addAttribute("page", page);
		
		return "board/updateresult";
	}
	// 글 삭제 폼
	@RequestMapping("boarddeleteform.do")
	public String boarddeleteform(int no, int page, Model model) {
		return "board/boarddeleteform";
	}
	
	// 글 삭제
	@RequestMapping("boarddelete.do")
	public String boarddelete(Board board, int page, Model model) {
		int result = 0;
		Board old = bs.getBoard(board.getNo()); // 상세 ㅈ어보 구하기
		
		// 비번 비교
		if(old.getPasswd().equals(board.getPasswd())) { // 비번 일치시
			result = bs.delete(board.getNo());	// 글 삭제
		} else {	// 비번 불일치시
			result = -1;
		}
		model.addAttribute("result", result);
		model.addAttribute("page", result);
		
		return "board/deleteresult";
	}
}
