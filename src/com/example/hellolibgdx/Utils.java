package com.example.hellolibgdx;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Utils {

	public static boolean overlapDetector(Image image1,Image image2){
		
//		System.out.println("----------->image1.getX(): " + image1.getX());
//		System.out.println("----------->image1.getY(): " + image1.getY());
//		System.out.println("----------->image2.getX(): " + image2.getX());
//		System.out.println("----------->image2.getY(): " + image2.getY());
		//-20����Ϊ�����ʱ���˸о�����ײ��,��ʵ���ϲ�û����ײ�ĸо�
		if((image1.getX()>=image2.getX() - 20) && (image1.getX() <= image2.getX() + image2.getWidth()) && (image1.getY()>=image2.getY()) && (image1.getY() <= image2.getY() + image2.getHeight())){
			return true;
		}
		
		return false;
	}
}
