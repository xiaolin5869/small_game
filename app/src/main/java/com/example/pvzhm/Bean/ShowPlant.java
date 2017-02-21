package com.example.pvzhm.Bean;

import org.cocos2d.nodes.CCSprite;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by xiaolin on 2016/9/22.
 * 显示得植物
 */
public class ShowPlant {
    static HashMap<Integer ,HashMap<String,String>> db;

    static {
        db=new HashMap<Integer ,HashMap<String,String>>();
        String format="image/fight/chose/choose_default%02d.png";
        for(int i=1;i<=9;i++)
        {
            HashMap<String,String> value=new HashMap<String,String>();
            value.put("path",String.format(format,i));
            value.put("sun",50+"");
            db.put(i,value);
        }
    }

    private CCSprite showSprite;
    private CCSprite bgSprite;

    public int getId() {
        return id;
    }

    private int id;
    /**
     * 根据id得到植物
     * @param id
     */
    public ShowPlant(int id){
        this.id=id;
        HashMap<String, String> hashMap = db.get(id);
        String path = hashMap.get("path");
        showSprite = CCSprite.sprite(path);
        showSprite.setAnchorPoint(0,0);

        bgSprite= CCSprite.sprite(path);
        bgSprite.setOpacity(150);//设置半透明
        bgSprite.setAnchorPoint(0,0);

    }

    public CCSprite getBgSprite() {
        return bgSprite;
    }

    public CCSprite getShowSprite() {
        return showSprite;
    }

}
