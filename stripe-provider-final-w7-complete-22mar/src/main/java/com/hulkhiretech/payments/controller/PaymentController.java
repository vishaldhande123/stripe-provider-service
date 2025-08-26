package com.hulkhiretech.payments.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payments.dto.CreatePaymentDTO;
import com.hulkhiretech.payments.dto.PaymentDTO;
import com.hulkhiretech.payments.pojo.CreatePaymentReq;
import com.hulkhiretech.payments.pojo.PaymentRes;
import com.hulkhiretech.payments.service.interfaces.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	private final ModelMapper modelMapper;

	/**
	 * Endpoint: http://localhost:8083/v1/payments
	 * Sample Request Body:
	 * 
	  {
		  "successUrl": "https://example.com/success",
		  "cancelUrl": "https://example.com/cancel",
		  "lineItems": [
		    {
		      "quantity": 5,
		      "currency": "EUR",
		      "productName": "Mechanical Keyboard",
		      "unitAmount": 1000
		    }
		  ]
		}

	 * @param createPaymentReq
	 * @return
	 */
	@PostMapping
	public ResponseEntity<PaymentRes> createPayment(
			@RequestBody CreatePaymentReq createPaymentReq) {
		log.info("invoked createPayment||createPaymentReq:" + createPaymentReq);

		CreatePaymentDTO paymentDTO = modelMapper.map(createPaymentReq, CreatePaymentDTO.class);

		log.info("Converted to DTO paymentDTO:" + paymentDTO);

		PaymentDTO response = paymentService.createPayment(paymentDTO);

		PaymentRes paymentRes = modelMapper.map(response, PaymentRes.class);

		log.info("returning paymentRes:" + paymentRes);
		return new ResponseEntity<>(paymentRes, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PaymentRes> getPayment(@PathVariable String id)  {
		log.info("invoked getPayment|| id:" + id);

		// to receive & convert to dto & pass DTO to service - THIs is NOT APPLICABLE.
		// NOT APPLICABLE

		// call the service
		PaymentDTO response = paymentService.getPayment(id);

		PaymentRes paymentRes = modelMapper.map(response, PaymentRes.class);

		log.info("returning paymentRes:" + paymentRes);
		return new ResponseEntity<>(paymentRes, HttpStatus.OK);
	}

	//TODO complete the expire payment logic
	@PostMapping("/{id}/expire")
	public String expirePayment(@PathVariable String id)  {
		log.info("invoked expirePayment|| id:" + id);

		return "Payment expired||" + id;
	}



}
