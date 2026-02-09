package demo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component(value = "quick_order")
@Primary
public class QuickOrder implements OrderService{

    @Override
    public void createOrder() {

    }
}
