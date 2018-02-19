package com.concafras.gestao.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.concafras.gestao.enums.EstadoCivil;
import com.concafras.gestao.enums.Sinalizador;
import com.concafras.gestao.enums.TipoContatoInternet;
import com.concafras.gestao.enums.TipoTelefone;
import com.concafras.gestao.form.PessoaOptionForm;
import com.concafras.gestao.model.BaseEntidade;
import com.concafras.gestao.model.ContatoInternet;
import com.concafras.gestao.model.Endereco;
import com.concafras.gestao.model.Entidade;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.Telefone;
import com.concafras.gestao.model.view.PessoaExcel;
import com.concafras.gestao.rest.utils.RestUtils;
import com.concafras.gestao.service.EmailService;
import com.concafras.gestao.service.EntidadeService;
import com.concafras.gestao.service.PessoaService;
import com.concafras.gestao.service.TelefoneService;
import com.concafras.gestao.util.Util;

@Controller
@RequestMapping("/gestao/pessoa")
public class PessoaController {
  
  private static final Logger logger = LoggerFactory.getLogger(PessoaController.class);

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private TelefoneService telefoneService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EntidadeService entidadeService;

	@RequestMapping("/")
	public String listPeople(Map<String, Object> map) {
		map.put("pessoaList", pessoaService.listPessoaResumo());
		map.put("basicos", true);
		return "pessoa.listar";
	}
	
	@RequestMapping("/entidade")
	public String listTrabalhadores(HttpServletRequest request, Map<String, Object> map) {
		BaseEntidade base = (BaseEntidade) request.getSession().getAttribute("INSTITUICAO_CONTROLE");
		map.put("pessoaList", entidadeService.listTrabalhadoresEntidade(base.getId()));
		map.put("basicos", true);
		return "pessoa.listar";
	}
	
	@RequestMapping("/list")
    public @ResponseBody List<PessoaOptionForm> listPessoa(@RequestParam String query, @RequestParam int maxRows) {
    	
    	 List<Pessoa> lista = pessoaService.listPessoa(query, maxRows);
    	 List<PessoaOptionForm> retorno = new ArrayList<PessoaOptionForm>();
    	 for (Pessoa pessoa : lista) {
    		 PessoaOptionForm e = new PessoaOptionForm();
			e.setId(pessoa.getId());
			e.setNome(pessoa.getNome());
			if(pessoa.getEndereco() != null && pessoa.getEndereco().getCidade() != null){
				e.setCidade(pessoa.getEndereco().getCidade().getNome());
				if(pessoa.getEndereco().getCidade().getEstado() != null){
					e.setUf(pessoa.getEndereco().getCidade().getEstado().getSigla());
				}
			}
			e.setEmail(pessoa.getPrimeiroEmail());
			retorno.add(e);
		}
    	return retorno;
    }
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addPerson(Map<String, Object> map) {
		
		Pessoa nova = new Pessoa();
		
		nova.setTelefones(new AutoPopulatingList<Telefone>(Telefone.class));
		
		nova.setEnderecos(new AutoPopulatingList<Endereco>(Endereco.class));
		
		nova.setEmails(new AutoPopulatingList<ContatoInternet>(ContatoInternet.class));
		
		map.put("pessoa", nova);
		
		List<EstadoCivil> estadocivilList = new ArrayList<EstadoCivil>( Arrays.asList(EstadoCivil.values() ));
    	map.put("estadocivilList", estadocivilList);
    	
    	List<TipoTelefone> tipotelefoneList = new ArrayList<TipoTelefone>( Arrays.asList(TipoTelefone.values() ));
    	map.put("tipotelefoneList", tipotelefoneList);
    	
    	List<TipoContatoInternet> tipocontatoList = new ArrayList<TipoContatoInternet>( Arrays.asList(TipoContatoInternet.values() ));
    	map.put("tipocontatoList", tipocontatoList);
    	
    	List<Sinalizador> sinalizadorList = new ArrayList<Sinalizador>( Arrays.asList(Sinalizador.values() ));
    	map.put("sinalizadorList", sinalizadorList);
		
		map.put("basicos", true);
		return "pessoa.novo";
	}
	
