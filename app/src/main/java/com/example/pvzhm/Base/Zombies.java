package com.example.pvzhm.Base;

import org.cocos2d.types.CGPoint;

import android.view.View;
import android.widget.Button;

/**
 * ��ʬ����
 * 
 * @author Administrator
 * 
 */
public abstract class Zombies extends BaseElement {

	protected int life = 50;// �生命值
	protected int attack = 10;// 攻击力
	protected int speed = 20;// �速度

	protected CGPoint startPoint;// 开始点
	protected CGPoint endPoint;// 结速点

	public Zombies(String filepath) {
		super(filepath);
		
		setScale(0.45f);
		setAnchorPoint(0.5f, 0);// 设置锚点
	}

	/**
	 * 移动
	 */
	public abstract void move();
	/**
	 * 攻击
	 * @param element:����ֲ�������ʬ
	 */
	public abstract void attack(BaseElement element);

	/**
	 * 被攻击
	 */
	public abstract void attacked(int attack);

}
