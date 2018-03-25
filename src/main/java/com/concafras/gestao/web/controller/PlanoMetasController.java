package com.concafras.gestao.web.controller;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.concafras.gestao.enums.EventoMeta;
import com.concafras.gestao.enums.SituacaoMeta;
import com.concafras.gestao.enums.TipoContratante;
import com.concafras.gestao.enums.TipoMeta;
import com.concafras.gestao.enums.TipoSituacaoMeta;
import com.concafras.gestao.form.EntidadeOptionForm;
import com.concafras.gestao.form.InstitutoOptionForm;
import com.concafras.gestao.form.MetaForm;
import com.concafras.gestao.form.PessoaOptionForm;
import com.concafras.gestao.form.PlanoMetasForm;
import com.concafras.gestao.form.RodizioVO;
import com.concafras.gestao.helper.MetasHelper;
import com.concafras.gestao.helper.PlanoMetasHelper;
import com.concafras.gestao.model.Anotacao;
import com.concafras.gestao.model.BaseEntidade;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.Dirigente;
import com.concafras.gestao.model.Facilitador;
import com.concafras.gestao.model.HistoricoMetaEntidade;
import com.concafras.gestao.model.MetaEntidade;
import com.concafras.gestao.model.MetaEntidadeAnotacao;
import com.concafras.gestao.model.MetaInstituto;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.PlanoMetas;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.model.view.PreContrato;
import com.concafras.gestao.model.view.ResumoMetaEntidade;
import com.concafras.gestao.rest.model.AnotacaoVO;
import com.concafras.gestao.rest.model.DatatableResponse;
import com.concafras.gestao.rest.model.HistoricoMetaEntidadeVO;
import com.concafras.gestao.rest.utils.RestUtils;
import com.concafras.gestao.security.UsuarioAutenticado;
import com.concafras.gestao.service.BaseInstitutoService;
import com.concafras.gestao.service.EntidadeService;
import com.concafras.gestao.service.FacilitadorService;
import com.concafras.gestao.service.MetaService;
import com.concafras.gestao.service.MetasInstitutoService;
import com.concafras.gestao.service.PessoaService;
import com.concafras.gestao.service.PlanoMetasService;
import com.concafras.gestao.service.RodizioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/gestao/planodemetas")
public class PlanoMetasController {
  
  private static final Logger logger = LoggerFactory.getLogger(PlanoMetasController.class);
  
  @Autowired
  private Environment environment;

  @Autowired
  private MetasInstitutoService metaInstitutoService;

  @Autowired
  private BaseInstitutoService baseInstitutoService;

  @Autowired
  private FacilitadorService facilitadorService;

  @Autowired
  private PessoaService pessoaService;

  @Autowired
  private RodizioService rodizioService;

  @Autowired
  private EntidadeService entidadeService;

  @Autowired
  private PlanoMetasService planoMetasService;

  @Autowired
  private MetaService metaService;
  
  @Autowired
  private PlanoMetasHelper planoMetasHelper;
  
  @Autowired
  private MetasHelper metasHelper;

  @Autowired
  @Qualifier("planoMetasFormValidator")
  private Validator planoMetasValidator;

  @InitBinder
  private void initBinder(WebDataBinder binder) {
    binder.setValidator(planoMetasValidator);
    bindDate(binder);
  }

  /**
   * Bind date.
   * 
   * @param binder
   *          the binder
   */
  protected void bindDate(WebDataBinder binder) {
    PropertyEditor pes = new PropertyEditorSupport() {
      @Override
      public void setAsText(String value) {

        try {
          Date parsedDate = new SimpleDateFormat("dd/MM/yyyy").parse(value);
          setValue(parsedDate);
        } catch (ParseException e) {
          try {
            Date parsedDate = new SimpleDateFormat("MM/yyyy").parse(value);
            setValue(parsedDate);
          } catch (ParseException e1) {
            setValue(null);
          }
        }
      }

      @Override
      public String getAsText() {
        try {
          String parsedDate = new SimpleDateFormat("dd/MM/yyyy")
              .format((Date) getValue());
          return parsedDate;
        } catch (Exception e) {
          try {
            String parsedDate = new SimpleDateFormat("MM/yyyy")
                .format((Date) getValue());
            return parsedDate;
          } catch (Exception e1) {
            return "";
          }
        }
      }
    };

    binder.registerCustomEditor(Date.class, pes);
  }