	@RequestMapping(value = "/addBase", method = RequestMethod.GET)
	public String addPessoaBasico(Map<String, Object> map) {
		
		Pessoa nova = new Pessoa();
		
		nova.setTelefones(new AutoPopulatingList<Telefone>(Telefone.class));
		
		nova.setEnderecos(new AutoPopulatingList<Endereco>(Endereco.class));
		
		nova.setEmails(new AutoPopulatingList<ContatoInternet>(ContatoInternet.class));
		
		nova.getEmails().add(new ContatoInternet(TipoContatoInternet.EMAILPES));
		
		nova.getTelefones().add(new Telefone(TipoTelefone.CELULAR));
		
		nova.getTelefones().add(new Telefone(TipoTelefone.FIXORES));
		
		map.put("pessoa", nova);
		
    	List<TipoTelefone> tipotelefoneList = new ArrayList<TipoTelefone>( Arrays.asList(TipoTelefone.values() ));
    	map.put("tipotelefoneList", tipotelefoneList);
    	
    	List<TipoContatoInternet> tipocontatoList = new ArrayList<TipoContatoInternet>( Arrays.asList(TipoContatoInternet.values() ));
    	map.put("tipocontatoList", tipocontatoList);
    	
		map.put("basicos", true);
		return "pessoa.novobasico";
	}
	
	@RequestMapping(value = "/addBase", method = RequestMethod.POST)
	public String addPessoaBasico(HttpServletRequest request, @ModelAttribute("pessoa") Pessoa pessoa,
			BindingResult result) {

		manageTelefones(pessoa);
		manageContatoInternets(pessoa);
		
		pessoaService.addPessoa(pessoa);
		
		BaseEntidade base = (BaseEntidade) request.getSession().getAttribute("INSTITUICAO_CONTROLE");
		
		if(base != null){
			Entidade entidade = entidadeService.findById(base.getId());
			
			List<Pessoa> trabalhadores = entidade.getTrabalhadores();
			if(trabalhadores == null){
				entidade.setTrabalhadores(new ArrayList<Pessoa>());
				trabalhadores = entidade.getTrabalhadores();
			}
			
			trabalhadores.add(pessoa);
			
			entidadeService.update(entidade);
		}

		if(base != null){
    		return "redirect:/gestao/pessoa/entidade/";
    	} else {
    		return "redirect:/gestao/pessoa/";
    	}
	}
	
	@RequestMapping(value="/editBase/{pessoaId}", method=RequestMethod.POST)
    public String editPessoaBasico(Map<String, Object> map, @PathVariable("pessoaId") Integer pessoaId) {
    	
        map.put("pessoa", pessoaService.getPessoa(pessoaId));
        
    	List<TipoTelefone> tipotelefoneList = new ArrayList<TipoTelefone>( Arrays.asList(TipoTelefone.values() ));
    	map.put("tipotelefoneList", tipotelefoneList);

    	List<TipoContatoInternet> tipocontatoList = new ArrayList<TipoContatoInternet>( Arrays.asList(TipoContatoInternet.values() ));
    	map.put("tipocontatoList", tipocontatoList);
    	
    	map.put("readonly", true);
    	
        map.put("basicos", true);
        
        return "pessoa.novobasico";
    }
	
