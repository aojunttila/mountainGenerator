import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.util.Random;

// a program that simulates the chemical process of Diffusion-limited Aggregation for procedural terrain generation

public class MainProcess{
    boolean[][]mainList;boolean fancyMode=false;
    BufferedImage displayImage;
    JFrameImage display;int tempFrameCount;
    JFramePolygon boardOutline;
    int displayList[][];
    int scaleCount=2;int fX;int fY;
    int sizeMultiplier=8;boolean[][][]stickGraph;
    int startX;int startY;int upscaleCount=0;
    int xpos=50;int ypos=50;int w;int h;
    int xWidth;int yWidth;int pixelCount=0;
    int framecount=0;int framecount2=0;Random rand=new Random();
    public MainProcess(int sX,int sY,boolean fm){
        startX=sX;startY=sY;fancyMode=fm;
        xWidth=(int)Math.pow(4,scaleCount)*startX;int yWidth=(int)Math.pow(4,scaleCount)*startY;
        displayImage=new BufferedImage(startX,startY,BufferedImage.TYPE_INT_RGB);
        //displayImage.setRGB(2,2,new Color(255,100,100).getRGB());
        display=new JFrameImage(xpos,ypos,(int)Math.pow(4,scaleCount)*startX*sizeMultiplier,(int)Math.pow(4,scaleCount)*startY*sizeMultiplier,0,displayImage);
        boardOutline=new JFramePolygon(new int[]{xpos,xpos+xWidth*sizeMultiplier,xpos+xWidth*sizeMultiplier,xpos}, new int[]{ypos,ypos,ypos+yWidth*sizeMultiplier,ypos+yWidth*sizeMultiplier}, new Color(100,100,100), new Color(100,100,100),10);
        mainList=createList(startX,startY);
        stickGraph=new boolean[startX][startY][4];
        updateDims();
        mainList[(int)(w/2)][(int)(h/2)]=true;
        pixelCount+=1;
        
    }

    int limiter=6;
    boolean stopAtUpscale=true;
    float divisor=6;
    public void nextFrame(){
        updateDims();
        framecount++;framecount2++;
        if(framecount2<1000){return;}
        if(pixelCount<(int)(w*h)/divisor&&(upscaleCount<limiter||!stopAtUpscale)){
            if(fancyMode){copyToDisplayList();}
            recursivePixelSpawn();
            recursiveMove(0);
            if(!(pixelCount<(int)(w*h)/divisor)){framecount2=0;}
        }else{
            if(upscaleCount<limiter){
                upscale();
                //mutateLines();
                recalculatePixelCount();
                copyToDisplayList();
            }else{return;}
        }
        try{
        updateImage();
        }catch(Exception e){System.out.println("oh no!");}
    }

    public void recursivePixelSpawn(){
        int tempX=rand.nextInt(mainList.length);
        int tempY=rand.nextInt(mainList[0].length);
        if(mainList[tempX][tempY]){recursivePixelSpawn();}
        else{fX=tempX;fY=tempY;}
    }

    public void updateDims(){
        w=mainList.length;
        h=mainList[0].length;
    }

