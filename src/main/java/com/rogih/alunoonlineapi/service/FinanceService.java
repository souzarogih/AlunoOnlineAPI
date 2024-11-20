package com.rogih.alunoonlineapi.service;

import com.rogih.alunoonlineapi.model.Invoice;
import com.rogih.alunoonlineapi.model.StudentFinance;
import com.rogih.alunoonlineapi.repository.InvoiceRepository;
import com.rogih.alunoonlineapi.repository.StudentFinanceRepository;
import com.rogih.alunoonlineapi.utils.DueDate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Log4j2
@Service
public class FinanceService {

    private static final Integer QUANTITY_OF_DAYS_BEFORE_GENERATE = 10;

    @Autowired
    StudentFinanceRepository studentFinanceRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    // cron a cada minuto:
//     @Scheduled(cron = "0 * * * * ?")
    //cron a cada 10 segundos
    @Scheduled(cron = "*/10 * * * * *")
    // cron de meia noite:
//    @Scheduled(cron = "0 0 0 * * ?")
    public void faturaGeneration() {
        log.info("Iniciando a geração de faturas... {}", LocalDateTime.now());

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime generationDeadline = currentDate.plusDays(QUANTITY_OF_DAYS_BEFORE_GENERATE);

        log.info("Buscando todos os registros financeiros do aluno");
        List<StudentFinance> studentFinances = studentFinanceRepository.findAll();

        for (StudentFinance studentFinance : studentFinances) {
            Integer dueDay = studentFinance.getDueDate();

            if (dueDay != null) {
                log.info("Calculando a data de vencimento do mês atual");
                LocalDate dueDateCurrentMonth = DueDate.calculateDueDate(dueDay, currentDate.getYear(), currentDate.getMonthValue());

                // Se a data de vencimento do mês atual já passou, calcular a data de vencimento do próximo mês
                if (dueDateCurrentMonth.isBefore(currentDate.toLocalDate())) {
                    dueDateCurrentMonth = DueDate.calculateDueDate(dueDay, currentDate.getYear(), currentDate.getMonthValue() + 1);
                }

                // Verificar se falta 10 dias ou menos para a data de vencimento
                if (dueDateCurrentMonth != null && (dueDateCurrentMonth.isBefore(generationDeadline.toLocalDate()) || dueDateCurrentMonth.isEqual(generationDeadline.toLocalDate()))) {
                     log.info("Verificar se já existe uma invoice para este aluno e data de vencimento");
                    if (invoiceRepository.existsByStudentFinancialAndDueDate(studentFinance, dueDateCurrentMonth.atTime(LocalTime.MIDNIGHT))) {
                        log.info("Fatura já existe para o aluno: {} com data de vencimento: {}", studentFinance.getId(), dueDateCurrentMonth);
                        continue;
                    }

                    log.info("Gerando invoice para o aluno: {}", studentFinance.getId());

                    Invoice invoice = new Invoice();
                    invoice.setStudentFinancial(studentFinance);
                    invoice.setDueDate(dueDateCurrentMonth.atTime(LocalTime.MIDNIGHT));
                    invoice.setCreatedAt(currentDate);

                    invoiceRepository.save(invoice);

                    log.info("Fatura gerada para o aluno: {} com data de vencimento: {}", studentFinance.getId(), dueDateCurrentMonth);

                }
            }

            log.info("Geração de faturas concluída.");
        }

    }


}
