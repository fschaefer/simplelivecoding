
/*    Simple LiveCoding Example 3    
 *    fjenett - 2006-05-07
 *    
 *    moving wall-paper.
 */

 size( 200, 200 );

// set val to be between 0.0 and 2.0
 float val = ( millis() / 100000.0 ) % 2.0;

// center it
 translate( width/2, height/2 );

// scale from 4.0 to -3.0
 scale( 4 - val*3.5 );
 
// rotate from 4.0 to -3.0
 rotate( frameCount / 60.0 );

// load image
 PImage img = loadImage( "anImage.jpg" );

// draw image centered
if ( img != null )
 image( img, -img.width/2, -img.height/2 );
