
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class EndGamePanel extends JPanel 
{
	private int winnerScore;
	//private JPanel resultPanel;		//��������ִ� �г�
	private EndGameCharacter userPanel, comPanel;
	private JPanel calPanel;		//��� ���� �۵� �ǹ� ���� üũ�ϴ� �г�
	
	private JLabel gobak,gwangbak,mungdda,pibak,goCount,swingCount;
	private Font font;
	
	private int scoreRate;			//���� �������
	private int final_score;		//���� ����
	
	private int userResult;		//������ �̰���� üũ
	private int[] userCardCount;		//������ ���� ī��
	private int[] comCardCount;			//��ǻ�Ͱ� ���� ī��
	
	EndGamePanel(User user, Computer com, int userResult)
	{
		this.setBounds(0,0,700,700);
		this.setBackground(Color.black);
		this.setLayout(null);
		
		font = new Font(null,Font.BOLD,30);
		
		calPanel = new JPanel();
		calPanel.setBounds(10,220,680,260);
		calPanel.setBackground(new Color(0,39,79));
		calPanel.setLayout(null);
		this.add(calPanel);
		
		gobak = new JLabel("��  ��");
		gobak.setFont(font);
		gobak.setForeground(Color.gray);
		gobak.setBounds(20,20,210,60);
		gobak.setBorder(new LineBorder(Color.gray, 4, true));
		gobak.setHorizontalAlignment(SwingConstants.CENTER);
		gobak.setVerticalAlignment(SwingConstants.CENTER);
		calPanel.add(gobak);
		gwangbak = new JLabel("��  ��");
		gwangbak.setFont(font);
		gwangbak.setForeground(Color.gray);
		gwangbak.setBounds(233,20,210,60);
		gwangbak.setBorder(new LineBorder(Color.gray, 4, true));
		gwangbak.setHorizontalAlignment(SwingConstants.CENTER);
		gwangbak.setVerticalAlignment(SwingConstants.CENTER);
		calPanel.add(gwangbak);
		mungdda = new JLabel("��  ��");
		mungdda.setFont(font);
		mungdda.setForeground(Color.gray);
		mungdda.setBounds(446,20,210,60);
		mungdda.setBorder(new LineBorder(Color.gray, 4, true));
		mungdda.setHorizontalAlignment(SwingConstants.CENTER);
		mungdda.setVerticalAlignment(SwingConstants.CENTER);
		calPanel.add(mungdda);
		pibak = new JLabel("��  ��");
		pibak.setFont(font);
		pibak.setForeground(Color.gray);
		pibak.setBounds(20,140,210,60);
		pibak.setBorder(new LineBorder(Color.gray, 4, true));
		pibak.setHorizontalAlignment(SwingConstants.CENTER);
		pibak.setVerticalAlignment(SwingConstants.CENTER);
		calPanel.add(pibak);
		goCount = new JLabel("��  X 0");
		goCount.setFont(font);
		goCount.setForeground(Color.gray);
		goCount.setBounds(233,140,210,60);
		goCount.setBorder(new LineBorder(Color.gray, 4, true));
		goCount.setHorizontalAlignment(SwingConstants.CENTER);
		goCount.setVerticalAlignment(SwingConstants.CENTER);
		calPanel.add(goCount);
		swingCount = new JLabel("����  X 0");
		swingCount.setFont(font);
		swingCount.setForeground(Color.gray);
		swingCount.setBounds(446,140,210,60);
		swingCount.setBorder(new LineBorder(Color.gray, 4, true));
		swingCount.setHorizontalAlignment(SwingConstants.CENTER);
		swingCount.setVerticalAlignment(SwingConstants.CENTER);
		calPanel.add(swingCount);
		
		userCardCount = user.getScoreCardCount();	//ȹ���� ī�� ����
		comCardCount = com.getScoreCardCount();		//ȹ���� ī�� ����
		
		scoreRate = 1;
		
		if(userResult == PageConstants.WIN)		//������ �̰�����
		{
			userPanel = new EndGameCharacter(PageConstants.WIN, user,this);
			comPanel = new EndGameCharacter(PageConstants.LOSE, com,this);
			
			final_score = user.getAllScore();
			
			switch(user.getGoCount())		//��� ���� üũ
			{
			case 0:				
				break;
			case 1:
				final_score += 1;
				break;
			case 2:
				final_score += 2; 
				break;
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:	
				scoreRate *= (2*(com.getGoCount()-2));
				break;
			}
			
			if(com.getGoCount() != 0)		//��� üũ
			{
				gobak.setBorder(new LineBorder(Color.yellow, 4, true));
				gobak.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(userCardCount[0] >= 3 && comCardCount[0] == 0)	//���� üũ
			{
				gwangbak.setBorder(new LineBorder(Color.yellow, 4, true));
				gwangbak.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(userCardCount[1] >= 7)		//�۵�
			{
				mungdda.setBorder(new LineBorder(Color.yellow, 4, true));
				mungdda.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(userCardCount[3] >= 10 && comCardCount[3] < 8)	//�ǹ�
			{
				pibak.setBorder(new LineBorder(Color.yellow, 4, true));
				pibak.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(user.getSwingCount() > 0)	//��� �������� üũ
			{
				swingCount.setBorder(new LineBorder(Color.yellow, 4, true));
				swingCount.setForeground(Color.yellow);
				swingCount.setText("���� X "+user.getSwingCount());
				
				for(int i=0; i<user.getSwingCount(); i++)
					scoreRate *= 2;
			}
			
			if(user.getGoCount() != 0)
			{
				goCount.setText("��  X "+user.getGoCount());
				goCount.setBorder(new LineBorder(Color.yellow, 4, true));
				goCount.setForeground(Color.yellow);
			}
			repaint();
		}//if
		else if(userResult == PageConstants.LOSE)	//������ ������
		{
			comPanel = new EndGameCharacter(PageConstants.WIN, com,this);
			userPanel = new EndGameCharacter(PageConstants.LOSE, user,this);
			
			final_score = com.getAllScore();
			
			switch(com.getGoCount())		//��� ���� üũ
			{
			case 0:				
				break;
			case 1:
				final_score += 1;
				break;
			case 2:
				final_score += 2; 
				break;
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:	
				scoreRate *= (2*(com.getGoCount()-2));
				break;
			}
			if(user.getGoCount() != 0)		//��� üũ
			{
				gobak.setBorder(new LineBorder(Color.yellow, 4, true));
				gobak.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(comCardCount[0] >= 3 && userCardCount[0] == 0)	//���� üũ
			{
				gwangbak.setBorder(new LineBorder(Color.yellow, 4, true));
				gwangbak.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(comCardCount[1] >= 7)		//�۹�
			{
				mungdda.setBorder(new LineBorder(Color.yellow, 4, true));
				mungdda.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(comCardCount[2] >= 10 && userCardCount[2] < 8)	//�ǹ�
			{
				pibak.setBorder(new LineBorder(Color.yellow, 4, true));
				pibak.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(com.getSwingCount() > 0)	//��� �������� üũ
			{
				swingCount.setBorder(new LineBorder(Color.yellow, 4, true));
				swingCount.setForeground(Color.yellow);
				swingCount.setText("���� X "+com.getSwingCount());
				
				for(int i=0; i<com.getSwingCount(); i++)
					scoreRate *= 2;
			}
			//���, ���� ������� üũ
			if(com.getGoCount() != 0)
			{
				goCount.setText("��  X "+com.getGoCount());
				goCount.setBorder(new LineBorder(Color.yellow, 4, true));
				goCount.setForeground(Color.yellow);
			}
			repaint();
		}
		else		//�������
		{
			comPanel = new EndGameCharacter(PageConstants.DRAW, com,this);
			userPanel = new EndGameCharacter(PageConstants.DRAW, user,this);
		}
		
		this.userResult = userResult;		//���� ���� ���
		
		this.add(userPanel);
		this.add(comPanel);
	}//EndGamePanel
	
	public int getScoreRate()
	{
		return scoreRate;
	}
	
	public int getFinalScore()
	{
		return final_score;
	}

}
