package br.com.carteira.cliente.util;

import org.modelmapper.ModelMapper;

public class ClassUtil<C> {

	private static ModelMapper modelMapper = new ModelMapper();

	public static <D> D convert(Object source, Class<D> destinationType) {
		return modelMapper.map(source, destinationType);
	}

}
