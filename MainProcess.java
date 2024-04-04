import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MainProcess{
    boolean[][]mainList;
    BufferedImage displayImage;
    JFrameImage display;
    JFramePolygon boardOutline;
    int scaleCount=4;int fX;int fY;
    int startX;int startY;int upscaleCount=0;
    int xpos=400;int ypos=50;int w;int h;
    int xWidth;int yWidth;int pixelCount=0;
    int framecount=0;Random rand=new Random();
    public MainProcess(int sX,int sY){
        startX=sX;startY=sY;
        xWidth=(int)Math.pow(4,scaleCount)*startX;int yWidth=(int)Math.pow(4,scaleCount)*startY;
        displayImage=new BufferedImage(startX,startY,BufferedImage.TYPE_INT_RGB);
        //displayImage.setRGB(2,2,new Color(255,100,100).getRGB());
        display=new JFrameImage(xpos,ypos,(int)Math.pow(4,scaleCount)*startX,(int)Math.pow(4,scaleCount)*startY,0,displayImage);
        boardOutline=new JFramePolygon(new int[]{xpos,xpos+xWidth,xpos+xWidth,xpos}, new int[]{ypos,ypos,ypos+yWidth,ypos+yWidth}, new Color(100,100,100), new Color(100,100,100),10);
        mainList=createList(startX,startY);
        updateDims();
        mainList[(int)(w/2)][(int)(h/2)]=true;
        pixelCount+=1;
        
    }

    public void nextFrame(){
        updateDims();
        System.out.println("1");
        framecount++;
        if(pixelCount<(int)(w*h)/4){
            System.out.println("12");
            recursivePixelSpawn();
            System.out.println("123");
            recursiveMove();
            System.out.println("13");
        }else{
            System.out.println("22");
            try{
            upscale();
            recalculatePixelCount();
            }catch(Exception e){System.out.println("oh no!");}
            System.out.println("23");
        }
        System.out.println("2");
        try{
        updateImage();
        }catch(Exception e){System.out.println("oh no!");}
        System.out.println("3");
        System.out.println(fX+" "+fY+" "+rand.nextInt(2));
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

    public void recalculatePixelCount(){
        updateDims();pixelCount=0;
        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                if(mainList[x][y]){pixelCount+=1;}
            }
        }
        pixelCount/=2;
    }

    public void upscale(){
        boolean[][]tempList=mainList;
        updateDims();
        mainList=createList(w*4,h*4);
        updateDims();
        //w=w*2;h=h*2;
        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                try{
                if(tempList[(int)(x/4)][(int)(y/4)]){
                    mainList[x][y]=true;
                }else{

                }}catch(Exception e){}
            }
        }
        upscaleCount+=1;
        displayImage=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
    }

    public void recursiveMove(){
        int tempMove=rand.nextInt(4);
        //up down left right
        mainList[fX][fY]=false;
        int tfX=fX;int tfY=fY;
        fY+=tempMove==0?-1:tempMove==1?1:0;
        fX+=tempMove==2?-1:tempMove==3?1:0;
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
    }

    public void draw(Graphics2D g2d){
        boardOutline.draw(g2d);
        display.draw(g2d);
    }

    public boolean[][]createList(int x,int y){
        boolean[][]temp=new boolean[x][y];
        return temp;
    }

    public void updateImage(){
        for(int x=0;x<mainList.length;x++){
            for(int y=0;y<mainList[0].length;y++){
                if(mainList[x][y]){
                    displayImage.setRGB(x,y,new Color(255,255,255).getRGB());
                }else{
                    displayImage.setRGB(x,y,new Color(0,0,0).getRGB());
                }
            }
        }
        display.image=displayImage;
    }

}
