package com.concafras.gestao.web.controller;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.concafras.gestao.enums.EventoMeta;
import com.concafras.gestao.enums.NivelAnotacao;
import com.concafras.gestao.enums.Sinalizador;
import com.concafras.gestao.enums.SituacaoMeta;
import com.concafras.gestao.enums.TipoContratante;
import com.concafras.gestao.enums.TipoMeta;
import com.concafras.gestao.enums.TipoSituacaoMeta;
import com.concafras.gestao.form.AnotacaoVO;
import com.concafras.gestao.form.EntidadeOptionForm;
import com.concafras.gestao.form.HistoricoMetaEntidadeVO;
import com.concafras.gestao.form.InstitutoOptionForm;
import com.concafras.gestao.form.MetaForm;
import com.concafras.gestao.form.PlanoMetasForm;
import com.concafras.gestao.form.RodizioVO;
import com.concafras.gestao.helper.MetasHelper;
import com.concafras.gestao.model.Anotacao;
import com.concafras.gestao.model.BaseEntidade;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.Dirigente;
import com.concafras.gestao.model.Entidade;
import com.concafras.gestao.model.Facilitador;
import com.concafras.gestao.model.HistoricoMetaEntidade;
import com.concafras.gestao.model.MetaEntidade;
import com.concafras.gestao.model.MetaEntidadeAnotacao;
import com.concafras.gestao.model.MetaInstituto;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.PlanoMetas;
import com.concafras.gestao.model.Rodizio;
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
import com.concafras.gestao.util.FlyingSaucerPDFCreationListener;
import com.concafras.gestao.util.MyPDFPageEvents;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/gestao/planodemetas")
public class PlanoMetasController {
	
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
     *            the binder
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
                    String parsedDate = new SimpleDateFormat("dd/MM/yyyy").format((Date)getValue());
                    return parsedDate;
                } catch (Exception e) {
                  try {
                    String parsedDate = new SimpleDateFormat("MM/yyyy").format((Date)getValue());
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
      ModelAndView model = new ModelAndView("metas.secretaria.selecaoCiclo", "planoMetasForm", form);
      model.addObject("rodizioList", rodizioService.findAll());
      model.addObject("action", "contratacaoSelecaoCiclo");
      return model;
    }
    
    @RequestMapping(value = "/secretaria/contratacaoSelecaoCiclo/{ciclo}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String statusEntidadePreenchimentoCiclo(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo)
        throws IOException {
      List<EntidadeOptionForm> retorno = null;
      retorno = planoMetasService.getEntidadesStatus(ciclo);
      return new RestUtils<EntidadeOptionForm>().createDatatableResponse(retorno);
    
    }
    
    @RequestMapping(value = "/secretaria/contratacaoSelecaoCiclo/{ciclo}/{entidade}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String statusEntidadePreenchimentoCiclo(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo, @PathVariable("entidade") Integer entidade)
        throws IOException {
      List<InstitutoOptionForm> retorno = null;
      retorno = planoMetasService.getInstitutosStatus(ciclo, entidade);
      return new RestUtils<InstitutoOptionForm>().createDatatableResponse(retorno);
      
    }

    
    @RequestMapping(value = "/listaContratadoGeralData/{ciclo}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String listaContratadoGeralData(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo)
        throws IOException {
      
      List<ResumoMetaEntidade> retorno = null;
      
      retorno = planoMetasService.getListaContratadoGeralData(ciclo, null, null, null, null);
      
      DatatableResponse<ResumoMetaEntidade> datatableResponse = new DatatableResponse<ResumoMetaEntidade>();
      datatableResponse.setiTotalDisplayRecords(retorno.size());
      datatableResponse.setiTotalRecords(retorno.size());
      datatableResponse.setAaData(retorno);
      
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      
      String json2 = gson.toJson(datatableResponse);
      
      return json2;
      
    }
    
    @RequestMapping(value = "/listaContratadoGeralData/{ciclo}/{status}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String listaContratadoGeralData(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo, @PathVariable("status") String status)
        throws IOException {
      List<ResumoMetaEntidade> retorno = null;
      retorno = planoMetasService.getListaContratadoGeralData(ciclo, null, null, null, status);
      return new RestUtils<ResumoMetaEntidade>().createDatatableResponse(retorno);

    }
    
    @RequestMapping(value = "/listaContratadoRegiaoData/{ciclo}/{regiao}/{status}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String listaContratadoRegiaoData(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo, @PathVariable("regiao") Integer regiao, @PathVariable("status") String status)
        throws IOException {
      List<ResumoMetaEntidade> retorno = null;
      retorno = planoMetasService.getListaContratadoGeralData(ciclo, regiao, null, null, status);
      return new RestUtils<ResumoMetaEntidade>().createDatatableResponse(retorno);
      
    }
    
    @RequestMapping(value = "/listaContratadoInstitutoData/{ciclo}/{instituto}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String listaContratadoInstitutoData(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo, @PathVariable("instituto") Integer instituto)
        throws IOException {
      return listaContratadoInstitutoData(request, ciclo, instituto, null);
    }
      
    @RequestMapping(value = "/listaContratadoInstitutoData/{ciclo}/{instituto}/{status}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String listaContratadoInstitutoData(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo, @PathVariable("instituto") Integer instituto, @PathVariable("status") String status)
        throws IOException {
      List<ResumoMetaEntidade> retorno = null;
      retorno = planoMetasService.getListaContratadoGeralData(ciclo, null, null, instituto, status);
      return new RestUtils<ResumoMetaEntidade>().createDatatableResponse(retorno);
      
    }
    
    @RequestMapping(value = "/listaContratadoEntidadeData/{ciclo}/{entidade}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String listaContratadoEntidadeData(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo, @PathVariable("entidade") Integer entidade)
        throws IOException {
      return listaContratadoEntidadeData(request, ciclo, entidade, null);
    }
      
    @RequestMapping(value = "/listaContratadoEntidadeData/{ciclo}/{entidade}/{status}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String listaContratadoEntidadeData(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo, @PathVariable("entidade") Integer entidade, @PathVariable("status") String status)
        throws IOException {
      List<ResumoMetaEntidade> retorno = null;
      retorno = planoMetasService.getListaContratadoGeralData(ciclo, null, entidade, null, status);
      return new RestUtils<ResumoMetaEntidade>().createDatatableResponse(retorno);
    }
    
    @RequestMapping(value = "/listaContratadoEntidadeInstitutoData/{ciclo}/{entidade}/{instituto}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String listaContratadoEntidadeInstitutoData(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo, @PathVariable("entidade") Integer entidade, @PathVariable("instituto") Integer instituto)
        throws IOException {
      return listaContratadoEntidadeInstitutoData(request, ciclo, entidade, instituto, null);
    }
    
    @RequestMapping(value = "/listaContratadoEntidadeInstitutoData/{ciclo}/{entidade}/{instituto}/{status}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String listaContratadoEntidadeInstitutoData(HttpServletRequest request, @PathVariable("ciclo") Integer ciclo, @PathVariable("entidade") Integer entidade, @PathVariable("instituto") Integer instituto, @PathVariable("status") String status)
        throws IOException {
      List<ResumoMetaEntidade> retorno = null;
      retorno = planoMetasService.getListaContratadoGeralData(ciclo, null, entidade, instituto, status);
      return new RestUtils<ResumoMetaEntidade>().createDatatableResponse(retorno);
    }
    
  @RequestMapping("/")
  public String seleciona(Map<String, Object> map, HttpServletRequest request,
      Authentication authentication) {
    return seleciona(map, null, request, authentication);
  }

  @RequestMapping("/ciclo/{ciclo}")
  public String seleciona(Map<String, Object> map,
      @PathVariable("ciclo") Integer ciclo, 
      HttpServletRequest request,
      Authentication authentication) {

    PlanoMetasForm planoMetasForm = new PlanoMetasForm();

    if ("ROLE_METAS_FACILITADOR".equals(request.getSession().getAttribute(
        "ROLE_CONTROLE"))) {
      UserDetails user = (UserDetails) authentication.getPrincipal();

      if (user instanceof UsuarioAutenticado) {
        UsuarioAutenticado usuario = (UsuarioAutenticado) user;

        Pessoa facilitadorSearch = usuario.getPessoa();

        List<Facilitador> facilitador = facilitadorService
            .getFacilitador(facilitadorSearch);

        Facilitador novoFac = null;

        for (Facilitador fac : facilitador) {
          novoFac = fac;
        }

        planoMetasForm.setInstituto(novoFac.getInstituto());
        planoMetasForm.setFacilitador(facilitadorSearch);
        
        if(ciclo == null){
          Rodizio rodizio = rodizioService.findByAtivoTrue();
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
      planoMetasForm.setRodizio(rodizio);
      request.getSession().setAttribute("CICLO_CONTROLE", rodizio);
    }

    if ("ROLE_METAS_PRESIDENTE".equals(request.getSession().getAttribute(
        "ROLE_CONTROLE"))){
      List<BaseInstituto> listaInstituto = baseInstitutoService.findAllOverview();
      BaseEntidade entidade = (BaseEntidade) request.getSession().getAttribute("INSTITUICAO_CONTROLE");
      for (BaseInstituto instituto : listaInstituto) {
        PlanoMetas plano = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade.getId(), instituto.getId(), ciclo);
        if(plano != null && plano.getFacilitador() != null){
          instituto.setRodizio(true);
        } else {
          instituto.setRodizio(false);
        }
      }
      map.put("institutoList", listaInstituto);
    }

    List<Facilitador> listaFacilitador = facilitadorService.listFacilitador();
    Map<String, String> facilitadorList = new HashMap<String, String>();
    for (Facilitador facilitador : listaFacilitador) {
      facilitadorList.put(String.valueOf(facilitador.getId()), facilitador
          .getTrabalhador().getNomeCompleto());
    }
    map.put("facilitadorList", facilitadorList);
    map.put("rodizio", true);
    return "metas.selecao";
  }

	@RequestMapping("/inicio")
	public ModelAndView contratacao(HttpServletRequest request,@ModelAttribute("planoMetasForm") @Validated PlanoMetasForm planoMetasForm, BindingResult result, RedirectAttributes redirect) {

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

      if ("ROLE_METAS_PRESIDENTE".equals(request.getSession().getAttribute(
          "ROLE_CONTROLE"))){
        List<BaseInstituto> listaInstituto = baseInstitutoService.findAllOverview();
        BaseEntidade entidade = (BaseEntidade) request.getSession().getAttribute("INSTITUICAO_CONTROLE");
        for (BaseInstituto instituto : listaInstituto) {
          PlanoMetas plano = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade.getId(), instituto.getId(), planoMetasForm.getRodizio().getId());
          if(plano != null && plano.isFinalizado()){
            instituto.setRodizio(true);
          } else {
            instituto.setRodizio(false);
          }
        }
        model.addObject("institutoList", listaInstituto);
      }

      List<Facilitador> listaFacilitador = facilitadorService.listFacilitador();
      Map<String, String> facilitadorList = new HashMap<String, String>();
      for (Facilitador facilitador : listaFacilitador) {
        facilitadorList.put(String.valueOf(facilitador.getId()), facilitador
            .getTrabalhador().getNomeCompleto());
      }
      model.addObject("facilitadorList", facilitadorList);

      planoMetasForm.setFase(1);

      return model;
    }
    
    ModelAndView model = new ModelAndView("metas.contratacao",
        "planoMetasForm", planoMetasForm);

    Rodizio rodizio = restauraRodizio(planoMetasForm);
    BaseInstituto instituto = restauraInstituto(planoMetasForm);
    BaseEntidade entidade = restauraEntidade(planoMetasForm);
    restauraFacilitador(planoMetasForm);

    planoMetasForm.setFase(2);
    
    // Se processo iniciado por secretaria
    if(rodizio != null 
        && instituto != null
        && entidade != null){
    
      PlanoMetas planoMetasAtual =  planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(
          entidade.getId(), 
          instituto.getId(), 
          rodizio.getId());
      
      if(planoMetasAtual != null){
        planoMetasForm.setId(planoMetasAtual.getId());
      
        planoMetasForm.setTipoContratante(planoMetasAtual.getTipoContratante());
      
        if( planoMetasAtual.getPresidente() != null){
          planoMetasForm.setPresidente( pessoaService.getPessoa( planoMetasAtual.getPresidente().getId() ));
        }
        
        if(planoMetasAtual.getNomePresidente() != null){
          planoMetasForm.setNomePresidente(planoMetasAtual.getNomePresidente());
        }
        
        if(planoMetasAtual.getEmailPresidente() != null){
          planoMetasForm.setEmailPresidente(planoMetasAtual.getEmailPresidente());
        }
        
        if(planoMetasAtual.getTelefonePresidente() != null){
          planoMetasForm.setTelefonePresidente(planoMetasAtual.getTelefonePresidente());
        }
        
        if(planoMetasAtual.getCoordenador() != null){
          planoMetasForm.setCoordenador( pessoaService.getPessoa( planoMetasAtual.getCoordenador().getId() ) );
          planoMetasForm.setEmailCoordenador( planoMetasForm.getCoordenador().getPrimeiroEmail() );
          planoMetasForm.setTelefoneCoordenador( planoMetasForm.getCoordenador().getPrimeiroTelefone() );
        } else {
          if(planoMetasAtual.getNomeCoordenador() != null){
            planoMetasForm.setNomeCoordenador( planoMetasAtual.getNomeCoordenador() );
          }
          if(planoMetasAtual.getEmailCoordenador() != null){
            planoMetasForm.setEmailCoordenador(planoMetasAtual.getEmailCoordenador());
          }
          if(planoMetasAtual.getTelefoneCoordenador() != null){
            planoMetasForm.setTelefoneCoordenador(planoMetasAtual.getTelefoneCoordenador());
          }          
        }
        
        if(planoMetasAtual.getContratante() != null){
          planoMetasForm.setContratante( pessoaService.getPessoa( planoMetasAtual.getContratante().getId() ) );
          planoMetasForm.setEmailContratante(planoMetasAtual.getContratante().getPrimeiroEmail() );
          planoMetasForm.setTelefoneContratante(planoMetasAtual.getContratante().getPrimeiroTelefone() );
        } else {
          if(planoMetasAtual.getNomeContratante() != null){
            planoMetasForm.setNomeContratante( planoMetasAtual.getNomeContratante() );
          }          
          if(planoMetasAtual.getEmailContratante() != null){
            planoMetasForm.setEmailContratante(planoMetasAtual.getEmailContratante());
          }
          if(planoMetasAtual.getTelefoneContratante() != null){
            planoMetasForm.setTelefoneContratante(planoMetasAtual.getTelefoneContratante());
          }
          
        }
        
        if(planoMetasAtual.getTipoContratante() == TipoContratante.OUTRO){
          planoMetasForm.setOutro(planoMetasAtual.getContratante());
        }
        
        if(planoMetasAtual.getFacilitador() != null){
          planoMetasForm.setFacilitador(planoMetasAtual.getFacilitador());
        }
        
      }  else {
        if(planoMetasForm.getEntidade() != null){
          BaseEntidade nova = entidadeService.findByIdOverview(planoMetasForm.getEntidade().getId());
          if(nova != null){
            if(nova.getPresidente() != null ){
              planoMetasForm.setPresidente(nova.getPresidente().getPessoa());
            }
            
            List<Dirigente> listaDirigente = nova.getDirigentes();

            if (listaDirigente != null) {
              for (Dirigente dirigente : listaDirigente) {
                if (dirigente.getInstituto().getId()
                    .equals(planoMetasForm.getInstituto().getId())) {
                  planoMetasForm.setCoordenador(dirigente.getTrabalhador());
                }
              }
            }
          }
          
        }
      }
      planoMetasForm.setFase(4);
    }

    // Se presidente
    if (request.getSession().getAttribute("INSTITUICAO_CONTROLE") != null) {
      BaseEntidade base = (BaseEntidade) request.getSession().getAttribute(
          "INSTITUICAO_CONTROLE");
      entidade = entidadeService.findByIdOverview(base.getId());
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
        
        planoMetasForm.setEntidade(base);

        planoMetasForm.setTipoContratante(TipoContratante.PRESIDENTE);
        planoMetasForm.setPresidente(base.getPresidente().getPessoa());
        planoMetasForm.setContratante(base.getPresidente().getPessoa());

        redirect.addFlashAttribute("planoMetasForm", planoMetasForm);

        return listAtividades(planoMetasForm, result);

      }

    }
   
    model.addObject("rodizio", Boolean.TRUE);

    return model;
  }


  @RequestMapping("/atividades")
	public ModelAndView listAtividades(@ModelAttribute("planoMetasForm") @Validated PlanoMetasForm planoMetasForm, BindingResult result) {
		
	  // Carrega a base do plano com os id enviados pela pagina anterior
    Rodizio rodizio = restauraRodizio(planoMetasForm);
    BaseInstituto instituto = restauraInstituto(planoMetasForm);
    BaseEntidade entidade = restauraEntidade(planoMetasForm);
    restauraFacilitador(planoMetasForm);
    restauraPresidente(planoMetasForm);
    restauraCoordenador(planoMetasForm);
    restauraContratante(planoMetasForm);
    
    // Se teve erros na validação retorna a página anterior
		if (result.hasErrors()) {
			ModelAndView model = new ModelAndView("metas.contratacao", "planoMetasForm", planoMetasForm);
			planoMetasForm.setFase(2);
			model.addObject("erros", true);
			return model;
		}
		
		// Verificar existencia do plano para esse ciclo
		PlanoMetas planoMetasAtual =  planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade.getId(), instituto.getId(), rodizio.getId());
		
		if(planoMetasAtual != null){
		  planoMetasForm.setId(planoMetasAtual.getId());
		} else {
		  planoMetasAtual = new PlanoMetas();
		  planoMetasAtual.setRodizio(rodizio);
		  planoMetasAtual.setInstituto(instituto);
		  planoMetasAtual.setEntidade(entidade);
		}
		
		// Popula dados do plano de metas atual com as informações do planoMetasForm
		planoMetasAtual.setEvento(planoMetasForm.getEvento());		
		
		if(planoMetasForm.getTipoContratante() != null){
      planoMetasAtual.setTipoContratante(planoMetasForm.getTipoContratante());
    }
    if( planoMetasForm.getPresidente() != null){
      planoMetasAtual.setPresidente( planoMetasForm.getPresidente() );
    }
    if(planoMetasForm.getNomePresidente() != null){
      planoMetasAtual.setNomePresidente(planoMetasForm.getNomePresidente());
    }
    if(planoMetasForm.getEmailPresidente() != null){
      planoMetasAtual.setEmailPresidente(planoMetasForm.getEmailPresidente());
    }
    if(planoMetasForm.getTelefonePresidente() != null){
      planoMetasAtual.setTelefonePresidente(planoMetasForm.getTelefonePresidente());
    }      
    if(planoMetasForm.getCoordenador() != null){
      if(planoMetasForm.getCoordenador().getId() != null){
        Pessoa coordenador = pessoaService.getPessoa(planoMetasForm.getCoordenador().getId());
        planoMetasAtual.setCoordenador( coordenador );
      }
      planoMetasAtual.setNomeCoordenador( planoMetasForm.getCoordenador().getNomeCompleto() );
    }
    if(planoMetasForm.getEmailCoordenador() != null){
      planoMetasAtual.setEmailCoordenador(planoMetasForm.getEmailCoordenador());
    }
    if(planoMetasForm.getTelefoneCoordenador() != null){
      planoMetasAtual.setTelefoneCoordenador(planoMetasForm.getTelefoneCoordenador());
    }
    
    if(planoMetasForm.getTipoContratante() == TipoContratante.PRESIDENTE){
      planoMetasAtual.setContratante(planoMetasAtual.getPresidente());
    }
    if(planoMetasForm.getTipoContratante() == TipoContratante.COORDENADOR && planoMetasForm.getCoordenador() != null ){
      planoMetasAtual.setContratante(planoMetasAtual.getCoordenador());
    }
    if(planoMetasForm.getTipoContratante() == TipoContratante.OUTRO && planoMetasForm.getOutro() != null && planoMetasForm.getOutro().getId() != null){
      Pessoa outro = pessoaService.getPessoa(planoMetasForm.getOutro().getId());
      planoMetasAtual.setContratante(outro);
    }
    if(planoMetasForm.getContratante() != null ){
      if(planoMetasForm.getContratante().getId() != null){
        Pessoa contratante = pessoaService.getPessoa(planoMetasForm.getContratante().getId());
        planoMetasAtual.setContratante( contratante );
      }
      planoMetasAtual.setContratante( planoMetasForm.getContratante() );
    }
    if(planoMetasForm.getNomeContratante() != null){
      planoMetasAtual.setNomeContratante(planoMetasForm.getNomeContratante());
    }
    if(planoMetasForm.getEmailContratante() != null){
      planoMetasAtual.setEmailContratante(planoMetasForm.getEmailContratante());
    }
    if(planoMetasForm.getTelefoneContratante() != null){
      planoMetasAtual.setTelefoneContratante(planoMetasForm.getTelefoneContratante());
    }
    
    if(planoMetasForm.getFacilitador() != null ){
      planoMetasAtual.setFacilitador(planoMetasForm.getFacilitador());
    } else if (planoMetasAtual.getFacilitador() != null){
      planoMetasForm.setFacilitador(planoMetasAtual.getFacilitador());
    }
    
    if(planoMetasAtual.getId() != null){
      planoMetasService.update(planoMetasAtual);
    }
		
			//Prepara 
		  planoMetasForm = preparaPlanoMetas(planoMetasForm, planoMetasAtual);

		  // Salva esboço atual
    	PlanoMetas plano = getPlano(planoMetasForm);
    	
    	if(plano.getId() == null || plano.getId() == 0){
    	  
        List<MetaEntidade> metas = plano.getListaMetas();
        for (MetaEntidade metaEntidade : metas) {
          if(metaEntidade.getId() == null || metaEntidade.getId() == 0){
            metaService.save(metaEntidade);
          } else {
            metaService.update(metaEntidade);
          }
        }
        planoMetasService.save(plano);

        planoMetasForm.setId(plano.getId());
        
        planoMetasAtual =  planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade.getId(), instituto.getId(), rodizio.getId());
        List<MetaEntidade> listaMetas2 =  metaService.findByEntidadeIdAndInstitutoId(entidade.getId(), instituto.getId());
        planoMetasAtual.setListaMetas(listaMetas2);
        
        planoMetasForm = preparaPlanoMetas(planoMetasForm, planoMetasAtual);
        
      }
    	
    	planoMetasForm.setFase(3);
      
    	
		ModelAndView model = new ModelAndView("metas.contratacao2", "planoMetasForm", planoMetasForm);
		
		List<SituacaoMeta> situacaoList = new ArrayList<SituacaoMeta>( Arrays.asList(SituacaoMeta.values() ));
    model.addObject("situacaoList", situacaoList);
    model.addObject("rodizio", Boolean.TRUE);
    	
		return model;
	}
	
  private PlanoMetasForm preparaPlanoMetas(PlanoMetasForm planoMetasForm, PlanoMetas planoMetasAtual) {
    List<MetaForm> metasForm = null;

    List<MetaInstituto> metasIntituto = metaInstitutoService.listMetaInstitutoByInstitutoResumo(planoMetasForm.getInstituto().getId());

    List<MetaEntidade> listaMetas =  metaService.findByEntidadeIdAndInstitutoId(planoMetasForm.getEntidade().getId(), planoMetasForm.getInstituto().getId());
    
    if(planoMetasAtual != null){
      planoMetasAtual.setListaMetas(listaMetas);
      planoMetasAtual.setEvento(planoMetasForm.getEvento());
    }
    
    //Primeiro Rodizio
    if (planoMetasAtual == null || listaMetas == null || listaMetas.size() == 0) {
      metasForm = processaMetaInstitutoToMetaForm(metasIntituto, planoMetasForm.getFacilitador(), planoMetasForm.getContratante(), planoMetasForm.getEvento(), planoMetasForm.getRodizio());
    } else if (listaMetas.size() > 0) {
      metasForm = processaMetaToMetaForm(metasIntituto, planoMetasAtual);
    } else {
      metasForm = new ArrayList<MetaForm>();
    }

    if (planoMetasAtual != null) {
      planoMetasForm.setAnotacoes(new ArrayList<Anotacao>());
      if (planoMetasAtual.getListaAnotacoes() != null) {
        for (Anotacao anot : planoMetasAtual.getListaAnotacoes()) {
          planoMetasForm.getAnotacoes().add(anot);
        }
      }

      planoMetasForm.setFinalizado(planoMetasAtual.isFinalizado());
      planoMetasForm.setValidado(planoMetasAtual.isValidado());
    }

    planoMetasForm.setDependencias(metasForm);

    return planoMetasForm;
  }
	
	private List<MetaForm> processaMetaInstitutoToMetaForm(List<MetaInstituto> atividades, Pessoa facilitador, Pessoa contratante, EventoMeta evento, Rodizio ciclo) {
    	
    	List<MetaForm> metas = new ArrayList<MetaForm>();
    	
    	atividades.removeAll(Collections.singleton(null));
    	
    	for (MetaInstituto atividade : atividades) {
    		MetaForm meta = new MetaForm();
			meta.setAtividade(atividade);
			meta.setSituacaoDesejada(new HistoricoMetaEntidadeVO());
			meta.setObservacoes(new ArrayList<AnotacaoVO>());
			
			if(evento == EventoMeta.PRERODIZIO){
				Anotacao anot = new Anotacao();
				anot.setNivel(NivelAnotacao.META_PRERODIZIO);
				if(contratante != null && contratante.getId() != null)
				  anot.setResponsavel(contratante);
				anot.setSinalizador(Sinalizador.VERDE);
				anot.setData(new Date());
				meta.getObservacoes().add(new AnotacaoVO(anot, ciclo));
			}
			
			if(evento == EventoMeta.RODIZIO){
				Anotacao anot = new Anotacao();
				anot.setNivel(NivelAnotacao.META_RODIZIO);
				if(facilitador != null && facilitador.getId() != null)
				  anot.setResponsavel(facilitador);
				anot.setSinalizador(Sinalizador.VERDE);
				anot.setData(new Date());
				meta.getObservacoes().add(new AnotacaoVO(anot, ciclo));
			}
			
			//Primeiro rodizio - Sem meta anterior
			meta.setSituacaoAnterior(null);
			
			//Primeiro rodizio - Situacao Atual - Inicial
      meta.setSituacaoAtual(new HistoricoMetaEntidadeVO());
      meta.getSituacaoAtual().setCiclo(new RodizioVO(ciclo));
      meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.INICIAL);
			
			List<MetaInstituto> subAtividades = atividade.getItens();
			if(subAtividades != null){
				List<MetaForm> metas1 = processaMetaInstitutoToMetaForm(subAtividades, facilitador, contratante, evento, ciclo);
				
				if(metas1.size() > 0){
					meta.setDependencias(metas1);
				}
			}
			
			metas.add(meta);
		}
    	
    	return metas;
	}
	
	
	private MetaForm processaMetasAnteriores(MetaForm meta, MetaInstituto metaInstituto, List<HistoricoMetaEntidade> historicoAnterior, PlanoMetas planometas) {
		if(planometas != null){
			List<MetaEntidade> metas = planometas.getListaMetas();
			MetaEntidade metaEntidade = searchMeta(metas, planometas.getEntidade(), planometas.getInstituto(), metaInstituto);
			
			if(metaEntidade != null){
			  // Carregar avaliacao pre-salva
			  HistoricoMetaEntidade situacaoAtualSalva = new MetasHelper().getHistoricoAvaliar(historicoAnterior);
        if(situacaoAtualSalva != null)
          meta.setSituacaoAtual(new HistoricoMetaEntidadeVO(situacaoAtualSalva));

			  //pegar ultimo historico diferente de avaliar
				HistoricoMetaEntidade historico = new MetasHelper().getUltimoHistorico(historicoAnterior, TipoSituacaoMeta.CONTRATAR);
				if(historico == null){
				  historico = new MetasHelper().getUltimoHistorico(historicoAnterior, TipoSituacaoMeta.PRECONTRATAR);
				}
				
				//HistoricoMetaEntidade historico = new MetasHelper().getHistoricoPreAvaliacao(historicoAnterior);
				if(historico != null && historico.getSituacao() != SituacaoMeta.NAOPLANEJADA){
				  HistoricoMetaEntidadeVO situacaoAnterior = new HistoricoMetaEntidadeVO(historico);
				  meta.setSituacaoAnterior(situacaoAnterior);
				  if(situacaoAnterior.getSituacao() == SituacaoMeta.PLANEJADA) {
				    if(situacaoAtualSalva == null){
		          HistoricoMetaEntidade ultimoHistorico = new MetasHelper().getUltimoHistorico(historicoAnterior);
		          if(ultimoHistorico != null 
		               && ultimoHistorico.getTipoSituacao() != TipoSituacaoMeta.INICIAL
		               && ultimoHistorico.getTipoSituacao() != TipoSituacaoMeta.PRECONTRATAR
		               && ultimoHistorico.getTipoSituacao() != TipoSituacaoMeta.CONTRATAR
		               ){ 
		            HistoricoMetaEntidadeVO situacaoAtual = new HistoricoMetaEntidadeVO(ultimoHistorico);
		            situacaoAtual.setId(null);
		            meta.setSituacaoAtual(situacaoAtual);
		            meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.AVALIAR);
		            return meta;
		          } else if (ultimoHistorico != null) {
		              HistoricoMetaEntidadeVO situacaoAtual = new HistoricoMetaEntidadeVO(ultimoHistorico);
		              situacaoAtual.setId(null);
	                meta.setSituacaoAtual(situacaoAtual);
	                meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.AVALIAR);
	                return meta;
		          }
				    }
				  }
				}
				
				if(historico == null || historico.getSituacao() == SituacaoMeta.NAOPLANEJADA){
				  // Pega Meta Inicial
				  historico = new MetasHelper().getUltimoHistorico(historicoAnterior, TipoSituacaoMeta.INICIAL);
				  if(historico != null){
				    HistoricoMetaEntidadeVO situacaoAnteriorNovo = new HistoricoMetaEntidadeVO(historico);
				    meta.setSituacaoAnterior(situacaoAnteriorNovo);
				    if(situacaoAtualSalva == null){
              meta.setSituacaoAtual(new HistoricoMetaEntidadeVO());
              meta.getSituacaoAtual().setCiclo(new RodizioVO( planometas.getRodizio().getCicloAnterior() ));
              meta.getSituacaoAtual().setSituacao( situacaoAnteriorNovo.getSituacao() );
              meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.AVALIAR);
            } 
		        return meta;
				  } else {
	          meta.setSituacaoAnterior(null);
	          meta.setSituacaoAtual(new HistoricoMetaEntidadeVO());
	          meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.INICIAL);
	          return meta;
	        }
				} 
				
			} else {
	      meta.setSituacaoAnterior(null);
	      meta.setSituacaoAtual(new HistoricoMetaEntidadeVO());
	      meta.getSituacaoAtual().setTipoSituacao(TipoSituacaoMeta.INICIAL);
	    }
		} 
		
		return meta;                                                               
	}

  private List<MetaForm> processaMetaToMetaForm(List<MetaInstituto> metasInstituto, PlanoMetas planoMetasAtual) {
		
		List<MetaEntidade> metas = planoMetasAtual.getListaMetas();
		
		List<MetaForm> metasForm = new ArrayList<MetaForm>();
		
		metasInstituto.removeAll(Collections.singleton(null));
		
		for (MetaInstituto metaInstituto : metasInstituto) {
			MetaForm metaForm = new MetaForm();
			
			MetaEntidade metaEntidade = searchMeta(metas, planoMetasAtual.getEntidade(), planoMetasAtual.getInstituto(), metaInstituto);
			
			List<HistoricoMetaEntidade> historicoAtual = metaService.findByMetaEntidadeIdAndRodizioId(metaEntidade.getId(), planoMetasAtual.getRodizio().getId());
			List<HistoricoMetaEntidade> historicoAnterior = null;
			if(planoMetasAtual.getRodizio().getCicloAnterior() != null){
			  historicoAnterior = metaService.findByMetaEntidadeIdAndRodizioId(metaEntidade.getId(), planoMetasAtual.getRodizio().getCicloAnterior().getId());
			} else {
			  historicoAnterior = new ArrayList<HistoricoMetaEntidade>();
			}
			metaForm = processaMetasAnteriores(metaForm, metaInstituto, historicoAnterior, planoMetasAtual);
			
			metaForm = new MetasHelper().processaMetaEntidade(metaEntidade,  
			    metaForm, 
			    historicoAtual,
			    historicoAnterior,
			    planoMetasAtual.getContratante(), 
			    planoMetasAtual.getFacilitador(), 
			    planoMetasAtual.getEvento(), 
			    planoMetasAtual.getRodizio(),
			    true
			);
			
			metaForm.setAtividade(metaInstituto);
			List<MetaInstituto> subAtividades = metaInstituto.getItens();
			if(subAtividades != null && subAtividades.size() > 0) {
				List<MetaForm> metas1 = processaMetaToMetaForm(subAtividades, planoMetasAtual);
			
				if(metas1.size() > 0){
					metaForm.setDependencias(metas1);
				}
			}
			
			metasForm.add(metaForm);
		}
		
		return metasForm;
	}
	
	private MetaEntidade searchMeta(List<MetaEntidade> metasEntidade, BaseEntidade entidadeId, BaseInstituto institutoId, MetaInstituto metaId){
		MetaEntidade meta = new MetaEntidade();
		
		meta.setEntidade(entidadeId);
		
		meta.setInstituto(institutoId);
		
		meta.setMeta(metaId);
		
		if(metasEntidade.indexOf(meta) != -1){
  		MetaEntidade retorno = metasEntidade.get(metasEntidade.indexOf(meta));
  		return retorno;		
		} else {
		  return null;
		}
	}

  @RequestMapping("/add")
  public ModelAndView saveContratacao(
      HttpServletRequest request,
      @ModelAttribute("planoMetasForm") @Validated PlanoMetasForm planoMetasForm,
      BindingResult result) {

    restauraRodizio(planoMetasForm);
    restauraInstituto(planoMetasForm);
    restauraEntidade(planoMetasForm);
    restauraFacilitador(planoMetasForm);

    if (result.hasErrors()) {
      restauraDependenciaCiclo(planoMetasForm.getDependencias());
      ModelAndView model = new ModelAndView("metas.contratacao2",
          "planoMetasForm", planoMetasForm);
      planoMetasForm.setFase(3);
      model.addObject("erros", true);
      return model;
    }

    ModelAndView model = new ModelAndView("metas.conclusao");

    if (planoMetasForm.getTipoContratante() == null) {
      planoMetasForm.setTipoContratante(TipoContratante.PRESIDENTE);
    }

    PlanoMetas plano = getPlano(planoMetasForm);
    //if (request.getSession().getAttribute("ROLE_CONTROLE") != null
    //    && request.getSession().getAttribute("ROLE_CONTROLE")
    //        .equals("ROLE_METAS_FACILITADOR")) {
    //  return model;
    //} else {
      if (plano.getId() == null || plano.getId() == 0) {
        planoMetasService.save(plano);
      } else {

        List<MetaEntidade> metas = plano.getListaMetas();

        for (MetaEntidade metaEntidade : metas) {
          List<MetaEntidadeAnotacao> anotacoes = metaEntidade.getAnotacoes();
          for (MetaEntidadeAnotacao metaEntidadeAnotacao : anotacoes) {
            if (metaEntidadeAnotacao.getAnotacao().getId() == null) {
              metaService.saveAnotacao(metaEntidadeAnotacao.getAnotacao());
              metaService.saveMetaAnotacao(metaEntidadeAnotacao);
            }
          }
          if (metaEntidade.getId() == null || metaEntidade.getId() == 0) {
            metaService.save(metaEntidade);
          } else {
            metaService.update(metaEntidade);

          }
        }
        planoMetasService.update(plano);
      }
    //}
    return model;
  }

	private void restauraDependenciaCiclo(List<MetaForm> lista) {
	  for (MetaForm metaForm : lista) {
	    if(metaForm.getCiclo() != null && metaForm.getCiclo().getId() != null){
  	    Rodizio rodVO = rodizioService.findById(metaForm.getCiclo().getId());
        metaForm.setCiclo(new RodizioVO(rodVO));
	    }
	    HistoricoMetaEntidadeVO hmeAnterior = metaForm.getSituacaoAnterior();
	    if(hmeAnterior.getCiclo() != null && hmeAnterior.getCiclo().getId() != null){
	      Rodizio rodVO = rodizioService.findById(hmeAnterior.getCiclo().getId());
	      hmeAnterior.setCiclo(new RodizioVO(rodVO));
	    }
	    HistoricoMetaEntidadeVO hmeAtual = metaForm.getSituacaoAtual();
	    if(hmeAtual.getCiclo() != null && hmeAtual.getCiclo().getId() != null){
	      Rodizio rodVO = rodizioService.findById(hmeAtual.getCiclo().getId());
	      hmeAtual.setCiclo(new RodizioVO(rodVO));
	    }
	    HistoricoMetaEntidadeVO hmeDesejada = metaForm.getSituacaoDesejada();
	    if(hmeDesejada.getCiclo() != null && hmeDesejada.getCiclo().getId() != null){
	      Rodizio rodVO = rodizioService.findById(hmeDesejada.getCiclo().getId());
	      hmeDesejada.setCiclo(new RodizioVO(rodVO));
	    }
	    if(metaForm.getDependencias() != null){
	      restauraDependenciaCiclo(metaForm.getDependencias());
	    }
    }
    
  }

  private PlanoMetas getPlano(PlanoMetasForm planoMetasForm) {
		PlanoMetas plano = new PlanoMetas();
		
		if(planoMetasForm.getId() != null && planoMetasForm.getId() > 0 ){
			plano = planoMetasService.findById(planoMetasForm.getId());
		} else {
			Rodizio rodizio = rodizioService.findById(planoMetasForm.getRodizio().getId());
			plano.setRodizio(rodizio);
			
			BaseInstituto instituto = baseInstitutoService.findById(planoMetasForm.getInstituto().getId());
			plano.setInstituto(instituto);
	    	
			BaseEntidade entidade = entidadeService.findById(planoMetasForm.getEntidade().getId());
			if(entidade == null){
			  entidade = new Entidade();
			  entidade.setId(planoMetasForm.getEntidade().getId());
			  entidade.setRazaoSocial(planoMetasForm.getEntidade().getRazaoSocial());
			}
			plano.setEntidade(entidade);
		}

    List<MetaEntidade> metasAux = metaService.findByEntidadeIdAndInstitutoId(plano.getEntidade().getId(), plano.getInstituto().getId());
    plano.setListaMetas(metasAux);
		
		plano.setFinalizado(planoMetasForm.isFinalizado());
		
		plano.setValidado(planoMetasForm.isValidado());
		
		plano.setEvento(planoMetasForm.getEvento());
		
		plano.setTipoContratante(planoMetasForm.getTipoContratante());
		
		if(planoMetasForm.getPresidente() != null && planoMetasForm.getPresidente().getId() != null){
			Pessoa presidente = pessoaService.getPessoa(planoMetasForm.getPresidente().getId());
			plano.setPresidente(presidente);
		} 
		
		if(planoMetasForm.getCoordenador() != null && planoMetasForm.getCoordenador().getId() != null){
			Pessoa coordenador = pessoaService.getPessoa(planoMetasForm.getCoordenador().getId());
			plano.setCoordenador(coordenador);
		}
		
		if(planoMetasForm.getTipoContratante() == TipoContratante.PRESIDENTE){
			plano.setContratante(plano.getPresidente());
		}
		if(planoMetasForm.getTipoContratante() == TipoContratante.COORDENADOR && planoMetasForm.getCoordenador() != null && planoMetasForm.getCoordenador().getId() != null){
			plano.setContratante(plano.getCoordenador());
		}
		if(planoMetasForm.getTipoContratante() == TipoContratante.OUTRO && planoMetasForm.getOutro() != null && planoMetasForm.getOutro().getId() != null){
			Pessoa outro = pessoaService.getPessoa(planoMetasForm.getOutro().getId());
			plano.setContratante(outro);
		}
		
		if(planoMetasForm.getCoordenador() != null && planoMetasForm.getCoordenador().getNomeCompleto() != null) plano.setNomeCoordenador(planoMetasForm.getCoordenador().getNomeCompleto());
		if(planoMetasForm.getEmailCoordenador() != null) plano.setEmailCoordenador(planoMetasForm.getEmailCoordenador());
		if(planoMetasForm.getTelefoneCoordenador() != null) plano.setTelefoneCoordenador(planoMetasForm.getTelefoneCoordenador());
		if(planoMetasForm.getOutro() != null && planoMetasForm.getOutro().getNomeCompleto() != null) plano.setNomeContratante(planoMetasForm.getOutro().getNomeCompleto());
		if(planoMetasForm.getEmailContratante() != null) plano.setEmailContratante(planoMetasForm.getEmailContratante());
		if(planoMetasForm.getTelefoneContratante() != null) plano.setTelefoneContratante(planoMetasForm.getTelefoneContratante());
		
		if(planoMetasForm.getFacilitador() != null && planoMetasForm.getFacilitador().getId() != null){
			Pessoa facilitador = pessoaService.getPessoa(planoMetasForm.getFacilitador().getId());
			plano.setFacilitador(facilitador);
		}

		List<MetaEntidade> metas = new ArrayList<MetaEntidade>();
		
		processaMetas(planoMetasForm.getDependencias(), metas, plano, null);
		
		plano.setListaMetas(metas);
		
		if(planoMetasForm.getAnotacoes() != null){
			Anotacao novaNota = null;
			for (Anotacao anot : planoMetasForm.getAnotacoes()) {
				if(anot.getId() == null && (anot.getTexto() == null || anot.getTexto().trim().equals(""))){
					continue;
				}
				
				if(plano.getListaAnotacoes() == null){
					plano.setListaAnotacoes(new ArrayList<Anotacao>());
				}
				
				if(plano.getListaAnotacoes().contains(anot)){
					novaNota = plano.getListaAnotacoes().get(plano.getListaAnotacoes().indexOf(anot));
					if(!novaNota.getTexto().equals(anot.getTexto())){
						novaNota.setData(new Date());
						if(plano.getEvento() == EventoMeta.RODIZIO){
						  if(plano.getFacilitador() != null && plano.getFacilitador().getId() != null )
						    novaNota.setResponsavel(plano.getFacilitador());
						} else {
						  if(plano.getContratante() != null && plano.getContratante().getId() != null )
						    novaNota.setResponsavel(plano.getContratante());
						}
						novaNota.setTexto(anot.getTexto());
					}
				} else {
					anot.setData(new Date());
					if(plano.getEvento() == EventoMeta.RODIZIO){
					  if(plano.getFacilitador() != null && plano.getFacilitador().getId() != null )
					    anot.setResponsavel(plano.getFacilitador());
					} else {
					  if(plano.getContratante() != null && plano.getContratante().getId() != null )
					    anot.setResponsavel(plano.getContratante());
					}
					plano.getListaAnotacoes().add(anot);
				}
			}
		}
		
		return plano;
	}
	
	private void processaMetas(List<MetaForm> metasForm, List<MetaEntidade> metas, PlanoMetas plano, MetaEntidade pai) {
    
	  
    	metasForm.removeAll(Collections.singleton(null));
    	
    	for (MetaForm metaForm : metasForm) {
    	  List<HistoricoMetaEntidade> historicoAtual = null;
    	  List<HistoricoMetaEntidade> historicoAnterior = null;
    		
    	  MetaEntidade meta = null;
    		if(metaForm.getId() != null && metaForm.getId() > 0){
    			meta = searchMeta(plano.getListaMetas(), plano.getEntidade(), plano.getInstituto(), metaForm.getAtividade());
    			historicoAtual = metaService.findByMetaEntidadeIdAndRodizioId(meta.getId(), plano.getRodizio().getId());
    			if(plano.getRodizio().getCicloAnterior() != null){
    			  historicoAnterior = metaService.findByMetaEntidadeIdAndRodizioId(meta.getId(), plano.getRodizio().getCicloAnterior().getId());
    			}
    		} else {
    			meta = new MetaEntidade();
    			meta.setInstituto(plano.getInstituto());
    			meta.setEntidade(plano.getEntidade());
    			meta.setPrimeiroRodizio(plano.getRodizio());
    			MetaInstituto metaInstituto = metaInstitutoService.getMetasInstituto(new Long(metaForm.getAtividade().getId()).intValue());
    			meta.setPai(meta);
    			meta.setMeta(metaInstituto);
    			meta.setDescricao(metaInstituto.getDescricao());
    			meta.setTipoMeta(metaInstituto.getTipoMeta());
    			meta.setViewOrder(metaInstituto.getViewOrder());
    			meta.setHistorico(new ArrayList<HistoricoMetaEntidade>());
    		}

    		 
    		HistoricoMetaEntidadeVO situacaoAtual = metaForm.getSituacaoAtual();
    		
			if(situacaoAtual.getTipoSituacao() == TipoSituacaoMeta.INICIAL){
				HistoricoMetaEntidade statusInicial = null;
				if(meta.getId() != null){
					if(historicoAtual != null && historicoAtual.size() > 0){
						for (HistoricoMetaEntidade historicoMetas : historicoAtual) {
							if(historicoMetas.getTipoSituacao() == TipoSituacaoMeta.INICIAL){
								statusInicial = historicoMetas;
								break;
							}
						}
					}
				} 
				if(statusInicial == null){
					statusInicial = new HistoricoMetaEntidade();
					statusInicial.setRodizio(plano.getRodizio());
					statusInicial.setTipoSituacao(TipoSituacaoMeta.INICIAL);
					statusInicial.setMeta(meta);
					meta.getHistorico().add(statusInicial);
				}
				statusInicial.setDataSituacao(new Date());
				
				statusInicial.setSituacao( metaForm.getSituacaoAtual().getSituacao() );
				
				if(statusInicial.getSituacao() == SituacaoMeta.NAOIMPLANTADA){
				  statusInicial.setConclusao(null);
				} else {
				  if(metaForm.getSituacaoAtual().getConclusao() != null){
	          statusInicial.setConclusao(metaForm.getSituacaoAtual().getConclusao());
	        }
				
  				if(metaForm.getSituacaoAtual().getPrevisao() != null){
  					statusInicial.setPrevisao(metaForm.getSituacaoAtual().getPrevisao());
  				}
  				
  				if(metaForm.getSituacaoAtual().getPrevisto() != null){
  					statusInicial.setPrevisto(metaForm.getSituacaoAtual().getPrevisto());
  				}
  				
  				if(metaForm.getSituacaoAtual().getRealizado() != null){
  					statusInicial.setRealizado(metaForm.getSituacaoAtual().getRealizado());
  				}
				}
				
				if(plano.getEvento() == EventoMeta.RODIZIO){
				  if(plano.getFacilitador() != null && plano.getFacilitador().getId() != null )
				    statusInicial.setResponsavel(plano.getFacilitador());
				} else {
					if(plano.getTipoContratante() == TipoContratante.PRESIDENTE){
						statusInicial.setResponsavel(plano.getPresidente());
					} else if(plano.getTipoContratante() == TipoContratante.COORDENADOR && plano.getCoordenador() != null && plano.getCoordenador().getId() != null){
						statusInicial.setResponsavel(plano.getCoordenador());
					} else if(plano.getTipoContratante() == TipoContratante.OUTRO && plano.getContratante() != null && plano.getContratante().getId() != null){
						statusInicial.setResponsavel(plano.getContratante());
					}
				}
				
				// Arrumar a questao de historico de metas para comtemplar as acoes anteriores e atuais no primeiro dia.
				// Arrumar as demais acoes para atender.
			} else if(situacaoAtual.getTipoSituacao() == TipoSituacaoMeta.AVALIAR){
			  HistoricoMetaEntidade statusAvaliar = null;
        if(meta.getId() != null){
          if(historicoAnterior != null && historicoAnterior.size() > 0){
            for (HistoricoMetaEntidade historicoMetas : historicoAnterior) {
              if(historicoMetas.getTipoSituacao() == TipoSituacaoMeta.AVALIAR){
                statusAvaliar = historicoMetas;
                break;
              }
            }
          }
        } 
        
        Rodizio ciclo = new Rodizio();
        ciclo.setId( metaForm.getSituacaoAtual().getCiclo().getId() );
        
        if(statusAvaliar == null){
          statusAvaliar = new HistoricoMetaEntidade();
          statusAvaliar.setRodizio(ciclo);
          statusAvaliar.setTipoSituacao(TipoSituacaoMeta.AVALIAR);
          statusAvaliar.setMeta(meta);
          meta.getHistorico().add(statusAvaliar);
        }
        
        statusAvaliar.setDataSituacao(new Date());
        
        statusAvaliar.setSituacao( metaForm.getSituacaoAtual().getSituacao() );
        
        statusAvaliar.setConclusao(metaForm.getSituacaoAtual().getConclusao());
        statusAvaliar.setPrevisao(metaForm.getSituacaoAtual().getPrevisao());
        statusAvaliar.setPrevisto(metaForm.getSituacaoAtual().getPrevisto());
        statusAvaliar.setRealizado(metaForm.getSituacaoAtual().getRealizado());
        
        if(plano.getEvento() == EventoMeta.RODIZIO){
          if(plano.getFacilitador() != null && plano.getFacilitador().getId() != null )
            statusAvaliar.setResponsavel(plano.getFacilitador());
        } else {
          if(plano.getTipoContratante() == TipoContratante.PRESIDENTE){
            statusAvaliar.setResponsavel(plano.getPresidente());
          } else if(plano.getTipoContratante() == TipoContratante.COORDENADOR && plano.getCoordenador() != null && plano.getCoordenador().getId() != null){
            statusAvaliar.setResponsavel(plano.getCoordenador());
          } else if(plano.getTipoContratante() == TipoContratante.OUTRO && plano.getContratante() != null && plano.getContratante().getId() != null){
            statusAvaliar.setResponsavel(plano.getContratante());
          }
        }
				
			}
			
			HistoricoMetaEntidadeVO situacaoDesejada = metaForm.getSituacaoDesejada();
			
			if(situacaoDesejada == null){
			  situacaoDesejada = new HistoricoMetaEntidadeVO();
			  situacaoDesejada.setCiclo( new RodizioVO( plano.getRodizio() ) );
			}
			
			TipoSituacaoMeta acaoDefault = null;
			
			if(plano.getEvento() == EventoMeta.PRERODIZIO){
				acaoDefault = TipoSituacaoMeta.PRECONTRATAR;
			} else if( plano.getEvento() == EventoMeta.RODIZIO ){
				acaoDefault = TipoSituacaoMeta.CONTRATAR;
			}
			
			//Se situacao é nula e tipo meta quantitativa e tem valor previsto entao planejado
			if(situacaoDesejada.getSituacao() == null && TipoMeta.META_QUANTITATIVA.equals(metaForm.getTipoMeta()) && situacaoDesejada.getPrevisto() != null){
				situacaoDesejada.setSituacao(SituacaoMeta.PLANEJADA);
			}
			
			//Situacao desejada pode ser apenas planejada/nao planejada 
			if(situacaoDesejada.getSituacao() != null &&
					(situacaoDesejada.getTipoSituacao() == acaoDefault ||
					 situacaoDesejada.getTipoSituacao() == null
					)
				){
				
				HistoricoMetaEntidade statusDesejado = null;
				if(meta.getId() != null){
					statusDesejado = new MetasHelper().getUltimoHistorico(historicoAtual, acaoDefault);
				}
				
				if(statusDesejado == null){
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
				
				if(plano.getEvento() == EventoMeta.RODIZIO){
				  if(plano.getFacilitador() != null && plano.getFacilitador().getId() != null)
				    statusDesejado.setResponsavel(plano.getFacilitador());
				} else {
				  if(plano.getContratante() != null && plano.getContratante().getId() != null)
				    statusDesejado.setResponsavel(plano.getContratante());
				}
				
			}
			
			if(metaForm.getObservacoes() != null){
				Anotacao novaNota = null;
				for (AnotacaoVO anot : metaForm.getObservacoes()) {
					if(anot.getId() == null && (anot.getTexto() == null || anot.getTexto().trim().equals(""))){
						continue;
					}
					
					if(meta.getAnotacoes() == null){
						meta.setAnotacoes(new ArrayList<MetaEntidadeAnotacao>());
					}
					
					List<MetaEntidadeAnotacao> listaAnotacoes = meta.getAnotacoes();
					
					Anotacao anotAux = new Anotacao();
					anotAux.setId(anot.getId());
					anotAux.setNivel(anot.getNivel());
					
					Rodizio cicloAux = new Rodizio();
					cicloAux.setId(anot.getCiclo().getId());
					
					MetaEntidadeAnotacao metaAnot = new MetaEntidadeAnotacao(meta, cicloAux, anotAux);
					
					if(listaAnotacoes.contains(metaAnot)){
					  int indice = listaAnotacoes.indexOf(metaAnot);
					  
					  if(indice != -1){
					    novaNota = listaAnotacoes.get(indice).getAnotacao();
					  }
						
					  if(novaNota != null && !novaNota.getTexto().equals(anot.getTexto())){
							novaNota.setData(new Date());
							novaNota.setTexto(anot.getTexto());
						}
						
					} else {
	          anotAux.setData(new Date());
	          if(anot.getResponsavel().getId() != null){
	            if(plano.getEvento() == EventoMeta.RODIZIO){
	              if(plano.getFacilitador() != null && plano.getFacilitador().getId() != null )
	                anotAux.setResponsavel(plano.getFacilitador());
	            } else {
	              if(plano.getTipoContratante() == TipoContratante.PRESIDENTE){
	                anotAux.setResponsavel(plano.getPresidente());
	              } else if(plano.getTipoContratante() == TipoContratante.COORDENADOR && plano.getCoordenador() != null && plano.getCoordenador().getId() != null){
	                anotAux.setResponsavel(plano.getCoordenador());
	              } else if(plano.getTipoContratante() == TipoContratante.OUTRO && plano.getContratante() != null && plano.getContratante().getId() != null){
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
			
			if(meta.getAnotacoes() != null){
  			for (MetaEntidadeAnotacao anotNova :  meta.getAnotacoes()) {
  			  Anotacao anotNovaCorrigi = anotNova.getAnotacao();
          if(anotNovaCorrigi.getResponsavel() != null && anotNovaCorrigi.getResponsavel().getId() == null)
            anotNovaCorrigi.setResponsavel(null);
        }
			}
			
			metas.add(meta);			
			
			List<MetaForm> subAtividades = metaForm.getDependencias();
			
			if(subAtividades != null)
				processaMetas(subAtividades, metas, plano, meta);
			
		}
    	
	}
	
	@RequestMapping("/list")
    public @ResponseBody List<PlanoMetasForm> listMetasEntidade(@RequestParam int entidade, @RequestParam int instituto) {
    	
    	List<PlanoMetas> lista = null;
    	if(entidade > 0 && instituto > 0){
    		lista = planoMetasService.findByEntidadeIdAndInstitutoId(entidade, instituto);
    	} else if(entidade > 0){
    		lista = planoMetasService.findByInstitutoId(instituto);
    	} else if(instituto > 0){
    		lista = planoMetasService.findByEntidadeId(entidade);
    	} else {
    		lista = planoMetasService.findAll();
    	}
    	
    	List<PlanoMetasForm> listanova = new ArrayList<PlanoMetasForm>();
    	
    	for (PlanoMetas planoMetas : lista) {
  			PlanoMetasForm planoMetasForm = new PlanoMetasForm();
  			List<MetaInstituto> atividades = metaInstitutoService.listMetaInstitutoByInstituto(planoMetas.getInstituto().getId());
  			
  			List<MetaEntidade> listaMetas =  metaService.findByEntidadeIdAndInstitutoId(planoMetas.getEntidade().getId(), planoMetas.getInstituto().getId());
  	    planoMetas.setListaMetas(listaMetas);
  			
  	    if(planoMetas.getListaMetas().size() > 0) {
  				List<MetaForm> metas = processaMetaToMetaForm(atividades, planoMetas);
  				planoMetasForm.setDependencias(metas);
  			}
  			listanova.add(planoMetasForm);
  		}
    	
    	return listanova;
    }
	
	 /**
	  * Impressão da ficha antes de salvar
	  * 
	  * @param planoMetasForm
	  * @return
	  */
  @RequestMapping("/imprimePreFichaRodizio")
  public ModelAndView preShowMetas(@ModelAttribute("planoMetasForm") PlanoMetasForm planoMetasForm) {
    
    restauraRodizio(planoMetasForm);
    restauraInstituto(planoMetasForm);
    restauraEntidade(planoMetasForm);
    restauraFacilitador(planoMetasForm);
    
    planoMetasForm.setFase(3);
    
    List<PlanoMetasForm> lista = new ArrayList<PlanoMetasForm>();
    
    lista.add(planoMetasForm);

    ModelAndView model = new ModelAndView("/app/metas/relatorioMetas.jsp");

    model.addObject("planoList", lista);
    
    List<SituacaoMeta> situacaoList = new ArrayList<SituacaoMeta>( Arrays.asList(SituacaoMeta.values() ));
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
	public ModelAndView showMetas(@ModelAttribute("planoMetasForm") @Validated PlanoMetasForm planoMetasForm) {
		
	  Rodizio rodizio = restauraRodizio(planoMetasForm);
    BaseInstituto instituto = restauraInstituto(planoMetasForm);
    BaseEntidade entidade = restauraEntidade(planoMetasForm);
    restauraFacilitador(planoMetasForm);
    
    	
		List<MetaForm> metasForm = null;
		
		List<MetaInstituto> metasIntituto =  metaInstitutoService.listMetaInstitutoByInstituto(instituto.getId());
		
		PlanoMetas planoMetasAtual =  planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade.getId(), instituto.getId(), rodizio.getId());
		
		List<MetaEntidade> listaMetas =  metaService.findByEntidadeIdAndInstitutoId(entidade.getId(), instituto.getId());
		
		if(planoMetasAtual != null){
      planoMetasForm.setId(planoMetasAtual.getId());
      planoMetasForm.setTipoContratante(planoMetasAtual.getTipoContratante());
      planoMetasForm.setContratante(planoMetasAtual.getContratante());
      Pessoa presidente = pessoaService.getPessoa(planoMetasAtual.getEntidade().getPresidente().getPessoa().getId());
      planoMetasForm.setPresidente(presidente);

      planoMetasAtual.setListaMetas(listaMetas);
      planoMetasAtual.setEvento(planoMetasForm.getEvento());
		}
		
		if(planoMetasAtual == null){
	    	metasForm = processaMetaInstitutoToMetaForm(metasIntituto, 
	    	    planoMetasForm.getFacilitador(), 
	    	    planoMetasForm.getContratante(), 
	    	    planoMetasForm.getEvento(),
	    	    planoMetasForm.getRodizio());	  
	    	
		} else if(planoMetasAtual.getListaMetas().size() > 0) {
			metasForm = processaMetaToMetaForm(metasIntituto, planoMetasAtual);
		} else {
			metasForm = new ArrayList<MetaForm>();
		}
		
  	planoMetasForm.setDependencias(metasForm);
  	
  	planoMetasForm.setFase(3);
  	
  	 List<PlanoMetasForm> lista = new ArrayList<PlanoMetasForm>();
     
     lista.add(planoMetasForm);

     ModelAndView model = new ModelAndView("/app/metas/relatorioMetas.jsp");

     model.addObject("planoList", lista);
		
		List<SituacaoMeta> situacaoList = new ArrayList<SituacaoMeta>( Arrays.asList(SituacaoMeta.values() ));
  	model.addObject("situacaoList", situacaoList);
    	
		return model;
	}

	/**
	 * Metodo responsavel pela preparação do dados para impressão da ficha em branco.
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
      BaseEntidade base = (BaseEntidade) request.getSession().getAttribute(
          "INSTITUICAO_CONTROLE");
      BaseEntidade entidade = entidadeService.findByIdOverview(base.getId());
      planoMetasForm.setEntidade(entidade);
    }
    
    List<MetaForm> metasForm = null;

    List<MetaInstituto> metasIntituto = metaInstitutoService.listMetaInstitutoByInstituto(planoMetasForm.getInstituto().getId());

    PlanoMetas planoMetasAtual = null;

    if (planoMetasAtual == null) {
      metasForm = processaMetaInstitutoToMetaForm(metasIntituto, 
          planoMetasForm.getFacilitador(),
          planoMetasForm.getContratante(), 
          planoMetasForm.getEvento(),
          planoMetasForm.getRodizio());
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
   * Metodo responsavel pela preparação do dados para impressão da ficha em branco.
   * 
   * @param request
   * @param planoMetasForm
   * @return
   */
  @RequestMapping("/imprimeTodasFichaBranco")
  public ModelAndView todasShowMetasBranco(HttpServletRequest request,
      @ModelAttribute("planoMetasForm") PlanoMetasForm planoMetasForm) {

    List<BaseInstituto> listaInstituto = baseInstitutoService.findByRodizioOverview(true);
    
    List<PlanoMetasForm> lista = new ArrayList<PlanoMetasForm>();
    
    for (BaseInstituto instituto : listaInstituto) {
      
      PlanoMetasForm planoMeta = new PlanoMetasForm();
      
      Rodizio rodizio = rodizioService.findById(planoMetasForm.getRodizio()
          .getId());
      
      if (request.getSession().getAttribute("INSTITUICAO_CONTROLE") != null) {
        BaseEntidade base = (BaseEntidade) request.getSession().getAttribute(
            "INSTITUICAO_CONTROLE");
        BaseEntidade entidade = entidadeService.findByIdOverview(base.getId());
       
        planoMeta.setEntidade(entidade);
        
      }
      
      if (planoMetasForm.getFacilitador() != null
          && planoMetasForm.getFacilitador().getId() != null) {
        Pessoa facilitador = pessoaService.getPessoa(planoMetasForm
            .getFacilitador().getId());
        planoMeta.setFacilitador(facilitador);
      }

      planoMeta.setRodizio(rodizio);
      planoMeta.setInstituto(instituto);
      planoMeta.setEvento(planoMetasForm.getEvento());

      List<MetaForm> metasForm = null;

      List<MetaInstituto> metasIntituto = metaInstitutoService
          .listMetaInstitutoByInstituto(instituto.getId());

      PlanoMetas planoMetasAtual = null;

      if (planoMetasAtual == null) {
        metasForm = processaMetaInstitutoToMetaForm(metasIntituto,
            planoMetasForm.getFacilitador(),
            planoMetasForm.getContratante(), 
            planoMetasForm.getEvento(),
            planoMetasForm.getRodizio());
      } 

      planoMeta.setDependencias(metasForm);

      planoMeta.setFase(3);

      lista.add(planoMeta);
      
      planoMeta = null;
      
    }

    ModelAndView model = new ModelAndView("/app/metas/relatorioMetas.jsp");

    model.addObject("planoList", lista);

    List<SituacaoMeta> situacaoList = new ArrayList<SituacaoMeta>(
        Arrays.asList(SituacaoMeta.values()));
    model.addObject("situacaoList", situacaoList);
    
    model.addObject("branco", true);

    return model;
    
  }
  
  @RequestMapping(value = "/common/reportgenerator/generatePDF")
  public void  generatePdf(HttpServletRequest req, HttpServletResponse res)
  {
      res.setContentType("text/html;charset=UTF-8");
      ServletOutputStream outStream=null;
      try 
      {
          
           String content = req.getParameter("content");
           
           String filename = req.getParameter("filename");


          System.out.println(content.replace("&", "&amp;"));

          res.setContentType("application/pdf");
          res.setHeader("Content-Disposition", "attachment;filename=" + filename + ".pdf");
          outStream = res.getOutputStream();

          ITextRenderer renderer = new ITextRenderer();
          
          MyPDFPageEvents mppe = new MyPDFPageEvents();
          
          FlyingSaucerPDFCreationListener fspcl = new FlyingSaucerPDFCreationListener(mppe);
          
          renderer.setListener(fspcl);
          
          content = content.replace("&nbsp;", ".");
          content = content.replace("<br>", "<br/>");
          
          
          renderer.setDocumentFromString(content);
          renderer.layout();
          renderer.createPDF(outStream);

      } 
      catch (Exception e) 
      {
          throw new RuntimeException();
      }
      finally
      {
          try
          {
              outStream.flush();
              outStream.close();
          }
          catch(Exception ex)
          {
            throw new RuntimeException();
          }

      }
  }
  
  
  private Rodizio restauraRodizio(PlanoMetasForm planoMetasForm) {
    // Verifica Rodizio
    if (planoMetasForm.getRodizio() != null
        && planoMetasForm.getRodizio().getId() != null) {
      Rodizio rodizio = rodizioService.findById(planoMetasForm.getRodizio().getId());    
      planoMetasForm.setRodizio(rodizio);
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
    if(planoMetasForm.getEntidade() != null && planoMetasForm.getEntidade().getId() != null){
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
    if(planoMetasForm.getFacilitador() != null && planoMetasForm.getFacilitador().getId() != null){
      Pessoa facilitador = pessoaService.getPessoa(planoMetasForm.getFacilitador().getId());
      planoMetasForm.setFacilitador(facilitador);
      return facilitador;
    } else {
      planoMetasForm.setFacilitador(null);
      return null;
    }
  }
  
  private Pessoa restauraPresidente(PlanoMetasForm planoMetasForm) {
    // Verifica Facilitador
    if(planoMetasForm.getPresidente() != null && planoMetasForm.getPresidente().getId() != null){
      Pessoa presidente = pessoaService.getPessoa(planoMetasForm.getPresidente().getId());
      planoMetasForm.setPresidente(presidente);
      return presidente;
    } else {
      planoMetasForm.setPresidente(null);
      return null;
    } 
  }
  
  private Pessoa restauraCoordenador(PlanoMetasForm planoMetasForm) {
    // Verifica Facilitador
    if(planoMetasForm.getCoordenador() != null && planoMetasForm.getCoordenador().getId() != null){
      Pessoa coordenador = pessoaService.getPessoa(planoMetasForm.getCoordenador().getId());
      planoMetasForm.setCoordenador(coordenador);
      return coordenador;
    } else {
      planoMetasForm.setCoordenador(null);
      return null;
    } 
  }
  
  private Pessoa restauraContratante(PlanoMetasForm planoMetasForm) {
    // Verifica Facilitador
    if(planoMetasForm.getContratante() != null && planoMetasForm.getContratante().getId() != null){
      Pessoa contratante = pessoaService.getPessoa(planoMetasForm.getContratante().getId());
      planoMetasForm.setContratante(contratante);
      return contratante;
    } else {
      planoMetasForm.setContratante(null);
      return null;
    } 
  }


}
