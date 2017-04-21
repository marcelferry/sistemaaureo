package com.concafras.gestao.web.controller;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.concafras.gestao.enums.DiaSemana;
import com.concafras.gestao.enums.TipoFrequencia;
import com.concafras.gestao.form.AtividadeForm;
import com.concafras.gestao.model.Atividade;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.view.AtividadeExcel;
import com.concafras.gestao.model.view.AtividadeWrapper;
import com.concafras.gestao.service.AtividadeService;
import com.concafras.gestao.service.BaseInstitutoService;
import com.concafras.gestao.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/gestao/atividade")
public class AtividadeController {
  
  private static final Logger logger = LoggerFactory.getLogger(AtividadeController.class);
	
	  /**
     * The Constant PATTERN_HH_MM.
     */
    private static final String PATTERN_HH_MM = "hh:mm";
    

    @Autowired
    private AtividadeService atividadeService;
    
    @Autowired
    private BaseInstitutoService baseInstitutoService;

    @RequestMapping("/listar")
    public ModelAndView listAtividade(@ModelAttribute("atividadeForm") AtividadeForm form) {

    	BaseInstituto instituto = baseInstitutoService.findById(form.getIdInstituto());

    	List<Atividade> atividades =  atividadeService.listAtividadeByInstituto(form.getIdInstituto());
    	
    	form = new AtividadeForm(form.getIdInstituto());
    	if(atividades != null){ 
    		for (Atividade atividade : atividades) {
    			List<Atividade> subAtividades = atividade.getSubAtividades();
    			if(subAtividades != null){
    				subAtividades.removeAll(Collections.singleton(null));
    				for (Atividade atividade2 : subAtividades) {
    					List<Atividade> subAtividades2 = atividade2.getSubAtividades();
    	    			if(subAtividades2 != null){
    	    				subAtividades2.removeAll(Collections.singleton(null));
    	    			}
					}
    				
    			}
			}
    		form.setAtividades(atividades);
    	} else {
    		form.setAtividades(new ArrayList<Atividade>());
    	}
        ModelAndView model = new ModelAndView("atividade.listar", "atividadeForm", form);
        
        model.addObject("instituto", instituto);
    	
    	model.addObject("basicos", Boolean.TRUE);
        
        return model;
    }
    
    @RequestMapping("/")
    public String listBaseInstituto(Map<String, Object> map) {
    	
    	map.put("institutoList", baseInstitutoService.findByRodizio(true));
    	map.put("basicos", true);
    	return "atividade.selecionar";
    }
    
    @RequestMapping("/editar")
    public ModelAndView editarAtividades(@ModelAttribute("atividadeForm") AtividadeForm form) {
    	
    	List<Atividade> atividades =  atividadeService.listAtividadeByInstituto(form.getIdInstituto());
    	
    	if(atividades != null){ 
    		for (Atividade atividade : atividades) {
    			List<Atividade> subAtividades = atividade.getSubAtividades();
    			if(subAtividades != null){
    				subAtividades.removeAll(Collections.singleton(null));
    				for (Atividade atividade2 : subAtividades) {
    					List<Atividade> subAtividades2 = atividade2.getSubAtividades();
    	    			if(subAtividades2 != null){
    	    				subAtividades2.removeAll(Collections.singleton(null));
    	    			}
					}
    			}
			}
    		form.setAtividades(atividades);
    	} else {
    		form.setAtividades(new ArrayList<Atividade>());
    	}
    	
    	
    	if(form.getAtividades().size() == 0)
    		form.getAtividades().add(new Atividade());
    	
    	BaseInstituto instituto = baseInstitutoService.findById(form.getIdInstituto());
    	
    	ModelAndView model = new ModelAndView("atividade.editarLista", "atividadeForm", form);
    	
    	List<TipoFrequencia> frequenciaList = new ArrayList<TipoFrequencia>( Arrays.asList(TipoFrequencia.values() ));
    	model.addObject("frequenciaList", frequenciaList);
    	
    	List<DiaSemana> semanaList = new ArrayList<DiaSemana>( Arrays.asList(DiaSemana.values() ));
    	model.addObject("semanaList", semanaList);
    	
    	model.addObject("instituto", instituto);
    	
    	model.addObject("basicos", Boolean.TRUE);
    	
    	return model ;
    	
    }
    
