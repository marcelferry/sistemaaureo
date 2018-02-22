package com.concafras.gestao.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.concafras.gestao.enums.EventoMeta;
import com.concafras.gestao.form.EntidadeOptionForm;
import com.concafras.gestao.form.InstitutoOptionForm;
import com.concafras.gestao.form.MetaForm;
import com.concafras.gestao.form.PessoaOptionForm;
import com.concafras.gestao.form.PlanoMetasForm;
import com.concafras.gestao.form.RodizioVO;
import com.concafras.gestao.helper.MetasHelper;
import com.concafras.gestao.helper.PlanoMetasHelper;
import com.concafras.gestao.model.BaseEntidade;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.MetaEntidade;
import com.concafras.gestao.model.MetaInstituto;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.PlanoMetas;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.model.view.StatusAtualInstitutoGraphicData;
import com.concafras.gestao.rest.model.PessoaVO;
import com.concafras.gestao.service.BaseInstitutoService;
import com.concafras.gestao.service.EntidadeService;
import com.concafras.gestao.service.MetaService;
import com.concafras.gestao.service.MetasInstitutoService;
import com.concafras.gestao.service.PessoaService;
import com.concafras.gestao.service.PlanoMetasService;
import com.concafras.gestao.service.RodizioService;
import com.concafras.gestao.service.mapper.PessoaServiceMapper;

@RestController
public class PlanoMetasRestController {

	@Autowired
	PlanoMetasService planoMetasService;

	@Autowired
	private MetaService metaService;

	@Autowired
	private MetasInstitutoService metaInstitutoService;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private EntidadeService entidadeService;

	@Autowired
	private BaseInstitutoService institutoService;

	@Autowired
	private RodizioService rodizioService;

	@Autowired
	private PessoaServiceMapper pessoaServiceMapper;

	@RequestMapping(value = "/api/v1/teste", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody PessoaVO teste() {
		Pessoa pessoa = pessoaService.getPessoa(1);
		PessoaVO pessoaVO = pessoaServiceMapper.mapPessoaOptionFormEntityToPessoaOptionForm(pessoa);
		return pessoaVO;
	}

	@RequestMapping(value = "/api/v1/planometas/ciclo/{ciclo}/entidade/{entidade}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody List<PlanoMetasForm> listarPlanoMetas(HttpServletRequest request,
			@PathVariable("ciclo") int ciclo, @PathVariable("entidade") int entidade) {

		EventoMeta evento = EventoMeta.PRERODIZIO;

		List<BaseInstituto> listaInstituto = institutoService.findByRodizioOverview(true);

		List<PlanoMetasForm> lista = new ArrayList<PlanoMetasForm>();

		List<StatusAtualInstitutoGraphicData> atual = planoMetasService.getStatusAtualGraphicData(ciclo, entidade);
		List<StatusAtualInstitutoGraphicData> desejado = planoMetasService.getStatusPlanejadoGraphicData(ciclo,
				entidade);

		for (BaseInstituto institutoCarregado : listaInstituto) {
			PlanoMetasForm plano = loadPlanoMetas(entidade, institutoCarregado.getId(), ciclo, evento, false);
			for (StatusAtualInstitutoGraphicData statusAtualInstitutoGraphicData : atual) {
				if (plano.getInstituto().getId() == statusAtualInstitutoGraphicData.getIdinstituto()) {
					plano.setSituacaoAtual(statusAtualInstitutoGraphicData);
				}
			}
			for (StatusAtualInstitutoGraphicData statusAtualInstitutoGraphicData : desejado) {
				if (plano.getInstituto().getId() == statusAtualInstitutoGraphicData.getIdinstituto()) {
					plano.setSituacaoDesejada(statusAtualInstitutoGraphicData);
				}
			}
			lista.add(plano);
		}

		return lista;

	}

	@RequestMapping(value = "/api/v1/planometas/ciclo/{ciclo}/entidade/{entidade}/instituto/{instituto}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody List<MetaForm> listarPlanoMetasPorInstituto(HttpServletRequest request,
			@PathVariable("ciclo") int ciclo, @PathVariable("entidade") int entidade,
			@PathVariable("instituto") int instituto) {

		EventoMeta evento = EventoMeta.PRERODIZIO;

		// Verificar existencia do plano para esse ciclo
		PlanoMetas planoLoaded = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade, instituto,
				ciclo);

		BaseEntidade entidadeLoaded = entidadeService.findById(entidade);
		BaseInstituto institutoLoaded = institutoService.findById(instituto);
		Rodizio rodizioLoaded = rodizioService.findById(ciclo);

		InstitutoOptionForm institutoForm = new InstitutoOptionForm(institutoLoaded);
		EntidadeOptionForm entidadeForm = new EntidadeOptionForm(entidadeLoaded);
		RodizioVO rodizioForm = new RodizioVO(rodizioLoaded, true, false);
		PessoaOptionForm facilitadorForm = null;
		PessoaOptionForm contratanteForm = null;

		// Nao existe plano de metas?
		if (planoLoaded != null) {
			if (planoLoaded.getFacilitador() != null)
				facilitadorForm = new PessoaOptionForm(planoLoaded.getFacilitador());
			if (planoLoaded.getContratante() != null)
				contratanteForm = new PessoaOptionForm(planoLoaded.getContratante());
		}

		List<MetaForm> dependencias = new PlanoMetasHelper(metaService, metaInstitutoService, pessoaService)
				.loadMetaForm(institutoForm, facilitadorForm, contratanteForm, evento, entidadeForm, rodizioForm, false,
						false);

		return dependencias;

	}

