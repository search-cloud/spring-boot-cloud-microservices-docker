package org.asion.sample.mvc;

import org.asion.sample.Sample;
import org.asion.sample.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Rob Winch
 * @author Doo-Hwan Kwak
 */
@Controller
@RequestMapping("/")
public class SampleController {

    @Autowired
    private SampleRepository sampleRepository;

    @RequestMapping
    public ModelAndView list() {
        Iterable<Sample> samples = sampleRepository.findAll();
        return new ModelAndView("samples/list", "samples", samples);
    }

    @RequestMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        Sample sample = sampleRepository.findOne(id);
        return new ModelAndView("samples/view", "sample", sample);
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute Sample sample) {
        return "samples/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@Valid Sample sample, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("samples/form", "formErrors", result.getAllErrors());
        }
        sample = sampleRepository.save(sample);
        redirect.addFlashAttribute("globalSample", "Successfully save a new sample");
        return new ModelAndView("redirect:/{sample.id}", "sample.id", sample.getId());
    }

    @RequestMapping("foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }

    @RequestMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        sampleRepository.delete(id);
        Iterable<Sample> samples = sampleRepository.findAll();
        return new ModelAndView("samples/list", "samples", samples);
    }

    @RequestMapping(value = "modify/{id}", method = RequestMethod.GET)
    public ModelAndView modifyForm(@PathVariable("id") Long id) {
        Sample sample = sampleRepository.findOne(id);
        return new ModelAndView("samples/form", "sample", sample);
    }

}
