package com.example.pvzhm.Base;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 攻击性植物
 * 
 * @author Administrator
 * 
 */
public abstract class AttackPlant extends Plant {
	// ����
	protected List<Bullet> bullets = new CopyOnWriteArrayList<Bullet>();

	public AttackPlant(String filepath) {
		super(filepath);
	}

	/*
	 * 生产攻击用的子弹
	 * @return
	 */
	public abstract Bullet createBullet();
	/**
	 * 弹夹 管理子弹
	 * @return
	 */
	public List<Bullet> getBullets() {
		return bullets;
	}

//	@Override
//	public void onDie(BaseElement element) {
//
//		if (element instanceof Bullet) {
//			bullets.remove(element);
//		}
//	}

}
