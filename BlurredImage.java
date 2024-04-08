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

    public void computeWeightList(){
        weightList=new int[mainList.length][mainList[0].length];
        populateOnes();
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
                    }
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
                    displayImage.setRGB(x,y,new Color(weightList[x][y]==1?0:255,255,255).getRGB());
                }else{
                    displayImage.setRGB(x,y,new Color(0,0,0).getRGB());
                }
            }
        }
        display.image=displayImage;
    }
}
