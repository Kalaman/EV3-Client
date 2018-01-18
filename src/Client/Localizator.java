package src.Client;

/**
 * Created by Kalaman on 17.01.18.
 */
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.kitfox.svg.SVGDiagram;

public class Localizator {
    private int initParticleAmount;
    private ArrayList<Particle> particleList;
    private SVGDiagram map;
    private RoomMap roomMap;

    public Localizator (int particleAmount,RoomMap roomMap) {
        this.initParticleAmount = particleAmount;
        this.particleList = new ArrayList<Particle>();
        this.map = roomMap.getSvgDiagram();
        this.roomMap = roomMap;

        generateRandomParticles();
    }

    public ArrayList<Particle> getParticles() {
        return particleList;
    }

    public void generateRandomParticles() {

        ArrayList<Line> lines = roomMap.getRoomLines();
        for (int i=0;i<initParticleAmount;++i) {
            boolean found = false;

            int randomX = 0;
            int randomY = 0;

            while (!found) {
                randomX = (int) (Math.random() * roomMap.getSvgDiagram().getWidth());
                randomY = (int) (Math.random() * roomMap.getSvgDiagram().getHeight());

                for (int j = 0; j < lines.size(); ++j) {
                    Line currentLine = lines.get(j);
                    if (randomX >= currentLine.getX1() && randomX < currentLine.getX2() &&
                            randomY >= currentLine.getY2()) {
                        found = true;
                        break;
                    }
                }
            }

            float randomDeg = (float) (Math.random() * 360) ;

            particleList.add(new Particle(randomX,randomY,randomDeg,0));
        }
    }

    /**
     * Performs the resampling of Particles according to the weights
     * @param sensorRange
     */
    public void filterParticles(float sensorRange){
        ArrayList<Particle> temp_particleList = new ArrayList<>();
        int maxValue = 0;

        for (Particle particle : particleList) {
            particle.evaluateParticle(roomMap,sensorRange);
            maxValue += particle.getWeight();
        }

        int index = (int) Math.random() * particleList.size();
        float beta = 0.0f;

        for (int i = 0; i < particleList.size(); i++) {
            beta += (Math.random() * 2 * maxValue);
            while (beta > particleList.get(index).getWeight()){
                beta -= particleList.get(index).getWeight();
                index = (index + 1) % particleList.size();
            }
            temp_particleList.add(generateNewParticle(particleList.get(index)));
        }

        particleList = temp_particleList;
    }

    public Particle generateNewParticle(Particle oldParticle){
        int degree = (int) (Math.random() * 360);
        int endX = (int) (oldParticle.getPositionX() + (Math.cos(Math.toRadians(degree)) * 10));
        int endY = (int) (oldParticle.getPositionY() + (Math.sin(Math.toRadians(degree)) * 10));
        Particle newParticle = new Particle(endX, endY, oldParticle.getDegree(),0);
        float distance = (float) Math.sqrt(Math.pow(oldParticle.getPositionX()-endX,2) + Math.pow(oldParticle.getPositionY()-endY,2));;
        return newParticle;
    }
}