import acm.graphics.GObject;
import stanford.karel.SuperKarel;
public class Homework extends SuperKarel {
    int[][] map;  // to use for coordination and drawing the solution
    int centerPointX,centerPointY, currentX =1, currentY =1, x=1,y=1,beeperCount=0,count =0;;
    public void moveAndCount(){  // this method moves carl around but all with tracking his moves across the map
        if(facingEast()) currentX++;
        else if(facingWest()) currentX--;
        else if(facingNorth()) currentY++;
        else currentY--;
        move();count++;
        System.out.println("step count = "+count);
        System.out.println("Current Location: x = "+ currentX + " y = "+ currentY);}
    public void putBeeperAndCount(){  // this method puts beepers and counts how much was used so far
     putBeeper();
     beeperCount++;
     System.out.println("Beeper count = "+beeperCount);}
    public int[][] getDimensions(){//getDimentions() method for getting the world size
        while (frontIsClear()){ moveAndCount(); x++;}
        turnLeft();
        while (frontIsClear()){ moveAndCount(); y++;}
        turnLeft();
        if(x > 6 && y > 6 ){return new int[x][y];}
        else {System.out.println("-------------- \nmap is too small\n---------------");return new int[0][0];}
    }
    public void splitMapEvenly(){ // this function will split the map into 4 equal spaces
        centerPointX = (x / 2); centerPointY = (y / 2);
        if (x % 2 == 1) { // odd x axis
            for (int i = 0; i < y; i++) {
                map[centerPointX][i] = 1;}
        } else { // even x axis
            for (int i = 0; i < y; i++) {
                map[centerPointX][i] = 1;
                map[centerPointX - 1][i] = 1;
            }}
        if (y % 2 == 1) { // odd y axis
            for (int i = 0; i < x; i++) {
                map[i][centerPointY] = 1;
            }
        } else { // even y axis
            for (int i = 0; i < x; i++) {
                map[i][centerPointY] = 1;
                map[i][centerPointY - 1] = 1;}
        }
    }
    public void drawInnerChambers() {
        if (x%2==0 && y%2==0  ){  //even x even
            int minVar = Math.min(x, y);
            int cSize = (minVar - 2) / 2;
            for (int i = centerPointY - cSize; i < centerPointY + cSize; i++) {
                map[centerPointX - cSize][i] = 1;
                map[centerPointX + cSize - 1][i] = 1;}
            for (int i = centerPointX - cSize; i < centerPointX + cSize; i++) {
                map[i][centerPointY - cSize] = 1;
                map[i][centerPointY + cSize - 1] = 1;}
        }
        else if (x%2==1 && y%2==1 ){// odd x odd
            int minVar = Math.min(x, y);
            int cSize = (minVar - 2) / 2;
            for (int i = centerPointY - cSize; i < centerPointY + cSize; i++) {
                map[centerPointX - cSize][i] = 1;
                map[centerPointX + cSize ][i] = 1;}
            for (int i = centerPointX - cSize; i <= centerPointX + cSize; i++) {
                map[i][centerPointY - cSize] = 1;
                map[i][centerPointY + cSize ] = 1;}
        }
        else if ((x%2==0 && y%2==1 )){//even x odd
            if(x>y) {
                int minVar = Math.min(x, y);
                int cSize = (minVar - 2) / 2;
                for (int i = centerPointY - cSize; i <= centerPointY + cSize; i++) {
                    map[centerPointX - cSize - 1][i] = 1;
                    map[centerPointX + cSize][i] = 1;}
                for (int i = centerPointX - cSize; i <= centerPointX + cSize; i++) {
                    map[i][centerPointY - cSize] = 1;
                    map[i][centerPointY + cSize] = 1;}
            }
            else if (y>x){
                int minVar = Math.min(x, y);
                int cSize = (minVar - 2) / 2;
                for (int i = centerPointY - cSize+1; i < centerPointY + cSize-1; i++) {
                    map[centerPointX - cSize ][i] = 1;
                    map[centerPointX + cSize-1][i] = 1;}
                for (int i = centerPointX - cSize; i < centerPointX + cSize; i++) {
                    map[i][centerPointY - cSize+1] = 1;
                    map[i][centerPointY + cSize-1] = 1;}
            }}
        else if (x%2==1 && y%2==0) {  //odd even
            if(x>y){
                int minVar = Math.min(x, y);
                int cSize = (minVar - 2) / 2;
                for (int i = centerPointY - cSize; i < centerPointY + cSize; i++) {
                    map[centerPointX - cSize+1][i] = 1;
                    map[centerPointX + cSize -1][i] = 1;}
                for (int i = centerPointX - cSize+1; i < centerPointX + cSize; i++) {
                    map[i][centerPointY - cSize] = 1;
                    map[i][centerPointY + cSize -1 ] = 1;}
            } else if (y > x) {
                int minVar = Math.min(x, y);
                int cSize = (minVar - 2) / 2;
                for (int i = centerPointY - cSize; i < centerPointY + cSize; i++) {
                    map[centerPointX - cSize][i] = 1;
                    map[centerPointX + cSize ][i] = 1;}
                for (int i = centerPointX - cSize; i < centerPointX + cSize+1; i++) {
                    map[i][centerPointY - cSize - 1] = 1;
                    map[i][centerPointY + cSize  ] = 1;}
            }}}
    public void putBeepers() {
        int minVar = Math.min(x, y);
        int cSize = (minVar - 2) / 2;
        goTo(centerPointX +1  ,y);
        System.out.println("Start drawing-------");
        //even x even
        if (x%2==0 && y%2==0){
            while((currentY!=centerPointY+cSize)){
                if (beeperOn('H') && !beepersPresent()) {putBeeperAndCount();}
                else if ((beeperOn('W'))) { go('W');putBeeperAndCount(); }
                else if ((beeperOn('E'))) { go('E');putBeeperAndCount(); }
                else if ((beeperOn('S'))) { go('S');putBeeperAndCount(); }
            }
            while (currentY!= 1 ) {
                if (beeperOn('H') && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn('W') ) { go('W');putBeeperAndCount(); } //if b on west
                else if (beeperOn('S')) {go('S');putBeeperAndCount();}
                else break;
            }
            while (currentY != y) {
                if (beeperOn('H') && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn('S')) {go('S');putBeeperAndCount();}
                else if (beeperOn('S') &&beeperOn('E')) {go('S');putBeeperAndCount();}
                else if (beeperOn('E') ) { go('E');putBeeperAndCount(); } //if b on east
                else break;
            }
            while (currentY != y) {
                if (beeperOn('H') && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn('E') ) { go('E');putBeeperAndCount(); } //if b on east
                else if (beeperOn('N')) {go('N');putBeeperAndCount();}
                else if ( beeperOn('W')) {go('W');putBeeperAndCount();}
                else break;
            }
            if (!beeperOn('W')){go('W');}
            while(true){
                if      (beeperOn('E')) { go('E');putBeeperAndCount();}
                else if (beeperOn('S')) { go('S');putBeeperAndCount();}
                else if (beeperOn('W')) { go('W');putBeeperAndCount();}
                else if (beeperOn('N')) { go('N');putBeeperAndCount();}
                else break;
            }}
        //odd x odd
        else if (x%2==1 && y%2==1) {
            while(true){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn("S")) {go('S');if(!beepersPresent())putBeeperAndCount();}
                if(currentY == 1 ){goTo(1,centerPointY+1);break;}
            }// will end at cx , 1
            while(true){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn("E")) {go('E');if(!beepersPresent())putBeeperAndCount();}
                if(currentX == x ){break;}
            }
            goTo(centerPointX +cSize +1 , centerPointY+2);
            while(true){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn("N") && !beeperOn("W")) {go('N');if(!beepersPresent())putBeeperAndCount();}
                else if (beeperOn("W")) {go('W');if(!beepersPresent())putBeeperAndCount();}
                else break;
            }
            while(currentX != centerPointX+1 ){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn("S")) {go('S');if(!beepersPresent())putBeeperAndCount();}
                else if (beeperOn("E")) {go('E');if(!beepersPresent())putBeeperAndCount();}
                else break;
            }
            while(currentX != centerPointX+cSize +2 && currentY !=centerPointY+1  ){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn("E")) {go('E');if(!beepersPresent())putBeeperAndCount();}
                else if (beeperOn("N") ) {go('N');if(!beepersPresent())putBeeperAndCount();}
                else break;
            }}
        //even x odd
        else if (x%2==0 && y%2==1) {
            if(y>x){cSize--;}
            while(true){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if ((beeperOn("W") && beeperOn("S"))) { go('W');putBeeperAndCount();go('S');putBeeperAndCount();go('E');putBeeperAndCount();go('S'); }
                else if ((beeperOn("S"))) { go('S'); }
                if (currentY == 1 ){break;}
            }
            while(true){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn("W")) {go('W');if(!beepersPresent())putBeeperAndCount();}
                if(currentY == 1 && currentX == centerPointX){break;}
            }
            goTo(1 , centerPointY+1);
            while(true){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn("E")) {go('E');if(!beepersPresent())putBeeperAndCount();}
                if(currentX == x){break;}
            }
            goTo(centerPointX +cSize +1 , centerPointY+2);
            while(true){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn("N") && !beeperOn("W")) {go('N');if(!beepersPresent())putBeeperAndCount();}
                else if (beeperOn("W")) {go('W');if(!beepersPresent())putBeeperAndCount();}
                else break;
            }
            while(true){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn("S")) {go('S');if(!beepersPresent())putBeeperAndCount();}
                else if (beeperOn("W")) {go('W');if(!beepersPresent())putBeeperAndCount();}
                else break;
            }
            while(currentX != centerPointX+cSize +2 && currentY !=centerPointY+1 ){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn("E") ) {go('E');if(!beepersPresent())putBeeperAndCount();}
                else if (beeperOn("N")) {go('N');if(!beepersPresent())putBeeperAndCount();}
                else break;
            }}
        // odd x even
        else if (x%2==1 && y%2==0) {
            if (y > x){cSize++;} // because of integer division the result comes decremented by one
            while(true){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn("S")) {go('S');if(!beepersPresent())putBeeperAndCount();}
                else break;
            }
            goTo(1 , centerPointY);
            if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
            while(true){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn("N") && beeperOn('E')) { go('N');if(!beepersPresent()){putBeeperAndCount();} go('E');if(!beepersPresent()){putBeeperAndCount();}go('S');if(!beepersPresent()){putBeeperAndCount();}go('E');if(!beepersPresent()){putBeeperAndCount();} }
                else if ((beeperOn("E"))) { go('E'); }
                else {go('N');if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}break;}
            }
            goTo(centerPointX +cSize  , centerPointY+2);
            while(true){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn("N")) {go('N');if(!beepersPresent())putBeeperAndCount();}
                else if (currentY == centerPointY + cSize){break;}
            }
            while(true){
                if (beeperOn("W")) {go('W');if(!beepersPresent())putBeeperAndCount();}
                else if(currentX == centerPointX ){go('W');break;}
                else break;

            }
            while(currentY != centerPointY - cSize){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if (beeperOn("S")) {go('S');if(!beepersPresent())putBeeperAndCount();}
                else if(currentY == centerPointY +1 ){go('S');go('S');}
                else break;
            }
            go('E');
            while(true){
                if (beeperOn("H") && !beepersPresent()) {putBeeperAndCount();}
                else if (currentY == centerPointY){break;}
                else if (beeperOn("E") ) {go('E');if(!beepersPresent())putBeeperAndCount();}
                else if (beeperOn("N")) {go('N');if(!beepersPresent())putBeeperAndCount();}
                else break;
            }}}
    public void turn(char d){
        if(d=='N')while (notFacingNorth())turnLeft();
        else if(d=='S')while (notFacingSouth())turnLeft();
        else if(d=='E')while (notFacingEast())turnLeft();
        else if(d=='W')while (notFacingWest())turnLeft();}
    public void goTo(int nextX,int nextY ){
        if(nextX> currentX)turn('E');
        else turn('W');
        while (nextX!= currentX){moveAndCount();}
        if(nextY> currentY)turn('N');
        else turn('S');
        while (nextY!= currentY){moveAndCount();}}
    public void go(char c){
        if(c == 'W' && 0 < currentX-1  && currentX-1 <= x  ){turn('W'); moveAndCount();}
        else if(c == 'E' && 0 < currentX+1  && currentX  < x  ){turn('E'); moveAndCount();}
        else if(c == 'N' && 0 < currentY+1  && currentY < y  ){turn('N'); moveAndCount();}
        else if(c == 'S' && 0 < currentY-1  && currentY-1 < y  ){turn('S'); moveAndCount();}}
    public boolean beeperOn(char c) {
        boolean R = false;
        if      (c=='W' && 0 <= currentX-2  && currentX-2 <= x  && map[currentX - 2][currentY - 1] == 1){map[currentX - 2][currentY - 1] =0;R=  true;}
        else if (c=='E' && 0 <= currentX    && currentX   < x   && map[currentX    ][currentY - 1] == 1){map[currentX    ][currentY - 1] =0;R=  true;}
        else if (c=='S' && 0 <= currentY -2 && currentY-2  <= y && map[currentX - 1][currentY - 2] == 1){map[currentX - 1][currentY - 2] =0;R=  true;}
        else if (c=='N' && 0 <= currentY    && currentY  <= y   && map[currentX - 1][currentY    ] == 1){map[currentX - 1][currentY    ] =0;R=  true;}
        else if (c== 'H' && map[currentX - 1][currentY - 1] == 1 ){map[currentX - 1][currentY - 1] =0;R = true;}
        return R;}
     public boolean beeperOn(String c) {
        boolean R = false;
        if      (c.equals("W") && 0 <= currentX-2  && currentX-2 <= x  && map[currentX - 2][currentY - 1] == 1){R=  true;}
        else if (c.equals("E") && 0 <= currentX    && currentX   < x   && map[currentX    ][currentY - 1] == 1){R=  true;}
        else if (c.equals("S") && 0 <= currentY -2 && currentY-2  <= y && map[currentX - 1][currentY - 2] == 1){R=  true;}
        else if (c.equals("N") && 0 <= currentY    && currentY  <= y   && map[currentX - 1][currentY    ] == 1){R=  true;}
        else if (c.equals("H") && map[currentX - 1][currentY - 1] == 1 ){R = true;}
        return R;}
    public void run() {
        x=1;y=1;count=0; // to start over after clicking (Start program)
        setBeepersInBag(1000);
        map = getDimensions();
        splitMapEvenly();
        drawInnerChambers ();
        for(int i =0; i< map.length;i++){
            System.out.print("[");
            for(int j =0; j<map[0].length;j++){
                System.out.print(map[i][j]+","); }
                System.out.print("]");System.out.println();}
        putBeepers();
        goTo(1,1); turn('E'); // to start over after clicking (Start program)
    }
}
