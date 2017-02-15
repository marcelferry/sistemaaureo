package com.concafras.gestao.web.controller;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
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
import com.concafras.gestao.enums.SituacaoMeta;
import com.concafras.gestao.enums.TipoFrequencia;
import com.concafras.gestao.form.PlanoModeloForm;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.MetaInstituto;
import com.concafras.gestao.model.view.ItemPlanoModeloWrapper;
import com.concafras.gestao.model.view.MetaInstitutoExcel;
import com.concafras.gestao.model.view.ResumoMetaEntidade;
import com.concafras.gestao.service.BaseInstitutoService;
import com.concafras.gestao.service.MetaService;
import com.concafras.gestao.service.MetasInstitutoService;
import com.concafras.gestao.service.PlanoMetasService;
import com.concafras.gestao.service.RodizioService;
import com.concafras.gestao.util.Util;

@Controller
@RequestMapping("/gestao/planoModelo")
public class PlanoModeloController {

  /**
   * The Constant PATTERN_YYYY_MM_DD.
   */
  private static final String PATTERN_HH_MM = "hh:mm";

  FormulaEvaluator evaluator;

  @Autowired
  private MetasInstitutoService metasInstitutoService;

  @Autowired
  private BaseInstitutoService baseInstitutoService;

  @Autowired
  private RodizioService rodizioService;

  @Autowired
  private PlanoMetasService planoMetasService;
  
  @Autowired
  private MetaService metaService;
  
  

  @RequestMapping("/listar/implantadas/{atividade}")
  public ModelAndView listEntidadesImplantadas( @PathVariable("atividade") Integer atividade, HttpServletRequest request) {
    
    ModelAndView model = null;
    
    List<ResumoMetaEntidade> retorno = metaService.findByMetaInstitutoAndStatus(atividade, SituacaoMeta.IMPLANTADA);
    
    MetaInstituto meta = metasInstitutoService.getMetasInstituto(atividade);
    
    model = new ModelAndView("planoModelo.dirigente.implantadas");      
    
    model.addObject("listaMetas", retorno);
    
    model.addObject("meta", meta);
    
    model.addObject("status",  SituacaoMeta.IMPLANTADA);
    
    return model;
  }
  
  @RequestMapping("/listar/planejadas/{atividade}")
  public ModelAndView listEntidadesPlanejadas( @PathVariable("atividade") Integer atividade, HttpServletRequest request) {
    
    ModelAndView model = null;
    
    List<ResumoMetaEntidade> retorno = metaService.findByMetaInstitutoAndStatus(atividade, SituacaoMeta.PLANEJADA);
    
    MetaInstituto meta = metasInstitutoService.getMetasInstituto(atividade);
    
    model = new ModelAndView("planoModelo.dirigente.planejadas");  
    
    model.addObject("listaMetas", retorno);
    
    model.addObject("status",  SituacaoMeta.PLANEJADA);
    
    model.addObject("meta", meta);
    
    return model;
  }
  
  @RequestMapping("/listar/naoplanejadas/{atividade}")
  public ModelAndView listEntidadesNaoPLanejadas( @PathVariable("atividade") Integer atividade, HttpServletRequest request) {
    
    ModelAndView model = null;
    
    List<ResumoMetaEntidade> retorno = metaService.findByMetaInstitutoAndStatus(atividade, SituacaoMeta.NAOPLANEJADA);
    
    MetaInstituto meta = metasInstitutoService.getMetasInstituto(atividade);
    
    model = new ModelAndView("planoModelo.dirigente.naoplanejadas");   
    
    model.addObject("listaMetas", retorno);
    
    model.addObject("status",  SituacaoMeta.NAOPLANEJADA);
    
    model.addObject("meta", meta);
    
    return model;
  }

