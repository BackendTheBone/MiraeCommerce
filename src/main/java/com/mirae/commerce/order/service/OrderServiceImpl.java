package com.mirae.commerce.order.service;

import com.mirae.commerce.common.utils.ListUtil;
import com.mirae.commerce.member.entity.Member;
import com.mirae.commerce.member.repository.MemberRepository;
import com.mirae.commerce.order.dto.AddOrderRequest;
import com.mirae.commerce.order.entity.Order;
import com.mirae.commerce.order.entity.OrderItem;
import com.mirae.commerce.order.repository.OrderRepository;
import com.mirae.commerce.order.vo.OrderItemVO;
import com.mirae.commerce.payment.entity.Payment;
import com.mirae.commerce.payment.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderItemService orderItemService;
    private final PaymentService paymentService;

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    public long addOrderRequest(AddOrderRequest addOrderRequest) {
        String username = addOrderRequest.getUsername();
        String address = addOrderRequest.getAddress();
        List<OrderItemVO> orderItemVOs = addOrderRequest.getOrderItemVOs();
        Order order = getUnfinishedOrder(username, address);

        List<OrderItem> orderItems = ListUtil.applyFunctionList(orderItemVOs, item -> item.toEntity(order));
        int totalPrice = orderItems.stream().mapToInt(OrderItem::getPrice).sum();

        Payment payment = paymentService.requestPayment(order, totalPrice);
        order.linkPaymentId(payment.getId());

        // TODO. ENUM 바꾸기
        if(payment.getStatus().equals("1")){
            order.changeStatus("1");
        }
        else {
            order.changeStatus("2");
        }
        add(order);
        return orderItemService.addOrderItems(orderItems);
    }

    private Order getUnfinishedOrder(String username, String address){
        Member member = memberRepository.findByUsername(username);
        // TODO. ENUM 바꾸기
        Order order = Order.builder().
                memberId(member.getMemberId()).
                orderedAt(LocalDateTime.now()).
                address(address).
                status("0").
                build();
        return add(order);
    }

    private Order add(Order order){
        return orderRepository.save(order);
    }
}
