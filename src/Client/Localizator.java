package src.Client;

/**
 * Created by Kalaman on 17.01.18.
 */
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

    public void filterParticles(float sensorRange){
        ArrayList<Particle> temp_particleList = new ArrayList<>();

        for (Particle particle : particleList) {
            particle.evaluateParticle(roomMap,sensorRange);
            if (particle.getWeight() != 0)
                temp_particleList.add(particle);
        }
        particleList = temp_particleList;
    }
}