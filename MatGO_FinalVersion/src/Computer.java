
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Computer extends JPanel
{
	private ScorePanel comScorePanel;		//��ǻ�� ȹ�� �� �г�
	private WhatooCard[] AllCard;			//��ü ī��
	private int[] card;			//������ �ִ� ���� �ε���
	private int nCardCount;			//������ �ִ� �� ����
	private JButton[] cardButton;		//ī��
	private int selectedCard;				//��ī��
	private int go_score;
	private int goCount;
	private int swingCount;			//����
	private int[] sameCardCount;
	
	private boolean gogo;
	private Audio_ music;
	
	private FieldPanel fieldPanel;
	private User user;
	
	Computer(WhatooCard[] AllCard, FieldPanel field)
	{
		card = new int[10];		//ī�� ���� �ʱ�ȭ
		this.AllCard = AllCard;
		nCardCount = 0;
		selectedCard = -1;
		goCount = 0;
		
		this.setBounds(0,0,700,200);
		this.setBackground(new Color(74,126,38));		//���� ���
		this.setLayout(null);
		
		fieldPanel = field;
		sameCardCount = new int[13];
		
		cardButton = new JButton[10];
		gogo = true;					//��ž �ߴ��� ���ߴ��� üũ
		
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
	
	public void setUserPanel(User user)
	{
		this.user = user;
	}
	
	public void addScoreCard(int card1){
		comScorePanel.addCard(card1);		//���� ī�� �߰�
	}
	
	public void addScoreCard(int card1, int card2){
		comScorePanel.addCard(card1, card2);		//���� ī�� �߰�
	}
	public void addScoreCard(int card1, int card2, int card3, int card4){
		comScorePanel.addCard(card1, card2, card3, card4);		//���� ī�� �߰�
	}
	
	public int selectCard(int card, int index)		//ī�� ����
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
				addScoreCard(user.removeScoreCard());	//��� �� ����
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
		if(AllCard[card[nCardCount-1]].getMonth() == 13)	//���ʽ� ī�带 ��������� �켱������ ����.
			return selectCard(card[nCardCount-1], nCardCount-1);
		
		for(int i=0; i<nCardCount; i++)
			for(int k=0; k<12; k++)
			{	//�ʵ�ī�尡 ���� �ϰ� �տ� ����ִ� ī��� ���� ī�� �ϰ��
				if(fieldCard[k] != -1 && 
						AllCard[card[i]].getMonth() == AllCard[fieldCard[k]].getMonth())
				{
					return selectCard(card[i], i);					
				}
			}
		
		//�ʵ�� �տ� ��� �ִ�ī�尡 ���� ���� ���
		Random rand = new Random();
		int randIndex = rand.nextInt(nCardCount);
		
		return selectCard(card[randIndex], randIndex);
	}
	
	public int[] getScoreCardCount()
	{
		return comScorePanel.getCardCount();
	}
	
	public int getAllScore()			//�� ���� ��������
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
			if(sameCardCount[i] == 4)		//����
				gogo = false;
		
		if(comScorePanel.getScore()>=7 && comScorePanel.getScore()>go_score)
		{	
			if(nCardCount == 0)
			{
				gogo = false;
				
				Audio_ music = new Audio_("Voice\\m_stop.mp3");
				fieldPanel.drawEventImage("stop_1.png");
			}
			else if(user.getAllScore() > 4)		//��ž
			{
				gogo = false;
				
				for(int i=0; i<12; i++)
					for(int j=0; j<4; j++)
						FieldPanel.fieldCard[i][j] = -1;
				
				Audio_ music = new Audio_("Voice\\m_stop.mp3");
				fieldPanel.drawEventImage("stop_1.png");
			}
			else					//������  4�� ���Ͽ��� �����!!
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
			/*			//�̺κ��� ���� �и� �����ش�.
			ImageIcon tempimg = new ImageIcon(AllCard[card[i]].getImage());
			*/
			ImageIcon tempimg = new ImageIcon("CardImage\\53.png");
			cardButton[i].setIcon(tempimg);
		}//for
	}
}





