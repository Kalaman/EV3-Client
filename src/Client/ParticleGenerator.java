package Client;

import java.util.ArrayList;

import com.kitfox.svg.SVGDiagram;

public class ParticleGenerator {

	private ArrayList<Particle> particleList;
	private SVGDiagram svg;
	
	public ParticleGenerator(SVGDiagram svg) {
		this.svg = svg;
		particleList = new ArrayList<>();
	}
	
	public void generateParticle(int numberOfParticles) {
		for (int i = 0; i < numberOfParticles; i++) {
			
			Particle particle = new Particle((int)(Math.random() * svg.getWidth()), (int)(Math.random() * svg.getWidth()), (int)(Math.random() * svg.getWidth()));
			particleList.add(particle);
		}
	}
}
