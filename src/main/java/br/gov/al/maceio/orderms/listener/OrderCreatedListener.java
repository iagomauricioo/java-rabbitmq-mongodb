package br.gov.al.maceio.orderms.listener;

import br.gov.al.maceio.orderms.listener.dto.OrderCreatedEvent;
import br.gov.al.maceio.orderms.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


import static br.gov.al.maceio.orderms.config.RabbitMqConfig.ORDER_QUEUE;

@Component
public class OrderCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = ORDER_QUEUE)
    public void listen(Message<OrderCreatedEvent> message) {
        logger.info("Message consumed: {}", message);
        orderService.save(message.getPayload());
    }

}
