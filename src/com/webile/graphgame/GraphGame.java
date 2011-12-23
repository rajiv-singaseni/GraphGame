package com.webile.graphgame;
//kasani chinna- rvrjcce 9948204569

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

public class GraphGame extends JFrame implements MouseListener,ActionListener
{
	final static int width=7;
	Vertex ListVertex=null;
	Vertex StartVertex=null;
	Vertex EndVertex=null;
	Edge ListEdge=null;
	private JLabel statusBar;
	boolean player1Turn;
	static int noOfShortMoves;
	static int noOfCutMoves;
	//static int noOfMoves;
	JLabel movesLabel = new JLabel("no of moves");
	JLabel turnLabel = new JLabel("sample");
	final static int MAX = 1000;
	Timer timer ;
	JLabel timerLabel = new JLabel("timer ");
	JPanel gamePanel;
	JPanel infoPanel;
	int secCount=0;
	int minCount=0;
	boolean player2Human = true;
	//boolean gameOver=false;

	// multiple variables
	public boolean tempPlayer1Human;
	public boolean player1Human;
	public final boolean defaultPlayer1Human = false;

	public String player1Name = new String();
	public final String defaultPlayer1Name=new String("Short");
	public String tempPlayer1Name = new String();

	public String player2Name = new String();
	public final String defaultPlayer2Name= new String("Cut");
	public String tempPlayer2Name = new String();

	public boolean music;
	public final boolean defaultMusic=true;
	public boolean tempMusic;

	public boolean shortStarts;
	public final boolean defaultShortStarts = false;
	public boolean tempShortStarts;

	public int difficulty;
	public final int defaultDifficulty = 2;
	public int tempDifficulty;

	Preference P;

	final String aboutString = new String("Graph game devloped by \n S.R.Narayana \n K.S.N.Murthy\nfor Cissorie2k7");
	final String topicsString = new String("GraphGame\nThis is a two player game. the first named Short tries to join the edges\n"+
	"which are marked green by marking them and the \nsecond player cut tries to remove disallowing short to win\nEnjoy:-)");

// functions

	void autoSet()
	{
		Edge clickedEdge = null;
		ListVertex.retrieval();
		StartVertex.gameTraversal(null);
		Edge mustEdge=null;
		mustEdge=EndVertex.findMustEdge();
		if(mustEdge != null)
		{
			System.out.println("must Edge found");
			clickedEdge = mustEdge;
		}
		else
		{
			Edge tempEdge = EndVertex.edgeLeastInDegree();
			clickedEdge = tempEdge;
		}
		if(clickedEdge !=null)
		{
			// short's turn
			if(clickedEdge.bState == true)  //ALREADY PERMANENTLY MARKED
			{

				statusBar.setText("invalid move");
				//System.exit(0);
				return ;
			}
			else
			{
				clickedEdge.markEdge();
				statusBar.setText("valid move");
				player1Turn = false;
				this.repaint();
				commonShort();
			}
		}
		else
		{
			repaint();
			player1Turn = true;
			statusBar.setText("invalid move ");
			return;
		}
	}// end of auto set
  // begining of getting the vertices from the VertexList file.

	void getVertices() throws FileNotFoundException
	{
		try{
		String st=new String("assets/VertexList.txt");
		int tempx,tempy,Vid,x;
		x=0;
		FileInputStream fis=new FileInputStream(st);
		Vid=1;
		do{
		tempx=0;
		tempy=0;
		try{ do{

		    x=fis.read();
		    if((char)x!='-')
		    {
			x=x-'0';
		    	tempx=tempx*10+x;
			}
		    else break;
		}while(true);

		do{

    		    x=fis.read();
		    if((char)x==';'||(char)x=='*') break;
		    {
			x=x-'0';
			tempy=tempy*10+x;
		    }
		   }while(true);
		}
		catch(Exception E){ }
		ListVertex=new Vertex(ListVertex,Vid,tempx,tempy);
		if(ListVertex.next==null) StartVertex=ListVertex;
		if((char)x=='*') { EndVertex=ListVertex; return; }
		Vid++;
		}while(true);

		} catch(FileNotFoundException F)
		{
			System.out.print("file not found");
			System.exit(0);
		}

	}//end of getVertices();

