package com.ybe.tr1ll1on.domain.order.service;

import com.ybe.tr1ll1on.domain.order.dto.request.OrderRequest;
import com.ybe.tr1ll1on.domain.order.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse order(OrderRequest orderRequest);
}
