
import java.awt.*;
import javax.swing.*;


public class ScorePanel extends JPanel 	//획득한 카드 패널
{
	private Point[] location;		//카드 놓는 위치
	private int[] nCardCount;		//각각 카드 장수
	private JLabel[] cardCountL;	//점수 표시
	private JLabel cardScore; // 카드 총점수 
	private int[][] haveCard;			//먹은 카드
	private int nScore;				//총 점수
	private WhatooCard[] AllCard;		//전체 카드
	private int[] prevScore; // 전단계의 점수를 저장할 변수 
	public static int delay; // 이미지 표현 변수 
	public static int Show;
	
	private boolean godori = false;
	private boolean hongdan = false;
	private boolean chungdan = false;
	private boolean chodan = false;
	private int godoriCount=0;
	private int hongdanCount = 0;
	private int chungdanCount = 0;
	private int chodanCount = 0;
	private int ssang=0;
	private int pScore =0; // 피 점수 
	private int number = 0; // 임시로 저장할 변수
	
	private FieldPanel fieldPanel;
	
	ScorePanel(Point setBoundsPos, WhatooCard[] AllCard, FieldPanel field)
	{
		location = new Point[4];
		for(int i=0; i<4; i++)			//카드 놓을 위치 초기화
			location[i] = new Point(i*160+20,19);
		
		haveCard = new int[4][24];		//각 위치마다 먹은 카드 저장
		for(int i=0; i<4; i++)
			haveCard[i][0] = -1;		//앞에만 초기화해주면 카드가 존재하는지 안하는지 알수있음
		nCardCount = new int[4];		//카드 개수
		cardCountL = new JLabel[4];		//먹은 카드 표시
		prevScore = new int[4]; // 임시변수 초기화
		
		this.fieldPanel = field;
		
		this.setLayout(null);
		this.AllCard = AllCard;
		this.setBounds(setBoundsPos.x,setBoundsPos.y,700,100);
		this.setBackground(new Color(99,149,48));
		
		for(int i=0; i<4; i++)
		{
			cardCountL[i] = new JLabel(""+nCardCount[i]);
			cardCountL[i].setFont(new Font("Verdana", Font.BOLD, 12));
			cardCountL[i].setBounds(i*160+20,0, 20, 10);
			this.add(cardCountL[i]);
		}
		
		
		//총점수 화면에 표시 
		cardScore = new JLabel(""+nScore);
		cardScore.setFont(new Font("Verdana", Font.BOLD, 40));
		cardScore.setForeground(Color.yellow);
		cardScore.setBounds(0,0, 100, 50);
		this.add(cardScore);
	}
	
	public void addCard(int card1)
	{	
		if(card1 == -1)
			return;
		
		switch(AllCard[card1].getValue())
		{
		case 16:
			haveCard[0][nCardCount[0]++] = card1;
			break;
		case 8:
			haveCard[1][nCardCount[1]++] = card1;
			break;
		case 4:
			haveCard[2][nCardCount[2]++] = card1;
			break;
		case 3:
		case 2:
		case 1:
			haveCard[3][nCardCount[3]++] = card1;
			break;
		}//switch-case
		
		calScore();			//점수 계산
		
		for(int i=0; i<3; i++)
			cardCountL[i].setText(""+nCardCount[i]);
		cardCountL[3].setText(""+pScore);
		repaint();
	}
	
	public void addCard(int card1, int card2)
	{
		int[] cardIndex = new int[]{card1, card2};
		
		for(int i=0; i<=1; i++)
		{
			switch(AllCard[cardIndex[i]].getValue())
			{
			case 16:
				haveCard[0][nCardCount[0]++] = cardIndex[i];
				break;
			case 8:
				haveCard[1][nCardCount[1]++] = cardIndex[i];
				break;
			case 4:
				haveCard[2][nCardCount[2]++] = cardIndex[i];
				break;
			case 3:
			case 2:
			case 1:
				haveCard[3][nCardCount[3]++] = cardIndex[i];
				break;
			}//switch-case
			calScore();
		}//for
		for(int i=0; i<3; i++)
			cardCountL[i].setText(""+nCardCount[i]);
		cardCountL[3].setText(""+pScore);
		
		repaint();
	}
	
	public void addCard(int card1, int card2, int card3, int card4)
	{
		int[] cardIndex = new int[]{card1, card2, card3, card4};
		
		for(int i=0; i<4; i++)
		{
			switch(AllCard[cardIndex[i]].getValue())
			{
			case 16:
				haveCard[0][nCardCount[0]++] = cardIndex[i];
				break;
			case 8:
				haveCard[1][nCardCount[1]++] = cardIndex[i];
				break;
			case 4:
				haveCard[2][nCardCount[2]++] = cardIndex[i];
				break;
			case 2:
			case 1:
				haveCard[3][nCardCount[3]++] = cardIndex[i];
				break;
			}//switch-case
			calScore();
		}//for
		
			for(int i=0; i<3; i++)
				cardCountL[i].setText(""+nCardCount[i]);
			cardCountL[3].setText(""+pScore);
		
		repaint();
	}
			
