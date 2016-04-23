package com.maliavin.vcp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.service.CommonService;

/**
 * Common controller for all users operations.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@RestController
public class CommonController {

    private static final int ELEMENT_PER_PAGE = 9;

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/videos/{pageNum}", method = RequestMethod.GET)
    public @ResponseBody Page<Video> listAllVideos(@PageableDefault(size = ELEMENT_PER_PAGE) Pageable pageable,
            @PathVariable int pageNum) {
        Page<Video> videos = commonService.listAllVideos(new PageRequest(pageNum, pageable.getPageSize()));
        return videos;
    }

    @RequestMapping(value = "/pagesCount", method = RequestMethod.GET)
    public @ResponseBody Map<String, Long> getPagesCount() {
        Map<String, Long> result = new HashMap<String, Long>(1);
        Long pagesCount = commonService.getPagesCount(ELEMENT_PER_PAGE);
        result.put("pagesCount", pagesCount);
        return result;
    }

    @RequestMapping(value = "/video/{videoId}", method = RequestMethod.GET)
    public @ResponseBody Video getVideo(@PathVariable String videoId) {
        Video video = commonService.getVideo(videoId);
        return video;
    }

}
