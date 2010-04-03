
/*    Simple LiveCoding Example 1    
 *    fjenett - 2006-05-07
 *    
 *    dark blue background and an orange line.
 */

color blueish = #002211;

background( blueish );

color orange = #FF9900;

stroke( orange );

line( random( 0, width ), random( 0, height ), random( 0, width ), random( 0, height ) );

