
/*    Simple LiveCoding Example 6    
 *    fjenett - 2006-12-26
 *    
 *    using time as an input.
 */
 
// make motor be a value between 0-59
int motor = millis() % 60;

// slowly fade background out by 
// filling it with a transparent white
//background( 0x05ffffff );
fill( 0x05ffffff );
noStroke();
rect( 0,0, width, height );

// pushMatrix and popMatrix are needed to make
// sure the transformations don't add between
// frames
stroke(0);
pushMatrix();

    translate(width/2, height/2); // to center of stage
    
    rotate( motor );
    translate( motor, 0 );
    scale( motor / 20f );
    rect( 0,0, 10, 10 );
    
popMatrix();
