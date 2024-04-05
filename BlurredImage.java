import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BlurredImage{
    boolean mainList[][];
    int xWidth;int yWidth;
    BufferedImage displayImage;
    JFrameImage display;
    JFramePolygon boardOutline;
    int sizeMultiplier=128;
    int startX,startY;
    public BlurredImage(boolean[][]list,int xpos,int ypos){
        mainList=list;
        //startX=sX;startY=sY;
        startX=list.length;startY=list[0].length;
        xWidth=startX;yWidth=startY;
        
        displayImage=new BufferedImage(startX,startY,BufferedImage.TYPE_INT_RGB);
        //displayImage.setRGB(2,2,new Color(255,100,100).getRGB());
        display=new JFrameImage(xpos,ypos,startX*sizeMultiplier,startY*sizeMultiplier,0,displayImage);
        boardOutline=new JFramePolygon(new int[]{xpos,xpos+xWidth*sizeMultiplier,xpos+xWidth*sizeMultiplier,xpos}, new int[]{ypos,ypos,ypos+yWidth*sizeMultiplier,ypos+yWidth*sizeMultiplier}, new Color(100,100,100), new Color(100,100,100),10);
        mainList=list;
        //stickGraph=new boolean[startX][startY][4];
        //mainList[(int)(w/2)][(int)(h/2)]=true;
        //pixelCount+=1;
    }

    public void draw(Graphics2D g2d){
        boardOutline.draw(g2d);
        display.draw(g2d);
    }

    public void updateList(boolean[][]list){
        mainList=list;
        displayImage=new BufferedImage(list.length,list[0].length,BufferedImage.TYPE_INT_RGB);
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
