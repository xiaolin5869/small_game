package com.example.pvzhm.enginer;

import android.view.MotionEvent;

import com.example.pvzhm.Base.Plant;
import com.example.pvzhm.Bean.FightLine;
import com.example.pvzhm.Bean.Nut;
import com.example.pvzhm.Bean.PeasePlant;
import com.example.pvzhm.Bean.PrimaryZombies;
import com.example.pvzhm.Bean.ShowPlant;
import com.example.pvzhm.Layer.FightLayer;
import com.example.pvzhm.utils.CommonUtils;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xiaolin on 2016/9/23.
 * 游戏控制类
 */
public class GameController {
    //单例模式
    private static GameController mController = new GameController();
    private List<CGPoint> mPoints;

    public static GameController GetInstance()
    {
        return mController;
    }

    private CCTMXTiledMap map;
    public static boolean isStart=false;
    private List<ShowPlant> selectedplant;

    public static List<FightLine> lines;//管理每行

    static {
        lines=new ArrayList<FightLine>();
        for(int i=0;i<5;i++)
        {
            FightLine line=new FightLine(i);
            lines.add(line);
        }
    }
    /**
     * 开始游戏
     */
    public void startGame(CCTMXTiledMap map, List<ShowPlant> selectedplant)
    {
        isStart =true;
        this.map=map;
        this.selectedplant=selectedplant;

         laodmap();
        //添加僵尸
//         addZombies();

        //定时器 1是方法名（反射需要float类型的参数） 2 是对象 3 间隔时间 4 是否暂停
        CCScheduler.sharedScheduler().schedule("addZombies",this,2,false);
        //安放植物

        //僵尸攻击植物

        //植物攻击僵尸
    }
    /**
     * 结束游戏
     */
    public void endGame()
    {
        isStart =false;
    }


    CGPoint[][] towers=new CGPoint[5][9];


    public void laodmap()
    {
        mPoints = CommonUtils.parseMap(map, "road");

        for(int i=1;i<=5;i++)
        {
            List<CGPoint> mapPoints = CommonUtils.parseMap(map, String.format("tower%02d", i));
            for(int j=0;j<mapPoints.size();j++)
            {
                towers[i-1][j]=mapPoints.get(j);
            }
        }

    }
    public void addZombies(float t)
    {
        Random random=new Random();
        int lineNum = random.nextInt(5);//[0,5)

        PrimaryZombies zombies=new PrimaryZombies(mPoints.get(lineNum*2),mPoints.get(lineNum*2+1));
        map.addChild(zombies,1);//设置权限在最上面

        //将僵尸记录到行战场
        lines.get(lineNum).addZomBies(zombies);
    }

    /**
     * 游戏开开始后处理触摸事件
     * @param point
     */
    private ShowPlant selPlant;//选择的植物
    private Plant InstallPlant;//选择的植物

    public void handlerTouchEvent(CGPoint point) {
        CCSprite chose = (CCSprite) map.getParent().getChildByTag(FightLayer.TAG_CHOSE);
        if (CGRect.containsPoint(chose.getBoundingBox(), point)) {
            if(selPlant!=null)
            {
                selPlant.getShowSprite().setOpacity(255);//设置不透明度，越大越不透明
                selPlant=null;
            }
            //有可能选择植物
            for (ShowPlant plant : selectedplant) {
                //选择植物
                if (CGRect.containsPoint(plant.getShowSprite().getBoundingBox(), point)) {
                    this.selPlant = plant;
                    selPlant.getShowSprite().setOpacity(150);//设置不透明度，越大越不透明
                    int id = selPlant.getId();
                    switch (id) {
                        case 1:
                            InstallPlant=new PeasePlant();//创建豌豆射手
                            break;
                        case 4:
                            InstallPlant = new Nut();//创建坚果
                            break;
                        default:
                            break;
                    }
                }
            }
        }else{
                //可能是安放植物
                if(selPlant!=null)
                {
                    int row= (int) (point.x/46)-1;//列 0-8
                    int lineNum= (int) ((CCDirector.sharedDirector().winSize().height-point.y)/54)-1;//0-4

                    if(lineNum<=4 && lineNum>=0 && row<=8 && row>=0)
                    {
                        //InstallPlant.setPosition(point);

                        //修正植物的位置
                        InstallPlant.setLine(lineNum);
                        InstallPlant.setRow(row);

                        FightLine fightLine = lines.get(lineNum);
                        if(!fightLine.containRow(row)) {
//                          设置修正后的位置
                            fightLine.addPlants(InstallPlant);
                            InstallPlant.setPosition(towers[lineNum][row]);
                            map.addChild(InstallPlant);
                        }
                    }
                    selPlant.getShowSprite().setOpacity(255);//设置不透明度，越大越不透明
                    InstallPlant=null;
                    selPlant=null;//需要重新选择
                }
            }

        }
}

