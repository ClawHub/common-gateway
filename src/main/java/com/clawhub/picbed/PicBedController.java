package com.clawhub.picbed;//package com.clawhub.boss;

import com.clawhub.result.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

/**
 * <Description> 图床网关<br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018/9/4 <br>
 */
@RestController
@RequestMapping("picBed")
public class PicBedController {

    @PostMapping("/post")
    public String post(@RequestBody String param) {
        return ResultUtil.getSucc(param);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "/upload/**")
    public String upload(MultipartHttpServletRequest request) throws IOException {
        Map<String, MultipartFile> map = request.getFileMap();
        MultipartFile multipartFile = map.get("file");
        String originalFilename = multipartFile.getOriginalFilename();
        System.out.println(originalFilename);
        //将b作为输入流；

        ByteArrayInputStream in = new ByteArrayInputStream(Base64.getEncoder().encode(multipartFile.getBytes()));
        System.out.println("==========in================" + in);
        //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
        BufferedImage image = ImageIO.read(in);
        System.out.println("==========image================" + image);
        ImageIO.write(image, "png", new File("C:\\Users\\admin\\Desktop\\" + originalFilename));
        System.out.println("==========write================");
        return ResultUtil.getSucc(map);
    }

    public void byte2image(byte[] data, String path) {
        if (data.length < 3 || path.equals("")) {
            return;
        }
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }


    /**
     * Welcome string.
     *
     * @return the string
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        return ResultUtil.getSucc();
    }
}
