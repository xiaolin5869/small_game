package com.example.pvzhm.Bean;

import com.example.pvzhm.Base.BaseElement;
import com.example.pvzhm.Base.Plant;
import com.example.pvzhm.Base.Zombies;
import com.example.pvzhm.utils.CommonUtils;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

/**
 * Created by xiaolin on 2016/9/24.
 */
public class PrimaryZombies extends Zombies {

    public PrimaryZombies(CGPoint startPoint,CGPoint endPoint) {
        super("image/zombies/zombies_1/walk/z_1_01.png");
        this.startPoint=startPoint;
        this.endPoint=endPoint;

        //设置起点
        setPosition(startPoint);

        move();
    }

    @Override
    public void move() {
         //僵尸动作的序列帧
        CCAction animate = CommonUtils.getAnimate("image/zombies/zombies_1/walk/z_1_%02d.png", 7, true);
        this.runAction(animate);

        //移动的时间
        float t = CGPointUtil.distance(startPoint, endPoint)/speed;
        CCMoveTo moveTo = CCMoveTo.action(t, endPoint);

        CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this, "endGame"));
        this.runAction(sequence);

    }

    /**
     * 游戏结束
     */
    public void endGame()
    {
        this.destroy();//僵尸消失
    }


    Plant targetPlant;
    public void attack(BaseElement element) {
            if (element instanceof Plant) {
                if (targetPlant == null) {
                    Plant plant = (Plant) element;
                    targetPlant = plant;
                    stopAllActions();//停止所有动作
                    //转换成攻击模式
                    CCAction animate = CommonUtils.getAnimate("image/zombies/zombies_1/attack/z_1_attack_%02d.png", 10, true);
                    this.runAction(animate);

                    //让植物一直掉血
                    CCScheduler.sharedScheduler().schedule("attachPlant",this,0.5f,false);
                }


        }
    }
   public void attachPlant(float t)
   {
       //植物被攻击 生命值减去攻击力
        targetPlant.attacked(attack);
         if(targetPlant.getLife()<=0)
         {
             //植物死亡
             targetPlant=null;
             CCScheduler.sharedScheduler().unschedule("attachPlant",this);//解除定时器
             stopAllActions();
             move();
         }
   }

    @Override
    public void attacked(int attack) {

        life-=attack;
        if(life<=0)
        {
            destroy();
        }
    }

    @Override
    public void baseAction() {

    }
}