	// begining of getting the Edge from the EdgeList file.
	void getEdges() throws FileNotFoundException
	{
		try {
		String st=new String("assets/EdgeList.txt");
		int vertex1,vertex2;
		char Eid;

		FileInputStream fis=new FileInputStream(st);
		int x=0;
		Eid='a';
		do{
		vertex1=0;
		vertex2=0;
		try {
		do{
		    x=fis.read();
		    if((char)x!='-')
		    {
			x=x-'0';
		    	vertex1=vertex1*10+x;
			}
		    else break;
		}while(true);

		do{

		    x=fis.read();
		    if((char)x==';'||(char)x=='*') break;
		    {
			x=x-'0';
			vertex2=vertex2*10+x;
		    }
		   }while(true);
		}
		catch(Exception E){ }
	// call an Edge constructor which creates an Edge joining vertex1 and vertex2.

		ListEdge=new Edge(ListEdge,Eid,ListVertex,vertex1,vertex2);

		if((char)x=='*') return;
			Eid++;
		}while(true);
		} // end of try block 1 which checks the existence of the file
		catch(FileNotFoundException F) // catch block for the try block 1
		{
			System.out.print("EdgeList.txt file not found");
			System.exit(0);
		}

	}
//end of getEdges();

//mouse listener events.

	public void mousePressed(MouseEvent event){	}
	public void mouseReleased(MouseEvent event){    }
	public void mouseEntered(MouseEvent event){     }
	public void mouseExited(MouseEvent event){	}

