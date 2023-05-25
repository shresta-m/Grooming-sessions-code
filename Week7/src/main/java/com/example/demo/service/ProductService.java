package com.example.demo.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.example.demo.exception.ResourceException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService{

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return product.get();
        }
        throw new ResourceException("Product with id "+ id+" not present");
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public Product update(Product product){
        Optional<Product> data = productRepository.findById(product.getId());
        if(data.isEmpty()){
            throw new ResourceException("Product with id "+product.getId()+" not present. So update cannot be performed");
        }
        return productRepository.save(product);
    }

    public void deleteProductById(Long id){
        Optional<Product> data = productRepository.findById(id);
        if(data.isEmpty()){
            throw new ResourceException("Product with id "+ id+" not present. So Deletion cannot be performed");
        }
        productRepository.deleteById(id);;
    }
    
}
