package dev.serverest.factories;

import com.github.javafaker.Faker;
import dev.serverest.models.Product;

import java.util.UUID;

public class ProductDataFactory {

    private final Faker faker = new Faker();

    public Product validProduct() {
        Product product = new Product();
        product.setNome("Produto " + UUID.randomUUID().toString().replace("-", "").substring(0, 10));
        product.setPreco(faker.number().numberBetween(1000, 100000));
        product.setDescricao(faker.lorem().sentence());
        product.setQuantidade(faker.number().numberBetween(1, 100));
        return product;
    }
}
