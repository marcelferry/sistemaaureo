package com.concafras.gestao.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.concafras.gestao.model.Anotacao;
import com.concafras.gestao.model.BaseEntidade;
import com.concafras.gestao.model.BaseInstituto;
import com.concafras.gestao.model.HistoricoMetaEntidade;
import com.concafras.gestao.model.MetaEntidade;
import com.concafras.gestao.model.MetaEntidadeAnotacao;
import com.concafras.gestao.model.MetaInstituto;
import com.concafras.gestao.model.Pessoa;
import com.concafras.gestao.model.PlanoMetas;
import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.rest.model.AnotacaoVO;
import com.concafras.gestao.rest.model.HistoricoMetaEntidadeVO;
import com.concafras.gestao.service.BaseInstitutoService;
import com.concafras.gestao.service.EntidadeService;
import com.concafras.gestao.service.MetaService;
import com.concafras.gestao.service.MetasInstitutoService;
import com.concafras.gestao.service.PessoaService;
import com.concafras.gestao.service.RodizioService;

@Service
public class PlanoMetasHelper {

	@Autowired
	private EntidadeService entidadeService;

	@Autowired
	private BaseInstitutoService baseInstitutoService;

	@Autowired
	private MetasInstitutoService metaInstitutoService;

	@Autowired
	private MetaService metaService;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private RodizioService rodizioService;

	@Autowired
	private MetasHelper metasHelper;

	public PlanoMetasHelper() {
		
	}

	public PlanoMetasForm mapPlanoMetasToPlanoMetasForm(PlanoMetas planoMetasAtual) {

		PlanoMetasForm planoMetasForm = new PlanoMetasForm();

		planoMetasForm.setId(planoMetasAtual.getId());
		planoMetasForm.setValidado(planoMetasAtual.isValidado());
		planoMetasForm.setFinalizado(planoMetasAtual.isFinalizado());
		planoMetasForm.setTipoContratante(planoMetasAtual.getTipoContratante());

		if (planoMetasAtual.getPresidente() != null) {
			planoMetasForm.setPresidente(new PessoaOptionForm(planoMetasAtual.getPresidente()));
		}
		planoMetasForm.setNomePresidente(planoMetasAtual.getNomePresidente());
		planoMetasForm.setEmailPresidente(planoMetasAtual.getEmailPresidente());
		planoMetasForm.setTelefonePresidente(planoMetasAtual.getTelefonePresidente());

		if (planoMetasAtual.getCoordenador() != null) {
			planoMetasForm.setCoordenador(new PessoaOptionForm(planoMetasAtual.getCoordenador()));
		}
		planoMetasForm.setNomeCoordenador(planoMetasAtual.getNomeCoordenador());
		planoMetasForm.setEmailCoordenador(planoMetasAtual.getEmailCoordenador());
		planoMetasForm.setTelefoneCoordenador(planoMetasAtual.getTelefoneCoordenador());

		if (planoMetasAtual.getContratante() != null) {
			planoMetasForm.setContratante(new PessoaOptionForm(planoMetasAtual.getContratante()));
		}
		planoMetasForm.setNomeContratante(planoMetasAtual.getNomeContratante());
		planoMetasForm.setEmailContratante(planoMetasAtual.getEmailContratante());
		planoMetasForm.setTelefoneContratante(planoMetasAtual.getTelefoneContratante());

		if (planoMetasAtual.getTipoContratante() == TipoContratante.OUTRO) {
			planoMetasForm.setOutro(new PessoaOptionForm(planoMetasAtual.getContratante()));
		}
		if (planoMetasAtual.getFacilitador() != null) {
			planoMetasForm.setFacilitador(new PessoaOptionForm(planoMetasAtual.getFacilitador()));
		}

		planoMetasForm.setRodizio(new RodizioVO(planoMetasAtual.getRodizio()));
		planoMetasForm.setInstituto(new InstitutoOptionForm(planoMetasAtual.getInstituto()));
		planoMetasForm.setEntidade(new EntidadeOptionForm(planoMetasAtual.getEntidade()));

		return planoMetasForm;
	}

