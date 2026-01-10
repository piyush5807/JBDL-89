package another_package;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${custom.prop}")
    private Integer customProp;

    public TestController() {
        System.out.println("Inside another_package.TestController constructor");
    }
}
