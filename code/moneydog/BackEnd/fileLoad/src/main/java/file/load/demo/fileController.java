package file.load.demo;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/File")
public class fileController {

//    private String filePath = "/image/";

    @Value("${web.upload-path}")
    private String filePath;

    @RequestMapping(value = "/Upload",method = RequestMethod.POST)
    public JSONObject UpLoadImage(@RequestParam(value = "img")MultipartFile file) throws RuntimeException{
        JSONObject jsonObject = new JSONObject();
        int errcode = 0;
        String errmsg = "";
        if(file.isEmpty()){
            errcode = -1;
            errmsg = "It is empty file";
            jsonObject.put("errcode",errcode);
            jsonObject.put("errmsg",errmsg);
            return jsonObject;
        }
        String imageName = UUID.randomUUID().toString() + file.getOriginalFilename();
        System.out.println(filePath + imageName);
        File dir = new File(filePath + imageName);
        if(!dir.getParentFile().exists()){
            dir.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dir);
            errcode = 1;
            errmsg = "UpLoad Successfully";
        }catch (Exception e){
            errcode = 0;
            errmsg = "Exception";
            System.out.println(e);
        }
        jsonObject.put("errcode",errcode);
        jsonObject.put("errmsg",errmsg);
        jsonObject.put("imageUrl",imageName);
        return jsonObject;
    }

    @RequestMapping(value = "/Download",method = RequestMethod.GET)
    public void DownLoadImage(@RequestParam(value = "img") String imageUrl, HttpServletRequest request, HttpServletResponse response) throws  RuntimeException{

        JSONObject jsonObject = new JSONObject();
        File file = new  File(imageUrl);
        String imgName =file.getName();
        FileInputStream is = null;

        ResourceHandlerRegistration registration = new ResourceHandlerRegistration();
        registration.addResourceLocations();
        if(file.exists()){
            response.setContentType("image/jpeg");
            try {
                 is = new FileInputStream(file);
                int i = is.available();
                byte data[] = new byte[i];
                is.read(data);
                is.close();
                response.setContentType("image/jpeg");
                OutputStream toClient = response.getOutputStream();
                toClient.write(data);
                toClient.close();

            }catch (Exception e){

            }
        }


//        if(file.exists()){
//            response.setContentType("application/force-download");
//            response.addHeader("Content-Disposition","attackment;fileName="+imgName);
//            byte[] buffer = new byte[1024];
//            FileInputStream fis = null;
//            BufferedInputStream bis = null;
//            try {
//                fis = new FileInputStream(file);
//                bis = new BufferedInputStream(fis);
//                OutputStream os = response.getOutputStream();
//                int i = bis.read(buffer);
//                while (i != -1){
//                    os.write(buffer,0,i);
//                    i = bis.read(buffer);
//                }
//
//            }catch (Exception e){
//
//            }finally {
//                if(bis != null){
//                    try {
//                        bis.close();
//                    }catch (IOException e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }

    }
}
