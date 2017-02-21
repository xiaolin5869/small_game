package com.example.pvzhm.Bean;

import com.example.pvzhm.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCSprite;

/**
 * Created by xiaolin on 2016/9/22.
 */
public class ShowZombies extends CCSprite {
    public ShowZombies() {
        super("image/zombies/zombies_1/shake/z_1_01.png");
        setScale(0.5f);
        setAnchorPoint(0.65f, 0);// 让僵尸的锚点在两腿之间
        CCAction animate = CommonUtils.getAnimate("image/zombies/zombies_1/shake/z_1_%02d.png", 2, true);// 来回抖动
        this.runAction(animate);
    }

}
