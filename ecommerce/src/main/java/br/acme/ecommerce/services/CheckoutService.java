package br.acme.ecommerce.services;

import br.acme.ecommerce.configuration.RabbitConfigure;
import br.acme.ecommerce.models.Sale;
import br.acme.ecommerce.models.StockProduct;
import br.acme.ecommerce.repositories.SalesRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CheckoutService
{
    private static final String URL = "http://localhost:8090/stock/updateStock";

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void saleProduct(final Sale sale)
    {
        salesRepository.save(sale);
        updateStock(sale, true);
    }

    private void updateStock(Sale sale, boolean async)
    {
        StockProduct stockProduct = new StockProduct();

        stockProduct.setId(sale.getIdProduct());
        stockProduct.setQuantity(sale.getAmount());

        if (async) {
            rabbitTemplate.convertAndSend(RabbitConfigure.SALE_EX, "", stockProduct);
            return;
        }

        restTemplate.postForObject("http://localhost:8090/stock/update", stockProduct, StockProduct.class);
    }
}