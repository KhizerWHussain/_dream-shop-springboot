package com.spring.practice.dreamshop.service.product;

import java.util.List;
import com.spring.practice.dreamshop.dto.ProductDTO;
import com.spring.practice.dreamshop.model.Product;
import com.spring.practice.dreamshop.request.AddProduct;
import com.spring.practice.dreamshop.request.UpdateProduct;

public interface IProductService {
    Product create(AddProduct product);

    List<Product> get();

    Product read(Long id);

    void delete(Long id);

    Product update(UpdateProduct product, Long product_id);

    List<Product> getByCategory(String category);

    List<Product> getByBrand(String brand);

    List<Product> getByCategoryAndBrand(String category, String brand);

    List<Product> getByName(String name);

    List<Product> getByBrandAndName(String brand, String name);

    Long countByBrandAndName(String brand, String name);

    ProductDTO convertToDTO(Product product);

    List<ProductDTO> getConvertedProducts(List<Product> products);
}
