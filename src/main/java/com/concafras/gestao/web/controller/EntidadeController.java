package com.concafras.gestao.web.controller;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.concafras.gestao.enums.Sinalizador;
import com.concafras.gestao.enums.TipoContatoInternet;
import com.concafras.gestao.enums.TipoEntidade;
import com.concafras.gestao.enums.TipoTelefone;
import com.concafras.gestao.form.DirigenteOptionForm;
import com.concafras.gestao.form.EntidadeOptionForm;
import com.concafras.gestao.form.PresidenteOptionForm;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.Cidade;
import com.concafras.gestao.model.ContatoInternet;
import com.concafras.gestao.model.Dirigente;
import com.concafras.gestao.model.Entidade;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.PlanoMetas;
import com.concafras.gestao.model.Presidente;
import com.concafras.gestao.model.Telefone;
import com.concafras.gestao.model.view.EntidadeExcel;
import com.concafras.gestao.rest.utils.RestUtils;
import com.concafras.gestao.service.BaseInstitutoService;
import com.concafras.gestao.service.CidadeService;
import com.concafras.gestao.service.DirigenteService;
import com.concafras.gestao.service.EmailService;
import com.concafras.gestao.service.EntidadeService;
import com.concafras.gestao.service.PessoaService;
import com.concafras.gestao.service.PlanoMetasService;
import com.concafras.gestao.service.PresidenteService;
import com.concafras.gestao.service.TelefoneService;
import com.concafras.gestao.util.Util;

@Controller
@RequestMapping("/gestao/entidade")
public class EntidadeController {
  
  private static final Logger logger = LoggerFactory.getLogger(EntidadeController.class);

	@Autowired
	private EntidadeService entidadeService;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private TelefoneService telefoneService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PresidenteService presidenteService;

	@Autowired
	private DirigenteService dirigenteService;
	
  @Autowired
  private PessoaService pessoaService;
  
  @Autowired
  private PlanoMetasService planoMetasService;
  
  @Autowired
  private BaseInstitutoService baseInstitutoService;


	@RequestMapping("/")
	public String listEntidade(Map<String, Object> map) {
		map.put("basicos", true);
		return "entidade.listar";
	}

