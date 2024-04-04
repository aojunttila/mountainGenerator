
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class JFrameImage extends JComponent
{
  private float xpos;
  private float ypos;
  public int ogX,ogY;  
  int xScale;
  int yScale;
  Ellipse2D.Double ring2;
  BufferedImage image;
  String filepath;
  Graphics2D g1;
  int tttttt=0;
  float alpha=1;
  //AffineTransform at;

  public JFrameImage(int posx,int posy,int xWidth,int yWidth,int rotation2, BufferedImage image2)
  {
    xpos=posx;ypos=posy;
    xScale=xWidth;yScale=yWidth;
    image=image2;
    ogX=posx;ogY=posy;

    //at.setToRotation(1.5, xpos*10 + (image.getWidth()*0.1 / 2), ypos*10 + (image.getHeight()*0.1 / 2));
    //at.rotate(1);
    
    
  }

  public void draw(Graphics2D g2){
    g1 = g2;
    //at = new AffineTransform();
  
    //at.scale(xScale,yScale); 
    //at.translate(xpos*(1/xScale)+tttttt,ypos*(1/yScale));
    
    //System.out.println(""hi2""+tttttt);
    //g2.setTransform(at);
    //Toolkit.getDefaultToolkit().sync(); 
    if(alpha<1){
    BufferedImage transRender = new BufferedImage(xScale>0?xScale:image.getWidth(),yScale>0?yScale:image.getHeight(),BufferedImage.TYPE_INT_RGB);
    Graphics2D transG2 = (Graphics2D) transRender.createGraphics();
            //Graphics2D g2d = (Graphics2D) g2.create();
            transG2.setComposite(AlphaComposite.SrcOver.derive(alpha));

        // Draw the image
        //g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);

        transG2.drawImage(image,0,0,xScale>0?xScale:image.getWidth(),yScale>0?yScale:image.getHeight(),null);
    g2.drawImage(transRender,(int)xpos,(int)ypos,null);
    transG2.dispose();}else{
      g2.drawImage(image,(int)xpos,(int)ypos,xScale>0?xScale:image.getWidth(),yScale>0?yScale:image.getHeight(),null);
    }
    //Toolkit.getDefaultToolkit().sync(); 
  }

  public void nextFrame(int sidething, Graphics2D g2){
    //ring2 = new Ellipse2D.Double(sidething, 75, 250, 250);
    //g1.setColor(Color.BLACK);
    //g1.fill(ring2);
    //draw(g2);
    //ring2.x = tttttt;
  }

  public void setPos(int x,int y){xpos=x;ypos=y;}
  public void setScale(int xS,int yS){xScale=xS>0?xS:xScale;yScale=yS>0?yS:yScale;}
  public void changePos(int x,int y){xpos+=x;ypos+=y;}
  public void changePos(float x,float y){xpos+=x;ypos+=y;}
  public void changeScale(int xS,int yS){xScale=xScale+xS>1?xScale+xS:1;yScale=yScale+yS>1?yScale+yS:1;}
  public int getXPos(){return (int)xpos;}
  public void setAlpha(float alpha2){alpha=alpha2>0?(alpha2>1?1:alpha2):0;}
  public int getYPos(){return (int)ypos;}
  public int getXScale(){return xScale;}
  public int getYScale(){return yScale;}
}