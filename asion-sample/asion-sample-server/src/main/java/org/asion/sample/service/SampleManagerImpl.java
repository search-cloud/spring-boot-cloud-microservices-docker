package org.asion.sample.service;

import com.alibaba.dubbo.config.annotation.DubboService;
import org.asion.sample.Sample;
import org.asion.sample.SampleManager;
import org.asion.sample.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Asion.
 * @since 16/5/29.
 */
@Component("sampleManager")
@DubboService(interfaceClass = SampleManager.class)
public class SampleManagerImpl implements SampleManager {

    @Autowired
    private SampleRepository sampleRepository;

    @Override
    public Sample save(Sample sample) {
        return sampleRepository.save(sample);
    }

    @Override
    public Sample findOne(Long id) {
        return sampleRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        sampleRepository.delete(id);
    }

    @Override
    public Iterable<Sample> findAll() {
        return sampleRepository.findAll();
    }
}
