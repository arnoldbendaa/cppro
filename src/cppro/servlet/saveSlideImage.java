package cppro.servlet;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import cppro.beans.UserAccount;
import cppro.utils.MyUtils;

import com.aspose.slides.AsposeLicenseException;
import com.aspose.slides.BackgroundType;
import com.aspose.slides.FillType;
import com.aspose.slides.IAutoShape;
import com.aspose.slides.IBaseSlide;
import com.aspose.slides.ILayoutSlide;
import com.aspose.slides.IPPImage;
import com.aspose.slides.IPictureFrame;
import com.aspose.slides.ISlide;
import com.aspose.slides.PictureFillMode;
import com.aspose.slides.Presentation;
import com.aspose.slides.ShapeType;
import com.softproideas.app.reviewbudget.export.controller.ExportController;


/**
 * Servlet implementation class saveSlideImage
 */

@WebServlet(urlPatterns = { "/saveSlideImage" })

public class saveSlideImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "uploadFiles";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public saveSlideImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		PreparedStatement pstmt=null; 
	    ResultSet rs=null; 
	    StringBuffer sb = new StringBuffer();
        String fileName = request.getParameter("fileName");
        sb.append(request.getParameter("img"));
        Connection conn = MyUtils.getStoredConnection(request);
        String timestamp = System.currentTimeMillis()+"";
        // gets absolute path of the web application
//   		String appPath = request.getServletContext().getRealPath("");
		String savePath = ExportController.uploadFolder;
		IPPImage imgx = null;
		
		String saveFilePathName = savePath+timestamp+".png";
		String saveFileName = timestamp+".png";
        try {
			byte[] imageByte = Base64.getDecoder().decode(sb.toString().split(",")[1]);
			File fileSaveDir = new File(savePath);
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdir();
			}
            new FileOutputStream(saveFilePathName).write(imageByte);
	  	} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        String base_url = request.getContextPath();
        String imgUrl = "/"+SAVE_DIR+"/"+saveFileName;
      try {
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO EXCEL_SLIDE(FILENAME,IMGURL,CREATETIME) VALUES ('"+fileName+"','"+imgUrl+"','"+timestamp+"')";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
	        pstmt.close(); 

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
      
		//get file name without extension
		File file = new File(fileName);
		String fileNameNoExtension = file.getName();
		String pptFileName = fileNameNoExtension+".pptx";
		String pptFileFullPathFull = savePath + pptFileName;
		File f = new File(pptFileFullPathFull);
		Presentation pres;
		//if ppt file is exist in upload folder then it will opens and otherwise it will create a ppt file 
		if(!f.exists()){
			pres = new Presentation();
		}else
			pres = new Presentation(pptFileFullPathFull);
		try{
		    imgx = pres.getImages().addImage(new FileInputStream(new File(saveFilePathName)));
		}
		catch(IOException e){}
		 int count = pres.getSlides().size();
		 count = count-1;
		IBaseSlide test = pres.getLayoutSlides().get_Item(count);
		pres.getSlides().addEmptySlide((ILayoutSlide) test);
		int id = (int) test.getSlideId();
		IBaseSlide slide = pres.getSlideById(id);
		slide.getShapes().addPictureFrame(ShapeType.Rectangle, 0, 0, (float) (imgx.getWidth()/2.67), imgx.getHeight()/2, imgx);
		
		//IPictureFrame pf = pres.getLayoutSlides().get_Item(0).getShapes().addPictureFrame(ShapeType.Rectangle, 0, 0, (float) (imgx.getWidth()/2.67), imgx.getHeight()/2, imgx);
//		test.getBackground().setType(BackgroundType.OwnBackground);
//		test.getBackground().getFillFormat().setFillType(FillType.Picture);
//		test.getBackground().getFillFormat().getPictureFillFormat()
//				.setPictureFillMode(PictureFillMode.Stretch);
//		test.getBackground().getFillFormat().getPictureFillFormat().getPicture().setImage(imgx);
		
		pres.save(pptFileFullPathFull, com.aspose.slides.SaveFormat.Pptx);
		System.out.println(pptFileFullPathFull);
		String pptFileUrl = "/"+SAVE_DIR+"/"+pptFileName;
		JSONObject dataObj = new JSONObject();
		try {
			dataObj.put("pptUrl",pptFileUrl);
			dataObj.put("errCode",0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        response.getWriter().append(dataObj.toString()).flush();
	}

}
