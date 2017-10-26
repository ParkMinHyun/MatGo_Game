
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class User extends JPanel
{
	private ScorePanel userScorePanel;		//���� ȹ�� �� �г�
	private WhatooCard[] AllCard;			//��ü ī��
	private int[] card;			//������ �ִ� ���� �ε���
	private int nCardCount;			//������ �ִ� �� ����
	private JButton[] cardButton;		//ī��
	private int selectedCard;				//��ī��
	
	private ClickListener clickL;
	private int goCount;			//��� �ߴ���
	private int go_score;      // go�� �� �ƹ��͵� �� �Ծ��� �� Ȯ�����ִ� ����
	private int swingCount;			//���� ��� �ߴ���
	private Audio_ music;
	private boolean gogo;			//���� ��� ���� ������ üũ
	int[] sameCardCount;		//���� �˻�� �迭
	
	private Computer com;
	private FieldPanel fieldPanel;
	
	User(WhatooCard[] AllCard, FieldPanel field)
	{
		card = new int[10];		//ī�� ���� �ʱ�ȭ
		this.AllCard = AllCard;
		nCardCount = 0;
		selectedCard = -1;
		
		fieldPanel = field;

		gogo = true;		//stop�ߴ��� ���ߴ��� üũ
		sameCardCount = new int[13];
		
		this.setBounds(0,500,700,200);
		this.setBackground(new Color(74,126,38));		//���� ���
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
			
			cardButton[i].addMouseListener(clickL); 		//���콺 ������ ����
		}
		
		userScorePanel = new ScorePanel(new Point(0,0),AllCard,field);
		this.add(userScorePanel);
	}
	
	public void addCard(int cardIndex)
	{
		this.card[nCardCount++] = cardIndex; 		//�� �߰�
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
		userScorePanel.addCard(card1, card2);		//���� ī�� �߰�
	}
	
	public void addScoreCard(int card1, int card2, int card3, int card4){
		userScorePanel.addCard(card1, card2, card3, card4);		//���� ī�� �߰�
	}
	
	public int removeScoreCard()		//ī������
	{
		return userScorePanel.removeCard();
	}
	
	public void selectCard(int card, int index)		//ī�� ����
	{	
		if(cardButton[index].isEnabled())			//ī�� ���ε������� ���� ����
		{	
			if(card <= 47 && sameCardCount[card/4] == 3)		//��ź
			{
				int[] fieldPanelCard = fieldPanel.getfieldCard();
				int boomIndex;	//��ź �� ī�� ��ġ
				int i;
				
				swingCount++;		//���� ����
				
				for(boomIndex=0; boomIndex<nCardCount; boomIndex++)		//��ź ���� ��ġ �˾Ƴ�
					if(AllCard[card].getMonth() == AllCard[this.card[boomIndex]].getMonth())
						break;
				
				nCardCount--;
				
				for(i=0; i<12; i++)
				{
					if(fieldPanelCard[i] != -1 && 
							AllCard[fieldPanelCard[i]].getMonth() == AllCard[card].getMonth())//�ʵ忡 ī�� ��������
					{
						fieldPanel.addFieldCard(this.card[boomIndex]);
						sameCardCount[this.card[boomIndex]/4]--;
						fieldPanel.addFieldCard(this.card[boomIndex+1]);
						sameCardCount[this.card[boomIndex+1]/4]--;
						selectedCard = this.card[boomIndex+2];
						sameCardCount[this.card[boomIndex+2]/4]--;
						
						for(int k=boomIndex; k<nCardCount; k++)
							this.card[k] = this.card[k+1];
						
						this.card[boomIndex] = 51;	//��ź
						this.card[boomIndex+1] = 51;	//��ź
						
						cardButton[nCardCount].setEnabled(false);
						cardButton[nCardCount].setVisible(false);
						
						repaint();
						
						break;
					}//if
				}//for
				
				if(i == 12)		//�ʵ忡 ���� ���ϸ� ���⸸ �ϰ� ������ ī�带 ���� �Ѵ�.
				{
					for(i=index; i<nCardCount; i++)
						this.card[i] = this.card[i+1];
					
					cardButton[nCardCount].setEnabled(false);
					cardButton[nCardCount].setVisible(false);
					selectedCard = card;
					
					System.out.println("����");
					
					sameCardCount[card/4]--;
				}
				
				sort(nCardCount);		//ī�� ����(��ź�� �ǵڷ� ������ ���ؼ�)
			}//if
			else{
				nCardCount--;
				for(int i=index; i<nCardCount; i++)
					this.card[i] = this.card[i+1];
				
				if(card >= 48 && card <= 50)		//���ʽ� ��
				{
					addScoreCard(card);			//���ʽ��� �߰�
					addScoreCard(com.removeScoreCard());	//��� �� ����
					addCard(fieldPanel.getHiddenCard());	//���� �п� ī�� �߰�
					selectedCard = -1;
				}
				else
				{
					cardButton[nCardCount].setEnabled(false);
					cardButton[nCardCount].setVisible(false);
					selectedCard = card;
					
					if(card/4 <12)		//��ź�ΰ�� ����
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
	
	public int getAllScore()			//�� ���� ��������
	{
		return userScorePanel.getScore();
	}
	
	public void checkEndGame()
	{
		selectedCard = -1;
		
		for(int i=0; i<12; i++)
			if(sameCardCount[i] == 4)		//����
				gogo = false;
		
		if(userScorePanel.getScore()>=7 && userScorePanel.getScore()>go_score) // go & stop ( 7�� �Ѿ��� �� 
																				  //			,go�� ������ ������ ���� ��
		{	
			if(nCardCount == 0)		//�а� �����Ƿ� ������ ����
			{	
				for(int i=0; i<12; i++)
					for(int j=0; j<4; j++)
						FieldPanel.fieldCard[i][j] = -1; // �ʵ忡 �ִ� ī�� ���ֱ�
					
				for(int i=0; i<nCardCount; i++)
				{
					cardButton[i].setEnabled(false);// ��ư ��Ȱ��ȭ
					cardButton[i].setVisible(false);
				}
				
				Audio_ music = new Audio_("Voice\\m_stop.mp3");
				fieldPanel.drawEventImage("stop_1.png");
				
				gogo = false;
			}
			else		//�а� ����
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
				else		//��ž�϶�
				{
					Audio_ music = new Audio_("Voice\\m_stop.mp3");
					
					
					for(int i=0; i<12; i++)
						for(int j=0; j<4; j++)
							FieldPanel.fieldCard[i][j] = -1; // �ʵ忡 �ִ� ī�� ���ֱ�
						
					for(int i=0; i<nCardCount; i++)
					{	
						cardButton[i].setEnabled(false);// ��ư ��Ȱ��ȭ
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
				if(button.equals(cardButton[i]))		//������ư���� ī�� ã��
				{		
					if(AllCard[card[i]].getMonth() != 13)
					{
						selectCard(card[i],i);			//ī�� �� ���° ī������ ���� ����
						setEnabledCard(false);			//ī�� ����
					}
					else
						selectCard(card[i],i);			//ī�� �� ���° ī������ ���� ����
					break;
				}
			}
		}
		
		public void mouseEntered(MouseEvent event)		//�׸� �ø���
		{
			Object button = event.getSource();
			
			for(int i=0; i<nCardCount; i++)
				if(button.equals(cardButton[i]))
				{
					cardButton[i].setLocation( // component ��ġ ����
							cardButton[i].getX(), cardButton[i].getY() - 20);
				}
			repaint();
		}
		
		public void mouseExited(MouseEvent event)		//�׸� ������
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