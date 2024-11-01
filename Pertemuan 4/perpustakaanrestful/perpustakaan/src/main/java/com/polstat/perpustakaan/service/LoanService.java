package com.polstat.perpustakaan.service;

import com.polstat.perpustakaan.dto.LoanDto;

import java.util.List;

public interface LoanService {
    void loanBook(LoanDto LoanDto);
    void returnBook(Long LoanId);
    List<LoanDto> getAllLoans();
}
