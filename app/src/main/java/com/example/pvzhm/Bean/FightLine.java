package com.example.pvzhm.Bean;

import com.example.pvzhm.Base.AttackPlant;
import com.example.pvzhm.Base.BaseElement;
import com.example.pvzhm.Base.Bullet;
import com.example.pvzhm.Base.Plant;
import com.example.pvzhm.Base.Zombies;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaolin on 2016/9/23.
 * 把一行的逻辑提取出来
 * 一行可以放僵尸 安放植物等
 */
public class FightLine {
    private  int lineNum;
    public FightLine(int lineNum) {
        this.lineNum=lineNum;

        CCScheduler.sharedScheduler().schedule("attackPlant",this,0.2f,false);
        CCScheduler.sharedScheduler().schedule("createBullet",this,0.2f,false);//定时器
        CCScheduler.sharedScheduler().schedule("attackZombies",this,0.2f,false);//定时器
    }


    private List<Zombies> mZombies=new ArrayList<>();

    private Map<Integer,Plant> plants=new HashMap<Integer, Plant>();//key为列号

    private ArrayList<AttackPlant> mAttackPlants=new ArrayList<>();//攻击植物的集合

    /**
     * 判断子弹是否和僵尸产生了碰撞
     * @param t
     */
    public void attackZombies(float t)
    {
        if(mAttackPlants.size()>0 && mZombies.size()>0)
        {
            for(Zombies zombies:mZombies)
            {
                float x = zombies.getPosition().x;
                float left=x-20;
                float right = x + 20;
               for(AttackPlant attackPlant:mAttackPlants)
               {
                   List<Bullet> bullets = attackPlant.getBullets();
                   for(Bullet bullet:bullets)
                   {
                       float bulletX = bullet.getPosition().x;
                       if(bulletX>left && bulletX<right)
                       {
                           zombies.attacked(bullet.getAttack());
//                           bullet.removeSelf();
                           bullet.setVisible(false);
                           bullet.setAttack(0);
                       }
                   }
               }

            }
        }
    }

    //发射子弹
    public void createBullet(float t)
    {
        if(mAttackPlants.size()>0 && mZombies.size()>0) {
            for (AttackPlant attackPlant : mAttackPlants) {
                attackPlant.createBullet();
            }
        }
    }

    public void addZomBies(final Zombies zombies)
    {
        mZombies.add(zombies);
        zombies.setDieListener(new BaseElement.DieListener() {
            @Override
            public void die() {
                mZombies.remove(zombies);
            }
        });
    }


    /**
     * 攻击植物
     */
    public void attackPlant(float t)
    {
        if(mZombies.size()>0 && plants.size()>0)
        {
            for(Zombies zombies:mZombies)
            {
                CGPoint position =zombies.getPosition();
                int row= (int) (position.x/46-1);//得到列
                Plant plant = plants.get(row);
                if(plant!=null)
                {
                    zombies.attack(plant);
                }

            }
        }
    }

    /**
     * 添加植物
     * @param plant
     */
    public void addPlants(final Plant plant)
    {
        plants.put(plant.getRow(),plant);

        if(plant instanceof AttackPlant)
        {
            mAttackPlants.add((AttackPlant)plant);//添加到集合
        }
        //设置植物的死亡监听
        plant.setDieListener(new BaseElement.DieListener() {
            @Override
            public void die() {
                plants.remove(plant.getRow());
                if(plant instanceof AttackPlant)
                {
                    mAttackPlants.remove((AttackPlant)plant);//集合中删除
                }

            }
        });


    }

    /**
     * 是否包含某列
     * @param row
     * @return
     */
    public boolean containRow(int row)
    {
        return plants.containsKey(row);
    }
}
