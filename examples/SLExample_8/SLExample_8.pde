
/*    Simple LiveCoding Example 8   
 *    fjenett 20080829
 *
 *    handling keys and mice
 */

background( #009988 );
noStroke();

if ( mousePressed )
{
    fill( #664400 );
    rect( random(width), random(height), 20, 20 );
}

if ( keyPressed )
{
    switch ( key )
    {
        case '1':
            fill( #004477 );
            ellipse( random(width), random(height), 20, 20 );
            break;
        case '2':
            fill( #336699 );
            ellipse( random(width), random(height), 20, 20 );
            break;
        default:
            fill( #ffffff );
            ellipse( random(width), random(height), 20, 20 );
    }
}
