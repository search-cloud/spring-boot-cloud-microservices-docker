package org.asion.search.mvc;

import org.asion.search.SearchSample;
import org.asion.search.SearchSampleRepository;
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
 * @author Asion
 * @since 16/5/5.
 */
@Controller
@RequestMapping("/")
public class SearchController {

    @Autowired
    private SearchSampleRepository sampleRepository;

    @RequestMapping
    public ModelAndView list() {
        Iterable<SearchSample> samples = sampleRepository.findAll();
        return new ModelAndView("samples/list", "samples", samples);
    }

    @RequestMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        SearchSample sample = sampleRepository.findOne(id);
        return new ModelAndView("samples/view", "sample", sample);
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute SearchSample sample) {
        return "samples/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@Valid SearchSample sample, BindingResult result, RedirectAttributes redirect) {
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
        Iterable<SearchSample> samples = sampleRepository.findAll();
        return new ModelAndView("samples/list", "samples", samples);
    }

    @RequestMapping(value = "modify/{id}", method = RequestMethod.GET)
    public ModelAndView modifyForm(@PathVariable("id") Long id) {
        SearchSample sample = sampleRepository.findOne(id);
        return new ModelAndView("samples/form", "sample", sample);
    }

}
