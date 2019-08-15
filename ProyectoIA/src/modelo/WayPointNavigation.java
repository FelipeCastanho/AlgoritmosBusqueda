package modelo;

import java.util.ArrayList;

public class WayPointNavigation {
    String tablero[][];
    ArrayList<ArrayList<String>> caminos;
    int posicionAgente[];
    int posicionMeta[];
    boolean gameOver;
    String camaraMeta;
    String camaraActual;

    public WayPointNavigation() {
        tablero = new String[20][20];
        caminos = new ArrayList<ArrayList<String>>();
        posicionAgente = new int[2];
        posicionMeta = new int[2];
        posicionAgente[0] = 0; 
        posicionAgente[1] = 0;
        posicionMeta[0] = 0;
        posicionMeta[1] = 0;
        gameOver = false;
        camaraMeta = "";
        camaraActual = "";
    }

    public String[][] getTablero() {
        return tablero;
    }

    public void setTablero(String[][] tablero) {
        this.tablero = tablero;
        actualizarPosiciones();
        camaraMeta = buscarCamara(posicionMeta);
        camaraActual = buscarCamara(posicionAgente);
    }

    public ArrayList<ArrayList<String>> getCaminos() {
        return caminos;
    }

    public void setCaminos(ArrayList<ArrayList<String>> caminos) {
        this.caminos = caminos;
    }

    public int[] getPosicionAgente() {
        return posicionAgente;
    }

    public void setPosicionAgente(int[] posicionAgente) {
        this.posicionAgente = posicionAgente;
    }

    public int[] getPosicionMeta() {
        return posicionMeta;
    }

