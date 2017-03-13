package org.asion.sample.mvc;

import org.asion.sample.Sample;
import org.asion.sample.SampleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Sample controller.
 * @author Asion.
 * @since 16/5/29.
 */
@Controller
@RequestMapping("/sample/")
public class SampleController {

    @Autowired
    private SampleManager sampleManager;

    @GetMapping("list")
    public ModelAndView list() {
        Iterable<Sample> samples = sampleManager.findAll();
        return new ModelAndView("sample/list", "samples", samples);
    }

    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        Sample sample = sampleManager.findOne(id);
        return new ModelAndView("sample/view", "sample", sample);
    }

    @GetMapping(params = "form")
    public String createForm(@ModelAttribute Sample sample) {
        return "sample/form";
    }

    @PostMapping(value = "create")
    public ModelAndView create(@Valid Sample sample, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("sample/form", "formErrors", result.getAllErrors());
        }
        sample = sampleManager.save(sample);
        redirect.addFlashAttribute("globalSample", "Successfully save a new sample");
        return new ModelAndView("redirect:/sample/{sample.id}", "sample.id", sample.getId());
    }

    @GetMapping("foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }

    @DeleteMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        sampleManager.delete(id);
        Iterable<Sample> samples = sampleManager.findAll();
        return new ModelAndView("sample/list", "samples", samples);
    }

    @GetMapping(value = "modify/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id) {
        Sample sample = sampleManager.findOne(id);
        return new ModelAndView("sample/form", "sample", sample);
    }

}
