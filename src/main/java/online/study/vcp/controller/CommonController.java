package online.study.vcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import online.study.vcp.domain.Video;
import online.study.vcp.service.CommonService;

@RestController
public class CommonController {
    
    @Autowired
    private CommonService commonService;
    
    @RequestMapping(value = "/videos", method = RequestMethod.GET)
    public @ResponseBody Page<Video> listAllVideos(@PageableDefault(size = 10) Pageable pageable)
    {
        Page<Video> videos = commonService.listAllVideos(pageable);
        return videos;
    }

}
