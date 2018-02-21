package com.concafras.gestao.web.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.concafras.gestao.enums.EventoMeta;
import com.concafras.gestao.enums.SituacaoMeta;
import com.concafras.gestao.enums.TipoSituacaoMeta;
import com.concafras.gestao.form.ContatoBasico;
import com.concafras.gestao.form.MetaForm;
import com.concafras.gestao.form.PessoaOptionForm;
import com.concafras.gestao.form.RodizioVO;
import com.concafras.gestao.helper.MetasHelper;
import com.concafras.gestao.model.Anotacao;
import com.concafras.gestao.model.ContatoInternet;
import com.concafras.gestao.model.HistoricoMetaEntidade;
import com.concafras.gestao.model.MetaEntidade;
import com.concafras.gestao.model.MetaEntidadeAnotacao;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.PlanoMetas;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.model.Telefone;
import com.concafras.gestao.rest.model.AnotacaoVO;
import com.concafras.gestao.rest.model.HistoricoMetaEntidadeVO;
import com.concafras.gestao.security.UsuarioAutenticado;
import com.concafras.gestao.service.MetaService;
import com.concafras.gestao.service.PessoaService;
import com.concafras.gestao.service.PlanoMetasService;

@Controller
@RequestMapping("/gestao/metas")
public class MetaController {

	private static final Logger logger = LoggerFactory.getLogger(MetaController.class);

	@Autowired
	private MetaService metaService;

	@Autowired
	private PlanoMetasService planoMetasService;

