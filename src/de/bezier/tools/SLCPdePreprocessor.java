/**
 *		SLCPdePreprocessor
 *
 *		@author		florian jenett - mail@bezier.de
 *		@modified	##date##
 *		@version	##version##
 */

package de.bezier.tools;

import antlr.*;
import antlr.collections.AST;

import processing.app.Preferences;
import processing.app.Sketch;
//import processing.app.SketchException;

import processing.app.preproc.*;
import processing.app.debug.*;

import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SLCPdePreprocessor
{
    Reader programReader;
    SimpleLiveCoding tool;
    
    public SLCPdePreprocessor ( SimpleLiveCoding _t )
    {
    	tool = _t;
    }
    
    public String process( String s )
    {
		s = Sketch.scrubComments( s );
		//System.out.println( s );
        
		StringWriter writer = new StringWriter();
		PdePreprocessor preproc = new PdePreprocessor("slcSketchName",4); /*sketchName, tabSize*/
		preproc.setMode( PdePreprocessor.Mode.JAVA );
		try {
			PreprocessResult result = preproc.write(writer, s, null);
		/*} catch (SketchException se) {
			se.printStackTrace();*/
		} catch ( RunnerException re ) {
			re.printStackTrace();
			return null;
		} catch (RecognitionException re) {
			re.printStackTrace();
			return null;
		} catch (TokenStreamException tse) {
			tse.printStackTrace();
			return null;
		}
		
		//System.out.println( writer.toString() );
		
		// This is just a quick fix for Processing 0191,
		// will need to be updated for upcoming version(s) that have
		// modes (java/android/js) and separate preprocessors.
		String code = writer.toString();
		String[] lines = code.split("\n");
		String codeOut = "";
		for ( int i = 19, k = lines.length; i < k-6; i++ )
		{
			codeOut += lines[i] + "\n";
		}
		//System.out.println( codeOut );
		
        return bshProcess( codeOut );
    }

    String bshProcess (String s)
    {
        String s1 = "0x([0-9a-fA-F]{2})([0-9a-fA-F]{2})([0-9a-fA-F]{2})([0-9a-fA-F]{2})";
        String s2 = "((Integer.parseInt(\"$1\",16)<<24)|(Integer.parseInt(\"$2\",16)<<16)|(Integer.parseInt(\"$3\",16)<<8)| Integer.parseInt(\"$4\",16))";
        String s3 = preg_replace (s1, s2, s);
        return s3;
    }
    
    String preg_replace ( String exp, String rep, String str )
    {
    	java.util.regex.Pattern p = Pattern.compile(exp);
        Matcher m = p.matcher(str); // get a matcher object
        String out = m.replaceAll(rep);
        return out;
    }

    public void writeHeader(PrintStream printstream)
    {
    }

    public void writeFooter(PrintStream printstream)
    {
    }

    public SLCPdePreprocessor()
    {
    }
}
