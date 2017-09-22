package domain;

import java.io.File;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

@XmlRootElement
@XmlSeeAlso(ConnectionPoint.class)
public class ConnectionPointDb {
	GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
	static GraphDatabaseService dbService = null;
	
	public void init() {
		dbService = dbFactory.newEmbeddedDatabaseBuilder(new File("data/neo4j.db")).newGraphDatabase();
	}
	
	public void close() {
		dbService.shutdown();
	}
	
	public void add(ConnectionPoint cp) {
		try (Transaction tx = dbService.beginTx())	//create Neo4j transaction
		{
			// Delete any old node by same name if present already
			Node node = dbService.findNode(Label.label("cpdb"), "name", cp.getName());
			if (node != null) {
				node.delete();
			}
			Node node1 = dbService.createNode(Label.label("cpdb"));	//create node in database
			node1.setProperty("name", cp.getName());	// Sets Name
			node1.setProperty("order", cp.getOrder());
			node1.setProperty("is_default", cp.getIs_default());
			node1.setProperty("vr_port_type", cp.getVr_port_type());
			node1.setProperty("anti_spoof_protection", cp.getAnti_spoof_protection());
			node1.setProperty("uuid", cp.getUuid());
			//node1.setProperty("time_created", cp.getTime_created());
			tx.success();	//commit transaction
		}
	}
	
	public void remove(ConnectionPoint cp) {
		try (Transaction tx = dbService.beginTx()) {
			Node node = dbService.findNode(Label.label("cpdb"), "name", cp.getName());
			node.delete();
			tx.success();
		}
	}
	
	public ConnectionPoint find(String name) {
		ConnectionPoint cp = new ConnectionPoint();
		try (Transaction tx = dbService.beginTx()) {
			Node node = dbService.findNode(Label.label("cpdb"), "name", name);
			cp.setName((String)node.getProperty("name")) ;
			cp.setOrder((int)node.getProperty("order"));
			cp.setIs_default((int)node.getProperty("is_default"));
		    cp.setVr_port_type((int)node.getProperty("vr_port_type"));
			cp.setAnti_spoof_protection((int)node.getProperty("anti_spoof_protection"));
			cp.setUuid((String)node.getProperty("uuid"));
			//cp.setTime_created((Date)node.getProperty("time_created"));

			tx.success();
		}
		return cp;
	}
	
}
