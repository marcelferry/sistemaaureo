package com.concafras.gestao.web.controller;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.concafras.gestao.model.security.Usuario;
import com.concafras.gestao.service.UsuarioService;
 
 
 
@Controller
@RequestMapping("/gestao/report/")
public class ReportController {
	
  private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
	
	@Autowired
	javax.sql.DataSource datasource;
 
    @Autowired
    UsuarioService userProfileService;
    
    @RequestMapping(value ="/entidades/{format}", method = RequestMethod.GET)
    public ModelAndView handleSimpleReportMulti(ModelMap modelMap, @PathVariable("format") String format) throws Exception {

        //Map model = new HashMap();
        modelMap.put("format", format);
        modelMap.put("REPORT_CONNECTION", datasource.getConnection());
        modelMap.put("START_DATE", new String("09-12-2011"));
        modelMap.put("END_DATE", new String("09-17-2011"));

        return new ModelAndView("report1", modelMap);       
    }
 
    @RequestMapping(method = RequestMethod.GET , value = "pdf")
    public ModelAndView generatePdfReport(ModelAndView modelAndView) throws SQLException{
 
        logger.debug("--------------generate PDF report----------");
 
        Map<String,Object> parameterMap = new HashMap<String,Object>();
 
        List<Usuario> usersList = userProfileService.findAll();
 
        JRDataSource JRdataSource = new JRBeanCollectionDataSource(usersList);
 
        parameterMap.put("datasource", JRdataSource);
        
        //pdfReport bean has ben declared in the jasper-views.xml file
        modelAndView = new ModelAndView("pdfReport", parameterMap);
 
        return modelAndView;
 
    }//generatePdfReport
 
 
 
    @RequestMapping(method = RequestMethod.GET , value = "xls")
    public ModelAndView generateXlsReport(ModelAndView modelAndView){
 
        logger.debug("--------------generate XLS report----------");
 
        Map<String,Object> parameterMap = new HashMap<String,Object>();
 
        List<Usuario> usersList = userProfileService.findAll();
 
        JRDataSource JRdataSource = new JRBeanCollectionDataSource(usersList);
 
        parameterMap.put("datasource", JRdataSource);
 
        //xlsReport bean has ben declared in the jasper-views.xml file
        modelAndView = new ModelAndView("xlsReport", parameterMap);
 
        return modelAndView;
 
    }//generatePdfReport
 
 
    @RequestMapping(method = RequestMethod.GET , value = "csv")
    public ModelAndView generateCsvReport(ModelAndView modelAndView){
 
        logger.debug("--------------generate CSV report----------");
 
        Map<String,Object> parameterMap = new HashMap<String,Object>();
 
        List<Usuario> usersList = userProfileService.findAll();
 
        JRDataSource JRdataSource = new JRBeanCollectionDataSource(usersList);
 
        parameterMap.put("datasource", JRdataSource);
 
        //xlsReport bean has ben declared in the jasper-views.xml file
        modelAndView = new ModelAndView("csvReport", parameterMap);
 
        return modelAndView;
 
    }//generatePdfReport
 
 
 
    @RequestMapping(method = RequestMethod.GET , value = "html")
    public ModelAndView generateHtmlReport(ModelAndView modelAndView){
 
        logger.debug("--------------generate HTML report----------");
 
        Map<String,Object> parameterMap = new HashMap<String,Object>();
 
        List<Usuario> usersList = userProfileService.findAll();
 
        JRDataSource JRdataSource = new JRBeanCollectionDataSource(usersList);
 
        parameterMap.put("datasource", JRdataSource);
 
        //xlsReport bean has ben declared in the jasper-views.xml file
        modelAndView = new ModelAndView("htmlReport", parameterMap);
 
        return modelAndView;
 
    }//generatePdfReport
 
 
}//ReportController