  @RequestMapping(value = "/secretaria/contratacaoListaCiclo")
  public ModelAndView contratacaoListaCiclos(Map<String, Object> map) {
    PlanoMetasForm form = new PlanoMetasForm();
    ModelAndView model = new ModelAndView("metas.secretaria.selecaoCiclo",
        "planoMetasForm", form);
    model.addObject("rodizioList", rodizioService.findAll());
    model.addObject("action", "contratacaoSelecaoCiclo");
    return model;
  }

  @RequestMapping(value = "/secretaria/contratacaoSelecaoCiclo/{ciclo}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String statusEntidadePreenchimentoCiclo(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo)
      throws IOException {
    List<EntidadeOptionForm> retorno = null;
    retorno = planoMetasService.getEntidadesStatus(ciclo);
    return new RestUtils<EntidadeOptionForm>().createDatatableResponse(retorno);

  }

  @RequestMapping(value = "/secretaria/contratacaoSelecaoCiclo/{ciclo}/{entidade}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String statusEntidadePreenchimentoCiclo(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo,
      @PathVariable("entidade") Integer entidade) throws IOException {
    List<InstitutoOptionForm> retorno = null;
    retorno = planoMetasService.getInstitutosStatus(ciclo, entidade);
    return new RestUtils<InstitutoOptionForm>()
        .createDatatableResponse(retorno);

  }

  @RequestMapping(value = "/listaContratadoGeralData/{ciclo}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String listaContratadoGeralData(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo)
      throws IOException {

    List<ResumoMetaEntidade> retorno = null;

    retorno = planoMetasService.getListaContratadoGeralData(ciclo, null, null,
        null, null);

    DatatableResponse<ResumoMetaEntidade> datatableResponse = new DatatableResponse<ResumoMetaEntidade>();
    datatableResponse.setiTotalDisplayRecords(retorno.size());
    datatableResponse.setiTotalRecords(retorno.size());
    datatableResponse.setAaData(retorno);

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    String json2 = gson.toJson(datatableResponse);

    return json2;

  }

  @RequestMapping(value = "/listaContratadoGeralData/{ciclo}/{status}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String listaContratadoGeralData(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo,
      @PathVariable("status") String status) throws IOException {
    List<ResumoMetaEntidade> retorno = null;
    retorno = planoMetasService.getListaContratadoGeralData(ciclo, null, null,
        null, status);
    return new RestUtils<ResumoMetaEntidade>().createDatatableResponse(retorno);

  }

  @RequestMapping(value = "/listaContratadoRegiaoData/{ciclo}/{regiao}/{status}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String listaContratadoRegiaoData(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo,
      @PathVariable("regiao") Integer regiao,
      @PathVariable("status") String status) throws IOException {
    List<ResumoMetaEntidade> retorno = null;
    
    Integer instituto = null;
    BaseInstituto baseInstituto = (BaseInstituto) request.getSession().getAttribute("INSTITUTO_CONTROLE");
    if(baseInstituto != null){
      instituto = baseInstituto.getId();
    }
    
    retorno = planoMetasService.getListaContratadoGeralData(ciclo, regiao, null,
        instituto, status);
    return new RestUtils<ResumoMetaEntidade>().createDatatableResponse(retorno);

  }

  @RequestMapping(value = "/listaContratadoInstitutoData/{ciclo}/{instituto}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String listaContratadoInstitutoData(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo,
      @PathVariable("instituto") Integer instituto) throws IOException {
    return listaContratadoInstitutoData(request, ciclo, instituto, null);
  }

  @RequestMapping(value = "/listaContratadoInstitutoData/{ciclo}/{instituto}/{status}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String listaContratadoInstitutoData(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo,
      @PathVariable("instituto") Integer instituto,
      @PathVariable("status") String status) throws IOException {
    List<ResumoMetaEntidade> retorno = null;
    retorno = planoMetasService.getListaContratadoGeralData(ciclo, null, null,
        instituto, status);
    return new RestUtils<ResumoMetaEntidade>().createDatatableResponse(retorno);

  }

  @RequestMapping(value = "/listaContratadoEntidadeData/{ciclo}/{entidade}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String listaContratadoEntidadeData(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo,
      @PathVariable("entidade") Integer entidade) throws IOException {
    return listaContratadoEntidadeData(request, ciclo, entidade, null);
  }

  @RequestMapping(value = "/listaContratadoEntidadeData/{ciclo}/{entidade}/{status}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String listaContratadoEntidadeData(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo,
      @PathVariable("entidade") Integer entidade,
      @PathVariable("status") String status) throws IOException {
    List<ResumoMetaEntidade> retorno = null;
    retorno = planoMetasService.getListaContratadoGeralData(ciclo, null,
        entidade, null, status);
    return new RestUtils<ResumoMetaEntidade>().createDatatableResponse(retorno);
  }

  @RequestMapping(value = "/listaContratadoEntidadeInstitutoData/{ciclo}/{entidade}/{instituto}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String listaContratadoEntidadeInstitutoData(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo,
      @PathVariable("entidade") Integer entidade,
      @PathVariable("instituto") Integer instituto) throws IOException {
    return listaContratadoEntidadeInstitutoData(request, ciclo, entidade,
        instituto, null);
  }

  @RequestMapping(value = "/listaContratadoEntidadeInstitutoData/{ciclo}/{entidade}/{instituto}/{status}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  public @ResponseBody String listaContratadoEntidadeInstitutoData(
      HttpServletRequest request, @PathVariable("ciclo") Integer ciclo,
      @PathVariable("entidade") Integer entidade,
      @PathVariable("instituto") Integer instituto,
      @PathVariable("status") String status) throws IOException {
    List<ResumoMetaEntidade> retorno = null;
    retorno = planoMetasService.getListaContratadoGeralData(ciclo, null,
        entidade, instituto, status);
    return new RestUtils<ResumoMetaEntidade>().createDatatableResponse(retorno);
  }

  @RequestMapping("/")
  public String seleciona(Map<String, Object> map, HttpServletRequest request,
      Authentication authentication) {
    request.getSession().setAttribute("profiles", environment.getActiveProfiles());
    return seleciona(map, null, request, authentication);
  }

  @RequestMapping("/ciclo/{ciclo}")
  public String seleciona(Map<String, Object> map,
      @PathVariable("ciclo") Integer ciclo, HttpServletRequest request,
      Authentication authentication) {

    PlanoMetasForm planoMetasForm = new PlanoMetasForm();

    if ("ROLE_METAS_FACILITADOR".equals(request.getSession().getAttribute("ROLE_CONTROLE"))) {
      UserDetails user = (UserDetails) authentication.getPrincipal();

      if (user instanceof UsuarioAutenticado) {
        UsuarioAutenticado usuario = (UsuarioAutenticado) user;

        Pessoa facilitadorSearch = pessoaService.getPessoa(usuario.getPessoa().getId());

        Rodizio rodizio = rodizioService.findByAtivoTrue();
        
        List<Facilitador> facilitador = facilitadorService
            .getFacilitador(facilitadorSearch, rodizio);

        Facilitador novoFac = null;

        for (Facilitador fac : facilitador) {
          novoFac = fac;
        }
        
        if(novoFac != null){
          planoMetasForm.setInstituto( new InstitutoOptionForm( novoFac.getInstituto() ) );
        }
        
        planoMetasForm.setFacilitador(new PessoaOptionForm( facilitadorSearch ) );

        if (ciclo == null) {
          ciclo = rodizio.getId();
        }
      }
    }

    planoMetasForm.setFase(1);

    map.put("planoMetasForm", planoMetasForm);

    if (ciclo == null) {
      List<Rodizio> listaRodizio = rodizioService.findAll();
      Map<String, String> rodizioList = new HashMap<String, String>();
      for (Rodizio rodizio : listaRodizio) {
        rodizioList.put(String.valueOf(rodizio.getId()),
            String.valueOf(rodizio.getCiclo()));
      }
      map.put("rodizioList", rodizioList);
    } else {
      Rodizio rodizio = rodizioService.findById(ciclo);
      planoMetasForm.setRodizio( new RodizioVO( rodizio ));
      request.getSession().setAttribute("CICLO_CONTROLE", rodizio);
    }

    if ("ROLE_METAS_PRESIDENTE".equals(request.getSession().getAttribute("ROLE_CONTROLE"))) {
      List<BaseInstituto> listaInstituto = baseInstitutoService.findAllOverview();
      BaseEntidade entidade = (BaseEntidade) request.getSession().getAttribute("INSTITUICAO_CONTROLE");
      List<PreContrato> listaSelecao = new ArrayList<PreContrato>();
      for (BaseInstituto instituto : listaInstituto) {
        PlanoMetas plano = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade.getId(), instituto.getId(), ciclo);
        PreContrato preContrato = new PreContrato();
        preContrato.setInstituto(instituto);
        if (plano != null) {
          preContrato.setInicializado(true);
        } else {
          preContrato.setInicializado(false);
        }

        if (plano != null && plano.getFacilitador() != null) {
          preContrato.setFinalizadoRodizio(true);
        } else {
          preContrato.setFinalizadoRodizio(false);
        }
        listaSelecao.add(preContrato);
      }
      map.put("institutoList", listaInstituto);
      map.put("preContratoList", listaSelecao);
    }

    List<Facilitador> listaFacilitador = facilitadorService.listFacilitador();
    Map<String, String> facilitadorList = new HashMap<String, String>();
    for (Facilitador facilitador : listaFacilitador) {
      facilitadorList.put(String.valueOf(facilitador.getId()),
          facilitador.getTrabalhador().getNome());
    }
    map.put("profiles", environment.getActiveProfiles());
    Date hoje = trim( new Date());
    Date amanha = addDay( hoje, 1);
    map.put("startDate", hoje );
    map.put("endDate", amanha );
    map.put("facilitadorList", facilitadorList);
    map.put("rodizio", true);
    return "metas.selecao";
  }

  @RequestMapping("/inicio")
  public ModelAndView contratacao(HttpServletRequest request,
      @ModelAttribute("planoMetasForm") @Validated PlanoMetasForm planoMetasForm,
      BindingResult result, RedirectAttributes redirect) {

    if (result.hasErrors()) {

      ModelAndView model = new ModelAndView("metas.selecao", "planoMetasForm",
          planoMetasForm);

      List<Rodizio> listaRodizio = rodizioService.findAll();
      Map<String, String> rodizioList = new HashMap<String, String>();
      for (Rodizio rodizio : listaRodizio) {
        rodizioList.put(String.valueOf(rodizio.getId()),
            String.valueOf(rodizio.getCiclo()));
      }
      model.addObject("rodizioList", rodizioList);

      if ("ROLE_METAS_PRESIDENTE".equals(request.getSession().getAttribute("ROLE_CONTROLE"))) {
        List<BaseInstituto> listaInstituto = baseInstitutoService.findAllOverview();
        BaseEntidade entidade = (BaseEntidade) request.getSession().getAttribute("INSTITUICAO_CONTROLE");
        List<PreContrato> listaSelecao = new ArrayList<PreContrato>();
        for (BaseInstituto instituto : listaInstituto) {
          PlanoMetas plano = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade.getId(), instituto.getId(), planoMetasForm.getRodizio().getId());
          PreContrato preContrato = new PreContrato();
          preContrato.setInstituto(instituto);
          if (plano != null) {
            preContrato.setInicializado(true);
          } else {
            preContrato.setInicializado(false);
          }

          if (plano != null && plano.getFacilitador() != null) {
            preContrato.setFinalizadoRodizio(true);
          } else {
            preContrato.setFinalizadoRodizio(false);
          }
          listaSelecao.add(preContrato);
        }
        model.addObject("institutoList", listaInstituto);
        model.addObject("preContratoList", listaSelecao);
      }

      List<Facilitador> listaFacilitador = facilitadorService.listFacilitador();
      Map<String, String> facilitadorList = new HashMap<String, String>();
      for (Facilitador facilitador : listaFacilitador) {
        facilitadorList.put(String.valueOf(facilitador.getId()),
            facilitador.getTrabalhador().getNome());
      }
      model.addObject("facilitadorList", facilitadorList);

      planoMetasForm.setFase(1);

      return model;
    }

    Rodizio rodizio = restauraRodizio(planoMetasForm);
    BaseInstituto instituto = restauraInstituto(planoMetasForm);
    BaseEntidade entidade = restauraEntidade(planoMetasForm);
    Pessoa facilitador = restauraFacilitador(planoMetasForm);
    EventoMeta evento = planoMetasForm.getEvento();

    int fase = 2;
    
    boolean processoSecretaria = false;
    boolean processoPresidente = false;
    
    // Se processo iniciado por secretaria
    if (request.getSession().getAttribute("ROLE_CONTROLE").equals("ROLE_METAS_SECRETARIA")) {
      processoSecretaria = true;
      fase = 4;
      entidade = entidadeService.findByIdOverview(entidade.getId());
    }
    if (request.getSession().getAttribute("ROLE_CONTROLE").equals("ROLE_METAS_PRESIDENTE")) {
      processoPresidente = true;
      if (request.getSession().getAttribute("INSTITUICAO_CONTROLE") != null) {
        BaseEntidade base = (BaseEntidade) request.getSession().getAttribute("INSTITUICAO_CONTROLE");
        entidade = entidadeService.findByIdOverview(base.getId());
      }
    }
    
    PlanoMetas planoMetasAtual = null;
    
    if (rodizio != null && instituto != null && entidade != null) {
      planoMetasAtual = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade.getId(), instituto.getId(), rodizio.getId());
      if (planoMetasAtual != null) {
        planoMetasForm = planoMetasHelper.mapPlanoMetasToPlanoMetasForm(planoMetasAtual);
        planoMetasHelper.mapPlanoMetasAnotacoesToPlanoMetasFormAnotacoes(planoMetasAtual, planoMetasForm);
        if (facilitador != null) {
          planoMetasForm.setFacilitador( new PessoaOptionForm( facilitador ) );
        }
        planoMetasForm.setEvento(evento);
      }
    }  
    