	public void paint(Graphics G)
	{

		super.paint(G);
		this.setBackground(Color.WHITE);
		Vertex Vtemp=ListVertex;
		Edge tempEdge=ListEdge;

	// Displaying the Vertices.
		while(Vtemp!=null)
		{
			G.drawOval(Vtemp.iPosx-width/2,Vtemp.iPosy-width/2,width,width);
		Vtemp=Vtemp.next;
		}

	//Displaying the Edges.
		while(tempEdge!=null)
		{
			if(tempEdge.bState==true)
				G.setColor(Color.GREEN);
			else
				G.setColor(Color.BLUE);

G.drawLine(tempEdge.V1.iPosx,tempEdge.V1.iPosy,tempEdge.V2.iPosx,tempEdge.V2.iPosy);
			tempEdge=tempEdge.next;
		}
		// Highlighting the Start and End Vertices
		G.setColor(Color.GREEN);
		G.fillOval(StartVertex.iPosx-width/2,StartVertex.iPosy-width/2,width,width);
		G.fillOval(EndVertex.iPosx-width/2,EndVertex.iPosy-width/2,width,width);
	}
	void destroy()
	{
		if(player1Turn==true)
		{
			statusBar.setText("Player1 won , path formed");
		}
		else
		{
			statusBar.setText("Player2 won, path cannot be formed");
		}
		removeMouseListener(this);
	}
	public static void main(String[] args)
	{
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
               createAndShowGUI();
           }
    	});
    }// end of main()*/

	JMenuBar createMenuBar()
	{
		final JMenuBar menuBar;
		final JMenu fileMenu,helpMenu;
		final JMenuItem NewGame,LoadGame,Options,Difficulty,SaveGame,Exit,Undo,About,Topics;
		JRadioButtonMenuItem rbMenuItem;
        //Create and set as the menu bar.
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		//Build the first menu.
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.getAccessibleContext().setAccessibleDescription("The file menu");
		menuBar.add(fileMenu);
        //a group of JMenuItems
		NewGame = new JMenuItem("New Game",KeyEvent.VK_N);
		//menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		NewGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		NewGame.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		NewGame.setActionCommand("newgame");
		NewGame.addActionListener(this);
		fileMenu.add(NewGame);
        LoadGame = new JMenuItem("Load Game");
		//fileMenu.add(LoadGame);
	    SaveGame = new JMenuItem("Save Game");
		SaveGame.setMnemonic(KeyEvent.VK_S);
		//fileMenu.add(SaveGame);
        //fileMenu.addSeparator();
		Undo = new JMenuItem("Undo");
		//fileMenu.add(Undo);
		Undo.setEnabled(false);
		Options = new JMenuItem("Options");
		fileMenu.add(Options);
		Exit = new JMenuItem("Exit");
		fileMenu.add(Exit);
        //Build second menu in the menu bar.
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.getAccessibleContext().setAccessibleDescription(
				                "This menu shows help");
		menuBar.add(helpMenu);

		Topics=new JMenuItem("Topics");
		helpMenu.add(Topics);
		Topics.addActionListener(
						new ActionListener() {
									public void actionPerformed(ActionEvent ae)
									{
										JOptionPane.showMessageDialog(Topics,topicsString);
								}
						  }
						  );
		About = new JMenuItem("About");
		helpMenu.add(About);
		About.addActionListener(
										new ActionListener() {
													public void actionPerformed(ActionEvent ae)
													{
														JOptionPane.showMessageDialog(About,aboutString);
												}
										  }
						  );

		addWindowListener( new WindowAdapter()
			{
				public void windowClosing(WindowEvent we)
				{
					System.exit(0);
				}
			});
	// Adding the Action Listeners to the Menu Items


		Exit.addActionListener(
						new ActionListener() {
							public void actionPerformed(ActionEvent AE) { System.exit(0); }
								  }
								  );

		Options.setActionCommand("options");
		Options.addActionListener(this);
		return menuBar;
	}
	public static void createAndShowGUI()
	{
			GraphGame A=new GraphGame();
			A.play();
	}
	public GraphGame()
	{
		super("Graph Game - WebileApps");
		P = new Preference(this);
		statusBar= new JLabel();
		getContentPane().add(statusBar,BorderLayout.SOUTH);
		timer = new Timer(999,this);
       	setSize(470,350);
		infoPanel = createInfoPanel();
		this.getContentPane().add(infoPanel,BorderLayout.NORTH);
		setVisible(true);
		setResizable(false);
		setJMenuBar(createMenuBar());

		player1Human = defaultPlayer1Human;
		player1Name = defaultPlayer1Name;
		player2Name = defaultPlayer2Name;
		player1Turn = shortStarts;

		music = defaultMusic;
		shortStarts = defaultShortStarts;
		difficulty= defaultDifficulty;

	} // End of the Constructor

	public void actionPerformed(ActionEvent e)
	{
		//System.out.println("action command recieved is "+e.getActionCommand());
		if("options".equals(e.getActionCommand()))
		{
			timer.stop();
			P.setVisible(true);
			if(P.closedState==0)
			{
				player1Human=defaultPlayer1Human;
				player1Name=defaultPlayer1Name;
				player2Name=defaultPlayer2Name;
				music=defaultMusic;
				shortStarts=defaultShortStarts;
				player1Turn = shortStarts;
				removeMouseListener(this);
				//difficulty=defaultDifficulty;
				play();
			}
			else if(P.closedState==1)
			{
				player1Human=tempPlayer1Human;
				player1Name=tempPlayer1Name;
				player2Name=tempPlayer2Name;
				music=tempMusic;
				shortStarts=tempShortStarts;
				player1Turn = shortStarts;
				removeMouseListener(this);
				//difficulty=tempDifficulty;
				play();
			}
			else timer.start();//do nothing when cancel is pressed
		}
		else if("newgame".equals(e.getActionCommand()))
		{
				removeMouseListener(this);
				System.out.println(" entered new game action listener");
				timer.stop();
				play();
		}
		else
		{// null for timer event
			timer.start();
			timerLabel.setText(getTimeString());
		}
	}

	String getTimeString()
	{
		secCount++;
		if(secCount==60)
		{
			minCount++;
			secCount=0;
		}
		String temp = null;
		if(minCount>9)
		temp = new String(minCount+":");
		else temp = new String("0"+minCount+":");
		if(secCount>9)
		temp = temp+secCount;
		else temp = temp+"0"+secCount;
		return temp;
	}// end of gettimestring

	JPanel createInfoPanel()
	{
		JPanel infoPanel = new JPanel(new GridLayout(1,0));
		turnLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		timerLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		movesLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		infoPanel.add(turnLabel);
		infoPanel.add(timerLabel);
		infoPanel.add(movesLabel);
		return infoPanel;
	}
	void commonShort()
	{
		//checking game status

		noOfShortMoves++;
		ListVertex.retrieval();
		StartVertex.shortTraversal(null);
		if(EndVertex.tDistance < MAX)
		{
			turnLabel.setText(player1Name +"wins");
			movesLabel.setText("Game Over");
			player1Turn=true;
			//statusBar.setText("Short has won the game");
			destroy();
		}
		else
		{
			player1Turn=false;
			movesLabel.setText("No of moves"+noOfCutMoves);
			turnLabel.setText(player2Name +"'s turn");
			//statusBar.setText("Cut's turn");

	     }
	     if(player2Human == false && player1Turn==false)
		 {
		 		autoCut();
		 }
	}//end of common short

	void commonCut()
	{
		//checking game over
		noOfCutMoves++;
		ListVertex.retrieval();
		StartVertex.cutTraversal(null);
		if(EndVertex.tDistance == MAX )
		{
			turnLabel.setText(player2Name+" wins");
			movesLabel.setText("Game Over");
			//statusBar.setText("Cut has won the game ");
			player1Turn=false;
			destroy();
		}
		else
		{
			player1Turn =true;
			movesLabel.setText("No of moves"+noOfShortMoves);
			turnLabel.setText(player1Name+"'s turn");
			//statusBar.setText("Short's turn");
		}
		if(player1Human == false && player1Turn==true)
		{
			autoSet();
		}
		else ; // leave to mouse eavesdropper..
	}//end of common cut

	void autoCut()
	{// this function is not really called
		 System.out.println("called autoCut!!!!!!");
		 //commonCut();
	}

	public void play()// from concluded game
	{
		ListVertex=null;
		ListEdge=null;
		StartVertex=null;
		EndVertex=null;
		noOfShortMoves=0;
		noOfCutMoves=0;
		minCount=0;
		secCount=0;
		player1Turn = shortStarts;

		this.addMouseListener(this);
		try { getVertices(); } catch (FileNotFoundException B){ }
		try { getEdges(); } catch (FileNotFoundException F){ }

		this.repaint();
		timer.start();
		//default intialisations

		if( shortStarts == true )
		{
			turnLabel.setText(player1Name+"'s turn");
			movesLabel.setText("no of moves 0");
			statusBar.setText(player1Name+"'s turn");
			if( player1Human == false && player1Turn == true)
			autoSet();

		}
		else //cutStarts
		{
			turnLabel.setText(player2Name+"'s turn");
			movesLabel.setText("no of moves 0");
			statusBar.setText(player2Name+"'s turn");
			if( player2Human == false && player1Turn == false)
			autoCut();
			else ;
		}
	}// end of play();

	//mouse listener events.
	public void mouseClicked(MouseEvent event)
	{
		if(player1Turn == true && player1Human == true)
		{
			setter(event.getX(),event.getY());
		}
		else if(player1Turn==false && player2Human == true)
		{
			cutter(event.getX(),event.getY());
		}

	}
	void cutter(int tempx,int tempy)
	{
		//if(gameOver==true) removeMouseListener(this);

		// Getting the EDGE WHERE THE MOUSE IS CLICKED !
		Edge clickedEdge=null;
		clickedEdge=ListEdge.IsInRange(tempx,tempy);
		// CHECKING THE VALIDITY OF THE EDGE OBTAINED, whether there is an edge or not !
		if(clickedEdge!=null)
		{
		    //player2's turn
		    if(clickedEdge.bState==true)  //ALREADY PERMANENTLY MARKED
			{
				statusBar.setText("invalid move already marked");
				if(music==true)
					Toolkit.getDefaultToolkit().beep();
				return ;
			}

			ListEdge=ListEdge.removeEdge(clickedEdge);
			this.repaint();
			commonCut();
		}
		else
		{
			repaint();
			player1Turn=false;
			statusBar.setText("invalid move no edge there");
			if(music==true)
				Toolkit.getDefaultToolkit().beep();
			return ;// leave to the mose eavesdropper.
		}
	}// end of cutter

	void setter(int tempx,int tempy)
	{
		// Getting the EDGE WHERE THE MOUSE IS CLICKED !
		Edge clickedEdge=null;
		clickedEdge=ListEdge.IsInRange(tempx,tempy);
		if(clickedEdge !=null)
		{
			if(clickedEdge.bState == true)  //ALREADY PERMANENTLY MARKED
			{
				statusBar.setText("invalid move,already marked");
				if(music==true)
					Toolkit.getDefaultToolkit().beep();
				return ;
			}
			else
			{

				clickedEdge.markEdge();
				statusBar.setText("valid move");
				player1Turn = false;
				this.repaint();
				commonShort();
			}
		}
		else
		{
			repaint();
			player1Turn = true;
			statusBar.setText("invalid move ");
			if(music==true)
				Toolkit.getDefaultToolkit().beep();
			return;
		}
	}//end of setter function

}// end of class GraphGame.