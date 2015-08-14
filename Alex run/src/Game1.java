

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.JFrame;

import javax.swing.*;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;



import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;



import java.awt.Graphics;
import java.awt.image.BufferedImage;



 class spritemanager {
  
   //firesprite fire;
   
   private static hero alex;
   
   private enemy panda[];
   
   int vx;

private Artefact[] artefactarray;

private coin[] coinarray;

private sprite[] spikearray;

sprite exitdoor ;

   public spritemanager()
   {
	   
	   setAlex(new hero(30,247));
	  panda = new MyDomParser().getEnemyInfo(panda);
	  artefactarray = new MyDomParser().getArtefactInfo(artefactarray);
	  coinarray = new MyDomParser().getCoinInfo(coinarray);
	  spikearray = new MyDomParser().getSpikeInfo(spikearray);
	  
	  exitdoor =  new MyDomParser().getExitInfo(exitdoor);
	  
   }
   


public void move(int vx)
   {
	   for(int i=0;i<panda.length;i++)
	   {
	   panda[i].move(vx);	
	   if(iscollide(getAlex(),panda[i]))
	   { 
	   screencomponents.decreaselife();}
	   }
	   
	   for(int i=0;i<artefactarray.length;i++)
	   {
	   artefactarray[i].move(vx,0);	
	   if(iscollide(getAlex(),artefactarray[i]))
	   {
	   screencomponents.scoreUpdate(artefactarray[i],artefactarray[i].value);}
	   }
	   
	   for(int i=0;i<coinarray.length;i++)
	   {
		   coinarray[i].move(vx,0);	
	   if(iscollide(getAlex(),coinarray[i]))
	   {   screencomponents.scoreUpdate(coinarray[i], 50);
	       coinarray[i].collected = true;
	   }
	   }
	   for(int i=0;i<spikearray.length;i++)
	   {
	   spikearray[i].move(vx, 0);
	   if(iscollide(getAlex(),spikearray[i]))
	   screencomponents.decreaselife();
	   }
	   
	   exitdoor.move(vx, 0);
	   
	   if(iscollide(getAlex(),exitdoor))
		   MyPanel.gamestop(true);
	}
   
   public void draw(Graphics g)
   {
	
	   exitdoor.draw(g);
	    getAlex().draw(g);
	    
	   for(int i=0;i<panda.length;i++) 
	     panda[i].draw(g);
	   for(int i=0;i<artefactarray.length;i++) 
		     artefactarray[i].draw(g);
	   for(int i=0;i<coinarray.length;i++) 
		     coinarray[i].draw(g);
	   for(int i=0;i<spikearray.length;i++) 
		     spikearray[i].draw(g);
	   //spike.draw(g);
	   
	   
	   
   }
   
   public static boolean iscollide(sprite a ,sprite b)
   {
	  int x1,dx1,x2,dx2;
	  int y1,dy1,y2,dy2;
	  
	  boolean collideX,collideY;
	  
	  x1 = a.getX(true);dx1 = a.getX(false);
	  x2 = b.getX(true);dx2 = b.getX(false);
	  
	  y1 = a.getY(true);dy1 = a.getY(false);
	  y2 = b.getY(true);dy2 = b.getY(false);
	  
	  double offset = 0.3;                               // giving a 30% offset
	  
     x2 += dx2 * offset;                                   
     dx2 -= dx2 * offset;
	 
     y2 += dy2 * offset;
     dy2 -= dy2 * offset;
     
	  if((x1>x2+dx2)||(x2>x1+dx1))
		  collideX = false;
	  else
		  collideX = true;
	  
	  if((y1>y2+dy2)||(y2>y1+dy1))
		  collideY = false;
		  else
			  collideY = true;
	  
	  return (collideX&&collideY);
   }

public static hero getAlex() {
	return alex;
}

private static void setAlex(hero alex) {
	spritemanager.alex = alex;
}
   
}