	@Autowired
	private PessoaService pessoaService;

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
					Date parsedDate = new SimpleDateFormat("MM/yyyy").parse(value);
					setValue(parsedDate);
				} catch (ParseException e) {
					setValue(null);
				}
			}

			@Override
			public String getAsText() {
				try {
					String parsedDate = new SimpleDateFormat("MM/yyyy").format((Date) getValue());
					return parsedDate;
				} catch (Exception e) {
					return "";
				}
			}
		});
	}

	@RequestMapping("/preview/{ciclo}/{id}")
	public ModelAndView previewMeta(@PathVariable Integer ciclo, @PathVariable Integer id, HttpServletRequest request,
			Authentication authentication) {
		return editarMeta("metas.preview", ciclo, id, request, authentication, false);
	}

	@RequestMapping("/edit/{ciclo}/{id}")
	public ModelAndView editarMeta(@PathVariable Integer ciclo, @PathVariable Integer id, HttpServletRequest request,
			Authentication authentication) {
		if ("ROLE_METAS_PRESIDENTE".equals(request.getSession().getAttribute("ROLE_CONTROLE"))) {
			return editarMeta("metas.editar", ciclo, id, request, authentication, true);
		} else {
			return editarMeta("metas.editar", ciclo, id, request, authentication, false);
		}
	}

	@RequestMapping(value = "/edit/save/{metaId}", method = RequestMethod.POST)
	public String editMeta(@ModelAttribute("metaForm") MetaForm meta, HttpServletRequest request,
			@PathVariable("metaId") Integer metaId, RedirectAttributes metaForm) {

		Rodizio ciclo = (Rodizio) request.getSession().getAttribute("CICLO_CONTROLE");

		meta.getDescricao();

		MetaEntidade metaEntity = metaService.findById(metaId);

		SituacaoMeta situacao = null;

		TipoSituacaoMeta sm = meta.getSituacaoDesejada().getTipoSituacao();
		if (sm.equals(TipoSituacaoMeta.REPLANEJAR)) {
			situacao = SituacaoMeta.REPLANEJADA;
		} else if (sm.equals(TipoSituacaoMeta.CONCLUIR)) {
			situacao = SituacaoMeta.IMPLANTADA;
		} else if (sm.equals(TipoSituacaoMeta.CONCLUIR_PARCIALMENTE)) {
			situacao = SituacaoMeta.IMPLPARCIAL;
		} else if (sm.equals(TipoSituacaoMeta.CANCELAR)) {
			situacao = SituacaoMeta.CANCELADA;
		}
		Pessoa responsavel = pessoaService.getPessoa(meta.getSituacaoDesejada().getResponsavel().getId());

		HistoricoMetaEntidade hme = new HistoricoMetaEntidade();
		hme.setRodizio(ciclo);
		hme.setMeta(metaEntity);
		hme.setNotificar(true);
		hme.setTipoSituacao(meta.getSituacaoDesejada().getTipoSituacao());
		hme.setDataSituacao(new Date());
		hme.setConclusao(meta.getSituacaoDesejada().getConclusao());
		hme.setPrevisao(meta.getSituacaoDesejada().getPrevisao());
		hme.setRealizado(meta.getSituacaoDesejada().getRealizado());
		hme.setPrevisto(meta.getSituacaoDesejada().getPrevisto());
		hme.setResponsavel(responsavel);
		hme.setSituacao(situacao);

		// TODO: REMOVER CAMPOS NAO NECESSARIOS
		// metaEntity.setTipoSituacao(meta.getSituacaoDesejada().getTipoSituacao());
		// metaEntity.setDataSituacao(new Date());
		// metaEntity.setConclusao(meta.getSituacaoDesejada().getConclusao());
		// metaEntity.setPrevisao(meta.getSituacaoDesejada().getPrevisao());
		// metaEntity.setRealizado(meta.getSituacaoDesejada().getRealizado());
		// metaEntity.setPrevisto(meta.getSituacaoDesejada().getPrevisto());
		// metaEntity.setResponsavel(responsavel);
		// metaEntity.setSituacao(situacao);
		//
		metaEntity.getHistorico().add(hme);

		// Pega o ultimo registro inserido na lista
		AnotacaoVO anot = meta.getObservacoes().get(meta.getObservacoes().size() - 1);
		Anotacao anotAux = new Anotacao();
		anotAux.setId(anot.getId());
		anotAux.setNivel(anot.getNivel());
		anotAux.setData(new Date());
		anotAux.setResponsavel(responsavel);
		anotAux.setSinalizador(anot.getSinalizador());
		anotAux.setTexto(anot.getTexto());
		MetaEntidadeAnotacao metaAnot = new MetaEntidadeAnotacao(metaEntity, ciclo, anotAux);
		metaEntity.getAnotacoes().add(metaAnot);

		metaService.update(metaEntity);

		return "redirect:/gestao/home/dashboard/listagem";
	}

	private ModelAndView editarMeta(String forward, Integer ciclo, Integer metaId, HttpServletRequest request,
			Authentication authentication, boolean editMode) {

		UserDetails user = (UserDetails) authentication.getPrincipal();

		Pessoa contratante = null;

		if (user instanceof UsuarioAutenticado) {
			UsuarioAutenticado usuario = (UsuarioAutenticado) user;

			contratante = usuario.getPessoa();
		}

		MetaForm form = new MetaForm();

		MetaEntidade meta = metaService.findById(metaId);

		// TODO: Obter plano a partir dos dados;
		PlanoMetas plano = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(meta.getEntidade().getId(),
				meta.getInstituto().getId(), ciclo);

		String rota = metaService.getCaminhoMeta(meta.getMeta().getId());

		form.setDescricaoCompleta(rota);

		HistoricoMetaEntidade hmeAtual = new MetasHelper(metaService, pessoaService).getUltimoHistorico(meta.getId(),
				plano.getRodizio().getId(), true);

		if (hmeAtual.getTipoSituacao() == TipoSituacaoMeta.PRECONTRATAR
				|| hmeAtual.getTipoSituacao() == TipoSituacaoMeta.CONCLUIR
				|| hmeAtual.getTipoSituacao() == TipoSituacaoMeta.CONCLUIR_PARCIALMENTE
				|| hmeAtual.getTipoSituacao() == TipoSituacaoMeta.CANCELAR) {
			editMode = false;
		}

		//FIX-ME
		form = new MetasHelper(metaService, pessoaService).preencheSituacaoDesejada(meta, form, EventoMeta.POSRODIZIO,
				new RodizioVO(plano.getRodizio()), true);

		form = new MetasHelper(metaService, pessoaService).preencheAnotacoes(meta, form, new PessoaOptionForm( contratante ), null, EventoMeta.POSRODIZIO,
				new RodizioVO(plano.getRodizio()), editMode);

		// PlanoMetasForm planoForm = new PlanoMetasForm(plano);

		List<ContatoBasico> contatos = processaContatosDoPlano(plano);

		for (HistoricoMetaEntidadeVO historicoMetaEntidade : form.getHistorico()) {
			if (historicoMetaEntidade.getResponsavel() != null) {
				if (plano.getFacilitador() != null) {
					if (historicoMetaEntidade.getResponsavel().getId() == plano.getFacilitador().getId()) {
						historicoMetaEntidade.setTipoResponsavel("Facil.");
						continue;
					}
				}
				if (plano.getPresidente() != null) {
					if (historicoMetaEntidade.getResponsavel().getId() == plano.getPresidente().getId()) {
						historicoMetaEntidade.setTipoResponsavel("Pres.");
						continue;
					}
				}
				if (plano.getCoordenador() != null) {
					if (historicoMetaEntidade.getResponsavel().getId() == plano.getCoordenador().getId()) {
						historicoMetaEntidade.setTipoResponsavel("Coord.");
						continue;
					}
				}
				if (plano.getContratante() != null) {
					if (historicoMetaEntidade.getResponsavel().getId() == plano.getContratante().getId()) {
						historicoMetaEntidade.setTipoResponsavel("Outro");
						continue;
					}
				}
			}
		}

		ModelAndView model = new ModelAndView(forward, "metaForm", form);

		model.addObject("listContatos", contatos);

		model.addObject("editMode", editMode);

		return model;
	}

	private List<ContatoBasico> processaContatosDoPlano(PlanoMetas plano) {

		Pessoa presidente = plano.getPresidente();
		Pessoa coordenador = plano.getCoordenador();
		Pessoa contratante = plano.getContratante();

		String nomePresidente = plano.getNomePresidente();
		String nomeCoordenador = plano.getNomeCoordenador();
		String nomeContratante = plano.getNomeContratante();
		String telefonePresidente = plano.getTelefonePresidente();
		String telefoneCoordenador = plano.getTelefoneCoordenador();
		String telefoneContratante = plano.getTelefoneContratante();
		String emailPresidente = plano.getEmailPresidente();
		String emailCoordenador = plano.getEmailCoordenador();
		String emailContratante = plano.getEmailContratante();

		List<ContatoBasico> contatos = new ArrayList<ContatoBasico>();

		// Add presidente
		ContatoBasico novocontato = new ContatoBasico("Presidente");
		if (presidente != null) {
			novocontato.setNomeCompleto(presidente.getNomeCompleto());

			for (Telefone tel : presidente.getTelefones()) {
				novocontato.addTelefone(tel.getDdd() + " " + tel.getNumero()
						+ (tel.getOperadora() != null ? " " + tel.getOperadora() : ""));
			}
			for (ContatoInternet inter : presidente.getEmails()) {
				novocontato.addEmail(inter.getTipo() + " " + inter.getContato());
			}

			if (nomePresidente != null && nomePresidente.trim().length() > 0) {
				if (presidente.getNomeCompleto().trim().equals(nomePresidente.trim())) {
					novocontato.addTelefone(telefonePresidente);
					novocontato.addEmail(emailPresidente);
				}
			}

			contatos.add(novocontato);

		} else if (nomePresidente != null && nomePresidente.trim().length() > 0) {
			novocontato.setNomeCompleto(nomePresidente);
			novocontato.addTelefone(telefonePresidente);
			novocontato.addEmail(emailPresidente);
			contatos.add(novocontato);
		}
		// Add coordenador
		novocontato = new ContatoBasico("Coordenador");
		if (coordenador != null) {
			novocontato.setNomeCompleto(coordenador.getNomeCompleto());

			for (Telefone tel : coordenador.getTelefones()) {
				novocontato.addTelefone(tel.getDdd() + " " + tel.getNumero()
						+ (tel.getOperadora() != null ? " " + tel.getOperadora() : ""));
			}
			for (ContatoInternet inter : coordenador.getEmails()) {
				novocontato.addEmail(inter.getTipo() + " " + inter.getContato());
			}

			if (nomeCoordenador != null && nomeCoordenador.trim().length() > 0) {
				if (coordenador.getNomeCompleto().trim().equals(nomeCoordenador.trim())) {
					novocontato.addTelefone(telefoneCoordenador);
					novocontato.addEmail(emailCoordenador);
				}
			}
			contatos.add(novocontato);

		} else if (nomeCoordenador != null && nomeCoordenador.trim().length() > 0) {
			novocontato.setNomeCompleto(nomeCoordenador);
			novocontato.addTelefone(telefoneCoordenador);
			novocontato.addEmail(emailCoordenador);
			contatos.add(novocontato);
		}
		// Add contratante
		novocontato = new ContatoBasico("Contratante");
		if (contratante != null && !contratante.getId().equals(presidente.getId())
				&& !contratante.getId().equals(coordenador.getId())) {
			novocontato.setNomeCompleto(contratante.getNomeCompleto());

			for (Telefone tel : contratante.getTelefones()) {
				novocontato.addTelefone(tel.getDdd() + " " + tel.getNumero()
						+ (tel.getOperadora() != null ? " " + tel.getOperadora() : ""));
			}
			for (ContatoInternet inter : contratante.getEmails()) {
				novocontato.addEmail(inter.getTipo() + " " + inter.getContato());
			}

			if (nomeContratante != null && nomeContratante.trim().length() > 0) {
				if (contratante.getNomeCompleto().trim().equals(nomeContratante.trim())) {
					novocontato.addTelefone(telefoneContratante);
					novocontato.addEmail(emailContratante);
				}
			}
			contatos.add(novocontato);

		} else if (nomeContratante != null && nomeContratante.trim().length() > 0) {
			novocontato.setNomeCompleto(nomeContratante);
			novocontato.addTelefone(telefoneContratante);
			novocontato.addEmail(emailContratante);
			contatos.add(novocontato);
		}

		return contatos;

	}

}
