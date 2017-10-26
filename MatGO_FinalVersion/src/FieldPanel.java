
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class FieldPanel extends JPanel //필드 정보
{
	public static Image coverImg;
	private WhatooCard[] AllCard;		//전체 화투카드
	public static int[][] fieldCard;		//뒤집힌 카드들
	private int[] hiddenCard;		//뒤집혀야될 카드들
	private int nCardCount;			//뒤집혀야될 카드들의 남은 수
	private int coverTempCard;			//카드 뒤집을때 잠시 저장할 공간
	private String eventImage;				//이벤트 발생시 이미지 이름 저장
	
	private int prevCard;		//이전에 낸카드
	
	private User user;
	
	FieldPanel(WhatooCard[] AllCard)
	{
		coverImg = new ImageIcon("CardImage\\54.PNG").getImage();
		
		this.AllCard = AllCard;
		fieldCard = new int[12][7];	
		for(int i=0; i<12; i++)
			for(int k=0; k<7; k++)
				fieldCard[i][k] = -1;		//값초기화 
		
		this.setBounds(0,200,700,300);

		eventImage = null;
		
		coverTempCard = -1;		//카드 없다는 의미
	}
	
	public int[] getfieldCard()
	{
		int[] tempfieldCard = new int[]{
				fieldCard[0][0],fieldCard[1][0],fieldCard[2][0],
				fieldCard[3][0],fieldCard[4][0],fieldCard[5][0],
				fieldCard[6][0],fieldCard[7][0],fieldCard[8][0],
				fieldCard[9][0],fieldCard[10][0],fieldCard[11][0]};
		
		return tempfieldCard;
	}
	
	public void addFieldCard(int cardIndex)
	{	
		boolean addCard = false;
		
		if(cardIndex >= 48 && cardIndex <= 50)		//보너스 카드 번호 일때
		{
			for(int i=0; i<12; i++)//이전에 낸카드 위에다가 보너스 카드를 올리기 위해서 찾음
				if(fieldCard[i][0] != -1 && AllCard[fieldCard[i][0]].getMonth() == AllCard[prevCard].getMonth())
				{
					for(int k=1; k<7; k++)
						if(fieldCard[i][k] == -1)
						{
							Audio_ music = new Audio_("Voice\\e_basic.wav");
							fieldCard[i][k] = cardIndex;
							addCard = true;
							repaint();
							break;
						}//if
				}
		}
		if(cardIndex == 51)		//폭탄 카드 일떄
		{
			return;
		}
		else
		{
			for(int i=0; i<12; i++)
				//추가하려는 카드가 필드에 존재하는 카드와 같은 월(index) 일경우 겹친다.
				if(fieldCard[i][0] != -1 && AllCard[fieldCard[i][0]].getMonth() == AllCard[cardIndex].getMonth())
				{
					for(int k=1; k<7; k++){
						if(fieldCard[i][k] == -1)
						{
							if(Dealer.nStart != 0)
							{
								Audio_ music = new Audio_("Voice\\e_basic.wav");
							}// 그 뒤부턴 맞힐 때마다 소리 나게하기 
								
							fieldCard[i][k] = cardIndex;
							addCard = true;
							repaint();
							break;
						}//if
					}//for
				}//if
			prevCard = cardIndex;		//이전에 낸카드로 저장
		}
		//카드를 추가하지 않았다면
		while(!addCard)
		{
			Random rand = new Random();
			int x = rand.nextInt(12);
			
			if(fieldCard[x][0] == -1){
				fieldCard[x][0] = cardIndex;
				repaint();
				break;
			}
			prevCard = cardIndex;		//이전에 낸카드로 저장
		}//while
		
	}//addFieldCard
			
	public void addHiddenCard(int[] hiddenCard, int cardCount)
	{
		this.hiddenCard = hiddenCard;
		nCardCount = cardCount;
	}
	
	public void removeCard(User user, int selectCard, int turnUpCard, Computer com)		//유저용
	{	
		int[] temp = new int[]{selectCard, turnUpCard};
		boolean bbuk = false;
		
		for(int j=0; j<2; j++){
			for(int i=0; i<12; i++)
			{
				boolean flag = false; 		//같은 숫자 찾으면 true	(숫자를 찾았는데 for문을 더 돌릴필요없어서)
				if(fieldCard[i][0] != -1 && 
						AllCard[fieldCard[i][0]].getMonth() == AllCard[temp[j]].getMonth())		//카드 제출한 부분만 확인
				{
					int cardCount=0;
					int bonusCardCount = 0;
					int sumCardCount=0;
					
					flag = true;
					for(int k=0; k<7; k++)
					{
						if(fieldCard[i][k] == -1)
							break;
						else if(AllCard[fieldCard[i][k]].getMonth() != 13)		//보너스카드가 아니면
							cardCount++;
						else		//보너스 카드
							bonusCardCount++;
					}

					sumCardCount = cardCount+bonusCardCount;
					
					if(cardCount == 1)
					{
						for(int k=1; k<sumCardCount; k++)		//보너스 카드만 없애기
						{
							user.addScoreCard(fieldCard[i][k]);
							fieldCard[i][k] = -1;
						}
					}//if
					else if(cardCount == 2)
					{
						for(int k=0; k<sumCardCount; k++)		//모든 카드 제거
						{
							user.addScoreCard(fieldCard[i][k]);
							fieldCard[i][k] = -1;
						}
						
						if(fieldCard[i][0] == selectCard &&
								fieldCard[i][sumCardCount-1] == turnUpCard)		//쪽
						{
							user.addScoreCard(com.removeScoreCard()); 	//카드 뺏어오기
							System.out.println("쪽");
						}
					}//else if(cardCount == 2)
					else if(cardCount == 3){
						if(fieldCard[i][1] == selectCard &&
								fieldCard[i][sumCardCount-1] == turnUpCard)		//기존에 1장 존재 (뻑)
						{
							if(bbuk == false)
							{
								drawEventImage("bbuk.png");
								System.out.println("쌋다.");
								bbuk = true;
							}
						}
						else		//기존에 2장있는 경우
						{
							String[] buttons = {"왼쪽", "오른쪽"};
							ImageIcon cardImageL = new ImageIcon(AllCard[fieldCard[i][1]].getImage());
							ImageIcon cardImageR = new ImageIcon(AllCard[fieldCard[i][0]].getImage());

							int choiceCard = JOptionPane.showOptionDialog(null,
									cardImageL, "어느것을 먹을까요?",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.PLAIN_MESSAGE,
									cardImageR, buttons, "왼쪽");
							
							user.addScoreCard(fieldCard[i][choiceCard], fieldCard[i][2]);	//맨 밑을 골랐을시 중간꺼 맨밑에 놓음
							fieldCard[i][choiceCard] = -1;
							fieldCard[i][2] = -1;
							if(choiceCard == 0){
								fieldCard[i][0] = fieldCard[i][1];
								fieldCard[i][1] = -1;
							}
							
							for(int k=3; k<sumCardCount; k++)		//보너스 획득
							{
								user.addScoreCard(fieldCard[i][k]);
								fieldCard[i][k] = -1;
							}
							repaint();		//일단 가져오는거 보여주기
						}//else
					}//else-if
					else if(cardCount == 4)
					{
						if(AllCard[fieldCard[i][sumCardCount-1]].getMonth() == turnUpCard)	//따닥
						{
							
						}
						for(int k=0; k<sumCardCount; k++)
						{
							user.addScoreCard(fieldCard[i][k]);
							fieldCard[i][k] = -1;		//필드에서 제거
						}
						user.addScoreCard(com.removeScoreCard());			//피 뺏어오기
					}//else if
					if(bbuk == false)
					{
						for(int k=0; k<bonusCardCount; k++)
						{
							try {
								repaint();
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							user.addScoreCard(com.removeScoreCard());
						}
					}
				}//if
				if(flag == true)
					break;
			}//for(i)
		}//for(j)
		
		int i;
		for(i=0; i<12; i++)		//싹쓸이 확인
			if(fieldCard[i][0] != -1)
				break;
		if(i == 12)
		{
			System.out.println("판쓸이");
			user.addScoreCard(com.removeScoreCard());
		}
		
		repaint();
	}
	public void removeCard(Computer com, int selectCard, int turnUpCard, User user)		//컴퓨터용
	{
		int[] temp = new int[]{selectCard, turnUpCard};
		boolean bbuk = false;
		
		for(int j=0; j<2; j++){
			for(int i=0; i<12; i++)
			{
				boolean flag = false; 		//같은 숫자 찾으면 true	(숫자를 찾았는데 for문을 더 돌릴필요없어서)
				if(fieldCard[i][0] != -1 && 
						AllCard[fieldCard[i][0]].getMonth() == AllCard[temp[j]].getMonth())		//카드 제출한 부분만 확인
				{
					int cardCount=0;
					int bonusCardCount = 0;
					int sumCardCount=0;
					
					flag = true;
					for(int k=0; k<7; k++)
					{
						if(fieldCard[i][k] == -1)
							break;
						else if(AllCard[fieldCard[i][k]].getMonth() != 13)		//보너스카드가 아니면
							cardCount++;
						else		//보너스 카드
							bonusCardCount++;
					}
					sumCardCount = cardCount+bonusCardCount;
					
					if(cardCount == 1)
					{
						for(int k=1; k<sumCardCount; k++)		//보너스 카드만 없애기
						{
							com.addScoreCard(fieldCard[i][k]);
							fieldCard[i][k] = -1;
						}
					}//if
					else if(cardCount == 2)
					{
						for(int k=0; k<sumCardCount; k++)		//모든 카드 제거
						{
							com.addScoreCard(fieldCard[i][k]);
							fieldCard[i][k] = -1;
						}
						
						if(fieldCard[i][0] == selectCard &&
								fieldCard[i][sumCardCount-1] == turnUpCard)		//쪽
						{
							com.addScoreCard(user.removeScoreCard()); 	//카드 뺏어오기
							System.out.println("쪽");
						}
					}//else if(cardCount == 2)
					else if(cardCount == 3){
						if(fieldCard[i][1] == selectCard &&
								fieldCard[i][sumCardCount-1] == turnUpCard)		//기존에 1장 존재 (뻑)
						{
							if(bbuk == false)
							{
								drawEventImage("bbuk.png");
								System.out.println("쌋다.");
								bbuk = true;
							}
						}
						else		//기존에 2장있는 경우
						{
							Random randChoice = new Random(); 			//임의로 카드 선택
							int choiceCard = randChoice.nextInt(2);
							
							com.addScoreCard(fieldCard[i][choiceCard], fieldCard[i][2]);	//맨 밑을 골랐을시 중간꺼 맨밑에 놓음
							fieldCard[i][choiceCard] = -1;
							fieldCard[i][2] = -1;
							if(choiceCard == 0){
								fieldCard[i][0] = fieldCard[i][1];
								fieldCard[i][1] = -1;
							}
							
							for(int k=3; k<sumCardCount; k++)		//보너스 획득
							{
								com.addScoreCard(fieldCard[i][k]);
								fieldCard[i][k] = -1;
							}
							repaint();		//일단 가져오는거 보여주기
						}//else
					}//else-if
					else if(cardCount == 4)
					{
						for(int k=0; k<sumCardCount; k++)
						{
							com.addScoreCard(fieldCard[i][k]);
							fieldCard[i][k] = -1;		//필드에서 제거
						}
						
						if(fieldCard[i][sumCardCount-1] == turnUpCard)	//따닥
						{
							
						}
						com.addScoreCard(user.removeScoreCard());			//피 뺏어오기
						
					}//else if
					
					if(bbuk == false)
					{
						for(int k=0; k<bonusCardCount; k++)
						{
							try {
								repaint();
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							com.addScoreCard(user.removeScoreCard());
						}
					}
				}//if
				if(flag == true)
					break;
			}//for(i)
		}//for(j)
		
		int i;
		for(i=0; i<12; i++)		//싹쓸이 확인
			if(fieldCard[i][0] != -1)
				break;
		if(i == 12)
		{
			System.out.println("판쓸이");
			com.addScoreCard(user.removeScoreCard());
		}
		
		repaint();
	}//removeCard-computer
	
	public int turnUpTheCard()		//히든카드 뒤집기
	{
		try {
			coverTempCard = hiddenCard[nCardCount];		//카드 뒤집는 모션 만듬
			this.repaint();
		
			Thread.sleep(500);
			
			coverTempCard = -1;
			this.repaint();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.hiddenCard[nCardCount--];
	}

	public void setUser(User user)
	{
		this.user = user;
	}
	
	public int getHiddenCard()
	{
		return this.hiddenCard[nCardCount--];
	}
	
	public void checkField(User user)		//시작시 보너스 카드, 총통 등을 검사
	{
		for(int i=0; i<12; i++)
		{
			if(fieldCard[i][3] != -1)		//처음에 4장 전부 쌓여있는 경우 (총통)
				user.setGoGo(false); 		//바로 게임종료	
		}//for
	}
	
	public void drawEventImage(String str)
	{
		try {
			eventImage = str;
			repaint();
			Thread.sleep(2000);
			
			eventImage = null;
			repaint();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
		
		page.drawImage(new ImageIcon("CardImage\\main2.png").getImage(), 	//필드 배경 마크 그리기
				0, 0, 700, 300, this);
		
		Dimension panel = this.getSize();		//패널 크기 가져오기
		//화면의 중심 찾기
		int whatooHeight = (int)WhatooCard.cardSize.getHeight();
		int whatooWidth = (int)WhatooCard.cardSize.getWidth();
		Point cardPos = new Point((int)(panel.getWidth()/2 - WhatooCard.cardSize.getWidth()/2),
				(int)(panel.getHeight()/2 - WhatooCard.cardSize.getHeight()/2));
		
		int[][] location = new int[][] {
			{cardPos.x -(whatooWidth+20)*3, cardPos.y+(whatooHeight/2+20)},
			{cardPos.x-(whatooWidth+20)*2, cardPos.y+(whatooHeight/2+20)},
			{cardPos.x-(whatooWidth+20)*1, cardPos.y+(whatooHeight/2+20)},
			{cardPos.x+(whatooWidth+20)*1, cardPos.y+(whatooHeight/2+20)},
			{cardPos.x+(whatooWidth+20)*2, cardPos.y+(whatooHeight/2+20)},
			{cardPos.x+(whatooWidth+20)*3, cardPos.y+(whatooHeight/2+20)},
			
			{cardPos.x-(whatooWidth+20)*3, cardPos.y-(whatooHeight/2+20)},
			{cardPos.x-(whatooWidth+20)*2, cardPos.y-(whatooHeight/2+20)},
			{cardPos.x-(whatooWidth+20)*1, cardPos.y-(whatooHeight/2+20)},
			{cardPos.x+(whatooWidth+20)*1, cardPos.y-(whatooHeight/2+20)},
			{cardPos.x+(whatooWidth+20)*2, cardPos.y-(whatooHeight/2+20)},
			{cardPos.x+(whatooWidth+20)*3, cardPos.y-(whatooHeight/2+20)},
			
			{cardPos.x, cardPos.y, 0, 0},		//센터
			{cardPos.x+whatooWidth/2, cardPos.y-whatooHeight/3}
		};
		
		page.drawImage(coverImg, location[12][0], location[12][1],		//가운데 뒤집혀있는 카드 뭉치
				whatooWidth, whatooHeight, this);
		
		for(int i=0; i<12; i++)
			for(int k=0; k<7; k++)
				if(fieldCard[i][k] != -1)
					page.drawImage(AllCard[fieldCard[i][k]].getImage(), 
							location[i][0]+k*10, location[i][1]+k*10,
							whatooWidth, whatooHeight,this);
		
		if(coverTempCard != -1)
			page.drawImage(AllCard[coverTempCard].getImage(),
					location[13][0], location[13][1], whatooWidth, whatooHeight,this);
		
		if(eventImage != null)
		{
			Image temp = new ImageIcon("CardImage\\"+eventImage).getImage();
			ImageIcon a = new ImageIcon("CardImage\\"+eventImage);
			
			page.drawImage(temp, 
					this.getWidth()/2 - a.getIconWidth()/2, this.getHeight()/2-a.getIconHeight()/2,
					a.getIconWidth(), a.getIconHeight(), this);
		}
		
	}//paintComponent

}
