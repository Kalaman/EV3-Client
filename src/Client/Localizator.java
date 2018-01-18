package src.Client;

/**
 * Created by Kalaman on 17.01.18.
 */
import java.util.ArrayList;
import com.kitfox.svg.SVGDiagram;

public class Localizator {
    private int initParticleAmount;
    private ArrayList<Particle> particleList;
    private SVGDiagram map;

    public Localizator (int particleAmount,SVGDiagram map) {
        this.initParticleAmount = particleAmount;
        this.particleList = new ArrayList<Particle>();
        this.map = map;

        generateRandomParticles();
    }

    public ArrayList<Particle> getParticles() {
        return particleList;
    }

    public void generateRandomParticles() {
        for (int i=0;i<initParticleAmount;++i) {
            int randomX = (int)(Math.random() * map.getWidth()) ;
            int randomY = (int)(Math.random() * map.getHeight()) ;
            float randomDeg = (float) (Math.random() * 360) ;

            particleList.add(new Particle(randomX,randomY,randomDeg,0));
        }
    }

}