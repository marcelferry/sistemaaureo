package com.concafras.gestao.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.concafras.gestao.enums.SituacaoMeta;
import com.concafras.gestao.enums.TipoMeta;
import com.concafras.gestao.form.EntidadeOptionForm;
import com.concafras.gestao.form.InstitutoOptionForm;
import com.concafras.gestao.form.MetaForm;
import com.concafras.gestao.form.PlanoMetasForm;
import com.concafras.gestao.form.RelatorioForm;
import com.concafras.gestao.form.RodizioVO;
import com.concafras.gestao.helper.MetasHelper;
import com.concafras.gestao.model.BaseEntidade;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.MetaEntidade;
import com.concafras.gestao.model.MetaInstituto;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.PlanoMetas;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.rest.utils.RestUtils;
import com.concafras.gestao.service.BaseInstitutoService;
import com.concafras.gestao.service.EntidadeService;
import com.concafras.gestao.service.MetaService;
import com.concafras.gestao.service.MetasInstitutoService;
import com.concafras.gestao.service.PessoaService;
import com.concafras.gestao.service.PlanoMetasService;
import com.concafras.gestao.service.RodizioService;
import com.concafras.gestao.util.FlyingSaucerPDFCreationListener;
import com.concafras.gestao.util.MyPDFPageEvents;

@Controller
@RequestMapping("/gestao/relatorio")
public class RelatorioController {
  
  private static final Logger logger = LoggerFactory.getLogger(RelatorioController.class);

  @Autowired
  PlanoMetasService planoMetasService;

  @Autowired
  private MetaService metaService;

  @Autowired
  private PessoaService pessoaService;
  
  @Autowired
  private EntidadeService entidadeService;

  @Autowired
  private RodizioService rodizioService;

  @Autowired
  private MetasInstitutoService metaInstitutoService;

  @Autowired
  private BaseInstitutoService baseInstitutoService;

  @RequestMapping("/statusEntidadePreenchimentoCiclo")
  public ModelAndView statusEntidadePreenchimentoCiclo(
      Map<String, Object> map) {

    RelatorioForm form = new RelatorioForm();

    ModelAndView model = new ModelAndView("relatorio.preenchimentoPrevio",
        "relatorioForm", form);

    model.addObject("rodizioList", rodizioService.findAll());

    model.addObject("action", "statusEntidadePreenchimentoCiclo");

    model.addObject("relatorio", true);

    return model;
  }