  @RequestMapping("/listar")
  public ModelAndView listMetasInstituto(
        @ModelAttribute("planoModeloForm") PlanoModeloForm form, HttpServletRequest request) {
      
      BaseInstituto instituto = null;
      
      if(form.getInstituto() == null || form.getInstituto().getId() == null){
        if("ROLE_METAS_DIRIGENTE".equals( request.getSession().getAttribute("ROLE_CONTROLE") ) ){
          instituto = (BaseInstituto) request.getSession().getAttribute("INSTITUTO_CONTROLE");
        }
      } else {
        instituto = baseInstitutoService.findById(form
            .getInstituto().getId());
      }
    

    List<MetaInstituto> planoModelos = metasInstitutoService
        .listMetaInstitutoByInstituto(instituto.getId());

    form = new PlanoModeloForm(instituto.getId());
    form.setInstituto(instituto);
    
    
    if (planoModelos != null) {
      cleanPlanoModeloList(planoModelos);
      /*planoModelos.removeAll(Collections.singleton(null));
      for (MetaInstituto planoModelo : planoModelos) {
        List<MetaInstituto> subPlanoModelos = planoModelo.getItens();
        if (subPlanoModelos != null) {
          subPlanoModelos.removeAll(Collections.singleton(null));
          for (MetaInstituto planoModelo2 : subPlanoModelos) {
            List<MetaInstituto> subPlanoModelos2 = planoModelo2.getItens();
            if (subPlanoModelos2 != null) {
              subPlanoModelos2.removeAll(Collections.singleton(null));
              for (MetaInstituto planoModelo3 : subPlanoModelos2) {
                List<MetaInstituto> subPlanoModelos3 = planoModelo3.getItens();
                if (subPlanoModelos3 != null) {
                  subPlanoModelos3.removeAll(Collections.singleton(null));
                  for (MetaInstituto planoModelo4 : subPlanoModelos3) {
                    List<MetaInstituto> subPlanoModelos4 = planoModelo4
                        .getItens();
                    if (subPlanoModelos4 != null) {
                      subPlanoModelos4.removeAll(Collections.singleton(null));
                      for (MetaInstituto planoModelo5 : subPlanoModelos4) {
                        List<MetaInstituto> subPlanoModelos5 = planoModelo5
                            .getItens();
                        if (subPlanoModelos5 != null) {
                          subPlanoModelos5.removeAll(Collections
                              .singleton(null));
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }*/
      form.setItens(planoModelos);
    } else {
      form.setItens(new ArrayList<MetaInstituto>());
    }
    ModelAndView model = null;
    
    if("ROLE_METAS_DIRIGENTE".equals( request.getSession().getAttribute("ROLE_CONTROLE") ) ){
      model = new ModelAndView("planoModelo.dirigente.listar", "planoModeloForm", form);      
    } else {
      model = new ModelAndView("planoModelo.listar",  "planoModeloForm", form);
    }
    model.addObject("instituto", instituto);
    model.addObject("rodizio", Boolean.TRUE);
    return model;
  }

  @RequestMapping("/")
  public ModelAndView listBaseInstituto(Map<String, Object> map) {

    PlanoModeloForm form2 = new PlanoModeloForm();

    ModelAndView model = new ModelAndView("planoModelo.selecionarInstituto",
        "planoModeloForm", form2);

    model
        .addObject("planoModeloList", baseInstitutoService.findByRodizio(true));

    model.addObject("rodizio", true);

    return model;
  }

  @RequestMapping("/editar")
  public ModelAndView editarPlanoModelos(
      @ModelAttribute("planoModeloForm") PlanoModeloForm form) {

    List<MetaInstituto> planoModelos = metasInstitutoService
        .listMetaInstitutoByInstituto(form.getInstituto().getId());

    if (planoModelos != null) {
      cleanPlanoModeloList(planoModelos);
      /*for (MetaInstituto planoModelo : planoModelos) {
        List<MetaInstituto> subPlanoModelos = planoModelo.getItens();
        if (subPlanoModelos != null) {
          subPlanoModelos.removeAll(Collections.singleton(null));
          for (MetaInstituto planoModelo2 : subPlanoModelos) {
            List<MetaInstituto> subPlanoModelos2 = planoModelo2.getItens();
            if (subPlanoModelos2 != null) {
              subPlanoModelos2.removeAll(Collections.singleton(null));
              for (MetaInstituto planoModelo3 : subPlanoModelos2) {
                List<MetaInstituto> subPlanoModelos3 = planoModelo3.getItens();
                if (subPlanoModelos3 != null) {
                  subPlanoModelos3.removeAll(Collections.singleton(null));
                  for (MetaInstituto planoModelo4 : subPlanoModelos3) {
                    List<MetaInstituto> subPlanoModelos4 = planoModelo4
                        .getItens();
                    if (subPlanoModelos4 != null) {
                      subPlanoModelos4.removeAll(Collections.singleton(null));
                      for (MetaInstituto planoModelo5 : subPlanoModelos4) {
                        List<MetaInstituto> subPlanoModelos5 = planoModelo5
                            .getItens();
                        if (subPlanoModelos5 != null) {
                          subPlanoModelos5.removeAll(Collections
                              .singleton(null));
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }*/
      form.setItens(planoModelos);
    } else {
      form.setItens(new ArrayList<MetaInstituto>());
    }

    if (form.getItens().size() == 0)
      form.getItens().add(new MetaInstituto());

    BaseInstituto instituto = baseInstitutoService.findById(form
        .getInstituto().getId());

    ModelAndView model = new ModelAndView("planoModelo.editarLista",
        "planoModeloForm", form);

    List<TipoFrequencia> frequenciaList = new ArrayList<TipoFrequencia>(
        Arrays.asList(TipoFrequencia.values()));
    model.addObject("frequenciaList", frequenciaList);

    List<DiaSemana> semanaList = new ArrayList<DiaSemana>(
        Arrays.asList(DiaSemana.values()));
    model.addObject("semanaList", semanaList);

    model.addObject("instituto", instituto);

    model.addObject("rodizio", Boolean.TRUE);

    return model;

  }

