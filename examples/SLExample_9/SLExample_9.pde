
/*    Simple LiveCoding Example 9   
 *    fjenett 20080829
 *
 *    how to store values inbetween frames
 */
 
size( 400, 400 );

fill( (frameCount/12.0)%255, (frameCount/5.0)%255, (frameCount/33.0)%255, 3 );
noStroke();
rect( 0,0,width,height );

int wh = 1 + abs(mouseX-(width/2) + mouseY-(height/2)) / 4;
int wh2 = wh/2;

// set or get values
float x = live.getFloat("x", width/2);
float y = live.getFloat("y", height/2);
float xDir = live.getFloat("xDir", random(1,2));
float yDir = live.getFloat("yDir", random(1,2));

x += xDir;
if ( x >= width-wh2 || x <= wh2 )  xDir *= -1;

y += yDir;
if ( y >= height-wh2 || y <= wh2 ) yDir *= -1;

fill( 255 );
ellipse( x, y, wh, wh );

// store values for next frame
live.set("x",x);
live.set("y",y);
live.set("xDir",xDir);
live.set("yDir",yDir);

