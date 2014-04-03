package com.pmrodrigues.ellasa.pagamentos.v1.cart;

import java.text.DecimalFormat;
import java.util.Iterator;

import org.w3c.dom.Element;

import com.pmrodrigues.ellasa.pagamentos.AkatusOperation;
import com.pmrodrigues.ellasa.pagamentos.entity.Address;
import com.pmrodrigues.ellasa.pagamentos.entity.Holder;
import com.pmrodrigues.ellasa.pagamentos.entity.Payer;
import com.pmrodrigues.ellasa.pagamentos.entity.Phone;
import com.pmrodrigues.ellasa.pagamentos.entity.Product;
import com.pmrodrigues.ellasa.pagamentos.entity.Transaction;
import com.pmrodrigues.ellasa.pagamentos.v1.AkatusXMLRequestBuilder;

class CartXMLRequestBuilder extends AkatusXMLRequestBuilder {

	private DecimalFormat decimalFormatter = new DecimalFormat("#.00");
	
	public String build(AkatusOperation operation) {
		createDocument();
		createCarrinho((CartOperation) operation);

		return transformDocument();
	}

	private void createCarrinho(CartOperation operation) {
		final Element carrinhoElement = document.createElement("carrinho");

		carrinhoElement
				.appendChild(createAccountElement("recebedor", operation));
		carrinhoElement.appendChild(createPayerElement(operation));
		carrinhoElement.appendChild(createProductsElement(operation));
		carrinhoElement.appendChild(createTransactionElement(operation));

		document.appendChild(carrinhoElement);
	}

	private Element createPayerElement(CartOperation operation) {

		final Payer payer = operation.getPayer();
		final Element payerElement = document.createElement("pagador");

		payerElement.appendChild(createElementWithTextContent("nome",
				payer.getName()));
		payerElement.appendChild(createElementWithTextContent("email",
				payer.getEmail()));
		payerElement.appendChild(createAddressesElement(payer
				.getAddressIterator()));
		payerElement.appendChild(createPhonesElement(payer.getPhoneIterator()));

		return payerElement;
	}

	private Element createAddressesElement(Iterator<Address> addressIterator) {

		final Element addressesElement = document.createElement("enderecos");

		while (addressIterator.hasNext()) {
			addressesElement.appendChild(createAddressElement(addressIterator
					.next()));
		}

		return addressesElement;
	}

	private Element createAddressElement(Address address) {
		final Element addressElement = document.createElement("endereco");

		addressElement.appendChild(createElementWithTextContent("tipo", address
				.getType().toString()));
		addressElement.appendChild(createElementWithTextContent("logradouro",
				address.getStreet()));
		addressElement.appendChild(createElementWithTextContent("numero",
				address.getNumber().toString()));
		addressElement.appendChild(createElementWithTextContent("bairro",
				address.getNeighbourhood()));
		addressElement.appendChild(createElementWithTextContent("complemento",
				address.getComplement()));
		addressElement.appendChild(createElementWithTextContent("cidade",
				address.getCity()));
		addressElement.appendChild(createElementWithTextContent("estado",
				address.getState().toString()));
		addressElement.appendChild(createElementWithTextContent("pais", address
				.getCountry().toString()));
		addressElement.appendChild(createElementWithTextContent("cep",
				address.getZip()));

		return addressElement;
	}

	private Element createPhonesElement(Iterator<Phone> phoneIterator) {
		final Element phonesElement = document.createElement("telefones");

		while (phoneIterator.hasNext()) {
			phonesElement.appendChild(createPhoneElement(phoneIterator.next()));
		}

		return phonesElement;
	}

	private Element createPhoneElement(Phone phone) {
		final Element phoneElement = document.createElement("telefone");

		phoneElement.appendChild(createElementWithTextContent("tipo", phone
				.getType().toString()));
		phoneElement.appendChild(createElementWithTextContent("numero",
				phone.getNumber()));

		return phoneElement;
	}

	private Element createProductsElement(CartOperation operation) {
		final Element productsElement = document.createElement("produtos");

		for (final Iterator<Product> productIterator = operation
				.getProductIterator(); productIterator.hasNext();) {
			productsElement.appendChild(createProductElement(productIterator
					.next()));
		}

		return productsElement;
	}

	private Element createProductElement(Product product) {
		final Element productElement = document.createElement("produto");

		productElement.appendChild(createElementWithTextContent("codigo",
				product.getCode()));
		productElement.appendChild(createElementWithTextContent("descricao",
				product.getDescription()));
		productElement.appendChild(createElementWithTextContent("quantidade",
				product.getQuantity().toString()));
		productElement.appendChild(createElementWithTextContent("preco",
				decimalFormatter.format(product.getPrice())));
		productElement.appendChild(createElementWithTextContent("peso", 
				decimalFormatter.format(product.getWeight())));
		productElement.appendChild(createElementWithTextContent("frete",
				decimalFormatter.format(product.getShipping())));
		productElement.appendChild(createElementWithTextContent("desconto",
				decimalFormatter.format(product.getDiscount())));

		return productElement;
	}

	private Element createTransactionElement(CartOperation operation) {
		final Element transactionElement = document.createElement("transacao");
		final Transaction transaction = operation.getTransaction();
		final Transaction.PaymentMethod paymentMethod = transaction
				.getPaymentMethod();
		
		transactionElement.appendChild(createElementWithTextContent(
				"desconto", decimalFormatter.format(transaction.getDiscountAmount())));
		transactionElement.appendChild(createElementWithTextContent(
				"peso", decimalFormatter.format(transaction.getWeight())));
		transactionElement.appendChild(createElementWithTextContent(
				"frete", decimalFormatter.format(transaction.getShippingAmount())));
		transactionElement.appendChild(createElementWithTextContent("moeda",
				transaction.getCurrency().toString()));
		transactionElement.appendChild(createElementWithTextContent(
				"referencia", transaction.getReference()));

		transactionElement.appendChild(createElementWithTextContent(
				"meio_de_pagamento", paymentMethod.toString()));

		switch (paymentMethod) {
		case CARTAO_AMEX:
		case CARTAO_DINERS:
		case CARTAO_ELO:
		case CARTAO_MASTER:
		case CARTAO_VISA:
			transactionElement.appendChild(createElementWithTextContent(
					"numero", transaction.getNumber()));
			transactionElement.appendChild(createElementWithTextContent(
					"parcelas", transaction.getInstallments().toString()));
			transactionElement.appendChild(createElementWithTextContent(
					"codigo_de_seguranca", transaction.getSecurityNumber()));
			transactionElement.appendChild(createElementWithTextContent(
					"expiracao", transaction.getExpiration()));
			transactionElement.appendChild(createCreditCardHolder(transaction));
			break;
		default:
		}

		return transactionElement;
	}

	private Element createCreditCardHolder(Transaction transaction) {
		final Element creditCardHolderElement = document
				.createElement("portador");
		final Holder holder = transaction.getHolder();

		creditCardHolderElement.appendChild(createElementWithTextContent(
				"nome", holder.getName()));
		creditCardHolderElement.appendChild(createElementWithTextContent("cpf",
				holder.getDocument()));
		creditCardHolderElement.appendChild(createElementWithTextContent(
				"telefone", holder.getPhone()));

		return creditCardHolderElement;
	}
}