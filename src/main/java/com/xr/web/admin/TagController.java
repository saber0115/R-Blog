package com.xr.web.admin;

import com.xr.pojo.Tag;
import com.xr.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tagList(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.ASC)
                                      Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String inserPage(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        Tag tag = tagService.getTag(id);
        model.addAttribute("tag",tag);
        return "admin/tags-input";
    }

    @PostMapping("/tags/save")
    public String save(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes){
        Tag tag1 = tagService.getByName(tag.getName());
        if(tag1 != null){
            result.rejectValue("name","nameError","该标签已经存在，不能重复添加。");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag tag2 =tagService.saveTag(tag);
        if(tag2 == null){
            redirectAttributes.addFlashAttribute("message","新增标签失败");
        }else{
            redirectAttributes.addFlashAttribute("message","新增标签成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/edit/{id}")
    public String edit(@Valid Tag tag,BindingResult result, @PathVariable Long id,RedirectAttributes redirectAttributes){
        Tag tag1 = tagService.getByName(tag.getName());
        if(tag1 != null){
            result.rejectValue("name","nameError","该标签已经存在。");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag tag2 =tagService.saveTag(tag);
        if(tag2 == null){
            redirectAttributes.addFlashAttribute("message","更新标签失败");
        }else{
            redirectAttributes.addFlashAttribute("message","更新标签成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
