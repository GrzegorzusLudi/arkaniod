package com.example.kaczordonald.arkanoid;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Brick {
    float x,y;
    int kolor=0;
    boolean alive;
    float wx=0,wy=0;
    GraphicsView g;
    Brick master = null;
    Brick(float x,float y,GraphicsView g){
        this.x = x;
        this.y = y;
        this.g = g;
    }
    void setBrick(int kolor,float wx,float wy){
        this.kolor = kolor;
        this.wx = wx;
        this.wy = wy;
        for(int i=0;i<wx && i<30;i++){
            for(int j=0;j<wy && j<18;j++){
                g.klocki[(int)x+i][(int)y+j].master = this;
            }
        }
    }
    void destroy(){
        this.kolor = 0;
        for(int i=0;i<wx && i<30;i++){
            for(int j=0;j<wy && j<18;j++){
                g.klocki[(int)x+i][(int)y+j].master = null;
            }
        }
        if(Math.random()<0.1)
            this.g.bonus(x*20+10,y*20+10);
        g.count--;
    }
    void draw(Canvas c){
        if(kolor>0) {
            Paint p = g.bl;
            switch (this.kolor) {
                case 1:
                    p = g.cy;
                    break;
                case 2:
                    p = g.g;
                    break;
                case 3:
                    p = g.r;
                    break;
                case 4:
                    p = g.y;
                    break;
                case 5:
                    p = g.o;
                    break;
                case 6:
                    p = g.bl;
                    break;
            }
            g.rekt(x * 20, y * 20, 20 * wx, 20 * wy, p, c);
        }
    }
}
