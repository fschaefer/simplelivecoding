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
import processing.app.preproc.*;

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
    
    public String process(String s)
    {
        if(s.length() > 0 && s.charAt(s.length() - 1) != '\n')
            s = s + '\n';
        Sketch.scrubComments(s);
        
        if( Preferences.getBoolean("preproc.substitute_unicode") )
        {
            char ac[] = s.toCharArray();
            int i = 0;
            for(int j = 0; j < ac.length; j++)
                if(ac[j] > '\177')
                    i++;

            if(i != 0)
            {
                int k = 0;
                char ac1[] = new char[ac.length + i * 5];
                for(int l = 0; l < ac.length; l++)
                    if(ac[l] < '\200')
                        ac1[k++] = ac[l];
                    else
                    if(ac[l] == '\240')
                    {
                        ac1[k++] = ' ';
                    } else
                    {
                        char c = ac[l];
                        ac1[k++] = '\\';
                        ac1[k++] = 'u';
                        char ac2[] = Integer.toHexString(c).toCharArray();
                        for(int i1 = 0; i1 < 4 - ac2.length; i1++)
                            ac1[k++] = '0';

                        System.arraycopy(ac2, 0, ac1, k, ac2.length);
                        k += ac2.length;
                    }

                s = new String(ac1, 0, k);
            }
        }
        programReader = new StringReader(s);
        PdeLexer pdelexer = new PdeLexer(programReader);
        pdelexer.setTokenObjectClass("antlr.CommonHiddenStreamToken");
        PdePreprocessor.filter = new TokenStreamCopyingHiddenTokenFilter(pdelexer);
        TokenStreamCopyingHiddenTokenFilter tokenstreamcopyinghiddentokenfilter = PdePreprocessor.filter;
        tokenstreamcopyinghiddentokenfilter.hide(146);
        tokenstreamcopyinghiddentokenfilter.hide(147);
        tokenstreamcopyinghiddentokenfilter.hide(145);
        tokenstreamcopyinghiddentokenfilter.copy(45);
        tokenstreamcopyinghiddentokenfilter.copy(77);
        tokenstreamcopyinghiddentokenfilter.copy(78);
        tokenstreamcopyinghiddentokenfilter.copy(73);
        tokenstreamcopyinghiddentokenfilter.copy(74);
        tokenstreamcopyinghiddentokenfilter.copy(75);
        tokenstreamcopyinghiddentokenfilter.copy(48);
        tokenstreamcopyinghiddentokenfilter.copy(47);
        tokenstreamcopyinghiddentokenfilter.copy(83);
        PdeRecognizer pderecognizer = new PdeRecognizer(tokenstreamcopyinghiddentokenfilter);
        pderecognizer.setASTNodeClass("antlr.ExtendedCommonASTWithHiddenTokens");
        try
        {
            pderecognizer.pdeProgram();
        }
        catch ( Exception exception )
        {
        	tool.editor.statusError( exception );
            exception.printStackTrace();
            return null;
        }
        ASTFactory astfactory = new ASTFactory();
        AST ast = pderecognizer.getAST();
        AST ast1 = astfactory.create(0, "AST ROOT");
        ast1.setFirstChild(ast);
        CommonAST.setVerboseStringConversion(true, pderecognizer.getTokenNames());
        PdeEmitter pdeemitter = new PdeEmitter();
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        PrintStream printstream = new PrintStream(bytearrayoutputstream);
        writeHeader(printstream);
        pdeemitter.setOut(printstream);
        try
        {
            pdeemitter.print(ast1);
        }
        catch (Exception exception)
        {
        	tool.editor.statusError( exception );
            exception.printStackTrace();
            return null;
        }
        writeFooter(printstream);
        printstream.close();
        return bshProcess(bytearrayoutputstream.toString());
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
