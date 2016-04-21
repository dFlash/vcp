package online.study.vcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import online.study.vcp.domain.Video;
import online.study.vcp.service.CommonService;

/**
 * Common controller for all users operations.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@RestController
public class CommonController {

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/videos", method = RequestMethod.GET)
    public @ResponseBody Page<Video> listAllVideos(@PageableDefault(size = 9) Pageable pageable) {
        Page<Video> videos = commonService.listAllVideos(pageable);
        return videos;
    }

    @RequestMapping(value = "/video", method = RequestMethod.GET)
    public @ResponseBody Video getVideo(@RequestParam("id") String id) {
        Video video = commonService.getVideo(id);
        return video;
    }

}
