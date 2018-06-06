package org.asion.webflux.service;

import com.alibaba.dubbo.config.annotation.DubboService;
import org.asion.webflux.Webflux;
import org.asion.webflux.WebfluxManager;
import org.asion.webflux.WebfluxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Asion.
 * @since 16/5/29.
 */
@Component
@DubboService(interfaceClass = WebfluxManager.class)
public class WebfluxManagerImpl implements WebfluxManager {

    private final WebfluxRepository sampleRepository;

    @Autowired
    public WebfluxManagerImpl(WebfluxRepository sampleRepository) {this.sampleRepository = sampleRepository;}

    @Override
    public Webflux save(Webflux sample) {
        return sampleRepository.save(sample);
    }

    @Override
    public Webflux findOne(Long id) {
        return sampleRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        sampleRepository.delete(id);
    }

    @Override
    public Iterable<Webflux> findAll() {
        return sampleRepository.findAll();
    }
}