    // Se presidente
    if (processoPresidente || processoSecretaria){
      if (entidade != null) {
        
        List<Dirigente> listaDirigente = entidade.getDirigentes();

        if (listaDirigente != null) {
          for (Dirigente dirigente : listaDirigente) {
            if (dirigente.getInstituto().getId()
                .equals(planoMetasForm.getInstituto().getId())) {
            		if(dirigente.getTrabalhador() != null) {
            			planoMetasForm.setCoordenador(  new PessoaOptionForm(  dirigente.getTrabalhador() ) );
            		}
            }
          }
        }
        
        planoMetasForm.setEntidade( new EntidadeOptionForm( entidade ) );
        planoMetasForm.setTipoContratante(TipoContratante.PRESIDENTE);
        if(entidade.getPresidente() != null && entidade.getPresidente().getPessoa() != null) { 
        		planoMetasForm.setPresidente(  new PessoaOptionForm(  entidade.getPresidente().getPessoa() ) );
        		planoMetasForm.setContratante(  new PessoaOptionForm(  entidade.getPresidente().getPessoa() ) );
        }
        
        redirect.addFlashAttribute("planoMetasForm", planoMetasForm);

        return listAtividades(planoMetasForm, result);

      }
    } 

    planoMetasForm.setFase(fase);
    