  @RequestMapping("/organizar")
  public ModelAndView organizarPlanoModelos(
      @ModelAttribute("planoModeloForm") PlanoModeloForm form) {

    List<MetaInstituto> planoModelos = metasInstitutoService
        .listMetaInstitutoByInstituto(form.getInstituto().getId());

    if (planoModelos != null) {
      cleanPlanoModeloList(planoModelos);
      /*for (MetaInstituto planoModelo : planoModelos) {
        List<MetaInstituto> subPlanoModelos = planoModelo.getItens();
        if (subPlanoModelos != null) {
          subPlanoModelos.removeAll(Collections.singleton(null));
          for (MetaInstituto planoModelo2 : subPlanoModelos) {
            List<MetaInstituto> subPlanoModelos2 = planoModelo2.getItens();
            if (subPlanoModelos2 != null) {
              subPlanoModelos2.removeAll(Collections.singleton(null));
              for (MetaInstituto planoModelo3 : subPlanoModelos2) {
                List<MetaInstituto> subPlanoModelos3 = planoModelo3.getItens();
                if (subPlanoModelos3 != null) {
                  subPlanoModelos3.removeAll(Collections.singleton(null));
                  for (MetaInstituto planoModelo4 : subPlanoModelos3) {
                    List<MetaInstituto> subPlanoModelos4 = planoModelo4
                        .getItens();
                    if (subPlanoModelos4 != null) {
                      subPlanoModelos4.removeAll(Collections.singleton(null));
                      for (MetaInstituto planoModelo5 : subPlanoModelos4) {
                        List<MetaInstituto> subPlanoModelos5 = planoModelo5
                            .getItens();
                        if (subPlanoModelos5 != null) {
                          subPlanoModelos5.removeAll(Collections
                              .singleton(null));
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }*/
      form.setItens(planoModelos);
    } else {
      form.setItens(new ArrayList<MetaInstituto>());
    }

    if (form.getItens().size() == 0)
      form.getItens().add(new MetaInstituto());

    BaseInstituto instituto = baseInstitutoService.findById(form
        .getInstituto().getId());

    ModelAndView model = new ModelAndView("planoModelo.organizar",
        "planoModeloForm", form);

    List<TipoFrequencia> frequenciaList = new ArrayList<TipoFrequencia>(
        Arrays.asList(TipoFrequencia.values()));
    model.addObject("frequenciaList", frequenciaList);

    List<DiaSemana> semanaList = new ArrayList<DiaSemana>(
        Arrays.asList(DiaSemana.values()));
    model.addObject("semanaList", semanaList);

    model.addObject("instituto", instituto);

    model.addObject("rodizio", Boolean.TRUE);

    return model;

  }

  @RequestMapping(value = "/process", method = RequestMethod.POST)
  public ModelAndView save(
      @ModelAttribute("planoModeloForm") PlanoModeloForm planoModeloForm) {

    BaseInstituto instituto = baseInstitutoService.findById(planoModeloForm
        .getInstituto().getId());

    List<MetaInstituto> planoModelos = planoModeloForm.getItens();

    ModelAndView model = new ModelAndView("planoModelo.listar",
        "planoModeloForm", planoModeloForm);

    model.addObject("instituto", instituto);

    model.addObject("rodizio", Boolean.TRUE);

    return model;
  }

