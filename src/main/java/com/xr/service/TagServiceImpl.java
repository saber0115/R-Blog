package com.xr.service;

import com.xr.dao.TagRepositoty;
import com.xr.handler.NotFoundException;
import com.xr.pojo.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepositoty tagRepositoty;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepositoty.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepositoty.findOne(id);
    }

    @Transactional
    @Override
    public Tag getByName(String name) {
        return tagRepositoty.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepositoty.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepositoty.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=new PageRequest(0,size,sort);
        return tagRepositoty.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) { //前端页面传递字符串参数1.2.3...，需转成数组类型作为查询条件
        List<Long> tagIds = convertToList(ids);
        return tagRepositoty.findAll(tagIds);
    }

    private List<Long> convertToList(String ids) { //将字符串转换为一个数组list
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }


    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tag1 = tagRepositoty.findOne(id);
        if(tag1 == null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag,tag1);
        return tagRepositoty.save(tag1);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepositoty.delete(id);
    }
}
