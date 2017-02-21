package com.example.pvzhm.Base;
/**
 * 防御性植物
 * @author Administrator
 *
 */
public abstract class DefancePlant extends Plant {

	public DefancePlant(String filepath) {
		super(filepath);
		life = 200;
	}

}
