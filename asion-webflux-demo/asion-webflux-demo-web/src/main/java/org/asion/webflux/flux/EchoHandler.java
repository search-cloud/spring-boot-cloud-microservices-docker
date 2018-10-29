package org.asion.webflux.flux;

import org.asion.webflux.Webflux;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class EchoHandler {

	public Mono<ServerResponse> findOne(ServerRequest request) {
		return ServerResponse.ok().body(request.bodyToMono(Webflux.class), Webflux.class);
	}

}