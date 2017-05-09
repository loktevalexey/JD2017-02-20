package by.it.vedom.project.java.dao;

import by.it.vedom.jd03_03.UniversalDAO;
import by.it.vedom.project.java.beans.Order;
import by.it.vedom.project.java.beans.Product;
import by.it.vedom.project.java.beans.Role;
import by.it.vedom.project.java.beans.User;

public class DAO {
    private static DAO instance;

    public by.it.vedom.jd03_03.UniversalDAO<Role> role;
    public by.it.vedom.jd03_03.UniversalDAO<User> user;
    public by.it.vedom.jd03_03.UniversalDAO<Product> product;
    public by.it.vedom.jd03_03.UniversalDAO<Order> order;

    private DAO() {
    }

    public static DAO getDAO(){
        if (instance==null){
            synchronized (DAO.class){
                if(instance==null){
                    instance=new DAO();
                    instance.user=new UniversalDAO<>(new User(),"users");
                    instance.role=new UniversalDAO<>(new Role(),"roles");
                    instance.product=new UniversalDAO<>(new Product(),"products");
                    instance.order=new UniversalDAO<>(new Order(),"orders");

                }
            }
        }
        return instance;
    }
}
