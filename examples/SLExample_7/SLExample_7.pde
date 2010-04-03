
/*    Simple LiveCoding Example 7    
 *    fjenett - 2006-12-26
 *    
 *    using time as an input.
 *    yet another clock example.
 */

size( 200, 200 );

// try this once the window is visible
// live.setTitleBarVisible( false );
live.setWindowPosition( 100, 122 );
    
background( 0x09FFFFFF );
fill( 0x99112233 );
stroke( 0 );


int w60 = width/60;
int w24 = width/24;
int h3  = height/3;

rect( w60 * second(), 0,    w60, h3 );

rect( w60 * minute(), h3,   w60, h3 );

rect( w24 * hour(),   2*h3, w24, h3 );