	@RequestMapping("/list")
	public @ResponseBody
	List<EntidadeOptionForm> listEntidade(@RequestParam String query,
			@RequestParam(value = "cidade", required = false) Integer cidade,
			@RequestParam(value = "instituto", required = false) Integer instituto,
			@RequestParam(value = "rodizio", required = false) Integer rodizio,			
			@RequestParam int maxRows) {

		List<Entidade> lista = entidadeService.listEntidade(query, cidade, maxRows);
		List<EntidadeOptionForm> retorno = new ArrayList<EntidadeOptionForm>();
		for (Entidade entidade : lista) {
			EntidadeOptionForm e = new EntidadeOptionForm();
			e.setId(entidade.getId());
			e.setRazaoSocial(entidade.getRazaoSocial());
			e.setEndereco(entidade.getEndereco().getLogradouro()
					+ (entidade.getEndereco().getNumero().trim().length() > 0 ? ", "
							+ entidade.getEndereco().getNumero()
							: "")
					+ (entidade.getEndereco().getCidade() != null ? " - "
							+ entidade.getEndereco().getCidade().getNome()
							+ "/"
							+ entidade.getEndereco().getCidade().getEstado()
									.getSigla() : ""));

			String telefone = entidade.getPrimeiroTelefone();
			e.setTelefone(telefone);
			if (entidade.getPresidente() != null) {
				e.setPresidente(new PresidenteOptionForm(entidade
						.getPresidente().getPessoa().getId(), entidade
						.getPresidente().getPessoa().getNome()));
			}
			PlanoMetas planoMetasAtual = null;
			if( rodizio != null && instituto != null){
			  planoMetasAtual = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade.getId(), instituto, rodizio);
			  if(planoMetasAtual != null){
	        if( planoMetasAtual.getPresidente() != null){
	          Pessoa presidente = pessoaService.getPessoa( planoMetasAtual.getPresidente().getId() );
	          e.setPresidente(new PresidenteOptionForm(presidente.getId(), presidente.getNome()));
	        } else {
	          String nome = null;
  	        if(planoMetasAtual.getNomePresidente() != null){
  	          nome = planoMetasAtual.getNomePresidente();
  	        }
  	        PresidenteOptionForm presidente = new PresidenteOptionForm(null, nome);
  	        e.setPresidente(presidente);
	        }
	        
	        if(planoMetasAtual.getCoordenador() != null){
	          Pessoa coordenador = pessoaService.getPessoa( planoMetasAtual.getCoordenador().getId()  );
	          e.setDirigente(new DirigenteOptionForm(
	              coordenador.getId(), 
	              coordenador.getNome(),
	              coordenador.getPrimeiroEmail(),
	              coordenador.getPrimeiroTelefone()               
	              ));
	        } else {
	          String nomeCoordenador = null;
	          String emailCoordenador = null;
	          String telefoneCoordenador = null;
	          if(planoMetasAtual.getNomeCoordenador() != null){
	            nomeCoordenador = planoMetasAtual.getNomeCoordenador() ;
	          }
	          if(planoMetasAtual.getEmailCoordenador() != null){
	            emailCoordenador = planoMetasAtual.getEmailCoordenador();
	          }
	          if(planoMetasAtual.getTelefoneCoordenador() != null){
	            telefoneCoordenador = planoMetasAtual.getTelefoneCoordenador();
	          }         
	          e.setDirigente(new DirigenteOptionForm(
                null, 
                nomeCoordenador,
                emailCoordenador,
                telefoneCoordenador               
                ));
	        }
	        
	        if(planoMetasAtual.getContratante() != null){
            Pessoa coordenador = pessoaService.getPessoa( planoMetasAtual.getContratante().getId()  );
            e.setOutro(new DirigenteOptionForm(
                coordenador.getId(), 
                coordenador.getNome(),
                coordenador.getPrimeiroEmail(),
                coordenador.getPrimeiroTelefone()               
                ));
          } else {
            String nomeContratante = null;
            String emailContratante = null;
            String telefoneContratante = null;
            if(planoMetasAtual.getNomeContratante() != null){
              nomeContratante = planoMetasAtual.getNomeContratante() ;
            }
            if(planoMetasAtual.getEmailContratante() != null){
              emailContratante = planoMetasAtual.getEmailContratante();
            }
            if(planoMetasAtual.getTelefoneContratante() != null){
              telefoneContratante = planoMetasAtual.getTelefoneContratante();
            }         
            e.setOutro(new DirigenteOptionForm(
                null, 
                nomeContratante,
                emailContratante,
                telefoneContratante               
                ));
          }
			  }
			}
			if (planoMetasAtual == null && instituto != null) {
				if (entidade.getDirigentes() != null
						&& entidade.getDirigentes().size() > 0) {
					Dirigente dirigenteAtivo = null;
					for (Dirigente pr : entidade.getDirigentes()) {
						if (pr.isAtivo()
								&& pr.getInstituto().getId() == instituto) {
							dirigenteAtivo = pr;
						}
					}
					if( dirigenteAtivo == null ){
					  for (Dirigente pr : entidade.getDirigentes()) {
	            if (pr.getInstituto().getId() == instituto) {
	              dirigenteAtivo = pr;
	            }
	          }
					}
					if (dirigenteAtivo != null
							&& dirigenteAtivo.getTrabalhador() != null)
						e.setDirigente(new DirigenteOptionForm(
						    dirigenteAtivo.getTrabalhador().getId(), 
						    dirigenteAtivo.getTrabalhador().getNome(),
						    dirigenteAtivo.getTrabalhador().getPrimeiroEmail(),
						    dirigenteAtivo.getTrabalhador().getPrimeiroTelefone()						    
						 )
					);
				}
			}
			if (entidade.getEndereco() != null
					&& entidade.getEndereco().getCidade() != null) {
				e.setCidade(entidade.getEndereco().getCidade().getNome());
				if (entidade.getEndereco().getCidade().getEstado() != null) {
					e.setUf(entidade.getEndereco().getCidade().getEstado()
							.getSigla());
				}
			}
			retorno.add(e);
		}
		return retorno;
	}

	private String getTelefone(List<Telefone> telefones) {
		String fone = "N/A";
		for (Iterator iterator = telefones.iterator(); iterator.hasNext();) {
			Telefone telefone = (Telefone) iterator.next();
			fone = telefone.getDdd() + " " + telefone.getNumero();
			if (fone.length() > 1) {
				return fone;
			}
		}
		return fone;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addEntidade(Map<String, Object> map) {

		map.put("entidade", new Entidade());
		map.put("basicos", true);
		
		List<BaseInstituto> listaInstituto = baseInstitutoService.findAllOverview();
		map.put("institutoList", listaInstituto);

		List<TipoEntidade> tipoEntidadeList = new ArrayList<TipoEntidade>(
				Arrays.asList(TipoEntidade.values()));
		map.put("tipoEntidadeList", tipoEntidadeList);

		List<TipoTelefone> tipotelefoneList = new ArrayList<TipoTelefone>(
				Arrays.asList(TipoTelefone.values()));
		map.put("tipotelefoneList", tipotelefoneList);

		List<TipoContatoInternet> tipocontatoList = new ArrayList<TipoContatoInternet>(
				Arrays.asList(TipoContatoInternet.values()));
		map.put("tipocontatoList", tipocontatoList);

		return "entidade.novo";
	}

	@RequestMapping(value = "/addBase", method = RequestMethod.GET)
	public String addEntidadeBase(Map<String, Object> map) {

		Entidade entidade = new Entidade();

		entidade.setTelefones(new AutoPopulatingList<Telefone>(Telefone.class));

		entidade.setEmails(new AutoPopulatingList<ContatoInternet>(
				ContatoInternet.class));

		entidade.getEmails().add(
				new ContatoInternet(TipoContatoInternet.EMAILCOM));

		entidade.getTelefones().add(new Telefone(TipoTelefone.FIXOCOM));

		map.put("entidade", entidade);
		map.put("basicos", true);
    
    List<BaseInstituto> listaInstituto = baseInstitutoService.findAllOverview();
    map.put("institutoList", listaInstituto);

		List<TipoEntidade> tipoEntidadeList = new ArrayList<TipoEntidade>(
				Arrays.asList(TipoEntidade.values()));
		map.put("tipoEntidadeList", tipoEntidadeList);

		List<TipoTelefone> tipotelefoneList = new ArrayList<TipoTelefone>(
				Arrays.asList(TipoTelefone.values()));
		map.put("tipotelefoneList", tipotelefoneList);

		List<TipoContatoInternet> tipocontatoList = new ArrayList<TipoContatoInternet>(
				Arrays.asList(TipoContatoInternet.values()));
		map.put("tipocontatoList", tipocontatoList);

		List<Sinalizador> sinalizadorList = new ArrayList<Sinalizador>(
				Arrays.asList(Sinalizador.values()));
		map.put("sinalizadorList", sinalizadorList);

		return "entidade.novobasico";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addEntidade(Map<String, Object> map,
			@ModelAttribute("entidade") @Validated Entidade entidade,
			BindingResult result) {

		if (result.hasErrors()) {
			map.put("entidade", entidade);
			map.put("basicos", true);
			
	    List<BaseInstituto> listaInstituto = baseInstitutoService.findAllOverview();
	    map.put("institutoList", listaInstituto);

			List<TipoEntidade> tipoEntidadeList = new ArrayList<TipoEntidade>(
					Arrays.asList(TipoEntidade.values()));
			map.put("tipoEntidadeList", tipoEntidadeList);

			List<TipoTelefone> tipotelefoneList = new ArrayList<TipoTelefone>(
					Arrays.asList(TipoTelefone.values()));
			map.put("tipotelefoneList", tipotelefoneList);

			List<TipoContatoInternet> tipocontatoList = new ArrayList<TipoContatoInternet>(
					Arrays.asList(TipoContatoInternet.values()));
			map.put("tipocontatoList", tipocontatoList);

			return "entidade.novo";
		}

		
		Integer id = entidade.getEndereco().getCidade().getId();

		if(id != null) {
			Cidade c = cidadeService.getCidade(id);
			entidade.getEndereco().setCidade(c);
		} else {
			entidade.getEndereco().setCidade(null);
		}

		List<Telefone> telefones2remove = manageTelefones(entidade);
		List<ContatoInternet> contatos2remove = manageContatoInternets(entidade);
		List<Presidente> presidentes2remove = managePresidentes(entidade);
		List<Dirigente> dirigentes2remove = manageDirigentes(entidade);

		if (entidade.getPresidente() == null
				|| entidade.getPresidente().getPessoa().getId() == null
				|| entidade.getPresidente().getPessoa().getId() <= 0) {
			entidade.setPresidente(null);
		}

		entidadeService.save(entidade);

		for (Telefone telefone : telefones2remove) {
			if (telefone.getId() != null) {
				// Implementar serviço que deleta telefone.
				telefoneService.removeTelefone(telefone.getId());
			}
		}

		for (ContatoInternet contato : contatos2remove) {
			if (contato.getId() != null) {
				// Implementar serviço que deleta telefone.
				emailService.removeEmail(contato.getId());
			}
		}

		for (Presidente presidente : presidentes2remove) {
			if (presidente.getId() != null) {
				// Implementar serviço que deleta telefone.
				presidenteService.removePresidente(presidente.getId());
			}
		}

		for (Dirigente dirigente : dirigentes2remove) {
			if (dirigente.getId() != null) {
				// Implementar serviço que deleta telefone.
				dirigenteService.removeDirigente(dirigente.getId());
			}
		}

		return "redirect:/gestao/entidade/";
	}

	@RequestMapping(value = "/edit/{entidadeId}", method = RequestMethod.POST)
	public String editInstituto(Map<String, Object> map,
			@PathVariable("entidadeId") Integer entidadeId) {

		map.put("entidade", entidadeService.findById(entidadeId));
		
    
    List<BaseInstituto> listaInstituto = baseInstitutoService.findAllOverview();
    map.put("institutoList", listaInstituto);

		List<TipoEntidade> tipoEntidadeList = new ArrayList<TipoEntidade>(
				Arrays.asList(TipoEntidade.values()));
		map.put("tipoEntidadeList", tipoEntidadeList);

		List<TipoTelefone> tipotelefoneList = new ArrayList<TipoTelefone>(
				Arrays.asList(TipoTelefone.values()));
		map.put("tipotelefoneList", tipotelefoneList);

		List<TipoContatoInternet> tipocontatoList = new ArrayList<TipoContatoInternet>(
				Arrays.asList(TipoContatoInternet.values()));
		map.put("tipocontatoList", tipocontatoList);

		List<Sinalizador> sinalizadorList = new ArrayList<Sinalizador>(
				Arrays.asList(Sinalizador.values()));
		map.put("sinalizadorList", sinalizadorList);

		map.put("basicos", true);
		return "entidade.editar";
	}

	@RequestMapping(value = "/edit/save/{entidadeId}", method = RequestMethod.POST)
	public String editInstituto(
			HttpServletRequest request,
			@ModelAttribute("entidade") Entidade entidade,
			@PathVariable("entidadeId") Integer entidadeId) {

		Integer id = entidade.getEndereco().getCidade().getId();

		Cidade c = cidadeService.getCidade(id);

		entidade.getEndereco().setCidade(c);

		List<Telefone> telefones2remove = manageTelefones(entidade);
		List<ContatoInternet> contatos2remove = manageContatoInternets(entidade);
		List<Presidente> presidentes2remove = managePresidentes(entidade);
		List<Dirigente> dirigentes2remove = manageDirigentes(entidade);

		if (entidade.getPresidente() == null
				|| entidade.getPresidente().getPessoa().getId() == null
				|| entidade.getPresidente().getPessoa().getId() <= 0) {
			entidade.setPresidente(null);
		}
		
		Entidade base = entidadeService.findById(entidade.getId());
		
		Util.myCopyProperties(entidade, base);

		entidadeService.update(base);
		//entidadeService.updateEntidade(entidade);

		for (Telefone telefone : telefones2remove) {
			if (telefone.getId() != null) {
				// Implementar serviço que deleta telefone.
				telefoneService.removeTelefone(telefone.getId());
			}
		}

		for (ContatoInternet contato : contatos2remove) {
			if (contato.getId() != null) {
				// Implementar serviço que deleta telefone.
				emailService.removeEmail(contato.getId());
			}
		}

		for (Presidente presidente : presidentes2remove) {
			if (presidente.getId() != null) {
				// Implementar serviço que deleta telefone.
				presidenteService.removePresidente(presidente.getId());
			}
		}

		for (Dirigente dirigente : dirigentes2remove) {
			if (dirigente.getId() != null) {
				// Implementar serviço que deleta telefone.
				dirigenteService.removeDirigente(dirigente.getId());
			}
		}
		
		return "entidade.conclusao";
	}

	@RequestMapping("/delete/{entidadeId}")
	public String deleteEntidade(@PathVariable("entidadeId") Integer entidadeId) {

		entidadeService.removeEntidade(entidadeId);

		return "redirect:/gestao/entidade/";
	}

	// Manage dynamically added or removed telefones
	private List<Telefone> manageTelefones(Entidade entidade) {
		// Store the telefones which shouldn't be persisted
		List<Telefone> telefones2remove = new ArrayList<Telefone>();
		if (entidade.getTelefones() != null) {
			for (Iterator<Telefone> i = entidade.getTelefones().iterator(); i
					.hasNext();) {
				Telefone telefone = i.next();
				// If the remove flag is true, remove the telefone from the list
				if (telefone != null
						&& (telefone.getRemove() == 1
								|| telefone.getNumero() == null || telefone
								.getNumero().trim().equals(""))) {
					telefones2remove.add(telefone);
					i.remove();
					// Otherwise, perform the links
				} else {
					// telefone.setPessoa(pessoa);
				}
			}
		}
		return telefones2remove;
	}

	private List<ContatoInternet> manageContatoInternets(Entidade entidade) {
		// Store the emails which shouldn't be persisted
		List<ContatoInternet> contatos2remove = new ArrayList<ContatoInternet>();
		if (entidade.getEmails() != null) {
			for (Iterator<ContatoInternet> i = entidade.getEmails().iterator(); i
					.hasNext();) {
				ContatoInternet email = i.next();
				// If the remove flag is true, remove the email from the list
				if (email != null
						&& (email.getRemove() == 1
								|| email.getContato() == null || email
								.getContato().trim().equals(""))) {
					contatos2remove.add(email);
					i.remove();
					// Otherwise, perform the links
				} else {
					// email.setPessoa(pessoa);
				}
			}
			
		// Set principal
      
      ContatoInternet contatoPrincipal = null;
      boolean principalLocalizado = false;
      for (Iterator<ContatoInternet> i = entidade.getEmails().iterator(); i.hasNext();) {
        ContatoInternet email = i.next();
         if(email.isPrincipal() && (email.getTipo() == TipoContatoInternet.EMAILCOM || email.getTipo() == TipoContatoInternet.EMAILPES)){
           contatoPrincipal = email;
           principalLocalizado = true;
           break;
         }
      }
      
      if(!principalLocalizado){
        for (Iterator<ContatoInternet> i = entidade.getEmails().iterator(); i.hasNext();) {
          ContatoInternet email = i.next();
           if(email.getTipo() == TipoContatoInternet.EMAILCOM || email.getTipo() == TipoContatoInternet.EMAILPES){
             contatoPrincipal = email;
             principalLocalizado = true;
             break;
           }
        }
      }
      
      if(principalLocalizado){
        for (Iterator<ContatoInternet> i = entidade.getEmails().iterator(); i.hasNext();) {
          ContatoInternet email = i.next();
           if(email.equals(contatoPrincipal)){
             email.setPrincipal(true);
           } else {
             email.setPrincipal(false);
           }
        }
      }
		}
		
		return contatos2remove;
	}

	private List<Presidente> managePresidentes(Entidade entidade) {
		// Store the Presidente which shouldn't be persisted
		List<Presidente> presidentes2remove = new ArrayList<Presidente>();
		if (entidade.getPresidentes() != null) {
			for (Iterator<Presidente> i = entidade.getPresidentes().iterator(); i
					.hasNext();) {
				Presidente presidente = i.next();
				// If the remove flag is true, remove the email from the list
				if (presidente != null && presidente.getRemove() == 1) {
					presidentes2remove.add(presidente);
					i.remove();
					// Otherwise, perform the links
				} else if(presidente != null){
					presidente.setEntidade(entidade);
				}
			}
		}
		return presidentes2remove;
	}

	private List<Dirigente> manageDirigentes(Entidade entidade) {
		// Store the Dirigente which shouldn't be persisted
		List<Dirigente> dirigentes2remove = new ArrayList<Dirigente>();
		if (entidade.getDirigentes() != null) {
			for (Iterator<Dirigente> i = entidade.getDirigentes().iterator(); i
					.hasNext();) {
				Dirigente dirigente = i.next();
				// If the remove flag is true, remove the email from the list
				if (dirigente != null && dirigente.getRemove() == 1) {
					dirigentes2remove.add(dirigente);
					i.remove();
				} else if (dirigente != null) {
					dirigente.setEntidade(entidade);
				}
			}
		}
		return dirigentes2remove;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String value) {
				try {
					Date parsedDate = dateFormat.parse(value);
					setValue(parsedDate);
				} catch (ParseException e) {
					setValue(null);
				}
			}

			@Override
			public String getAsText() {
				try {
					String parsedDate = dateFormat.format((Date) getValue());
					return parsedDate;
				} catch (Exception e) {
					return "";
				}
			}
		});
	}

	@RequestMapping(value = "/validateCnpjUnique", method = RequestMethod.GET)
	public @ResponseBody
	boolean validateCnpjUnique(@RequestBody String cnpj) {

		return !entidadeService.existeEntidadeCnpj(cnpj);
		
	}

	@RequestMapping(value = "/listPagination", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody
	String springPaginationDataTables(HttpServletRequest request)
			throws IOException {

		// Fetch the page number from client
		Integer pageNumber = 0;
		if (null != request.getParameter("iDisplayStart"))
			pageNumber = (Integer
					.valueOf(request.getParameter("iDisplayStart")) / 10) + 1;

		// Fetch search parameter
		String searchParameter = request.getParameter("sSearch");
		
		// Sort parameter
		String sortCol = request.getParameter("iSortCol_0");
		
		// Sort parameter
		String sortDir = request.getParameter("sSortDir_0");

		// Fetch Page display length
		Integer pageDisplayLength = Integer.valueOf(request
				.getParameter("iDisplayLength"));

		Long totalDisplayRecords = entidadeService
				.countListRangeEntidade(searchParameter);

		// Create page list data
		List<EntidadeOptionForm> entidadeList = createPaginationData(
				searchParameter, sortCol, sortDir, pageNumber, pageDisplayLength);

    return new RestUtils<EntidadeOptionForm>().createDatatablePaginateResponse(entidadeList, totalDisplayRecords.intValue(), totalDisplayRecords.intValue() );
    
	}

	private List<EntidadeOptionForm> createPaginationData(
			String searchParameter, String sortCol, String sortDir, Integer pageNumber,
			Integer pageDisplayLength) {

		int startRange = 0 + (pageDisplayLength * (pageNumber - 1));
		
		if(sortCol.equals("0")){
			sortCol = "id";
		} else if(sortCol.equals("1")){
			sortCol = "razaoSocial";
		} else if(sortCol.equals("2")){
			sortCol = "presidente";
		} else if(sortCol.equals("3")){
			sortCol = "cidade";
		} else {
			sortCol = null;
		}

		List<Entidade> lista = entidadeService.listRangeEntidade(
				searchParameter, sortCol, sortDir, startRange, pageDisplayLength);
		List<EntidadeOptionForm> retorno = new ArrayList<EntidadeOptionForm>();
		for (Entidade entidade : lista) {
			EntidadeOptionForm e = new EntidadeOptionForm();
			e.setId(entidade.getId());
			e.setRazaoSocial(entidade.getRazaoSocial());
			e.setEndereco(entidade.getEndereco().getLogradouro()
					+ (entidade.getEndereco().getNumero().trim().length() > 0 ? ", "
							+ entidade.getEndereco().getNumero()
							: "")
					+ (entidade.getEndereco().getCidade() != null ? " - "
							+ entidade.getEndereco().getCidade().getNome()
							+ "/"
							+ entidade.getEndereco().getCidade().getEstado()
									.getSigla() : ""));

			String telefone = entidade.getPrimeiroTelefone();
			e.setTelefone(telefone);
			if (entidade.getPresidente() != null) {
				e.setPresidente(new PresidenteOptionForm(entidade
						.getPresidente().getPessoa().getId(), entidade
						.getPresidente().getPessoa().getNome()));
			}

			if (entidade.getEndereco() != null
					&& entidade.getEndereco().getCidade() != null) {
				e.setCidade(entidade.getEndereco().getCidade().getNome());
				if (entidade.getEndereco().getCidade().getEstado() != null) {
					e.setUf(entidade.getEndereco().getCidade().getEstado()
							.getSigla());
				}
			}
			retorno.add(e);
		}
		return retorno;
	}
	
  private int processExcelFile(InputStream file, Map<String, Object> map) {

    int count = 0;

    try {

      XSSFWorkbook wb = new XSSFWorkbook(file);
      XSSFSheet sheet = wb.getSheetAt(0);
      Row linha = null;
      XSSFCell cell = null;
      FormulaEvaluator ev = wb.getCreationHelper().createFormulaEvaluator();
      Integer numLinha = 2;
      String nome = null;
      List<EntidadeExcel> listEntidadeExcels = new ArrayList<EntidadeExcel>();
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
            EntidadeExcel ae = processaLinha(ev, numLinha, sheet);
            listEntidadeExcels.add(ae);
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

      map.put("listEntidades", listEntidadeExcels);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return count;

  }

  private EntidadeExcel processaLinha(FormulaEvaluator ev, Integer numLinha,
      Sheet sheet) {

    Row linha = sheet.getRow(numLinha);
    XSSFCell cell = (XSSFCell) linha.getCell(2);

    boolean excluir;
    Integer codigo;
    String nome;
    String cnpj;
    String logradouro;
    String numero;
    String complemento;
    String bairro;
    String cidade;
    String estado;
    String fone1;
    String fone2;
    String dirigente;
    String email;

    cell = (XSSFCell) linha.getCell(0);
    String sExcluir = Util.getCellValue(ev, cell, sheet);
    excluir = !sExcluir.trim().isEmpty();

    cell = (XSSFCell) linha.getCell(1);
    codigo = Integer.parseInt(Util.getCellValue(ev, cell, sheet));

    cell = (XSSFCell) linha.getCell(2);
    nome = Util.getCellValue(ev, cell, sheet);

    cell = (XSSFCell) linha.getCell(3);
    cnpj = Util.getCellValue(ev, cell, sheet);

    cell = (XSSFCell) linha.getCell(4);
    logradouro = Util.getCellValue(ev, cell, sheet);

    cell = (XSSFCell) linha.getCell(5);
    numero = Util.getCellValue(ev, cell, sheet);

    cell = (XSSFCell) linha.getCell(6);
    complemento = Util.getCellValue(ev, cell, sheet);

    cell = (XSSFCell) linha.getCell(7);
    bairro = Util.getCellValue(ev, cell, sheet);

    cell = (XSSFCell) linha.getCell(8);
    cidade = Util.getCellValue(ev, cell, sheet);

    cell = (XSSFCell) linha.getCell(8);
    estado = Util.getCellValue(ev, cell, sheet);

    cell = (XSSFCell) linha.getCell(8);
    fone1 = Util.getCellValue(ev, cell, sheet);

    cell = (XSSFCell) linha.getCell(8);
    fone2 = Util.getCellValue(ev, cell, sheet);

    cell = (XSSFCell) linha.getCell(8);
    dirigente = Util.getCellValue(ev, cell, sheet);

    cell = (XSSFCell) linha.getCell(8);
    email = Util.getCellValue(ev, cell, sheet);

    EntidadeExcel atividadeImportada = new EntidadeExcel(excluir, codigo, nome,
        cnpj, logradouro, numero, complemento, bairro, cidade, estado, fone1,
        fone2, dirigente, email);

    return atividadeImportada;

  }
	
}
