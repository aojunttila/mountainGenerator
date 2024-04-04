//package Utils;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JFrameBase extends JFrame{
  Random random=new Random();int w=1500;int h=1000;int s1=1;double s=s1;
  BufferedImage i;
  int framecount=-1;
  JFrame frame;
  JPanel panel;
  Boolean mouseDown=false;
  int scrollAmount;
  int mouseX=0;
  int mouseY=0;
  JFrameCompBase comp;
  ArrayList<Float>frametimes=new ArrayList<Float>(); 
  //Canvas canvas=new Canvas(w,h);
    public JFrameBase(){
      h=(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
      w=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
      Util ut=new Util();
      i=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
      frame=new JFrame();
      panel=new JPanel();
      comp=new JFrameCompBase(panel,w,h);
      frame.setSize(w+100,h+100);
      //frame.add(comp);
      //p.getGraphics().drawLine(10,10, 100, 200);
      frame.setVisible(true);
      //setContentPane(p);setSize(w,h);setVisible(true);
      //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);setLayout(null);
      panel.setVisible(true);
      panel.setLayout(new OverlayLayout(panel));
      panel.add(comp);
      comp.paintComponent((Graphics2D)frame.getGraphics());
      //((Graphics2D)frame.getGraphics()).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      frame.add(panel);
      frame.setFocusable(true);
      frame.requestFocusInWindow();

      fullDraw();
      //run();
      ///*
      ScheduledExecutorService executor=Executors.newScheduledThreadPool(1);
      Runnable task=()->{

        if(framecount>1){ut.startTimer();}
        comp.nextFrame(mouseX,mouseY,mouseDown);comp.repaint();
        if(framecount>1){frametimes.add(ut.stopTimer(false));}
        framecount+=1;
        //System.out.println(mouseX+" "+mouseY);
        if(framecount==1){
          for(int i=0;i<frametimes.size();i++){
            float frametotal=0;
            frametotal+=frametimes.get(i);
            System.out.println(Util.colorText("Average frame time: "+(frametotal/framecount)+" ms",1,150,1));
          }
        }
      };
      executor.scheduleAtFixedRate(task,0,1000/1000,TimeUnit.MILLISECONDS);//*/

      ///*
    panel.addMouseWheelListener(new MouseWheelListener() {
      public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println(e.getWheelRotation());
        if(e.getWheelRotation()==1){scrollAmount+=e.getWheelRotation();}
        else if(s>s1){scrollAmount+=e.getWheelRotation();}
        System.out.println(s1+scrollAmount/4);
        s=s1+scrollAmount/4;

      }
    });
    panel.addMouseListener(new MouseAdapter(){
      public void mousePressed(MouseEvent e){mouseDown=true;panel.requestFocusInWindow();}
      public void mouseReleased(MouseEvent e){mouseDown=false;}});
      panel.addMouseMotionListener(new MouseAdapter(){
      public void mouseMoved(MouseEvent e){mouseX=e.getX();mouseY=e.getY();}
      public void mouseDragged(MouseEvent e){mouseX=e.getX();mouseY=e.getY();}});
      panel.addKeyListener(new KeyListener(){
      public void keyTyped(KeyEvent e){}
      public void keyPressed(KeyEvent e){
        //System.out.println(e.getKeyCode());
      }
      public void keyReleased(KeyEvent e){
        //System.out.println(e.getKeyCode());
      }
      });
      //*/
    }


    public void run(){
      long lastTime = System.nanoTime();
      final double ns = 1000000000.0 / 60.0;
      double delta = 0;
      while(true){
          long now = System.nanoTime();
          delta += (now - lastTime) / ns;
          lastTime = now;
          while(delta >= 1){
            comp.nextFrame(mouseX,mouseY,mouseDown);comp.repaint();
              delta--;
              }
          } 
     }


      private void fullDraw(){
        //for(int y=0;y<h;y++){for(int x=0;x<w;x++){
          //v=canvas.getValue(x,y);
          //if(d[v].length==3){c=new Color(d[v][0],d[v][1],d[v][2]);}
          //else{c=new Color(d[v][0]+random.nextInt(d[v][3]),d[v][1]+random.nextInt(d[v][4]),d[v][2]+random.nextInt(d[v][5]));}
          //i.setRGB(x,y,c.getRGB());

          /*
        ut.startTimer();
        comp.nextFrame();comp.repaint();
        frametimes.add(ut.stopTimer(false));
        framecount+=1;
        if(framecount==1){
          for(int i=0;i<frametimes.size();i++){
            float frametotal=0;
            frametotal+=frametimes.get(i);
            System.out.println(Util.colorText("Average frame time: "+(frametotal/framecount)+" ms",1,150,1));
          }
        }
           */
      }

    public static void main(String[]args){
      SwingUtilities.invokeLater(()->new JFrameBase());}
}