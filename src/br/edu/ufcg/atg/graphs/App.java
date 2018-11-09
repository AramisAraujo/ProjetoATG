package br.edu.ufcg.atg.graphs;


import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;
import java.net.*;
import java.rmi.server.ExportException;
import java.util.*;
import java.io.*;


public class App {


    public static void main(String[] args){
    	Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    	
    	List<String> nodes = readNodes();
    	
    	createAllNodes(graph, nodes);
    	makeEdges(graph);
    	
    	System.out.println(graph.vertexSet().size());
    	System.out.println(graph.edgeSet().size());
    }
    
    private static List<String> readNodes() {
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
    
    private static void createAllNodes(Graph graph, List<String> nodes) {
    	for (String nodeId : nodes) {
			graph.addVertex(nodeId);
		}
    }
    
    
    @SuppressWarnings("unchecked")
	private static void makeEdges(Graph graph) {
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
            	  graph.setEdgeWeight(edge, Double.parseDouble(weight));
              }
              
              
           }
           br.close();
        }catch(IOException ioe){
           ioe.printStackTrace();
        }
     }
  
        
}
