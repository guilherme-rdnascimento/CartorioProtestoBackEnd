package br.edu.ifpe.pdsc_modelo.rest;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.util.List;

import javax.naming.NamingException;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import br.edu.ifpe.pdsc_modelo.ejb.Login;
import br.edu.ifpe.pdsc_modelo.entidades.User;
import br.edu.ifpe.pdsc_modelo.filter.JWTTokenNeeded;
import br.edu.ifpe.pdsc_modelo.util.ClientUtility;
import br.edu.ifpe.pdsc_modelo.util.PasswordUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 

@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Transactional
public class UserEndpoint {

	@POST
	@Path("/login")
	public Response authenticateUser(String usuario) {

		try {
			JSONObject jo = (JSONObject) new JSONParser().parse(usuario); 
	          
	        String login = jo.get("login").toString();
	        String senha = jo.get("senha").toString();
	        
			Login loginbean = ClientUtility.getLoginBean();
			User user = loginbean.login(login, PasswordUtils.digestPassword(senha));
			
			if (user == null)
				throw new SecurityException("Invalid user/password");

			// Issue a token for the user
			String token = Jwts.builder()
					.setSubject(login)
					.claim("custom", "myCustom")
					.signWith(SignatureAlgorithm.HS256,
						DatatypeConverter.parseBase64Binary("tmMY7VZuZ1DrsTF8JNImtiZ6Im6nx+2lLMEWhvRHneE="))
					.compact();

			user.setToken(token);
			loginbean.updateUser(user);
			
			// Return the token on the response
			return Response.ok().header(AUTHORIZATION, "Bearer " + token)
					.header("Access-Control-Expose-Headers", "*").build();

		} catch (SecurityException e) {
			return Response.status(FORBIDDEN).build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.status(UNAUTHORIZED).build();
		}
	}


	@POST
	public Response create(String usuario) {
		try {
			JSONObject jo = (JSONObject) new JSONParser().parse(usuario); 
	          
	        String nome = jo.get("nome").toString();
	        String senha = jo.get("senha").toString();
	        String login = jo.get("login").toString();
	        String email = jo.get("email").toString();
			
			Login loginbean = ClientUtility.getLoginBean();
			
			User user = loginbean.cadastrarUsuario(nome, PasswordUtils.digestPassword(senha), login, email);
			if (user != null)
				return Response.ok(user).build();
		} catch (NamingException | ParseException e) {
			e.printStackTrace();
		}
		return Response.status(NOT_FOUND).build();
	}

	@GET
	@JWTTokenNeeded
	@Path("/{id}")
	public Response findById(@PathParam("id") int id) {
		try {
			Login loginbean = ClientUtility.getLoginBean();
			User user = loginbean.getUsuario(id);
			if (user != null)
				return Response.ok(user).build();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(NOT_FOUND).build();
	}

	@GET
	@JWTTokenNeeded
	public Response findAllUsers() {
		try {
			Login loginbean = ClientUtility.getLoginBean();
			List<User> allUsers = loginbean.getAllUsers();
			if (allUsers != null)
				return Response.ok(allUsers).build();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(NOT_FOUND).build();
	}
	
}