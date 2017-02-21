package com.example.pvzhm.Base;
/**
 * 植物的共性
 * @author Administrator
 *
 */
public abstract class Plant extends BaseElement {

	protected int life=100;// 生命值

	protected int line;// 行号
	protected int row;//  列号

	public Plant(String filepath) {
		super(filepath);
		setScale(0.65);
		setAnchorPoint(0.5f, 0);// 设置锚点在两腿中间
	}

	/**
	 * ������
	 * 
	 * @param attack 被攻击
	 */
	public void attacked(int attack) {
		life -= attack;
		if (life <= 0) {
			destroy();
		}
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getLife() {
		return life;
	}



}
