package org.raghu.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

	@GET
	@Path("/annotations")
	public String getParamsUsingAnnotations(@MatrixParam("param") String matrixParam,
			@HeaderParam("customHeaderValue") String header, @CookieParam("cookieName") String cookie) {
		return "test " + matrixParam + " Header " + header + " cookieName " + cookie;

	}

	@GET
	@Path("/context")
	public String getParamsUsingContext(@Context UriInfo urifInfo, @Context HttpHeaders headers) {
		return "Current Path " + urifInfo.getAbsolutePath().toString() + " Cookie Information "
				+ headers.getCookies().toString();

	}

}
