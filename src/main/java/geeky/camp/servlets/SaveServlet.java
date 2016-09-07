package geeky.camp.servlets;

import geeky.camp.dao.PhonebookDAO;
import geeky.camp.structs.PhonebookRecord;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

@WebServlet("/Save")
public class SaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String json = bodyAsString(request);
		Gson gson = new Gson();
		PhonebookRecord record = gson.fromJson(json, PhonebookRecord.class);
		if (record.getId() == -1) {
			PhonebookDAO.create(record);
		} else {
			PhonebookDAO.update(record);
		}
	}
	
	private String bodyAsString(HttpServletRequest request) throws IOException {
		return IOUtils.toString(request.getInputStream()); 
	}

}