    ModelAndView model = new ModelAndView("metas.contratacao", "planoMetasForm", planoMetasForm);

    model.addObject("rodizio", Boolean.TRUE);

    return model;
  }

  

  @RequestMapping("/atividades")
  public ModelAndView listAtividades(
      @ModelAttribute("planoMetasForm") @Validated PlanoMetasForm planoMetasForm,
      BindingResult result) {

    // Carrega a base do plano com os id enviados pela pagina anterior
    Rodizio rodizio = restauraRodizio(planoMetasForm);
    BaseInstituto instituto = restauraInstituto(planoMetasForm);
    BaseEntidade entidade = restauraEntidade(planoMetasForm);
    Pessoa facilitador = restauraFacilitador(planoMetasForm);
    restauraPresidente(planoMetasForm);
    restauraCoordenador(planoMetasForm);
    restauraContratante(planoMetasForm);
    EventoMeta evento = planoMetasForm.getEvento();

    // Se teve erros na validação retorna a página anterior
    if (result.hasErrors()) {
      ModelAndView model = new ModelAndView("metas.contratacao", "planoMetasForm", planoMetasForm);
      planoMetasForm.setFase(2);
      model.addObject("erros", true);
      return model;
    }

    // Verificar existencia do plano para esse ciclo
    PlanoMetas plano = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade.getId(),instituto.getId(), rodizio.getId());

    //Nao existe plano de metas?
    if (plano == null) {
      plano = new PlanoMetas();
    }
    
    //Atualizo dados das tela anterior (Contratante/Coordenador/Presidente/Evento)
    planoMetasHelper.mapPlanoMetasFormToPlanoMetas(planoMetasForm, plano);
    
    plano = planoMetasService.saveOrUpdate(plano, null);
    
    planoMetasForm = planoMetasHelper.mapPlanoMetasToPlanoMetasForm(plano);
    planoMetasHelper.mapPlanoMetasAnotacoesToPlanoMetasFormAnotacoes(plano, planoMetasForm);

    planoMetasForm.setEvento(evento);

    List<MetaForm> dependencias = planoMetasHelper.loadMetaForm(
    		planoMetasForm.getInstituto(),
    		planoMetasForm.getFacilitador(),
    		planoMetasForm.getContratante(), 
    		planoMetasForm.getEvento(), 
    		planoMetasForm.getEntidade(), 
    		planoMetasForm.getRodizio(),
			true, true);
    
    planoMetasForm.setDependencias(dependencias);
    
    Long prioridades = metaService.countListMetaEntidadePrioridade(instituto.getId());
    
    planoMetasForm.setPrioridades(prioridades);

    planoMetasForm.setFase(3);

    ModelAndView model = new ModelAndView("metas.contratacao2", "planoMetasForm", planoMetasForm);

    List<SituacaoMeta> situacaoList = new ArrayList<SituacaoMeta>(Arrays.asList(SituacaoMeta.values()));
    model.addObject("situacaoList", situacaoList);
    model.addObject("rodizio", Boolean.TRUE);

    return model;
  }

  

  @RequestMapping("/add")
  public ModelAndView saveContratacao(HttpServletRequest request,
      @ModelAttribute("planoMetasForm") @Validated PlanoMetasForm planoMetasForm,
      BindingResult result) {
    
    String jsonInString = null;

    try {
      ObjectMapper mapper = new ObjectMapper();
      
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      
      jsonInString = mapper.writeValueAsString(planoMetasForm);
            
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    Rodizio rodizio = restauraRodizio(planoMetasForm);
    BaseInstituto instituto = restauraInstituto(planoMetasForm);
    BaseEntidade entidade = restauraEntidade(planoMetasForm);
    restauraFacilitador(planoMetasForm);

    if (result.hasErrors()) {
    		planoMetasHelper.restauraDependenciaCiclo(planoMetasForm.getDependencias());
      ModelAndView model = new ModelAndView("metas.contratacao2", "planoMetasForm", planoMetasForm);
      planoMetasForm.setFase(3);
      model.addObject("erros", true);
      return model;
    }
    
    try {
      String file = "metas_r_" + 2017 + "_e_" + ( entidade != null ? entidade.getId() : "null" ) + "_i_" + (instituto != null ? instituto.getId() : "null") + ".json";
      FileUtils.writeStringToFile(new File("/data/metas/" +  file), jsonInString);
    } catch (Exception e) {
      e.printStackTrace();
    }

    
    ModelAndView model = new ModelAndView("metas.conclusao");

    if (planoMetasForm.getTipoContratante() == null) {
      planoMetasForm.setTipoContratante(TipoContratante.PRESIDENTE);
    }

    // Verificar existencia do plano para esse ciclo
    PlanoMetas plano = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade.getId(), instituto.getId(), rodizio.getId());

    //Nao existe plano de metas? Se entrar aqui tá errado... 
    if (plano == null) {
      plano = new PlanoMetas();
    }
    
    try {
      
      //Atualizo dados das tela anterior (Contratante/Coordenador/Presidente/Evento)
    	planoMetasHelper.mapPlanoMetasFormToPlanoMetas(planoMetasForm, plano);
      
      List<MetaEntidade> metas = planoMetasHelper.preparaMetas(planoMetasForm, plano);
      
      planoMetasService.saveOrUpdate(plano, metas);

    } catch (Exception e) {
      System.out.println("ERRO");
      System.out.println("======================================");
      System.out.println(planoMetasForm);
      System.out.println("======================================");      
    }
    
    return model;
  }

  @RequestMapping("/list")
  public @ResponseBody List<PlanoMetasForm> listMetasEntidade(
      @RequestParam int entidade, @RequestParam int instituto) {

    List<PlanoMetas> lista = null;
    if (entidade > 0 && instituto > 0) {
      lista = planoMetasService.findByEntidadeIdAndInstitutoId(entidade,
          instituto);
    } else if (entidade > 0) {
      lista = planoMetasService.findByInstitutoId(instituto);
    } else if (instituto > 0) {
      lista = planoMetasService.findByEntidadeId(entidade);
    } else {
      lista = planoMetasService.findAll();
    }

    List<PlanoMetasForm> listanova = new ArrayList<PlanoMetasForm>();

    for (PlanoMetas planoMetas : lista) {
      PlanoMetasForm planoMetasForm = new PlanoMetasForm();
      List<MetaInstituto> atividades = metaInstitutoService
          .listMetaInstitutoByInstituto(planoMetas.getInstituto().getId(), true);

      List<MetaEntidade> listaMetas = metaService.findByEntidadeIdAndInstitutoId(planoMetas.getEntidade().getId(), planoMetas.getInstituto().getId());
      planoMetas.setMetas(listaMetas);
      
      PessoaOptionForm facilitador = null;
      PessoaOptionForm contratante = null;
      
      if(planoMetas.getFacilitador() != null) {
    	  	facilitador = new PessoaOptionForm(planoMetas.getFacilitador());
      }
    	  
      if(planoMetas.getContratante() != null) {
    	  	contratante = new PessoaOptionForm(planoMetas.getContratante());
      }
      

      if (planoMetas.getMetas().size() > 0) {
        List<MetaForm> metas = metasHelper.mapMetaEntidadeToMetaForm(
             atividades, 
             facilitador,
             contratante, 
             EventoMeta.RODIZIO, 
             new EntidadeOptionForm( planoMetas.getEntidade() ) , 
             new RodizioVO( planoMetas.getRodizio() ),
             true,
             true);
        		planoMetasForm.setDependencias(metas);
      }
      listanova.add(planoMetasForm);
    }

    return listanova;
  }

  private Rodizio restauraRodizio(PlanoMetasForm planoMetasForm) {
    // Verifica Rodizio
    if (planoMetasForm.getRodizio() != null
        && planoMetasForm.getRodizio().getId() != null) {
      Rodizio rodizio = rodizioService.findById(planoMetasForm.getRodizio().getId());
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
      planoMetasForm.setInstituto( new InstitutoOptionForm( instituto ) );
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
      planoMetasForm.setEntidade( new EntidadeOptionForm( entidade ) );
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
      PessoaOptionForm pessoaFacilitador = new PessoaOptionForm(facilitador);
      planoMetasForm.setFacilitador(pessoaFacilitador);
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
      PessoaOptionForm pessoaPresidente = new PessoaOptionForm(presidente);
      planoMetasForm.setPresidente(pessoaPresidente);
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
      PessoaOptionForm pessoaCoordenador = new PessoaOptionForm(coordenador);
      planoMetasForm.setCoordenador(pessoaCoordenador);
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
      PessoaOptionForm pessoaContratante = new PessoaOptionForm(contratante);
      planoMetasForm.setContratante(pessoaContratante);
      return contratante;
    } else {
      planoMetasForm.setContratante(null);
      return null;
    }
  }
  
  public static Date trim(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.HOUR_OF_DAY, 0);

    return calendar.getTime();
  }
  
  public static Date addDay(Date date, int days) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, days);
    return calendar.getTime();
  }

}
