package io.github.kardeiz.example.webapp

import javax.ws.rs._
import javax.ws.rs.core.{Context, Response, UriInfo}
import javax.annotation._

import javax.servlet.http.{HttpServletRequest, HttpServletResponse, HttpServlet}

import javax.servlet.ServletConfig
import javax.servlet.ServletContext

class Config extends org.glassfish.jersey.server.ResourceConfig {
  register(classOf[Router])
}

@Path("/") 
class Router { 

  @Context var request: HttpServletRequest = _
  @Context var response: HttpServletResponse = _
  @Context var servletContext: ServletContext = _
  @Context var uriInfo: UriInfo = _

  @PostConstruct def before { flash.rotateIn }
  @PreDestroy def after { flash.rotateOut }

  object flash {
    val KeyNow  = "local.flash.now"
    val KeyNext = "local.flash.next"

    case class Wrapper(wrapped: String)

    def rotateIn {
      for {
        session <- Option(request.getSession(false))
        obj     <- Option(session.getAttribute(KeyNext))
      } {
        request.setAttribute(KeyNow, obj)
        session.removeAttribute(KeyNext)
      }
    }

    def rotateOut {
      for (obj <- Option(request.getAttribute(KeyNext))) {
        request.getSession.setAttribute(KeyNext, obj)
      }
    }

    def now = Option(request.getAttribute(KeyNow)) match {
      case Some(x: Wrapper) => x.wrapped
      case Some(x) if x.isInstanceOf[Wrapper] => "Something's wrong"
      case _ => "NOPE"
    }

    def next(value: String) {
      request.setAttribute(KeyNext, Wrapper(value))
    }
  }

  @Path("/flash/set") @GET def flashSet: Response = {
    flash.next("I'm set!")
    Response.seeOther(new java.net.URI("/flash/get")).build
  }

  @Path("/flash/get") @GET def flashGet: Response = {
    Response.status(200).entity(flash.now).build
  }

}

