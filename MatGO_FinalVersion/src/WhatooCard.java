
import java.awt.*;
import javax.swing.*;

public class WhatooCard		//ī�� ����
{
	private int index;		//ī�� ������ ��ȣ(������ ī�屸���� ��ȣ)
	private int month;		//���� ���� 1~12
	private int value;		//�� ���� ��ġ�� ��=16, �ʲ�=8, ��=4, ����=2, ��=1, ������=2or8=10
	private Image image;	//�� �̹���
	public static final Dimension cardSize = new Dimension(40,62);	//�̹��� ũ��

	WhatooCard(int index, int month, Image image, int value)
	{
		setAll(index,month,image,value);
	}
	
	WhatooCard(int index, int month, Image image)
	{
		setAll(index,month,image,0);
	}
	
	public void setAll(int index, int number, Image image, int value)
	{
		this.index = index;
		this.month = number;
		this.image = image;
		this.value = value;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	public int getMonth()
	{
		return month;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public int getIndex()
	{
		return index;
	}
}
