/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryfxfinal;

import java.sql.SQLException;

/**
 *
 * @author MemeMaster64
 */
public class Customer extends User {

    public Customer(int id, String uname, String passwd, String cname, String email, String phone, String dateofregister,  String address ) {
        super(id, uname, passwd, cname, email, phone, dateofregister,  address,false );
        
    }    
    @Override
    public String toString() {
        return "Customer{" + super.toString() + '}';
    }
    
}
