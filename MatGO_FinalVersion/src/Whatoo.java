
import java.awt.*;
import javax.swing.*;

public class Whatoo extends JPanel implements Runnable
{
	public static int[][] cardValueSetting = new int[][] {		//화투 값 임시저장
		{16, 4, 1, 1}, {8, 4, 1, 1}, {16, 4, 1, 1}, {8, 4, 1, 1},
		{8, 4, 1, 1}, {8, 4, 1, 1}, {8, 4, 1, 1}, {16, 8, 1, 1},
		{8, 4, 1, 1}, {8, 4, 1, 1}, {16, 2, 1, 1}, {16, 8, 4, 2},
		{2, 2, 3, 100}, {0, 0, 0, 0} 	//100은 폭탄
	};
	
	private WhatooCard[] AllCard;
	private User user;
	private Computer com;
	private FieldPanel fieldPanel;
	private Dealer dealer;
	private final int TIME = 500;
	private Thread gameStartThread;		//게임 동작을 다른 프로세서로 동작
	private PrimaryPanel primary;
	private int comSelectCard;
	private int turnUpCard;
	
	
	Whatoo(PrimaryPanel primary)		//화투 클래스 초기화
	{
		this.setBounds(0,0,700,700);
		this.setLayout(null);
		
		gameStartThread = null;		//쓰레드 초기화
		
		this.primary = primary; 
		
		AllCard = new WhatooCard[52];		//카드 52개 선언	(폭탄은 초기화만하고 게임에서 제외한다)
		
		for(int i=0; i<52; i++)		//카드 초기화
		{
			Image image = new ImageIcon("CardImage\\"+(i+1)+".PNG").getImage();
			AllCard[i] = new WhatooCard(i, (i/4)+1, image);
			AllCard[i].setValue(cardValueSetting[i/4][i%4]);
		}
		
		fieldPanel = new FieldPanel(AllCard);	//바닥 패널
		
		user = new User(AllCard, fieldPanel);
		com = new Computer(AllCard, fieldPanel);
		
		user.setComputerPanel(com);
		com.setUserPanel(user);
		
		dealer = new Dealer(fieldPanel, user, com);		//딜러가 카드 섞고 분배해준다.
		
		this.add(user);
		this.add(com);
		this.add(fieldPanel);
	}
	
	public User getUser()
	{
		return user;
	}
	
	public Computer getCom()
	{
		return com;
	}
	
	public void start()
	{
		if(gameStartThread == null)
			gameStartThread = new Thread(this);
		
		gameStartThread.start();
	}
	
	public void stop()
	{
		if(gameStartThread != null)
			gameStartThread.stop();
	}
	
	public void run()
	{
		fieldPanel.checkField(user);		//총통 있는지
		user.checkEndGame();
		com.checkEndGame();
		
		while(user.getGoGo() && com.getGoGo())
		{
			try {
				
				while(user.getSelectedCard() == -1)		//입력 받을때까지 
				{
					gameStartThread.sleep(TIME);
				}
				
				fieldPanel.addFieldCard(user.getSelectedCard());		//카드 내기
				gameStartThread.sleep(TIME);
				
				turnUpCard = fieldPanel.turnUpTheCard();
				fieldPanel.addFieldCard(turnUpCard); 		//카드 뒤집기
				gameStartThread.sleep(TIME);
				
				while(AllCard[turnUpCard].getMonth() == 13)		//보너스 카드라면
				{
					turnUpCard = fieldPanel.turnUpTheCard();
					fieldPanel.addFieldCard(turnUpCard);
					gameStartThread.sleep(TIME);
				}
				
				fieldPanel.removeCard(user, user.getSelectedCard(), turnUpCard,com);
				gameStartThread.sleep(TIME);
				
				user.checkEndGame();
				
				if(!user.getGoGo())		//스탑 했다면
					break;
				
				//컴퓨터				
				while((comSelectCard = com.getSelectedCard(fieldPanel.getfieldCard())) == -1)
				{
					gameStartThread.sleep(TIME);
				}

				fieldPanel.addFieldCard(comSelectCard);
				gameStartThread.sleep(TIME);
				
				turnUpCard = fieldPanel.turnUpTheCard();
				fieldPanel.addFieldCard(turnUpCard);
				gameStartThread.sleep(TIME);
				while(AllCard[turnUpCard].getMonth() == 13)		//보너스 카드라면
				{
					turnUpCard = fieldPanel.turnUpTheCard();
					fieldPanel.addFieldCard(turnUpCard);
					gameStartThread.sleep(TIME);
				}
				
				fieldPanel.removeCard(com, comSelectCard, turnUpCard,user);
				gameStartThread.sleep(TIME);
				
				com.checkEndGame();
				
				user.setEnabledCard(true); 		//카드 선택할수있도록 버튼 봉인해제
				
				if(!com.getGoGo())		//스탑 했다면
					break;
				
				if(user.getCardCount() == 0)		//나가리
				{
					//비김
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//try-catch
		}//while
		
		primary.setPage(PageConstants.END_PAGE);
		//종료 패널에 승자 패자 정보 및 각종 정보 넘겨주기
		
		this.stop();
	}//run()
}
