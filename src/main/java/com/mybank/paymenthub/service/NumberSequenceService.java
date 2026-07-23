package com.mybank.paymenthub.service;

import com.mybank.paymenthub.entity.NumberSequence;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
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