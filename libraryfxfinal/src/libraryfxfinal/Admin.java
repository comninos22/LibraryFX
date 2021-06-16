/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryfxfinal;

/**
 *
 * @author MemeMaster64
 */
public class Admin extends User{
    public Admin( int id, String uname, String passwd, String cname, String email, String phone, String dateofregister, String address ) {
        super(id, uname, passwd, cname, email, phone, dateofregister, address,true);       
    }

    @Override
    public String toString() {
        return "Admin{" +super.toString()+ '}';
    }

    
}