	@RequestMapping(value = "/api/v1/metas/ciclo/{ciclo}/entidade/{entidade}/instituto/{instituto}/meta/{meta}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody MetaForm listarMeta(HttpServletRequest request, @PathVariable("ciclo") int ciclo,
			@PathVariable("entidade") int entidade, @PathVariable("instituto") int instituto,
			@PathVariable("meta") int meta) {

		EventoMeta evento = EventoMeta.PRERODIZIO;

		// Verificar existencia do plano para esse ciclo
		PlanoMetas planoLoaded = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade, instituto,
				ciclo);

		BaseEntidade entidadeLoaded = entidadeService.findById(entidade);
		BaseInstituto institutoLoaded = institutoService.findById(instituto);
		Rodizio rodizioLoaded = rodizioService.findById(ciclo);
		MetaInstituto metaInstitutoLoaded = metaInstitutoService.getMetasInstituto(meta);

		InstitutoOptionForm institutoForm = new InstitutoOptionForm(institutoLoaded);
		EntidadeOptionForm entidadeForm = new EntidadeOptionForm(entidadeLoaded);
		RodizioVO rodizioForm = new RodizioVO(rodizioLoaded);
		PessoaOptionForm facilitadorForm = null;
		PessoaOptionForm contratanteForm = null;

		// Nao existe plano de metas?
		if (planoLoaded != null) {
			if (planoLoaded.getFacilitador() != null)
				facilitadorForm = new PessoaOptionForm(planoLoaded.getFacilitador());
			if (planoLoaded.getContratante() != null)
				contratanteForm = new PessoaOptionForm(planoLoaded.getContratante());
		}

		MetaForm metaForm = new PlanoMetasHelper(metaService, metaInstitutoService, pessoaService).loadMetaForm(
				metaInstitutoLoaded, institutoForm, facilitadorForm, contratanteForm, evento, entidadeForm, rodizioForm,
				false, false);

