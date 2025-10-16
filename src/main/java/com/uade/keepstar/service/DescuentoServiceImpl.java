package com.uade.keepstar.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.keepstar.entity.Discount;
import com.uade.keepstar.entity.Product;
import com.uade.keepstar.entity.dto.*;
import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.repository.DescuentoRepository;
import com.uade.keepstar.repository.ProductRepository;

@Service
public class DescuentoServiceImpl implements DescuentoService {

    @Autowired
    private DescuentoRepository descuentoRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public DescuentoResponse create(DescuentoRequest request) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(request.getProducto());
        if (product.isEmpty()) {
            throw new ProductNotFoundException();
        }
        Discount descuento = Discount.builder()
                .code(request.getCode())
                .porcentaje(request.getPorcentaje())
                .product(product.get())
                .build();

        descuento = descuentoRepository.save(descuento);
        return new DescuentoResponse(descuento);
    }

    @Override
    public DescuentoResponse getByCode(String code) {
        Optional<Discount> descuento = descuentoRepository.findByCode(code);
        return descuento.map(DescuentoResponse::new).orElse(null);
    }

    @Override
    public ApplyDiscountResponse apply(ApplyDiscountRequest request) {
        double total = request.getOrderTotal() == null ? 0d : request.getOrderTotal();
        if (total <= 0d) {
            return ApplyDiscountResponse.builder()
                    .code(request.getCode())
                    .orderTotal(total)
                    .discountAmount(0d)
                    .totalToPay(total)
                    .description("Total inválido")
                    .build();
        }

        Optional<Discount> opt = descuentoRepository.findByCode(request.getCode());
        if (opt.isEmpty()) {
            return ApplyDiscountResponse.builder()
                    .code(request.getCode())
                    .orderTotal(total)
                    .discountAmount(0d)
                    .totalToPay(total)
                    .description("Código inválido")
                    .build();
        }

        Discount d = opt.get();
        double discountAmount = Math.max(0d, total * (d.getPorcentaje() / 100.0));
        discountAmount = Math.min(discountAmount, total); // no superar el total
        double toPay = total - discountAmount;

        return ApplyDiscountResponse.builder()
                .code(d.getCode())
                .orderTotal(total)
                .discountAmount(discountAmount)
                .totalToPay(toPay)
                .description(d.getPorcentaje() + "% aplicado")
                .build();
    }
}
