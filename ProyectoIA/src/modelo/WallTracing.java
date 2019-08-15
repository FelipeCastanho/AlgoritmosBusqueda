package modelo;


import java.util.ArrayList;


public class WallTracing {
    int tablero[][];
    int posicionAgente[];
    int posicionMeta[];
    boolean posiblesPasos[]; // 0 - izquierda, 1 - frente, 2 - derecha, 3 - atras 
    boolean gameOver;
    int direccion;

    public WallTracing() {
        direccion = 2;
        tablero = new int[20][20];
        posicionAgente = new int[2];
        posicionMeta = new int[2];
        posiblesPasos = new boolean[4];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                tablero[i][j] = 0;
            }
        }
        posicionAgente[0] = 0; 
        posicionAgente[1] = 0;
        posicionMeta[0] = 0;
        posicionMeta[1] = 0;
        posiblesPasos[0] = false;
        posiblesPasos[1] = false;
        posiblesPasos[2] = false;
        posiblesPasos[3] = false;
        gameOver = false;
    }

    public void setTablero(int[][] tablero) {
        this.tablero = tablero;
        actualizarPosiciones();
    }

    public void setPosicionAgente(int[] posicionAgente) {
        this.posicionAgente = posicionAgente;
    }

    public void setPosicionMeta(int[] posicionMeta) {
        this.posicionMeta = posicionMeta;
    }

    public void setPosiblesPasos(boolean[] posiblesPasos) {
        this.posiblesPasos = posiblesPasos;
    }
    
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int[][] getTablero() {
        return tablero;
    }

    public int[] getPosicionAgente() {
        return posicionAgente;
    }

    public int[] getPosicionMeta() {
        return posicionMeta;
    }

    public boolean[] getPosiblesPasos() {
        return posiblesPasos;
    }
    
    public boolean getGameOver() {
        return gameOver;
    }
        
    public void actualizarPosiciones(){
         for(int i = 0; i < 20; i++){ 
            for(int j = 0; j < 20; j++){
                if(tablero[i][j] == 2){ //El agente esta representado con el numero 2
                    posicionAgente[0] = i;
                    posicionAgente[1] = j;
                }
                if(tablero[i][j] == 3){//La meta esta representada por el numero 3
                    posicionMeta[0] = i;
                    posicionMeta[1] = j;
                }
            }     
        }    
    }
    
    boolean comprobacionParedes(int x, int y){
        if(x > 0){
            if(tablero[x-1][y]==1) return true;
            if(y > 0) if(tablero[x-1][y-1]==1) return true;
            if(y < 19) if(tablero[x-1][y+1]==1) return true;
        }
        if(y > 0) if(tablero[x][y-1]==1) return true;
        if(x < 19){
            if(tablero[x+1][y]==1) return true;
             if(y > 0) if(tablero[x+1][y-1]==1) return true;
             if(y < 19) if(tablero[x+1][y+1]==1) return true;
        }
        if(y < 19) if(tablero[x][y+1]==1) return true;
        return false;
    }
    
    int[] validarPasos(){
        int filaP = posicionAgente[0];
        int columnaP = posicionAgente[1];
        int r = posicionAgente[0];
        int c = posicionAgente[1];
        if(direccion == 4){
            if(tablero[r-1][c] == 0 && comprobacionParedes(r-1, c)){
                direccion = 2;
                filaP--;
            } else if(tablero[r][c+1] == 0 && comprobacionParedes(r, c+1)){
                direccion = 4;
                columnaP++;
            } else if(tablero[r+1][c] == 0 && comprobacionParedes(r+1, c)){
                direccion = 6;
                filaP++;
            } else if(tablero[r][c-1] == 0 && comprobacionParedes(r, c-1)){
                direccion = 8;
                columnaP--;
            }
            
        }
        else if(direccion == 6){
            if(tablero[r][c+1] == 0 && comprobacionParedes(r, c+1)){
                direccion = 4;
                columnaP++;
            }else if(tablero[r+1][c] == 0 && comprobacionParedes(r+1, c)){
                direccion = 6;
                filaP++;
            } else if(tablero[r][c-1] == 0 && comprobacionParedes(r, c-1)){
                direccion = 8;
                columnaP--;
            }
            else if(tablero[r-1][c] == 0 && comprobacionParedes(r-1, c)){
                direccion = 2;
                filaP--;
            }
        }
        else if(direccion == 8){
            if(tablero[r+1][c] == 0 && comprobacionParedes(r+1, c)){
                direccion = 6;
                filaP++;
            } else if(tablero[r][c-1] == 0 && comprobacionParedes(r, c-1)){
                direccion = 8;
                columnaP--;
            } else if(tablero[r-1][c] == 0 && comprobacionParedes(r-1, c)){
                direccion = 2;
                filaP--;
            } else if(tablero[r][c+1] == 0 && comprobacionParedes(r, c+1)){
                direccion = 4;
                columnaP++;
            }
        }
        else if(direccion == 2){
            if(tablero[r][c-1] == 0 && comprobacionParedes(r, c-1)){
                direccion = 8;
                columnaP--;
            } else if(tablero[r-1][c] == 0 && comprobacionParedes(r-1, c)){
                direccion = 2;
                filaP--;
            } else if(tablero[r][c+1] == 0 && comprobacionParedes(r, c+1)){
                direccion = 4;
                columnaP++;
            }else if(tablero[r+1][c] == 0 && comprobacionParedes(r+1, c)){
                direccion = 6;
                filaP++;
            }
        } 
        int[] res = {filaP, columnaP};
        return res;
    }

    public ArrayList<int[]> Bresenham(int x0, int y0, int x1, int y1) { 
        ArrayList<int[]> puntos = new ArrayList<int[]>();
        int x, y, dx, dy, p, incE, incNE, stepx, stepy;
        dx = (x1 - x0);
        dy = (y1 - y0);
       /* determinar que punto usar para empezar, cual para terminar */
        if (dy < 0) { 
            dy = -dy; 
            stepy = -1; 
        }else {
          stepy = 1;
        }
        if (dx < 0) {  
          dx = -dx;  
          stepx = -1; 
        }else {
          stepx = 1;
        }

        x = x0;
        y = y0;
        int posicion [] = {x, y};
        if(tablero[x][y] == 1) return null;
        puntos.add(posicion);
       /* se cicla hasta llegar al extremo de la línea */
        if(dx>dy){
            p = 2*dy - dx;
            incE = 2*dy;
            incNE = 2*(dy-dx);
            while (x != x1){
                x = x + stepx;
                if (p < 0){
                p = p + incE;
                }else {
                    y += stepy;
                    p += incNE;
                }
                int posicion1 [] = {x, y};
                if(tablero[x][y] == 1) return null;
                puntos.add(posicion1);
            }
        }
        else{
            p = 2*dx - dy;
            incE = 2*dx;
            incNE = 2*(dx-dy);
            while (y != y1){
                y += stepy;
                if (p < 0){
                    p += incE;
                }else {
                    x += stepx;
                    p += incNE;
                }
                int posicion1 [] = {x, y};
                if(tablero[x][y] == 1) return null;
                puntos.add(posicion1);
          }
        }
        return puntos;
    }
    
    public int[] nextStep(){
        if(!gameOver){
            ArrayList<int[]> pasos = Bresenham(posicionAgente[0], posicionAgente[1], posicionMeta[0], posicionMeta[1]);
            if(pasos != null){
                int respuesta[] = new int[2];   
                respuesta[0] = pasos.get(1)[0];
                respuesta[1] = pasos.get(1)[1];
                tablero[respuesta[0]][respuesta[1]] = 2;
                tablero[posicionAgente[0]][posicionAgente[1]] = 0;
                posicionAgente[0] = respuesta[0];
                posicionAgente[1] = respuesta[1];
                if(posicionAgente[0] == posicionMeta[0] && posicionAgente[1] == posicionMeta[1]) gameOver = true;
                return respuesta;
            }else {
                int respuesta[] = validarPasos();
                if(respuesta[0] == posicionAgente[0] && respuesta[1] == posicionAgente[1]) respuesta = irPared();
                tablero[posicionAgente[0]][posicionAgente[1]] = 0;
                tablero[respuesta[0]][respuesta[1]] = 2;
                posicionAgente[0] = respuesta[0];
                posicionAgente[1] = respuesta[1];
                if(posicionAgente[0] == posicionMeta[0] && posicionAgente[1] == posicionMeta[1]) gameOver = true;
                return respuesta;
                
            }
        }
        if(posicionAgente[0] == posicionMeta[0] && posicionAgente[1] == posicionMeta[1]) gameOver = true;
        return null;
    }
    
    public int getDireccion(){
        return direccion;
    }

    private int[] irPared() {
        int respuesta[] = new int[2];
        respuesta[0] = posicionAgente[0];
        respuesta[1] = posicionAgente[1];
        while(respuesta[1]>0){
            if(tablero[respuesta[0]][respuesta[1]-1] == 1){
                break;
            }
            else respuesta[1]--;
        }
        ArrayList<int[]> pasos = Bresenham(posicionAgente[0], posicionAgente[1], respuesta[0], respuesta[1]);
        respuesta[0] = pasos.get(1)[0];
        respuesta[1] = pasos.get(1)[1];        
        return respuesta;
    }
}
