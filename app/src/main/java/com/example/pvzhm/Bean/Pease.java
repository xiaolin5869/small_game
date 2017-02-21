package com.example.pvzhm.Bean;

import android.nfc.tech.NfcBarcode;

import com.example.pvzhm.Base.Bullet;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

/**
 * Created by xiaolin on 2016/9/24.
 */
public class Pease extends Bullet {
    public Pease() {
        super("image/fight/bullet.png");
        this.setScale(0.65f);
    }

    @Override
    public void move() {
        CGPoint point = getPosition();
        CGPoint targetPosition= CCNode.ccp(CCDirector.sharedDirector().winSize().width,point.y);
        float t = CGPointUtil.distance(point, targetPosition)/speed;
        CCMoveTo moveTo = CCMoveTo.action(t, targetPosition);

        CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this,"destroy"));//回收
        this.runAction(sequence);
    }
}
