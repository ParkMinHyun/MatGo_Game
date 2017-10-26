import java.awt.*;
import javax.swing.*;

public class PrimaryPanel extends JPanel
{
	private StartPage startPage;
	private Whatoo whatoo;
	private RulePage rulePage;
	private EndGamePanel endPage;
	
	public PrimaryPanel(int pageNumber)
	{
		this.setPreferredSize(new Dimension(700,740));
		this.setLayout(null);
		
		startPage = null;
		whatoo = null;
		rulePage = null;
		
		setPage(pageNumber);
	}//StartPage()
	
	public void setPage(int pageNumber)
	{
		switch(pageNumber)
		{
		case PageConstants.START_PAGE:		//메인화면(start,rule)화면
			if(startPage == null)
				startPage = new StartPage(this);
			this.removeAll();
			this.add(startPage);
			this.setVisible(false);
			this.setVisible(true);
			break;
		case PageConstants.WHATOO_PAGE:		//게임 시작버튼(whatoo)
			if(whatoo == null)
				whatoo = new Whatoo(this);
			this.remove(startPage);
			this.add(whatoo);
			this.setVisible(false);
			this.setVisible(true);
			whatoo.start();			//쓰레드로 동작
			break;
		case PageConstants.RULE_PAGE:
			if(rulePage == null)
				rulePage = new RulePage(this);
			this.remove(startPage);
			this.add(rulePage);
			this.setVisible(false);
			this.setVisible(true);
			break;
		case PageConstants.END_PAGE:
			if(!whatoo.getUser().getGoGo())		//유저가 스탑을 눌렀는지 체크
				endPage = new EndGamePanel(whatoo.getUser(),whatoo.getCom(),PageConstants.WIN);
			else if(!whatoo.getCom().getGoGo())		//컴퓨터가 스탑을 눌렀을 경우
				endPage = new EndGamePanel(whatoo.getUser(),whatoo.getCom(),PageConstants.LOSE);
			else 			//나가리
				endPage = new EndGamePanel(whatoo.getUser(),whatoo.getCom(),PageConstants.DRAW);
			
			this.remove(whatoo);
			this.add(endPage);
			this.setVisible(false);
			this.setVisible(true);
			//게임 종료시 게임 점수 보여줌
			break;
		}//switch
	}//setPage()
}