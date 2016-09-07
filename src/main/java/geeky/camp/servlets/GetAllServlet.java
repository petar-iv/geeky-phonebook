package geeky.camp.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import geeky.camp.structs.PhonebookRecord;
import geeky.camp.dao.PhonebookDAO;

@WebServlet("/GetAll")
public class GetAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<PhonebookRecord> list = PhonebookDAO.getAll();
		Gson gson = new Gson();
		String json = gson.toJson(list);
		response.setContentType("application/json");
		response.getWriter().print(json);
	}

}