// -----------------------------------------------------         sprite class -------------------------------------------


  class sprite
   {
	 private int X;
	 private int Y;
	 private int dx;
	 private int dy;
	  Image im;
	 int getX(boolean i)
	 {
		 if(i)
		return X;
		 else 
			 return dx;
	 }
	 
	 int getY(boolean i)
	 {
		 if(i)
		return Y;
		 else 
			 return dy;
	 }
	 
	 public sprite(int x,int y,int dx,int dy,String s)
	 {
		 X =x;     Y = y;   this.dx = dx; this.dy = dy;
		 im = new imagesloader().getimg("images/"+s);
	 }
	 
	 public void draw(Graphics g)
	 {
		 g.drawImage(im,X,Y,X+dx,Y+dy,0,0,dx,dy,null);
	 }
	 
	 void move(int velX,int velY)
	 {
		  X += velX;
		  Y += velY;
	 }
	 
	 void setY(int x)
	 {
		 this.Y = x;
	 }
	 
	}
 
 //---------------------------------------------------------------------------------------------------------------------

  
  
  class coin extends sprite
   {
	  int picX;
	  int picY;
	  
	  boolean movn_up;
	  
	  int height;
	  int max_height = 30;
	  
	  boolean collected = false;
	  
	  public coin(int X,int Y)
	  {
		 super(X,Y,35,35,"coin.png"); 
		 picX = picY = 0;
		 movn_up = true;
		 
		 height = 0;
	  }

	  @Override
	  public void move(int vx,int vy)  // always vy = 0
	  {
	 if(!collected)                             // if coin has not beeb yet collected move it up & dwn
		  {
		  if(movn_up)
		  {
			  super.move(vx, -1);
			  height ++;
			  if(height>=max_height)
				  movn_up = false;
		  }
		  else
		  {
			  super.move(vx, 1);
			  height --;
			  if(height<=0)
				  movn_up = true;
		  }
		}
	 else
		  {
			  super.move(vx, vy);
		  }
	  }
	  
	@Override
	public  void draw(Graphics g) {
		// TODO Auto-generated method stub
		coin_img_change();
		int x = getX(true),y = getY(true);
		int dx = getX(false),dy = getY(false);
		
		g.drawImage(im,x,y,x+dx,y+dy,picX*64,picY*64,picX*64+64,picY*64+64,null);
		
	}
	
	private void coin_img_change()
	{
		picX++;
		if(picX == 8)
		{
			picX %= 8;
		    picY ++;
		    picY %= 8;
		}
	}
	 
	 
 }
  
  //-------------------------------------------------------------------------------------------------------------------------
  
 class Artefact extends sprite
  {
	  int num_images;
	  int picY;
	  int imgX;
	  int imgY;
	  
	  int value;
	  
	  int frames;
	    int framecount=0;
	  
	  public Artefact(int X,int Y,int dx,int dy,String s,int frames,int value)
	  {
		 super(X,Y,dx,dy,"Artefacts/"+s); 
		 picY = 0;
		 num_images = 10;
		 imgX = 88;
		 imgY = 880/num_images;
		 this.frames = frames;
		 this.value = value;
	  }

	@Override
	public  void draw(Graphics g) {
		// TODO Auto-generated method stub
		img_change();
		int x = getX(true),y = getY(true);
		int dx = getX(false),dy = getY(false);
		//g.drawImage(im,x,y,x+dx,y+dy,0,0,imgX,imgY,null);
		g.drawImage(im,x,y,x+dx,y+dy,0,picY*imgY,imgX,picY*imgY+imgY,null);
		
	}
	
	private void img_change()
	{
		if(framecount == frames)
		{
		picY ++;
		picY %= num_images;
		framecount = 0;
		}
		else
			framecount++;
	}
	 
	 
}
 
 
 //--------------------------------------------------------------------------------------------------------------------------
 
 class firesprite extends sprite
 {
	 
	 int picX;
	 int img_no = 8;
	 
	 public firesprite(int X,int Y)
	 {
		super(X,Y,110,62,"lava.png"); 
		picX = 0;
	 }

	@Override
	public  void draw(Graphics g) {
		// TODO Auto-generated method stub
		firemove();
		int x = getX(true),y = getY(true);
		int dx = getX(false),dy = getY(false);
		
		g.drawImage(im,x,y,x+dx,y+dy,picX*dx,0,picX*dx+dx,dy,null);
		
	}
	
	private void firemove()
	{
		super.move(0, 0);
		picX++;
		picX %= img_no;
	}
	 
	
	 
 }
 
 //----------------------------------------------------------------------------------------------------------------------
 
 
 
 //----------------------------------------------------------------------------------------------------------------------
 
 class screencomponents
 {
	 Image score_im;
	 Image heart;
		static int scoreX = 345;
		
		   private static int score = 0;
		
		   private static int life = 3;
		   
		  static boolean reviving;
		   
		public screencomponents()
		{
			score_im = new imagesloader().getimg("images/Score.png");
			heart = new imagesloader().getimg("images/heart.png");
			reviving = false;
		}
		
		public void draw(Graphics g)
		{
			
			g.drawImage(score_im,scoreX,0,130,40,null);
			g.drawString(""+score, 420, 25);
			for(int i=1;i<=life;i++)
			g.drawImage(heart,10*i+(15*(i-1)),10,20,20,null);
		}
		
		static void scoreUpdate(final sprite gold,final int value)
		{

		    Thread t = new Thread()
		    {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					try {
						while(true)
						{
							int vx;
							int vy;
							vx = (gold.getX(true)- (scoreX+50))/10;
							vy = gold.getY(true)/10;
							
							if(vy<=0)
								break;
							
						gold.move(-vx, -vy);
						Thread.sleep(15);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gold.move(0, -100);
				//	p.print("stop");
					score += value;
				}
		    	
				
				
		    };
		    
		    t.start();
			
			}
        
		static int getscore()
		{
			return score;
		}
		
		static void decreaselife()
		{
			if(!reviving)
			{
				life--;
				hero.revivingstart();
				if(life == 0)
				{

					MyPanel.gamestop(false);
				}
				Thread t = new Thread(){
					public void run(){
						reviving = true;
						try {
							Thread.sleep(2500);
							reviving = false;
							hero.revivingend();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				
				t.start();
			}
		}
 
 }
 
 
 
 


 class single_brick extends sprite{
 int X;                                         //  dummy variables for use within fns . assigned values present in super class before usage
int Y;
private static int dx;
private static int dy;


static int offset = 195;
static int gap = 90;
Image im;

int index;
public single_brick(int index ,int x)
{
	super(x,400-offset-index*17-gap*index,100,20,"board.png");
	
	setDx(super.getX(false));
	setDy(super.getY(false));
	
	}


void move(int Vx)
{
  
  super.move(Vx, 0);
  
	}


boolean iscollide(sprite s)
{
	if(checkX(s)==1&&checkY(s)==1)           //    s.setY(this.Y-s.getY(false)-15);
		return true;
	else 
		return false;
	
	}


int checkX(sprite s)
{
	int X,dX;
	X = s.getX(true);
    dX = s.getX(false);
    
    this.X = super.getX(true);
    single_brick.setDx(super.getX(false));
    
    if(X+dX>this.X&&X<single_brick.getDx()+this.X)
    	return 1;
    else
    	return 0;
	
	}


int checkY(sprite s)
{
	int e=0,Y,dY;
	Y = s.getY(true);
	dY = s.getY(false);
	e = Y+dY ;
	
	this.Y = super.getY(true);
	single_brick.setDy(super.getY(false));
	
//	p.print("e "+e+" hero.vel2 "+hero.vel2);
	
	if((this.Y - 5 < e) && (e < this.Y+single_brick.getDy()))
	{
		//System.out.println(""+e);
		s.setY(this.Y-dY);
		return 1;
	}
	else
		return 0;
	}

public static int getDy() {
	return dy;
}

public static void setDy(int dy) {
	single_brick.dy = dy;
}

public static int getDx() {
	return dx;
}

public static void setDx(int dx) {
	single_brick.dx = dx;
}

}




class Ribbonmanager {
	  
	static int base_vel;
	
	  imagesloader iml = new imagesloader();
	  
	  private Ribbon rb1;
	  private Ribbon rb2;
	  private Ribbon rb3;
	  private Ribbon rb4;

	@SuppressWarnings("static-access")
	public Ribbonmanager(int base)
	{
	
		 this.base_vel = base;
		
	rb1 = new Ribbon(3806,609,1*base_vel,"mountain l1.png");
	rb2 = new Ribbon(3806,609,2*base_vel,"mountain l2.png");
	rb3 = new Ribbon(783,260,4*base_vel,"grassland.png");
	rb4 = new Ribbon(1500,1050,6*base_vel,"ground.png");
	
	}

	void draw(Graphics g)
	{
		rb1.draw(g);
		rb2.draw(g);
		rb3.draw(g);
		rb4.draw(g);
	}
	
	}

class Ribbon
{
	private BufferedImage img;
	volatile int Xhead = 0;
	  int Xpic ;
	  int Ypic ;
	  int vel;
	  
	  public Ribbon(int X,int Y, int v,String s)
	  {
		  Xpic = X;
			Ypic = Y;
			vel = v;
			img = new imagesloader().getimg("images/"+s);
	  }
	
	  public void draw(Graphics g)
		{
		Xhead+=vel;

		//if(f == true)
		{
		if(Xhead>Xpic)
		Xhead=0;

		if(Xhead+500>Xpic)
		{
		g.drawImage(img,0,0,Xpic-Xhead,400,Xhead,0,Xpic,Ypic,null);
		g.drawImage(img,Xpic-Xhead,0,500,400,0,0,500-(Xpic-Xhead),Ypic,null);
		}
		else
		g.drawImage(img,0,0,500,400,Xhead,0,Xhead+500,Ypic,null);
		}


		
		}

	  
	}








 class MyPanel extends JPanel
            implements Runnable
  
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	brickmanager brickmngr;
	Ribbonmanager rbn;
	spritemanager sprmgr;
	int i=0;
	Dimension D;
	
	screencomponents screen;
	
	int sleeptime;
	private static boolean game_complete = false;
	
     static volatile boolean running = true;
	
 public MyPanel()
  {
	 sleeptime = 25;  // 25
	 
	 
	 
	 this.setFocusable(true);
	 requestFocus();
	 addKeyListener( new KeyAdapter() {

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			super.keyPressed(arg0);
			spritemanager.getAlex().keyprocess();
		}

			
			 
			 } );
	 addMouseListener( new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			super.mouseClicked(arg0);
			spritemanager.getAlex().keyprocess();
		}
		 
		
		 } );
	 rbn = new Ribbonmanager(1);                                        //  argument  - base vel with which all ribbons move                     
	 brickmngr = new brickmanager();
	 sprmgr = new spritemanager();
	 screen = new screencomponents();
	 Thread t = new Thread(this);
	 t.start();
	 }




@Override
protected void paintComponent(Graphics g) {
	// TODO Auto-generated method stub
	super.paintComponent(g);
	if(running)
	{
		gameUpdate();
	gameRender(g);
	}
	else if (!game_complete)
	{
		Image im = new imagesloader().getimg("images/game_over.png");
		g.drawImage(im,0,0,500,400,null);
	}
	else
	{
		Image im = new imagesloader().getimg("images/fog.png");
		g.drawImage(im,0,0,500,400,null);
		Font f = new Font("comic sans ms", 20, 20);
		g.setFont(f);
		g.setColor(Color.white);
		g.drawString("Score : "+ screencomponents.getscore(), 200, 250);
	}
}

private void gameUpdate()
{
	brickmngr.move(-5);
	sprmgr.move(-5);
	}

private void gameRender(Graphics g)
{
	rbn.draw(g);
	brickmngr.draw(g);
	sprmgr.draw(g);
	screen.draw(g);
	}

@Override
public void run() {
	// TODO Auto-generated method stub
	while(running)
	{
		try {
			Thread.sleep(sleeptime);
			repaint();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	gameOver();
}




private void gameOver() {
	// TODO Auto-generated method stub
	
	repaint();
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.exit(0);
	
}




public static void gamestop(boolean complete) {
	// TODO Auto-generated method stub
	
		game_complete = complete;
	running = false;
	
}



}




 class MyDomParser {
  
  int getBricksInfo(single_brick brickarray[])                   // returns number of bricks
	   {
		// TODO Auto-generated method stub
		
		 int number = 0;                                                            // number of bricks
		 
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	   try {
		DocumentBuilder builder =  factory.newDocumentBuilder();
		try {
			Document doc = builder.parse("res/resources.xml");
			Node root = doc.getFirstChild();
			Element Rootelement = (Element) root;
			
			NodeList Bricks = Rootelement.getElementsByTagName("brick");
			
			
			
	for(int j=0;j<Bricks.getLength();j++)
	{
			Node Brick = Bricks.item(j);
			Element Brickelement = (Element) Brick;
			int posn , index , num;
             posn = Integer.parseInt(Brickelement.getAttribute("position"));
             index = Integer.parseInt(Brickelement.getAttribute("index"));
             num = Integer.parseInt(Brickelement.getAttribute("number"));
             
             appendbrick(posn,index,num,number,brickarray);
	           
             number += num;
	}		  
			

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return number;
	}

	private void appendbrick( int posn, int index, int num, int number,single_brick[] brickarray) {
		// TODO Auto-generated method stub
		for(int i=0;i<num;i++)
		{
			brickarray[number+i] = new single_brick(index,posn+i*single_brick.getDx());
		}
	}
	
	
	
	//-----------------------------------------------------------------------------------------------------------
	
	
  enemy[] getEnemyInfo(enemy enemyarray[])                   // returns array of enemies 
	   {
		// TODO Auto-generated method stub
		                                                          
		 
		  
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	   try {
		DocumentBuilder builder =  factory.newDocumentBuilder();
		try {
			Document doc = builder.parse("res/resources.xml");
			Node root = doc.getFirstChild();
			Element Rootelement = (Element) root;
			
			NodeList Bricks = Rootelement.getElementsByTagName("enemy");
			
			enemyarray = new enemy[Bricks.getLength()];
			
	for(int j=0;j<Bricks.getLength();j++)
	{
			Node Brick = Bricks.item(j);
			Element Brickelement = (Element) Brick;
			int posX, posY,vel,run_length;
            posX = Integer.parseInt(Brickelement.getAttribute("posX"));
            posY = Integer.parseInt(Brickelement.getAttribute("posY"));
            vel = Integer.parseInt(Brickelement.getAttribute("vel"));
            run_length = Integer.parseInt(Brickelement.getAttribute("run_length"));
            
            // 55,90,"panda/run_right.png"
            enemyarray[j] = new enemy(posX,posY,55,90,"panda/run_right.png",2,vel,run_length);                                                        // C
           
	           
           
	}		  
			

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	   return enemyarray;
	}
	
//----------------------------------------------------------------------------------------------------------------------
  
  
  Artefact[] getArtefactInfo(Artefact Artefactarray[])                   // returns array of enemies 
  {
	// TODO Auto-generated method stub 
	  
   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
  try {
	DocumentBuilder builder =  factory.newDocumentBuilder();
	try {
		Document doc = builder.parse("res/resources.xml");
		Node root = doc.getFirstChild();
		Element Rootelement = (Element) root;
		
		NodeList Bricks = Rootelement.getElementsByTagName("Artefact");
		
		Artefactarray = new Artefact[Bricks.getLength()];
		
for(int j=0;j<Bricks.getLength();j++)
{
		Node Brick = Bricks.item(j);
		Element Brickelement = (Element) Brick;
		int posX, posY,frames,value;
		String name;
       posX = Integer.parseInt(Brickelement.getAttribute("posX"));
       posY = Integer.parseInt(Brickelement.getAttribute("posY"));
       name = Brickelement.getAttribute("image");
       frames = Integer.parseInt(Brickelement.getAttribute("frames"));
       value = Integer.parseInt(Brickelement.getAttribute("value"));
      // run_length = Integer.parseInt(Brickelement.getAttribute("run_length"));
       
       // 55,90,"panda/run_right.png"
       Artefactarray[j] = new Artefact(posX,posY,50,50,name,frames,value);                                                        // C
      
          
      
}		  
		

	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
} catch (ParserConfigurationException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
 
  return Artefactarray;
}
  
  
//----------------------------------------------------------------------------------------------------------------------
  
  
  coin[] getCoinInfo(coin coinarray[])                   // returns array of enemies 
  {
	// TODO Auto-generated method stub 
	  
   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
  try {
	DocumentBuilder builder =  factory.newDocumentBuilder();
	try {
		Document doc = builder.parse("res/resources.xml");
		Node root = doc.getFirstChild();
		Element Rootelement = (Element) root;
		
		NodeList Bricks = Rootelement.getElementsByTagName("Coin");
		
		coinarray = new coin[Bricks.getLength()];
		
for(int j=0;j<Bricks.getLength();j++)
{
		Node Brick = Bricks.item(j);
		Element Brickelement = (Element) Brick;
		int posX, posY;
       posX = Integer.parseInt(Brickelement.getAttribute("posX"));
       posY = Integer.parseInt(Brickelement.getAttribute("posY"));
       
       coinarray[j] = new coin(posX,posY);                                                        // C
      
          
      
}		  
		

	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
} catch (ParserConfigurationException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
 
  return coinarray;
}

  //------------------------------------------------------------------------------------------------------------------------
  
 public sprite[] getSpikeInfo(sprite[] spikearray) {
	// TODO Auto-generated method stub
	  
  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
   try {
	 DocumentBuilder builder =  factory.newDocumentBuilder();
	  try {
		Document doc = builder.parse("res/resources.xml");
		Node root = doc.getFirstChild();
		Element Rootelement = (Element) root;
		
		NodeList Bricks = Rootelement.getElementsByTagName("Spike");
		
		spikearray = new sprite[Bricks.getLength()];
		
for(int j=0;j<Bricks.getLength();j++)
{
		Node Brick = Bricks.item(j);
		Element Brickelement = (Element) Brick;
		int posX, posY;
     posX = Integer.parseInt(Brickelement.getAttribute("posX"));
     posY = Integer.parseInt(Brickelement.getAttribute("posY"));
     
     spikearray[j] = new sprite(posX,posY,100,44,"spikes.png");                                                        // C
    
        
    
}		  
		

	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
} catch (ParserConfigurationException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

	return spikearray;
}
  
sprite getExitInfo(sprite exit)
 {
	  
	  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	   try {
		 DocumentBuilder builder =  factory.newDocumentBuilder();
		  try {
			Document doc = builder.parse("res/resources.xml");
			Node root = doc.getFirstChild();
			Element Rootelement = (Element) root;
			
			NodeList Bricks = Rootelement.getElementsByTagName("Exit");
			Node Brick = Bricks.item(0);
			Element Brickelement = (Element)Brick;
			int posn = Integer.parseInt(Brickelement.getAttribute("position"));
		    
		     
		     exit = new sprite(posn,60,500,500,"gate.png");         
			
	
			

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		return exit;
 } 
 
}




class imagesloader
{
 ImageIcon im;

 public BufferedImage getimg(String s)
 {
  ImageIcon imIcon = new ImageIcon(s);
 return makeBIM(imIcon.getImage( ), imIcon.getIconWidth( ),
 imIcon.getIconHeight( ));
 }
public imagesloader(){};

private BufferedImage makeBIM(Image im, int width, int height)
// make a BufferedImage copy of im, assuming an alpha channel
{
 BufferedImage copy = new BufferedImage(width, height,
 BufferedImage.TYPE_INT_ARGB);
 // create a graphics context
 Graphics2D g2d = copy.createGraphics( );
 // copy image
 g2d.drawImage(im,0,0,null);
 g2d.dispose( );
 return copy;
}
}





class hero extends sprite 
{
	 int picY = 0;
	 boolean jumping ;
	 boolean jumpstart;
	 int jumpflag ;
	 int jcount = 2;
	 Image jump_up;
	 Image jump_down;
	 
	 
	 int imgX = 80;
     int imgY = 149;
	 
	 int vel1 ;
	 static  int vel2;
	 
	 int initialy ;
	 
	 static boolean reviving;
	 int cnt=0;
	 
public hero(int x,int y)
{
   super(x,y-5,45,90,"hero/run.png");
   initialy = this.getY(true)+this.getY(false);
 
	jump_up = new imagesloader().getimg("images/hero/jump_up.png");
	 jump_down = new imagesloader().getimg("images/hero/jump_down.png");
	 jump_reset();
	 
	 reviving = false;
}
	
	 void move(int velX) {
		// TODO Auto-generated method stub
		super.move(-velX, 0);
	}



	 
	 
	 
	@Override
  public void draw(Graphics g) {
		cnt++;
		if(!reviving||cnt%10==0)               //  if hero is reviving to blink images
		{
			boolean isgravity = gravity();
		spritecollide();                          // check if hero collide with any other sprite
		
		if(isgravity &&!jumping)
		{
			jumping = true;
			jumpflag = 7*jcount*2;
		}
			
		if(jumpstart)
		{
			   jumping = true;
			   jumpstart = false;
		}
		
		int x = getX(true),y = getY(true);
		int dx = getX(false),dy = getY(false);
		
		if(!jumping)
		{
		hero_image_change();
		g.drawImage(im,x,y,x+dx,y+dy+7,0,picY*imgY,imgX,picY*imgY+imgY,null);
		}
		else
			jumpmovedraw(g,x,y,dx,dy);
		}
		
	}
	

	private void hero_image_change()
	{
	 picY++;
	 picY %= 15;
	}

    private boolean gravity()                                          // true if gravity is to be applied (sprite not stable)
	 {
	 	int y = this.getY(true);
		int dy = this.getY(false);
		
		if(y+dy>=initialy)
		{
			jump_reset();
			super.setY(initialy-dy);
		    return false;
		}
		else 
		{
			boolean t = brickmanager.ontopcheck(this);           // t is true if sprite on top of any brick
			if(t)
			{
                  jump_reset();
				   return false;
			   }
		}
		
		return true;
	}                                 
 	
                                               void keyprocess()
                                           {
	                                           if(jumping == false)
		                                          jumpstart = true;
                                           }
  
    void spritecollide()
    {
    	if(brickmanager.collisioncheck(this))      // hero_sprite has collided with any brick
    	{
    		if(jumping == true )
    		{
    			jumpflag = 7*jcount;               //  forceses hero  to jump down
    		}
    	}
    }
    
    
     void jumpmovedraw(Graphics g,int x,int y ,int dx,int dy)
       {
	   
	     if(jumpflag < 7*jcount)
	     {
		    super.move(0, -vel1);
		  // System.out.println(""+vel1);
		    int cntr = jumpflag/jcount;
		    g.drawImage(jump_up,x,y,x+dx,y+dy,0,cntr*154,80,cntr*154+154,null);
		    jumpflag++;
		     vel1--;
	       }
	        else 
	          {
		           super.move(0, vel2);
		     
		           if(jumpflag > 7*jcount*2)
			             jumpflag = 7*jcount;
		   
		            int cntr = (jumpflag - 7*jcount)/jcount;
		            g.drawImage(jump_down,x,y,x+dx+15,y+dy,0,cntr*150,97,cntr*150+150,null);
		            jumpflag++;
		            vel2++;
		   
	          }
	 
	       
  }
	
  void jump_reset()
  {
	   jumping = false;
	   jumpflag = 0;
	   vel1 = 18;
	   vel2 = 5;
  }

public static void revivingstart() {
	// TODO Auto-generated method stub
	reviving = true;
}

public static void revivingend() {
	// TODO Auto-generated method stub
	reviving = false;
}
	 
}



class enemy extends sprite {

	Image img_left;
	
	int picY = 0;
    int img_no = 15;  //15
	
    int imgX = 97;         //97 2340
    int imgY = 2340/img_no;
    
    int frames;
    int framecount;
    
    int vel;
    int dist = 0;
    
    boolean movin_left = false;
    
    int run_length;
    
	public enemy(int x, int y, int dx, int dy, String s,int frames , int vel ,int rl) {
		super(x, y, dx, dy, s);                   // change 
		this.frames = frames;
		this.vel = vel;
		run_length = rl;
		// TODO Auto-generated constructor stub
		
		img_left = new imagesloader().getimg("images/panda/run_left.png");
	}
   
	 void move(int vx)
	{
		 super.move(vx,0);
		 
		 if(movin_left)
		 {
			 
			 super.move(-vel, 0);
			 dist -= vel;
			 if(dist <= 0)
				 movin_left =false;
		 }
		 else
		 {
			 super.move(vel, 0);
			 dist +=vel;
			 if(dist >= run_length)
				 movin_left = true;
		 }
	}
	
	 
	 @Override
	public void draw(Graphics g)
	{
		 
		 int x = getX(true),y = getY(true);
			int dx = getX(false),dy = getY(false);
			
			if(!movin_left)
		         g.drawImage(im,x,y,x+dx,y+dy,0,picY*imgY,imgX,picY*imgY+imgY,null);
			else
				g.drawImage(img_left,x,y,x+dx,y+dy,0,picY*imgY,imgX,picY*imgY+imgY,null);
		imgchange();
	}
	
	private void imgchange()
	{
		if(framecount == frames)
		{
		picY ++;
		picY %= img_no;
		framecount = 0;
		}
		else
			framecount++;
	}
	
}



class brickmanager
{
	
	
	static single_brick brickarray[];
	
	 static int num_bricks;

	
	public brickmanager()
	{
		
		brickarray = new single_brick[50];
		
		num_bricks = new MyDomParser().getBricksInfo(brickarray);
		
	}
	
	
	void draw(Graphics g)
	{
		for(int i=0;i<num_bricks;i++)
		{
			brickarray[i].draw(g);
		}
		
		
	}
	void move(int vx)
	{
		
		for(int i=0;i<num_bricks;i++)
		{
			brickarray[i].move(vx);	
		}	
	}
	
	
 static  boolean ontopcheck(sprite s)
	{
	  int i;
		 for(i=0;i<num_bricks;i++)
		 {
			 if(brickarray[i].iscollide(s))
			 {
				 return true;
			 }
		 }
		
		 return false;
	}
  static boolean collisioncheck(sprite s)
	{
	  int i;
		 for(i=0;i<num_bricks;i++)
		 {
			 if(spritemanager.iscollide(s, brickarray[i]))
			 {
				 //p.print("collide true");
				 return true;
			 }
		 }
		 return false;
	}
}


public class Game1
{
public static void main( String[] args ){

JFrame f1 = new JFrame("Frame1");
f1.add(new Component2(true));
f1.setSize(500,400);
f1.setLocation(30,30);
f1.setVisible(true);

try {
	Thread.sleep(1000);
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

JFrame frame = new JFrame("My Game");
frame.add(new MyPanel());
frame.setVisible( true );
frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
frame.setSize(500,400);
frame.setLocation(30,30);
frame.setResizable(false);
f1.setVisible(false);

}
}                                            //end of game(

 class Component2 extends JComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6490816500115073062L;
    Image im;
	
	public Component2(boolean start)
	{
		if(start)
		im = new imagesloader().getimg("images/alex_run1.png");
		else
			im = new imagesloader().getimg("images/alex_run1.png");
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(im,0,0,null);
		//g.fillRect(0, 0, 500, 500);
	}
	
	
	
}
