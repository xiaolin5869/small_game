package com.example.pvzhm.Base;
/**
 * 生态型植物 像向日葵等
 * @author Administrator
 *
 */
public abstract class ProductPlant extends Plant {

	public ProductPlant(String filepath) {
		super(filepath);
	}

	/**
	 * ���⡢���
	 */
	public abstract void create();
	

}