	public void calScore()		//점수계산
	{
		if(nCardCount[0] >=3 && nCardCount[0] != prevScore[0] ) // 광이 3개 이상일때 
		{
			prevScore[0] = nCardCount[0];
			
			if(nCardCount[0] == 3)
			{	
				nScore = nScore + 3;		
				cardScore.setText(""+nScore);
				Audio_ music = new Audio_("Voice\\m_gwang3.wav");
				
				fieldPanel.drawEventImage("3gwang.png");		//삼광 그리기
			}
			
			if(nCardCount[0] == 4)
			{
				nScore = nScore + 1;
				cardScore.setText(""+nScore);
				Audio_ music = new Audio_("Voice\\m_gwang4.wav");
				
				fieldPanel.drawEventImage("4gwang.png");
			}
			
			
			if(nCardCount[0] ==5)
			{
				nScore = nScore + 11;
				cardScore.setText(""+nScore);
				Audio_ music = new Audio_("Voice\\m_gwang5.wav");
				
				fieldPanel.drawEventImage("5gwang.png");
			}
		}
		
		if(godori == false)
		{
			for(int i=0; i<10; i++)
				if(haveCard[1][i] == 4 || haveCard[1][i] == 12 || haveCard[1][i] == 29)
					godoriCount++;
			
			if(godoriCount == 3)
			{
				nScore = nScore + 5;
				cardScore.setText(""+nScore);
				Audio_ music = new Audio_("Voice\\m_godori.wav");
				
				fieldPanel.drawEventImage("godori.png");
				
				prevScore[1] += 1;
				godori = true;
			}
			else
				godoriCount = 0;		//고도리가 3개가 아니므로 다시 초기화
		}//if
		
		if(nCardCount[1] >=5 && nCardCount[1] != prevScore[1]) // 십끗이 5장 이상일때 
		{
			prevScore[1] = nCardCount[1];
			
			if(nCardCount[1] >=5)
			{
				nScore = nScore + 1;
				cardScore.setText(""+nScore);
			}		
		}
		
		if(chodan == false)
		{
			for(int i=0; i<9; i++)
			{
				if(haveCard[2][i] == 13 || haveCard[2][i] == 17 || haveCard[2][i] == 25 ) // 초단 일때 
					chodanCount++;
			}//for
			
			if(chodanCount == 3)
			{
				nScore = nScore + 3;
				cardScore.setText(""+nScore);
				Audio_ music = new Audio_("Voice\\m_cho.wav");
				
				fieldPanel.drawEventImage("chodan.png");
				
				chodan = true;
			}
			else
				chodanCount = 0;
		}//if
		
		if(hongdan == false)
		{
			for(int i=0; i<9; i++)
			{
				if(haveCard[2][i] == 1 || haveCard[2][i] == 5 || haveCard[2][i] == 9) // 홍단일때  
					hongdanCount++;
			}
			
			if(hongdanCount == 3)
			{
				nScore += 3;
				cardScore.setText(""+nScore);
				Audio_ music = new Audio_("Voice\\m_hong.wav");
				
				fieldPanel.drawEventImage("hongdan.png");
				
				hongdan = true;
			}
			else
				hongdanCount = 0;
			
		}//if (hongdan)
		
		if(chungdan == false)
		{
			for(int i=0; i<9; i++)
			{
				if(haveCard[2][i] == 21 || haveCard[2][i] == 33 || haveCard[2][i] == 37) // 청단일때 
					chungdanCount++;
			}
			
			if(chungdanCount == 3)
			{
				nScore = nScore + 3;
				cardScore.setText(""+nScore);
				Audio_ music = new Audio_("Voice\\m_chung.wav");
				
				fieldPanel.drawEventImage("chungdan.png");
				
				chungdan = true;
			}
			else
				chungdanCount = 0;
		}//if-chungdan	
		
		//----------------------------------------------------------------------띠 계산
		
		if(nCardCount[2] >= 5 && nCardCount[2] != prevScore[2]) // 띠가 5장 이상일때
		{
			prevScore[2] = nCardCount[2];
			
			nScore = nScore + 1;
	         cardScore.setText(""+nScore);
		}	//for
		
		//----------------------------------------------------------------------피 계산
		if(nCardCount[3] != prevScore[3])		//새로 추가된 카드가 있다면
		{
			for(int i=prevScore[3]; i<nCardCount[3]; i++)
				pScore += AllCard[haveCard[3][i]].getValue();
			prevScore[3] = nCardCount[3];
		}
		
		if(pScore >=10)
		{
			nScore = nScore + 1;
			cardScore.setText(""+nScore);
		}
	}
			
	public int getScore()
	{
		return nScore;
	}
	
	public int[] getCardCount()
	{
		return nCardCount;
	}
	
	public int removeCard()		//카드 제거
	{
		int removeCard=0;
		boolean flag = false;
		
		if(nCardCount[3] > 0)
		{
			for(int value=1; value<=3; value++)
			{
				for(int i=nCardCount[3]-1; i>=0; i--)
				{
					if(AllCard[haveCard[3][i]].getValue() == value)		//1점부터 차례대로 확인
					{
						removeCard = haveCard[3][i];
						
						for(int j=i; j<nCardCount[3]-1; j++)		//카드 중간꺼를 제거했으므로
							haveCard[3][j] = haveCard[3][j+1];		//재 정렬
						haveCard[3][--nCardCount[3]] = -1;			//재정렬한후 맨뒤에 남은거 삭제
						flag = true;		//제거 했으므로
						break;
					}//if
				}//for
				if(flag)
					break;
			}//for
		}//if
		else		//카드가 한장도 없을때
		{
			return -1;
		}
		
		cardCountL[3].setText(""+nCardCount[3]);		//카드뺏기고 카드 개수 다시 보이기
		repaint();
		return removeCard;
	}
	
	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
		
		int whatooWidth = (int)WhatooCard.cardSize.getWidth();
		int whatooHeight = (int)WhatooCard.cardSize.getHeight();
		
		for(int i=0; i<4; i++)
		{
			for(int k=0; k<nCardCount[i]; k++)		//각 자리마다 먹은 계수만큼 그리기
			{
				page.drawImage(AllCard[haveCard[i][k]].getImage(),
						location[i].x+13*(k%10), location[i].y+15*(k/10),
						whatooWidth, whatooHeight,this);
			}//for
		}//for
	}
	
}
