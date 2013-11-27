package controller.barang;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Render;
import model.Barang;

@WebServlet("/barang/search")
public class search extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public search() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	private Vector<HashMap<String,String>> data;
	private Barang barang;
	
	String q;
	String kat;
	String h1, h2;
	int hal;
	String sort;
	int total;
	String paging;
	private static final String[] sortby = new String[] {"nama asc", "nama desc", "harga asc", "harga desc"};
	String query;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("q")!=null)
			query = "( (`nama` LIKE '%" + request.getParameter("q") + "%')";
		else
			query = "( (`nama` LIKE '%%')";
		if (request.getParameter("kat")!=null)
			query = query + "AND ( `id_kategori` = " + request.getParameter("kat") + " )";
		if (request.getParameter("h1")!=null)
			query = query + "AND ( `harga` >= " + request.getParameter("h1") + " )";
		if (request.getParameter("h2")!=null)
			query = query + "AND ( `harga` <= " + request.getParameter("h2") + " )";
		query = query + ") ";
		if ((request.getParameter("sort")!=null)&&((request.getParameter("sort")=="harga ASC")||(request.getParameter("sort")=="harga DESC")||(request.getParameter("sort")=="nama ASC")||(request.getParameter("sort")=="nama DESC")))
			query = query + " ORDER BY " + request.getParameter("sort");
		
		if (request.getParameter("hal")!=null)
			hal = Integer.parseInt(request.getParameter("hal"));
		else
			hal = 1;
		
		System.out.println(query);
		barang = new Barang();
		barang.findByCondition(query);
		barang.formatAllCurrency();
		data = barang.getDataVector();
		
		total = data.size();
		
		paging = barang.paginasi(total,hal,10, query);
		request.setAttribute("title", "Search");
		request.setAttribute("model", data);
		request.setAttribute("paging", paging);
		Render.Show(request, response, "../browse.jsp");

	}

}
