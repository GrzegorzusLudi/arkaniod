package com.example.kaczordonald.arkanoid;

import android.graphics.Canvas;

/**
 * Created by KaczorDonald on 2017-05-13.
 */

public class Ball {
    float x;
    float y;
    float vx;
    float vy;
    boolean power = false;
    public Ball(GraphicsView g,float x,float y){
        this.x = x;
        this.y = y;
        this.vx = g.acc;
        this.vy = -8;
    }
    private boolean check(float x,float y,GraphicsView g){
        int wx = (int)(Math.floor(x/20));
        int wy = (int)(Math.floor(y/20));
        if(wx>=0 && wx<30 && wy>=0) {
            if (wy<18 && g.klocki[wx][wy].master!=null) {
                g.klocki[wx][wy].master.destroy();
                return true;
            } else
                return false;
        }
        return false;
    }
    public void anim(GraphicsView g,Canvas c){
        x+=vx;
        y+=vy;
        if(check(x,y-10,g)){
            if(!power)
            if(vy<0)
            vy = -vy;
        } else if(check(x,y+10,g)){
            if(!power)
            if(vy>0)
            vy = -vy;
        } else if(check(x-10,y,g)){
            if(!power)
            if(vx<0)
            vx = -vx;
        } else if(check(x+10,y,g)){
            if(!power)
            if(vx>0)
            vx = -vx;
        }
        if(x<10){
            x = 10;
            if(vx<0)
            vx = -vx;
        }
        if(x>590){
            x = 590;
            if(vx>0)
                vx = -vx;
        }
        if(y<10){
            y = 10;
            if(vy<0)
                vy = -vy;
        }
        if(y>750 && y<780 && x>g.platfx-g.platfw/2 && x<g.platfx+g.platfw/2){
            if(vy>0)
            vy = -vy;
            vx += (x-g.platfx)/3;
        }
        double sq = Math.sqrt((double)(vx*vx+vy*vy));
        double r = Math.atan2((double)vx,(double)vy);
        if(sq!=10){
            vx=(float)(sq*Math.sin(r));
            vy=(float)(sq*Math.cos(r));
        }
        if(!power)
            g.drawBall(x,y,g.w,c);
        else {
            if(y%10<5)
                g.drawBall(x, y, g.y, c);
            else
                g.drawBall(x, y, g.cy, c);
        }
    }
}
