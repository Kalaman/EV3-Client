package src;
/**
 * Created by Kalaman on 12.01.18.
 */
public class Node {
    private int positionX;
    private int positionY;
    private float degree;

    public Node (int posX,int posY,float deg) {
        positionX = posX;
        positionY = posY;
        degree = deg % 360;
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
}
