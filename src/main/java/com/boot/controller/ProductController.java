package com.boot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.entity.Product;
import com.boot.service.ProductService;

import java.util.List;
import java.util.Optional;
/**
 * The @RestController annotation specifies that this class is a controller for RESTful API requests. 
 * The @RequestMapping annotation specifies the base URL for all requests handled by this controller.
 */

 //Next, we need to add methods to handle HTTP requests. In this example, we will add methods to handle
 // GET, POST, PUT, and DELETE requests.
@RestController
@RequestMapping("/api/v1")
public class ProductController {
     private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * The @PostMapping annotation is used to indicate that this class will handle HTTP Post requests and 
     * return the response as JSON. It is used to map the /api/v1/products path to this class.
     * @RequestBody is an annotation in Spring Framework used to bind the HTTP request body to a parameter in a controller method.
     *  When a client sends an HTTP POST or PUT request, it may include data in the request body. This data is typically in JSON or
     *  XML format and contains information about the resource being created or updated.
     */

     // Create a new product
    @PostMapping("/product")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(savedProduct);
    }
    //for Get Request all the products, we will be using the following code:
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.fetchAllProducts();
        return ResponseEntity.ok(products);

    }
    /**
     * The @GetMapping annotation is used to indicate that this class will handle HTTP Get requests and return the response as JSON.
     *  It is used to map the /api/v1/products path to this class. Here the getAllProducts() method fetches all the 
     * products and has a path /products.
     */

     //for Get Request of a single product, we will be using the following code:

     // Get a product by ID
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.fetchProductById(id);
        return productOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * The@PathVariable annotation is used to extract data from the URL path of an HTTP request. 
     * It is used to capture dynamic segments of a URL and map them to a method parameter in a Spring Boot controller. 
     * The getProductById() method is used to get a product by id and has a path /products/{id}.
     */

     /**
      * In this example, we’ve added a @PutMapping annotation for the updateProduct() method. 
      The @PutMapping annotation is used to map HTTP PUT requests to the /product/{id} endpoint, 
      where {id} is a path variable for the product ID. The @RequestBody annotation is used to 
      bind the request body to the product parameter in the method. When a PUT request is made to /api/v1/product/{id},
    the updateProduct() method will be executed with the id parameter set to the product ID from the URL path and the product.
      */
      
      //For Update Requests, we will be using the following code:
      // Update a product
   @PutMapping(path = "/product/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> updatedProductOptional = productService.updateProduct(id, product);
        return updatedProductOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //For Delete Requests, we will be using the following code:
    //Delte a product
 @DeleteMapping(value = "/product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        boolean deletionStatus = productService.deleteProduct(id);
        if (deletionStatus) {
            return ResponseEntity.ok("Product with ID " + id + " has been deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete product with ID " + id);
        }
    }

/**
 * Now, we are completed with the programming side and just remain with the database and then test the endpoints and then we are done.
First of all, we will have to configure MySql in our application.properties files.
we will add the following code in the application.properties file
    
        spring.datasource.url=jdbc:mysql://localhost:3306/name of your database
        spring.datasource.username=your username for mysql
        spring.datasource.password=your password for mysql 
        spring.jpa.hibernate.ddl-auto=create-drop

 */

    
}