  @RequestMapping(value = "/statusEntidadePreenchimentoCiclo/{ciclo}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String statusEntidadePreenchimentoCiclo(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo)
      throws IOException {

    List<EntidadeOptionForm> retorno = null;

    retorno = planoMetasService.getEntidadesParticipantes(ciclo);

    return new RestUtils<EntidadeOptionForm>().createDatatableResponse(retorno);

  }

  @RequestMapping(value = "/statusEntidadePreenchimentoCiclo/{ciclo}/{entidade}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String statusEntidadePreenchimentoCiclo(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo,
      @PathVariable("entidade") Integer entidade) throws IOException {

    List<InstitutoOptionForm> retorno = null;

    retorno = planoMetasService.getInstitutosPreechidos(ciclo, entidade);

    return new RestUtils<InstitutoOptionForm>()
        .createDatatableResponse(retorno);

  }

  @RequestMapping(value = "/metascontratadasporentidade/ciclos")
  public ModelAndView metasContratadasPorEntidade(Map<String, Object> map) {

    RelatorioForm form = new RelatorioForm();
    ModelAndView model = new ModelAndView("relatorio.metascontratadas.entidade",
        "relatorioForm", form);

    model.addObject("rodizioList", rodizioService.findAll());
    model.addObject("relatorio", true);

    return model;
  }

  @RequestMapping(value = "/metascontratadasporentidade/ciclo/{ciclo}/entidades", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String metasContratadasPorEntidade(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo)
      throws IOException {

    List<EntidadeOptionForm> retorno = null;

    retorno = planoMetasService.getEntidadesParticipantes(ciclo);

    return new RestUtils<EntidadeOptionForm>().createDatatableResponse(retorno);

  }

  @RequestMapping(value = "/metascontratadasporentidade/ciclo/{ciclo}/entidade/{entidade}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String statusEntidadeContratacaoCiclo(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo,
      @PathVariable("entidade") Integer entidade) throws IOException {

    List<InstitutoOptionForm> retorno = null;

    retorno = planoMetasService.getInstitutosContratados(ciclo, entidade);

    return new RestUtils<InstitutoOptionForm>()
        .createDatatableResponse(retorno);

  }

  @RequestMapping("/statusInstitutoContratacaoCiclo")
  public ModelAndView statusInstitutoContratacaoCiclo(Map<String, Object> map) {

    RelatorioForm form = new RelatorioForm();

    ModelAndView model = new ModelAndView("relatorio.contratacoes.instituto",
        "relatorioForm", form);

    model.addObject("rodizioList", rodizioService.findAll());

    model.addObject("action", "statusInstitutoContratacaoCiclo");

    model.addObject("relatorio", true);

    return model;
  }

  @RequestMapping(value = "/statusInstitutoContratacaoCiclo/{ciclo}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String statusInstitutoContratacaoCiclo(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo)
      throws IOException {

    List<InstitutoOptionForm> retorno = new ArrayList<InstitutoOptionForm>();

    List<BaseInstituto> lista = baseInstitutoService.findByRodizio(true);

    for (BaseInstituto baseInstituto : lista) {
      InstitutoOptionForm item = new InstitutoOptionForm();
      item.setId(baseInstituto.getId());
      item.setDescricao(baseInstituto.getDescricao());
      retorno.add(item);
    }

    return new RestUtils<InstitutoOptionForm>()
        .createDatatableResponse(retorno);

  }

  @RequestMapping(value = "/statusInstitutoContratacaoCiclo/{ciclo}/{entidade}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String statusInstitutoContratacaoCiclo(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo,
      @PathVariable("entidade") Integer entidade) throws IOException {

    List<InstitutoOptionForm> retorno = null;

    retorno = planoMetasService.getInstitutosContratados(ciclo, entidade);

    return new RestUtils<InstitutoOptionForm>()
        .createDatatableResponse(retorno);

  }

  @RequestMapping("/fichasembranco")
  public ModelAndView fichasEmBranco(Map<String, Object> map) {

    RelatorioForm form = new RelatorioForm();

    ModelAndView model = new ModelAndView("relatorio.geral.fichasembranco",
        "relatorioForm", form);

    model.addObject("institutoList",
        baseInstitutoService.findByRodizioOverview(true));

    model.addObject("relatorio", true);

    return model;
  }

  @RequestMapping("/entidade/fichasimpressao/{ciclo}")
  public ModelAndView fichasImpressao(Map<String, Object> map,
      @PathVariable("ciclo") Integer ciclo) {

    RelatorioForm form = new RelatorioForm();

    ModelAndView model = new ModelAndView("relatorio.entidade.fichasimpressao",
        "relatorioForm", form);

    model.addObject("relatorio", true);

    Rodizio rodizio = rodizioService.findById(ciclo);

    map.put("CICLO_CONTROLE", rodizio);

    return model;
  }

  /**
   * Impressão da ficha antes de salvar
   * 
   * @param planoMetasForm
   * @return
   */
  @RequestMapping("/imprimePreFichaRodizio")
  public ModelAndView preShowMetas(
      @ModelAttribute("planoMetasForm") PlanoMetasForm planoMetasForm) {

    restauraRodizio(planoMetasForm);
    restauraInstituto(planoMetasForm);
    restauraEntidade(planoMetasForm);
    restauraFacilitador(planoMetasForm);

    planoMetasForm.setFase(3);

    List<PlanoMetasForm> lista = new ArrayList<PlanoMetasForm>();

    lista.add(planoMetasForm);

    ModelAndView model = new ModelAndView("/app/metas/relatorioMetas.jsp");

    model.addObject("planoList", lista);

    List<SituacaoMeta> situacaoList = new ArrayList<SituacaoMeta>(
        Arrays.asList(SituacaoMeta.values()));
    model.addObject("situacaoList", situacaoList);

    return model;
  }

  /**
   * Impressão da ficha preenchida
   * 
   * @param planoMetasForm
   * @return
   */
  @RequestMapping("/imprimeFichaRodizio")
  public ModelAndView showMetas(
      @ModelAttribute("planoMetasForm") @Validated PlanoMetasForm planoMetasForm) {

    List<PlanoMetasForm> lista = obtemDadosInstituto(planoMetasForm);

    ModelAndView model = new ModelAndView("/app/metas/relatorioMetas.jsp");

    model.addObject("planoList", lista);

    List<SituacaoMeta> situacaoList = new ArrayList<SituacaoMeta>(
        Arrays.asList(SituacaoMeta.values()));
    model.addObject("situacaoList", situacaoList);

    return model;
  }

  /**
   * Impressão da ficha preenchida
   * 
   * @param planoMetasForm
   * @return
   */
  @RequestMapping("/imprimeFichaRodizio/XLS")
  public void showMetasXLS(HttpServletRequest request,
      HttpServletResponse response,
      @ModelAttribute("planoMetasForm") @Validated PlanoMetasForm planoMetasForm) {

    List<PlanoMetasForm> lista = obtemDadosInstituto(planoMetasForm);

    Workbook wb = new XSSFWorkbook();

    styles = createStyles(wb);

    geraPlanilha(wb, lista, false);

    // Write the output to a file
    response.setHeader("Content-disposition", "attachment; filename=METAS_TODOS_INSTITUTOS.xlsx");
    try {
      wb.write(response.getOutputStream());
      wb.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Metodo responsavel pela preparação do dados para impressão da ficha em
   * branco.
   * 
   * @param request
   * @param planoMetasForm
   * @return
   */
  @RequestMapping("/imprimePreFichaBranco")
  public ModelAndView preShowMetasBranco(HttpServletRequest request,
      @ModelAttribute("planoMetasForm") @Validated PlanoMetasForm planoMetasForm) {

    restauraRodizio(planoMetasForm);
    restauraInstituto(planoMetasForm);
    restauraEntidade(planoMetasForm);
    restauraFacilitador(planoMetasForm);

    if (request.getSession().getAttribute("INSTITUICAO_CONTROLE") != null) {
      BaseEntidade base = (BaseEntidade) request.getSession()
          .getAttribute("INSTITUICAO_CONTROLE");
      BaseEntidade entidade = entidadeService.findByIdOverview(base.getId());
      planoMetasForm.setEntidade(entidade);
    }

    List<MetaForm> metasForm = null;

    List<MetaInstituto> metasIntituto = metaInstitutoService
        .listMetaInstitutoByInstituto(planoMetasForm.getInstituto().getId(), true);

    PlanoMetas planoMetasAtual = null;

    if (planoMetasAtual == null) {
      metasForm = new MetasHelper(metaService).mapMetaInstitutoToMetaForm(metasIntituto,
          planoMetasForm.getFacilitador(), planoMetasForm.getContratante(),
          planoMetasForm.getEvento(), planoMetasForm.getRodizio());
    }

    planoMetasForm.setDependencias(metasForm);

    planoMetasForm.setFase(3);

    List<PlanoMetasForm> lista = new ArrayList<PlanoMetasForm>();

    lista.add(planoMetasForm);

    ModelAndView model = new ModelAndView("/app/metas/relatorioMetas.jsp");

    model.addObject("planoList", lista);

    List<SituacaoMeta> situacaoList = new ArrayList<SituacaoMeta>(
        Arrays.asList(SituacaoMeta.values()));
    model.addObject("situacaoList", situacaoList);

    model.addObject("branco", true);

    return model;

  }

  /**
   * Metodo responsavel pela preparação do dados para impressão da ficha em
   * branco.
   * 
   * @param request
   * @param planoMetasForm
   * @return
   */
  @RequestMapping("/imprimeTodasFichaBranco")
  public ModelAndView todasShowMetasBranco(HttpServletRequest request,
      @ModelAttribute("planoMetasForm") PlanoMetasForm planoMetasForm) {

    BaseEntidade entidade = null;

    if (request.getSession().getAttribute("INSTITUICAO_CONTROLE") != null) {
      entidade = (BaseEntidade) request.getSession().getAttribute("INSTITUICAO_CONTROLE");
      entidade = entidadeService.findByIdOverview(entidade.getId());

    }

    List<PlanoMetasForm> lista = obtemDadosTodosInstitutos(planoMetasForm, entidade);

    ModelAndView model = new ModelAndView("/app/metas/relatorioMetas.jsp");

    model.addObject("planoList", lista);

    List<SituacaoMeta> situacaoList = new ArrayList<SituacaoMeta>(
        Arrays.asList(SituacaoMeta.values()));
    model.addObject("situacaoList", situacaoList);

    model.addObject("branco", true);

    return model;

  }

  private static SimpleDateFormat fmt = new SimpleDateFormat("MM/yy");
  private Map<String, CellStyle> styles;

  /**
   * Metodo responsavel pela preparação do dados para impressão da ficha em
   * branco.
   * 
   * @param request
   * @param planoMetasForm
   * @return
   */
  @RequestMapping("/imprimeTodasFichaBranco/XLS")
  public void todasShowMetasBrancoXLS(HttpServletRequest request,
      HttpServletResponse response,
      @ModelAttribute("planoMetasForm") PlanoMetasForm planoMetasForm) {

    BaseEntidade entidade = null;

    if (request.getSession().getAttribute("INSTITUICAO_CONTROLE") != null) {
      entidade = (BaseEntidade) request.getSession().getAttribute("INSTITUICAO_CONTROLE");
      entidade = entidadeService.findByIdOverview(entidade.getId());
    }

    List<PlanoMetasForm> lista = obtemDadosTodosInstitutos(planoMetasForm, entidade);

    Workbook wb = new XSSFWorkbook();

    styles = createStyles(wb);

    geraPlanilha(wb, lista, true);

    // Write the output to a file
    response.setHeader("Content-disposition", "attachment; filename=FICHAS_TODOS_INSTITUTOS.xlsx");
    try {
      wb.write(response.getOutputStream());
      wb.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
  

  /**
   * Metodo responsavel pela preparação do dados para impressão da ficha em
   * branco.
   * 
   * @param request
   * @param planoMetasForm
   * @return
   */
  @RequestMapping("/imprimeTodasFicha")
  public ModelAndView todasShowMetas(HttpServletRequest request, @ModelAttribute("planoMetasForm") PlanoMetasForm planoMetasForm) {

    List<PlanoMetasForm> lista = obtemDadosTodosInstitutos(planoMetasForm);

    ModelAndView model = new ModelAndView("/app/metas/relatorioMetas.jsp");

    model.addObject("planoList", lista);

    List<SituacaoMeta> situacaoList = new ArrayList<SituacaoMeta>(
        Arrays.asList(SituacaoMeta.values()));
    model.addObject("situacaoList", situacaoList);

    model.addObject("branco", true);

    return model;

  }
  
  
  /**
   * Metodo responsavel pela preparação do dados para impressão da ficha.
   * 
   * @param request
   * @param planoMetasForm
   * @return
   */
  @RequestMapping("/imprimeTodasFicha/XLS")
  public void todasShowMetasXLS(HttpServletRequest request,
      HttpServletResponse response,
      @ModelAttribute("planoMetasForm") PlanoMetasForm planoMetasForm) {
    
    List<PlanoMetasForm> lista = obtemDadosTodosInstitutos(planoMetasForm);
    
    Workbook wb = new XSSFWorkbook();
    
    styles = createStyles(wb);
    
    geraPlanilha(wb, lista, false);
    
    // Write the output to a file
    response.setHeader("Content-disposition", "attachment; filename=FICHAS_TODOS_INSTITUTOS.xlsx");
    try {
      wb.write(response.getOutputStream());
      wb.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

  private List<PlanoMetasForm> obtemDadosTodosInstitutos(PlanoMetasForm planoMetasForm) {

    Rodizio rodizio = restauraRodizio(planoMetasForm);
    BaseEntidade entidade = restauraEntidade(planoMetasForm);
    Pessoa facilitador = restauraFacilitador(planoMetasForm);

    List<BaseInstituto> listaInstituto = baseInstitutoService.findByRodizioOverview(true);

    List<PlanoMetasForm> lista = new ArrayList<PlanoMetasForm>();

    for (BaseInstituto instituto : listaInstituto) {
      PlanoMetasForm plano = preparaPlanoMetasForm(entidade, instituto, rodizio, facilitador);
      lista.add(plano);
    }
    
    return lista;
  }
  
  private List<PlanoMetasForm> obtemDadosInstituto(PlanoMetasForm planoMetasForm) {
    Rodizio rodizio = restauraRodizio(planoMetasForm);
    BaseInstituto instituto = restauraInstituto(planoMetasForm);
    BaseEntidade entidade = restauraEntidade(planoMetasForm);
    Pessoa facilitador = restauraFacilitador(planoMetasForm);


    List<PlanoMetasForm> lista = new ArrayList<PlanoMetasForm>();

    PlanoMetasForm plano = preparaPlanoMetasForm(entidade, instituto, rodizio, facilitador);
    
    lista.add(plano);

    return lista;
  }
  
  
  
  private PlanoMetasForm preparaPlanoMetasForm(BaseEntidade entidade, BaseInstituto instituto, Rodizio rodizio, Pessoa facilitador) {
    
    PlanoMetasForm planoMetasForm = new PlanoMetasForm();
    
    if (entidade != null) {
      planoMetasForm.setEntidade(entidade);
    }

    planoMetasForm.setFacilitador(facilitador);

    planoMetasForm.setRodizio( new RodizioVO( rodizio ) );
    planoMetasForm.setInstituto(instituto);
    planoMetasForm.setEvento(planoMetasForm.getEvento());
    
    List<MetaForm> metasForm = null;

    List<MetaInstituto> metasIntituto = metaInstitutoService.listMetaInstitutoByInstituto(instituto.getId(), true);

    PlanoMetas planoMetasAtual = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade.getId(), instituto.getId(), rodizio.getId());

    List<MetaEntidade> listaMetas = metaService.findByEntidadeIdAndInstitutoId(entidade.getId(), instituto.getId());

    if (planoMetasAtual != null) {
      planoMetasForm.setId(planoMetasAtual.getId());
      planoMetasForm.setTipoContratante(planoMetasAtual.getTipoContratante());
      planoMetasForm.setContratante(planoMetasAtual.getContratante());
      Pessoa presidente = pessoaService.getPessoa(planoMetasAtual.getEntidade().getPresidente().getPessoa().getId());
      planoMetasForm.setPresidente(presidente);

      planoMetasAtual.setMetas(listaMetas);
    }

    if (planoMetasAtual == null) {
      metasForm = new MetasHelper(metaService).mapMetaInstitutoToMetaForm(metasIntituto,
          planoMetasForm.getFacilitador(), planoMetasForm.getContratante(),
          planoMetasForm.getEvento(), planoMetasForm.getRodizio());

    } else if (planoMetasAtual.getMetas().size() > 0) {
      metasForm = new MetasHelper(metaService).mapMetaEntidadeToMetaForm(metasIntituto, 
          planoMetasForm.getFacilitador(),
          planoMetasForm.getContratante(), 
          planoMetasForm.getEvento(), 
          planoMetasForm.getEntidade(), 
          planoMetasForm.getRodizio());
    } else {
      metasForm = new ArrayList<MetaForm>();
    }

    planoMetasForm.setDependencias(metasForm);

    planoMetasForm.setFase(3); 
    
    return planoMetasForm;
  }

  private List<PlanoMetasForm> obtemDadosTodosInstitutos(
      PlanoMetasForm planoMetasForm, BaseEntidade entidade) {
    List<BaseInstituto> listaInstituto = baseInstitutoService.findByRodizioOverview(true);

    List<PlanoMetasForm> lista = new ArrayList<PlanoMetasForm>();

    for (BaseInstituto instituto : listaInstituto) {

      PlanoMetasForm planoMeta = new PlanoMetasForm();

      Rodizio rodizio = rodizioService.findById(planoMetasForm.getRodizio().getId());

      if (entidade != null) {
        planoMeta.setEntidade(entidade);
      }

      if (planoMetasForm.getFacilitador() != null && planoMetasForm.getFacilitador().getId() != null) {
        Pessoa facilitador = pessoaService.getPessoa(planoMetasForm.getFacilitador().getId());
        planoMeta.setFacilitador(facilitador);
      }

      planoMeta.setRodizio( new RodizioVO( rodizio ) );
      planoMeta.setInstituto(instituto);
      planoMeta.setEvento(planoMetasForm.getEvento());

      List<MetaForm> metasForm = null;

      List<MetaInstituto> metasIntituto = metaInstitutoService.listMetaInstitutoByInstituto(instituto.getId(), true);

      PlanoMetas planoMetasAtual = null;

      if (planoMetasAtual == null) {
        metasForm = new MetasHelper(metaService).mapMetaInstitutoToMetaForm(metasIntituto,
            planoMetasForm.getFacilitador(), planoMetasForm.getContratante(),
            planoMetasForm.getEvento(), planoMetasForm.getRodizio());
      }

      planoMeta.setDependencias(metasForm);

      planoMeta.setFase(3);

      lista.add(planoMeta);

      planoMeta = null;

    }

    return lista;
  }

  private void geraPlanilha(Workbook wb, List<PlanoMetasForm> lista, boolean branco) {
    for (PlanoMetasForm plan : lista) {

      Sheet sheet = wb.createSheet(plan.getInstituto().getNome());
      sheet.protectSheet("password");

      // turn off gridlines
      sheet.setDisplayGridlines(false);
      sheet.setPrintGridlines(false);
      sheet.setFitToPage(true);
      sheet.setHorizontallyCenter(true);
      PrintSetup printSetup = sheet.getPrintSetup();
      printSetup.setLandscape(true);

      // the following three statements are required only for HSSF
      sheet.setAutobreaks(true);
      printSetup.setFitHeight((short) 1);
      printSetup.setFitWidth((short) 1);
      
      Row row1 = sheet.createRow((short) 0);
      Cell cell1 = row1.createCell((short) 2);
      cell1.setCellValue("PLANO DE METAS DO CENTRO ESPÍRITA");

      
      CellRangeAddress cellRangeAddress = new CellRangeAddress(
          0, //first row (0-based)
          0, //last row  (0-based)
          2, //first column (0-based)
          10  //last column  (0-based)
      );
      sheet.addMergedRegion(cellRangeAddress);
      
      
      //


      String[] titles;
      if(branco){
        titles = new String[]{ "ID", "Nível", "Atividade", "Como está?", "Data/Quant.", "Como será?", "Data/Quant.", "Observações" };
      } else {
        titles = new String[]{ "ID", "Nível", "Atividade", "Como estava?",
            "Data/Quant.", "Como está?", "Data/Quant.", "Como será?", "Data/Quant.", "Observações" };
      }

      // the header row: centered text in 48pt font
      Row headerRow = sheet.createRow(5);
      headerRow.setHeightInPoints(12.75f);
      for (int i = 0; i < titles.length; i++) {
        Cell cell = headerRow.createCell(i+1);
        cell.setCellValue(titles[i]);
        cell.setCellStyle(styles.get("header"));
      }

      // columns for 11 weeks starting from 9-Jul
      Calendar calendar = Calendar.getInstance();
      int year = calendar.get(Calendar.YEAR);

      try {
        calendar.setTime(fmt.parse("04/17"));
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      calendar.set(Calendar.YEAR, year);
      for (int i = 0; i < 0; i++) {
        Cell cell = headerRow.createCell(titles.length + i);
        cell.setCellValue(calendar);
        cell.setCellStyle(styles.get("header_date"));
        calendar.roll(Calendar.MONTH, true);
      }
      // freeze the first row
      //sheet.createFreezePane(0, 1);

      Row row;
      Cell cell;
      int rownum = 6;
      int nivel = 1;
      for (int i = 0; i < plan.getDependencias()
          .size(); i++, rownum++, nivel++) {
        rownum = processaLinha(sheet, plan.getDependencias().get(i), rownum,
            nivel + ".", branco);
      }

      // set column widths, the width is measured in units of 1/256th of a
      // character width
      int col = 0;
      sheet.setColumnWidth(col++, 256 * 3);
      sheet.setColumnWidth(col++, 256 * 6);
      sheet.setColumnWidth(col++, 256 * 6);
      sheet.setColumnWidth(col++, 256 * 65);
      if(!branco){
        sheet.setColumnWidth(col++, 256 * 20);
        sheet.setColumnWidth(col++, 256 * 12);
      }
      sheet.setColumnWidth(col++, 256 * 20);
      sheet.setColumnWidth(col++, 256 * 12);
      sheet.setColumnWidth(col++, 256 * 20);
      sheet.setColumnWidth(col++, 256 * 12);
      sheet.setColumnWidth(col++, 256 * 70);
      sheet.setZoom(80); //75% scale
      
      sheet.setColumnHidden(1,true);

    }
  }

  private static class MetaExcel {

    public Integer id;
    public TipoMeta tipoMeta;
    public SituacaoMeta situacaoAnterior;
    public Date conclusaoAnterior;
    public String descricao;
    public boolean primeiroRodizio;
    public BigDecimal realizadoAnterior;
    public SituacaoMeta situacaoAtual;
    public Date conclusaoAtual;
    public BigDecimal realizadoAtual;

  }

  private int processaLinha(Sheet sheet, MetaForm metaForm, int rownum,
      String nivel, boolean branco) {
    Row row;
    Cell cell;
    String styleName;
    row = sheet.createRow(rownum);

    MetaExcel meta = preparaMeta(metaForm);

    int cellnum = 1;
    cell = row.createCell(cellnum);
    styleName = "cell_normal";
    if(meta.id != null){
      cell.setCellValue(meta.id);
    } else {
      cell.setCellValue("");      
    }
    cell.setCellStyle(styles.get(styleName));

    cellnum++;
    cell = row.createCell(cellnum);
    styleName = "cell_bb";
    cell.setCellValue(nivel);
    cell.setCellStyle(styles.get(styleName));

    cellnum++;
    cell = row.createCell(cellnum);
    styleName = "cell_bb";
    cell.setCellValue(meta.descricao);
    cell.setCellStyle(styles.get(styleName));

    if(!branco){
      cellnum++;
      cell = row.createCell(cellnum);
      styleName = "cell_bb";
      cell.setCellValue(meta.situacaoAnterior == null ? ""
          : meta.situacaoAnterior.getSituacao());
      cell.setCellStyle(styles.get(styleName));
      
      //if (TipoMeta.META_EXECUCAO.equals(meta.tipoMeta)) {
      //  adicionaComboStatusExecucao(sheet, cell);
      //} else if (TipoMeta.META_IMPLANTACAO.equals(meta.tipoMeta)) {
      //  adicionaComboStatusImplantacao(sheet, cell);
      //}
  
      cellnum++;
      cell = row.createCell(cellnum);
      styleName = "cell_b_date";
      if(meta.conclusaoAnterior != null){
        cell.setCellValue(meta.conclusaoAnterior  );
      } 
      cell.setCellStyle(styles.get(styleName));
      
    }
    cellnum++;
    cell = row.createCell(cellnum);
    styleName = "cell_normal_unlock";
    cell.setCellValue(
        meta.situacaoAtual == null ? "" : meta.situacaoAtual.getSituacao());
    cell.setCellStyle(styles.get(styleName));
    
    if (TipoMeta.META_EXECUCAO.equals(meta.tipoMeta)) {
      adicionaComboStatusExecucao(sheet, cell);
    } else if (TipoMeta.META_IMPLANTACAO.equals(meta.tipoMeta)) {
      adicionaComboStatusImplantacao(sheet, cell);
    }

    cellnum++;
    cell = row.createCell(cellnum);
    styleName = "cell_bu_date";
    if(meta.conclusaoAtual != null){
      cell.setCellValue(meta.conclusaoAtual);
    }
    cell.setCellStyle(styles.get(styleName));

    cellnum++;
    cell = row.createCell(cellnum);
    styleName = "cell_normal_unlock";
    cell.setCellValue("");
    cell.setCellStyle(styles.get(styleName));

    adicionaComboStatusPlanejado(sheet, cell);

    cellnum++;
    cell = row.createCell(cellnum);
    styleName = "cell_bu_date";
    cell.setCellValue("");
    cell.setCellStyle(styles.get(styleName));
    
    cellnum++;
    cell = row.createCell(cellnum);
    styleName = "cell_normal_unlock";
    cell.setCellValue("");
    cell.setCellStyle(styles.get(styleName));


    int subNivel = 1;
    if (metaForm.getDependencias() != null
        && metaForm.getDependencias().size() > 0) {
      rownum++;
      for (int i = 0; i < metaForm.getDependencias()
          .size(); i++, rownum++, subNivel++) {
        rownum = processaLinha(sheet, metaForm.getDependencias().get(i), rownum,
            nivel + subNivel + ".", branco);
      }
    }

    return rownum;

  }

  private MetaExcel preparaMeta(MetaForm metaForm) {
    MetaExcel meta = new MetaExcel();
    meta.id = metaForm.getId();
    meta.descricao = metaForm.getAtividade().getDescricao();
    meta.tipoMeta = metaForm.getAtividade().getTipoMeta();
    if (metaForm.getSituacaoAnterior() == null
        || metaForm.getSituacaoAnterior().getSituacao() == null) {
      meta.primeiroRodizio = true;
      if (metaForm.getSituacaoAtual() != null) {
        if (metaForm.getSituacaoAtual().getSituacao() != null) {
          meta.situacaoAtual = metaForm.getSituacaoAtual().getSituacao();
          meta.conclusaoAtual = metaForm.getSituacaoAtual().getConclusao();
          if (meta.tipoMeta == TipoMeta.META_QUANTITATIVA) {
            meta.realizadoAtual = metaForm.getSituacaoAtual().getRealizado();
          }
        }
      }
    } else if (metaForm.getSituacaoAnterior()
        .getSituacao() == SituacaoMeta.IMPLANTADA
        || metaForm.getSituacaoAnterior()
            .getSituacao() == SituacaoMeta.IMPLPARCIAL) {
      meta.situacaoAnterior = metaForm.getSituacaoAnterior().getSituacao();
      meta.conclusaoAnterior = metaForm.getSituacaoAnterior().getConclusao();
      if (meta.tipoMeta == TipoMeta.META_QUANTITATIVA) {
        meta.realizadoAnterior = metaForm.getSituacaoAnterior().getRealizado();
      }
      meta.situacaoAtual = metaForm.getSituacaoAtual().getSituacao();
      meta.conclusaoAtual = metaForm.getSituacaoAtual().getConclusao();
      if (meta.tipoMeta == TipoMeta.META_QUANTITATIVA) {
        meta.realizadoAtual = metaForm.getSituacaoAtual().getRealizado();
      }
    } else if (metaForm.getSituacaoAnterior()
        .getSituacao() == SituacaoMeta.PLANEJADA) {
      meta.situacaoAnterior = metaForm.getSituacaoAnterior().getSituacao();
      meta.conclusaoAnterior = metaForm.getSituacaoAnterior().getPrevisao();
      meta.situacaoAtual = metaForm.getSituacaoAtual().getSituacao();
      if (metaForm.getSituacaoAtual().getSituacao() == SituacaoMeta.PLANEJADA) {
        meta.conclusaoAtual = metaForm.getSituacaoAtual().getPrevisao();
      } else {
        meta.conclusaoAtual = metaForm.getSituacaoAtual().getConclusao();
      }

    }

    return meta;
  }

  private void adicionaComboStatusImplantacao(Sheet sheet, Cell cell) {
    DataValidationHelper dvHelper = new XSSFDataValidationHelper(
        (XSSFSheet) sheet);
    DataValidationConstraint dvConstraint = dvHelper
        .createExplicitListConstraint(
            new String[] { "", "Implantada", "Parcialmente", "Não Implantada" });
    CellRangeAddressList addressList = new CellRangeAddressList(
        cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(),
        cell.getColumnIndex());
    DataValidation validation = dvHelper.createValidation(dvConstraint,
        addressList);
    validation.setShowErrorBox(true);
    sheet.addValidationData(validation);
  }

  private void adicionaComboStatusExecucao(Sheet sheet, Cell cell) {
    DataValidationHelper dvHelper = new XSSFDataValidationHelper(
        (XSSFSheet) sheet);
    DataValidationConstraint dvConstraint = dvHelper
        .createExplicitListConstraint(
            new String[] { "", "Realizada", "Não Realizada" });
    CellRangeAddressList addressList = new CellRangeAddressList(
        cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(),
        cell.getColumnIndex());
    DataValidation validation = dvHelper.createValidation(dvConstraint,
        addressList);
    validation.setShowErrorBox(true);
    sheet.addValidationData(validation);
  }
  
  private void adicionaComboStatusPlanejado(Sheet sheet, Cell cell) {
    DataValidationHelper dvHelper = new XSSFDataValidationHelper(
        (XSSFSheet) sheet);
    DataValidationConstraint dvConstraint = dvHelper
        .createExplicitListConstraint(
            new String[] { "", "Planejado", "Não Planejado" });
    CellRangeAddressList addressList = new CellRangeAddressList(
        cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(),
        cell.getColumnIndex());
    DataValidation validation = dvHelper.createValidation(dvConstraint,
        addressList);
    validation.setShowErrorBox(true);
    sheet.addValidationData(validation);
  }

  @RequestMapping(value = "/common/reportgenerator/generatePDF")
  public void generatePdf(HttpServletRequest req, HttpServletResponse res) {
    res.setContentType("text/html;charset=UTF-8");
    ServletOutputStream outStream = null;
    try {

      String content = req.getParameter("content");

      String filename = req.getParameter("filename");

      System.out.println(content.replace("&", "&amp;"));

      res.setContentType("application/pdf");
      res.setHeader("Content-Disposition",
          "attachment;filename=" + filename + ".pdf");
      outStream = res.getOutputStream();

      ITextRenderer renderer = new ITextRenderer();

      MyPDFPageEvents mppe = new MyPDFPageEvents();

      FlyingSaucerPDFCreationListener fspcl = new FlyingSaucerPDFCreationListener(
          mppe);

      renderer.setListener(fspcl);

      content = content.replace("&nbsp;", ".");
      content = content.replace("<br>", "<br/>");

      renderer.setDocumentFromString(content);
      renderer.layout();
      renderer.createPDF(outStream);

    } catch (Exception e) {
      throw new RuntimeException();
    } finally {
      try {
        outStream.flush();
        outStream.close();
      } catch (Exception ex) {
        throw new RuntimeException();
      }

    }
  }

  /**
   * create a library of cell styles
   */
  private static Map<String, CellStyle> createStyles(Workbook wb) {
    Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
    DataFormat df = wb.createDataFormat();

    CellStyle style;
    Font headerFont = wb.createFont();
    headerFont.setBold(true);
    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.CENTER);
    style
        .setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setFont(headerFont);
    styles.put("header", style);

    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.CENTER);
    style
        .setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setFont(headerFont);
    style.setDataFormat(df.getFormat("mmm/yy"));
    styles.put("header_date", style);

    Font font1 = wb.createFont();
    font1.setBold(true);
    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.LEFT);
    style.setFont(font1);
    styles.put("cell_b", style);

    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.CENTER);
    style.setFont(font1);
    styles.put("cell_b_centered", style);

    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.RIGHT);
    style.setFont(font1);
    style.setDataFormat(df.getFormat("mmm/yy"));
    styles.put("cell_b_date", style);

    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.RIGHT);
    style.setFont(font1);
    style.setDataFormat(df.getFormat("mmm/yy"));
    style.setLocked(false);
    styles.put("cell_bu_date", style);

    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.RIGHT);
    style.setFont(font1);
    style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setDataFormat(df.getFormat("d-mmm"));
    styles.put("cell_g", style);

    Font font2 = wb.createFont();
    //font2.setColor(IndexedColors.BLUE.getIndex());
    font2.setBold(true);
    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.LEFT);
    style.setFont(font2);
    styles.put("cell_bb", style);
    
    //font2.setColor(IndexedColors.BLUE.getIndex());
    font2.setBold(true);
    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.LEFT);
    style.setFont(font2);
    style.setLocked(false);
    styles.put("cell_bb_unlock", style);

    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.RIGHT);
    style.setFont(font1);
    style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setDataFormat(df.getFormat("d-mmm"));
    styles.put("cell_bg", style);

