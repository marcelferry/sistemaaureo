/*
 * Created on 14 nov 2015 ( Time 11:49:18 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.concafras.gestao.service.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.concafras.gestao.model.MetaEntidade;
import com.concafras.gestao.rest.model.MetaEntidadeVO;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class MetaEntidadeServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public MetaEntidadeServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'MetaEntidadeVOEntity' to 'MetaEntidadeVO'
	 * @param metaEntidadeEntity
	 */
	public MetaEntidadeVO mapMetaEntidadeEntityToMetaEntidadeVO(MetaEntidade metaEntidadeEntity) {
		if(metaEntidadeEntity == null) {
			return null;
		}

		//--- Generic mapping 
		MetaEntidadeVO metaEntidade = map(metaEntidadeEntity, MetaEntidadeVO.class);

		return metaEntidade;
	}
	
	/**
	 * Mapping from 'MetaEntidadeVO' to 'MetaEntidadeEntity'
	 * @param metaEntidade
	 * @param metaEntidadeEntity
	 */
	public void mapMetaEntidadeToMetaEntidadeEntity(MetaEntidadeVO metaEntidade, MetaEntidade metaEntidadeEntity) {
		if(metaEntidade == null) {
			return;
		}

		//--- Generic mapping 
		map(metaEntidade, metaEntidadeEntity);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}