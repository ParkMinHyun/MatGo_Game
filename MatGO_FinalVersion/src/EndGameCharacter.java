
import java.awt.*;
import javax.swing.*;

public class EndGameCharacter extends JPanel 
{
	private JLabel money;
	private int result;
	private int pflag;		//�� �г��� �����гη� ��������� üũ(0-��ǻ��, 1-����)	
	private EndGamePanel panel;
	private User user;
	private Computer com;
	
	EndGameCharacter(int result, Computer com, EndGamePanel panel)
	{
		this.setLayout(null);
		this.setBounds(10, 10, 680, 200);
		this.result = result;
		pflag = 0;
		
		this.panel = panel;
		this.com = com;
		
		money = new JLabel();
		
		money.setBounds(180, 70, 400, 50);		//�� ǥ��
		money.setFont(new Font("Verdana", Font.BOLD, 30));
		money.setForeground(Color.yellow);
		
		if(result == PageConstants.WIN)
			money.setBackground(new Color(0,162,162));
		else if(result == PageConstants.LOSE)
			money.setBackground(new Color(181,0,0));
		else	//�������
			money.setBackground(Color.green);
		this.add(money);
	}
	
	EndGameCharacter(int result, User user, EndGamePanel panel)
	{
		this.setLayout(null);
		this.setBounds(10, 490, 680, 200);
		this.result = result;
		pflag = 1;		
		
		this.panel = panel;
		this.user = user;
		
		money = new JLabel();
		
		money.setBounds(180, 70, 400, 50);		//�� ǥ��
		money.setFont(new Font("Verdana", Font.BOLD, 30));
		money.setForeground(Color.yellow);
		money.setVerticalAlignment(SwingConstants.CENTER);
		money.setHorizontalAlignment(SwingConstants.CENTER);
		
		if(result == PageConstants.WIN)
			money.setBackground(new Color(0,162,162));
		else if(result == PageConstants.LOSE)
			money.setBackground(new Color(181,0,0));
		else	//�������
			money.setBackground(Color.green);
		this.add(money);
	}
	
	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
		
		if(pflag == 0)		//��ǻ�Ϳ� �г�
		{
			if(result == PageConstants.WIN)
			{
				page.setColor(new Color(0,191,191));	//�̱�� cyan����ѻ�
				page.fillRect(0, 0, 680, 200);
				page.setColor(new Color(153,255,255));
				page.fillRect(5, 5, 670, 40);
				
				page.drawImage(new ImageIcon("BackgroundImage\\computer2.jpg").getImage(),
						10, 80, 84, 84, this);	//computer��
				page.drawImage(new ImageIcon("CardImage\\mark_win.png").getImage()	//�̱丶ũ
						, 540, 10, 132, 132, this);
				
				page.setColor(new Color(0,162,162));		//�� Ȯ��
				page.fillRect(190, 155, 300, 30);
				
				money.setText(panel.getFinalScore()+" X "+ panel.getScoreRate() +" X 1000Won");
				repaint();
			}
			else if(result == PageConstants.LOSE)
			{
				page.setColor(new Color(255,45,45));	//���� ��ȫ��
				page.fillRect(0, 0, 680, 200);
				page.setColor(new Color(255,130,130));
				page.fillRect(5, 5, 670, 40);
				
				page.drawImage(new ImageIcon("BackgroundImage\\computer1.jpg").getImage(),
						10, 80, 84, 84, this);	//computer��
				page.drawImage(new ImageIcon("CardImage\\mark_lose.png").getImage()	//����ũ
						, 540, 10, 132, 132, this);
				
				page.setColor(new Color(181,0,0));		//�� Ȯ��
				page.fillRect(190, 155, 300, 30);
			}
			else		//�������
			{
				page.setColor(new Color(128,255,0));
				page.fillRect(0, 0, 700, 200);
				page.setColor(new Color(183,255,111));
				page.fillRect(5, 5, 690, 40);
				
				page.drawImage(new ImageIcon("BackgroundImage\\computer1.jpg").getImage(),
						20, 0, 84, 84, this);	//computer��
				page.setColor(new Color(181,0,0));		//�� Ȯ��
				page.fillRect(190, 155, 300, 30);
			}
		}//if
		else if(pflag == 1)		//user�� �г�
		{
			if(result == PageConstants.WIN)
			{
				page.setColor(new Color(0,191,191));	//�̱�� cyan����ѻ�
				page.fillRect(0, 0, 680, 200);
				page.setColor(new Color(153,255,255));
				page.fillRect(5, 5, 670, 40);
			
				page.drawImage(new ImageIcon("BackgroundImage\\user2.jpg").getImage(),
						570, 80, 84, 84, this);	//user��
				page.drawImage(new ImageIcon("CardImage\\mark_win.png").getImage(),	//�̱丶ũ
						10, 10, 132, 132, this);
				
				page.setColor(new Color(0,162,162));		//�� Ȯ��
				page.fillRect(190, 155, 300, 30);
				
				page.setColor(Color.yellow);
				
				
				money.setText(panel.getFinalScore()+" X "+ panel.getScoreRate() +" X 1000Won");
				repaint();
				
			}
			else if(result == PageConstants.LOSE)
			{
				page.setColor(new Color(255,45,45));	//���� ��ȫ��
				page.fillRect(0, 0, 680, 200);
				page.setColor(new Color(255,130,130));
				page.fillRect(5, 5, 670, 40);
				
				page.drawImage(new ImageIcon("BackgroundImage\\user1.jpg").getImage(),
						570, 80, 84, 84, this);	//user��
				page.drawImage(new ImageIcon("CardImage\\mark_lose.png").getImage(),	//����ũ
						10, 10, 132, 132, this);
				
				page.setColor(new Color(181,0,0));		//�� Ȯ��
				page.fillRect(190, 155, 300, 30);
			}
			else
			{
				page.setColor(new Color(128,255,0));
				page.fillRect(0, 0, 680, 200);
				page.setColor(new Color(183,255,111));
				page.fillRect(5, 5, 670, 40);
				
				page.drawImage(new ImageIcon("BackgroundImage\\user1.jpg").getImage(),
						570, 20, 84, 84, this);	//user��
				page.setColor(new Color(181,0,0));		//�� Ȯ��
				page.fillRect(190, 155, 300, 30);
			}
			
		}//else
		//�̹��� �ְ� �׸���
	}//paintComponent
	
	
}
