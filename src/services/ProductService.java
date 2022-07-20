//package services;
//
//import java.util.Collection;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.ServletContext;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//
//import beans.Product;
//import dao.ProductDAO;
//
//@Path("/products")
//public class ProductService {
//	
//	@Context
//	ServletContext ctx;
//	
//	public ProductService() {
//	}
//	
//	@PostConstruct
//	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
//	public void init() {
//		// Ovaj objekat se instancira više puta u toku rada aplikacije
//		// Inicijalizacija treba da se obavi samo jednom
//		if (ctx.getAttribute("productDAO") == null) {
//	    	String contextPath = ctx.getRealPath("");
//			ctx.setAttribute("productDAO", new ProductDAO(contextPath));
//		}
//	}
//	
//	@GET
//	@Path("/")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Collection<Product> getProducts() {
//		ProductDAO dao = (ProductDAO) ctx.getAttribute("productDAO");
//		return dao.findAll();
//	}
//	
//	@POST
//	@Path("/")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Product newProduct(Product product) {
//		ProductDAO dao = (ProductDAO) ctx.getAttribute("productDAO");
//		return dao.save(product);
//	}
//
//	@GET
//	@Path("/{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Product findOne(@PathParam("id") String id) {
//		ProductDAO dao = (ProductDAO) ctx.getAttribute("productDAO");
//		return dao.findProduct(id);
//	}
//	
//	// rest/products/search?name=Proizvod2
//	@GET
//	@Path("/search")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Product search(@QueryParam("name") String name) {
//		ProductDAO dao = (ProductDAO) ctx.getAttribute("productDAO");
//		return dao.findAll().stream()
//				.filter(product -> product.getName().equals(name))
//				.findFirst()
//				.orElse(null);
//	}
//	
//	@PUT
//	@Path("/{id}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Product changeOne(Product product, @PathParam("id") String id) {
//		ProductDAO dao = (ProductDAO) ctx.getAttribute("productDAO");
//		return dao.change(product);
//	}
//	
//	@DELETE
//	@Path("/{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Product deleteProduct(@PathParam("id") String id) {
//		ProductDAO dao = (ProductDAO) ctx.getAttribute("productDAO");
//		return dao.delete(id);
//	}
//	
//}
