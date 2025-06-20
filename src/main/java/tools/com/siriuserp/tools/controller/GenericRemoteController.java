package com.siriuserp.tools.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Betsu Brahmana Restu
 * @author Rama Almer Felix
 * Sirius Indonesia,PT
 * www.siriuserp.com
 */
@Controller
public class GenericRemoteController extends ControllerBase {
    @Autowired
    private GenericDao genericDao;

    @RequestMapping("genericremoteload.json")
    public ModelAndView generic(@RequestParam("name")String name, @RequestParam(value="param", required=false)String param, @RequestParam("value")Object value) throws Exception {
        JSONResponse response = new JSONResponse();

        try
        {
            response.store("generic", genericDao.getUniqeField(Class.forName(name),
                    SiriusValidator.validateParam(param) ? param : "id", SiriusValidator.validateParam(param) ? value : Long.valueOf(value.toString())));
        }
        catch (Exception e)
        {
            response.statusError();
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    @RequestMapping("genericremotelist.json")
    public ModelAndView list(
            @RequestParam("name")  String name,
            @RequestParam(value="key",   required=false) String[] keys,
            @RequestParam(value="type",  required=false) String[] types,
            @RequestParam(value="value", required=false) String[] values
    ) {
        JSONResponse res = new JSONResponse();
        try {
            int n = (keys != null ? keys.length : 0);
            String[] fields = new String[n];
            Object[] params = new Object[n];

            for (int i = 0; i < n; i++) {
                String raw = values[i];
                switch (types[i].toLowerCase()) {
                    case "long":
                        params[i] = Long.valueOf(raw);
                        break;
                    case "boolean":
                        params[i] = Boolean.valueOf(raw);
                        break;
                    default:
                        params[i] = raw;     // String
                }
                fields[i] = keys[i];       // default operator "="
            }

            List<?> list = genericDao.getUniqeFields(Class.forName(name), fields, params);
            res.store("list", list);
        } catch (Exception e) {
            res.statusError();
            res.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }
}
