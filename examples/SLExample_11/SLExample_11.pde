size( 300, 300 );

filter( BLUR, 0.65 );

colorMode(HSB); // hue, saturation, brightness
smooth();
noStroke();

for ( int i = 0; i < 5; i++ )
{
    fill( random( 255 ), 255, 255 );
    int w = random( 2, 10 );
    ellipse( random( width ), frameCount%height, w, w );
}
