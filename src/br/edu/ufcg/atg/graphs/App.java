package br.edu.ufcg.atg.graphs;


import org.jgrapht.*;
import org.jgrapht.graph.*;
import java.util.*;

import javax.swing.JFrame;

import java.io.*;


public class App {
	public static Graph<String, DefaultWeightedEdge> graph = 
			new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

    public static void main(String[] args){
    	
    	List<String> nodes = readNodes();
    	
    	createAllNodes(graph, nodes);
    	makeEdges(graph);
    	
    	System.out.println("Created "+graph.vertexSet().size()+" Nodes.");
    	System.out.println("Created "+graph.edgeSet().size()+" Edges.");
    	
    	
    	Viewer viewer = new Viewer();
    	
    	viewer.init();    	
    	
		JFrame frame = new JFrame();
		
		frame.getContentPane().add(viewer);
		
		frame.setTitle("IMDBest");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
    	System.out.println("finished");
    	
    }
   
    public static List<String> readNodes() {
    	String arquivo = "resources/nodes.txt";
    	List<String> ids = new ArrayList<String>();
        try{
           BufferedReader br = new BufferedReader(new FileReader(arquivo));
           while(br.ready()){
              String id = br.readLine();
              ids.add(id);
           }
           br.close();
        }catch(IOException ioe){
           ioe.printStackTrace();
        }
		return ids;
     }
    
    public static void createAllNodes(Graph<String, DefaultWeightedEdge> graph, List<String> nodes) {
    	for (String nodeId : nodes) {
			graph.addVertex(nodeId);
		}
    }
    
    
    @SuppressWarnings("unchecked")
	public static void makeEdges(Graph<String, DefaultWeightedEdge> graph) {
    	String arquivo = "resources/edges.csv";
        try{
           BufferedReader br = new BufferedReader(new FileReader(arquivo));
           while(br.ready()){
              String line = br.readLine();
              String[] splited = line.split(",");
              String id1 = splited[0];
              String weight = splited[1];
              String id2 = splited[2];
              
              Object edge = graph.addEdge(id1, id2);
              if(edge != null) {
            	  graph.setEdgeWeight((DefaultWeightedEdge) edge, Double.parseDouble(weight));
              }
              
              
           }
           br.close();
        }catch(IOException ioe){
           ioe.printStackTrace();
        }
     }
  
        
}
