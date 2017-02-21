package com.example.pvzhm.utils;

import android.graphics.Paint;

import com.example.pvzhm.Layer.MenuLayer;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCFlipXTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xiaolin on 2016/9/20.
 */
public class CommonUtils {

    public static void changeLayer(CCLayer newlayer) {
        CCScene scene = CCScene.node();
        scene.addChild(newlayer);
        CCFlipXTransition transition = CCFlipXTransition.transition(2, scene, 0);
        CCDirector.sharedDirector().replaceScene(transition);//切换场景
    }

    public static CGSize getsize() {
     return CCDirector.sharedDirector().getWinSize();
    }

    /**
     * 解析地图，返回其中的坐标，以list的形式返回
     * @param map
     * @param name
     * @return
     */
    public static List<CGPoint>  parseMap(CCTMXTiledMap map,String name) {
        List<CGPoint> points = new ArrayList<CGPoint>();
        CCTMXObjectGroup group = map.objectGroupNamed(name);
        ArrayList<HashMap<String, String>> objects = group.objects;
        for(HashMap<String, String> hashmap:objects)
        {
              int x=Integer.parseInt(hashmap.get("x"));
              int y=Integer.parseInt(hashmap.get("y"));
              CGPoint point = CCNode.ccp(x, y);
              points.add(point);
        }
        return points;

    }

    /**
     * 创建了序列帧的动作
     * @param format  格式化的路径
     * @param num   帧数
     * @param isForerver  是否永不停止的循环
     * @return
     */
    public  static CCAction getAnimate(String format, int num, boolean isForerver) {
        ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();
        //String format="image/loading/loading_%02d.png";
        for (int i = 1; i <= num; i++) {
            CCSpriteFrame spriteFrame = CCSprite.sprite(String.format(format, i)).displayedFrame();
            frames.add(spriteFrame);
        }
        CCAnimation anim = CCAnimation.animation("", 0.2f, frames);
        // 序列帧一般必须永不停止的播放  不需要永不停止播放,需要指定第二个参数 false
        if (isForerver) {
            CCAnimate animate = CCAnimate.action(anim);
            CCRepeatForever forever = CCRepeatForever.action(animate);
            return forever;
        } else {
            CCAnimate animate = CCAnimate.action(anim, false);
            return animate;
        }
    }

}