    Font font3 = wb.createFont();
    font3.setFontHeightInPoints((short) 14);
    font3.setColor(IndexedColors.DARK_BLUE.getIndex());
    font3.setBold(true);
    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.LEFT);
    style.setFont(font3);
    style.setWrapText(true);
    styles.put("cell_h", style);

    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.LEFT);
    style.setWrapText(true);
    styles.put("cell_normal", style);

    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.CENTER);
    style.setWrapText(true);
    styles.put("cell_normal_centered", style);

    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.LEFT);
    style.setWrapText(true);
    style.setLocked(false);
    styles.put("cell_normal_unlock", style);

    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.RIGHT);
    style.setWrapText(true);
    style.setDataFormat(df.getFormat("d-mmm"));
    styles.put("cell_normal_date", style);

    style = createBorderedStyle(wb);
    style.setAlignment(HorizontalAlignment.LEFT);
    style.setIndention((short) 1);
    style.setWrapText(true);
    styles.put("cell_indented", style);

    style = createBorderedStyle(wb);
    style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    styles.put("cell_blue", style);

    return styles;
  }

  private static CellStyle createBorderedStyle(Workbook wb) {
    BorderStyle thin = BorderStyle.THIN;
    short black = IndexedColors.BLACK.getIndex();

    CellStyle style = wb.createCellStyle();
    style.setBorderRight(thin);
    style.setRightBorderColor(black);
    style.setBorderBottom(thin);
    style.setBottomBorderColor(black);
    style.setBorderLeft(thin);
    style.setLeftBorderColor(black);
    style.setBorderTop(thin);
    style.setTopBorderColor(black);
    return style;
  }

  @Scheduled(initialDelay = 60000, fixedRate = 86400000)
  private void enviarAlertaVencimentoMeta() {

  }

  private Rodizio restauraRodizio(PlanoMetasForm planoMetasForm) {
    // Verifica Rodizio
    if (planoMetasForm.getRodizio() != null
        && planoMetasForm.getRodizio().getId() != null) {
      Rodizio rodizio = rodizioService
          .findById(planoMetasForm.getRodizio().getId());
      planoMetasForm.setRodizio( new RodizioVO( rodizio ) );
      return rodizio;
    } else {
      planoMetasForm.setRodizio(null);
      return null;
    }
  }

  private BaseInstituto restauraInstituto(PlanoMetasForm planoMetasForm) {
    // Verifica Instituto
    if (planoMetasForm.getInstituto() != null
        && planoMetasForm.getInstituto().getId() != null) {
      BaseInstituto instituto = baseInstitutoService
          .findByIdOverview(planoMetasForm.getInstituto().getId());
      planoMetasForm.setInstituto(instituto);
      return instituto;
    } else {
      planoMetasForm.setInstituto(null);
      return null;
    }
  }

  private BaseEntidade restauraEntidade(PlanoMetasForm planoMetasForm) {
    // Verifica Entidade
    if (planoMetasForm.getEntidade() != null
        && planoMetasForm.getEntidade().getId() != null) {
      BaseEntidade entidade = entidadeService
          .findByIdOverview(planoMetasForm.getEntidade().getId());
      planoMetasForm.setEntidade(entidade);
      return entidade;
    } else {
      planoMetasForm.setEntidade(null);
      return null;
    }
  }

  private Pessoa restauraFacilitador(PlanoMetasForm planoMetasForm) {
    // Verifica Facilitador
    if (planoMetasForm.getFacilitador() != null
        && planoMetasForm.getFacilitador().getId() != null) {
      Pessoa facilitador = pessoaService
          .getPessoa(planoMetasForm.getFacilitador().getId());
      planoMetasForm.setFacilitador(facilitador);
      return facilitador;
    } else {
      planoMetasForm.setFacilitador(null);
      return null;
    }
  }

  private Pessoa restauraPresidente(PlanoMetasForm planoMetasForm) {
    // Verifica Facilitador
    if (planoMetasForm.getPresidente() != null
        && planoMetasForm.getPresidente().getId() != null) {
      Pessoa presidente = pessoaService
          .getPessoa(planoMetasForm.getPresidente().getId());
      planoMetasForm.setPresidente(presidente);
      return presidente;
    } else {
      planoMetasForm.setPresidente(null);
      return null;
    }
  }

  private Pessoa restauraCoordenador(PlanoMetasForm planoMetasForm) {
    // Verifica Facilitador
    if (planoMetasForm.getCoordenador() != null
        && planoMetasForm.getCoordenador().getId() != null) {
      Pessoa coordenador = pessoaService
          .getPessoa(planoMetasForm.getCoordenador().getId());
      planoMetasForm.setCoordenador(coordenador);
      return coordenador;
    } else {
      planoMetasForm.setCoordenador(null);
      return null;
    }
  }

  private Pessoa restauraContratante(PlanoMetasForm planoMetasForm) {
    // Verifica Facilitador
    if (planoMetasForm.getContratante() != null
        && planoMetasForm.getContratante().getId() != null) {
      Pessoa contratante = pessoaService
          .getPessoa(planoMetasForm.getContratante().getId());
      planoMetasForm.setContratante(contratante);
      return contratante;
    } else {
      planoMetasForm.setContratante(null);
      return null;
    }
  }

}