    @RequestMapping("/organizar")
    public ModelAndView organizarAtividades(@ModelAttribute("atividadeForm") AtividadeForm form) {
    	
    	List<Atividade> atividades =  atividadeService.listAtividadeByInstituto(form.getIdInstituto());
    	
    	if(atividades != null){ 
    		for (Atividade atividade : atividades) {
    			List<Atividade> subAtividades = atividade.getSubAtividades();
    			if(subAtividades != null){
    				subAtividades.removeAll(Collections.singleton(null));
    				for (Atividade atividade2 : subAtividades) {
    					List<Atividade> subAtividades2 = atividade2.getSubAtividades();
    					if(subAtividades2 != null){
    						subAtividades2.removeAll(Collections.singleton(null));
    					}
    				}
    			}
    		}
    		form.setAtividades(atividades);
    	} else {
    		form.setAtividades(new ArrayList<Atividade>());
    	}
    	
    	
    	if(form.getAtividades().size() == 0)
    		form.getAtividades().add(new Atividade());
    	
    	BaseInstituto instituto = baseInstitutoService.findById(form.getIdInstituto());
    	
    	ModelAndView model = new ModelAndView("atividade.organizar", "atividadeForm", form);
    	
    	List<TipoFrequencia> frequenciaList = new ArrayList<TipoFrequencia>( Arrays.asList(TipoFrequencia.values() ));
    	model.addObject("frequenciaList", frequenciaList);
    	
    	List<DiaSemana> semanaList = new ArrayList<DiaSemana>( Arrays.asList(DiaSemana.values() ));
    	model.addObject("semanaList", semanaList);
    	
    	model.addObject("instituto", instituto);
    	
    	model.addObject("basicos", Boolean.TRUE);
    	
    	return model ;
    	
    }
    
    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("atividadeForm") AtividadeForm atividadeForm) {
    	
    	
    	BaseInstituto instituto = baseInstitutoService.findById(atividadeForm.getIdInstituto());

    	List<Atividade> atividades = atividadeForm.getAtividades();
    	
    	for(Atividade atividade : atividades){
    		if( !atividade.getDescricao().trim().isEmpty() ){
	    		atividade.setInstituto(instituto);
	    		if( atividade.getId() == 0){
	    			atividadeService.addAtividade(atividade);
	    		} else {
	    			if(atividade.getSubAtividades() != null){
		    			for(Atividade a : atividade.getSubAtividades()){
		    				a.setPai(atividade);
		    			}
	    			}
	    			atividadeService.updateAtividade(atividade);
	    		}
    		}
    	}
    	
    	atividades =  atividadeService.listAtividadeByInstituto(atividadeForm.getIdInstituto());
    	
        ModelAndView model = new ModelAndView("atividade.listar", "atividadeForm", atividadeForm);
        
        model.addObject("instituto", instituto);
    	
    	model.addObject("basicos", Boolean.TRUE);
        
        return model;
    }
    

    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    public ModelAndView sort(@ModelAttribute("atividadeForm") AtividadeForm atividadeForm) {
    	
    	
    	List<Atividade> atividades = atividadeForm.getAtividades();
    	
    	String organizar = atividadeForm.getOrganizar();
    	
    	try{
	    	ObjectMapper mapper = new ObjectMapper();
	    	
	    	AtividadeWrapper[] novasAtividades = mapper.readValue(organizar, AtividadeWrapper[].class);
	    	
	    	int sortOrder = 1;
	    	
	    	for (AtividadeWrapper atividadeWrapper : novasAtividades) {
	    		updateGrupo(atividadeWrapper, sortOrder, null);
	    		sortOrder++;
			}
	    	
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    	
    	atividades =  atividadeService.listAtividadeByInstituto(atividadeForm.getIdInstituto());
    	
    	BaseInstituto instituto = baseInstitutoService.findById(atividadeForm.getIdInstituto());
    	
        ModelAndView model = new ModelAndView("atividade.listar", "atividadeForm", atividadeForm);
        
        model.addObject("instituto", instituto);
    	
    	model.addObject("basicos", Boolean.TRUE);
        
        return model;
    }
    
    @RequestMapping(value = "/processImport", method = RequestMethod.POST)
    public ModelAndView processImport(@ModelAttribute("atividadeForm") AtividadeForm atividadeForm) {
    	
    	List<Atividade> atividades = atividadeForm.getAtividades();
    	
    	BaseInstituto instituto = baseInstitutoService.findById(atividadeForm.getIdInstituto());
    	
    	insertGrupo(atividades, instituto, null);
    	
    	atividades =  atividadeService.listAtividadeByInstituto(atividadeForm.getIdInstituto());
    	
    	ModelAndView model = new ModelAndView("atividade.listar", "atividadeForm", atividadeForm);
    	
    	model.addObject("instituto", instituto);
    	
    	model.addObject("basicos", Boolean.TRUE);
    	
    	return model;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addAtividade(Map<String, Object> map, @ModelAttribute("atividadeForm") AtividadeForm form) {
    	
    	Atividade atividade = new Atividade();
    	
    	BaseInstituto institutoSelecionado = baseInstitutoService.findById(form.getIdInstituto());
    	
    	atividade.setInstituto(institutoSelecionado);
    	
    	map.put("atividade", atividade);
    	
    	List<BaseInstituto> lista = baseInstitutoService.findByRodizio(false);
    	
    	
        Map<String, String> institutoList = new HashMap<String, String>();
        
        for (BaseInstituto instituto : lista) {
			institutoList.put(String.valueOf(instituto.getId()), instituto.getDescricao());
		}
        
        map.put("institutoList",institutoList);
        
        List<TipoFrequencia> frequenciaList = new ArrayList<TipoFrequencia>( Arrays.asList(TipoFrequencia.values() ));
        map.put("frequenciaList", frequenciaList);
    	
    	List<DiaSemana> semanaList = new ArrayList<DiaSemana>( Arrays.asList(DiaSemana.values() ));
    	map.put("semanaList", semanaList);
        
        
        map.put("basicos", true); 
    	return "atividade.novo";
    }
    
    @RequestMapping(value = "/edit/{atividadeId}", method=RequestMethod.POST)
    public String editAtividade(Map<String, Object> map, @PathVariable("atividadeId") Integer atividadeId) {
    	
    	Atividade atividade = atividadeService.getAtividade(atividadeId);
    	
    	map.put("atividade", atividade);
    	
    	List<BaseInstituto> lista = baseInstitutoService.findByRodizio(false);
    	
        Map<String, String> institutoList = new HashMap<String, String>();
        
        for (BaseInstituto instituto : lista) {
			institutoList.put(String.valueOf(instituto.getId()), instituto.getDescricao());
		}
        
        map.put("institutoList",institutoList);
        
        List<TipoFrequencia> frequenciaList = new ArrayList<TipoFrequencia>( Arrays.asList(TipoFrequencia.values() ));
        map.put("frequenciaList", frequenciaList);
    	
    	List<DiaSemana> semanaList = new ArrayList<DiaSemana>( Arrays.asList(DiaSemana.values() ));
    	map.put("semanaList", semanaList);
        
        
        map.put("basicos", true); 
    	return "atividade.editar";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addAtividade(@ModelAttribute("atividade") Atividade atividade, BindingResult result, RedirectAttributes atividadeForm) {

    	atividadeService.addAtividade(atividade);
    	
    	atividadeForm.addFlashAttribute("atividadeForm", new AtividadeForm( atividade.getInstituto().getId() ) );

        return "redirect:/gestao/atividade/listar";
    }
    
   
    @RequestMapping(value="/edit/save/{atividadeId}", method=RequestMethod.POST)
    public String editAtividade(@ModelAttribute("atividade") Atividade atividade, @PathVariable("atividadeId") Integer atividadeId, RedirectAttributes atividadeForm) {
    	
    	atividadeService.updateAtividade(atividade);
    	
    	atividadeForm.addFlashAttribute("atividadeForm", new AtividadeForm( atividade.getInstituto().getId() ) );

        return "redirect:/gestao/atividade/listar";
    }

    @RequestMapping("/delete/{atividadeId}")
    public String deleteAtividade(@PathVariable("atividadeId") Integer atividadeId) {

    	atividadeService.removeAtividade(atividadeId);

        return "redirect:/gestao/atividade/";
    }
    
    public void updateGrupo(AtividadeWrapper atividadeWrapper, Integer sortOrder, Integer idPai){
    	
    	Atividade atividade = atividadeService.getAtividade(atividadeWrapper.getId());
    	
    	atividade.setViewOrder(sortOrder);
    	
    	if(idPai != null ){
    		Atividade pai = atividadeService.getAtividade(idPai);
    		atividade.setPai(pai);
    	} else {
    		atividade.setPai(null);
    	}
    	
    	atividadeService.updateAtividade(atividade);
    	
    	if( atividadeWrapper.getChildren() != null && atividadeWrapper.getChildren().length > 0 ){
    		int order = 1;
    		for(AtividadeWrapper atividadeWrapper2 : atividadeWrapper.getChildren()){
    			updateGrupo(atividadeWrapper2, order, atividade.getId());
    			order++;
    		}
    	}
    }
    
    public void insertGrupo(List<Atividade> atividades, BaseInstituto instituto, Atividade pai){
    	for(Atividade atividade : atividades){
    		if( !atividade.getDescricao().trim().isEmpty() ){
	    		atividade.setInstituto(instituto);
	    		atividade.setPai(pai);
	    		if( atividade.getId() == null || atividade.getId().equals(0)){
	    			atividadeService.addAtividade(atividade);
	    		} else {
	    			atividadeService.updateAtividade(atividade);
	    		}
	    		if(atividade.getSubAtividades() != null){
		    		insertGrupo(atividade.getSubAtividades() , instituto, atividade);
    			}
    		}
    	}
    }
    
    @RequestMapping(value = "upload", method = RequestMethod.GET)
	public String upload(Map<String, Object> map, @ModelAttribute("atividadeForm") AtividadeForm form) {
		map.put("basicos", true);
		map.put("atividadeForm", form);
		return "atividade.upload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(HttpServletRequest request, Map<String, Object> map, @ModelAttribute("atividadeForm") AtividadeForm form) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("file");
		
		int count = 0;
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if(extension.trim().equalsIgnoreCase("xlsx")){
            try {
				count = processExcelFile(multipartFile.getInputStream(), map);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        map.put("atividadeForm", form);
        
    	map.put("basicos", true);
		return "atividade.uploadvalidate";
	}
	
	
  private int processExcelFile(InputStream file, Map<String, Object> map) {

    int count = 0;

    try {

      // Create a workbook using the File System
      XSSFWorkbook wb = new XSSFWorkbook(file);

      // Get the first sheet from workbook
      XSSFSheet sheet = wb.getSheetAt(0);

      Row linha;
      XSSFCell cell;

      FormulaEvaluator ev = wb.getCreationHelper().createFormulaEvaluator();

      Integer numLinha = 8;
      String nome = null;
      List<AtividadeExcel> listAtividadeExcels = new ArrayList<AtividadeExcel>();

      int tentativas = 0;

      do {
        linha = sheet.getRow(numLinha);
        if (linha == null) {
          numLinha++;
          tentativas++;
        } else {
          cell = (XSSFCell) linha.getCell(2);
          nome = Util.getCellValue(ev, cell, sheet);
          if (!Util.isNullOrEmpty(nome)) {
            AtividadeExcel ae = processaLinha(ev, numLinha, sheet);
            listAtividadeExcels.add(ae);
            numLinha++;
            tentativas = 0;
            ae = null;
          } else {
            numLinha++;
            tentativas++;
          }
        }
        linha = null;

      } while (!Util.isNullOrEmpty(nome) || tentativas < 2);

      reorganizar(listAtividadeExcels);

      map.put("listAtividades", listAtividadeExcels);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return count;

  }
    

  private void reorganizar(List<AtividadeExcel> listAtividadeExcels) {
    List<AtividadeExcel> listRemover = new ArrayList<AtividadeExcel>();

    for (int i = 10; i > 1; i--) {
      for (AtividadeExcel atividadeExcel : listAtividadeExcels) {
        String id = atividadeExcel.getId();
        String[] idArray = Util.createIdArray(id);

        if (idArray.length == i) {
          System.out.println("ID " + idArray.length);
          for (AtividadeExcel atividade : listAtividadeExcels) {
            String subId = atividade.getId();
            String[] subIdArray = Util.createIdArray(subId);
            System.out.println("SUB " + subIdArray.length);
            if (subIdArray.length == (i - 1)) {
              if (Util.idArrayCompare(subIdArray, idArray)) {
                if (atividade.getLista() == null)
                  atividade.setLista(new ArrayList<AtividadeExcel>());
                atividade.getLista().add(atividadeExcel);
                listRemover.add(atividadeExcel);
                System.out.println(i + " - Removendo " + atividadeExcel.getId()
                    + " " + atividadeExcel.getNome());
              }
            }
            subId = null;
            subIdArray = null;
          }

        }
        id = null;
        idArray = null;
      }
    }
    listAtividadeExcels.removeAll(listRemover);
  }

	private AtividadeExcel processaLinha(FormulaEvaluator ev, Integer numLinha, Sheet sheet) {
		
		int viewOrder;
		boolean grupo;
		boolean atividade;
		boolean acao;
		String nome;
		Row linha = sheet.getRow(numLinha);
		XSSFCell cell = (XSSFCell) linha.getCell(2);
		
		nome = Util.getCellValue(ev, cell, sheet);
		cell = (XSSFCell) linha.getCell(1);
		String sViewOrder = Util.getCellValue(ev, cell, sheet);
		
		cell = (XSSFCell) linha.getCell(3);
		String sGrupo = Util.getCellValue(ev, cell, sheet);
		grupo = !sGrupo.trim().isEmpty();
		
		cell = (XSSFCell) linha.getCell(4);
		String sAtividade = Util.getCellValue(ev, cell, sheet);
		atividade = !sAtividade.trim().isEmpty();
		
		cell = (XSSFCell) linha.getCell(5);
		String sAcao = Util.getCellValue(ev, cell, sheet);
		acao = !sAcao.trim().isEmpty();
		
		AtividadeExcel atividadeImportada = new AtividadeExcel(sViewOrder, nome, grupo, atividade, acao);
		
		System.out.print(sViewOrder);
		System.out.print(" ");
		System.out.println(nome);
	
		return atividadeImportada;
		
	}

	
    
    /**
     * Pre inicializa controller
     * 
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        bindDate(binder);
    }
    
    /**
     * Bind date.
     * 
     * @param binder
     *            the binder
     */
    protected void bindDate(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                try {
                    Date parsedDate = new SimpleDateFormat(PATTERN_HH_MM).parse(value);
                    setValue(parsedDate);
                } catch (ParseException e) {
                    setValue(null);
                }
            }
        });
    }
    
}
