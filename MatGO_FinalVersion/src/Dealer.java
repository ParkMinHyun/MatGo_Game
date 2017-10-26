
import java.util.*;		//랜덤 클래스 사용
import javax.swing.*;

public class Dealer		//패 분배 클래스 
{
	private int[] card;  		//카드를 가리키는 인덱스
	private int nCardCount;			//카드 개수
	public static int nStart = 0; 	//카드맞힐 때마다짝짝 거리는  음향 구분해주는  변수
	
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
				int temp, randCard;		//임시저장소, 랜덤으로 뽑아 섞을 카드
				Random rand = new Random();
				randCard = rand.nextInt(51-i)+i;	//앞에 섞어놓은것은 건들지 않는다.
				
				temp = cardIndex[i];
				cardIndex[i] = cardIndex[randCard];
				cardIndex[randCard] = temp;
			}
	}
	
	void distributeCard(FieldPanel fieldPanel, User user, Computer com)
	{
		for(int i=0; i<4; i++)
		{
			if(card[--nCardCount]/4+1 == 13)	//보너스 패인경우
			{
				user.addScoreCard(card[nCardCount]);
				i--;
			}
			else
				fieldPanel.addFieldCard(card[nCardCount]);	//카드 뽑아서 놓기
		}
		
		for(int i=0; i<5; i++)
			com.addCard(card[--nCardCount]);		//카드 분배
		
		for(int i=0; i<5; i++)
			user.addCard(card[--nCardCount]);
		
		for(int i=0; i<4; i++)
		{
			if(card[--nCardCount]/4+1 == 13)	//보너스 패인경우
			{
				user.addScoreCard(card[nCardCount]);
				i--;
			}
			else
				fieldPanel.addFieldCard(card[nCardCount]);	//카드 뽑아서 놓기
		}
		for(int i=0; i<5; i++)
			com.addCard(card[--nCardCount]);		//카드 분배
		
		for(int i=0; i<5; i++)
			user.addCard(card[--nCardCount]);
		
		fieldPanel.addHiddenCard(card, --nCardCount);	//남은 패 및 남은 패 개수
		nStart = 1;
	}

}