	@RequestMapping(value="/editBase/save/{pessoaId}", method=RequestMethod.POST)
    public String editPessoaBasico(HttpServletRequest request, @ModelAttribute("pessoa") Pessoa pessoa, @PathVariable("pessoaId") Integer pessoaId) {
    	
    	
    	List<Telefone> telefones2remove = manageTelefones(pessoa);
    	List<ContatoInternet> contatos2remove = manageContatoInternets(pessoa);
    	
    	Pessoa pessoaBase = pessoaService.getPessoa(pessoa.getId());
    	
    	pessoaBase.setNomeCompleto(pessoa.getNomeCompleto());
    	pessoaBase.setCpf(pessoa.getCpf());
    	if(pessoa.getEndereco() == null){
    		pessoa.setEndereco(new Endereco());
    	}
    	
    	pessoaBase.getEndereco().setCidade(pessoa.getEndereco().getCidade());
    	
    	pessoaService.updatePessoa(pessoa);
    	
    	BaseEntidade base = (BaseEntidade) request.getSession().getAttribute("INSTITUICAO_CONTROLE");
		
		if(base != null){
			Entidade entidade = entidadeService.findById(base.getId());
			
			List<Pessoa> trabalhadores = entidade.getTrabalhadores();
			if(trabalhadores == null){
				entidade.setTrabalhadores(new ArrayList<Pessoa>());
				trabalhadores = entidade.getTrabalhadores();
			}
			
			if(!trabalhadores.contains(pessoa)){
			  trabalhadores.add(pessoa);
			}
			
			entidadeService.update(entidade);
		}
    	
    	for (Telefone telefone : telefones2remove) {
            if (telefone.getId() != null) {
            	//Implementar serviço que deleta telefone.
                telefoneService.removeTelefone(telefone.getId());
            }
        }
    	
    	for (ContatoInternet contato : contatos2remove) {
    		if (contato.getId() != null) {
    			//Implementar serviço que deleta telefone.
    			 emailService.removeEmail(contato.getId());
    		}
    	}

    	if(base != null){
    		return "redirect:/gestao/pessoa/entidade/";
    	} else {
    		return "redirect:/gestao/pessoa/";
    	}
    }
	
