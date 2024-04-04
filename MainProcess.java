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
    int scaleCount=7;int fX;int fY;
    int startX;int startY;
    int xpos=400;int ypos=50;int w;int h;
    int xWidth;int yWidth;int pixelCount=0;
    int framecount=0;Random rand=new Random();
    public MainProcess(int sX,int sY){
        xWidth=(int)Math.pow(2,scaleCount)*5;int yWidth=(int)Math.pow(2,scaleCount)*5;
        displayImage=new BufferedImage(5,5,BufferedImage.TYPE_INT_RGB);
        displayImage.setRGB(2,2,new Color(255,100,100).getRGB());
        display=new JFrameImage(xpos,ypos,(int)Math.pow(2,scaleCount)*5,(int)Math.pow(2,scaleCount)*5,0,displayImage);
        boardOutline=new JFramePolygon(new int[]{xpos,xpos+xWidth,xpos+xWidth,xpos}, new int[]{ypos,ypos,ypos+yWidth,ypos+yWidth}, new Color(100,100,100), new Color(100,100,100),10);
        startX=sX;startY=sY;
        mainList=createList(startX,startY);
        mainList[2][2]=true;
        pixelCount+=1;
        
    }

    public void nextFrame(){
        updateDims();
        framecount++;
        if(pixelCount<(int)(w*h)/2){
            recursivePixelSpawn();
            recursiveMove();
        }
        updateImage();
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
        for(int x=-1;x<2;x++){
            for(int y=-1;y<2;y++){
                try{
                    if(mainList[fX+x][fY+y]){temp+=1;}
                }catch(Exception e){}
            }
        }
        if(temp>1){return true;}
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
