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
public abstract class User {
    int id;
     String uname;
     String passwd;
     String cname;
     String email;
     String phone;
     String dateofregister;
     String card;
     String address;
     boolean isadmin;

    public User(int id, String uname, String passwd, String cname, String email, String phone, String dateofregister , String address, boolean isadmin ) {
        this.id = id;
        this.uname = uname;
        this.passwd = passwd;
        this.cname = cname;
        this.email = email;
        this.phone = phone;
        this.dateofregister = dateofregister;
        this.address=address;
        this.isadmin=isadmin;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", uname=" + uname + ", passwd=" + passwd + ", cname=" + cname + ", email=" + email + ", phone=" + phone + ", dateofregister=" + dateofregister + ", card=" + card + ", address=" + address + ", isadmin=" + isadmin + '}';
    }

    public String getUname() {
        return uname;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getCname() {
        return cname;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDateofregister() {
        return dateofregister;
    }

    public boolean isAdmin() {
        return isadmin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDateofregister(String dateofregister) {
        this.dateofregister = dateofregister;
    }

    
     
     
}
