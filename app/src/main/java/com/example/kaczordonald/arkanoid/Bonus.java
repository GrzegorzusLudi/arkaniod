package com.example.kaczordonald.arkanoid;

import android.graphics.Canvas;

/**
 * Created by KaczorDonald on 2017-05-13.
 */

public class Bonus {
    float x,y;
    int val;
    //0 - 3 dodatkowe,1 - podwojenie, 2 - poszerzenie pałki,3 - kamikadze
    public Bonus(float x,float y){
        this.x = x;
        this.y = y;
        this.val = (int) Math.floor(Math.random()*4);
    }
    public void anim(GraphicsView g,Canvas c){
        g.drawBall(x,y,g.cy,c);
        c.drawText("?",x-5,y+5,g.bk);
        this.y+=4;

        if(y>750 && y<780 && x>g.platfx-g.platfw/2 && x<g.platfx+g.platfw/2){
            g.take(this);
        }
    }
}
