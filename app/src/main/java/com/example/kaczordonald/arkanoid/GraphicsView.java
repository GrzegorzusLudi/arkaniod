package com.example.kaczordonald.arkanoid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by KaczorDonald on 2017-05-12.
 */

public class GraphicsView extends View {
    public Paint r,bk,bl,g,y,w,cy,o;
    float platfx = 300f;
    float startplatfw = 80f;
    float platfw = startplatfw;
    float acc = 0f;
    float vel = 0f;
    boolean pilka = true;
    Brick klocki[][] = new Brick[30][30];
    ArrayList<Ball> balls = new ArrayList<Ball>();
    ArrayList<Bonus> bonusy = new ArrayList<Bonus>();
    int count = 0;
    int deaths = 0;
    public float a = 0;
    public void setAcc(float rot){
        this.acc = rot;
    }
    private void newGame(){
        platfw = startplatfw;
        balls.clear();
        bonusy.clear();
        pilka = true;
        count = 0;
        deaths = 0;
        for(int k = 0;k<200;k++){
            float x = (float)Math.floor(Math.random()*30);
            float y = (float)Math.floor(Math.random()*30);
            float wx = (float)Math.floor(Math.random()*3)+1;
            float wy = (float)Math.floor(Math.random()*3)+1;
            if(x+wx>30)
                wx = 30-x;
            if(y+wy>18)
                wy = 18-y;
            boolean oo = false;
            if(wx==1 && wy==1)
                oo=true;
            else
            for(int i=0;i<wx && i<30;i++){
                for(int j=0;j<wy && j<18;j++){
                    if(klocki[(int)x+i][(int)y+j].master!=null)
                        oo = true;
                }
            }
            if(!oo) {
                klocki[(int) x][(int) y].setBrick((int) Math.floor(Math.random() * 6 + 1), wx, wy);
                count++;
            }
        }
        for(int i=0;i<30;i++){
            for(int j=5;j<18;j++){
                if(Math.random()>0.3 && klocki[i][j].master==null) {
                    klocki[i][j].setBrick((int) Math.floor(Math.random() * 6 + 1), 1, 1);
                    count++;
                }
            }
        }
    }
    public GraphicsView(Context context, AttributeSet atts) {
        super(context,atts);
        r = new Paint();
        r.setARGB(255,255,0,0);
        bk = new Paint();
        bk.setARGB(255,0,0,0);
        bl = new Paint();
        bl.setARGB(255,0,0,255);
        g = new Paint();
        g.setARGB(255,0,255,0);
        y = new Paint();
        y.setARGB(255,255,255,0);
        w = new Paint();
        w.setARGB(255,255,255,255);
        cy = new Paint();
        cy.setARGB(255,0,255,255);
        o = new Paint();
        o.setARGB(255,255,128,0);

        for(int i =0;i<30;i++){
            for(int j=0;j<30;j++){
                klocki[i][j]=new Brick((float)i,(float)j,this);
            }
        }
        newGame();
    }
    public void rekt(float l,float t, float w,float h,Paint p,Canvas c){
        c.drawLine(l,t,l+w,t, p);
        c.drawLine(l,t,l,t+h, p);
        c.drawLine(l+w,t,l+w,t+h, p);
        c.drawLine(l,t+h,l+w,t+h, p);
        c.drawRect(l+5,t+5,l+w-5,t+h-5,p);
    }
    public void drawBall(float x,float y,Paint p,Canvas c){
        c.drawOval(new RectF(x-10,y-10,x+10,y+10),p);
    }
    public void bonus(float x, float y){
        bonusy.add(new Bonus(x,y));
    }
    public void take(Bonus b){
        switch(b.val){
            case 0:
                Ball b1 = new Ball(this,platfx, 750);
                Ball b2 = new Ball(this,platfx, 750);
                Ball b3 = new Ball(this,platfx, 750);
                balls.add(b1);
                balls.add(b2);
                balls.add(b3);
                b1.vx = -5;
                b3.vx = 5;
                break;

            case 1:
                int ss = balls.size();
                for(int i = 0;i<ss;i++){
                    balls.add(new Ball(this,balls.get(i).x, balls.get(i).y));
                    balls.get(i+ss).vx = -balls.get(i).vx;
                    balls.get(i+ss).vy = -balls.get(i).vy;
                }
                break;
            case 2:
                platfw+=20;
                break;
            case 3:
                Ball b4 = new Ball(this,platfx, 750);
                balls.add(b4);
                b4.power = true;
                break;
        }
        bonusy.remove(b);
    }
    @Override
    protected void onDraw(Canvas c){
        super.onDraw(c);


        if(this.acc<0){
            platfx+=this.acc;
        } else {
            platfx+=this.acc;
        }
        if(platfx<platfw/2) {
            platfx = platfw / 2;
            if(vel<0)
            vel = -vel;
        }
        if(platfx>600-platfw/2) {
            platfx = 600 - platfw / 2;
            if(vel>0)
            vel = -vel;
        }

        c.save();
        c.scale(((float)c.getWidth())/600f,((float)c.getHeight())/800f);
        c.drawRGB(0,0,0);

        count = 0;
        for(int i=0;count==0 && i<30;i++){
            for(int j=5;count==0 && j<18;j++){
                if(klocki[i][j].master!=null) {
                    count++;
                }
            }
        }

        if(count<=0){
            w.setTextSize(40);
            c.drawText("Wybiłeś wszystkie klocki",80,200,w);
            w.setTextSize(28);
            c.drawText("Śmierci: "+deaths,230,300,w);
            w.setTextSize(32);
            c.drawText("Tap to restart",200,400,w);
        }

        for(int i =0;i<30;i++){
            for(int j=0;j<18;j++){
                klocki[i][j].draw(c);
            }
        }
        for(int n = 0;n<balls.size();n++){
            balls.get(n).anim(this,c);
            if(balls.get(n).y>800)
                balls.remove(n);
        }
        for(int n = 0;n<bonusy.size();n++){
            bonusy.get(n).anim(this,c);
        }
        if(balls.size()==0) {
            if(!pilka && count>0)
                deaths++;
            pilka = true;
        }

        rekt(platfx-platfw/2,760,platfw,20, w,c);
        if(pilka)
            drawBall(platfx,750,w,c);

        c.restore();
    }
    public void touch(){
        if(count==0) {
            newGame();
        } else if(pilka) {
            pilka = false;
            balls.add(new Ball(this,platfx, 750));
        }
    }
}