	public void mapPlanoMetasAnotacoesToPlanoMetasFormAnotacoes(PlanoMetas planoMetasAtual,
			PlanoMetasForm planoMetasForm) {
		planoMetasForm.setAnotacoes(new ArrayList<AnotacaoVO>());
		if (planoMetasAtual.getAnotacoes() != null) {
			for (Anotacao anot : planoMetasAtual.getAnotacoes()) {
				AnotacaoVO nova = new AnotacaoVO(anot);
				planoMetasForm.getAnotacoes().add(nova);
			}
		}
	}

	public List<MetaForm> loadMetaForm(InstitutoOptionForm instituto, PessoaOptionForm facilitador,
			PessoaOptionForm contratante, EventoMeta evento, EntidadeOptionForm entidade, RodizioVO rodizio,
			boolean loadAtividade, boolean loadDependencias) {

		List<MetaForm> metasForm = null;

		List<MetaInstituto> metasIntituto = metaInstitutoService.listMetaInstitutoByInstitutoResumo(instituto.getId());

		Long countMetasEntidade = metaService.countListMetaEntidade(entidade.getId(), instituto.getId());

		// Primeiro Rodizio
		if (countMetasEntidade == 0) {
			metasForm = metasHelper.createMetaFormFromMetaInstituto(metasIntituto, instituto, facilitador, contratante,
					evento, entidade, rodizio, loadAtividade, loadDependencias);
		} else if (countMetasEntidade > 0) {
			metasForm = metasHelper.mapMetaEntidadeToMetaForm(metasIntituto, facilitador, contratante, evento, entidade,
					rodizio, loadAtividade, loadDependencias);
		} else {
			metasForm = new ArrayList<MetaForm>();
		}

		return metasForm;
	}

	public MetaForm loadMetaForm(MetaInstituto metaInstituto, InstitutoOptionForm instituto,
			PessoaOptionForm facilitador, PessoaOptionForm contratante, EventoMeta evento, EntidadeOptionForm entidade,
			RodizioVO rodizio, boolean loadAtividade, boolean loadDependencias) {

		MetaEntidade metasEntidade = metaService.findByEntidadeIdAndMetaInstitutoId(entidade.getId(),
				metaInstituto.getId());

		MetaForm metaForm = null;
		// Primeiro Rodizio
		if (metasEntidade == null) {
			metaForm = metasHelper.createMetaFormFromMetaInstituto(metaInstituto, instituto, facilitador, contratante,
					evento, entidade, rodizio, loadAtividade, loadDependencias);
		} else {
			metaForm = metasHelper.mapMetaEntidadeToMetaForm(metaInstituto, facilitador, contratante, evento, entidade,
					rodizio, loadAtividade, loadDependencias);
		}

		return metaForm;
	}

	public void restauraDependenciaCiclo(List<MetaForm> lista) {
		for (MetaForm metaForm : lista) {
			if (metaForm.getCiclo() != null && metaForm.getCiclo().getId() != null) {
				Rodizio rodVO = rodizioService.findById(metaForm.getCiclo().getId());
				metaForm.setCiclo(new RodizioVO(rodVO));
			}
			HistoricoMetaEntidadeVO hmeAnterior = metaForm.getSituacaoAnterior();
			if (hmeAnterior.getCiclo() != null && hmeAnterior.getCiclo().getId() != null) {
				Rodizio rodVO = rodizioService.findById(hmeAnterior.getCiclo().getId());
				hmeAnterior.setCiclo(new RodizioVO(rodVO));
			}
			HistoricoMetaEntidadeVO hmeAtual = metaForm.getSituacaoAtual();
			if (hmeAtual.getCiclo() != null && hmeAtual.getCiclo().getId() != null) {
				Rodizio rodVO = rodizioService.findById(hmeAtual.getCiclo().getId());
				hmeAtual.setCiclo(new RodizioVO(rodVO));
			}
			HistoricoMetaEntidadeVO hmeDesejada = metaForm.getSituacaoDesejada();
			if (hmeDesejada.getCiclo() != null && hmeDesejada.getCiclo().getId() != null) {
				Rodizio rodVO = rodizioService.findById(hmeDesejada.getCiclo().getId());
				hmeDesejada.setCiclo(new RodizioVO(rodVO));
			}
			if (metaForm.getDependencias() != null) {
				restauraDependenciaCiclo(metaForm.getDependencias());
			}
		}

	}

