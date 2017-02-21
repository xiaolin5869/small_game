package com.example.pvzhm.Layer;

import com.example.pvzhm.utils.CommonUtils;

import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

/**
 * Created by xiaolin on 2016/9/20.
 */
public class MenuLayer extends BaseLayer
{
    public MenuLayer() {
        init();
    }

    public void init() {
        CCSprite sprite = CCSprite.sprite("image/menu/main_menu_bg.jpg");
        sprite.setAnchorPoint(0,0);
        this.addChild(sprite);

        //添加菜单
        CCSprite normal= CCSprite.sprite("image/menu/start_adventure_default.png");
        CCSprite pressed= CCSprite.sprite("image/menu/start_adventure_press.png");
        //参数1 是默认的图片，参数2是按下的图片，参数3是对象，参数4是调用参数3的方法（反射）
        CCMenuItem items= CCMenuItemSprite.item(normal,pressed,this,"onclick");

        CCMenu menu=CCMenu.menu(items);
        menu.setScale(0.5f);
        menu.setPosition(winSize.width/2-25,winSize.height/2-110);
        menu.setRotation(4.5f);
        this.addChild(menu);

    }
    //菜单能正擦反射，必须有一个参数object类型
    public void onclick(Object object)
    {
//        System.out.println("点击啦");
        CommonUtils.changeLayer(new FightLayer());
    }
}
