package demo;

import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    @Qualifier("quick_order") // this annotation tells spring boot to inject quickOrder bean as a dependency in this class
    OrderService orderService;

    @Autowired
    @Qualifier("scheduleOrder")
    OrderService scheduleOrderService;

    @PostMapping("/order")
    public void createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        System.out.println("Inside createOrder ... " + this.orderService + " " + this.scheduleOrderService);

        if(createOrderRequest.getOrderType().equals(OrderType.NOW)){
            this.orderService.createOrder();
        }else{
            this.scheduleOrderService.createOrder();
        }
    }
}
