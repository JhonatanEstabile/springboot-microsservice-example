package br.acme.estoque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.acme.estoque.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
}
