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

import domain.ConnectionPoint;
import domain.ConnectionPointManager;
import domain.NetConnection;
import domain.NetConnectionManager;
import domain.VirtualLink;
import domain.VirtualLinkManager;

@Path("/ns")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class NSRestService {
	@Context
	private UriInfo uriInfo;
	/*
	 **************************************
	 * VL REST API HANDLERS
	 **************************************
	 */
	
	/*
	 * Create a VL
	 * @param VL 
	 */
	@POST
	@Path("vl")
	public Response create(VirtualLink vl) {
		if (vl == null)
			throw new BadRequestException();
		VirtualLinkManager.add(vl);
		URI uri = uriInfo.getAbsolutePathBuilder()
				.path(vl.getName())
				.build();
		return Response.created(uri).build();
	}
	
	/*
	 * Get a vl
	 * @param id of the vl to retrieve
	 * @return the status and vl
	 */
	@GET
	@Path("/vl/{name}")
	public Response get(@PathParam("name") String name, @QueryParam("type") String type) {
		VirtualLink vl = VirtualLinkManager.find(name);
		if (vl == null)
			throw new NotFoundException();
		return Response
				.ok(vl, "xml".equals(type) ? MediaType.APPLICATION_XML :MediaType.APPLICATION_JSON )
				.build();
	}
	
	/*
	 * Update a vl
	 * @param new VL
	 */
	@PUT
	@Path("vl")
	public Response update(VirtualLink vl) {
		if (vl == null)
			throw new BadRequestException();
		VirtualLinkManager.update(vl);
		URI uri = uriInfo.getAbsolutePathBuilder()
				.path(String.valueOf(vl.getName()))
				.build();
		return Response.created(uri).build();
	}
	
	/*
	 * Delete a vl
	 */
	@DELETE
	@Path("/vl/{name}")
	public Response delete(@PathParam("name") String name, @QueryParam("type") String type) {
		VirtualLink vl = VirtualLinkManager.find(name);
		if (vl == null) {
			throw new NotFoundException();
		}
		VirtualLinkManager.delete(name);
		return Response.noContent().build();
	}
	/***************************************
	 *   ConnectionPoint REST API Handler
	 ***************************************
	 */
	
	/*
	 * Create a CP 
	 */
	@POST
	@Path("cp")
	public Response create_cp(ConnectionPoint cp) {
		if (cp == null)
			throw new BadRequestException();
		ConnectionPointManager.add(cp);
		URI uri = uriInfo.getAbsolutePathBuilder()
				.path(cp.getName())
				.build();
		return Response.created(uri).build();
	}
	
	/*
	 * @GET - Get a CP by its name
	 */
	@GET
	@Path("/cp/{name}")
	public Response get_cp(@PathParam("name") String name, @QueryParam("type") String type) {
		ConnectionPoint cp = ConnectionPointManager.find(name);
		if (cp == null)
			throw new NotFoundException();
		return Response
				.ok(cp, "xml".equals(type) ? MediaType.APPLICATION_XML :MediaType.APPLICATION_JSON )
				.build();
	}
	/*
	 * Update a cp
	 * @param new Cp
	 */
	@PUT
	@Path("cp")
	public Response update_cp(ConnectionPoint cp) {
		if (cp == null)
			throw new BadRequestException();
		ConnectionPointManager.update(cp);
		URI uri = uriInfo.getAbsolutePathBuilder()
				.path(String.valueOf(cp.getName()))
				.build();
		return Response.created(uri).build();
	}
	
	/*
	 * Delete a cp
	 */
	@DELETE
	@Path("/cp/{name}")
	public Response delete_cp(@PathParam("name") String name, @QueryParam("type") String type) {
		ConnectionPoint cp = ConnectionPointManager.find(name);
		if (cp == null) {
			throw new NotFoundException();
		}
		ConnectionPointManager.delete(name);
		return Response.noContent().build();
	}
	
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
	public Response create_nc(NetConnection nc) {
		if (nc == null)
			throw new BadRequestException();
		NetConnectionManager.add(nc);
		URI uri = uriInfo.getAbsolutePathBuilder()
				.path(nc.getName())
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
				.path(String.valueOf(nc.getName()))
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
	 */
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
	
}
