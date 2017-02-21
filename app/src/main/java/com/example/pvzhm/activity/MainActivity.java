package com.example.pvzhm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pvzhm.Layer.WelcomeLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends AppCompatActivity {

    private CCDirector director;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建surfaceview
        CCGLSurfaceView surfaceView=new CCGLSurfaceView(this);
        setContentView(surfaceView);

        //导演
        director = CCDirector.sharedDirector();
        //开启线程
        director.attachInView(surfaceView);
        director.setDisplayFPS(true);//显示帧率
        director.setScreenSize(480,320);//设置屏幕的大小
        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);//设置横屏

        CCScene scene = CCScene.node();//创建场景
        scene.addChild(new WelcomeLayer());

        director.runWithScene(scene);

    }

    protected void onPause() {
        super.onPause();
        director.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        director.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        director.end();
    }
}