	@RequestMapping("/deleteBase/{personId}")
	public String deletePessoaBasico(HttpServletRequest request, @PathVariable("personId") Integer personId) {

		BaseEntidade base = (BaseEntidade) request.getSession().getAttribute("INSTITUICAO_CONTROLE");
		
		if(base != null){
			Entidade entidade = entidadeService.findById(base.getId());
			
			List<Pessoa> trabalhadores = entidade.getTrabalhadores();
			if(trabalhadores == null){
				entidade.setTrabalhadores(new ArrayList<Pessoa>());
				trabalhadores = entidade.getTrabalhadores();
			}
			
			for (Pessoa trabalhador : trabalhadores) {
				if(trabalhador.getId().equals( personId )){
					trabalhadores.remove(trabalhador);
					break;
				}
			}			
			
			entidadeService.update(entidade);
		}

		if(base != null){
    		return "redirect:/gestao/pessoa/entidade/";
    	} else {
    		return "redirect:/gestao/pessoa/";
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("pessoa") Pessoa pessoa,
			BindingResult result) {

		manageTelefones(pessoa);
		manageContatoInternets(pessoa);
		
		pessoaService.addPessoa(pessoa);

		return "redirect:/gestao/pessoa/";
	}
	
	@RequestMapping(value="/edit/{pessoaId}", method=RequestMethod.POST)
    public String editPessoa(Map<String, Object> map, @PathVariable("pessoaId") Integer pessoaId) {
    	
        map.put("pessoa", pessoaService.getPessoa(pessoaId));
        
		List<EstadoCivil> estadocivilList = new ArrayList<EstadoCivil>( Arrays.asList(EstadoCivil.values() ));
    	map.put("estadocivilList", estadocivilList);
    	
    	List<TipoTelefone> tipotelefoneList = new ArrayList<TipoTelefone>( Arrays.asList(TipoTelefone.values() ));
    	map.put("tipotelefoneList", tipotelefoneList);

    	List<TipoContatoInternet> tipocontatoList = new ArrayList<TipoContatoInternet>( Arrays.asList(TipoContatoInternet.values() ));
    	map.put("tipocontatoList", tipocontatoList);
    	
    	List<Sinalizador> sinalizadorList = new ArrayList<Sinalizador>( Arrays.asList(Sinalizador.values() ));
    	map.put("sinalizadorList", sinalizadorList);
		
        map.put("basicos", true);
        return "pessoa.editar";
    }
    
    @RequestMapping(value="/edit/save/{pessoaId}", method=RequestMethod.POST)
    public String editPessoa(@ModelAttribute("pessoa") Pessoa pessoa, @PathVariable("pessoaId") Integer pessoaId) {
    	
    	
    	List<Telefone> telefones2remove = manageTelefones(pessoa);
    	List<ContatoInternet> contatos2remove = manageContatoInternets(pessoa);
    	
    	Pessoa base = pessoaService.getPessoa(pessoa.getId());
    	
    	Util.myCopyProperties(pessoa, base);
    	
    	pessoaService.updatePessoa(base);
    	
    	for (Telefone telefone : telefones2remove) {
            if (telefone.getId() != null) {
            	//Implementar serviço que deleta telefone.
                telefoneService.removeTelefone(telefone.getId());
            }
        }
    	
    	for (ContatoInternet contato : contatos2remove) {
    		if (contato.getId() != null) {
    			//Implementar serviço que deleta telefone.
    			 emailService.removeEmail(contato.getId());
    		}
    	}

        return "redirect:/gestao/pessoa/";
    }
	
	@RequestMapping(value = "/saveImport", method = RequestMethod.POST)
	public String saveImportPerson(@ModelAttribute("importado") PessoaExcel importado,
			BindingResult result) {
		
		Pessoa person = PessoaExcel.getPessoa(importado);
		
		pessoaService.addPessoa(person);
		
		return "redirect:/gestao/pessoa/";
	}

	@RequestMapping("/delete/{personId}")
	public String deletePerson(@PathVariable("personId") Integer personId) {

		pessoaService.removePessoa(personId);

		return "redirect:/gestao/pessoa/";
	}

	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String upload(Map<String, Object> map) {
		map.put("basicos", true);
		return "pessoa.upload";
	}
	
  @RequestMapping(value = "upload", method = RequestMethod.POST)
  public String upload(HttpServletRequest request, Map<String, Object> map) {
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

    List<EstadoCivil> estadocivilList = new ArrayList<EstadoCivil>(
        Arrays.asList(EstadoCivil.values()));
    map.put("estadocivilList", estadocivilList);
    map.put("basicos", true);
    return "pessoa.uploadvalidate";
  }
	
	
  private int processExcelFile(InputStream file, Map<String, Object> map) {

    int count = 0;

    try {

      XSSFWorkbook wb = new XSSFWorkbook(file);

      XSSFSheet planilha = wb.getSheetAt(0);

      CellReference celula;
      PessoaExcel importado = new PessoaExcel();

      FormulaEvaluator ev = wb.getCreationHelper().createFormulaEvaluator();

      celula = new CellReference("B5");
      importado.nome = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("B6");
      importado.pai = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("B7");
      importado.mae = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("B8");
      importado.rg = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("F8");
      importado.cpf = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("C9");
      importado.dataNascimento = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("G9");
      importado.naturalidade = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("C10");
      importado.setEstadoCivil(Util.getCellValue(ev, celula, planilha));

      celula = new CellReference("F10");
      importado.conjuge = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("B11");
      importado.endereco = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("J11");
      importado.numero = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("B12");
      importado.bairro = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("F12");
      importado.cep = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("I12");
      importado.estado = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("B13");
      importado.cidade = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("B15");
      importado.telResDDD = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("C15");
      importado.telResNum = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("B16");
      importado.telComDDD = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("C16");
      importado.telComNum = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("B17");
      importado.telCelDDD = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("C17");
      importado.telCelNum = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("B18");
      importado.telCel2DDD = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("C18");
      importado.telCel2Num = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("G14");
      importado.email = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("G15");
      importado.facebook = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("G16");
      importado.profissao = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("I17");
      importado.trabalhaPosto = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("F18");
      importado.posto = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("D19");
      importado.associado = Util.getCellValue(ev, celula, planilha);

      celula = new CellReference("A21");
      importado.anotacoes = Util.getCellValue(ev, celula, planilha);

      System.out.println(importado);

      map.put("importada", importado);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return count;

  }
	
	// Manage dynamically added or removed telefones
    private List<Telefone> manageTelefones(Pessoa pessoa) {
        // Store the telefones which shouldn't be persisted
        List<Telefone> telefones2remove = new ArrayList<Telefone>();
        if (pessoa.getTelefones() != null) {
            for (Iterator<Telefone> i = pessoa.getTelefones().iterator(); i.hasNext();) {
            	Telefone telefone = i.next();
                // If the remove flag is true, remove the telefone from the list
                if (telefone != null && (telefone.getRemove() == 1 || telefone.getNumero() == null || telefone.getNumero().trim().equals("") )) {
                    telefones2remove.add(telefone);
                    i.remove();
                // Otherwise, perform the links
                } else {
                    //telefone.setPessoa(pessoa);
                }
            }
        }
        return telefones2remove;
    }
    
    private List<ContatoInternet> manageContatoInternets(Pessoa pessoa) {
    	// Store the emails which shouldn't be persisted
    	List<ContatoInternet> contatos2remove = new ArrayList<ContatoInternet>();
    	if (pessoa.getEmails() != null) {
    	  // Remove contatos
    		for (Iterator<ContatoInternet> i = pessoa.getEmails().iterator(); i.hasNext();) {
    			ContatoInternet email = i.next();
    			// If the remove flag is true, remove the email from the list
    			if (email != null && (email.getRemove() == 1 || email.getContato() == null || email.getContato().trim().equals("") )) {
    				contatos2remove.add(email);
    				i.remove();
    				// Otherwise, perform the links
    			} else {
    				email.isPrincipal();
    			}
    		}
    		
    		// Set principal
    		
    		ContatoInternet contatoPrincipal = null;
    		boolean principalLocalizado = false;
    		for (Iterator<ContatoInternet> i = pessoa.getEmails().iterator(); i.hasNext();) {
          ContatoInternet email = i.next();
           if(email.isPrincipal() && (email.getTipo() == TipoContatoInternet.EMAILCOM || email.getTipo() == TipoContatoInternet.EMAILPES)){
             contatoPrincipal = email;
             principalLocalizado = true;
             break;
           }
        }
    		
    		if(!principalLocalizado){
      		for (Iterator<ContatoInternet> i = pessoa.getEmails().iterator(); i.hasNext();) {
            ContatoInternet email = i.next();
             if(email.getTipo() == TipoContatoInternet.EMAILCOM || email.getTipo() == TipoContatoInternet.EMAILPES){
               contatoPrincipal = email;
               principalLocalizado = true;
               break;
             }
          }
    		}
    		
    		if(principalLocalizado){
          for (Iterator<ContatoInternet> i = pessoa.getEmails().iterator(); i.hasNext();) {
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

		Long totalDisplayRecords = pessoaService
				.countListRangePessoa(searchParameter);

		// Create page list data
		List<PessoaOptionForm> personsList = createPaginationData(
				searchParameter, sortCol, sortDir, pageNumber, pageDisplayLength);

		return new RestUtils<PessoaOptionForm>().createDatatablePaginateResponse(personsList, totalDisplayRecords.intValue(), totalDisplayRecords.intValue() );
    
	}

	private List<PessoaOptionForm> createPaginationData(
			String searchParameter, String sortCol, String sortDir, Integer pageNumber,
			Integer pageDisplayLength) {

		int startRange = 0 + (pageDisplayLength * (pageNumber - 1));
		
		if(sortCol.equals("0")){
			sortCol = "id";
		} else if(sortCol.equals("1")){
			sortCol = "nomeCompleto";
		} else if(sortCol.equals("3")){
			sortCol = "cidade";
		} else {
			sortCol = null;
		}

		List<Pessoa> lista = pessoaService.listRangePessoa(
				searchParameter, sortCol, sortDir, startRange, pageDisplayLength);
		List<PessoaOptionForm> retorno = new ArrayList<PessoaOptionForm>();
		for (Pessoa entidade : lista) {
		  PessoaOptionForm e = new PessoaOptionForm();
			e.setId(entidade.getId());
			e.setNome(entidade.getNomeCompleto());

			String email = entidade.getPrimeiroEmail();
			e.setEmail(email);

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
	
}
