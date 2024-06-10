package com.rogih.alunoonlineapi.service;

import com.rogih.alunoonlineapi.dtos.InvoiceRequest;
import com.rogih.alunoonlineapi.model.Invoice;
import com.rogih.alunoonlineapi.model.StudentFinance;
import com.rogih.alunoonlineapi.repository.InvoiceRepository;
import com.rogih.alunoonlineapi.repository.StudentFinanceRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
@Service
public class InvoiceService {

    @Autowired
    StudentFinanceRepository studentFinanceRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    public Invoice create(Long studentId,InvoiceRequest invoiceRequest) {
        log.info("Service create invoice studentId={} InvoiceRequest={}", studentId, invoiceRequest);

        Optional<StudentFinance> studentFinanceDB = studentFinanceRepository.findByStudentId(studentId);
        if(studentFinanceDB.isPresent()){
            Invoice invoiceForSave = new Invoice(
                    null,
                    studentFinanceDB.get(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    invoiceRequest.getPaymentAmount()
            );
            Invoice invoiceSaved = invoiceRepository.save(invoiceForSave);
            log.info("Dados da fatura= {}", invoiceSaved);
            return invoiceSaved;
        }else {
            log.info("Falha ao salvar os dados de pagamento da fatura. studentId={} Invoice={}", studentId, invoiceRequest);
            return null;
        }

    }
}
