package br.acme.estoque.services;

import br.acme.estoque.configuration.RabbitConfigure;
import br.acme.estoque.models.Product;
import br.acme.estoque.models.Sale;
import br.acme.estoque.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService
{
    Logger logger = LoggerFactory.getLogger(StockService.class);

    ProductRepository productRepository;

    Environment environment;

    public StockService(ProductRepository productRepository, Environment enviroment)
    {
        this.productRepository = productRepository;
        this.environment = enviroment;
    }

    public List<Product> getProducts()
    {
        logger.info("StockService.getProducts= " + environment.getProperty("local.server.port"));
        return productRepository.findAll();
    }

    public void updateStock(Sale sale)
    {
        Optional<Product> product = productRepository.findById(sale.getId());

        if (product.isPresent()) {
            Product registeredProduct = product.get();
            registeredProduct.setStock(registeredProduct.getStock() - sale.getQuantity());
            productRepository.save(registeredProduct);
        }
    }

    @RabbitListener(queues = RabbitConfigure.SALE_QUEUE)
    public void consumer(Sale sale)
    {
        updateStock(sale);
    }
}
