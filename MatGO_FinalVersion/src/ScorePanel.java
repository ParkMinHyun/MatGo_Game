
import java.awt.*;
import javax.swing.*;


public class ScorePanel extends JPanel 	//ȹ���� ī�� �г�
{
	private Point[] location;		//ī�� ���� ��ġ
	private int[] nCardCount;		//���� ī�� ���
	private JLabel[] cardCountL;	//���� ǥ��
	private JLabel cardScore; // ī�� ������ 
	private int[][] haveCard;			//���� ī��
	private int nScore;				//�� ����
	private WhatooCard[] AllCard;		//��ü ī��
	private int[] prevScore; // ���ܰ��� ������ ������ ���� 
	public static int delay; // �̹��� ǥ�� ���� 
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
	private int pScore =0; // �� ���� 
	private int number = 0; // �ӽ÷� ������ ����
	
	private FieldPanel fieldPanel;
	
	ScorePanel(Point setBoundsPos, WhatooCard[] AllCard, FieldPanel field)
	{
		location = new Point[4];
		for(int i=0; i<4; i++)			//ī�� ���� ��ġ �ʱ�ȭ
			location[i] = new Point(i*160+20,19);
		
		haveCard = new int[4][24];		//�� ��ġ���� ���� ī�� ����
		for(int i=0; i<4; i++)
			haveCard[i][0] = -1;		//�տ��� �ʱ�ȭ���ָ� ī�尡 �����ϴ��� ���ϴ��� �˼�����
		nCardCount = new int[4];		//ī�� ����
		cardCountL = new JLabel[4];		//���� ī�� ǥ��
		prevScore = new int[4]; // �ӽú��� �ʱ�ȭ
		
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
		
		
		//������ ȭ�鿡 ǥ�� 
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
		
		calScore();			//���� ���
		
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
			
	public void calScore()		//�������
	{
		if(nCardCount[0] >=3 && nCardCount[0] != prevScore[0] ) // ���� 3�� �̻��϶� 
		{
			prevScore[0] = nCardCount[0];
			
			if(nCardCount[0] == 3)
			{	
				nScore = nScore + 3;		
				cardScore.setText(""+nScore);
				Audio_ music = new Audio_("Voice\\m_gwang3.wav");
				
				fieldPanel.drawEventImage("3gwang.png");		//�ﱤ �׸���
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
				godoriCount = 0;		//������ 3���� �ƴϹǷ� �ٽ� �ʱ�ȭ
		}//if
		
		if(nCardCount[1] >=5 && nCardCount[1] != prevScore[1]) // �ʲ��� 5�� �̻��϶� 
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
				if(haveCard[2][i] == 13 || haveCard[2][i] == 17 || haveCard[2][i] == 25 ) // �ʴ� �϶� 
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
				if(haveCard[2][i] == 1 || haveCard[2][i] == 5 || haveCard[2][i] == 9) // ȫ���϶�  
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
				if(haveCard[2][i] == 21 || haveCard[2][i] == 33 || haveCard[2][i] == 37) // û���϶� 
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
		
		//----------------------------------------------------------------------�� ���
		
		if(nCardCount[2] >= 5 && nCardCount[2] != prevScore[2]) // �찡 5�� �̻��϶�
		{
			prevScore[2] = nCardCount[2];
			
			nScore = nScore + 1;
	         cardScore.setText(""+nScore);
		}	//for
		
		//----------------------------------------------------------------------�� ���
		if(nCardCount[3] != prevScore[3])		//���� �߰��� ī�尡 �ִٸ�
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
	
	public int removeCard()		//ī�� ����
	{
		int removeCard=0;
		boolean flag = false;
		
		if(nCardCount[3] > 0)
		{
			for(int value=1; value<=3; value++)
			{
				for(int i=nCardCount[3]-1; i>=0; i--)
				{
					if(AllCard[haveCard[3][i]].getValue() == value)		//1������ ���ʴ�� Ȯ��
					{
						removeCard = haveCard[3][i];
						
						for(int j=i; j<nCardCount[3]-1; j++)		//ī�� �߰����� ���������Ƿ�
							haveCard[3][j] = haveCard[3][j+1];		//�� ����
						haveCard[3][--nCardCount[3]] = -1;			//���������� �ǵڿ� ������ ����
						flag = true;		//���� �����Ƿ�
						break;
					}//if
				}//for
				if(flag)
					break;
			}//for
		}//if
		else		//ī�尡 ���嵵 ������
		{
			return -1;
		}
		
		cardCountL[3].setText(""+nCardCount[3]);		//ī�廯��� ī�� ���� �ٽ� ���̱�
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
			for(int k=0; k<nCardCount[i]; k++)		//�� �ڸ����� ���� �����ŭ �׸���
			{
				page.drawImage(AllCard[haveCard[i][k]].getImage(),
						location[i].x+13*(k%10), location[i].y+15*(k/10),
						whatooWidth, whatooHeight,this);
			}//for
		}//for
	}
	
}
