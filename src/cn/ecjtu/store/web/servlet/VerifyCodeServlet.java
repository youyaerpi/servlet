package cn.ecjtu.store.web.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.vcode.utils.VerifyCode;

@SuppressWarnings("serial")
public class VerifyCodeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		VerifyCode vc= new VerifyCode();
        BufferedImage image = vc.getImage();
        request.getSession().setAttribute("session_vcode", vc.getText());
        VerifyCode.output(image, response.getOutputStream());
        
        
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
