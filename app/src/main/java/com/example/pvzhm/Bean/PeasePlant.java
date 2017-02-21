package com.example.pvzhm.Bean;

import com.example.pvzhm.Base.AttackPlant;
import com.example.pvzhm.Base.Bullet;
import com.example.pvzhm.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCNode;

/**
 * Created by xiaolin on 2016/9/24.
 */
public class PeasePlant extends AttackPlant {
    public PeasePlant() {
        super("image/plant/pease/p_2_01.png");
        baseAction();
    }

    @Override
    public Bullet createBullet() {
        if(bullets.size()<1)
        {
            final Pease pease=new Pease();//创建子弹
            pease.setPosition(CCNode.ccp(this.getPosition().x+20,this.getPosition().y+40));
            this.getParent().addChild(pease);//图层上添加子弹

            //子弹消失的监听
            pease.setDieListener(new DieListener() {
            @Override
            public void die() {
                 bullets.remove(pease);
            }
        });
            bullets.add(pease);
            pease.move();//子弹移动
        }
        return null;
    }

    @Override
    public void baseAction() {
        CCAction animate = CommonUtils.getAnimate("image/plant/pease/p_2_%02d.png", 7, true);
        this.runAction(animate);
    }
}
