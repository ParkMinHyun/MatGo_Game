
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class EndGamePanel extends JPanel 
{
	private int winnerScore;
	//private JPanel resultPanel;		//결과보여주는 패널
	private EndGameCharacter userPanel, comPanel;
	private JPanel calPanel;		//고박 광박 멍따 피박 등을 체크하는 패널
	
	private JLabel gobak,gwangbak,mungdda,pibak,goCount,swingCount;
	private Font font;
	
	private int scoreRate;			//점수 몇배인지
	private int final_score;		//최종 점수
	
	private int userResult;		//유저가 이겼는지 체크
	private int[] userCardCount;		//유저가 먹은 카드
	private int[] comCardCount;			//컴퓨터가 먹은 카드
	
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
		
		gobak = new JLabel("고  박");
		gobak.setFont(font);
		gobak.setForeground(Color.gray);
		gobak.setBounds(20,20,210,60);
		gobak.setBorder(new LineBorder(Color.gray, 4, true));
		gobak.setHorizontalAlignment(SwingConstants.CENTER);
		gobak.setVerticalAlignment(SwingConstants.CENTER);
		calPanel.add(gobak);
		gwangbak = new JLabel("광  박");
		gwangbak.setFont(font);
		gwangbak.setForeground(Color.gray);
		gwangbak.setBounds(233,20,210,60);
		gwangbak.setBorder(new LineBorder(Color.gray, 4, true));
		gwangbak.setHorizontalAlignment(SwingConstants.CENTER);
		gwangbak.setVerticalAlignment(SwingConstants.CENTER);
		calPanel.add(gwangbak);
		mungdda = new JLabel("멍  따");
		mungdda.setFont(font);
		mungdda.setForeground(Color.gray);
		mungdda.setBounds(446,20,210,60);
		mungdda.setBorder(new LineBorder(Color.gray, 4, true));
		mungdda.setHorizontalAlignment(SwingConstants.CENTER);
		mungdda.setVerticalAlignment(SwingConstants.CENTER);
		calPanel.add(mungdda);
		pibak = new JLabel("피  박");
		pibak.setFont(font);
		pibak.setForeground(Color.gray);
		pibak.setBounds(20,140,210,60);
		pibak.setBorder(new LineBorder(Color.gray, 4, true));
		pibak.setHorizontalAlignment(SwingConstants.CENTER);
		pibak.setVerticalAlignment(SwingConstants.CENTER);
		calPanel.add(pibak);
		goCount = new JLabel("고  X 0");
		goCount.setFont(font);
		goCount.setForeground(Color.gray);
		goCount.setBounds(233,140,210,60);
		goCount.setBorder(new LineBorder(Color.gray, 4, true));
		goCount.setHorizontalAlignment(SwingConstants.CENTER);
		goCount.setVerticalAlignment(SwingConstants.CENTER);
		calPanel.add(goCount);
		swingCount = new JLabel("흔들기  X 0");
		swingCount.setFont(font);
		swingCount.setForeground(Color.gray);
		swingCount.setBounds(446,140,210,60);
		swingCount.setBorder(new LineBorder(Color.gray, 4, true));
		swingCount.setHorizontalAlignment(SwingConstants.CENTER);
		swingCount.setVerticalAlignment(SwingConstants.CENTER);
		calPanel.add(swingCount);
		
		userCardCount = user.getScoreCardCount();	//획득한 카드 저장
		comCardCount = com.getScoreCardCount();		//획득한 카드 저장
		
		scoreRate = 1;
		
		if(userResult == PageConstants.WIN)		//유저가 이겼을때
		{
			userPanel = new EndGameCharacter(PageConstants.WIN, user,this);
			comPanel = new EndGameCharacter(PageConstants.LOSE, com,this);
			
			final_score = user.getAllScore();
			
			switch(user.getGoCount())		//몇고 인지 체크
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
			
			if(com.getGoCount() != 0)		//고박 체크
			{
				gobak.setBorder(new LineBorder(Color.yellow, 4, true));
				gobak.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(userCardCount[0] >= 3 && comCardCount[0] == 0)	//광박 체크
			{
				gwangbak.setBorder(new LineBorder(Color.yellow, 4, true));
				gwangbak.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(userCardCount[1] >= 7)		//멍따
			{
				mungdda.setBorder(new LineBorder(Color.yellow, 4, true));
				mungdda.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(userCardCount[3] >= 10 && comCardCount[3] < 8)	//피박
			{
				pibak.setBorder(new LineBorder(Color.yellow, 4, true));
				pibak.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(user.getSwingCount() > 0)	//몇번 흔들었는지 체크
			{
				swingCount.setBorder(new LineBorder(Color.yellow, 4, true));
				swingCount.setForeground(Color.yellow);
				swingCount.setText("흔들기 X "+user.getSwingCount());
				
				for(int i=0; i<user.getSwingCount(); i++)
					scoreRate *= 2;
			}
			
			if(user.getGoCount() != 0)
			{
				goCount.setText("고  X "+user.getGoCount());
				goCount.setBorder(new LineBorder(Color.yellow, 4, true));
				goCount.setForeground(Color.yellow);
			}
			repaint();
		}//if
		else if(userResult == PageConstants.LOSE)	//유저가 졌을때
		{
			comPanel = new EndGameCharacter(PageConstants.WIN, com,this);
			userPanel = new EndGameCharacter(PageConstants.LOSE, user,this);
			
			final_score = com.getAllScore();
			
			switch(com.getGoCount())		//몇고 인지 체크
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
			if(user.getGoCount() != 0)		//고박 체크
			{
				gobak.setBorder(new LineBorder(Color.yellow, 4, true));
				gobak.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(comCardCount[0] >= 3 && userCardCount[0] == 0)	//광박 체크
			{
				gwangbak.setBorder(new LineBorder(Color.yellow, 4, true));
				gwangbak.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(comCardCount[1] >= 7)		//멍박
			{
				mungdda.setBorder(new LineBorder(Color.yellow, 4, true));
				mungdda.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(comCardCount[2] >= 10 && userCardCount[2] < 8)	//피박
			{
				pibak.setBorder(new LineBorder(Color.yellow, 4, true));
				pibak.setForeground(Color.yellow);
				
				scoreRate *= 2;
			}
			if(com.getSwingCount() > 0)	//몇번 흔들었는지 체크
			{
				swingCount.setBorder(new LineBorder(Color.yellow, 4, true));
				swingCount.setForeground(Color.yellow);
				swingCount.setText("흔들기 X "+com.getSwingCount());
				
				for(int i=0; i<com.getSwingCount(); i++)
					scoreRate *= 2;
			}
			//몇고, 흔들기 몇번인지 체크
			if(com.getGoCount() != 0)
			{
				goCount.setText("고  X "+com.getGoCount());
				goCount.setBorder(new LineBorder(Color.yellow, 4, true));
				goCount.setForeground(Color.yellow);
			}
			repaint();
		}
		else		//비겼을떄
		{
			comPanel = new EndGameCharacter(PageConstants.DRAW, com,this);
			userPanel = new EndGameCharacter(PageConstants.DRAW, user,this);
		}
		
		this.userResult = userResult;		//유저 승패 결과
		
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
