package com.ecommerce.api.services;

import com.ecommerce.api.dto.request.OrdersRequest;
import com.ecommerce.api.dto.request.PaymentRequest;
import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.response.CarDTO;
import com.ecommerce.api.dto.response.PaymentsDTO;
import com.ecommerce.api.dto.response.ProductDTO;
import com.ecommerce.api.persistence.entities.Orders;
import com.ecommerce.api.persistence.entities.Users;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Payments;
import com.ecommerce.api.persistence.interfaces.CrudPayments;
import com.ecommerce.api.persistence.repository.RepositoryPayment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentServices implements CrudPayments {
    @Autowired
    private RepositoryPayment repositoryPayment;
    @Autowired
    private OrdersServices  ordersServices;
    @Autowired
    private CarServices  carServices;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void createPayment(Long userId) {
        List<CarDTO> car = carServices.getCart(userId);

        if (car.isEmpty()) {
            throw new IllegalArgumentException("No hay productos");
        }

        // Calcular el precio total CORRECTAMENTE
        BigDecimal carPriceProduct = car.stream()
                .flatMap(carDTO -> carDTO.getProductId().stream()) // Aplanar la lista de productos
                .map(ProductDTO::getPrice) // Obtener el precio del ProductDTO
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Obtener los IDs de productos CORRECTAMENTE
        Set<Long> productIds = car.stream()
                .flatMap(carDTO -> carDTO.getProductId().stream()) // Aplanar la lista de productos
                .map(ProductDTO::getId) // Obtener el ID del ProductDTO
                .collect(Collectors.toSet());

        OrdersRequest order = OrdersRequest.builder()
                .userId(userId)
                .orderDate(LocalDate.now())
                .status("Pendiente")
                .totalAmount(carPriceProduct)
                .productRequest(productIds) // Usar el Set<Long>
                .build();

        ordersServices.save(order);
        carServices.clearCart(userId);

        rabbitTemplate.convertAndSend("payment_queue", order);
    }

    @Override
    public List<PaymentsDTO> findAll() {
       return repositoryPayment.findAll().stream().map(x-> PaymentsDTO.builder()
                .id(x.getId())
                .orderId(x.getOrderId().getId())
                .userId(x.getUserId().getId())
                .paymentMethod(x.getPaymentMethod())
                .paymentAmount(x.getPaymentAmount())
                .paymentStatus(x.getPaymentStatus())
                .paymentCurrency(x.getPaymentCurrency())
                .paymentDate(x.getPaymentDate())
                .transactionId(x.getTransactionId())
                .createdAt(x.getCreatedAt())
                .build()).toList();
    }

    @Override
    public PaymentsDTO findById(Long id) {


        return PaymentsDTO.builder()
                .id(repositoryPayment.findById(id).get().getId())
                .orderId(repositoryPayment.findById(id).get().getOrderId().getId())
                .userId(repositoryPayment.findById(id).get().getUserId().getId())
                .paymentMethod(repositoryPayment.findById(id).get().getPaymentMethod())
                .paymentAmount(repositoryPayment.findById(id).get().getPaymentAmount())
                .paymentStatus(repositoryPayment.findById(id).get().getPaymentStatus())
                .paymentCurrency(repositoryPayment.findById(id).get().getPaymentCurrency())
                .paymentDate(repositoryPayment.findById(id).get().getPaymentDate())
                .transactionId(repositoryPayment.findById(id).get().getTransactionId())
                .createdAt(repositoryPayment.findById(id).get().getCreatedAt())
                .build();
    }

    @Override
    public void updatePayment(PaymentRequest payment, Long id) {

        Users user = Users.builder()
                .id(payment.getUserId())
                .build();

        repositoryPayment.findById(id).ifPresent(existingPayment -> {
            existingPayment.setUserId(user);
            existingPayment.setPaymentMethod(payment.getPaymentMethod());
            existingPayment.setPaymentAmount(payment.getPaymentAmount());
            existingPayment.setPaymentStatus(payment.getPaymentStatus());
            existingPayment.setPaymentCurrency(payment.getPaymentCurrency());
            existingPayment.setPaymentDate(payment.getPaymentDate());
            existingPayment.setTransactionId(payment.getTransactionId());
            repositoryPayment.save(existingPayment);
        });
    }

    @Override
    public List<PaymentsDTO> findByUserId(Long userId) {
               Users user = Users.builder()
                .id(userId)
                .build();


        return repositoryPayment.findByUserId(user).stream().map(x-> PaymentsDTO.builder()
         .id(x.getId())
         .orderId(x.getOrderId().getId())
         .userId(x.getUserId().getId())
         .paymentMethod(x.getPaymentMethod())
         .paymentAmount(x.getPaymentAmount())
         .paymentStatus(x.getPaymentStatus())
         .paymentCurrency(x.getPaymentCurrency())
         .paymentDate(x.getPaymentDate())
         .transactionId(x.getTransactionId())
         .createdAt(x.getCreatedAt())
         .build()).toList();
    }

    @Override
    public List<PaymentsDTO> findByPaymentMethod(String paymentMethod) {
               return repositoryPayment.findAll().stream().filter(x->x.getPaymentMethod().equals(paymentMethod)).map(x-> PaymentsDTO.builder()
                .id(x.getId())
                .orderId(x.getOrderId().getId())
                .userId(x.getUserId().getId())
                .paymentMethod(x.getPaymentMethod())
                .paymentAmount(x.getPaymentAmount())
                .paymentStatus(x.getPaymentStatus())
                .paymentCurrency(x.getPaymentCurrency())
                .paymentDate(x.getPaymentDate())
                .transactionId(x.getTransactionId())
                .createdAt(x.getCreatedAt())
                .build()).toList();

    }

    @Override
    public List<PaymentsDTO> findByPaymentStatus(String paymentStatus) {
               return repositoryPayment.findAll().stream().filter(x->x.getPaymentStatus().equals(paymentStatus)).map(x-> PaymentsDTO.builder()
                .id(x.getId())
                .orderId(x.getOrderId().getId())
                .userId(x.getUserId().getId())
                .paymentMethod(x.getPaymentMethod())
                .paymentAmount(x.getPaymentAmount())
                .paymentStatus(x.getPaymentStatus())
                .paymentCurrency(x.getPaymentCurrency())
                .paymentDate(x.getPaymentDate())
                .transactionId(x.getTransactionId())
                .createdAt(x.getCreatedAt())
                .build()).toList();
    }

    @Override
    public List<PaymentsDTO> findByPaymentDate(LocalDate paymentDate) {
               return repositoryPayment.findAll().stream().filter(x->x.getPaymentDate().equals(paymentDate)).map(x-> PaymentsDTO.builder()
                .id(x.getId())
                .orderId(x.getOrderId().getId())
                .userId(x.getUserId().getId())
                .paymentMethod(x.getPaymentMethod())
                .paymentAmount(x.getPaymentAmount())
                .paymentStatus(x.getPaymentStatus())
                .paymentCurrency(x.getPaymentCurrency())
                .paymentDate(x.getPaymentDate())
                .transactionId(x.getTransactionId())
                .createdAt(x.getCreatedAt())
                .build()).toList();
    }

    @Override
    public List<PaymentsDTO> findByOrderId(Long orderId) {
        Orders orders = Orders.builder()
                .id(orderId)
                .build();
        return repositoryPayment.findAll().stream().filter(x->x.getOrderId().equals(orders)).map(x-> PaymentsDTO.builder()
                .id(x.getId())
                .orderId(x.getOrderId().getId())
                .userId(x.getUserId().getId())
                .paymentMethod(x.getPaymentMethod())
                .paymentAmount(x.getPaymentAmount())
                .paymentStatus(x.getPaymentStatus())
                .paymentCurrency(x.getPaymentCurrency())
                .paymentDate(x.getPaymentDate())
                .transactionId(x.getTransactionId())
                .createdAt(x.getCreatedAt())
                .build()).toList();

    }

    @Override
    public void deletePayment(Long id) {
        repositoryPayment.deleteById(id);
    }

    @Override
    public void savePayment(PaymentRequest paymentRequest) {
            Payments payment = Payments.builder()
                .id(paymentRequest.getId())
                .paymentMethod(paymentRequest.getPaymentMethod())
                .paymentAmount(paymentRequest.getPaymentAmount())
                .paymentStatus(paymentRequest.getPaymentStatus())
                .paymentCurrency(paymentRequest.getPaymentCurrency())
                .paymentDate(paymentRequest.getPaymentDate())
                .transactionId(paymentRequest.getTransactionId())
                .createdAt(paymentRequest.getCreatedAt())
                .build();
        repositoryPayment.save(payment);
    }

}
