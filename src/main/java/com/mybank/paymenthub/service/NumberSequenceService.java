package com.mybank.paymenthub.service;

import com.mybank.paymenthub.entity.NumberSequence;
import com.mybank.paymenthub.repository.NumberSequenceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class NumberSequenceService {

    private final NumberSequenceRepository numberSequenceRepository;

    public String generateOwnerNumber(){
        return generateNumber("OWNER");
    }
    public String generateMerchantNumber(){
        return generateNumber("MERCHANT");
    }

    public String generateOutletNumber(){
        return generateNumber("OUTLET");
    }

    public String generateTerminalNumber(){
        return generateNumber("TERMINAL");
    }

    public String generateContractNumber(){
        return generateNumber("CONTRACT");
    }

    public String generateTerminalAssignmentNumber(){
        return generateNumber("TERMINAL_ASSIGNMENT");
    }

    public String generateTransactionRef(){
        return generateNumber("TRANSACTION_REF");
    }

    public String generateTransactionNumber(){
        return generateNumber("TRANSACTION_NUMBER");
    }

    public String generateSettlementNumber(){
        return generateNumber("SETTLEMENT_NUMBER");
    }


    @Transactional
    public String generateNumber(String sequenceName){

        NumberSequence numberSequence =
                numberSequenceRepository.findForUpdate(sequenceName);

        Long nextNumber = numberSequence.getCurrentValue() + 1;

        numberSequence.setCurrentValue(nextNumber);
        numberSequenceRepository.save(numberSequence);


        return String.format("%08d", nextNumber);
    }
}