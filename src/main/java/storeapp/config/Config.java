package storeapp.config;

import storeapp.repository.AdminRepository;
import storeapp.repository.CategoryRepository;
import storeapp.repository.CustomerRepository;
import storeapp.repository.ProductRepository;
import storeapp.services.AdminServiceImpl;
import storeapp.services.AuthContext;
import storeapp.services.CategoryServiceImpl;
import storeapp.services.CustumerService;
import storeapp.services.CustumerServiceImpl;
import storeapp.services.ProductServiceImpl;
import storeapp.userinterface.MenuApp;
import storeapp.view.AdminView;
import storeapp.view.CategoryView;
import storeapp.view.CustomerView;
import storeapp.view.ProductView;

public class Config {

    private Config() {
    }

    public static MenuApp createMenuApp() {
        // Repositorios
        CustomerRepository customerRepository = new CustomerRepository();
        AdminRepository adminRepository = new AdminRepository();
        CategoryRepository categoryRepository = new CategoryRepository();
        ProductRepository productRepository = new ProductRepository();

        // Servicios
        CustumerService customerService = new CustumerServiceImpl(customerRepository);
        AuthContext authContext = new AuthContext();
        AdminServiceImpl adminService = new AdminServiceImpl(customerRepository, adminRepository, authContext);
        CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository);
        ProductServiceImpl productService = new ProductServiceImpl(productRepository, categoryRepository);

        // Vistas
        CustomerView customerView = new CustomerView(customerService);
        AdminView adminView = new AdminView(adminService);
        CategoryView categoryView = new CategoryView(categoryService);
        ProductView productView = new ProductView(productService);

        // MenuApp con todas las dependencias necesarias
        return new MenuApp(customerView, adminView, productView, categoryView, adminService, adminRepository);
    }
}


