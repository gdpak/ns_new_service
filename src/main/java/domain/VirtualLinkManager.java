package domain;


/*
 * CRUD API for Virtual Link Resource
 * 
 * @author Deepak Agrawal
 */

public class VirtualLinkManager {
	private static VirtualLinkDb Vldb = new VirtualLinkDb();
    static { // Add some Data to this list for test
    	Vldb.init();
		Vldb.add(new VirtualLink("vl1", 0, 40, 4, "1.1.1.0/24", 23, 1));
		Vldb.add(new VirtualLink("vl2", 0, 40, 4, "1.1.2.0/24", 23, 1));
		Vldb.close();
	}
	/*
	 * Get all VL List
	 */
	public static VirtualLinkDb getVls() {
		return Vldb;
	}
	
	/*
	 * @GET - Get one specified VL
	 */
	public static VirtualLink find(String name) {
		Vldb.init();
		VirtualLink vl = Vldb.find(name);
		Vldb.close();
		return ((vl == null)?null:vl);
	}
	/*
	 * @POST - Add a VL 
	 */
	
	public static void add(VirtualLink vl) {
		Vldb.init();
		Vldb.add(vl);
		Vldb.close();
	}

	/*
	 * @DELETE - Delete a VL 
	 */
	public static void delete(String name) {
		Vldb.init();
		VirtualLink vl = Vldb.find(name);
		Vldb.remove(vl);
		Vldb.close();
	}
	
	/*
	 * @PUT - Update a VL
	 */
	public static VirtualLink update(VirtualLink vl) {
		Vldb.init();
		VirtualLink vl_origin = Vldb.find(vl.getName());
		if (vl_origin == null) {
			Vldb.close();
			return null;
		}
		/*
		 * Delete the old VL
		 */
		delete(vl_origin.getName());
		add(vl);
		Vldb.close();
		return vl;
	}
	

}
