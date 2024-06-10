package com.rogih.alunoonlineapi.controller;

import com.rogih.alunoonlineapi.dtos.InvoiceRequest;
import com.rogih.alunoonlineapi.model.Invoice;
import com.rogih.alunoonlineapi.service.InvoiceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/{studentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice create(@RequestBody InvoiceRequest invoiceRequest,
                            @PathVariable Long studentId) {
        log.info("Controller invoice payment student={} invoice={}", studentId, invoiceRequest);
        return invoiceService.create(studentId, invoiceRequest);
    }
}
