/**
 * Nov 15, 2008 11:34:07 AM
 * com.siriuserp.sdk.adapter
 * GridViewAdapter.java
 */
package com.siriuserp.sdk.adapter;

import com.siriuserp.sdk.dm.Model;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class GridViewAdapter extends AbstractUIAdapter
{   
    private static final long serialVersionUID = 7262770560413712738L;
    
    private Model model;
    
    public GridViewAdapter(){}

    public Model getModel()
    {
        return model;
    }

    public void setModel(Model model)
    {
        this.model = model;
    }
}