    public void setPosicionMeta(int[] posicionMeta) {
        this.posicionMeta = posicionMeta;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
     public void actualizarPosiciones(){
         for(int i = 0; i < 20; i++){ 
            for(int j = 0; j < 20; j++){
                if(tablero[i][j].equals("2")){ //El agente esta representado con el numero 2
                    posicionAgente[0] = i;
                    posicionAgente[1] = j;
                }
                if(tablero[i][j].equals("3")){//La meta esta representada por el numero 3
                    posicionMeta[0] = i;
                    posicionMeta[1] = j;
                }
            }     
        }    
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
        if(tablero[x][y].equals("1")) return null;
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
                if(tablero[x][y].equals("1")) return null;
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
                if(tablero[x][y].equals("1")) return null;
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
                tablero[respuesta[0]][respuesta[1]] = "2";
                tablero[posicionAgente[0]][posicionAgente[1]] = "0";
                posicionAgente[0] = respuesta[0];
                posicionAgente[1] = respuesta[1];
                if(posicionAgente[0] == posicionMeta[0] && posicionAgente[1] == posicionMeta[1]){
                    gameOver = true;
                }
                return respuesta;
            }else {
                //String camaraActual = buscarCamara(posicionAgente);
                String siguienteCamara = siguienteCamara(camaraActual, camaraMeta);
                int[] posicionCorte = buscarCorte(camaraActual, siguienteCamara);
               
                ArrayList<int[]> pasosCamara = Bresenham(posicionAgente[0], posicionAgente[1], posicionCorte[0], posicionCorte[1]);
                if(pasosCamara != null){
                    int respuestaCamara[] = new int[2];   
                    respuestaCamara[0] = pasosCamara.get(1)[0];
                    respuestaCamara[1] = pasosCamara.get(1)[1];
                    String aux = tablero[respuestaCamara[0]][respuestaCamara[1]];
                    tablero[respuestaCamara[0]][respuestaCamara[1]] = "2";
                    tablero[posicionAgente[0]][posicionAgente[1]] = camaraActual;
                    camaraActual = aux;
                    posicionAgente[0] = respuestaCamara[0];
                    posicionAgente[1] = respuestaCamara[1];
                    if(posicionAgente[0] == posicionMeta[0] && posicionAgente[1] == posicionMeta[1]) gameOver = true;
                    return respuestaCamara;
                }
                else{
                     //System.out.println(posicionAgente[0] + " " + posicionAgente[1] + " -- "+posicionCorte[0] + " "+posicionCorte[1] + " -- "+ siguienteCamara);
                    int[] nuevoPunto = buscarPuntoOpcional(posicionAgente, posicionCorte, camaraActual);
                    if(nuevoPunto == null){
                        //Aquiiiii la solucioooooooooon
                        nuevoPunto = centroCamara(camaraActual);
                    }
                    ArrayList<int[]> pasosOpcional = Bresenham(posicionAgente[0], posicionAgente[1], nuevoPunto[0], nuevoPunto[1]); 
                    int respuestaOpcional[] = new int[2];
                    respuestaOpcional[0] = pasosOpcional.get(1)[0];
                    respuestaOpcional[1] = pasosOpcional.get(1)[1];
                    String aux = tablero[respuestaOpcional[0]][respuestaOpcional[1]];
                    tablero[respuestaOpcional[0]][respuestaOpcional[1]] = "2";
                    tablero[posicionAgente[0]][posicionAgente[1]] = camaraActual;
                    camaraActual = aux;
                    posicionAgente[0] = respuestaOpcional[0];
                    posicionAgente[1] = respuestaOpcional[1];
                    if(posicionAgente[0] == posicionMeta[0] && posicionAgente[1] == posicionMeta[1]) gameOver = true;
                    return respuestaOpcional;     
                }
                
            }
        }
        if(posicionAgente[0] == posicionMeta[0] && posicionAgente[1] == posicionMeta[1]) gameOver = true;
        return null;
    }

    private String siguienteCamara(String camaraActual, String camaraMeta) {
        for(int i = 1; i < caminos.size(); i++){
            if(caminos.get(i).get(0).equals(camaraActual)){
                for(int j = 1; j < caminos.get(i).size(); j++){
                    if(caminos.get(0).get(j).equals(camaraMeta)) {
                        return caminos.get(i).get(j);
                    }
                }
                
            }
        }
        return "";
    }

    private int[] buscarCorte(String camaraActual, String siguienteCamara) {
        int respuesta[] = new int[2];
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                if(tablero[i][j].equals(camaraActual) || tablero[i][j].equals("2")){
                    if(i > 0){
                        if(tablero[i-1][j].equals(siguienteCamara)){
                            respuesta[0] = i-1;
                            respuesta[1] = j;
                            break;
                        } 
                    }
                    if(j > 0){
                        if(tablero[i][j-1].equals(siguienteCamara)){
                            respuesta[0] = i;
                            respuesta[1] = j-1;
                            break;
                        }
                    }
                    if(i < 19){
                        if(tablero[i+1][j].equals(siguienteCamara)){
                            respuesta[0] = i+1;
                            respuesta[1] = j;
                            break;
                        } 
                    }
                    if(j < 19){
                        if(tablero[i][j+1].equals(siguienteCamara)){
                            respuesta[0] = i;
                            respuesta[1] = j+1;
                            break;
                        }
                    }
                }
            }
        } 
        return respuesta;
    }

    private int[] buscarPuntoOpcional(int[] posicionAgente, int[] posicionCorte, String camaraActual) {
        int[] respuesta = new int[2];
        //Busqueda alredor de posicionCorte con una casilla
        if(posicionCorte[0] > 0){
            if(tablero[posicionCorte[0]-1][posicionCorte[1]].equals(camaraActual)){
                respuesta[0] = posicionCorte[0]-1;
                respuesta[1] = posicionCorte[1];
                if(Bresenham(posicionAgente[0], posicionAgente[1], respuesta[0], respuesta[1]) != null)return respuesta;
            }
        }
        if(posicionCorte[1] > 0){
           if(tablero[posicionCorte[0]][posicionCorte[1]-1].equals(camaraActual)){
                respuesta[0] = posicionCorte[0];
                respuesta[1] = posicionCorte[1]-1;
                if(Bresenham(posicionAgente[0], posicionAgente[1], respuesta[0], respuesta[1]) != null)return respuesta;
            }
        }
        if(posicionCorte[0] < 19){
           if(tablero[posicionCorte[0]+1][posicionCorte[1]].equals(camaraActual)){
                respuesta[0] = posicionCorte[0]+1;
                respuesta[1] = posicionCorte[1];
                if(Bresenham(posicionAgente[0], posicionAgente[1], respuesta[0], respuesta[1]) != null)return respuesta;
            }
        }
         if(posicionCorte[1] < 19){
           if(tablero[posicionCorte[0]][posicionCorte[1]+1].equals(camaraActual)){
                respuesta[0] = posicionCorte[0];
                respuesta[1] = posicionCorte[1]+1;
                if(Bresenham(posicionAgente[0], posicionAgente[1], respuesta[0], respuesta[1]) != null)return respuesta;
            }
        }
         //Busqueda alredor de posicionCorte con dos casillas
        if(posicionCorte[0] > 1){
            if(tablero[posicionCorte[0]-2][posicionCorte[1]].equals(camaraActual)){
                respuesta[0] = posicionCorte[0]-2;
                respuesta[1] = posicionCorte[1];
                if(Bresenham(posicionAgente[0], posicionAgente[1], respuesta[0], respuesta[1]) != null)return respuesta;
            }
        }
        if(posicionCorte[1] > 1){ 
           if(tablero[posicionCorte[0]][posicionCorte[1]-2].equals(camaraActual)){
                respuesta[0] = posicionCorte[0];
                respuesta[1] = posicionCorte[1]-2;
                if(Bresenham(posicionAgente[0], posicionAgente[1], respuesta[0], respuesta[1]) != null)return respuesta;
            }
        }
        if(posicionCorte[0] < 18){
           if(tablero[posicionCorte[0]+2][posicionCorte[1]].equals(camaraActual)){
                respuesta[0] = posicionCorte[0]+2;
                respuesta[1] = posicionCorte[1];
                if(Bresenham(posicionAgente[0], posicionAgente[1], respuesta[0], respuesta[1]) != null)return respuesta;
            }
        }
         if(posicionCorte[1] < 18){
           if(tablero[posicionCorte[0]][posicionCorte[1]+2].equals(camaraActual)){
                respuesta[0] = posicionCorte[0];
                respuesta[1] = posicionCorte[1]+2;
                if(Bresenham(posicionAgente[0], posicionAgente[1], respuesta[0], respuesta[1]) != null)return respuesta;
            }
        }
        //System.out.println("Llegue al null");
        return null;
    }

    private String buscarCamara(int[] posicion) {
        String arregloCamaras[] = new String[8];
        for(int i = 0; i < arregloCamaras.length; i++){
            arregloCamaras[i] = "";
        }
        int indice = 0;
         if(posicion[0] > 0){
            if(!tablero[posicion[0]-1][posicion[1]].equals("1")){
                if(existe(tablero[posicion[0]-1][posicion[1]], arregloCamaras)) return tablero[posicion[0]-1][posicion[1]];
                else {
                    arregloCamaras[indice] = tablero[posicion[0]-1][posicion[1]];
                    indice++;
                }
                
            }
            if(posicion[1] > 0) if(!tablero[posicion[0]-1][posicion[1]-1].equals("1")) {
                if(existe(tablero[posicion[0]-1][posicion[1]-1], arregloCamaras)) return tablero[posicion[0]-1][posicion[1]-1];
                else {
                    arregloCamaras[indice] = tablero[posicion[0]-1][posicion[1]-1];
                    indice++;
                }
            }
            if(posicion[1] < 19) if(!tablero[posicion[0]-1][posicion[1]+1].equals("1")){  
                if(existe(tablero[posicion[0]-1][posicion[1]+1], arregloCamaras)) return tablero[posicion[0]-1][posicion[1]+1];
                else{
                    arregloCamaras[indice] = tablero[posicion[0]-1][posicion[1]+1];//Ajfasdflja fajdfklajsd vñajsdkñljv asvlas j
                    indice++;
                }
            }
                
        }
        if(posicion[1] > 0) if(!tablero[posicion[0]][posicion[1]-1].equals("1")){
            if(existe(tablero[posicion[0]][posicion[1]-1], arregloCamaras)) return tablero[posicion[0]][posicion[1]-1];
            else{
                arregloCamaras[indice] = tablero[posicion[0]][posicion[1]-1];
                indice++;
            }
        }
        if(posicion[0] < 19){
            if(!tablero[posicion[0]+1][posicion[1]].equals("1")){
                if(existe(tablero[posicion[0]+1][posicion[1]], arregloCamaras)) return tablero[posicion[0]+1][posicion[1]];
                else{
                    arregloCamaras[indice] = tablero[posicion[0]+1][posicion[1]];
                    indice++;
                }
            }
             if(posicion[1] > 0) if(!tablero[posicion[0]+1][posicion[1]-1].equals("1")){
                 if(existe(tablero[posicion[0]+1][posicion[1]-1], arregloCamaras)) return tablero[posicion[0]+1][posicion[1]-1];
                 else{
                     arregloCamaras[indice] = tablero[posicion[0]+1][posicion[1]-1];
                     indice++;
                 }
             }
             if(posicion[1] < 19) if(!tablero[posicion[0]+1][posicion[1]+1].equals("1")) {
                 if(existe(tablero[posicion[0]+1][posicion[1]+1], arregloCamaras)) return tablero[posicion[0]+1][posicion[1]+1];
                 else{
                     arregloCamaras[indice] = tablero[posicion[0]+1][posicion[1]+1];
                     indice++;
                 }
             }
        }
        if(posicion[1] < 19) if(!tablero[posicion[0]][posicion[1]+1].equals("1")){
            if(existe(tablero[posicion[0]][posicion[1]+1], arregloCamaras)) return tablero[posicion[0]][posicion[1]+1];
            else{
                arregloCamaras[indice] = tablero[posicion[0]][posicion[1]+1];
                indice++;
            }
        }
        return "";
    }

    private boolean existe(String camara, String[] arregloCamaras) {
        for(int i = 0; i < arregloCamaras.length; i++){
            if(arregloCamaras[i].equals(camara)) return true;
        }
        return false;
    }

    private int[] centroCamara(String camaraActual) {
        int x1 = 20;
        int x2 = 0;
        int y1 = 20;
        int y2 = 0;
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                if(tablero[i][j].equals(camaraActual)){
                    if(i < x1) x1 = i;
                    if(i > x2) x2 = i;
                    if(j < y1) y1 = j;
                    if(j > y2) y2 = j;
                }
            }
        }
        int[] respuesta = new int[2];
        respuesta[0] = (x1 + (x2-x1)/2);
        respuesta[1] = (y1 + (y2-y1)/2);
        return respuesta;
    }
}