	public List<MetaEntidade> preparaMetas(PlanoMetasForm planoMetasForm, PlanoMetas plano) {

		List<MetaEntidade> metasAux = metaService.findByEntidadeIdAndInstitutoId(plano.getEntidade().getId(),
				plano.getInstituto().getId());

		plano.setMetas(metasAux);

		List<MetaEntidade> metas = new ArrayList<MetaEntidade>();

		processaMetas(planoMetasForm.getRodizio(), planoMetasForm.getEvento(), planoMetasForm.getDependencias(), metas,
				plano, null);

		return metas;
	}

	private void processaMetas(RodizioVO ciclo, EventoMeta evento, List<MetaForm> metasForm, List<MetaEntidade> metas,
			PlanoMetas plano, MetaEntidade pai) {
		metasForm.removeAll(Collections.singleton(null));

		for (MetaForm metaForm : metasForm) {
			List<HistoricoMetaEntidade> historicoAtual = null;
			List<HistoricoMetaEntidade> historicoAnterior = null;

			MetaEntidade meta = null;
			if (metaForm.getId() != null && metaForm.getId() > 0) {
				meta = metaService.findById(metaForm.getId()); 
				historicoAtual = metaService.findByMetaEntidadeIdAndRodizioId(meta.getId(), plano.getRodizio().getId());
				if (plano.getRodizio().getCicloAnterior() != null) {
					historicoAnterior = metaService.findByMetaEntidadeIdAndRodizioId(meta.getId(),
							plano.getRodizio().getCicloAnterior().getId());
				}
			} else {
				meta = new MetaEntidade();
				meta.setInstituto(plano.getInstituto());
				meta.setEntidade(plano.getEntidade());
				meta.setPrimeiroRodizio(plano.getRodizio());
				MetaInstituto metaInstituto = metaInstitutoService
						.getMetasInstituto(new Long(metaForm.getAtividade().getId()).intValue());
				meta.setPai(pai);
				meta.setMeta(metaInstituto);
				meta.setHistorico(new ArrayList<HistoricoMetaEntidade>());
			}

			HistoricoMetaEntidadeVO situacaoAtual = metaForm.getSituacaoAtual();

			if (situacaoAtual.getTipoSituacao() == TipoSituacaoMeta.INICIAL) {
				HistoricoMetaEntidade statusInicial = null;
				if (meta.getId() != null) {
					if (historicoAtual != null && historicoAtual.size() > 0) {
						for (HistoricoMetaEntidade historicoMetas : historicoAtual) {
							if (historicoMetas.getTipoSituacao() == TipoSituacaoMeta.INICIAL) {
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
						statusInicial.setConclusao(metaForm.getSituacaoAtual().getConclusao());
					}

					if (metaForm.getSituacaoAtual().getPrevisao() != null) {
						statusInicial.setPrevisao(metaForm.getSituacaoAtual().getPrevisao());
					}

					if (metaForm.getSituacaoAtual().getPrevisto() != null) {
						statusInicial.setPrevisto(metaForm.getSituacaoAtual().getPrevisto());
					}

					if (metaForm.getSituacaoAtual().getRealizado() != null) {
						statusInicial.setRealizado(metaForm.getSituacaoAtual().getRealizado());
					}
				}

				if (evento == EventoMeta.RODIZIO) {
					if (plano.getFacilitador() != null && plano.getFacilitador().getId() != null)
						statusInicial.setResponsavel(plano.getFacilitador());
				} else {
					if (plano.getTipoContratante() == TipoContratante.PRESIDENTE) {
						statusInicial.setResponsavel(plano.getPresidente());
					} else if (plano.getTipoContratante() == TipoContratante.COORDENADOR
							&& plano.getCoordenador() != null && plano.getCoordenador().getId() != null) {
						statusInicial.setResponsavel(plano.getCoordenador());
					} else if (plano.getTipoContratante() == TipoContratante.OUTRO && plano.getContratante() != null
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
							if (historicoMetas.getTipoSituacao() == TipoSituacaoMeta.AVALIAR) {
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
					if (plano.getFacilitador() != null && plano.getFacilitador().getId() != null)
						statusAvaliar.setResponsavel(plano.getFacilitador());
				} else {
					if (plano.getTipoContratante() == TipoContratante.PRESIDENTE) {
						statusAvaliar.setResponsavel(plano.getPresidente());
					} else if (plano.getTipoContratante() == TipoContratante.COORDENADOR
							&& plano.getCoordenador() != null && plano.getCoordenador().getId() != null) {
						statusAvaliar.setResponsavel(plano.getCoordenador());
					} else if (plano.getTipoContratante() == TipoContratante.OUTRO && plano.getContratante() != null
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
					&& TipoMeta.META_QUANTITATIVA.equals(metaForm.getAtividade().getTipoMeta())
					&& situacaoDesejada.getPrevisto() != null) {
				situacaoDesejada.setSituacao(SituacaoMeta.PLANEJADA);
			}

			// Situacao desejada pode ser apenas planejada/nao planejada
			if (situacaoDesejada.getSituacao() != null && (situacaoDesejada.getTipoSituacao() == acaoDefault
					|| situacaoDesejada.getTipoSituacao() == null)) {

				HistoricoMetaEntidade statusDesejado = null;
				if (meta.getId() != null) {
					statusDesejado = metasHelper.getUltimoHistorico(meta.getId(), plano.getRodizio().getId(),
							acaoDefault, true);
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
					if (plano.getFacilitador() != null && plano.getFacilitador().getId() != null)
						statusDesejado.setResponsavel(plano.getFacilitador());
				} else {
					if (plano.getContratante() != null && plano.getContratante().getId() != null)
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
					if (anot.getId() != null && anot.getCiclo() != null && anot.getCiclo().getId() != null) {
						cicloAux = new Rodizio();
						cicloAux.setId(anot.getCiclo().getId());
					} else if (anot.getId() == null) {
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

						if (novaNota != null && !novaNota.getTexto().equals(anot.getTexto())) {
							novaNota.setData(new Date());
							novaNota.setTexto(anot.getTexto());
						}

					} else {
						anotAux.setData(new Date());
						if (anot.getResponsavel().getId() != null) {
							if (evento == EventoMeta.RODIZIO) {
								if (plano.getFacilitador() != null && plano.getFacilitador().getId() != null)
									anotAux.setResponsavel(plano.getFacilitador());
							} else {
								if (plano.getTipoContratante() == TipoContratante.PRESIDENTE) {
									anotAux.setResponsavel(plano.getPresidente());
								} else if (plano.getTipoContratante() == TipoContratante.COORDENADOR
										&& plano.getCoordenador() != null && plano.getCoordenador().getId() != null) {
									anotAux.setResponsavel(plano.getCoordenador());
								} else if (plano.getTipoContratante() == TipoContratante.OUTRO
										&& plano.getContratante() != null && plano.getContratante().getId() != null) {
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
					if (anotNovaCorrigi.getResponsavel() != null && anotNovaCorrigi.getResponsavel().getId() == null)
						anotNovaCorrigi.setResponsavel(null);
				}
			}

			metas.add(meta);

			List<MetaForm> subAtividades = metaForm.getDependencias();

			if (subAtividades != null)
				processaMetas(ciclo, evento, subAtividades, metas, plano, meta);

		}

	}

	public void mapPlanoMetasFormToPlanoMetas(PlanoMetasForm planoMetasForm, PlanoMetas planoMetas) {
		if (planoMetas.getId() == null) {
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

		if (planoMetasForm.getRodizio() != null) {
			Rodizio rodizio = rodizioService.findById(planoMetasForm.getRodizio().getId());
			planoMetas.setRodizio(rodizio);
		}
		if (planoMetasForm.getInstituto() != null) {
			BaseInstituto instituto = baseInstitutoService.findById(planoMetasForm.getInstituto().getId());
			planoMetas.setInstituto(instituto);
		}
		if (planoMetasForm.getEntidade() != null) {
			BaseEntidade entidade = entidadeService.findById(planoMetasForm.getEntidade().getId());
			planoMetas.setEntidade(entidade);
		}

		if (planoMetasForm.getPresidente() != null && planoMetasForm.getPresidente().getId() != null) {
			Pessoa presidente = pessoaService.getPessoa(planoMetasForm.getPresidente().getId());
			planoMetas.setPresidente(presidente);
			planoMetas.setNomePresidente(planoMetasForm.getPresidente().getNome());
		}

		if (planoMetasForm.getCoordenador() != null && planoMetasForm.getCoordenador().getId() != null) {
			Pessoa coordenador = pessoaService.getPessoa(planoMetasForm.getCoordenador().getId());
			planoMetas.setCoordenador(coordenador);
			planoMetas.setNomeCoordenador(planoMetasForm.getCoordenador().getNome());
		}

		if (planoMetasForm.getTipoContratante() == TipoContratante.PRESIDENTE) {
			planoMetas.setContratante(planoMetas.getPresidente());
		}
		if (planoMetasForm.getTipoContratante() == TipoContratante.COORDENADOR
				&& planoMetasForm.getCoordenador() != null) {
			planoMetas.setContratante(planoMetas.getCoordenador());
		}
		if (planoMetasForm.getTipoContratante() == TipoContratante.OUTRO && planoMetasForm.getOutro() != null
				&& planoMetasForm.getOutro().getId() != null) {
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
							if (contratoEntity.getFacilitador() != null
									&& contratoEntity.getFacilitador().getId() != null)
								novaNota.setResponsavel(contratoEntity.getFacilitador());
						} else {
							if (contratoEntity.getContratante() != null
									&& contratoEntity.getContratante().getId() != null)
								novaNota.setResponsavel(contratoEntity.getContratante());
						}
						novaNota.setTexto(anot.getTexto());
					}
				} else {
					anot.setData(new Date());
					if (contratoForm.getEvento() == EventoMeta.RODIZIO) {
						if (contratoEntity.getFacilitador() != null && contratoEntity.getFacilitador().getId() != null)
							anot.setResponsavel(new PessoaOptionForm(contratoEntity.getFacilitador()));
					} else {
						if (contratoEntity.getContratante() != null && contratoEntity.getContratante().getId() != null)
							anot.setResponsavel(new PessoaOptionForm(contratoEntity.getContratante()));
					}
					Anotacao nova = new Anotacao();
					nova.setData(anot.getData());
					nova.setId(anot.getId());
					nova.setNivel(anot.getNivel());
					nova.setSinalizador(anot.getSinalizador());
					nova.setTexto(anot.getTexto());
					if (anot.getResponsavel() != null) {
						Pessoa pessoa = pessoaService.getPessoa(anot.getResponsavel().getId());
						nova.setResponsavel(pessoa);
					}
					contratoEntity.getAnotacoes().add(nova);
				}
			}
		}
	}
}
