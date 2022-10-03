package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 *
 * @author Margarita Alcaraz
 */
public class CnvTablero extends Canvas {

    private ArrayList<Color> colores = new ArrayList<>();
    private ArrayList<Integer> posiciones = new ArrayList<>();
    private int caniasRes = 2;
    private int numFilasPorAspa;
    
    public CnvTablero(int numFilasPorAspa){
        if(numFilasPorAspa>7) numFilasPorAspa = 7;
        else if(numFilasPorAspa<4) numFilasPorAspa = 4;
        this.numFilasPorAspa = numFilasPorAspa;
    }
    
    public void setCaniasRes(int res){
        caniasRes = res;
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        Rectangle rect = new Rectangle();
        rect.setBounds(0, 0, this.getWidth()-1, this.getHeight()-1);
        //System.out.println("ancho: "+this.getWidth()+" alto: "+this.getHeight());
        g2d.setColor(Color.white);
        g2d.fill(rect);
        g2d.setColor(Color.black);
        g2d.draw(rect);
        dibujarTablero(g2d);
        for (int i = 0; i < 4; i++) {
            dibujarJugador(g2d, i);
        }
        dibujarCanias(g2d);
        dibujarTurno(g2d);
        dibujarFicha(g2d, 0, Color.BLACK);
        dibujarFicha(g2d, 12, Color.blue);
        dibujarFicha(g2d, 17, Color.magenta);
        dibujarFicha(g2d, 6, Color.green);
        dibujarFicha(g2d, 52, Color.ORANGE);
        for (int i = 0; i < posiciones.size(); i++) {
            dibujarFicha(g2d, posiciones.get(i), colores.get(i));
        }
        
    }
    
    public void dibujarTurno(Graphics2D g){
        g.drawString("Turno: 1", 20, 410);
    }
    
    public void dibujarCanias(Graphics2D g){
        Rectangle r = new Rectangle();
        int ancho = 25, alto = 45; 
        int x = 20, y = 450;
        int marcasDibujadas = 0;
        for (int i = 0; i < 5; i++) {
            g.setColor(Color.GREEN);
            r.setBounds(x+(ancho*i)+(10*i), y, ancho, alto);
            g.fill(r);
            if(caniasRes>marcasDibujadas){
                g.setColor(Color.black);
                g.fillOval(x+(ancho*i)+(10*i)+5, y+15, 15, 15);
                marcasDibujadas++;
            }
        }
        g.setColor(Color.black);
        g.drawString("Resultado de las cañas:", 20, 435);
        
    }
    
