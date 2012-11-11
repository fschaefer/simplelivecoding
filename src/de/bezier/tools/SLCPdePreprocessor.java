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

import processing.app.*;
import processing.core.*;

import processing.mode.java.*;
import processing.mode.java.preproc.*;
import processing.mode.java.runner.*;
//import processing.app.debug.*;

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
    
    public String process( String s ) throws SketchException
    {
		//s = JavaBuild.scrubComments( s );

		StringWriter writer = new StringWriter();

		// PdePreprocessor preproc = new PdePreprocessor("slcSketchName",4); /*sketchName, tabSize*/
		// preproc.setMode( PdePreprocessor.Mode.JAVA );
		// try {
		// 	PreprocessorResult result = preproc.write(writer, s, null);
		// /*} catch (SketchException se) {
		// 	se.printStackTrace();*/
		// } catch ( RunnerException re ) {
		// 	re.printStackTrace();
		// 	return null;
		// } catch ( processing.app.SketchException se ) {
		// 	se.printStackTrace();
		// 	return null;
		// } catch (RecognitionException re) {
		// 	re.printStackTrace();
		// 	return null;
		// } catch (TokenStreamException tse) {
		// 	tse.printStackTrace();
		// 	return null;
		// }

		Sketch sketch = tool.papplet.sketch;

		String[] codeFolderPackages = new String[0];

	    PreprocessorResult result;
        PdePreprocessor preprocessor = new PdePreprocessor( sketch.getName());

		try 
		{
			result = preprocessor.write( writer, s, codeFolderPackages );
		} 
		catch (antlr.RecognitionException re) 
		{
		  int errorLine = re.getLine() - 1;
		  int errorFile = 0;
		  errorLine -= sketch.getCode(errorFile).getPreprocOffset();
		  String msg = re.getMessage();

		  if (msg.contains("expecting RCURLY")) 
		  {
		    throw new SketchException("Found one too many { characters " +
		                              "without a } to match it.",
		                              errorFile, errorLine, re.getColumn(), false);
		  }

		  if (msg.contains("expecting LCURLY")) 
		  {
		    System.err.println(msg);
		    String suffix = ".";
		    String[] m = PApplet.match(msg, "found ('.*')");
		    if (m != null) {
		      suffix = ", not " + m[1] + ".";
		    }
		    throw new SketchException("Was expecting a { character" + suffix,
		                               errorFile, errorLine, re.getColumn(), false);
		  }

		  if (msg.indexOf("expecting RBRACK") != -1) 
		  {
		    System.err.println(msg);
		    throw new SketchException("Syntax error, " +
		                              "maybe a missing ] character?",
		                              errorFile, errorLine, re.getColumn(), false);
		  }

		  if (msg.indexOf("expecting SEMI") != -1) 
		  {
		    System.err.println(msg);
		    throw new SketchException("Syntax error, " +
		                              "maybe a missing semicolon?",
		                              errorFile, errorLine, re.getColumn(), false);
		  }

		  if (msg.indexOf("expecting RPAREN") != -1) 
		  {
		    System.err.println(msg);
		    throw new SketchException("Syntax error, " +
		                              "maybe a missing right parenthesis?",
		                              errorFile, errorLine, re.getColumn(), false);
		  }

		  if (msg.indexOf("preproc.web_colors") != -1) 
		  {
		    throw new SketchException("A web color (such as #ffcc00) " +
		                              "must be six digits.",
		                              errorFile, errorLine, re.getColumn(), false);
		  }

		  throw new SketchException(msg, errorFile,
		                            errorLine, re.getColumn(), false);

		} 
		catch (antlr.TokenStreamRecognitionException tsre) 
		{
		  String mess = "^line (\\d+):(\\d+):\\s";

		  String[] matches = PApplet.match(tsre.toString(), mess);
		  
		  if (matches != null) 
		  {
		    int errorLine = Integer.parseInt(matches[1]) - 1;
		    int errorColumn = Integer.parseInt(matches[2]);

		    int errorFile = 0;
		    for (int i = 1; i < sketch.getCodeCount(); i++) {
		      SketchCode sc = sketch.getCode(i);
		      if (sc.isExtension("pde") &&
		          (sc.getPreprocOffset() < errorLine)) {
		        errorFile = i;
		      }
		    }
		    errorLine -= sketch.getCode(errorFile).getPreprocOffset();

		    throw new SketchException(tsre.getMessage(),
		                              errorFile, errorLine, errorColumn);

		  } 
		  else 
		  {
		    String msg = tsre.toString();
		    throw new SketchException(msg, 0, -1, -1);
		  }

		} 
		catch (SketchException pe) 
		{
		  throw pe;

		} 
		catch (Exception ex) 
		{
		  System.err.println("Uncaught exception type:" + ex.getClass());
		  ex.printStackTrace();
		  throw new SketchException(ex.toString());
		}

		// String code = writer.toString();
		// String[] lines = code.split("\n");
		String codeOut = "";
		// for ( int i = 0, k = lines.length; i < k; i++ )
		// {
		// 	if ( lines[i].toLowerCase().indexOf(); )
		// 	codeOut += lines[i] + "\n";
		// }

		// System.out.println( codeOut );
		// System.out.println( result );

		// codeOut = bshProcess( codeOut );

		codeOut =   "public void draw () {\n"+
				    "background( random( 200 ) );\n"+
					"}\n";
		
        return codeOut;
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
