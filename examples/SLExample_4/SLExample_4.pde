
/*    Simple LiveCoding Example 4    
 *    fjenett - 2006-05-07
 *    
 *    the simple live ... erm, livecoding.
 */

 size( 300, 100 );
 background( 255 );
 fill( 12 );

// set val to be between 0.0 and 1.0
 float val = ( millis() / 10000.0 ) % 1.0;

PFont fnt = loadFont( dataPath("Glypha-24.vlw") );
textFont( fnt );

String txt = "Simple LiveCoding:\nType, Save, See.";
text( txt, 20, 35 );

float nx = 20; float ny = 35;
char nl = (new String("\n")).charAt( 0 );
int idx = int(val * (txt.length()-1));

for ( int i = 0 ; i < txt.length() ; i++ )
{
    fill( #FF9900 );
    char c = txt.charAt( i );
    if ( i == idx )
        text( c, nx, ny );
    
    nx += fnt.width( c )*24;
    
    if ( c == nl ) {
        nx = 20;
        ny += 28;
    }
}
