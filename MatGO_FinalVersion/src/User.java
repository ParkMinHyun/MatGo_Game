
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class User extends JPanel
{
	private ScorePanel userScorePanel;		//유저 획득 패 패널
	private WhatooCard[] AllCard;			//전체 카드
	private int[] card;			//가지고 있는 패의 인덱스
	private int nCardCount;			//가지고 있는 패 개수
	private JButton[] cardButton;		//카드
	private int selectedCard;				//고른카드
	
	private ClickListener clickL;
	private int goCount;			//몇고 했는지
	private int go_score;      // go한 뒤 아무것도 못 먹었을 때 확인해주는 변수
	private int swingCount;			//흔들기 몇번 했는지
	private Audio_ music;
	private boolean gogo;			//게임 계속 할지 안할지 체크
	int[] sameCardCount;		//총통 검사용 배열
	
	private Computer com;
	private FieldPanel fieldPanel;
	
	User(WhatooCard[] AllCard, FieldPanel field)
	{
		card = new int[10];		//카드 개수 초기화
		this.AllCard = AllCard;
		nCardCount = 0;
		selectedCard = -1;
		
		fieldPanel = field;

		gogo = true;		//stop했는지 안했는지 체크
		sameCardCount = new int[13];
		
		this.setBounds(0,500,700,200);
		this.setBackground(new Color(74,126,38));		//대충 녹색
		this.setLayout(null);
		
		clickL = new ClickListener();
		
		cardButton = new JButton[10];
		
		for(int i=0; i<10; i++)
		{
			int whatooHeight = (int)WhatooCard.cardSize.getHeight();
			int whatooWidth = (int)WhatooCard.cardSize.getWidth();
			cardButton[i] = new JButton(new ImageIcon("CardImage\\50.PNG"));
			cardButton[i].setBounds((int)(WhatooCard.cardSize.getWidth()+20)*i + 60,119,
					whatooWidth, whatooHeight);
			this.add(cardButton[i]);
			
			cardButton[i].addMouseListener(clickL); 		//마우스 리스너 장착
		}
		
		userScorePanel = new ScorePanel(new Point(0,0),AllCard,field);
		this.add(userScorePanel);
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
	
	public void addScoreCard(int card1)
	{
		userScorePanel.addCard(card1);
	}
	
	public void addScoreCard(int card1, int card2){
		userScorePanel.addCard(card1, card2);		//먹은 카드 추가
	}
	
	public void addScoreCard(int card1, int card2, int card3, int card4){
		userScorePanel.addCard(card1, card2, card3, card4);		//먹은 카드 추가
	}
	
	public int removeScoreCard()		//카드제거
	{
		return userScorePanel.removeCard();
	}
	
	public void selectCard(int card, int index)		//카드 제출
	{	
		if(cardButton[index].isEnabled())			//카드 봉인되있으면 제출 못함
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
					addScoreCard(com.removeScoreCard());	//상대 피 뺏기
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
		}//if
	}
	
	public void setEnabledCard(boolean a)
	{
		for(int i=0; i<nCardCount; i++)
			cardButton[i].setEnabled(a);
	}
	
	public void setComputerPanel(Computer com)
	{
		this.com = com;
	}
	
	public int getSelectedCard()
	{
		return selectedCard;
	}
	
	public int getCardCount()
	{
		return nCardCount;
	}
	
	public int[] getScoreCardCount()
	{
		return userScorePanel.getCardCount();
	}
	
	public int getSwingCount()
	{
		return swingCount;
	}
	
	public int getGoCount()
	{
		return goCount;
	}
	
	public boolean getGoGo() // gogo
	{
		return gogo;
	}
	
	public void setGoGo(boolean gogo)
	{
		this.gogo = gogo;
	}
	
	public int getAllScore()			//총 점수 가져오기
	{
		return userScorePanel.getScore();
	}
	
	public void checkEndGame()
	{
		selectedCard = -1;
		
		for(int i=0; i<12; i++)
			if(sameCardCount[i] == 4)		//총통
				gogo = false;
		
		if(userScorePanel.getScore()>=7 && userScorePanel.getScore()>go_score) // go & stop ( 7점 넘었을 떄 
																				  //			,go한 점수가 전보다 높을 때
		{	
			if(nCardCount == 0)		//패가 없으므로 무조건 스톱
			{	
				for(int i=0; i<12; i++)
					for(int j=0; j<4; j++)
						FieldPanel.fieldCard[i][j] = -1; // 필드에 있는 카드 없애기
					
				for(int i=0; i<nCardCount; i++)
				{
					cardButton[i].setEnabled(false);// 버튼 비활성화
					cardButton[i].setVisible(false);
				}
				
				Audio_ music = new Audio_("Voice\\m_stop.mp3");
				fieldPanel.drawEventImage("stop_1.png");
				
				gogo = false;
			}
			else		//패가 존재
			{
				String[] buttons = {"GO", "STOP"};
				ImageIcon Left = new ImageIcon("CardImage\\stop.PNG");
				ImageIcon Right = new ImageIcon("CardImage\\go.PNG");
			
			    int GoStop = JOptionPane.showOptionDialog(null,Left, "Go or Stop?",
							 JOptionPane.YES_NO_OPTION,
					   		 JOptionPane.PLAIN_MESSAGE,	Right, buttons, "");
				
				
				if (GoStop == JOptionPane.YES_OPTION) 
				{
					goCount++;
					go_score =  userScorePanel.getScore();
					
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
				}
				else		//스탑일때
				{
					Audio_ music = new Audio_("Voice\\m_stop.mp3");
					
					
					for(int i=0; i<12; i++)
						for(int j=0; j<4; j++)
							FieldPanel.fieldCard[i][j] = -1; // 필드에 있는 카드 없애기
						
					for(int i=0; i<nCardCount; i++)
					{	
						cardButton[i].setEnabled(false);// 버튼 비활성화
						cardButton[i].setVisible(false);
					}
					
					fieldPanel.drawEventImage("stop_1.png");
					
					gogo = false;
				}//else
			}//else
		}
		
	}//checkEndGame
	
	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
		
		for(int i=0; i<nCardCount; i++)
		{	
			ImageIcon tempimg = new ImageIcon(AllCard[card[i]].getImage());
			cardButton[i].setIcon(tempimg);
		}//for
	}
	
	public class ClickListener implements MouseListener
	{
		public void mousePressed(MouseEvent event){}
		public void mouseReleased(MouseEvent event){}
		
		public void mouseClicked(MouseEvent event)
		{
			Object button = event.getSource();
			
			for(int i=0; i<nCardCount; i++)
			{
				if(button.equals(cardButton[i]))		//누른버튼으로 카드 찾기
				{		
					if(AllCard[card[i]].getMonth() != 13)
					{
						selectCard(card[i],i);			//카드 및 면번째 카드인지 정보 전달
						setEnabledCard(false);			//카드 봉인
					}
					else
						selectCard(card[i],i);			//카드 및 면번째 카드인지 정보 전달
					break;
				}
			}
		}
		
		public void mouseEntered(MouseEvent event)		//그림 올리기
		{
			Object button = event.getSource();
			
			for(int i=0; i<nCardCount; i++)
				if(button.equals(cardButton[i]))
				{
					cardButton[i].setLocation( // component 위치 지정
							cardButton[i].getX(), cardButton[i].getY() - 20);
				}
			repaint();
		}
		
		public void mouseExited(MouseEvent event)		//그림 내리기
		{
			Object button = event.getSource();
			
			for(int i=0; i<nCardCount; i++)
				if(button.equals(cardButton[i]))
				{
					cardButton[i].setLocation(
							cardButton[i].getX(), cardButton[i].getY() + 20);
				}
			repaint();
		}
	}
}