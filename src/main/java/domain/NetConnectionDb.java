package domain;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


/*
 * DB layer to save network connections to DB
 * @author Deepak Agrawal
 */
@XmlRootElement
@XmlSeeAlso(NetConnection.class)
public class NetConnectionDb {
	static List<NetConnection> ncdb = new ArrayList<NetConnection>();
	public void add(NetConnection nc, String vnfID) {
		nc.setVnfID(vnfID);
		ncdb.add(nc);
		System.out.printf("vnfID = %s", vnfID);
	}
	
	public void remove(NetConnection nc) {
		ncdb.remove(nc);
	
	}
	
	public NetConnection find(String vnfID) {
		Iterator<NetConnection> it = ncdb.iterator();
		System.out.printf("called find");
		while (it.hasNext()) {
			NetConnection current = it.next();
			System.out.printf("vnfID1 = %s, vnfID2=%s", vnfID, current.getVnfID());
			if (current.getVnfID().equals(vnfID)) {
				return (current);
			}
		}
		return null;
	}
	
	/* Find VL from a given vnfc
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
	*/

}
