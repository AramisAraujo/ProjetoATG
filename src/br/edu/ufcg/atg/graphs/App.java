package br.edu.ufcg.atg.graphs;

import org.jgrapht.*;
import org.jgrapht.alg.clique.BronKerboschCliqueFinder;
import org.jgrapht.graph.*;
import java.util.*;

import java.io.*;


public class App {
	public static Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(
			DefaultWeightedEdge.class);

	public static void main(String[] args) {

		List<String> nodes = readNodes();

		createAllNodes(graph, nodes);
		makeEdges(graph, 0);

		System.out.println("Created " + graph.vertexSet().size() + " Nodes.");
		System.out.println("Created " + graph.edgeSet().size() + " Edges.");



		questao1(graph);
		questao2(nodes);
		questao3(nodes);
		questao4(graph);


	}
	
	//QUESTÃO 1
	public static void questao1(Graph<String, DefaultWeightedEdge> graph) {
				System.out.println("Quais pessoas trabalharam em mais produções juntos? \n");
				
				BronKerboschCliqueFinder<String, DefaultWeightedEdge> bcf = new BronKerboschCliqueFinder<String, DefaultWeightedEdge>(graph);
				HashSet cliqueMax =  (HashSet) bcf.maximumIterator().next();
				
				System.out.println(cliqueMax);
	}
	
	//QUESTÃO 2
	public static void questao2(List<String> nodes) {
		System.out.println("\nQual maior grupo de pessoas que trabalharam em filmes de sucesso "
				+ "(Filme de sucesso: Classificados com nota acima de 9)?");
		
		Graph<String, DefaultWeightedEdge> graph2 = new SimpleWeightedGraph<String, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);
		
		createAllNodes(graph2, nodes);
		makeEdges(graph2, 9.0);
		
		BronKerboschCliqueFinder<String, DefaultWeightedEdge> bcf = new BronKerboschCliqueFinder<String, DefaultWeightedEdge>(graph2);
		HashSet cliqueMax =  (HashSet) bcf.maximumIterator().next();
		
		System.out.println(cliqueMax);
		
	}
	
	//QUESTÃO 3
	public static void questao3(List<String> nodes) {
		System.out.println("\nQual a melhor equipe de pessoas que pode ser formada para produção de um filme com "
				+ "base nas notas de produções anteriores?");
		
		Graph<String, DefaultWeightedEdge> graph2 = new SimpleWeightedGraph<String, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);
		
		createAllNodes(graph2, nodes);
		makeEdges(graph2, 9.4);
		
		BronKerboschCliqueFinder<String, DefaultWeightedEdge> bcf = new BronKerboschCliqueFinder<String, DefaultWeightedEdge>(graph2);
		HashSet cliqueMax =  (HashSet) bcf.maximumIterator().next();
		
		System.out.println(cliqueMax);
		
		
		
	}
	//QUESTÃO 4
	public static void questao4(Graph graph) {
		System.out.println("Encontrar a(as) pessoa(as) com maior Proximidade a Kevin Bacon ");
		
		String kevinBaconID = "nm0000102";
		
		
		
		Iterator<DefaultWeightedEdge> baconEdges = graph.edgesOf(kevinBaconID).iterator();
		
		Map<String, Integer> densestNodes = new HashMap<String, Integer>();
		
		while(baconEdges.hasNext()) {
			String node = baconEdges.next().toString().replace("(", "").split(" ")[0];
			
			if(densestNodes.containsKey(node)) {
				int count = densestNodes.get(node);
				densestNodes.put(node, count + 1);
			}else {
				densestNodes.put(node, 1);
			}
		}
		
		System.out.println(densestNodes);
		
		
		
		
		
		
		
		
        //String result = "Maior indice bacon: " + IDmaior + " com distancia " + maiorTamanho;
	}

	public static List<String> readNodes() {
		String arquivo = "resources/nodes.txt";
		List<String> ids = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(arquivo));
			while (br.ready()) {
				String id = br.readLine();
				ids.add(id);
			}
			br.close();
		} catch (IOException ioe) {
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
	public static void makeEdges(Graph<String, DefaultWeightedEdge> graph, double thresholdNota) {
		String arquivo = "resources/edges.csv";
		try {
			BufferedReader br = new BufferedReader(new FileReader(arquivo));
			while (br.ready()) {
				String line = br.readLine();
				String[] splited = line.split(",");
				String id1 = splited[0];
				String weight = splited[1];
				String id2 = splited[2];
				
				if(Double.parseDouble(weight) < thresholdNota) {
					continue;
				}

				Object edge = graph.addEdge(id1, id2);
				if (edge != null) {
					graph.setEdgeWeight((DefaultWeightedEdge) edge, Double.parseDouble(weight));
				}

			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