  @RequestMapping(value = "/sort", method = RequestMethod.POST)
  public ModelAndView sort(
      @ModelAttribute("planoModeloForm") PlanoModeloForm planoModeloForm) {

    List<MetaInstituto> planoModelos = null;

    String organizar = planoModeloForm.getOrganizar();

    try {
      ObjectMapper mapper = new ObjectMapper();

      ItemPlanoModeloWrapper[] novasPlanoModelos = mapper.readValue(organizar,
          ItemPlanoModeloWrapper[].class);

      int sortOrder = 1;

      for (ItemPlanoModeloWrapper planoModeloWrapper : novasPlanoModelos) {
        updateGrupo(planoModeloWrapper, sortOrder, null);
        sortOrder++;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    // metasInstitutoService.updatePlanoModelo(planoModelo);

    planoModelos = metasInstitutoService
        .listMetaInstitutoByInstituto(planoModeloForm.getInstituto().getId());

    planoModeloForm.setItens(planoModelos);

    BaseInstituto instituto = baseInstitutoService
        .findByIdOverview(planoModeloForm.getInstituto().getId());

    ModelAndView model = new ModelAndView("planoModelo.listar",
        "planoModeloForm", planoModeloForm);

    model.addObject("instituto", instituto);

    model.addObject("rodizio", Boolean.TRUE);

    return model;
  }

  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String addPlanoModelo(Map<String, Object> map,
      @ModelAttribute("planoModeloForm") PlanoModeloForm planoModeloForm) {

    MetaInstituto planoModelo = new MetaInstituto();

    BaseInstituto base = baseInstitutoService
        .findByIdOverview(planoModeloForm.getInstituto().getId());

    planoModelo.setInstituto(base);

    map.put("planoModelo", planoModelo);

    map.put("rodizio", true);

    return "planoModelo.novo";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String addPlanoModelo(
      @ModelAttribute("planoModelo") MetaInstituto planoModelo,
      BindingResult result, RedirectAttributes planoModeloForm) {
    
    if(planoModelo.getAtividade() == null || planoModelo.getAtividade().getId() == null){
      planoModelo.setAtividade(null);
    }
    
    if(planoModelo.getRodizio() == null || planoModelo.getRodizio().getId() == null){
      planoModelo.setRodizio(null);
    }
    
    if(planoModelo.getPai() == null || planoModelo.getPai().getId() == null){
      planoModelo.setPai(null);
    }

    metasInstitutoService.addMetasInstituto(planoModelo);

    planoModeloForm.addFlashAttribute("planoModeloForm", new PlanoModeloForm(
        planoModelo.getInstituto().getId()));

    return "redirect:/gestao/planoModelo/listar";
  }

  @RequestMapping(value = "/edit/{planoModeloId}", method = RequestMethod.POST)
  public String editPlanoModelo(Map<String, Object> map,
      @PathVariable("planoModeloId") Integer planoModeloId) {

    MetaInstituto planoModelo = metasInstitutoService
        .getMetasInstituto(planoModeloId);

    map.put("planoModelo", planoModelo);

    map.put("rodizio", true);
    
    return "planoModelo.editar";
  }

  @RequestMapping(value = "/edit/save/{planoModeloId}", method = RequestMethod.POST)
  public String editPlanoModelo(
      @ModelAttribute("planoModelo") MetaInstituto planoModelo,
      @PathVariable("planoModeloId") Integer planoModeloId,
      RedirectAttributes planoModeloForm) {
    
    if(planoModelo.getAtividade() == null || planoModelo.getAtividade().getId() == null){
      planoModelo.setAtividade(null);
    }
    
    if(planoModelo.getRodizio() == null || planoModelo.getRodizio().getId() == null){
      planoModelo.setRodizio(null);
    }
    
    if(planoModelo.getPai() == null || planoModelo.getPai().getId() == null){
      planoModelo.setPai(null);
    }

    metasInstitutoService.updateMetasInstituto(planoModelo);

    planoModeloForm.addFlashAttribute("planoModeloForm", new PlanoModeloForm(
        planoModelo.getInstituto().getId()));

    return "redirect:/gestao/planoModelo/listar";
  }

  @RequestMapping("/delete/{planoModeloId}")
  public String deletePlanoModelo(
      @PathVariable("planoModeloId") Integer planoModeloId) {

    metasInstitutoService.removeMetaInstituto(planoModeloId);

    return "redirect:/gestao/planoModelo/";
  }

  public void updateGrupo(ItemPlanoModeloWrapper planoModeloWrapper,
      Integer sortOrder, Integer idPai) {

    MetaInstituto planoModelo = metasInstitutoService
        .getMetasInstituto(planoModeloWrapper.getId());

    planoModelo.setViewOrder(sortOrder);

    if (idPai != null) {
      MetaInstituto pai = metasInstitutoService.getMetasInstituto(idPai);
      planoModelo.setPai(pai);
    } else {
      planoModelo.setPai(null);
    }

    metasInstitutoService.updateMetasInstituto(planoModelo);

    if (planoModeloWrapper.getChildren() != null
        && planoModeloWrapper.getChildren().length > 0) {
      int order = 1;
      for (ItemPlanoModeloWrapper planoModeloWrapper2 : planoModeloWrapper
          .getChildren()) {
        updateGrupo(planoModeloWrapper2, order, planoModelo.getId());
        order++;
      }
    }
  }

  public void insertGrupo(List<MetaInstituto> atividades,
      BaseInstituto instituto, MetaInstituto pai) {
    for (MetaInstituto atividade : atividades) {
      if (!atividade.getDescricao().trim().equals("")) {
        atividade.setInstituto(instituto);
        atividade.setPai(pai);
        if (atividade.getId() == null || atividade.getId().equals(0)) {
          metasInstitutoService.addMetasInstituto(atividade);
        } else {
          metasInstitutoService.updateMetasInstituto(atividade);
        }
        if (atividade.getItens() != null) {
          insertGrupo(atividade.getItens(), instituto, atividade);
        }
      }
    }
  }

  @RequestMapping(value = "upload", method = RequestMethod.GET)
  public String upload(Map<String, Object> map,
      @ModelAttribute("planoModeloForm") PlanoModeloForm form) {
    map.put("rodizio", true);
    map.put("planoModeloForm", form);
    return "planoModelo.upload";
  }

  @RequestMapping(value = "upload", method = RequestMethod.POST)
  public String upload(HttpServletRequest request, Map<String, Object> map,
      @ModelAttribute("planoModeloForm") PlanoModeloForm form) {
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    MultipartFile multipartFile = multipartRequest.getFile("file");

    int count = 0;
    String extension = FilenameUtils.getExtension(multipartFile
        .getOriginalFilename());
    if (extension.trim().equalsIgnoreCase("xlsx")) {
      try {
        count = processExcelFile(multipartFile.getInputStream(), map);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    map.put("planoModeloForm", form);

    map.put("rodizio", true);
    return "planoModelo.uploadvalidate";
  }

  @RequestMapping(value = "/processImport", method = RequestMethod.POST)
  public ModelAndView processImport(
      @ModelAttribute("planoModeloForm") PlanoModeloForm planoModeloForm) {

    List<MetaInstituto> metas = planoModeloForm.getItens();

    BaseInstituto instituto = baseInstitutoService.findById(planoModeloForm
        .getInstituto().getId());

    insertGrupo(metas, instituto, null);

    metas = metasInstitutoService.listMetaInstitutoByInstituto(planoModeloForm
        .getInstituto().getId());

    ModelAndView model = new ModelAndView("planoModelo.listar",
        "planoModeloForm", planoModeloForm);

    model.addObject("instituto", instituto);

    model.addObject("rodizio", Boolean.TRUE);

    return model;
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

      evaluator = wb.getCreationHelper().createFormulaEvaluator();

      Integer numLinha = 8;
      String nome = null;
      List<MetaInstitutoExcel> listMetaInstitutoExcels = new ArrayList<MetaInstitutoExcel>();

      int tentativas = 0;

      do {
        linha = sheet.getRow(numLinha);
        if (linha == null) {
          numLinha++;
          tentativas++;
        } else {
          cell = (XSSFCell) linha.getCell(2);
          nome = getCellValue(cell, sheet);
          if (!Util.isNullOrEmpty(nome)) {
            MetaInstitutoExcel ae = processaLinha(numLinha, sheet);
            listMetaInstitutoExcels.add(ae);
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

      reorganizar(listMetaInstitutoExcels);

      map.put("listAtividades", listMetaInstitutoExcels);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return count;

  }

  private void reorganizar(List<MetaInstitutoExcel> listMetaInstitutoExcels) {
    List<MetaInstitutoExcel> listRemover = new ArrayList<MetaInstitutoExcel>();

    for (int i = 10; i > 1; i--) {
      for (MetaInstitutoExcel atividadeExcel : listMetaInstitutoExcels) {
        String id = atividadeExcel.getId();
        String[] idArray = Util.createIdArray(id);

        if (idArray.length == i) {
          System.out.println("ID " + idArray.length);
          for (MetaInstitutoExcel atividade : listMetaInstitutoExcels) {
            String subId = atividade.getId();
            String[] subIdArray = Util.createIdArray(subId);
            System.out.println("SUB " + subIdArray.length);
            if (subIdArray.length == (i - 1)) {
              if (Util.idArrayCompare(subIdArray, idArray)) {
                if (atividade.getLista() == null)
                  atividade.setLista(new ArrayList<MetaInstitutoExcel>());
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
    listMetaInstitutoExcels.removeAll(listRemover);
  }

  private MetaInstitutoExcel processaLinha(Integer numLinha, Sheet sheet) {

    int viewOrder;
    boolean grupo;
    boolean atividade;
    boolean acao;
    String nome;
    Row linha = sheet.getRow(numLinha);
    XSSFCell cell = (XSSFCell) linha.getCell(2);

    nome = getCellValue(cell, sheet);
    cell = (XSSFCell) linha.getCell(1);
    String sViewOrder = getCellValue(cell, sheet);

    cell = (XSSFCell) linha.getCell(3);
    String sGrupo = getCellValue(cell, sheet);
    grupo = !sGrupo.trim().toUpperCase().equals("");

    cell = (XSSFCell) linha.getCell(4);
    String sAtividade = getCellValue(cell, sheet);
    atividade = !sAtividade.trim().toUpperCase().equals("");

    cell = (XSSFCell) linha.getCell(5);
    String sAcao = getCellValue(cell, sheet);
    acao = !sAcao.trim().toUpperCase().equals("");

    // if(sViewOrder.indexOf('.') != -1){
    // String array[] = sViewOrder.split("\\.");
    // sViewOrder = array[nivel];
    // }

    MetaInstitutoExcel ae = new MetaInstitutoExcel(sViewOrder, nome, grupo,
        atividade, acao);

    System.out.print(sViewOrder);
    System.out.print(" ");
    System.out.println(nome);

    return ae;

  }

  public String getCellValue(XSSFCell cell, Sheet mySheet) {
    String retorno = "";

    switch (cell.getCellType()) {
    case XSSFCell.CELL_TYPE_NUMERIC:
      CellValue cValue = evaluator.evaluate(cell);

      double dv = cValue.getNumberValue();

      if (HSSFDateUtil.isCellDateFormatted(cell)) {

        Date date = HSSFDateUtil.getJavaDate(dv);

        String dateFmt = cell.getCellStyle().getDataFormatString();
        /*
         * strValue = new SimpleDateFormat(dateFmt).format(date); - won't work
         * as Java fmt differs from Excel fmt. If Excel date format is
         * mm/dd/yyyy, Java will always be 00 for date since "m" is minutes of
         * the hour.
         */

        retorno = new CellDateFormatter(dateFmt).format(date);
        // takes care of idiosyncrasies of Excel

        retorno = new SimpleDateFormat("dd/MM/yyyy").format(date);
      } else {
        NumberFormat df = NumberFormat
            .getNumberInstance(new Locale("pt", "BR"));
        df.setMaximumFractionDigits(0);
        retorno = String.valueOf(df.format(cell.getNumericCellValue()));
      }
      break;
    case XSSFCell.CELL_TYPE_STRING:
      retorno = String.valueOf(cell.getStringCellValue());
      break;
    default:
      retorno = String.valueOf(cell.getRawValue());
      break;
    }
    if (retorno == null || retorno.trim().equals("null")) {
      retorno = "";
    }
    return retorno;
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
   *          the binder
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
  
  protected void cleanPlanoModeloList(List<MetaInstituto> listaPlanos){
    if (listaPlanos != null) {
      listaPlanos.removeAll(Collections.singleton(null));
      for (MetaInstituto planoModelo : listaPlanos) {
        List<MetaInstituto> listaFilhos = planoModelo.getItens();
          cleanPlanoModeloList(listaFilhos);
        }
      }
  }
}
