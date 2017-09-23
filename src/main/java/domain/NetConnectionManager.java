package domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * CRUD API for Network Connection Manager
 */

public class NetConnectionManager {
	private static NetConnectionDb ncdb = new NetConnectionDb();
	static {// Add some dummy netconnection for testing
		final Map<String, Object> cpdata1 = new LinkedHashMap<String, Object>(2);
		cpdata1.put("name", "cp1");
		cpdata1.put("type", "cpmc");
		final Map<String, Object> cpdata2 = new LinkedHashMap<String, Object>(2);
		cpdata2.put("name", "cp2");
		cpdata2.put("type", "cpmc");
		final Map<String, Object> vldata1 = new LinkedHashMap<String, Object>(2);
		vldata1.put("name", "vl1");
		vldata1.put("type", "vlbc");
		final Map<String, Object> vldata2 = new LinkedHashMap<String, Object>(2);
		vldata2.put("name", "vl1");
		vldata2.put("type", "vlbc");
		
		List<vl> vllist1 = new ArrayList<vl>();
		final vl vl1 = new vl(vldata1);
		final vl vl2 = new vl(vldata2);
		vllist1.add(vl1);
		vllist1.add(vl2);
		
		List<cp> cplist1 = new ArrayList<cp>();
		final cp cp1 = new cp(cpdata1);
		final cp cp2 = new cp(cpdata2);
		cplist1.add(cp1);
		cplist1.add(cp2);
		
		final nc nc1 = new nc("vnfc_btas", vllist1, cplist1);
		List<nc> nclist1 = new ArrayList<nc>();
		nclist1.add(nc1);
		
		ncdb.add(new NetConnection(nclist1), "vnf_btas");

	}
	
	/*
	 * Get one specified NC
	 */
	public static NetConnection find(String vnfID){
		NetConnection nc = ncdb.find(vnfID);
		return((nc == null)?null:nc);
	}
	
	/*
	 * POST - Add a NC
	 */
	public static void add(NetConnection nc, String vnfID) {
		ncdb.add(nc, vnfID);
	}

	/*
	 * @DELETE - Delete a NC 
	 */
	public static void delete(String name) {
		NetConnection nc = find(name);
		ncdb.remove(nc);
	}
	
	/*
	 * @PUT - Update a NC
	 */
	public static NetConnection update(NetConnection nc) {
		NetConnection nc_origin = find(nc.getVnfID());
		if (nc_origin == null) {
			return null;
		}
		
		/*
		 * Delete the old NC
		 */
		delete(nc_origin.getVnfID());
		add(nc, nc.getVnfID());
		return nc;
	}
	
	/*
	 * GET VL from vnfc
	 
	public static VirtualLink find_vl_from_vnfc(String vnfc_name) {
		ncdb.init();
		VirtualLink vl = ncdb.find_vl_from_vnfc(vnfc_name);
		ncdb.close();
		return vl;
	
	}
	*/

}
