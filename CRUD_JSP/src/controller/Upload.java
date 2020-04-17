package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.io.ObjectInputStream.GetField;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import dao.ManipulaXMLProduto;

/**
 * Servlet implementation class Upload
 */
@WebServlet(name = "Upload", value = "/upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(Upload.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Upload() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		File file = new File("Segmentdetail.xlsx");

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet("SegmentLogs Info");
		spreadsheet.setDefaultColumnWidth(20);

		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		downloadFile(file, response);
		out.close();
		workbook.close();

		/*
		 * PrintWriter out = response.getWriter();
		 * 
		 * out.println(
		 * "<input type=\"button\" onclick=\"javascript: location.href='ServletUploadConsultaColetiva';disabled = true\" value=\"Baixar Arquivo\" />"
		 * );
		 */

	}

	private void downloadFile(File file, HttpServletResponse response) {

		try {
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("content-disposition", "attachment; filename=Segmentdetail.xlsx");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "no-store");
			response.addHeader("Cache-Control", "max-age=0");
			FileInputStream fin = null;
			try {
				fin = new FileInputStream(file);
			} catch (final FileNotFoundException e) {
				e.printStackTrace();
			}
			final int size = 1024;
			try {
				response.setContentLength(fin.available());
				final byte[] buffer = new byte[size];
				ServletOutputStream outputStream = null;

				outputStream = response.getOutputStream();
				int length = 0;
				while ((length = fin.read(buffer)) != -1) {
					outputStream.write(buffer, 0, length);
				}
				fin.close();
				outputStream.flush();
				outputStream.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

}
