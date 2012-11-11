/**
 *		SLCInterpreter
 *
 *		@author		florian jenett - mail@bezier.de
 *		@modified	##date##
 *		@version	##version##
 */
 
package de.bezier.tools;

import bsh.EvalError;
import bsh.Interpreter;
import java.io.PrintStream;
import processing.app.Editor;

public class SLCInterpreter
{
    SLCPdePreprocessor preproc;
    SLCPApplet parent;
    Interpreter bip;

    SLCInterpreter( SLCPApplet slcpapplet )
    {
        parent = null;
        bip = null;
        
        preproc = new SLCPdePreprocessor( slcpapplet.tool );
        parent = slcpapplet;
        init();
    }

    void init()
    {
        try
        {
            bip = new Interpreter();
            bip.eval("import processing.core.*;");
            bip.set("__p__", parent);
            bip.eval("importObject(__p__);");
        }
        catch(EvalError _ex)
        {
        	_ex.printStackTrace();
        }
    }

    public boolean call( String s )
    {
        s = preproc.process(s);
        
        if ( s == null || s.equals("") ) return false;
			
        init();
        
        try
        {
            bip.eval(s);
        }
        catch ( EvalError evalerror )
        {
        	parent.editor.statusError( evalerror );
        	evalerror.printStackTrace();
            //System.out.println(evalerror);
            //parent.editor.error(evalerror);
            return false;
        }
        
        return true;
    }
}
