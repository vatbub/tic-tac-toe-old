package debugging;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import gui.*;
import model.*;

public class printableTree<V> extends SimpleGraph<V, DefaultEdge>{

	private static final long serialVersionUID = 6857703912177376991L;

	public printableTree(V object){
		this(new ClassBasedEdgeFactory<V, DefaultEdge>(DefaultEdge.class));
		
	}
	
	public printableTree(EdgeFactory<V, DefaultEdge> ef) {
		super(ef);
		// TODO Auto-generated constructor stub
	}
	
}
