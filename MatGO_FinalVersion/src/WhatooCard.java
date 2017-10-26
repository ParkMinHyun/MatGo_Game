
import java.awt.*;
import javax.swing.*;

public class WhatooCard		//카드 정보
{
	private int index;		//카드 고유의 번호(각각의 카드구별할 번호)
	private int month;		//패의 숫자 1~12
	private int value;		//각 패의 가치값 광=16, 십끗=8, 띠=4, 쌍피=2, 피=1, 구쌍피=2or8=10
	private Image image;	//패 이미지
	public static final Dimension cardSize = new Dimension(40,62);	//이미지 크기

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
