/**
 *		SLCPApplet
 *
 *		@author		florian jenett - mail@bezier.de
 *		@modified	##date##
 *		@version	##version##
 */
 
package de.bezier.tools;

import java.awt.Dimension;
import java.awt.Frame;
import java.io.File;
import java.util.Hashtable;

import processing.app.*;
import processing.core.PApplet;

import com.twmacinta.util.MD5;

public class SLCPApplet
extends PApplet
{
    Editor editor;
    Sketch sketch;
    SimpleLiveCoding tool;
    
    SLCInterpreter interpreter;
    String oldHash;
    String newHash;
    String lastCode;
    boolean DEBUG;
    String currentCode;
    String bailedHash;
    
    public SLC Live;
    
    int widthO, heightO;
    boolean rendererWarning = true;

    SLCPApplet ( Editor _e, SimpleLiveCoding _t )
    { 
    	interpreter = null;
        oldHash = "";
        newHash = "";
        lastCode = "";
        DEBUG = false;
        currentCode = "";
        bailedHash = "";
        
        editor = _e;
        sketch = _e.getSketch();
        sketchChanged();
        
        tool = _t;
        
        Live = new SLC();
    }
    
    private String computeMD5 ( String s )
    {
        String s1 = "";
        try
        {
	    // Fast MD5 by Timothy W Macinta
	    // http://www.twmacinta.com/myjava/fast_md5.php
            MD5 md5 = new MD5();
            md5.Update(s);
            s1 = md5.asHex();
        }
        catch(Exception exception)
        {
            System.out.println(exception);
        }
        return s1;
    }

    public void setup ( )
    {
        size(200, 200);
        widthO = width; heightO = height;
        interpreter = new SLCInterpreter(this);
    }

    public void draw ( )
    {
    	if ( sketch == null || sketch != editor.getSketch() )
    	{
    		sketchChanged();
    	}
    	
    	if ( widthO != width && heightO != height )
    	{
    		sketchSizeChanged();
    	}
    	
        currentCode = editor.getText();
        newHash = computeMD5(currentCode);
        
        if ( !oldHash.equals(newHash) && !bailedHash.equals(newHash) && !editor.getSketch().isModified() )
        {
            if ( !interpreter.call( currentCode + "" ) )
            {
                bailedHash = newHash;
				//noLoop();
            } 
            else
            {
                oldHash = newHash;
                bailedHash = "";
                lastCode = currentCode;
            }
        } 
        else
        {
            if ( !interpreter.call( lastCode + "" ) ) {
				//System.err.println( "SLC stopped." );
				//noLoop();
			}
        }
    }
    
    private void sketchChanged ()
    {
		sketchPath = sketch.getFolder().getAbsolutePath();
		
		sketch = editor.getSketch();
		
        println( "Sketch changed to: " + sketch.getName() );
    }
    
    private void sketchSizeChanged ()
    {
        widthO = width; heightO = height;
        println( "Sketch size changed to: " + width + " , " + height );
    }

    public void size ( int i, int j )
    {
        if ( i != width || j != height )
        {
            frame.setSize( new Dimension( i, j + ( Live.titleBarVisible ? 22 : 0 ) ) );
            super.size( i, j );
            background( 125 );
        }
    }
    
    public void size ( int i, int j, String klass )
    {
    	if ( rendererWarning )
    	{
	    	println( "Sorry currently you can't change renderer." );
	    	rendererWarning = false;
	    }
	    
    	size( i, j );
    }

    public static void main ( String args[] )
    {
        PApplet.main(new String[] {
            "SLCPApplet"
        });
    }
    
    public void exit ()
    {
    	super.stop();
    	frame.hide();
    }
    
    public class SLC
    {
    	Hashtable storage;
    	
		boolean titleBarVisible = true;
    
    	SLC ()
    	{
    		storage = new Hashtable();
    	}
    	
    	public void set ( String name, boolean value )
    	{
    		storage.put( name, new Boolean(value) );
    	}
    	
    	public void set ( String name, char value )
    	{
    		storage.put( name, new Character(value) );
    	}
    	
    	public void set ( String name, int value )
    	{
    		storage.put( name, new Integer(value) );
    	}
    	
    	public void set ( String name, float value )
    	{
    		storage.put( name, new Float(value) );
    	}
    	
    	public void set ( String name, String value )
    	{
    		storage.put( name, value );
    	}
    	
    	public void set ( String name, Object value )
    	{
    		storage.put( name, value );
    	}
    	
    	public boolean getBool ( String name, boolean def )
    	{
    		Boolean b = (Boolean)storage.get( name );
    		if ( b != null )
    			return b.booleanValue();
    		else
    			return def;
    	}
    	
    	public char getChar ( String name, char def )
    	{
    		Character c = (Character)storage.get( name );
    		if ( c != null )
    			return c.charValue();
    		else
    			return def;
    	}
    	
    	public int getInt ( String name, int def )
    	{
    		Integer i = (Integer)storage.get( name );
    		if ( i != null )
	    		return i.intValue();
	    	else
	    		return def;
    	}
    	
    	public float getFloat ( String name, float def )
    	{
    		Float f = (Float)storage.get( name );
    		if ( f != null )
	    		return f.floatValue();
	    	else
	    		return def;
    	}
    	
    	public String getString ( String name, String def )
    	{
    		String s = (String)(storage.get( name ));
    		if ( s != null )
    			return s;
    		else
    			return def;
    	}
    	
    	public Object getObject ( String name, Object def )
    	{
    		Object o = storage.get( name );
    		if ( o != null )
    			return o;
    		else
    			return def;
    	}
    	
		public void setTitleBarVisible ( boolean barVisible )
		{
			barVisible = barVisible;
		
			if ( barVisible != titleBarVisible && frame != null && 
				 frame.isVisible() && frame.isDisplayable() )
			{
				frame.removeWindowListener(tool);
				
				frame.dispose();
				frame.setUndecorated( !barVisible );
				frame.setVisible(true);
				
				java.awt.Point loc = frame.getLocation();
				loc.y += 22 * (titleBarVisible ? 1 : -1);
				frame.setLocation(loc);
				
				titleBarVisible = barVisible;
				
				frame.addWindowListener(tool);
			}
		}
		
		public void setWindowPosition ( int x, int y )
		{
			if ( frame != null )
			{
				frame.setLocation( new java.awt.Point( x, y ) );
			}
		}
   	}
}
