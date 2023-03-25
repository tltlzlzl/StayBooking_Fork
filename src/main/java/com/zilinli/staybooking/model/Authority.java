//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 03/23
// * Definition: Implementation of Authority class.
//**********************************************************************************************************************

package com.zilinli.staybooking.model;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// System includes
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Entity
@Table(name="authority")
public class Authority implements Serializable {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************

    public Authority() {}
    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }
//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    public String getUsername() {
        return username;
    }

    public Authority setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getAuthority() {
        return authority;
    }

    public Authority setAuthority(String authority) {
        this.authority = authority;
        return this;
    }

//**********************************************************************************************************************
// * Protected methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************

    @Id
    private String username;
    private String authority;
    private static final long serialVersionUID = 1L;
}