		return metaForm;

	}

	@RequestMapping(value = "/api/v1/metas/ciclo/{ciclo}/entidade/{entidade}/instituto/{instituto}/meta/{meta}/details", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody List<MetaForm> listarMetaDependencias(HttpServletRequest request,
			@PathVariable("ciclo") int ciclo, @PathVariable("entidade") int entidade,
			@PathVariable("instituto") int instituto, @PathVariable("meta") int meta) {

		EventoMeta evento = EventoMeta.PRERODIZIO;

		// Verificar existencia do plano para esse ciclo
		PlanoMetas planoLoaded = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade, instituto,
				ciclo);

		BaseEntidade entidadeLoaded = entidadeService.findById(entidade);
		BaseInstituto institutoLoaded = institutoService.findById(instituto);
		Rodizio rodizioLoaded = rodizioService.findById(ciclo);
		MetaInstituto metaInstitutoLoaded = metaInstitutoService.getMetasInstituto(meta);

		InstitutoOptionForm institutoForm = new InstitutoOptionForm(institutoLoaded);
		EntidadeOptionForm entidadeForm = new EntidadeOptionForm(entidadeLoaded);
		RodizioVO rodizioForm = new RodizioVO(rodizioLoaded);
		PessoaOptionForm facilitadorForm = null;
		PessoaOptionForm contratanteForm = null;

		// Nao existe plano de metas?
		if (planoLoaded != null) {
			if (planoLoaded.getFacilitador() != null)
				facilitadorForm = new PessoaOptionForm(planoLoaded.getFacilitador());
			if (planoLoaded.getContratante() != null)
				contratanteForm = new PessoaOptionForm(planoLoaded.getContratante());
		}

		List<MetaForm> dependencias = new MetasHelper(metaService, pessoaService).mapMetaEntidadeToMetaForm(
				metaInstitutoLoaded.getItens(), facilitadorForm, contratanteForm, evento, entidadeForm, rodizioForm,
				false, false);

		return dependencias;

	}

	private PlanoMetasForm loadPlanoMetas(Integer entidade, Integer instituto, Integer ciclo, EventoMeta evento,
			boolean loadMetas) {

		// Verificar existencia do plano para esse ciclo
		PlanoMetas planoLoaded = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade, instituto,
				ciclo);

		BaseEntidade entidadeLoaded = entidadeService.findById(entidade);
		BaseInstituto institutoLoaded = institutoService.findById(instituto);
		Rodizio rodizioLoaded = rodizioService.findById(ciclo);
		Long prioridades = metaService.countListMetaEntidadePrioridade(instituto);

		// Nao existe plano de metas?
		if (planoLoaded == null) {
			planoLoaded = new PlanoMetas();
			planoLoaded.setEntidade(entidadeLoaded);
			planoLoaded.setInstituto(institutoLoaded);
			planoLoaded.setRodizio(rodizioLoaded);
		}

		PlanoMetasForm plano = new PlanoMetasHelper(metaService, metaInstitutoService, pessoaService)
				.mapPlanoMetasToPlanoMetasForm(planoLoaded);

		plano.setEvento(evento);
		plano.setPrioridades(prioridades);
		plano.setFase(3);

		new PlanoMetasHelper(metaService, metaInstitutoService, pessoaService)
				.mapPlanoMetasAnotacoesToPlanoMetasFormAnotacoes(planoLoaded, plano);

		if (loadMetas) {
			List<MetaForm> dependencias = new PlanoMetasHelper(metaService, metaInstitutoService, pessoaService)
					.loadMetaForm(plano.getInstituto(), plano.getFacilitador(), plano.getContratante(),
							plano.getEvento(), plano.getEntidade(), plano.getRodizio(), true, true);

			plano.setDependencias(dependencias);
		}

		return plano;
	}

	private List<MetaForm> loadMetas(Integer entidade, Integer instituto, Integer ciclo, EventoMeta evento) {

		// Verificar existencia do plano para esse ciclo
		PlanoMetas planoLoaded = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade, instituto,
				ciclo);

		BaseEntidade entidadeLoaded = entidadeService.findById(entidade);
		BaseInstituto institutoLoaded = institutoService.findById(instituto);
		Rodizio rodizioLoaded = rodizioService.findById(ciclo);
		Long prioridades = metaService.countListMetaEntidadePrioridade(instituto);

		// Nao existe plano de metas?
		if (planoLoaded == null) {
			planoLoaded = new PlanoMetas();
			planoLoaded.setEntidade(entidadeLoaded);
			planoLoaded.setInstituto(institutoLoaded);
			planoLoaded.setRodizio(rodizioLoaded);
		}

		PlanoMetasForm plano = new PlanoMetasHelper(metaService, metaInstitutoService, pessoaService)
				.mapPlanoMetasToPlanoMetasForm(planoLoaded);

		plano.setEvento(evento);
		plano.setPrioridades(prioridades);
		plano.setFase(3);

		List<MetaForm> dependencias = new PlanoMetasHelper(metaService, metaInstitutoService, pessoaService)
				.loadMetaForm(plano.getInstituto(), plano.getFacilitador(), plano.getContratante(), plano.getEvento(),
						plano.getEntidade(), plano.getRodizio(), true, true);

		return dependencias;
	}

	@RequestMapping(value = "/api/v1/planometas/print/ciclo/{ciclo}/entidade/{entidade}/instituto/{instituto}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody PlanoMetasForm listarMetasPorInstituto(HttpServletRequest request,
			@PathVariable("ciclo") int ciclo, @PathVariable("entidade") int entidade,
			@PathVariable("instituto") int instituto) {

		List<PlanoMetasForm> retorno = new ArrayList<PlanoMetasForm>();

		BaseEntidade baseEntidade = entidadeService.findById(entidade);
		BaseInstituto baseInstitulo = institutoService.findById(instituto);
		RodizioVO rodizio = new RodizioVO(rodizioService.findById(ciclo));

		PlanoMetasForm plano = preparaPlanoMetasForm(baseEntidade, baseInstitulo, rodizio, null);
		retorno.add(plano);

		return plano;

	}

	private PlanoMetasForm preparaPlanoMetasForm(BaseEntidade entidade, BaseInstituto instituto, RodizioVO rodizio,
			Pessoa facilitador) {

		PlanoMetasForm planoMetasForm = new PlanoMetasForm();

		if (entidade != null) {
			planoMetasForm.setEntidade(new EntidadeOptionForm(entidade));
		}

		PessoaOptionForm pessoaFacilitador = new PessoaOptionForm(facilitador);

		planoMetasForm.setFacilitador(pessoaFacilitador);

		planoMetasForm.setRodizio(rodizio);
		if (instituto != null) {
			planoMetasForm.setInstituto(new InstitutoOptionForm(instituto));
		}
		planoMetasForm.setEvento(planoMetasForm.getEvento());

		List<MetaForm> metasForm = null;

		List<MetaInstituto> metasIntituto = metaInstitutoService.listMetaInstitutoByInstituto(instituto.getId(), true);

		PlanoMetas planoMetasAtual = planoMetasService.findByEntidadeIdAndInstitutoIdAndRodizioId(entidade.getId(),
				instituto.getId(), rodizio.getId());

		List<MetaEntidade> listaMetas = metaService.findByEntidadeIdAndInstitutoId(entidade.getId(), instituto.getId());

		if (planoMetasAtual != null) {
			planoMetasForm.setId(planoMetasAtual.getId());
			planoMetasForm.setTipoContratante(planoMetasAtual.getTipoContratante());
			PessoaOptionForm pessoaContratante = new PessoaOptionForm(planoMetasAtual.getContratante());
			planoMetasForm.setContratante(pessoaContratante);
			Pessoa presidente = pessoaService
					.getPessoa(planoMetasAtual.getEntidade().getPresidente().getPessoa().getId());
			PessoaOptionForm pessoaPresidente = new PessoaOptionForm(presidente);
			planoMetasForm.setPresidente(pessoaPresidente);

			planoMetasAtual.setMetas(listaMetas);
		}

		if (planoMetasAtual == null) {
			metasForm = new MetasHelper(metaService, pessoaService).createMetaFormFromMetaInstituto(metasIntituto,
					planoMetasForm.getFacilitador(), planoMetasForm.getContratante(), planoMetasForm.getEvento(),
					planoMetasForm.getRodizio(), true);

		} else if (planoMetasAtual.getMetas().size() > 0) {

			metasForm = new MetasHelper(metaService, pessoaService).mapMetaEntidadeToMetaForm(metasIntituto,
					planoMetasForm.getFacilitador(), planoMetasForm.getContratante(), planoMetasForm.getEvento(),
					planoMetasForm.getEntidade(), planoMetasForm.getRodizio(), true, true);
		} else {
			metasForm = new ArrayList<MetaForm>();
		}

		planoMetasForm.setDependencias(metasForm);

		planoMetasForm.setFase(3);

		return planoMetasForm;
	}
}
