/**
 *	<h3>SimpleLiveCoding</h3>
 *
 *	<p>
 *	Simple Live Code, a Processing IDE tool that allows to "watch"
 *	a sketch that is written in basic mode.
 *	</p>
 *
 *	<p>
 *	This tool is partly based on the "Processing Interpreter Demo" by
 *	Florian Schäfer (aka "FloHimself", florian.schaefer@gmail.com):
 *	<ul><li>
 *	<a href="http://bit.ly/bED9u1">Live Code post by FloHimself</a>
 *	</li><li>
 *	<a href="http://rzserv2.fhnon.de/~lg016586/processing/Processing_Interpreter_Demo-001.zip">His original Interpreter Demo</a>
 *	</li></ul>
 *	</p>
 *		
 *	@author		florian jenett - mail@bezier.de
 *	@modified	##date##
 *	@version	##version##
 */

package de.bezier.tools;

import processing.app.*;
import processing.app.tools.*;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import java.util.Vector;
import javax.swing.JFrame;


public class SimpleLiveCoding
implements Tool, WindowListener
{
    // TODO: add recorder for code sessions that allows to save, load and replay sessions.
    //
	
    static SLCPApplet papplet;
    
    static String version = "##version##";
    static String builton = "##date##";
    
    JFrame frame;
    boolean startupmsg;
    boolean running;
    Vector sketchHash;
    
    Editor editor;

    public SimpleLiveCoding ( )
    {
    }
    
    public String getMenuTitle ()
    {
    	return "Simple Live Coding";
    }

    public void init ( Editor _e )
    {
		editor = _e;
    }

    public void run ()
    {
        startupmsg = true;
        running = false;
        
    	if ( frame == null || !frame.isVisible() )
    	{
        	frame = new JFrame("SLC - " + version);
        	frame.setSize(new Dimension(200, 200));
        	
        	papplet = new SLCPApplet( editor, this );
        	papplet.frame = frame;
        	papplet.init();
        	frame.getContentPane().add(papplet, "Center");
        	
        	frame.addWindowListener(this);
        	//frame.addKeyListener(papplet);
        	//frame.addMouseListener(papplet);
        }
        
    	frame.show();
    	frame.setVisible( true );
    	
        boolean flag = true;
        
        sayHello();
        
        papplet.init();
        System.out.println("Simple LiveCoding: started.");
    }
    
    void halt ()
    {
    	papplet.stop();
		System.out.println("Simple LiveCoding: stopped. (@ frame "+papplet.frameCount+")");
		
        frame.hide();
    }

    public void windowOpened(WindowEvent windowevent)
    {
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowDeiconified(WindowEvent windowevent)
    {
    }

    public void windowIconified(WindowEvent windowevent)
    {
    }

    public void windowClosed(WindowEvent windowevent)
    {
    	halt();
    }

    public void windowClosing(WindowEvent windowevent)
    {
    	halt();
    }
    
    private void sayHello()
    {
        if ( startupmsg )
        {
	    System.out.println("");
	    System.out.println("Simple Live Coding - version "+version+" - built "+builton);
	    System.out.println("fjenett - 2008-2011 - https://github.com/fjenett/simplelivecoding");
	    System.out.println("");
	    System.out.println("Use in \"basic programming mode\" only, see:");
	    System.out.println("http://processing.org/reference/environment/index.html#Programming_modes");
	    System.out.println("");
	    System.out.println("Current Renderer: " + papplet.g.getClass().getName());
	    System.out.println("");
	    System.out.println("Additional API:");
	    System.out.println("\tLive.setTitleBarVisible( boolean ) // show, hide window title bar");
	    System.out.println("\tLive.setWindowPosition( int, int ) // move window on screen");
	    System.out.println("");
	    startupmsg = false;
	}
    }
}
