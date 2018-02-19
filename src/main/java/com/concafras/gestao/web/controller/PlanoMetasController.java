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
import com.concafras.gestao.form.AnotacaoVO;
import com.concafras.gestao.form.EntidadeOptionForm;
import com.concafras.gestao.form.HistoricoMetaEntidadeVO;
import com.concafras.gestao.form.InstitutoOptionForm;
import com.concafras.gestao.form.MetaForm;
import com.concafras.gestao.form.PessoaOptionForm;
import com.concafras.gestao.form.PlanoMetasForm;
import com.concafras.gestao.form.RodizioVO;
import com.concafras.gestao.helper.MetasHelper;
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
import com.concafras.gestao.rest.model.DatatableResponse;
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

        Pessoa facilitadorSearch = usuario.getPessoa();

        Rodizio rodizio = rodizioService.findByAtivoTrue();
        
        List<Facilitador> facilitador = facilitadorService
            .getFacilitador(facilitadorSearch, rodizio);

        Facilitador novoFac = null;

        for (Facilitador fac : facilitador) {
          novoFac = fac;
        }
        
        if(novoFac != null){
          planoMetasForm.setInstituto(novoFac.getInstituto());
        }
        
        planoMetasForm.setFacilitador(facilitadorSearch);

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
          facilitador.getTrabalhador().getNomeCompleto());
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
            facilitador.getTrabalhador().getNomeCompleto());
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
        planoMetasForm = mapPlanoMetasToPlanoMetasForm(planoMetasAtual);
        if (facilitador != null) {
          planoMetasForm.setFacilitador(facilitador);
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
              planoMetasForm.setCoordenador(dirigente.getTrabalhador());
            }
          }
        }
        
        planoMetasForm.setEntidade(entidade);
        planoMetasForm.setTipoContratante(TipoContratante.PRESIDENTE);
        planoMetasForm.setPresidente(entidade.getPresidente().getPessoa());
        planoMetasForm.setContratante(entidade.getPresidente().getPessoa());

        redirect.addFlashAttribute("planoMetasForm", planoMetasForm);

        return listAtividades(planoMetasForm, result);

      }
    } 

    planoMetasForm.setFase(fase);
    
    ModelAndView model = new ModelAndView("metas.contratacao", "planoMetasForm", planoMetasForm);

    model.addObject("rodizio", Boolean.TRUE);

    return model;
  }

  private PlanoMetasForm mapPlanoMetasToPlanoMetasForm(PlanoMetas planoMetasAtual) {
    
    PlanoMetasForm planoMetasForm = new PlanoMetasForm();
    
    planoMetasForm.setId(planoMetasAtual.getId());
    planoMetasForm.setValidado(planoMetasAtual.isValidado());
    planoMetasForm.setFinalizado(planoMetasAtual.isFinalizado());
    planoMetasForm.setTipoContratante(planoMetasAtual.getTipoContratante());

    planoMetasForm.setPresidente( planoMetasAtual.getPresidente() );
    planoMetasForm.setNomePresidente(planoMetasAtual.getNomePresidente());
    planoMetasForm.setEmailPresidente(planoMetasAtual.getEmailPresidente());
    planoMetasForm.setTelefonePresidente(planoMetasAtual.getTelefonePresidente());

    planoMetasForm.setCoordenador(planoMetasAtual.getCoordenador());
    planoMetasForm.setNomeCoordenador(planoMetasAtual.getNomeCoordenador());
    planoMetasForm.setEmailCoordenador(planoMetasAtual.getEmailCoordenador());
    planoMetasForm.setTelefoneCoordenador(planoMetasAtual.getTelefoneCoordenador());

    planoMetasForm.setContratante(planoMetasAtual.getContratante());
    planoMetasForm.setNomeContratante(planoMetasAtual.getNomeContratante());
    planoMetasForm.setEmailContratante(planoMetasAtual.getEmailContratante());
    planoMetasForm.setTelefoneContratante(planoMetasAtual.getTelefoneContratante());

    if (planoMetasAtual.getTipoContratante() == TipoContratante.OUTRO) {
      planoMetasForm.setOutro( new PessoaOptionForm( planoMetasAtual.getContratante() ) );
    }
    planoMetasForm.setFacilitador(planoMetasAtual.getFacilitador());
    
    planoMetasForm.setRodizio( new RodizioVO( planoMetasAtual.getRodizio() ) );
    planoMetasForm.setInstituto(planoMetasAtual.getInstituto());
    planoMetasForm.setEntidade(planoMetasAtual.getEntidade());
    
    mapAnotacoesPlanoMetasToPlanoMetasForm(planoMetasAtual, planoMetasForm);
    
    return planoMetasForm;
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
    mapPlanoMetasFormToPlanoMetas(planoMetasForm, plano);
    
    plano = planoMetasService.saveOrUpdate(plano, null);
    
    planoMetasForm = mapPlanoMetasToPlanoMetasForm(plano);

    planoMetasForm.setEvento(evento);

    mapMetasFromPlanoMetasToPlanoMetasForm(planoMetasForm, plano);
    
    Long prioridades = metaService.countListMetaEntidadePrioridade(instituto.getId());
    
    planoMetasForm.setPrioridades(prioridades);

    planoMetasForm.setFase(3);

    ModelAndView model = new ModelAndView("metas.contratacao2", "planoMetasForm", planoMetasForm);

    List<SituacaoMeta> situacaoList = new ArrayList<SituacaoMeta>(Arrays.asList(SituacaoMeta.values()));
    model.addObject("situacaoList", situacaoList);
    model.addObject("rodizio", Boolean.TRUE);

    return model;
  }

  private void preparaAnotacoes(PlanoMetasForm contratoForm, PlanoMetas contratoEntity) {
    if (contratoForm.getAnotacoes() != null) {
      Anotacao novaNota = null;
      for (AnotacaoVO anot : contratoForm.getAnotacoes()) {
        if (anot.getId() == null && (anot.getTexto() == null || anot.getTexto().trim().equals(""))) {
          continue;
        }

        if (contratoEntity.getAnotacoes() == null) {
          contratoEntity.setAnotacoes(new ArrayList<Anotacao>());
        }

        if (contratoEntity.getAnotacoes().contains(anot)) {
          novaNota = contratoEntity.getAnotacoes().get(contratoEntity.getAnotacoes().indexOf(anot));
          if (!novaNota.getTexto().equals(anot.getTexto())) {
            novaNota.setData(new Date());
            if (contratoForm.getEvento() == EventoMeta.RODIZIO) {
              if (contratoEntity.getFacilitador() != null && contratoEntity.getFacilitador().getId() != null)
                novaNota.setResponsavel(contratoEntity.getFacilitador());
            } else {
              if (contratoEntity.getContratante() != null && contratoEntity.getContratante().getId() != null)
                novaNota.setResponsavel(contratoEntity.getContratante());
            }
            novaNota.setTexto(anot.getTexto());
          }
        } else {
          anot.setData(new Date());
          if (contratoForm.getEvento() == EventoMeta.RODIZIO) {
            if (contratoEntity.getFacilitador() != null && contratoEntity.getFacilitador().getId() != null)
              anot.setResponsavel( new PessoaOptionForm( contratoEntity.getFacilitador() ) );
          } else {
            if (contratoEntity.getContratante() != null && contratoEntity.getContratante().getId() != null)
              anot.setResponsavel(new PessoaOptionForm( contratoEntity.getContratante() ) );
          }
          Anotacao nova = new Anotacao();
          nova.setData(anot.getData());
          nova.setId(anot.getId());
          nova.setNivel(anot.getNivel());
          nova.setSinalizador(anot.getSinalizador());
          nova.setTexto(anot.getTexto());
          if(anot.getResponsavel() != null) { 
        	  	Pessoa pessoa = pessoaService.getPessoa(anot.getResponsavel().getId());
        	  	nova.setResponsavel(pessoa);
          }
          contratoEntity.getAnotacoes().add(nova);
        }
      }
    }
  }

  private void mapAnotacoesPlanoMetasToPlanoMetasForm(PlanoMetas planoMetasAtual, PlanoMetasForm planoMetasForm) {
    planoMetasForm.setAnotacoes(new ArrayList<AnotacaoVO>());
    if (planoMetasAtual.getAnotacoes() != null) {
      for (Anotacao anot : planoMetasAtual.getAnotacoes()) {
    	  	AnotacaoVO nova = new AnotacaoVO(anot);
        planoMetasForm.getAnotacoes().add(nova);
      }
    }
  }

  private void mapMetasFromPlanoMetasToPlanoMetasForm(PlanoMetasForm contratoForm, PlanoMetas contratoEntity) {
    
    List<MetaForm> metasForm = null;

    List<MetaInstituto> metasIntituto = metaInstitutoService.listMetaInstitutoByInstitutoResumo(contratoForm.getInstituto().getId());

    List<MetaEntidade> metasEntidade = metaService.findByEntidadeIdAndInstitutoId( contratoForm.getEntidade().getId(), contratoForm.getInstituto().getId());

    // Primeiro Rodizio
    if (metasEntidade == null || metasEntidade.size() == 0) {
      metasForm = new MetasHelper(metaService).mapMetaInstitutoToMetaForm(
          metasIntituto, 
          contratoForm.getFacilitador(),
          contratoForm.getContratante(), 
          contratoForm.getEvento(),
          contratoForm.getRodizio());
    } else if (metasEntidade.size() > 0) {
      metasForm = new MetasHelper(metaService).mapMetaEntidadeToMetaForm(
          metasIntituto, 
          contratoForm.getFacilitador(),
          contratoForm.getContratante(), 
          contratoForm.getEvento(), 
          contratoForm.getEntidade(), 
          contratoForm.getRodizio());
    } else {
      metasForm = new ArrayList<MetaForm>();
    }
    
    contratoForm.setDependencias(metasForm);
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
      restauraDependenciaCiclo(planoMetasForm.getDependencias());
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
      mapPlanoMetasFormToPlanoMetas(planoMetasForm, plano);
      
      List<MetaEntidade> metas = preparaMetas(planoMetasForm, plano);
      
      planoMetasService.saveOrUpdate(plano, metas);

    } catch (Exception e) {
      System.out.println("ERRO");
      System.out.println("======================================");
      System.out.println(planoMetasForm);
      System.out.println("======================================");      
    }
    
    return model;
  }

  private void restauraDependenciaCiclo(List<MetaForm> lista) {
    for (MetaForm metaForm : lista) {
      if (metaForm.getCiclo() != null && metaForm.getCiclo().getId() != null) {
        Rodizio rodVO = rodizioService.findById(metaForm.getCiclo().getId());
        metaForm.setCiclo(new RodizioVO(rodVO));
      }
      HistoricoMetaEntidadeVO hmeAnterior = metaForm.getSituacaoAnterior();
      if (hmeAnterior.getCiclo() != null
          && hmeAnterior.getCiclo().getId() != null) {
        Rodizio rodVO = rodizioService.findById(hmeAnterior.getCiclo().getId());
        hmeAnterior.setCiclo(new RodizioVO(rodVO));
      }
      HistoricoMetaEntidadeVO hmeAtual = metaForm.getSituacaoAtual();
      if (hmeAtual.getCiclo() != null && hmeAtual.getCiclo().getId() != null) {
        Rodizio rodVO = rodizioService.findById(hmeAtual.getCiclo().getId());
        hmeAtual.setCiclo(new RodizioVO(rodVO));
      }
      HistoricoMetaEntidadeVO hmeDesejada = metaForm.getSituacaoDesejada();
      if (hmeDesejada.getCiclo() != null
          && hmeDesejada.getCiclo().getId() != null) {
        Rodizio rodVO = rodizioService.findById(hmeDesejada.getCiclo().getId());
        hmeDesejada.setCiclo(new RodizioVO(rodVO));
      }
      if (metaForm.getDependencias() != null) {
        restauraDependenciaCiclo(metaForm.getDependencias());
      }
    }

  }

  private List<MetaEntidade> preparaMetas(PlanoMetasForm planoMetasForm, PlanoMetas plano) {
    
    List<MetaEntidade> metasAux = metaService.findByEntidadeIdAndInstitutoId(plano.getEntidade().getId(), plano.getInstituto().getId());
    
    plano.setMetas(metasAux);
    
    List<MetaEntidade> metas = new ArrayList<MetaEntidade>();

    processaMetas(planoMetasForm.getRodizio(), planoMetasForm.getEvento(), planoMetasForm.getDependencias(), metas, plano, null);

    return metas;
  }
  
  private void mapPlanoMetasFormToPlanoMetas(PlanoMetasForm planoMetasForm, PlanoMetas planoMetas) {
    if(planoMetas.getId() == null){
      planoMetas.setId(planoMetasForm.getId());
    }
    
    // Popula dados do plano de metas atual com as informações do planoMetasForm
    planoMetas.setTipoContratante(planoMetasForm.getTipoContratante());
    
    planoMetas.setNomePresidente(planoMetasForm.getNomePresidente());
    planoMetas.setEmailPresidente(planoMetasForm.getEmailPresidente());
    planoMetas.setTelefonePresidente(planoMetasForm.getTelefonePresidente());
    
    planoMetas.setNomeCoordenador(planoMetasForm.getNomeCoordenador());
    planoMetas.setEmailCoordenador(planoMetasForm.getEmailCoordenador());
    planoMetas.setTelefoneCoordenador(planoMetasForm.getTelefoneCoordenador());

    planoMetas.setNomeContratante(planoMetasForm.getNomeContratante());
    planoMetas.setEmailContratante(planoMetasForm.getEmailContratante());
    planoMetas.setTelefoneContratante(planoMetasForm.getTelefoneContratante());
    
    planoMetas.setValidado(planoMetasForm.isValidado());
    planoMetas.setFinalizado(planoMetasForm.isFinalizado());
    
    if(planoMetasForm.getRodizio() != null){
      Rodizio rodizio = rodizioService.findById(planoMetasForm.getRodizio().getId());
      planoMetas.setRodizio(rodizio);
    }
    if(planoMetasForm.getInstituto() != null){
      BaseInstituto instituto = baseInstitutoService.findById(planoMetasForm.getInstituto().getId());
      planoMetas.setInstituto(instituto);
    }
    if(planoMetasForm.getEntidade() != null){
      BaseEntidade entidade = entidadeService.findById(planoMetasForm.getEntidade().getId());
      planoMetas.setEntidade(entidade);
    }
    
    if (planoMetasForm.getPresidente() != null && planoMetasForm.getPresidente().getId() != null) {
      Pessoa presidente = pessoaService.getPessoa(planoMetasForm.getPresidente().getId());
      planoMetas.setPresidente(presidente);
      planoMetas.setNomePresidente(planoMetasForm.getPresidente().getNomeCompleto());
    }
    
    if (planoMetasForm.getCoordenador() != null && planoMetasForm.getCoordenador().getId() != null) {
      Pessoa coordenador = pessoaService.getPessoa(planoMetasForm.getCoordenador().getId());
      planoMetas.setCoordenador(coordenador);
      planoMetas.setNomeCoordenador(planoMetasForm.getCoordenador().getNomeCompleto());
    }

    if (planoMetasForm.getTipoContratante() == TipoContratante.PRESIDENTE) {
      planoMetas.setContratante(planoMetas.getPresidente());
    }
    if (planoMetasForm.getTipoContratante() == TipoContratante.COORDENADOR && planoMetasForm.getCoordenador() != null) {
      planoMetas.setContratante(planoMetas.getCoordenador());
    }
    if (planoMetasForm.getTipoContratante() == TipoContratante.OUTRO && planoMetasForm.getOutro() != null && planoMetasForm.getOutro().getId() != null) {
      Pessoa outro = pessoaService.getPessoa(planoMetasForm.getOutro().getId());
      planoMetas.setContratante(outro);
    }
    
    if (planoMetasForm.getContratante() != null && planoMetasForm.getContratante().getId() != null) {
        Pessoa contratante = pessoaService.getPessoa(planoMetasForm.getContratante().getId());
        planoMetas.setContratante(contratante);
    }

    if (planoMetasForm.getFacilitador() != null && planoMetasForm.getFacilitador().getId() != null) {
      Pessoa facilitador = pessoaService.getPessoa(planoMetasForm.getFacilitador().getId());
      planoMetas.setFacilitador(facilitador);
    }
    
    preparaAnotacoes(planoMetasForm, planoMetas);
    
  }

  private void processaMetas(RodizioVO ciclo, EventoMeta evento, List<MetaForm> metasForm, List<MetaEntidade> metas, PlanoMetas plano, MetaEntidade pai) {
    metasForm.removeAll(Collections.singleton(null));

    for (MetaForm metaForm : metasForm) {
      List<HistoricoMetaEntidade> historicoAtual = null;
      List<HistoricoMetaEntidade> historicoAnterior = null;

      MetaEntidade meta = null;
      if (metaForm.getId() != null && metaForm.getId() > 0) {
        meta =  metaService.findById( metaForm.getId() ); // new MetasHelper(metaService).searchMeta(plano.getMetas(), plano.getEntidade(), plano.getInstituto(), metaForm.getAtividade());
        historicoAtual = metaService.findByMetaEntidadeIdAndRodizioId(
            meta.getId(), plano.getRodizio().getId());
        if (plano.getRodizio().getCicloAnterior() != null) {
          historicoAnterior = metaService.findByMetaEntidadeIdAndRodizioId(
              meta.getId(), plano.getRodizio().getCicloAnterior().getId());
        }
      } else {
        meta = new MetaEntidade();
        meta.setInstituto(plano.getInstituto());
        meta.setEntidade(plano.getEntidade());
        meta.setPrimeiroRodizio(plano.getRodizio());
        MetaInstituto metaInstituto = metaInstitutoService.getMetasInstituto(
            new Long(metaForm.getAtividade().getId()).intValue());
        meta.setPai(meta);
        meta.setMeta(metaInstituto);
        meta.setDescricao(metaInstituto.getDescricao());
        meta.setTipoMeta(metaInstituto.getTipoMeta());
        meta.setViewOrder(metaInstituto.getViewOrder());
        meta.setHistorico(new ArrayList<HistoricoMetaEntidade>());
      }

      HistoricoMetaEntidadeVO situacaoAtual = metaForm.getSituacaoAtual();

      if (situacaoAtual.getTipoSituacao() == TipoSituacaoMeta.INICIAL) {
        HistoricoMetaEntidade statusInicial = null;
        if (meta.getId() != null) {
          if (historicoAtual != null && historicoAtual.size() > 0) {
            for (HistoricoMetaEntidade historicoMetas : historicoAtual) {
              if (historicoMetas
                  .getTipoSituacao() == TipoSituacaoMeta.INICIAL) {
                statusInicial = historicoMetas;
                break;
              }
            }
          }
        }
        if (statusInicial == null) {
          statusInicial = new HistoricoMetaEntidade();
          statusInicial.setRodizio(plano.getRodizio());
          statusInicial.setTipoSituacao(TipoSituacaoMeta.INICIAL);
          statusInicial.setMeta(meta);
          meta.getHistorico().add(statusInicial);
        }
        statusInicial.setDataSituacao(new Date());

        statusInicial.setSituacao(metaForm.getSituacaoAtual().getSituacao());

        if (statusInicial.getSituacao() == SituacaoMeta.NAOIMPLANTADA) {
          statusInicial.setConclusao(null);
        } else {
          if (metaForm.getSituacaoAtual().getConclusao() != null) {
            statusInicial
                .setConclusao(metaForm.getSituacaoAtual().getConclusao());
          }

          if (metaForm.getSituacaoAtual().getPrevisao() != null) {
            statusInicial
                .setPrevisao(metaForm.getSituacaoAtual().getPrevisao());
          }

          if (metaForm.getSituacaoAtual().getPrevisto() != null) {
            statusInicial
                .setPrevisto(metaForm.getSituacaoAtual().getPrevisto());
          }

          if (metaForm.getSituacaoAtual().getRealizado() != null) {
            statusInicial
                .setRealizado(metaForm.getSituacaoAtual().getRealizado());
          }
        }

        if (evento == EventoMeta.RODIZIO) {
          if (plano.getFacilitador() != null
              && plano.getFacilitador().getId() != null)
            statusInicial.setResponsavel(plano.getFacilitador());
        } else {
          if (plano.getTipoContratante() == TipoContratante.PRESIDENTE) {
            statusInicial.setResponsavel(plano.getPresidente());
          } else if (plano.getTipoContratante() == TipoContratante.COORDENADOR
              && plano.getCoordenador() != null
              && plano.getCoordenador().getId() != null) {
            statusInicial.setResponsavel(plano.getCoordenador());
          } else if (plano.getTipoContratante() == TipoContratante.OUTRO
              && plano.getContratante() != null
              && plano.getContratante().getId() != null) {
            statusInicial.setResponsavel(plano.getContratante());
          }
        }

        // Arrumar a questao de historico de metas para comtemplar as acoes
        // anteriores e atuais no primeiro dia.
        // Arrumar as demais acoes para atender.
      } else if (situacaoAtual.getTipoSituacao() == TipoSituacaoMeta.AVALIAR) {
        HistoricoMetaEntidade statusAvaliar = null;
        if (meta.getId() != null) {
          if (historicoAnterior != null && historicoAnterior.size() > 0) {
            for (HistoricoMetaEntidade historicoMetas : historicoAnterior) {
              if (historicoMetas
                  .getTipoSituacao() == TipoSituacaoMeta.AVALIAR) {
                statusAvaliar = historicoMetas;
                break;
              }
            }
          }
        }

        Rodizio cicloSituacaoAtual = new Rodizio();
        cicloSituacaoAtual.setId(metaForm.getSituacaoAtual().getCiclo().getId());

        if (statusAvaliar == null) {
          statusAvaliar = new HistoricoMetaEntidade();
          statusAvaliar.setRodizio(cicloSituacaoAtual);
          statusAvaliar.setTipoSituacao(TipoSituacaoMeta.AVALIAR);
          statusAvaliar.setMeta(meta);
          meta.getHistorico().add(statusAvaliar);
        }

        statusAvaliar.setDataSituacao(new Date());

        statusAvaliar.setSituacao(metaForm.getSituacaoAtual().getSituacao());

        statusAvaliar.setConclusao(metaForm.getSituacaoAtual().getConclusao());
        statusAvaliar.setPrevisao(metaForm.getSituacaoAtual().getPrevisao());
        statusAvaliar.setPrevisto(metaForm.getSituacaoAtual().getPrevisto());
        statusAvaliar.setRealizado(metaForm.getSituacaoAtual().getRealizado());

        if (evento == EventoMeta.RODIZIO) {
          if (plano.getFacilitador() != null
              && plano.getFacilitador().getId() != null)
            statusAvaliar.setResponsavel(plano.getFacilitador());
        } else {
          if (plano.getTipoContratante() == TipoContratante.PRESIDENTE) {
            statusAvaliar.setResponsavel(plano.getPresidente());
          } else if (plano.getTipoContratante() == TipoContratante.COORDENADOR
              && plano.getCoordenador() != null
              && plano.getCoordenador().getId() != null) {
            statusAvaliar.setResponsavel(plano.getCoordenador());
          } else if (plano.getTipoContratante() == TipoContratante.OUTRO
              && plano.getContratante() != null
              && plano.getContratante().getId() != null) {
            statusAvaliar.setResponsavel(plano.getContratante());
          }
        }

      }

      HistoricoMetaEntidadeVO situacaoDesejada = metaForm.getSituacaoDesejada();

      if (situacaoDesejada == null) {
        situacaoDesejada = new HistoricoMetaEntidadeVO();
        situacaoDesejada.setCiclo(new RodizioVO(plano.getRodizio()));
      }

      TipoSituacaoMeta acaoDefault = TipoSituacaoMeta.CONTRATAR;

      if (evento == EventoMeta.PRERODIZIO) {
        acaoDefault = TipoSituacaoMeta.PRECONTRATAR;
      } else if (evento == EventoMeta.RODIZIO) {
        acaoDefault = TipoSituacaoMeta.CONTRATAR;
      }

      // Se situacao é nula e tipo meta quantitativa e tem valor previsto entao
      // planejado
      if (situacaoDesejada.getSituacao() == null
          && TipoMeta.META_QUANTITATIVA.equals(metaForm.getTipoMeta())
          && situacaoDesejada.getPrevisto() != null) { 
        situacaoDesejada.setSituacao(SituacaoMeta.PLANEJADA);
      }

      // Situacao desejada pode ser apenas planejada/nao planejada
      if (situacaoDesejada.getSituacao() != null
          && (situacaoDesejada.getTipoSituacao() == acaoDefault
              || situacaoDesejada.getTipoSituacao() == null)) {

        HistoricoMetaEntidade statusDesejado = null;
        if (meta.getId() != null) {
          statusDesejado = new MetasHelper(metaService)
              .getUltimoHistorico(meta.getId(), plano.getRodizio().getId(), acaoDefault, true);
        }

        if (statusDesejado == null) {
          statusDesejado = new HistoricoMetaEntidade();
          statusDesejado.setRodizio(plano.getRodizio());
          statusDesejado.setTipoSituacao(acaoDefault);
          statusDesejado.setMeta(meta);
          meta.getHistorico().add(statusDesejado);
        }

        statusDesejado.setDataSituacao(new Date());
        statusDesejado.setSituacao(situacaoDesejada.getSituacao());
        statusDesejado.setConclusao(situacaoDesejada.getConclusao());
        statusDesejado.setPrevisao(situacaoDesejada.getPrevisao());
        statusDesejado.setRealizado(situacaoDesejada.getRealizado());
        statusDesejado.setPrevisto(situacaoDesejada.getPrevisto());

        if (evento == EventoMeta.RODIZIO) {
          if (plano.getFacilitador() != null
              && plano.getFacilitador().getId() != null)
            statusDesejado.setResponsavel(plano.getFacilitador());
        } else {
          if (plano.getContratante() != null
              && plano.getContratante().getId() != null)
            statusDesejado.setResponsavel(plano.getContratante());
        }

      }

      if (metaForm.getObservacoes() != null) {
        Anotacao novaNota = null;
        for (AnotacaoVO anot : metaForm.getObservacoes()) {
          if (anot.getId() == null && (anot.getTexto() == null || anot.getTexto().trim().equals(""))) {
            continue;
          }

          if (meta.getAnotacoes() == null) {
            meta.setAnotacoes(new ArrayList<MetaEntidadeAnotacao>());
          }

          List<MetaEntidadeAnotacao> listaAnotacoes = meta.getAnotacoes();

          Anotacao anotAux = new Anotacao();
          anotAux.setId(anot.getId());
          anotAux.setNivel(anot.getNivel());

          Rodizio cicloAux = null; 
          Rodizio cicloLoaded = rodizioService.findById(ciclo.getId());
          if(anot.getId() != null && anot.getCiclo() != null && anot.getCiclo().getId() != null){
            cicloAux = new Rodizio();
            cicloAux.setId(anot.getCiclo().getId());
          } else if(anot.getId() == null ) {
            cicloAux = cicloLoaded;
          } else {
            continue;
          }

          MetaEntidadeAnotacao metaAnot = new MetaEntidadeAnotacao(meta, cicloAux, anotAux);

          if (listaAnotacoes.contains(metaAnot)) {
            int indice = listaAnotacoes.indexOf(metaAnot);

            if (indice != -1) {
              novaNota = listaAnotacoes.get(indice).getAnotacao();
            }

            if (novaNota != null
                && !novaNota.getTexto().equals(anot.getTexto())) {
              novaNota.setData(new Date());
              novaNota.setTexto(anot.getTexto());
            }

          } else {
            anotAux.setData(new Date());
            if (anot.getResponsavel().getId() != null) {
              if (evento == EventoMeta.RODIZIO) {
                if (plano.getFacilitador() != null
                    && plano.getFacilitador().getId() != null)
                  anotAux.setResponsavel(plano.getFacilitador());
              } else {
                if (plano.getTipoContratante() == TipoContratante.PRESIDENTE) {
                  anotAux.setResponsavel(plano.getPresidente());
                } else if (plano
                    .getTipoContratante() == TipoContratante.COORDENADOR
                    && plano.getCoordenador() != null
                    && plano.getCoordenador().getId() != null) {
                  anotAux.setResponsavel(plano.getCoordenador());
                } else if (plano.getTipoContratante() == TipoContratante.OUTRO
                    && plano.getContratante() != null
                    && plano.getContratante().getId() != null) {
                  anotAux.setResponsavel(plano.getContratante());
                }
              }
            }
            anotAux.setSinalizador(anot.getSinalizador());
            anotAux.setTexto(anot.getTexto());
            listaAnotacoes.add(metaAnot);
          }
        }
      }

      if (meta.getAnotacoes() != null) {
        for (MetaEntidadeAnotacao anotNova : meta.getAnotacoes()) {
          Anotacao anotNovaCorrigi = anotNova.getAnotacao();
          if (anotNovaCorrigi.getResponsavel() != null
              && anotNovaCorrigi.getResponsavel().getId() == null)
            anotNovaCorrigi.setResponsavel(null);
        }
      }

      metas.add(meta);

      List<MetaForm> subAtividades = metaForm.getDependencias();

      if (subAtividades != null)
        processaMetas(ciclo, evento, subAtividades, metas, plano, meta);

    }

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

      if (planoMetas.getMetas().size() > 0) {
        List<MetaForm> metas = new MetasHelper(metaService).mapMetaEntidadeToMetaForm(
             atividades, 
             planoMetas.getFacilitador(),
             planoMetas.getContratante(), 
             EventoMeta.RODIZIO, 
             planoMetas.getEntidade(), 
             new RodizioVO( planoMetas.getRodizio() ) );
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
