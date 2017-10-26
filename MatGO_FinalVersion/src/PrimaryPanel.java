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
		case PageConstants.START_PAGE:		//����ȭ��(start,rule)ȭ��
			if(startPage == null)
				startPage = new StartPage(this);
			this.removeAll();
			this.add(startPage);
			this.setVisible(false);
			this.setVisible(true);
			break;
		case PageConstants.WHATOO_PAGE:		//���� ���۹�ư(whatoo)
			if(whatoo == null)
				whatoo = new Whatoo(this);
			this.remove(startPage);
			this.add(whatoo);
			this.setVisible(false);
			this.setVisible(true);
			whatoo.start();			//������� ����
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
			if(!whatoo.getUser().getGoGo())		//������ ��ž�� �������� üũ
				endPage = new EndGamePanel(whatoo.getUser(),whatoo.getCom(),PageConstants.WIN);
			else if(!whatoo.getCom().getGoGo())		//��ǻ�Ͱ� ��ž�� ������ ���
				endPage = new EndGamePanel(whatoo.getUser(),whatoo.getCom(),PageConstants.LOSE);
			else 			//������
				endPage = new EndGamePanel(whatoo.getUser(),whatoo.getCom(),PageConstants.DRAW);
			
			this.remove(whatoo);
			this.add(endPage);
			this.setVisible(false);
			this.setVisible(true);
			//���� ����� ���� ���� ������
			break;
		}//switch
	}//setPage()
}