    public void dibujarTablero(Graphics2D g){
        int anchoCas = 25, altoCas = 25;
        int inicioHorzX = 200+((7-numFilasPorAspa)*anchoCas);
        int inicioHorzY = 220-altoCas;
        int inicioVertY = 20+((7-numFilasPorAspa)*altoCas);
        int inicioVertX = 400-anchoCas;
        Rectangle r = new Rectangle();
        //r.setBounds(200, 20, 400, 400);
        //g.draw(r);
        int numColsFilas = numFilasPorAspa*2+2;
        //aspa horizontal
        int x = inicioHorzX, y = inicioHorzY;
        for (int j = 0; j < numColsFilas; j++) {
            if(j==0){
                Arc2D.Double arco = new Arc2D.Double(x, y, anchoCas*2, altoCas*2, 90, 180, Arc2D.OPEN);
                g.draw(arco);
            } else if(j==(numColsFilas-1)){
                Arc2D.Double arco = new Arc2D.Double(x-anchoCas, y, anchoCas*2, altoCas*2, 90, -180, Arc2D.OPEN);
                g.draw(arco);        
            } else {
                r.setBounds(x, y, anchoCas, altoCas*2);
                g.draw(r);
                if(j==2||j==numColsFilas-3){
                    int[] pointsX = new int[3];
                    int[] pointsY = new int[]{y, y+altoCas, y};
                    for (int i = 0; i < 3; i++) 
                        pointsX[i] = x + (i*(anchoCas/2));
                    g.fillPolygon(pointsX, pointsY, 3);
                    pointsY[0] += altoCas*2;
                    pointsY[2] += altoCas*2;
                    g.fillPolygon(pointsX, pointsY, 3);
                }
            }
            x+=anchoCas;
        }
        
        //aspa vertical
        x = inicioVertX; y = inicioVertY;
        for (int j = 0; j < numColsFilas; j++) {
            if(j==0){
                Arc2D.Double arco = new Arc2D.Double(x, y, anchoCas*2, altoCas*2, 180, -180, Arc2D.OPEN);
                g.draw(arco);
            } else if(j==(numColsFilas-1)){
                Arc2D.Double arco = new Arc2D.Double(x, y-altoCas, anchoCas*2, altoCas*2, 180, 180, Arc2D.OPEN);
                g.draw(arco);        
            } else {
                r.setBounds(x, y, anchoCas*2, altoCas);
                g.draw(r);
                if(j==2||j==numColsFilas-3){
                    int[] pointsX = new int[]{x, x+anchoCas, x};
                    int[] pointsY = new int[3];
                    for (int i = 0; i < 3; i++) 
                        pointsY[i] = y + (i*(altoCas/2));
                    g.fillPolygon(pointsX, pointsY, 3);
                    pointsX[0] += anchoCas*2;
                    pointsX[2] += anchoCas*2;
                    g.fillPolygon(pointsX, pointsY, 3);
                }
            }
            y+=altoCas;
        }
        
        //horz
        g.drawLine(inicioHorzX, inicioHorzY+altoCas, inicioHorzX+(numColsFilas*anchoCas), inicioHorzY+altoCas);
        //vert
        g.drawLine(inicioVertX+anchoCas, inicioVertY, inicioVertX+anchoCas, inicioVertY+(numColsFilas*altoCas));
        
    }
    
    public void dibujarJugador(Graphics2D g, int pos){
        g.drawString("Jugador "+(pos+1)+": Nombre", 20, (90*pos)+20);
        g.drawString("Apuesta: 100", 20, (90*pos)+35);
        g.drawString("Fichas Restantes: 10", 20, (90*pos)+50);
        g.drawString("Fichas Eliminadas: 2", 20, (90*pos)+65);
        g.drawString("En juego", 20, (90*pos)+80);
    }
    
    public void removeAllFichas(){
        colores.clear();
        posiciones.clear();
    }
    
    public void addFicha(int pos, Color color){
        posiciones.add(pos);
        colores.add(color);
    }
    
    public void dibujarFicha(Graphics2D g, int pos, Color color){
        int numCas = numFilasPorAspa*8+4;
        if(pos>=numCas) pos = numCas-1;
        else if(pos<0) pos = 0;
        int anchoFicha = 15, altoFicha = 15;
        int posInicioX = 405, posInicioY = 200;
        
        int f=numFilasPorAspa, i = -1, aspa = 0;
        while(pos>f){
            i=f;
            f+=numFilasPorAspa;
            aspa++;
            if(aspa%2==0)f++;
        }
        
        int x = posInicioX, y = posInicioY;
        if(aspa>=1&&aspa<=4) y += 25;
        if(aspa>=3&&aspa<=6) x -= 25;
        boolean cambiarX = (aspa/2)%2==0;
        boolean sumar = (aspa%2==0&&aspa<3)||(aspa%2!=0&&aspa>3);
        if(aspa%2!=0){
            int finalCas = numFilasPorAspa*25;
            if(cambiarX){
                x = (sumar)? x-finalCas : x+finalCas;
            } else {
                y = (sumar)? y-finalCas : y+finalCas;
            }
        }
        
        int avanza = pos-i-1;
        if(cambiarX){
            x = (sumar)? x+(avanza*25) : x-(avanza*25);
        } else {
            y = (sumar)? y+(avanza*25) : y-(avanza*25);
        }
        
        g.setColor(color);
        g.fillOval(x, y, anchoFicha, altoFicha);
        
    }
    
}
