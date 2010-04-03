
/*    Simple LiveCoding Example 5    
 *    fjenett - 2006-12-25
 *    
 *    some pattern
 */

background( #ffffFF );
noStroke();

fill( #992299 );
for ( int i=0; i < width; i+=20 )
{
    rect( i, 0, 10, height );   
}

fill( #337711 );
for ( int i=0; i < height; i+=20 )
{
    rect( 0, i, width, 10 ); 
}

fill( #FFFF99 );
ellipseMode( CORNER );
for ( int i=0; i < width*height; i+=20 )
{
    ellipse( i%width, i/width*20, 10, 10 );
}
