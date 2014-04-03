package com.pmrodrigues.ellasa.pagamentos;

/**
 * Interface para definição de um construtor do corpo da requisição.
 * 
 * @author João Batista Neto
 */
public interface AkatusRequestBuilder {
	/**
	 * @return Uma {@link String} que representa o Content-Type do corpo da
	 *         requisição.
	 */
	String getContentType();

	/**
	 * Constroi o corpo da requisição.
	 * 
	 * @param operation
	 *            é uma {@link AkatusOperation} que terá o corpo da requisição
	 *            construído.
	 * @return A {@link String} que representa o corpo que será enviado na
	 *         requisição.
	 */
	String build(AkatusOperation operation);
}