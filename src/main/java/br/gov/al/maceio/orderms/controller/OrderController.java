package br.gov.al.maceio.orderms.controller;

import br.gov.al.maceio.orderms.controller.dto.ApiResponse;
import br.gov.al.maceio.orderms.controller.dto.OrderResponse;
import br.gov.al.maceio.orderms.controller.dto.PaginationResponse;
import br.gov.al.maceio.orderms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(@PathVariable("customerId") Long customerId,
                                                                 @RequestParam(defaultValue = "0", name = "page") Integer page,
                                                                 @RequestParam(defaultValue = "10", name = "size") Integer size) {

        var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, size));
        var totalOnOrders = orderService.findTotalOnOrdersByCustomerId(customerId);
        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOnOrders", totalOnOrders),
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }
}
