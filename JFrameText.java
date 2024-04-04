import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class JFrameText extends JComponent
{
  JLabel label;
  float transX,transY;
  //int[]arrayX,arrayY;
  Color linecolor,fillcolor;
  int[]arrayX,arrayY;
  float linewidth;
  int xScale;
  int xpos,ypos;
  int yScale;
  int rotation;
  //AffineTransform at;

/*
 * 
 */
  public JFrameText(String text){
    label=new JLabel(text);
    label.setForeground(Color.WHITE);
    setLocation(100,100);
    label.setLocation(100,100);
    label.setFont(new Font("Monospaced", Font.BOLD, 16));
  }

  public void draw(Graphics2D g2){
    label.paint(g2);
    label.paintComponents(g2);
  }

  public void nextFrame(int sidething, Graphics2D g2){
    //ring2 = new Ellipse2D.Double(sidething, 75, 250, 250);
    //g1.setColor(Color.BLACK);
    //g1.fill(ring2);
    //draw(g2);
    //ring2.x = tttttt;
  }

  public void setText(String text){
    label.setText(text);
  }
  public JLabel getObject(){
    return label;
  }

}