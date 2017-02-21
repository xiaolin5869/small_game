package com.example.pvzhm.Layer;

import android.view.MotionEvent;

import com.example.pvzhm.Bean.ShowPlant;
import com.example.pvzhm.Bean.ShowZombies;
import com.example.pvzhm.enginer.GameController;
import com.example.pvzhm.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by xiaolin on 2016/9/22.
 * 僵尸战斗的地方
 */
public class FightLayer extends BaseLayer{

    public static final int TAG_CHOSE =10 ;
    private CCTMXTiledMap map;//地图
    private List<CGPoint> zombies;//僵尸
    private CCSprite chose;//玩家已选择的植物
    private CCSprite choose;//玩家可选择的植物
    private CCSprite start;
    private CCSprite ready;

    public FightLayer() {
        init();
    }

    private void init() {

        loadMap();
        parseMap();
        showZombies();
        moveMap();
    }

    /**
     * 展示僵尸
     */
    private void showZombies() {
        for(int i=0;i<zombies.size();i++)
        {
            CGPoint point = zombies.get(i);
            ShowZombies zombies=new ShowZombies();
            zombies.setPosition(point);
            map.addChild(zombies);//把僵尸记载在地图上
        }
    }

    private void parseMap()
    {
        zombies = CommonUtils.parseMap(map, "zombies");
    }

    /**
     * 地图移动
     */
    private void moveMap() {
        int x= -150;
        CCMoveTo move=CCMoveTo.action(3,ccp(x,0));
        CCSequence sequence = CCSequence.actions(CCDelayTime.action(3), move,CCDelayTime.action(2), CCCallFunc.action(this,"loadContainer"));
        map.runAction(sequence);
    }

    /**
     * 加载选择植物的容器
     */
    public void loadContainer()
    {
        chose = CCSprite.sprite("image/fight/chose/fight_chose.png");
        chose.setAnchorPoint(0,1);
        chose.setPosition(0,map.getContentSize().height);
        this.addChild(chose,0,TAG_CHOSE);

        choose = CCSprite.sprite("image/fight/chose/fight_choose.png");
        choose.setAnchorPoint(0,0);
        this.addChild(choose);
        loadShowPlant();

        //加载开始按钮
        start = CCSprite.sprite("image/fight/chose/fight_start.png");
        start.setPosition(choose.getContentSize().width/2,30);
        choose.addChild(start);
    }

    /**
     * 加载植物
     */
    private List<ShowPlant> mPlants;
    private void loadShowPlant() {
        mPlants=new ArrayList<ShowPlant>();

        for(int i=1;i<=9;i++)
        {
            ShowPlant plant=new ShowPlant(i);
            CCSprite sprite = plant.getShowSprite();

            //先添加背景植物，在下面
            CCSprite bgSprite = plant.getBgSprite();
            bgSprite.setPosition(16 + ((i - 1) % 4) * 54,
                    175 - ((i - 1) / 4) * 59);
            choose.addChild(bgSprite);

            // 设置坐标
            sprite.setPosition(16 + ((i - 1) % 4) * 54,
                    175 - ((i - 1) / 4) * 59);
            choose.addChild(sprite); // 添加到了容器上
            choose.addChild(sprite);

            mPlants.add(plant);
        }
       setIsTouchEnabled(true);//打开触摸事件开关
    }

    List<ShowPlant> selectedplant=new CopyOnWriteArrayList<ShowPlant>();//已被选择植物的列表

    boolean islock;//判断是否被锁
    boolean isDel;//是否删除了已选中的植物
    public boolean ccTouchesBegan(MotionEvent event) {
        CGPoint point = this.convertTouchToNodeSpace(event);
        if(GameController.isStart)
        {
            //游戏开始
            GameController.GetInstance().handlerTouchEvent(point);//处理触摸事件
            return super.ccTouchesBegan(event);
        }


        CGRect box = choose.getBoundingBox();//可以选择植物的区域
        CGRect box1 = chose.getBoundingBox();//已选植物的区域

       if( CGRect.containsPoint(box1,point))
       {
           //有可能反选植物
           isDel=false;
           for (ShowPlant plant : selectedplant) {
               CGRect boundingBox = plant.getShowSprite().getBoundingBox();
               if (CGRect.containsPoint(boundingBox, point)) {
                  //反选植物
                   CCMoveTo moveTo=CCMoveTo.action(1, plant.getBgSprite().getPosition());
                   plant.getShowSprite().runAction(moveTo);
                   selectedplant.remove(plant);
                   isDel=true;
                   continue;
               }
               if(isDel)
               {
                   CCMoveBy moveBy=CCMoveBy.action(1,ccp(-53,0));//原坐标移动的距离，moveTo是移动到某位置
                   plant.getShowSprite().runAction(moveBy);
               }

           }

       }else if (CGRect.containsPoint(box, point)) {
               if(CGRect.containsPoint(start.getBoundingBox(),point))
               {
                   //点击开始按钮
                   ready();
               }else if (selectedplant.size() < 5 && !islock) {
                   //有可能选择植物
                   for (ShowPlant plant : mPlants) {
                       CGRect boundingBox = plant.getShowSprite().getBoundingBox();
                       if (CGRect.containsPoint(boundingBox, point)) {
                           islock = true;//加锁
                           CCMoveTo moveTo = CCMoveTo.action(0.5f,
                                   ccp(75 + selectedplant.size() * 53,
                                           255));
                           CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this, "unlock"));
                           plant.getShowSprite().runAction(sequence);
                           selectedplant.add(plant);
                       }
                   }

               } else {

               }
           }

        return super.ccTouchesBegan(event);
    }

    /**
     * 游戏开始前的准备
     */
    private void ready() {
       //缩小已选择植物的容器
        chose.setScale(0.5f);
        //将已选择的植物重新提取出来
        for(ShowPlant plant:selectedplant)
        {
            plant.getShowSprite().setScale(0.5f);// 因为父容器缩小了 孩子一起缩小 0.65f
            plant.getShowSprite().setPosition(
                    plant.getShowSprite().getPosition().x * 0.5f, //0.65f
                    plant.getShowSprite().getPosition().y
                            + (CCDirector.sharedDirector().getWinSize().height - plant
                            .getShowSprite().getPosition().y)
                            * 0.5f);// 设置坐标 0.35f
            this.addChild(plant.getShowSprite());
        }

        choose.removeSelf();// 回收容器
        // 地图的平移
        int x = 150;
        CCMoveBy moveBy = CCMoveBy.action(1, ccp(x, 0));
        CCSequence sequence=CCSequence.actions(moveBy, CCCallFunc.action(this, "preGame"));
        map.runAction(sequence);
    }

    public void preGame()
    {
        ready = CCSprite.sprite("image/fight/startready_01.png");
        ready.setPosition(map.getContentSize().width/2,map.getContentSize().height/2);
        this.addChild(ready);

        String format="image/fight/startready_%02d.png";
        CCAction animate = CommonUtils.getAnimate(format, 3, false);
        CCSequence sequence = CCSequence.actions((CCAnimate)animate, CCCallFunc.action(this,"startGame"));
        ready.runAction(sequence);
    }

    /**
     * 开始游戏
     */
    public void startGame()
    {
        ready.removeSelf();

        GameController controller = GameController.GetInstance();
        controller.startGame(map,selectedplant);

    }

    /**
     * 解锁
     */
    public void unlock()
    {
        islock=false;
    }

    /**
     * 加载地图
     */
    private void loadMap() {
        map = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
        map.setAnchorPoint(0,0);
        CGSize size = map.getContentSize();
        map.setPosition(0,0);
        this.addChild(map);
    }
}
