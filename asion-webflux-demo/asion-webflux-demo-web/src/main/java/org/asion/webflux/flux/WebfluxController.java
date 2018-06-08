package org.asion.webflux.flux;

import org.asion.webflux.Webflux;
import org.asion.webflux.WebfluxManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author Asion.
 * @since 2018/6/1.
 */
@RestController
@RequestMapping("/demo/")
public class WebfluxController {

    private final WebfluxManager webfluxManager;

    @Autowired
    public WebfluxController(WebfluxManager webfluxManager) {this.webfluxManager = webfluxManager;}

    @GetMapping("{id}")
    @ResponseBody
    public Webflux view(@PathVariable("id") Long id) {
        return webfluxManager.findOne(id);
    }

    @GetMapping("{id}/flux")
    public Mono<Webflux> flux(@PathVariable("id") Long id) {
        return Mono.empty();
    }
}
