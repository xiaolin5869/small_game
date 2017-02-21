package com.example.pvzhm.Base;
/**
 * �ӵ�
 */
public abstract class Bullet extends Product {
	protected int attack = 20;// 攻击力
	protected int speed =100;// 生命值

	public Bullet(String filepath) {
		super(filepath);
	}

	@Override
	public void baseAction() {

	}
	/**
	 * �ƶ�
	 */
	public abstract void move();

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	
}