    public void copyToDisplayList(boolean absolute){
        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                if(absolute)displayList[x][y]=mainList[x][y]?255:0;
            }
        }
    }

    public void recalculatePixelCount(){
        updateDims();pixelCount=0;
        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                if(mainList[x][y]){pixelCount+=1;}
            }
        }
        //pixelCount/=2;
    }

    public void mutateLines(){
        updateDims();
        int randomThing;
        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                if(mainList[x][y]&&rand.nextInt(6)==0){
                    if(x>0&&x<w-1&&mainList[x+1][y]&&mainList[x-1][y]){
                        randomThing=rand.nextInt(2);randomThing*=2;randomThing-=1;
                        mainList[x][y]=false;mainList[x][y+randomThing]=true;}
                    else if(y>0&&y<h-1&&mainList[x][y+1]&&mainList[x][y-1]){
                        randomThing=rand.nextInt(2);randomThing*=2;randomThing-=1;
                        mainList[x][y]=false;mainList[x+randomThing][y]=true;}
                }
            }
        }
    }

    public void upscale(){
        boolean[][]tempList=mainList;
        updateDims();
        mainList=createList(w*2,h*2);
        boolean[][][]stickGraph2=new boolean[w*2][h*2][4];

        //updateDims();
        //w=w*2;h=h*2;
        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                if(tempList[x][y]){
                    try{if(tempList[x][y]){
                        mainList[x*2][y*2]=true;
                    }}catch(Exception e){}
                    try{if(stickGraph[x][y][1]){
                        mainList[x*2+1][y*2]=true;
                        stickGraph2[x*2][y*2][1]=true;
                        stickGraph2[x*2+1][y*2][1]=true;
                    }}catch(Exception e){}
                    try{if(stickGraph[x][y][3]){
                        mainList[x*2][y*2+1]=true;
                        stickGraph2[x*2][y*2][3]=true;
                        stickGraph2[x*2][y*2+1][3]=true;
                    }}catch(Exception e){}
                    try{if(stickGraph[x][y][2]){
                        mainList[x*2][y*2-1]=true;
                        stickGraph2[x*2][y*2][2]=true;
                        stickGraph2[x*2][y*2-1][2]=true;
                    }}catch(Exception e){}
                    try{if(stickGraph[x][y][0]){
                        mainList[x*2-1][y*2]=true;
                        stickGraph2[x*2][y*2][0]=true;
                        stickGraph2[x*2-1][y*2][0]=true;
                    }}catch(Exception e){}

                }else{}
            }
        }
        upscaleCount+=1;
        //framecount2=0;
        updateDims();
        stickGraph=stickGraph2;
        displayImage=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
    }

    public static final int MAX_RECURSION_DEPTH = 2000;

    public void recursiveMove(int recursionDepth) {
        if (recursionDepth >= MAX_RECURSION_DEPTH) {
            mainList[fX][fY]=false;
            return;
        }
        
        int tempMove = rand.nextInt(4);
        
        int tfX = fX;
        int tfY = fY;
    
        try {
            mainList[fX][fY] = false;
            fY += tempMove == 0 ? -1 : tempMove == 1 ? 1 : 0;
            fX += tempMove == 2 ? -1 : tempMove == 3 ? 1 : 0;
            mainList[fX][fY] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            fX = tfX;
            fY = tfY;
        }
    
        if (checkIfTouching()) {
            pixelCount += 1;
        } else {
            if(fancyMode){displayList[fX][fY]=rand.nextInt(100);}
            recursiveMove(recursionDepth + 1);
        }
    }
    
    //right left up down
    public boolean checkIfTouching() {
        try {
            if (fX < mainList.length - 1 && mainList[fX + 1][fY]) {
                stickGraph[fX+1][fY][0]=true;
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            if (fX > 0 && mainList[fX - 1][fY]) {
                stickGraph[fX-1][fY][1]=true;
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            if (fY < mainList[0].length - 1 && mainList[fX][fY + 1]) {
                stickGraph[fX][fY+1][2]=true;
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            if (fY > 0 && mainList[fX][fY - 1]) {
                stickGraph[fX][fY-1][3]=true;
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        return false;
    }
    
    
    /*
    public void recursiveMove(){
        int tempMove=rand.nextInt(4);
        //up down left right
        try{
        mainList[fX][fY]=false;}catch(Exception e){}
        int tfX=0;int tfY=0;
        try{
        tfX=fX;tfY=fY;
        fY+=tempMove==0?-1:tempMove==1?1:0;
        fX+=tempMove==2?-1:tempMove==3?1:0;}catch(Exception e){}
        try{mainList[fX][fY]=true;}
        catch(Exception e){fX=tfX;fY=tfY;mainList[fX][fY]=true;}
        if(checkIfTouching()){pixelCount+=1;}
        else{recursiveMove();}
    }

    public boolean checkIfTouching(){
        int temp=0;
        try{if(mainList[fX+1][fY]){return true;}}catch(Exception e){}
        try{if(mainList[fX-1][fY]){return true;}}catch(Exception e){}
        try{if(mainList[fX][fY+1]){return true;}}catch(Exception e){}
        try{if(mainList[fX][fY-1]){return true;}}catch(Exception e){}
        return false;
    }*/

    public void draw(Graphics2D g2d){
        boardOutline.draw(g2d);
        display.draw(g2d);
    }

    public boolean[][]createList(int x,int y){
        boolean[][]temp=new boolean[x][y];
        if(fancyMode){displayList=new int[x][y];}
        return temp;
    }

    public void updateImage(){
        for(int x=0;x<mainList.length;x++){
            for(int y=0;y<mainList[0].length;y++){
                if(fancyMode){
                    displayImage.setRGB(x,y,new Color(displayList[x][y],displayList[x][y],displayList[x][y]).getRGB());
                }
                else{
                    if(mainList[x][y]){
                        displayImage.setRGB(x,y,new Color(255,255,255).getRGB());
                    }else{
                        displayImage.setRGB(x,y,new Color(0,0,0).getRGB());
                    }
                }
            }
        }
        display.image=displayImage;
    }

}
