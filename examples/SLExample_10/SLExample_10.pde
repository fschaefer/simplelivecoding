size( 300, 300 );
frameRate( 12 );

fill( 0x09444444 );
noStroke();
rect( 0,0,width,height );
fill( 255 );
stroke( 0 );
smooth();

int[] arr = new int[] {
  1,2,3,4,5,6,7,8,9,10  
};


translate( width/2, height/2 );
rotate( random( TWO_PI ) );

float s = 2+random(5), d = s+random(5);

for ( int i = 0; i < arr.length; i++ )
{
    rect( arr[i]*s, arr[i]*s, arr[i]*d, arr[i]*d );
}
