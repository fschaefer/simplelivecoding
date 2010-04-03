
/*    Simple LiveCoding Example 2    
 *    fjenett - 2006-05-07
 *    
 *    petrol flower.
 *    the values are depending on the current system-clocks seconds.
 */

// adding size here will enforce it, meaning you 
// can't change the size of the window manually.
size( 210, 210 );

// set val to be between 0.0 and 1.0
float val = map(second(), 0, 60, 0, 1);

// center it
translate(width/2, height/2);

// scale between 4.0 and 0.5
scale( lerp( 4, 0.5, val ) );

// rotate between 0.0 to 8 * PI
rotate( lerp( TWO_PI*4, 0, val ) );

fill( lerpColor( color(0,200,0), color(255, 255, 0), val ) );
noStroke();

// draw rect
rect( -26, -26, 52, 52 );
