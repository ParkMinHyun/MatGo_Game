
import java.awt.*;
import javax.swing.*;

public class Whatoo extends JPanel implements Runnable
{
	public static int[][] cardValueSetting = new int[][] {		//ȭ�� �� �ӽ�����
		{16, 4, 1, 1}, {8, 4, 1, 1}, {16, 4, 1, 1}, {8, 4, 1, 1},
		{8, 4, 1, 1}, {8, 4, 1, 1}, {8, 4, 1, 1}, {16, 8, 1, 1},
		{8, 4, 1, 1}, {8, 4, 1, 1}, {16, 2, 1, 1}, {16, 8, 4, 2},
		{2, 2, 3, 100}, {0, 0, 0, 0} 	//100�� ��ź
	};
	
	private WhatooCard[] AllCard;
	private User user;
	private Computer com;
	private FieldPanel fieldPanel;
	private Dealer dealer;
	private final int TIME = 500;
	private Thread gameStartThread;		//���� ������ �ٸ� ���μ����� ����
	private PrimaryPanel primary;
	private int comSelectCard;
	private int turnUpCard;
	
	
	Whatoo(PrimaryPanel primary)		//ȭ�� Ŭ���� �ʱ�ȭ
	{
		this.setBounds(0,0,700,700);
		this.setLayout(null);
		
		gameStartThread = null;		//������ �ʱ�ȭ
		
		this.primary = primary; 
		
		AllCard = new WhatooCard[52];		//ī�� 52�� ����	(��ź�� �ʱ�ȭ���ϰ� ���ӿ��� �����Ѵ�)
		
		for(int i=0; i<52; i++)		//ī�� �ʱ�ȭ
		{
			Image image = new ImageIcon("CardImage\\"+(i+1)+".PNG").getImage();
			AllCard[i] = new WhatooCard(i, (i/4)+1, image);
			AllCard[i].setValue(cardValueSetting[i/4][i%4]);
		}
		
		fieldPanel = new FieldPanel(AllCard);	//�ٴ� �г�
		
		user = new User(AllCard, fieldPanel);
		com = new Computer(AllCard, fieldPanel);
		
		user.setComputerPanel(com);
		com.setUserPanel(user);
		
		dealer = new Dealer(fieldPanel, user, com);		//������ ī�� ���� �й����ش�.
		
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
		fieldPanel.checkField(user);		//���� �ִ���
		user.checkEndGame();
		com.checkEndGame();
		
		while(user.getGoGo() && com.getGoGo())
		{
			try {
				
				while(user.getSelectedCard() == -1)		//�Է� ���������� 
				{
					gameStartThread.sleep(TIME);
				}
				
				fieldPanel.addFieldCard(user.getSelectedCard());		//ī�� ����
				gameStartThread.sleep(TIME);
				
				turnUpCard = fieldPanel.turnUpTheCard();
				fieldPanel.addFieldCard(turnUpCard); 		//ī�� ������
				gameStartThread.sleep(TIME);
				
				while(AllCard[turnUpCard].getMonth() == 13)		//���ʽ� ī����
				{
					turnUpCard = fieldPanel.turnUpTheCard();
					fieldPanel.addFieldCard(turnUpCard);
					gameStartThread.sleep(TIME);
				}
				
				fieldPanel.removeCard(user, user.getSelectedCard(), turnUpCard,com);
				gameStartThread.sleep(TIME);
				
				user.checkEndGame();
				
				if(!user.getGoGo())		//��ž �ߴٸ�
					break;
				
				//��ǻ��				
				while((comSelectCard = com.getSelectedCard(fieldPanel.getfieldCard())) == -1)
				{
					gameStartThread.sleep(TIME);
				}

				fieldPanel.addFieldCard(comSelectCard);
				gameStartThread.sleep(TIME);
				
				turnUpCard = fieldPanel.turnUpTheCard();
				fieldPanel.addFieldCard(turnUpCard);
				gameStartThread.sleep(TIME);
				while(AllCard[turnUpCard].getMonth() == 13)		//���ʽ� ī����
				{
					turnUpCard = fieldPanel.turnUpTheCard();
					fieldPanel.addFieldCard(turnUpCard);
					gameStartThread.sleep(TIME);
				}
				
				fieldPanel.removeCard(com, comSelectCard, turnUpCard,user);
				gameStartThread.sleep(TIME);
				
				com.checkEndGame();
				
				user.setEnabledCard(true); 		//ī�� �����Ҽ��ֵ��� ��ư ��������
				
				if(!com.getGoGo())		//��ž �ߴٸ�
					break;
				
				if(user.getCardCount() == 0)		//������
				{
					//���
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//try-catch
		}//while
		
		primary.setPage(PageConstants.END_PAGE);
		//���� �гο� ���� ���� ���� �� ���� ���� �Ѱ��ֱ�
		
		this.stop();
	}//run()
}
