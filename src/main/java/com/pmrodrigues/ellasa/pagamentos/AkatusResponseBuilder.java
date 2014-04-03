package com.pmrodrigues.ellasa.pagamentos;

/**
 * Interface para definição de um construtor do objeto da resposta.
 * 
 * @author João Batista Neto
 */
public interface AkatusResponseBuilder {
	/**
	 * Constroi o objeto que representa a resposta.
	 * 
	 * @param response
	 *            é uma {@link String} que será utilizada para a construção do
	 *            objeto de resposta da operação.
	 * @return Uma instância de {@link AkatusResponse} que representa a resposta
	 *         da operação.
	 */
	AkatusResponse build(String response);
}