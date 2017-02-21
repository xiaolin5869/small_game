package com.example.pvzhm.Bean;

import com.example.pvzhm.Base.DefancePlant;
import com.example.pvzhm.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;

/**
 * Created by xiaolin on 2016/9/24.
 */
public class Nut  extends DefancePlant{
    public Nut() {
        super("image/plant/nut/p_3_01.png");
        baseAction();
    }

    public void baseAction() {
        CCAction animate = CommonUtils.getAnimate("image/plant/nut/p_3_%02d.png", 11, true);
        this.runAction(animate);
    }
}
