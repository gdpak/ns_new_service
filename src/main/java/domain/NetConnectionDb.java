package domain;

import java.io.File;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/*
 * DB layer to save network connections to DB
 * @author Deepak Agrawal
 */
@XmlRootElement
@XmlSeeAlso(NetConnection.class)
public class NetConnectionDb {
	GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
	static GraphDatabaseService dbService = null;
	
	public void init() {
		dbService = dbFactory.newEmbeddedDatabaseBuilder(new File("data/neo4j.db")).newGraphDatabase();
	}
	
	public void close() {
		dbService.shutdown();
	}
	
	public void add(NetConnection nc) {
		try (Transaction tx = dbService.beginTx())	//create Neo4j transaction
		{
			// Delete any old node by same name if present already
			Node node = dbService.findNode(Label.label("ncdb"), "name", nc.getName());
			if (node != null) {
				node.delete();
			}
			Node node1 = dbService.createNode(Label.label("ncdb"));	//create node in database
			node1.setProperty("name", nc.getName());
			node1.setProperty("vnfc1", nc.getVnfc1());
			node1.setProperty("vnfc2", nc.getVnfc2());
			node1.setProperty("cp1", nc.getCp1());
			node1.setProperty("cp2", nc.getCp2());
			node1.setProperty("vl", nc.getVl());

			// Create two graph nodes vnfc1 and vnfc2
			Node vnf_node1 = dbService.createNode(Label.label("vnfcdb"));
			vnf_node1.setProperty("name", nc.getVnfc1());	// Sets Name of VNFC1
			Node vnf_node2 = dbService.createNode(Label.label("vnfcdb"));	//create node in database
			vnf_node2.setProperty("name", nc.getVnfc2());	// Sets Name
			// Create Relationship between vnfc1 and cp1
			Node cp1_node = dbService.findNode(Label.label("cpdb"), "name", nc.getCp1());
			Relationship re1 = vnf_node1.createRelationshipTo(cp1_node, RelationshipType.withName("connection_point"));
			re1.setProperty("Type", "CP");
			// Create Relationship between vnfc2 and cp2
		    Node cp2_node = dbService.findNode(Label.label("cpdb"), "name", nc.getCp2());
			Relationship re2 = vnf_node2.createRelationshipTo(cp2_node, RelationshipType.withName("connection_point"));
			re2.setProperty("Type", "CP");
			
			// Create RelationShip between cp1 and vl
			Node vl_node = dbService.findNode(Label.label("vldb"), "name", nc.getVl());
			Relationship re3 = cp1_node.createRelationshipTo(vl_node, RelationshipType.withName("cp_to_vl"));
			re3.setProperty("Type", "cp_to_vl");
			
			// Create Relationship between cp2 and vl
			Relationship re4 = cp2_node.createRelationshipTo(vl_node, RelationshipType.withName("cp_to_vl"));
			re4.setProperty("Type", "cp_to_vl");
			tx.success();
		}
	}
	
	public void remove(NetConnection nc) {
		try (Transaction tx = dbService.beginTx()) {
			Node node = dbService.findNode(Label.label("ncdb"), "name", nc.getName());
			node.delete();
			
			// Remove vnfc1 and vnfc2 node
			Node node1 = dbService.findNode(Label.label("vnfcdb"), "name", nc.getVnfc1());
			Iterable<Relationship> allre_vnfc1 = node1.getRelationships(Direction.OUTGOING);
			for (Relationship re: allre_vnfc1) {
				re.delete();
			}
			node1.delete();
			Node node2 = dbService.findNode(Label.label("vnfcdb"), "name", nc.getVnfc2());
			Iterable<Relationship> allre_vnfc2 = node2.getRelationships(Direction.OUTGOING);
			for (Relationship re: allre_vnfc2) {
				re.delete();
			}
			node2.delete();
			
			// Remove relationships to cp as cp can be part of only one VL so ideally there will be 
			// only ONE OUTGOING EDGE from CP to VL
			Node cp1_node = dbService.findNode(Label.label("cpdb"), "name", nc.getCp1());
			Iterable<Relationship> allre1 = cp1_node.getRelationships(Direction.OUTGOING);
			for (Relationship re: allre1) {
				re.delete();
			}
			Node cp2_node = dbService.findNode(Label.label("cpdb"), "name", nc.getCp2());
			Iterable<Relationship> allre2 = cp2_node.getRelationships(Direction.OUTGOING);
			for (Relationship re: allre2) {
				re.delete();
			}
			tx.success();
		}
	}
	
	public NetConnection find(String name) {
		NetConnection nc = new NetConnection();
		try (Transaction tx = dbService.beginTx()) {
			Node node = dbService.findNode(Label.label("ncdb"), "name", name);
			nc.setName((String)node.getProperty("name"));
			nc.setCp1((String)node.getProperty("cp1"));
			nc.setCp2((String)node.getProperty("cp2"));
			nc.setVl((String)node.getProperty("vl"));
			nc.setVnfc1((String)node.getProperty("vnfc1"));
			nc.setVnfc2((String)node.getProperty("vnfc2"));
			tx.success();
		}
		return (nc);
	}
	
	// Find VL from a given vnfc
	public VirtualLink find_vl_from_vnfc(String vnfc_name) {
		VirtualLink vl = new VirtualLink();
		try (Transaction tx = dbService.beginTx()) {
			Node node = dbService.findNode(Label.label("vnfcdb"), "name", vnfc_name);
			Iterable<Relationship> allre = node.getRelationships(Direction.OUTGOING);
			Node vlnode=null;
			for (Relationship re: allre) {
				Node cpnode = re.getOtherNode(node);
				Iterable<Relationship> allre_cp = cpnode.getRelationships(Direction.OUTGOING);
			    for (Relationship recp: allre_cp) {
			    	vlnode = recp.getOtherNode(cpnode);
			    }
			}
			vl.setName((String)vlnode.getProperty("name")) ;
			vl.setType((int)vlnode.getProperty("type"));
			vl.setTenant_vlan((int)vlnode.getProperty("tenant_vlan"));
			vl.setIp_version((int)vlnode.getProperty("ip_version"));
			vl.setIp_cidr((String)vlnode.getProperty("ip_cidr"));
			vl.setSegment_id((int)vlnode.getProperty("segment_id"));
			vl.setDhcp_enabled((int)vlnode.getProperty("dhcp_enabled"));
			vl.setCloud_tenant_id((int)vlnode.getProperty("cloud_tenant_id"));
			tx.success();
			}
		return (vl);
	}

}
