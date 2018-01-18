package src.Client;

import java.awt.*;
import java.util.ArrayList;


/**
 * Created by Kalaman on 12.01.18.
 */
public class Particle {
    private int positionX;
    private int positionY;
    private float degree;
    private int endX;
    private int endY;
    private int weight;
    
    public Particle (int posX,int posY,float deg,int particleWeight) {
        positionX = posX;
        positionY = posY;
        degree = deg % 360;
        weight = particleWeight;
        endX = getPositionX() + (int)(Math.cos(Math.toRadians(getDegree())) * 100);
        endY = getPositionY() + (int)(Math.sin(Math.toRadians(getDegree())) * 100);
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public float getDegree() {
        return degree;
    }


    public float getDistanceToWall(ArrayList<Line> lines) {
        Point intersection = null;

        for (Line line : lines) {
            intersection = findIntersection(line.getX1(), line.getY1(), line.getX2(), line.getY2());
            if (intersection != null)
                break;
        }

        if (intersection == null)
            return -1;
        else
            return (float) Math.sqrt(Math.pow(positionX-intersection.getX(),2) + Math.pow(positionY-intersection.getY(),2));
    }

    public Point findIntersection(int x1, int y1, int x2, int y2){
                int d = (x1-x2)*(positionY-endY) - (y1-y2)*(positionX-endX);
        if (d == 0) return null;
        int xi = ((positionX-endX)*(x1*y2-y1*x2)-(x1-x2)*(positionX*endY-positionY*endX))/d;
        int yi = ((positionY-endY)*(x1*y2-y1*x2)-(y1-y2)*(positionX*endY-positionY*endX))/d;
        Point p = new Point(xi,yi);
        if (xi < Math.min(x1,x2) || xi > Math.max(x1,x2)) return null;
        if (xi < Math.min(positionX,endX) || xi > Math.max(positionX,endX)) return null;
        return p;
    }

	public void drawParticleLine(Particle particle, Color red, Graphics2D graphics2d) {
        graphics2d.drawLine(getPositionX() ,
                getPositionY(),endX,endY);
	}
}
