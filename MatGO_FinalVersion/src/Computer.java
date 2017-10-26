
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Computer extends JPanel
{
	private ScorePanel comScorePanel;		//컴퓨터 획득 패 패널
	private WhatooCard[] AllCard;			//전체 카드
	private int[] card;			//가지고 있는 패의 인덱스
	private int nCardCount;			//가지고 있는 패 개수
	private JButton[] cardButton;		//카드
	private int selectedCard;				//고른카드
	private int go_score;
	private int goCount;
	private int swingCount;			//흔들기
	private int[] sameCardCount;
	
	private boolean gogo;
	private Audio_ music;
	
	private FieldPanel fieldPanel;
	private User user;
	
	Computer(WhatooCard[] AllCard, FieldPanel field)
	{
		card = new int[10];		//카드 개수 초기화
		this.AllCard = AllCard;
		nCardCount = 0;
		selectedCard = -1;
		goCount = 0;
		
		this.setBounds(0,0,700,200);
		this.setBackground(new Color(74,126,38));		//대충 녹색
		this.setLayout(null);
		
		fieldPanel = field;
		sameCardCount = new int[13];
		
		cardButton = new JButton[10];
		gogo = true;					//스탑 했는지 안했는지 체크
		
		for(int i=0; i<10; i++)
		{
			int whatooHeight = (int)WhatooCard.cardSize.getHeight();
			int whatooWidth = (int)WhatooCard.cardSize.getWidth();
			cardButton[i] = new JButton(new ImageIcon("CardImage\\51.PNG"));
			cardButton[i].setBounds((int)(WhatooCard.cardSize.getWidth()+20)*i + 60,19,
					whatooWidth, whatooHeight);
			this.add(cardButton[i]);
			
		}
		
		comScorePanel = new ScorePanel(new Point(0,100),AllCard,field);
		this.add(comScorePanel);
		
	}
	
	public boolean getGoGo()
	{
		return gogo;
	}
	
	public void addCard(int cardIndex)
	{
		this.card[nCardCount++] = cardIndex; 		//패 추가
		sameCardCount[cardIndex/4]++;
		sort(nCardCount);
	}
	public void sort(int n)
	{
		int temp;
		
		for( int i=0; i<n; i++)
		{
			for(int j=i+1; j<n; j++)
			{
				if(card[i] > card[j] )
				{
					temp = card[j];
					card[j] = card[i];
					card[i] = temp;
				}
			}
		}
	}
	
	public void setUserPanel(User user)
	{
		this.user = user;
	}
	
	public void addScoreCard(int card1){
		comScorePanel.addCard(card1);		//먹은 카드 추가
	}
	
	public void addScoreCard(int card1, int card2){
		comScorePanel.addCard(card1, card2);		//먹은 카드 추가
	}
	public void addScoreCard(int card1, int card2, int card3, int card4){
		comScorePanel.addCard(card1, card2, card3, card4);		//먹은 카드 추가
	}
	
	public int selectCard(int card, int index)		//카드 제출
	{	
		if(card <= 47 && sameCardCount[card/4] == 3)		//폭탄
		{
			int[] fieldPanelCard = fieldPanel.getfieldCard();
			int boomIndex;	//폭탄 낼 카드 위치
			int i;
			
			swingCount++;		//흔들기 증가
			
			for(boomIndex=0; boomIndex<nCardCount; boomIndex++)		//폭탄 시작 위치 알아냄
				if(AllCard[card].getMonth() == AllCard[this.card[boomIndex]].getMonth())
					break;
			
			nCardCount--;
			
			for(i=0; i<12; i++)
			{
				if(fieldPanelCard[i] != -1 && 
						AllCard[fieldPanelCard[i]].getMonth() == AllCard[card].getMonth())//필드에 카드 한장존재
				{
					fieldPanel.addFieldCard(this.card[boomIndex]);
					sameCardCount[this.card[boomIndex]/4]--;
					fieldPanel.addFieldCard(this.card[boomIndex+1]);
					sameCardCount[this.card[boomIndex+1]/4]--;
					selectedCard = this.card[boomIndex+2];
					sameCardCount[this.card[boomIndex+2]/4]--;
					
					for(int k=boomIndex; k<nCardCount; k++)
						this.card[k] = this.card[k+1];
					
					this.card[boomIndex] = 51;	//폭탄
					this.card[boomIndex+1] = 51;	//폭탄
					
					cardButton[nCardCount].setEnabled(false);
					cardButton[nCardCount].setVisible(false);
					
					repaint();
					
					break;
				}//if
			}//for
			
			if(i == 12)		//필드에 존재 안하면 흔들기만 하고 선택한 카드를 제출 한다.
			{
				for(i=index; i<nCardCount; i++)
					this.card[i] = this.card[i+1];
				
				cardButton[nCardCount].setEnabled(false);
				cardButton[nCardCount].setVisible(false);
				selectedCard = card;
				
				System.out.println("흔들기");
				
				sameCardCount[card/4]--;
			}
			
			sort(nCardCount);		//카드 정렬(폭탄을 맨뒤로 보내기 위해서)
		}//if
		else{
			nCardCount--;
			for(int i=index; i<nCardCount; i++)
				this.card[i] = this.card[i+1];
			
			if(card >= 48 && card <= 50)		//보너스 패
			{
				addScoreCard(card);			//보너스피 추가
				addScoreCard(user.removeScoreCard());	//상대 피 뺏기
				addCard(fieldPanel.getHiddenCard());	//현재 패에 카드 추가
				selectedCard = -1;
			}
			else
			{
				cardButton[nCardCount].setEnabled(false);
				cardButton[nCardCount].setVisible(false);
				selectedCard = card;
				
				if(card/4 <12)		//폭탄인경우 예외
					sameCardCount[card/4]--;
			}
		}//else
		
		return selectedCard;
	}
	
	public void setEnabledCard(boolean a)
	{
		for(int i=0; i<nCardCount; i++)
			cardButton[i].setEnabled(a);
	}
	
	public int removeScoreCard()
	{
		return comScorePanel.removeCard();
	}
	
	public int getSelectedCard(int[] fieldCard)
	{	
		if(AllCard[card[nCardCount-1]].getMonth() == 13)	//보너스 카드를 들고있으면 우선적으로 낸다.
			return selectCard(card[nCardCount-1], nCardCount-1);
		
		for(int i=0; i<nCardCount; i++)
			for(int k=0; k<12; k++)
			{	//필드카드가 존재 하고 손에 들고있는 카드와 같은 카드 일경우
				if(fieldCard[k] != -1 && 
						AllCard[card[i]].getMonth() == AllCard[fieldCard[k]].getMonth())
				{
					return selectCard(card[i], i);					
				}
			}
		
		//필드랑 손에 들고 있는카드가 같지 않을 경우
		Random rand = new Random();
		int randIndex = rand.nextInt(nCardCount);
		
		return selectCard(card[randIndex], randIndex);
	}
	
	public int[] getScoreCardCount()
	{
		return comScorePanel.getCardCount();
	}
	
	public int getAllScore()			//총 점수 가져오기
	{
		return comScorePanel.getScore();
	}
	
	public int getGoCount()
	{
		return goCount;
	}
	
	public int getSwingCount()
	{
		return swingCount;
	}
	
	public void checkEndGame()
	{
		selectedCard = -1;
		
		for(int i=0; i<12; i++)
			if(sameCardCount[i] == 4)		//총통
				gogo = false;
		
		if(comScorePanel.getScore()>=7 && comScorePanel.getScore()>go_score)
		{	
			if(nCardCount == 0)
			{
				gogo = false;
				
				Audio_ music = new Audio_("Voice\\m_stop.mp3");
				fieldPanel.drawEventImage("stop_1.png");
			}
			else if(user.getAllScore() > 4)		//스탑
			{
				gogo = false;
				
				for(int i=0; i<12; i++)
					for(int j=0; j<4; j++)
						FieldPanel.fieldCard[i][j] = -1;
				
				Audio_ music = new Audio_("Voice\\m_stop.mp3");
				fieldPanel.drawEventImage("stop_1.png");
			}
			else					//유저가  4점 이하여서 고고고고!!
			{
				goCount++;
				go_score =  comScorePanel.getScore();
				
				if( goCount == 1 )
					music = new Audio_("Voice\\m_go1.wav");
		
				else if ( goCount == 2)
					music = new Audio_("Voice\\m_go2.wav");
				
				else if (goCount==3)
					music = new Audio_("Voice\\m_go3.wav");
				
				else if (goCount==4)
					music = new Audio_("Voice\\m_go4.wav");
				
				else if (goCount==5)
					music = new Audio_("Voice\\m_go5.wav");
				fieldPanel.drawEventImage(goCount+"go.png");
			}//else
			

		}//if
	}
	
	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
		
		for(int i=0; i<nCardCount; i++)
		{	
			/*			//이부분은 상태 패를 보여준다.
			ImageIcon tempimg = new ImageIcon(AllCard[card[i]].getImage());
			*/
			ImageIcon tempimg = new ImageIcon("CardImage\\53.png");
			cardButton[i].setIcon(tempimg);
		}//for
	}
}





