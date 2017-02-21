package com.example.pvzhm.Layer;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.MotionEvent;

import com.example.pvzhm.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;

/**
 * Created by xiaolin on 2016/9/20.
 */
public class WelcomeLayer extends CCLayer {

    private CGSize winSize;
    private CCSprite start;

    public WelcomeLayer() {
        new AsyncTask<Void,Void,Void>()
        {
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(6000);
                return null;
            }

            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                start.setVisible(true);
                setIsTouchEnabled(true);//打开触摸事件开关
            }
        }.execute();
        init();
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        CGPoint point = this.convertTouchToNodeSpace(event);//转换成cocos2d坐标
        CGRect boundingBox = start.getBoundingBox();
        if(CGRect.containsPoint(boundingBox,point))
        {
//            System.out.println("按下啦");
            CommonUtils.changeLayer(new MenuLayer());//切换图层
        }
        return super.ccTouchesBegan(event);
    }

    private void init() {
        logo();
    }

    /**
     * 展示logo
     */
    private void logo() {
        CCSprite sprite = CCSprite.sprite("image/popcap_logo.png");
        winSize = CCDirector.sharedDirector().getWinSize();
        sprite.setPosition(winSize.width/2, winSize.height/2);
        this.addChild(sprite);

        CCHide hide = CCHide.action();//隐藏精灵
        CCDelayTime delayTime = CCDelayTime.action(1);//延时1s
        CCSequence sequence = CCSequence.actions(delayTime, delayTime, hide, delayTime,CCCallFunc.action(this,"loadWelcome"));//logo显示2s后跳转
        sprite.runAction(sequence);
    }

    /**
     * 加载欢迎界面
     */
    public void loadWelcome()
    {
        CCSprite sprite = CCSprite.sprite("image/welcome.jpg");
        sprite.setAnchorPoint(0,0);
        this.addChild(sprite);
        loading();//加载动画
    }

    /**
     * 动画展示
     */
    private void loading() {

        CCSprite sprite = CCSprite.sprite("image/loading/loading_01.png");
        sprite.setPosition(winSize.width/2,30);
        this.addChild(sprite);

//        ArrayList<CCSpriteFrame> frames=new ArrayList<>();
//        String format="image/loading/loading_%02d.png";
//        for(int i=1;i<10;i++)
//        {
//            CCSpriteFrame frame = CCSprite.sprite(String.format(format, i)).displayedFrame();
//            frames.add(frame);
//        }
//        CCAnimation animation = CCAnimation.animation("", 0.4f, frames);
//        CCAnimate animate = CCAnimate.action(animation,false);//false 表示不需要循环播放
        CCAction animate = CommonUtils.getAnimate("image/loading/loading_%02d.png", 9, false);
        sprite.runAction(animate);

        start = CCSprite.sprite("image/loading/loading_start.png");
        start.setPosition(winSize.width/2,30);
        start.setVisible(false);//设置不可见
        this.addChild(start);
    }
}
