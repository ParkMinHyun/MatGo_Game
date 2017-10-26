
import java.util.*;		//���� Ŭ���� ���
import javax.swing.*;

public class Dealer		//�� �й� Ŭ���� 
{
	private int[] card;  		//ī�带 ����Ű�� �ε���
	private int nCardCount;			//ī�� ����
	public static int nStart = 0; 	//ī����� ������¦¦ �Ÿ���  ���� �������ִ�  ����
	
	Dealer(FieldPanel fieldPanel, User user, Computer com)
	{
		this.card = new int[51];
		for(int i=0; i<51; i++)
			this.card[i] = i;
		nCardCount = 51;
		shuffleCard(card);
		distributeCard(fieldPanel, user, com);
	}
	
	void shuffleCard(int[] cardIndex)
	{	
		for(int k=0; k<3; k++)
			for(int i=0; i<51; i++)
			{
				int temp, randCard;		//�ӽ������, �������� �̾� ���� ī��
				Random rand = new Random();
				randCard = rand.nextInt(51-i)+i;	//�տ� ����������� �ǵ��� �ʴ´�.
				
				temp = cardIndex[i];
				cardIndex[i] = cardIndex[randCard];
				cardIndex[randCard] = temp;
			}
	}
	
	void distributeCard(FieldPanel fieldPanel, User user, Computer com)
	{
		for(int i=0; i<4; i++)
		{
			if(card[--nCardCount]/4+1 == 13)	//���ʽ� ���ΰ��
			{
				user.addScoreCard(card[nCardCount]);
				i--;
			}
			else
				fieldPanel.addFieldCard(card[nCardCount]);	//ī�� �̾Ƽ� ����
		}
		
		for(int i=0; i<5; i++)
			com.addCard(card[--nCardCount]);		//ī�� �й�
		
		for(int i=0; i<5; i++)
			user.addCard(card[--nCardCount]);
		
		for(int i=0; i<4; i++)
		{
			if(card[--nCardCount]/4+1 == 13)	//���ʽ� ���ΰ��
			{
				user.addScoreCard(card[nCardCount]);
				i--;
			}
			else
				fieldPanel.addFieldCard(card[nCardCount]);	//ī�� �̾Ƽ� ����
		}
		for(int i=0; i<5; i++)
			com.addCard(card[--nCardCount]);		//ī�� �й�
		
		for(int i=0; i<5; i++)
			user.addCard(card[--nCardCount]);
		
		fieldPanel.addHiddenCard(card, --nCardCount);	//���� �� �� ���� �� ����
		nStart = 1;
	}

}
