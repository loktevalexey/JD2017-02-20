package by.it.radivonik.project.java.controller;

import by.it.radivonik.project.java.beans.Avto;
import by.it.radivonik.project.java.beans.Klient;
import by.it.radivonik.project.java.beans.Role;
import by.it.radivonik.project.java.beans.Tovar;
import by.it.radivonik.project.java.dao.DAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Radivonik on 05.05.2017.
 */
public class FrontController extends HttpServlet {
    private RequestDispatcher getDispatcher(AbstractAction action) {
        return getServletContext().getRequestDispatcher(action.getJsp());
    }

    @Override
    public void init() throws ServletException {
        DAO dao = DAO.getInstance();
        try {
            List<Role> roles = dao.getRole().getAll("");
            List<Tovar> tovars = dao.getTovar().getAll("");
            List<Klient> klients = dao.getKlient().getAll("");
            List<Avto> avtos = dao.getAvto().getAll("");
            getServletContext().setAttribute("roles_spr", roles);
            getServletContext().setAttribute("tovars_spr", tovars);
            getServletContext().setAttribute("klients_spr", klients);
            getServletContext().setAttribute("avtos_spr", avtos);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
        AbstractAction action = Actions.defineFrom(req);
        action.execute(req);
        getDispatcher(action).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
        AbstractAction action = Actions.defineFrom(req);
        AbstractAction nextAction = action.execute(req);
        if (nextAction != null)
            resp.sendRedirect("do?command=" + nextAction);
        else
            getDispatcher(action).forward(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-cache, no-store");
    }
}
