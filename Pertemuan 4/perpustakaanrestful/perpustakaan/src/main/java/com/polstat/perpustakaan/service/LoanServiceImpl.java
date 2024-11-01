package com.polstat.perpustakaan.service;

import com.polstat.perpustakaan.dto.LoanDto;
import com.polstat.perpustakaan.entity.Loan;
import com.polstat.perpustakaan.entity.Book;
import com.polstat.perpustakaan.entity.Member;
import com.polstat.perpustakaan.mapper.LoanMapper;
import com.polstat.perpustakaan.repository.LoanRepository;
import com.polstat.perpustakaan.repository.BookRepository;
import com.polstat.perpustakaan.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void loanBook(LoanDto loanDto) {
        Member member = memberRepository.findById(loanDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member tidak ditemukan"));

        Book book = bookRepository.findById(loanDto.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Buku tidak ditemukan"));

        Loan loan = LoanMapper.mapToLoan(loanDto, member, book);
        loan.setBorrowDate(LocalDate.now());
        loan.setLoanStatus(Loan.LoanStatus.BORROWED); // Use the enum here
        loanRepository.save(loan); // Ensure you save 'loan' instead of 'borrow'
    }

    @Override
    public void returnBook(Long borrowId) {
        Loan loan = loanRepository.findById(borrowId)
                .orElseThrow(() -> new IllegalArgumentException("Loan tidak ditemukan")); // Provide a message

        loan.setReturnDate(LocalDate.now());
        loan.setLoanStatus(Loan.LoanStatus.RETURNED); // Use the enum here

        LocalDate dueDate = loan.getBorrowDate().plusDays(14);
        if (loan.getReturnDate().isAfter(dueDate)) {
            loan.setOverdueDays((int) loan.getReturnDate().toEpochDay() - (int) dueDate.toEpochDay());
        } else {
            loan.setOverdueDays(0);
        }

        loanRepository.save(loan);
    }

    @Override
    public List<LoanDto> getAllLoans() {
        List<Loan> loans = loanRepository.findAll(); // Corrected from 'Loans' to 'Loan'
        return loans.stream()
                .map(LoanMapper::mapToLoanDto)
                .collect(Collectors.toList());
    }
}
