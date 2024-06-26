import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BlurredImage{
    boolean mainList[][];
    int xWidth;int yWidth;
    BufferedImage displayImage;
    JFrameImage display;boolean[][][]stickGraph;
    JFramePolygon boardOutline;
    int sizeMultiplier=128;
    int pxCount;int[][]weightList;
    int startX,startY;int[][]orderList;
    public BlurredImage(boolean[][]list,int[][]olist,boolean[][][]stickList,int xpos,int ypos){
        mainList=list;
        stickGraph=stickList;
        //startX=sX;startY=sY;
        startX=list.length;startY=list[0].length;
        xWidth=startX;yWidth=startY;
        orderList=olist;
        displayImage=new BufferedImage(startX,startY,BufferedImage.TYPE_INT_RGB);
        //displayImage.setRGB(2,2,new Color(255,100,100).getRGB());
        display=new JFrameImage(xpos,ypos,startX*sizeMultiplier,startY*sizeMultiplier,0,displayImage);
        boardOutline=new JFramePolygon(new int[]{xpos,xpos+xWidth*sizeMultiplier,xpos+xWidth*sizeMultiplier,xpos}, new int[]{ypos,ypos,ypos+yWidth*sizeMultiplier,ypos+yWidth*sizeMultiplier}, new Color(100,100,100), new Color(100,100,100),10);
        mainList=list;
        weightList=new int[startX][startY];
        //stickGraph=new boolean[startX][startY][4];
        //mainList[(int)(w/2)][(int)(h/2)]=true;
        //pixelCount+=1;
    }

    public void draw(Graphics2D g2d){
        boardOutline.draw(g2d);
        display.draw(g2d);
    }

    public void updateList(boolean[][]list,int[][]list2,boolean[][][]list3,int pixelCount){
        mainList=list;
        orderList=list2;
        stickGraph=list3;
        pxCount=pixelCount;
        weightList=new int[mainList.length][mainList[0].length];
        displayImage=new BufferedImage(list.length,list[0].length,BufferedImage.TYPE_INT_RGB);
    }

    int weightcap=20;
    int iterationLimit=20;
    public void computeWeightList(){
        weightList=new int[mainList.length][mainList[0].length];
        populateOnes();
        advanceWeights(0);
    }

    public void advanceWeights(int iteration){
        if(iteration>iterationLimit){return;}
        int maxWeight=0;int[][]weightList2=new int[mainList.length][mainList[0].length];
        //weightList2=weightList.clone();
        int maxmaxWeight=0;
        for(int x=0;x<mainList.length;x++){
            for(int y=0;y<mainList[0].length;y++){
                weightList2[x][y]=weightList[x][y];}}
        for(int x=0;x<mainList.length;x++){
            for(int y=0;y<mainList[0].length;y++){
                maxWeight=0;
                if(mainList[x][y]&&!(weightList2[x][y]>0)){
                    try{maxWeight=weightList2[x+1][y]>maxWeight?weightList2[x+1][y]:maxWeight;}catch(Exception e){}
                    try{maxWeight=weightList2[x-1][y]>maxWeight?weightList2[x-1][y]:maxWeight;}catch(Exception e){}
                    try{maxWeight=weightList2[x][y+1]>maxWeight?weightList2[x][y+1]:maxWeight;}catch(Exception e){}
                    try{maxWeight=weightList2[x][y-1]>maxWeight?weightList2[x][y-1]:maxWeight;}catch(Exception e){}
                    if(maxWeight>0){weightList[x][y]=maxWeight+1;if(maxWeight>maxmaxWeight){maxmaxWeight=maxWeight;}}
                }
            }
        }
        if(maxmaxWeight<weightcap){advanceWeights(iteration+1);}
    }

    public void populateOnes(){
        int temp=0;
        for(int x=0;x<mainList.length;x++){
            for(int y=0;y<mainList[0].length;y++){
                if(mainList[x][y]){
                    temp=0;
                    if(stickGraph[x][y][0]){temp++;}
                    if(stickGraph[x][y][1]){temp++;}
                    if(stickGraph[x][y][2]){temp++;}
                    if(stickGraph[x][y][3]){temp++;}
                    if(temp==0){
                        weightList[x][y]=1;
                    }else{weightList[x][y]=0;}
                }
            }
        }
    }

    public int numNewPixels(){
        int temp=0;
        for(int x=0;x<orderList.length;x++){
            for(int y=0;y<orderList[0].length;y++){
                if(orderList[x][y]>1){temp++;}
            }}return temp;
    }

    public void updateImage(){
        computeWeightList();
        for(int x=0;x<mainList.length;x++){
            for(int y=0;y<mainList[0].length;y++){
                if(mainList[x][y]){
                    int hi=-1;
                    hi=orderList[x][y];
                    int brightness=weightList[x][y]>0?weightList[x][y]*20:0;
                    brightness=brightness>255?255:brightness;
                    displayImage.setRGB(x,y,new Color(brightness,brightness,brightness).getRGB());
                }else{
                    displayImage.setRGB(x,y,new Color(0,0,0).getRGB());
                }
            }
        }
        display.image=displayImage;
    }
}
