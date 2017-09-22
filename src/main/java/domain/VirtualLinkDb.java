package domain;

import java.io.File;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/*
 * Layer which converts VL records to DB 
 */

/*
 * Neo4J Based implementation
 */
@XmlRootElement
@XmlSeeAlso(VirtualLink.class)
public class VirtualLinkDb {
	GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
	static GraphDatabaseService dbService = null;
	
	public void init() {
		dbService = dbFactory.newEmbeddedDatabaseBuilder(new File("data/neo4j.db")).newGraphDatabase();
	}
	
	public void close() {
		dbService.shutdown();
	}
	public void add(VirtualLink vl) {
		try (Transaction tx = dbService.beginTx())	//create Neo4j transaction
		{
			// Delete any old node by same name if present already
			Node node = dbService.findNode(Label.label("vldb"), "name", vl.getName());
			if (node != null) {
				node.delete();
			}
			Node node1 = dbService.createNode(Label.label("vldb"));	//create node in database
			node1.setProperty("name", vl.getName());	// Sets Name
			node1.setProperty("type", vl.getType());
			node1.setProperty("tenant_vlan", vl.getTenant_vlan());
			node1.setProperty("ip_version", vl.getIp_version());
			node1.setProperty("ip_cidr", vl.getIp_cidr());
			node1.setProperty("segment_id", vl.getSegment_id());
			node1.setProperty("dhcp_enabled", vl.isDhcp_enabled());
			node1.setProperty("cloud_tenant_id", vl.getCloud_tenant_id());
			//node1.setProperty("uuid", vl.getUuid());
			//node1.setProperty("time_created", (String)vl.getTime_created());
			tx.success();	//commit transaction
		}
	}
	
	public void remove(VirtualLink vl) {
		try (Transaction tx = dbService.beginTx()) {
			Node node = dbService.findNode(Label.label("vldb"), "name", vl.getName());
			node.delete();
			tx.success();
		}
		
	}

	public VirtualLink find(String name) {
		VirtualLink vl = new VirtualLink();
		try (Transaction tx = dbService.beginTx()) {
			Node node = dbService.findNode(Label.label("vldb"), "name", name);
			vl.setName((String)node.getProperty("name")) ;
			vl.setType((int)node.getProperty("type"));
			vl.setTenant_vlan((int)node.getProperty("tenant_vlan"));
			vl.setIp_version((int)node.getProperty("ip_version"));
			vl.setIp_cidr((String)node.getProperty("ip_cidr"));
			vl.setSegment_id((int)node.getProperty("segment_id"));
			vl.setDhcp_enabled((int)node.getProperty("dhcp_enabled"));
			vl.setCloud_tenant_id((int)node.getProperty("cloud_tenant_id"));
			//vl.setUuid((String)node.getProperty("uuid"));
			//vl.setTime_created((Date)node.getProperty("time_created"));

			tx.success();
		}
		return vl;
	}
}
