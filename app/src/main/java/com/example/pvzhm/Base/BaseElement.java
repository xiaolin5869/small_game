package com.example.pvzhm.Base;

import org.cocos2d.nodes.CCSprite;

import android.view.View;
import android.widget.Button;


/**
 * 对战元素共性
 * 
 * @author Administrator
 * 
 */
public abstract class BaseElement extends CCSprite {
	public interface DieListener{  // 死亡的借口
		void die();
	}
	private DieListener dieListener;  // 死亡的监听
	public void setDieListener(DieListener dieListener) {  // ��©��һ������ 
		this.dieListener = dieListener;
	}


	public BaseElement(String filepath) {
		super(filepath);
	}

	/**
	 *
	 * 原地不动的方法
	 */
	public abstract void baseAction();

	/**
	 * 销毁
	 */
	public void destroy() {
		if(dieListener!=null){
			dieListener.die();     //��ֲ��������ʱ�� ���ýӿڵķ���
		}
		this.removeSelf();
	}
}
