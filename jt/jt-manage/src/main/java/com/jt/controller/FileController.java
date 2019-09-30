package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.EasyUIImage;

/*
   *  实现文件的上传
 */
@Controller
public class FileController {
//上传完成后重定向到上传页面
	/*
	 * 1.准备文件的目录
	 * 2.文件名
	 * 3.实现文件上传
	 */
	
	@RequestMapping("/file")
	public String fileImage(MultipartFile fileImage) throws IllegalStateException, IOException {
		//定义文件夹
		File fileDir = new File("D:/1-JT/image");
		if(!fileDir.exists()) {
			//如果文件不存在创建文件夹
			fileDir.mkdirs();
		}
		//动态获取图片名称
		String fileName = fileImage.getOriginalFilename();
		File file = new File("D:/1-JT/image/"+fileName);
		//直接将文件上传
		fileImage.transferTo(file);
		
		return "redirect:/file.jsp";
		
	}
	@Autowired
	private FileService fileService;
      /*
                   * 实现文件上传，参数名称uploadFile
      */
	@RequestMapping("/pic/upload")
	@ResponseBody
	public EasyUIImage uploadFile(MultipartFile uploadFile) {
		
		return fileService.updateFile(uploadFile);
	}
	 
}
