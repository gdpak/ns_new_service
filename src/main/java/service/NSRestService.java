package service;

import java.net.URI;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import domain.NetConnection;
import domain.NetConnectionManager;


@Path("/ns")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class NSRestService {
	@Context
	private UriInfo uriInfo;

	/******************************************
	 * Network Connection REST API Handler
	 * ****************************************
	 * @param nc
	 * @return
	 */
	/*
	 * Create a NC 
	 */
	@POST
	@Path("nc")
	public Response create_nc(NetConnection nc, @QueryParam("vnfID") String vnfID) {
		if (nc == null)
			throw new BadRequestException();
		NetConnectionManager.add(nc, vnfID);
		URI uri = uriInfo.getAbsolutePathBuilder()
				.path(nc.getVnfID())
				.build();
		return Response.created(uri).build();
	}
	
	/*
	 * @GET - Get a NC by its name
	 */
	@GET
	@Path("/nc/{name}")
	public Response get_nc(@PathParam("name") String name, @QueryParam("type") String type) {
		NetConnection nc = NetConnectionManager.find(name);
		if (nc == null)
			throw new NotFoundException();
		return Response
				.ok(nc, "xml".equals(type) ? MediaType.APPLICATION_XML :MediaType.APPLICATION_JSON )
				.build();
	}
	/*
	 * Update a NC
	 * @param new NC
	 */
	@PUT
	@Path("nc")
	public Response update_nc(NetConnection nc) {
		if (nc == null)
			throw new BadRequestException();
		NetConnectionManager.update(nc);
		URI uri = uriInfo.getAbsolutePathBuilder()
				.path(String.valueOf(nc.getVnfID()))
				.build();
		return Response.created(uri).build();
	}
	
	/*
	 * Delete a NC
	 */
	@DELETE
	@Path("/nc/{name}")
	public Response delete_nc(@PathParam("name") String name, @QueryParam("type") String type) {
		NetConnection nc = NetConnectionManager.find(name);
		if (nc == null) {
			throw new NotFoundException();
		}
		NetConnectionManager.delete(name);
		return Response.noContent().build();
	}
	
	/*
	 * ***************************************
	 *  REST API for Consumption of VNF LCM
	 *  **************************************
	 
	@GET
	@Path("nc/vnfc/{name}")
	public Response find_vl_vnfc(@PathParam("name") String name, @QueryParam("type") String type) {
		VirtualLink vl = NetConnectionManager.find_vl_from_vnfc(name);
		if (vl == null)
			throw new NotFoundException();
		return Response
				.ok(vl, "xml".equals(type) ? MediaType.APPLICATION_XML :MediaType.APPLICATION_JSON )
				.build();
	}
	*/
}
