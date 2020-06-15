package br.acme.estoque.api;

import static org.springframework.http.HttpStatus.OK;

import br.acme.estoque.models.Product;
import br.acme.estoque.models.Sale;
import br.acme.estoque.services.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockApi
{
    StockService stockService;

    public StockApi(StockService stockService)
    {
        this.stockService = stockService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> list()
    {
        return new ResponseEntity<>(stockService.getProducts(), OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Long> updateStock(@RequestBody Sale sale)
    {
        stockService.updateStock(sale);
        return ResponseEntity.ok().build();
    }
}
