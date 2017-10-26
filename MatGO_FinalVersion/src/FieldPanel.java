
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class FieldPanel extends JPanel //�ʵ� ����
{
	public static Image coverImg;
	private WhatooCard[] AllCard;		//��ü ȭ��ī��
	public static int[][] fieldCard;		//������ ī���
	private int[] hiddenCard;		//�������ߵ� ī���
	private int nCardCount;			//�������ߵ� ī����� ���� ��
	private int coverTempCard;			//ī�� �������� ��� ������ ����
	private String eventImage;				//�̺�Ʈ �߻��� �̹��� �̸� ����
	
	private int prevCard;		//������ ��ī��
	
	private User user;
	
	FieldPanel(WhatooCard[] AllCard)
	{
		coverImg = new ImageIcon("CardImage\\54.PNG").getImage();
		
		this.AllCard = AllCard;
		fieldCard = new int[12][7];	
		for(int i=0; i<12; i++)
			for(int k=0; k<7; k++)
				fieldCard[i][k] = -1;		//���ʱ�ȭ 
		
		this.setBounds(0,200,700,300);

		eventImage = null;
		
		coverTempCard = -1;		//ī�� ���ٴ� �ǹ�
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
		
		if(cardIndex >= 48 && cardIndex <= 50)		//���ʽ� ī�� ��ȣ �϶�
		{
			for(int i=0; i<12; i++)//������ ��ī�� �����ٰ� ���ʽ� ī�带 �ø��� ���ؼ� ã��
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
		if(cardIndex == 51)		//��ź ī�� �ϋ�
		{
			return;
		}
		else
		{
			for(int i=0; i<12; i++)
				//�߰��Ϸ��� ī�尡 �ʵ忡 �����ϴ� ī��� ���� ��(index) �ϰ�� ��ģ��.
				if(fieldCard[i][0] != -1 && AllCard[fieldCard[i][0]].getMonth() == AllCard[cardIndex].getMonth())
				{
					for(int k=1; k<7; k++){
						if(fieldCard[i][k] == -1)
						{
							if(Dealer.nStart != 0)
							{
								Audio_ music = new Audio_("Voice\\e_basic.wav");
							}// �� �ں��� ���� ������ �Ҹ� �����ϱ� 
								
							fieldCard[i][k] = cardIndex;
							addCard = true;
							repaint();
							break;
						}//if
					}//for
				}//if
			prevCard = cardIndex;		//������ ��ī��� ����
		}
		//ī�带 �߰����� �ʾҴٸ�
		while(!addCard)
		{
			Random rand = new Random();
			int x = rand.nextInt(12);
			
			if(fieldCard[x][0] == -1){
				fieldCard[x][0] = cardIndex;
				repaint();
				break;
			}
			prevCard = cardIndex;		//������ ��ī��� ����
		}//while
		
	}//addFieldCard
			
	public void addHiddenCard(int[] hiddenCard, int cardCount)
	{
		this.hiddenCard = hiddenCard;
		nCardCount = cardCount;
	}
	
	public void removeCard(User user, int selectCard, int turnUpCard, Computer com)		//������
	{	
		int[] temp = new int[]{selectCard, turnUpCard};
		boolean bbuk = false;
		
		for(int j=0; j<2; j++){
			for(int i=0; i<12; i++)
			{
				boolean flag = false; 		//���� ���� ã���� true	(���ڸ� ã�Ҵµ� for���� �� �����ʿ���)
				if(fieldCard[i][0] != -1 && 
						AllCard[fieldCard[i][0]].getMonth() == AllCard[temp[j]].getMonth())		//ī�� ������ �κи� Ȯ��
				{
					int cardCount=0;
					int bonusCardCount = 0;
					int sumCardCount=0;
					
					flag = true;
					for(int k=0; k<7; k++)
					{
						if(fieldCard[i][k] == -1)
							break;
						else if(AllCard[fieldCard[i][k]].getMonth() != 13)		//���ʽ�ī�尡 �ƴϸ�
							cardCount++;
						else		//���ʽ� ī��
							bonusCardCount++;
					}

					sumCardCount = cardCount+bonusCardCount;
					
					if(cardCount == 1)
					{
						for(int k=1; k<sumCardCount; k++)		//���ʽ� ī�常 ���ֱ�
						{
							user.addScoreCard(fieldCard[i][k]);
							fieldCard[i][k] = -1;
						}
					}//if
					else if(cardCount == 2)
					{
						for(int k=0; k<sumCardCount; k++)		//��� ī�� ����
						{
							user.addScoreCard(fieldCard[i][k]);
							fieldCard[i][k] = -1;
						}
						
						if(fieldCard[i][0] == selectCard &&
								fieldCard[i][sumCardCount-1] == turnUpCard)		//��
						{
							user.addScoreCard(com.removeScoreCard()); 	//ī�� �������
							System.out.println("��");
						}
					}//else if(cardCount == 2)
					else if(cardCount == 3){
						if(fieldCard[i][1] == selectCard &&
								fieldCard[i][sumCardCount-1] == turnUpCard)		//������ 1�� ���� (��)
						{
							if(bbuk == false)
							{
								drawEventImage("bbuk.png");
								System.out.println("����.");
								bbuk = true;
							}
						}
						else		//������ 2���ִ� ���
						{
							String[] buttons = {"����", "������"};
							ImageIcon cardImageL = new ImageIcon(AllCard[fieldCard[i][1]].getImage());
							ImageIcon cardImageR = new ImageIcon(AllCard[fieldCard[i][0]].getImage());

							int choiceCard = JOptionPane.showOptionDialog(null,
									cardImageL, "������� �������?",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.PLAIN_MESSAGE,
									cardImageR, buttons, "����");
							
							user.addScoreCard(fieldCard[i][choiceCard], fieldCard[i][2]);	//�� ���� ������� �߰��� �ǹؿ� ����
							fieldCard[i][choiceCard] = -1;
							fieldCard[i][2] = -1;
							if(choiceCard == 0){
								fieldCard[i][0] = fieldCard[i][1];
								fieldCard[i][1] = -1;
							}
							
							for(int k=3; k<sumCardCount; k++)		//���ʽ� ȹ��
							{
								user.addScoreCard(fieldCard[i][k]);
								fieldCard[i][k] = -1;
							}
							repaint();		//�ϴ� �������°� �����ֱ�
						}//else
					}//else-if
					else if(cardCount == 4)
					{
						if(AllCard[fieldCard[i][sumCardCount-1]].getMonth() == turnUpCard)	//����
						{
							
						}
						for(int k=0; k<sumCardCount; k++)
						{
							user.addScoreCard(fieldCard[i][k]);
							fieldCard[i][k] = -1;		//�ʵ忡�� ����
						}
						user.addScoreCard(com.removeScoreCard());			//�� �������
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
		for(i=0; i<12; i++)		//�Ͼ��� Ȯ��
			if(fieldCard[i][0] != -1)
				break;
		if(i == 12)
		{
			System.out.println("�Ǿ���");
			user.addScoreCard(com.removeScoreCard());
		}
		
		repaint();
	}
	public void removeCard(Computer com, int selectCard, int turnUpCard, User user)		//��ǻ�Ϳ�
	{
		int[] temp = new int[]{selectCard, turnUpCard};
		boolean bbuk = false;
		
		for(int j=0; j<2; j++){
			for(int i=0; i<12; i++)
			{
				boolean flag = false; 		//���� ���� ã���� true	(���ڸ� ã�Ҵµ� for���� �� �����ʿ���)
				if(fieldCard[i][0] != -1 && 
						AllCard[fieldCard[i][0]].getMonth() == AllCard[temp[j]].getMonth())		//ī�� ������ �κи� Ȯ��
				{
					int cardCount=0;
					int bonusCardCount = 0;
					int sumCardCount=0;
					
					flag = true;
					for(int k=0; k<7; k++)
					{
						if(fieldCard[i][k] == -1)
							break;
						else if(AllCard[fieldCard[i][k]].getMonth() != 13)		//���ʽ�ī�尡 �ƴϸ�
							cardCount++;
						else		//���ʽ� ī��
							bonusCardCount++;
					}
					sumCardCount = cardCount+bonusCardCount;
					
					if(cardCount == 1)
					{
						for(int k=1; k<sumCardCount; k++)		//���ʽ� ī�常 ���ֱ�
						{
							com.addScoreCard(fieldCard[i][k]);
							fieldCard[i][k] = -1;
						}
					}//if
					else if(cardCount == 2)
					{
						for(int k=0; k<sumCardCount; k++)		//��� ī�� ����
						{
							com.addScoreCard(fieldCard[i][k]);
							fieldCard[i][k] = -1;
						}
						
						if(fieldCard[i][0] == selectCard &&
								fieldCard[i][sumCardCount-1] == turnUpCard)		//��
						{
							com.addScoreCard(user.removeScoreCard()); 	//ī�� �������
							System.out.println("��");
						}
					}//else if(cardCount == 2)
					else if(cardCount == 3){
						if(fieldCard[i][1] == selectCard &&
								fieldCard[i][sumCardCount-1] == turnUpCard)		//������ 1�� ���� (��)
						{
							if(bbuk == false)
							{
								drawEventImage("bbuk.png");
								System.out.println("����.");
								bbuk = true;
							}
						}
						else		//������ 2���ִ� ���
						{
							Random randChoice = new Random(); 			//���Ƿ� ī�� ����
							int choiceCard = randChoice.nextInt(2);
							
							com.addScoreCard(fieldCard[i][choiceCard], fieldCard[i][2]);	//�� ���� ������� �߰��� �ǹؿ� ����
							fieldCard[i][choiceCard] = -1;
							fieldCard[i][2] = -1;
							if(choiceCard == 0){
								fieldCard[i][0] = fieldCard[i][1];
								fieldCard[i][1] = -1;
							}
							
							for(int k=3; k<sumCardCount; k++)		//���ʽ� ȹ��
							{
								com.addScoreCard(fieldCard[i][k]);
								fieldCard[i][k] = -1;
							}
							repaint();		//�ϴ� �������°� �����ֱ�
						}//else
					}//else-if
					else if(cardCount == 4)
					{
						for(int k=0; k<sumCardCount; k++)
						{
							com.addScoreCard(fieldCard[i][k]);
							fieldCard[i][k] = -1;		//�ʵ忡�� ����
						}
						
						if(fieldCard[i][sumCardCount-1] == turnUpCard)	//����
						{
							
						}
						com.addScoreCard(user.removeScoreCard());			//�� �������
						
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
		for(i=0; i<12; i++)		//�Ͼ��� Ȯ��
			if(fieldCard[i][0] != -1)
				break;
		if(i == 12)
		{
			System.out.println("�Ǿ���");
			com.addScoreCard(user.removeScoreCard());
		}
		
		repaint();
	}//removeCard-computer
	
	public int turnUpTheCard()		//����ī�� ������
	{
		try {
			coverTempCard = hiddenCard[nCardCount];		//ī�� ������ ��� ����
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
	
	public void checkField(User user)		//���۽� ���ʽ� ī��, ���� ���� �˻�
	{
		for(int i=0; i<12; i++)
		{
			if(fieldCard[i][3] != -1)		//ó���� 4�� ���� �׿��ִ� ��� (����)
				user.setGoGo(false); 		//�ٷ� ��������	
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
		
		page.drawImage(new ImageIcon("CardImage\\main2.png").getImage(), 	//�ʵ� ��� ��ũ �׸���
				0, 0, 700, 300, this);
		
		Dimension panel = this.getSize();		//�г� ũ�� ��������
		//ȭ���� �߽� ã��
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
			
			{cardPos.x, cardPos.y, 0, 0},		//����
			{cardPos.x+whatooWidth/2, cardPos.y-whatooHeight/3}
		};
		
		page.drawImage(coverImg, location[12][0], location[12][1],		//��� �������ִ� ī�� ��ġ
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
